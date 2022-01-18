package cofh.thermal.core.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class ElementalProjectileEntity extends DamagingProjectileEntity {

    public ElementalProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {

        super(type, world);
    }

    public ElementalProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {

        super(type, shooter, accelX, accelY, accelZ, world);
    }

    public ElementalProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, double x, double y, double z, double accelX, double accelY, double accelZ, World world) {

        super(type, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    public void tick() {

        Entity owner = getOwner();
        if (level.isClientSide || (owner == null || !owner.removed) && level.hasChunkAt(blockPosition())) {
            if (!leftOwner) {
                leftOwner = checkLeftOwner();
            }
            if (!level.isClientSide) {
                setSharedFlag(6, isGlowing());
            }
            baseTick();
            if (shouldBurn()) {
                setSecondsOnFire(1);
            }

            RayTraceResult entityResult = ProjectileHelper.getHitResult(this, this::canHitEntity);
            if (entityResult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, entityResult)) {
                onHit(entityResult);
            }

            checkInsideBlocks();
            Vector3d velocity = getDeltaMovement();
            Vector3d pos = position();
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float resistance = getInertia();
            if (isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    level.addParticle(ParticleTypes.BUBBLE, pos.x + velocity.x * i * 0.25F, pos.y + velocity.y * i * 0.25F, pos.z + velocity.z * i * 0.25F, velocity.x, velocity.y, velocity.z);
                }
                resistance = getWaterInertia();
            }

            level.addParticle(getTrailParticle(), pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
            setDeltaMovement(velocity.add(xPower, yPower, zPower).scale(resistance));
            setPos(pos.x + velocity.x, pos.y + velocity.y, pos.z + velocity.z);
        } else {
            remove();
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
    public IPacket<?> getAddEntityPacket() {

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
}
