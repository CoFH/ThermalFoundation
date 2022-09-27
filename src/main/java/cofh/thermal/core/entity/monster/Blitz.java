package cofh.thermal.core.entity.monster;

import cofh.thermal.core.config.ThermalClientConfig;
import cofh.thermal.core.entity.projectile.BlitzProjectile;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

import static cofh.core.init.CoreMobEffects.SHOCKED;
import static cofh.core.init.CoreParticles.SPARK;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreSounds.*;
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BLITZ;
import static cofh.thermal.lib.common.ThermalIDs.ID_BLITZ;

public class Blitz extends Monster {

    private static final EntityDataAccessor<Byte> ANGRY = SynchedEntityData.defineId(Blitz.class, EntityDataSerializers.BYTE);

    public static boolean canSpawn(EntityType<Blitz> entityType, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BLITZ).getAsBoolean() && Monster.checkMonsterSpawnRules(entityType, world, reason, pos, rand);
    }

    public Blitz(EntityType<? extends Blitz> type, Level world) {

        super(type, world);

        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.navigation = new FlyingPathNavigation(this, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);

        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BlitzAttackGoal(this));
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

        return ThermalClientConfig.mobAmbientSounds ? SOUND_BLITZ_AMBIENT.get() : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {

        return SOUND_BLITZ_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {

        return SOUND_BLITZ_DEATH.get();
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
                this.level.addParticle(isAngry() ? (SimpleParticleType) SPARK.get() : ParticleTypes.CLOUD, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {

        return super.canBeAffected(effect) && !effect.getEffect().equals(SHOCKED.get());
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {

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

        private final Blitz blitz;
        private int attackTime;
        private int navTime;

        public BlitzAttackGoal(Blitz blitzIn) {

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
            Vec3 pos = blitz.getEyePosition(0.5F);
            Vec3 targetPos = target.position().add(0, target.getBbHeight() * 0.5F, 0);
            Vec3 diff = targetPos.subtract(pos);
            double distSqr = blitz.distanceToSqr(target);
            if (blitz.getSensing().hasLineOfSight(target) && distSqr < getFollowDistance() * getFollowDistance()) {

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
                        Level world = blitz.level;
                        world.playSound(null, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, SOUND_BLITZ_SHOOT.get(), SoundSource.HOSTILE, 1.0F, (blitz.random.nextFloat() - 0.5F) * 0.2F + 1.0F);
                        // imagine using what you learn in school
                        float gravity = 0.05F;
                        float horzSpeed = 0.8F;
                        double horzDist = Math.sqrt(diff.horizontalDistanceSqr());
                        double time = 1.25F * horzDist;
                        Vec3 horzVel = diff.scale(horzSpeed / horzDist);

                        BlitzProjectile projectile = new BlitzProjectile(pos.x, pos.y, pos.z, 0, -gravity, 0, world);
                        projectile.setDeltaMovement(horzVel.x, gravity * time + diff.y / time, horzVel.z);
                        projectile.setOwner(blitz);
                        world.addFreshEntity(projectile);
                    }
                    if (distSqr > 400.0) {
                        blitz.navigation.stop();
                        navTime = 0;
                    } else if (navTime <= 0) {
                        Vec3 want = (new Vec3(pos.x - targetPos.x, 0, pos.z - targetPos.z)).normalize().scale(30);
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
