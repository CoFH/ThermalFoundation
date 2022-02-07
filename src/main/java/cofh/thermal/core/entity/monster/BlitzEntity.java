package cofh.thermal.core.entity.monster;

import cofh.lib.util.references.CoreReferences;
import cofh.thermal.core.entity.projectile.BlitzProjectileEntity;
import cofh.thermal.lib.common.ThermalConfig;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
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
import static cofh.thermal.lib.common.ThermalIDs.ID_BLITZ;

public class BlitzEntity extends MonsterEntity {

    private static final DataParameter<Byte> ANGRY = EntityDataManager.defineId(BlitzEntity.class, DataSerializers.BYTE);

    public static boolean canSpawn(EntityType<BlitzEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BLITZ).getAsBoolean() && MonsterEntity.checkMonsterSpawnRules(entityType, world, reason, pos, rand);
    }

    public BlitzEntity(EntityType<? extends BlitzEntity> type, World world) {

        super(type, world);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.navigation = new FlyingPathNavigator(this, world);
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
        this.goalSelector.addGoal(8, new SwimGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
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
            if (this.random.nextInt(3) == 0) {
                this.level.addParticle(isAngry() ? CoreReferences.SPARK_PARTICLE : ParticleTypes.CLOUD, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean canBeAffected(EffectInstance effect) {

        return super.canBeAffected(effect) && !effect.equals(CoreReferences.SHOCKED);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {

        return new ItemStack(ITEMS.get("blitz_spawn_egg"));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {

        return source == DamageSource.LIGHTNING_BOLT || source.msgId.equals(ID_BLITZ) || super.isInvulnerableTo(source);
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
        private int navTime;

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

            this.navTime = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {

            this.blitz.setAngry(false);
            this.navTime = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            --attackTime;
            --navTime;
            LivingEntity target = blitz.getTarget();
            if (target == null) {
                return;
            }
            Vector3d pos = blitz.getEyePosition(0.5F);
            Vector3d targetPos = target.position().add(0, target.getBbHeight() * 0.5F, 0);
            Vector3d diff = targetPos.subtract(pos);
            double distSqr = blitz.distanceToSqr(target);
            if (blitz.getSensing().canSee(target) && distSqr < getFollowDistance() * getFollowDistance()) {
                blitz.getLookControl().setLookAt(target, 10.0F, 10.0F);
                blitz.setAngry(true);
                if (distSqr < 4.0) {
                    if (attackTime <= 0) {
                        attackTime = 20;
                        blitz.doHurtTarget(target);
                    }
                } else if (distSqr < 576.0) {
                    if (attackTime <= 0) {
                        attackTime = 20;
                        World world = blitz.level;
                        world.playSound(null, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, SOUND_BLITZ_SHOOT, SoundCategory.HOSTILE, 1.0F, (blitz.random.nextFloat() - 0.5F) * 0.2F + 1.0F);
                        // imagine using what you learn in school
                        float gravity = 0.05F;
                        float horzSpeed = 0.8F;
                        double horzDist = MathHelper.sqrt(getHorizontalDistanceSqr(diff));
                        double time = 1.25F * horzDist;
                        Vector3d horzVel = diff.scale(horzSpeed / horzDist);

                        BlitzProjectileEntity projectile = new BlitzProjectileEntity(pos.x, pos.y, pos.z, 0, -gravity, 0, world);
                        projectile.setDeltaMovement(horzVel.x, gravity * time + diff.y / time, horzVel.z);
                        projectile.setOwner(blitz);
                        world.addFreshEntity(projectile);
                    }
                    if (distSqr > 400.0) {
                        blitz.navigation.stop();
                        navTime = 0;
                    } else if (navTime <= 0) {
                        Vector3d want = (new Vector3d(pos.x - targetPos.x, 0, pos.z - targetPos.z)).normalize().scale(30);
                        blitz.navigation.moveTo(targetPos.x + want.x, targetPos.y, targetPos.z + want.z, 1.0D);
                        navTime = 15;
                    }
                } else if (navTime <= 0) {
                    blitz.navigation.moveTo(targetPos.x, targetPos.y, targetPos.z, 1.0D);
                    navTime = 15;
                }
            } else {
                blitz.setAngry(false);
            }
            super.tick();
        }

        private double getFollowDistance() {

            return this.blitz.getAttributeValue(Attributes.FOLLOW_RANGE);
        }

    }

}
