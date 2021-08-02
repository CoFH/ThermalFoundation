package cofh.thermal.core.entity.projectile;

import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.lib.util.references.CoreReferences.CHILLED;
import static cofh.thermal.core.init.TCoreReferences.BLIZZ_PROJECTILE_ENTITY;

public class BlizzProjectileEntity extends DamagingProjectileEntity {

    private static final int CLOUD_DURATION = 20;

    public static float baseDamage = 5.0F;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;
    public static int effectRadius = 2;

    public BlizzProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {

        super(type, world);
    }

    public BlizzProjectileEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {

        super(BLIZZ_PROJECTILE_ENTITY, shooter, accelX, accelY, accelZ, world);
    }

    public BlizzProjectileEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {

        super(BLIZZ_PROJECTILE_ENTITY, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    protected boolean isFireballFiery() {

        return false;
    }

    @Override
    protected IParticleData getParticle() {

        return ParticleTypes.ITEM_SNOWBALL;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity.isBurning()) {
                entity.extinguish();
            }
            if (!entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addPotionEffect(new EffectInstance(CHILLED, effectDuration, effectAmplifier, false, false));
            }
            entity.attackEntityFrom(BlizzDamageSource.causeDamage(this, func_234616_v_()), entity.isImmuneToFire() ? baseDamage + 3.0F : baseDamage);
        }
        if (Utils.isServerWorld(world)) {
            if (effectRadius > 0) {
                AreaUtils.freezeNearbyGround(this, world, this.getPosition(), effectRadius);
                AreaUtils.freezeSurfaceWater(this, world, this.getPosition(), effectRadius, false);
                AreaUtils.freezeSurfaceLava(this, world, this.getPosition(), effectRadius, false);
                makeAreaOfEffectCloud();
            }
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected void registerData() {

    }

    @Override
    public IPacket<?> createSpawnPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getPosX(), getPosY(), getPosZ());
        cloud.setRadius(1);
        cloud.setParticleData(ParticleTypes.ITEM_SNOWBALL);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

    // region DAMAGE SOURCE
    protected static class BlizzDamageSource extends EntityDamageSource {

        public BlizzDamageSource(Entity source) {

            super("blizz", source);
        }

        public static DamageSource causeDamage(BlizzProjectileEntity entityProj, Entity entitySource) {

            return (new IndirectEntityDamageSource("blizz", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
        }

    }
    // endregion
}
