package cofh.thermal.core.entity.monster;

import cofh.thermal.core.entity.projectile.BlitzProjectileEntity;
import cofh.thermal.lib.common.ThermalConfig;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreSounds.*;
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BLITZ;

public class BlitzEntity extends MonsterEntity {

    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private static final DataParameter<Byte> ANGRY = EntityDataManager.defineId(BlitzEntity.class, DataSerializers.BYTE);

    public static boolean canSpawn(EntityType<BlitzEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BLITZ).getAsBoolean() && MonsterEntity.checkMonsterSpawnRules(entityType, world, reason, pos, rand);
    }

    public BlitzEntity(EntityType<? extends BlitzEntity> type, World world) {

        super(type, world);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(PathNodeType.LAVA, -1.0F);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BlitzAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void defineSynchedData() {

        super.defineSynchedData();
        this.entityData.define(ANGRY, (byte) 0);
    }

    @Override
    protected SoundEvent getAmbientSound() {

        return ThermalConfig.mobAmbientSounds ? SOUND_BLITZ_AMBIENT : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {

        return SOUND_BLITZ_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {

        return SOUND_BLITZ_DEATH;
    }

    @Override
    public void aiStep() {

        if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
        if (this.level.isClientSide) {
            //            if (this.rand.nextInt(256) == 0 && !this.isSilent()) {
            //                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SOUND_BLITZ_ROAM, this.getSoundCategory(), 0.5F + 0.25F * this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, true);
            //            }
            if (this.random.nextInt(2) == 0) {
                this.level.addParticle(ParticleTypes.CLOUD, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        super.aiStep();
    }

    @Override
    protected void customServerAiStep() {

        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.random.nextGaussian() * 3.0F;
        }
        LivingEntity livingentity = this.getTarget();
        if (livingentity != null && livingentity.getEyeY() > this.getEyeY() + (double) this.heightOffset && this.canAttack(livingentity)) {
            Vector3d vec3d = this.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, ((double) 0.3F - vec3d.y) * (double) 0.3F, 0.0D));
            this.hasImpulse = true;
        }
        super.customServerAiStep();
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {

        return new ItemStack(ITEMS.get("blitz_spawn_egg"));
    }

    // region ANGER MANAGEMENT
    public boolean isAngry() {

        return (this.entityData.get(ANGRY) & 1) != 0;
    }

    protected void setAngry(boolean angry) {

        byte b0 = this.entityData.get(ANGRY);
        if (angry) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }
        this.entityData.set(ANGRY, b0);
    }
    // endregion

    static class BlitzAttackGoal extends Goal {

        private final BlitzEntity blitz;
        private int attackTime;
        private int chaseStep;

        public BlitzAttackGoal(BlitzEntity blitzIn) {

            this.blitz = blitzIn;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {

            LivingEntity target = this.blitz.getTarget();
            return target != null && target.isAlive() && this.blitz.canAttack(target);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {

        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {

            this.blitz.setAngry(false);
            this.chaseStep = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            --attackTime;
            LivingEntity target = blitz.getTarget();
            if (target == null) {
                return;
            }
            Vector3d pos = blitz.getEyePosition(0.5F);
            Vector3d targetPos = target.position().add(0, target.getBbHeight() * 0.5F, 0);
            Vector3d diff = targetPos.subtract(pos);
            double distSqr = blitz.distanceToSqr(target);
            if (blitz.getSensing().canSee(target) && distSqr < getFollowDistance() * getFollowDistance()) {
                chaseStep = 0;
                blitz.getLookControl().setLookAt(target, 10.0F, 10.0F);
                if (distSqr < 4.0) {
                    if (attackTime <= 0) {
                        attackTime = 20;
                        blitz.doHurtTarget(target);
                    }
                } else {
                    blitz.setAngry(true);
                    if (attackTime <= 0) {
                        attackTime = 20;
                        World world = blitz.level;
                        world.playSound(null, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, SOUND_BLITZ_SHOOT, SoundCategory.HOSTILE, 1.0F, (blitz.random.nextFloat() - 0.5F) * 0.2F + 1.0F);
                        // imagine using what you learn in school
                        float gravity = 0.05F;
                        float horzSpeed = 0.5F;
                        double horzDist = MathHelper.sqrt(getHorizontalDistanceSqr(diff));
                        double time = 2 * horzDist;
                        Vector3d horzVel = diff.scale(horzSpeed / horzDist);

                        BlitzProjectileEntity projectile = new BlitzProjectileEntity(pos.x, pos.y, pos.z, 0, -gravity, 0, world);
                        projectile.setDeltaMovement(horzVel.x, gravity * time + diff.y / time, horzVel.z);
                        projectile.setOwner(blitz);
                        world.addFreshEntity(projectile);
                    }
                    if (distSqr < 256.0) {
                        Vector3d want = (new Vector3d(blitz.getX() - target.getX(), 0, blitz.getZ() - target.getZ())).normalize().scale(20);
                        blitz.getMoveControl().setWantedPosition(want.x, blitz.getZ(), want.z, 1.0D);
                    } else if (distSqr > 576.0) {
                        blitz.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.0D);
                    }
                    return;
                }
            } else {
                blitz.setAngry(false);
            }
            if (chaseStep < 5) {
                ++chaseStep;
                blitz.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.0D);
            }
            super.tick();
        }

        private double getFollowDistance() {

            return this.blitz.getAttributeValue(Attributes.FOLLOW_RANGE);
        }

    }

}
