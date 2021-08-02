package cofh.thermal.core.entity.monster;

import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
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
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BASALZ;

public class BasalzEntity extends MonsterEntity {

    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private static final DataParameter<Byte> ANGRY = EntityDataManager.createKey(BasalzEntity.class, DataSerializers.BYTE);

    public static boolean canSpawn(EntityType<BasalzEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BASALZ).getAsBoolean() && MonsterEntity.canMonsterSpawnInLight(entityType, world, reason, pos, rand);
    }

    public BasalzEntity(EntityType<? extends BasalzEntity> type, World world) {

        super(type, world);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, 2.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.experienceValue = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BasalzAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
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

        return ThermalConfig.mobAmbientSounds ? SOUND_BASALZ_AMBIENT : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {

        return SOUND_BASALZ_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {

        return SOUND_BASALZ_DEATH;
    }

    @Override
    public void livingTick() {

        if (!this.onGround && this.getMotion().y < 0.0D) {
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        }
        if (this.world.isRemote) {
            //            if (this.rand.nextInt(256) == 0 && !this.isSilent()) {
            //                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SOUND_BASALZ_ROAM, this.getSoundCategory(), 0.5F + 0.25F * this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, true);
            //            }
            if (this.isAngry() && this.rand.nextInt(2) == 0) {
                this.world.addParticle(ParticleTypes.FALLING_LAVA, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), 0.0D, 0.0D, 0.0D);
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

        return new ItemStack(ITEMS.get("basalz_spawn_egg"));
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

    static class BasalzAttackGoal extends Goal {

        private final BasalzEntity basalz;
        private int attackStep;
        private int attackTime;
        private int chaseStep;

        public BasalzAttackGoal(BasalzEntity basalzIn) {

            this.basalz = basalzIn;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {

            LivingEntity livingentity = this.basalz.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && this.basalz.canAttack(livingentity);
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

            this.basalz.setAngry(false);
            this.chaseStep = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            --this.attackTime;
            LivingEntity livingentity = this.basalz.getAttackTarget();
            if (livingentity != null) {
                boolean flag = this.basalz.getEntitySenses().canSee(livingentity);
                if (flag) {
                    this.chaseStep = 0;
                } else {
                    ++this.chaseStep;
                }
                double d0 = this.basalz.getDistanceSq(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }
                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.basalz.attackEntityAsMob(livingentity);
                    }
                    this.basalz.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getPosX() - this.basalz.getPosX();
                    double d2 = livingentity.getPosYHeight(0.5D) - this.basalz.getPosYHeight(0.5D);
                    double d3 = livingentity.getPosZ() - this.basalz.getPosZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.basalz.setAngry(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.basalz.setAngry(false);
                        }
                        if (this.attackStep > 1) {
                            float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                            this.basalz.world.playEvent(null, 1018, this.basalz.getPosition(), 0);
                            BasalzProjectileEntity projectile = new BasalzProjectileEntity(this.basalz, d1 + this.basalz.getRNG().nextGaussian() * (double) f, d2, d3 + this.basalz.getRNG().nextGaussian() * (double) f, this.basalz.world);
                            projectile.setPosition(projectile.getPosX(), this.basalz.getPosYHeight(0.5D) + 0.5D, projectile.getPosZ());
                            this.basalz.world.addEntity(projectile);
                        }
                    }
                    this.basalz.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);
                } else if (this.chaseStep < 5) {
                    this.basalz.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), 1.0D);
                }
                super.tick();
            }
        }

        private double getFollowDistance() {

            return this.basalz.getAttributeValue(Attributes.FOLLOW_RANGE);
        }

    }

}
