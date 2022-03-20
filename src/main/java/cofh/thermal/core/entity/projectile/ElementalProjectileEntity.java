package cofh.thermal.core.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class ElementalProjectileEntity extends AbstractHurtingProjectile {

    public ElementalProjectileEntity(EntityType<? extends AbstractHurtingProjectile> type, Level world) {

        super(type, world);
    }

    public ElementalProjectileEntity(EntityType<? extends AbstractHurtingProjectile> type, LivingEntity shooter, double accelX, double accelY, double accelZ, Level world) {

        super(type, shooter, accelX, accelY, accelZ, world);
    }

    public ElementalProjectileEntity(EntityType<? extends AbstractHurtingProjectile> type, double x, double y, double z, double accelX, double accelY, double accelZ, Level world) {

        super(type, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    public void tick() {

        Entity owner = getOwner();
        if (level.isClientSide || (owner == null || !owner.isRemoved()) && level.hasChunkAt(blockPosition())) {
            if (!leftOwner) {
                leftOwner = checkLeftOwner();
            }
            if (!level.isClientSide) {
                setSharedFlag(6, isCurrentlyGlowing());
            }
            baseTick();
            if (shouldBurn()) {
                setSecondsOnFire(1);
            }

            HitResult entityResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (entityResult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, entityResult)) {
                onHit(entityResult);
            }

            checkInsideBlocks();
            Vec3 velocity = getDeltaMovement();
            Vec3 pos = position();
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float resistance = getInertia();
            if (isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    level.addParticle(ParticleTypes.BUBBLE, pos.x + velocity.x * i * 0.25F, pos.y + velocity.y * i * 0.25F, pos.z + velocity.z * i * 0.25F, velocity.x, velocity.y, velocity.z);
                }
                resistance = getWaterInertia();
            }

            level.addParticle(getTrailParticle(), pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
            setDeltaMovement(velocity.add(xPower, yPower, zPower).scale(resistance));
            setPos(pos.x + velocity.x, pos.y + velocity.y, pos.z + velocity.z);
        } else {
            discard();
        }

    }

    protected float getWaterInertia() {

        return 0.8F;
    }

    @Override
    protected boolean shouldBurn() {

        return false;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public float getBrightness() {

        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        return false;
    }

    @Override
    public float getPickRadius() {

        return 0.0F;
    }

    @Override
    public boolean isPickable() {

        return false;
    }

    // region HELPERS
    public abstract float getDamage(Entity target);

    public abstract int getEffectAmplifier(Entity target);

    public abstract int getEffectDuration(Entity target);
    // endregion
}
