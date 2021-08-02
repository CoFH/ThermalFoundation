package cofh.thermal.core.entity.monster;

import cofh.thermal.core.entity.projectile.BlizzProjectileEntity;
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
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BLIZZ;

public class BlizzEntity extends MonsterEntity {

    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private static final DataParameter<Byte> ANGRY = EntityDataManager.createKey(BlizzEntity.class, DataSerializers.BYTE);

    public static boolean canSpawn(EntityType<BlizzEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BLIZZ).getAsBoolean() && MonsterEntity.canMonsterSpawnInLight(entityType, world, reason, pos, rand);
    }

    public BlizzEntity(EntityType<? extends BlizzEntity> type, World world) {

        super(type, world);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, -1.0F);
        this.experienceValue = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BlizzAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerData() {

        super.registerData();
        this.dataManager.register(ANGRY, (byte) 0);
    }

    @Override
    protected SoundEvent getAmbientSound() {

        return ThermalConfig.mobAmbientSounds ? SOUND_BLIZZ_AMBIENT : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {

        return SOUND_BLIZZ_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {

        return SOUND_BLIZZ_DEATH;
    }

    @Override
    public void livingTick() {

        if (!this.onGround && this.getMotion().y < 0.0D) {
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        }
        if (this.world.isRemote) {
            //            if (this.rand.nextInt(256) == 0 && !this.isSilent()) {
            //                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SOUND_BLIZZ_ROAM, this.getSoundCategory(), 0.5F + 0.25F * this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, true);
            //            }
            if (this.rand.nextInt(2) == 0) {
                this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        super.livingTick();
    }

    @Override
    protected void updateAITasks() {

        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
        }
        LivingEntity livingentity = this.getAttackTarget();
        if (livingentity != null && livingentity.getPosYEye() > this.getPosYEye() + (double) this.heightOffset && this.canAttack(livingentity)) {
            Vector3d vec3d = this.getMotion();
            this.setMotion(this.getMotion().add(0.0D, ((double) 0.3F - vec3d.y) * (double) 0.3F, 0.0D));
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {

        return new ItemStack(ITEMS.get("blizz_spawn_egg"));
    }

    // region ANGER MANAGEMENT
    public boolean isAngry() {

        return (this.dataManager.get(ANGRY) & 1) != 0;
    }

    protected void setAngry(boolean angry) {

        byte b0 = this.dataManager.get(ANGRY);
        if (angry) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }
        this.dataManager.set(ANGRY, b0);
    }
    // endregion

    static class BlizzAttackGoal extends Goal {

        private final BlizzEntity blizz;
        private int attackStep;
        private int attackTime;
        private int chaseStep;

        public BlizzAttackGoal(BlizzEntity blizzIn) {

            this.blizz = blizzIn;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {

            LivingEntity livingentity = this.blizz.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && this.blizz.canAttack(livingentity);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {

            this.attackStep = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {

            this.blizz.setAngry(false);
            this.chaseStep = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            --this.attackTime;
            LivingEntity livingentity = this.blizz.getAttackTarget();
            if (livingentity != null) {
                boolean flag = this.blizz.getEntitySenses().canSee(livingentity);
                if (flag) {
                    this.chaseStep = 0;
                } else {
                    ++this.chaseStep;
                }
                double d0 = this.blizz.getDistanceSq(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }
                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.blizz.attackEntityAsMob(livingentity);
                    }
                    this.blizz.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getPosX() - this.blizz.getPosX();
                    double d2 = livingentity.getPosYHeight(0.5D) - this.blizz.getPosYHeight(0.5D);
                    double d3 = livingentity.getPosZ() - this.blizz.getPosZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.blizz.setAngry(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.blizz.setAngry(false);
                        }
                        if (this.attackStep > 1) {
                            float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                            this.blizz.world.playEvent(null, 1018, this.blizz.getPosition(), 0);
                            // this.blizz.world.playSound(blizz.getPosX() + 0.5D, blizz.getPosY() + 0.5D, blizz.getPosZ() + 0.5D, SOUND_BLIZZ_SHOOT, SoundCategory.HOSTILE, 1.0F, (blizz.rand.nextFloat() - blizz.rand.nextFloat()) * 0.2F + 1.0F, false);
                            BlizzProjectileEntity projectile = new BlizzProjectileEntity(this.blizz, d1 + this.blizz.getRNG().nextGaussian() * (double) f, d2, d3 + this.blizz.getRNG().nextGaussian() * (double) f, this.blizz.world);
                            projectile.setPosition(projectile.getPosX(), this.blizz.getPosYHeight(0.5D) + 0.5D, projectile.getPosZ());
                            this.blizz.world.addEntity(projectile);
                        }
                    }
                    this.blizz.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);
                } else if (this.chaseStep < 5) {
                    this.blizz.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), 1.0D);
                }
                super.tick();
            }
        }

        private double getFollowDistance() {

            return this.blizz.getAttributeValue(Attributes.FOLLOW_RANGE);
        }

    }

}
