package cofh.thermal.core.entity.monster;

import cofh.lib.util.references.CoreReferences;
import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
import cofh.thermal.lib.common.ThermalConfig;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;
import java.util.Random;

import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreSounds.*;
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BASALZ;
import static cofh.thermal.lib.common.ThermalIDs.ID_BASALZ;

public class Basalz extends Monster {

    protected static final int DEFAULT_ORBIT = 8;
    protected static final int DEPLOY_TIME = 6;

    private static final EntityDataAccessor<Byte> ANGRY = SynchedEntityData.defineId(Blitz.class, EntityDataSerializers.BYTE);
    private static final Vec3 VERT = new Vec3(0, 1, 0);

    protected int attackTime = 0;
    protected int angerTime = 72000;
    protected boolean wasAngry = false;

    public static boolean canSpawn(EntityType<Basalz> entityType, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BASALZ).getAsBoolean() && Monster.checkMonsterSpawnRules(entityType, world, reason, pos, rand);
    }

    public Basalz(EntityType<? extends Basalz> type, Level world) {

        super(type, world);

        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 2.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);

        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BasalzAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FloatGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder registerAttributes() {

        return Monster.createMonsterAttributes()
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
            Vec3 pos = this.position();
            for (Entity target : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0F, 1.0f, 4.0F))) {
                if (!(target instanceof Basalz) && distanceToSqr(target) < 12.25 && hasLineOfSight(target)) {
                    attackTime = 15;
                    Vec3 targetPos = target.position();
                    Vec3 offset = targetPos.subtract(pos).normalize().cross(VERT).scale(0.5);
                    BasalzProjectileEntity projectile = new BasalzProjectileEntity(targetPos.x + offset.x, getY() + this.getBbHeight() * 0.5F, targetPos.z + offset.z, 0, 0, 0, level);
                    projectile.setDeltaMovement(-offset.x, 0, -offset.z);
                    projectile.setOwner(this);
                    projectile.onHit(new EntityHitResult(target));
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
    public boolean canBeAffected(MobEffectInstance effect) {

        return super.canBeAffected(effect) && !effect.getEffect().equals(CoreReferences.SUNDERED);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {

        return new ItemStack(ITEMS.get("basalz_spawn_egg"));
    }

    @OnlyIn (Dist.CLIENT)
    @Override
    public AABB getBoundingBoxForCulling() {

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

    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {

        super.onSyncedDataUpdated(data);
        if (level.isClientSide && data.equals(ANGRY) && (isAngry() != wasAngry)) {
            angerTime = Math.max(0, DEPLOY_TIME - angerTime);
            wasAngry = isAngry();
        }
    }
    // endregion

    static class BasalzAttackGoal extends Goal {

        private final Basalz basalz;
        private int attackTime;
        private int refreshTime = 100;
        private int chaseStep;
        private int navTime;

        public BasalzAttackGoal(Basalz basalzIn) {

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
            Vec3 pos = basalz.position();
            Vec3 targetPos = target.position();
            double distSqr = basalz.distanceToSqr(target);
            if (basalz.getSensing().hasLineOfSight(target) && distSqr < getFollowDistance() * getFollowDistance()) {
                chaseStep = 0;
                if (basalz.getOrbit() > 0) {
                    basalz.getLookControl().setLookAt(target, 10.0F, 10.0F);
                    if (!basalz.isAngry()) {
                        basalz.setAngry(true);
                        basalz.level.playSound(null, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, SOUND_BASALZ_SHOOT, SoundSource.HOSTILE, 2.5F, (basalz.random.nextFloat() - 0.5F) * 0.2F + 1.0F);
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
                            Vec3 diff = (new Vec3(pos.x - targetPos.x, 0, pos.z - targetPos.z)).normalize().scale(16);
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
