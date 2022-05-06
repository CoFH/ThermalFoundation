package cofh.thermal.core.entity.monster;

import cofh.lib.util.references.CoreReferences;
import cofh.thermal.core.config.ThermalClientConfig;
import cofh.thermal.core.entity.projectile.BlizzProjectile;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreSounds.*;
import static cofh.thermal.lib.common.ThermalFlags.FLAG_MOB_BLIZZ;
import static cofh.thermal.lib.common.ThermalIDs.ID_BLIZZ;

public class Blizz extends Monster {

    private static final EntityDataAccessor<Byte> ANGRY = SynchedEntityData.defineId(Blizz.class, EntityDataSerializers.BYTE);

    public static boolean canSpawn(EntityType<Blizz> entityType, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand) {

        return ThermalFlags.getFlag(FLAG_MOB_BLIZZ).getAsBoolean() && Monster.checkMonsterSpawnRules(entityType, world, reason, pos, rand);
    }

    public Blizz(EntityType<? extends Blizz> type, Level world) {

        super(type, world);

        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.navigation = new FlyingPathNavigation(this, world);
        //this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);

        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(4, new BlizzAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FloatGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder registerAttributes() {

        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
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

        return ThermalClientConfig.mobAmbientSounds ? SOUND_BLIZZ_AMBIENT : null;
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
    public void aiStep() {

        if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
        if (this.level.isClientSide) {
            //            if (this.rand.nextInt(256) == 0 && !this.isSilent()) {
            //                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SOUND_BLIZZ_ROAM, this.getSoundCategory(), 0.5F + 0.25F * this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, true);
            //            }
            if (this.random.nextInt(2) == 0) {
                this.level.addParticle(CoreReferences.FROST_PARTICLE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        super.aiStep();
    }

    @Override
    protected void onChangedBlock(BlockPos pos) {

        FrostWalkerEnchantment.onEntityMoved(this, level, pos, 1);

        if (this.shouldRemoveSoulSpeed(this.getBlockStateOn())) {
            this.removeSoulSpeed();
        }
        this.tryAddSoulSpeed();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        return super.hurt(source, source.isFire() ? amount + 3 : amount);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {

        return super.canBeAffected(effect) && !effect.getEffect().equals(CoreReferences.CHILLED);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {

        return false;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {

        return new ItemStack(ITEMS.get("blizz_spawn_egg"));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {

        return source.msgId.equals(ID_BLIZZ) || super.isInvulnerableTo(source);
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

    static class BlizzAttackGoal extends Goal {

        private final Blizz blizz;
        private final Vec3[] hoverOffsets = getHoverOffsets(new Vec3(1.5, 2.25, 0), 16);
        private int attackTime;
        private int hoverStep;
        private int chaseStep;

        public BlizzAttackGoal(Blizz blizzIn) {

            this.blizz = blizzIn;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {

            LivingEntity target = this.blizz.getTarget();
            return target != null && target.isAlive() && this.blizz.canAttack(target);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {

            this.chaseStep = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {

            this.blizz.setAngry(false);
            this.chaseStep = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            --attackTime;
            LivingEntity target = blizz.getTarget();
            if (target == null) {
                return;
            }
            Vec3 pos = blizz.position();
            Vec3 targetPos = target.getEyePosition(0.5F);
            Vec3 diff = pos.subtract(targetPos);
            double distSqr = blizz.distanceToSqr(target);
            double horzDistSqr = diff.horizontalDistanceSqr();
            if (blizz.getSensing().hasLineOfSight(target) && distSqr < getFollowDistance() * getFollowDistance()) {
                chaseStep = 0;
                blizz.getLookControl().setLookAt(target, 10.0F, 10.0F);
                if (distSqr < 4.0) {
                    if (attackTime <= 0) {
                        attackTime = 20;
                        blizz.doHurtTarget(target);
                    }
                } else if (pos.y > target.getY() + 2) {
                    blizz.setAngry(true);
                    if (attackTime <= 0) {
                        attackTime = 7;
                        Random rand = blizz.getRandom();
                        Level world = blizz.level;
                        //TODO: less annoying sound
                        //world.playSound(null, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, SOUND_BASALZ_SHOOT, SoundCategory.HOSTILE, 1.0F, (rand.nextFloat() - 0.5F) * 0.2F + 1.0F);
                        BlizzProjectile projectile;
                        if (horzDistSqr < 9) {
                            projectile = new BlizzProjectile(targetPos.x + rand.nextGaussian() * 0.8F, pos.y - rand.nextFloat() * 0.5F - 0.2F, targetPos.z + rand.nextGaussian() * 0.8F, 0, -1.0, 0, world);
                        } else {
                            projectile = new BlizzProjectile(pos.x + rand.nextGaussian() * 0.8F, pos.y - rand.nextFloat() - 0.2F, pos.z + rand.nextGaussian() * 0.8F, 0, -1.0, 0, world);
                        }
                        projectile.setOwner(blizz);
                        world.addFreshEntity(projectile);
                        ++hoverStep;
                        hoverStep %= hoverOffsets.length;
                    }
                    blizz.getMoveControl().setWantedPosition(targetPos.x + hoverOffsets[hoverStep].x, targetPos.y + hoverOffsets[hoverStep].y, targetPos.z + hoverOffsets[hoverStep].z, 1.0D);
                    return;
                } else {
                    blizz.setAngry(false);
                }
            } else {
                blizz.setAngry(false);
            }
            if (chaseStep < 5) {
                ++chaseStep;
                blizz.getMoveControl().setWantedPosition(targetPos.x, targetPos.y + hoverOffsets[0].y, targetPos.z, 1.0D);
            }
            super.tick();
        }

        private double getFollowDistance() {

            return this.blizz.getAttributeValue(Attributes.FOLLOW_RANGE);
        }

        protected static Vec3[] getHoverOffsets(Vec3 start, int steps) {

            float stepRad = (float) Math.PI * 2 / steps;
            Vec3[] offsets = new Vec3[steps];
            for (int i = 0; i < steps; ++i) {
                offsets[i] = start.yRot(stepRad * i);
            }
            return offsets;
        }

    }

}
