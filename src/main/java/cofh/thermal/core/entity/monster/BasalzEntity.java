package cofh.thermal.core.entity.monster;

import cofh.lib.util.references.CoreReferences;
import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
import cofh.thermal.lib.common.ThermalConfig;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.entity.Entity;
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;
import java.util.Random;

import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreSounds.*;
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BASALZ;
import static cofh.thermal.lib.common.ThermalIDs.ID_BASALZ;

public class BasalzEntity extends MonsterEntity {

    protected static final int DEFAULT_ORBIT = 8;
    public static final int DEPLOY_TIME = 6;
    private static final DataParameter<Byte> ANGRY = EntityDataManager.defineId(BasalzEntity.class, DataSerializers.BYTE);
    private static final Vector3d vert = new Vector3d(0, 1, 0);
    protected int attackTime = 0;
    public int angerTime = 72000;
    protected boolean wasAngry = false;

    public static boolean canSpawn(EntityType<BasalzEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BASALZ).getAsBoolean() && MonsterEntity.checkMonsterSpawnRules(entityType, world, reason, pos, rand);
    }

    public BasalzEntity(EntityType<? extends BasalzEntity> type, World world) {

        super(type, world);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(PathNodeType.LAVA, 2.0F);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BasalzAttackGoal(this));
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
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void defineSynchedData() {

        super.defineSynchedData();
        this.entityData.define(ANGRY, (byte) 16);
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
    public void aiStep() {

        if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
        if (this.level.isClientSide) {
            //if (this.random.nextInt(256) == 0 && !this.isSilent()) {
            //    this.playSound(SOUND_BASALZ_ROAM, 0.5F + 0.25F * this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
            //}
            if (this.isAngry() && this.random.nextInt(2) == 0) {
                this.level.addParticle(ParticleTypes.FALLING_LAVA, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
            ++angerTime;
        } else if (isAlive() && isAngry() && attackTime <= 0 && getOrbit() > 0) {
            Vector3d pos = this.position();
            for (Entity target : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0F, 1.0f, 4.0F))) {
                if (!(target instanceof BasalzEntity) && distanceToSqr(target) < 12.25 && canSee(target)) {
                    attackTime = 15;
                    Vector3d targetPos = target.position();
                    Vector3d offset = targetPos.subtract(pos).normalize().cross(vert).scale(0.5);
                    BasalzProjectileEntity projectile = new BasalzProjectileEntity(targetPos.x + offset.x, getY() + this.getBbHeight() * 0.5F, targetPos.z + offset.z, 0, 0, 0, level);
                    projectile.setDeltaMovement(-offset.x, 0, -offset.z);
                    projectile.setOwner(this);
                    projectile.onHit(new EntityRayTraceResult(target));
                    if (getOrbit() > 0) {
                        reduceOrbit();
                    } else {
                        break;
                    }
                }
            }
        } else {
            --attackTime;
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        return super.hurt(source, source == DamageSource.LIGHTNING_BOLT ? amount + 3 : amount);
    }

    @Override
    public boolean canBeAffected(EffectInstance effect) {

        return super.canBeAffected(effect) && !effect.equals(CoreReferences.SUNDERED);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {

        return new ItemStack(ITEMS.get("basalz_spawn_egg"));
    }

    @OnlyIn (Dist.CLIENT)
    @Override
    public AxisAlignedBB getBoundingBoxForCulling() {

        return isAngry() ? super.getBoundingBoxForCulling().inflate(4) : super.getBoundingBoxForCulling();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {

        return source.msgId.equals(ID_BASALZ) || super.isInvulnerableTo(source);
    }

    // region ANGER/ORBIT MANAGEMENT
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

    public int getOrbit() {

        return (this.entityData.get(ANGRY) & 62) >> 1;
    }

    public void setOrbit(int orbit) {

        this.entityData.set(ANGRY, (byte) ((this.entityData.get(ANGRY) & -63) | ((orbit & 31) << 1)));
    }

    public void reduceOrbit() {

        setOrbit(Math.max(getOrbit() - 1, 0));
    }

    public void resetOrbit() {

        setOrbit(DEFAULT_ORBIT);
    }

    public void onSyncedDataUpdated(DataParameter<?> data) {

        super.onSyncedDataUpdated(data);
        if (level.isClientSide && data.equals(ANGRY) && (isAngry() != wasAngry)) {
            angerTime = Math.max(0, DEPLOY_TIME - angerTime);
            wasAngry = isAngry();
        }
    }
    // endregion

    static class BasalzAttackGoal extends Goal {

        private final BasalzEntity basalz;
        private int attackTime;
        private int refreshTime = 100;
        private int chaseStep;
        private int navTime;

        public BasalzAttackGoal(BasalzEntity basalzIn) {

            this.basalz = basalzIn;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {

            LivingEntity livingentity = this.basalz.getTarget();
            return livingentity != null && livingentity.isAlive() && this.basalz.canAttack(livingentity);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {

            this.chaseStep = 0;
            this.navTime = 0;
            this.refreshTime = 100;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {

            this.basalz.setAngry(false);
            this.chaseStep = 0;
            this.navTime = 0;
            this.refreshTime = 100;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            --attackTime;
            --navTime;
            LivingEntity target = basalz.getTarget();
            if (target == null) {
                return;
            }
            Vector3d pos = basalz.position();
            Vector3d targetPos = target.position();
            double distSqr = basalz.distanceToSqr(target);
            if (basalz.getSensing().canSee(target) && distSqr < getFollowDistance() * getFollowDistance()) {
                chaseStep = 0;
                if (basalz.getOrbit() > 0) {
                    basalz.getLookControl().setLookAt(target, 10.0F, 10.0F);
                    if (!basalz.isAngry()) {
                        basalz.setAngry(true);
                        basalz.level.playSound(null, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, SOUND_BASALZ_SHOOT, SoundCategory.HOSTILE, 2.5F, (basalz.random.nextFloat() - 0.5F) * 0.2F + 1.0F);
                        navTime = 0;
                    }
                    if (distSqr < 2.25) {
                        if (attackTime <= 0) {
                            attackTime = 20;
                            basalz.doHurtTarget(target);
                        }
                    } else if (distSqr < 12.25) {
                        basalz.navigation.stop();
                        navTime = 0;
                    } else if (navTime <= 0) {
                        basalz.navigation.moveTo(target, 1.0D);
                        navTime = 15;
                    }
                } else {
                    if (basalz.isAngry()) {
                        basalz.setAngry(false);
                        navTime = 0;
                    }
                    if (refreshTime > 0) {
                        --refreshTime;
                        if (distSqr < 144.0 && navTime <= 0) {
                            Vector3d diff = (new Vector3d(pos.x - targetPos.x, 0, pos.z - targetPos.z)).normalize().scale(16);
                            basalz.getLookControl().setLookAt(targetPos.x + diff.x, basalz.getEyeY(), targetPos.z + diff.z, 10.0F, 10.0F);
                            basalz.navigation.moveTo(targetPos.x + diff.x, targetPos.y, targetPos.z + diff.z, 1.0D);
                            navTime = 15;
                        }
                    } else {
                        refreshTime = 100;
                        basalz.resetOrbit();
                    }
                }
            } else {
                if (chaseStep < 5) {
                    ++chaseStep;
                    basalz.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.0D);
                } else {
                    basalz.setAngry(false);
                }
            }
            super.tick();
        }

        private double getFollowDistance() {

            return this.basalz.getAttributeValue(Attributes.FOLLOW_RANGE);
        }

    }

}
