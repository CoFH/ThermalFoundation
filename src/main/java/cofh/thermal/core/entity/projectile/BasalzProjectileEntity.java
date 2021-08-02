package cofh.thermal.core.entity.projectile;

import cofh.lib.util.Utils;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static cofh.lib.util.references.CoreReferences.SUNDERED;
import static cofh.thermal.core.init.TCoreReferences.BASALZ_PROJECTILE_ENTITY;

public class BasalzProjectileEntity extends DamagingProjectileEntity {

    public static float baseDamage = 7.0F;
    public static int knockbackStrength = 2;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;

    public BasalzProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {

        super(type, world);
    }

    public BasalzProjectileEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {

        super(BASALZ_PROJECTILE_ENTITY, shooter, accelX, accelY, accelZ, world);
    }

    public BasalzProjectileEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {

        super(BASALZ_PROJECTILE_ENTITY, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    protected boolean isFireballFiery() {

        return false;
    }

    @Override
    protected IParticleData getParticle() {

        return ParticleTypes.FALLING_LAVA;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (!entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addPotionEffect(new EffectInstance(SUNDERED, effectDuration, effectAmplifier, false, false));
                Vector3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double) knockbackStrength * 0.6D);
                if (vec3d.lengthSquared() > 0.0D) {
                    living.addVelocity(vec3d.x, 0.1D, vec3d.z);
                }
            }
            entity.attackEntityFrom(BasalzDamageSource.causeDamage(this, func_234616_v_()), baseDamage);
        }
        if (Utils.isServerWorld(world)) {
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

    @Override
    public float getBrightness() {

        return 1.0F;
    }

    // region DAMAGE SOURCE
    protected static class BasalzDamageSource extends EntityDamageSource {

        public BasalzDamageSource(Entity source) {

            super("basalz", source);
        }

        public static DamageSource causeDamage(BasalzProjectileEntity entityProj, Entity entitySource) {

            return (new IndirectEntityDamageSource("basalz", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
        }

    }
    // endregion
}
