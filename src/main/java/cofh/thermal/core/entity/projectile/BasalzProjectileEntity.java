package cofh.thermal.core.entity.projectile;

import cofh.lib.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
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

import static cofh.lib.util.references.CoreReferences.SUNDERED;
import static cofh.thermal.core.init.TCoreReferences.BASALZ_PROJECTILE_ENTITY;

public class BasalzProjectileEntity extends ElementalProjectileEntity {

    public static float defaultDamage = 7.0F;
    public static float knockbackStrength = 1.2F;
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
    protected IParticleData getTrailParticle() {

        return ParticleTypes.FALLING_LAVA;
    }

    @Override
    public void onHit(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity.hurt(BasalzDamageSource.causeDamage(this, getOwner()), defaultDamage) && !entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addEffect(new EffectInstance(SUNDERED, effectDuration, effectAmplifier, false, false));
                Vector3d velocity = this.getDeltaMovement();
                if (velocity.lengthSqr() > 0.01) {
                    living.knockback(knockbackStrength, -velocity.x, -velocity.z);
                }
            }
        }
        if (Utils.isServerWorld(level)) {
            this.remove();
        }
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
