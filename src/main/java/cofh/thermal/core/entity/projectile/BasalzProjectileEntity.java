package cofh.thermal.core.entity.projectile;

import cofh.lib.util.Utils;
import cofh.thermal.core.entity.monster.BlitzEntity;
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
import static cofh.thermal.lib.common.ThermalIDs.ID_BASALZ;

public class BasalzProjectileEntity extends ElementalProjectileEntity {

    public static float defaultDamage = 6.0F;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;
    public static float knockbackStrength = 1.2F;

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
            if (entity.hurt(BasalzDamageSource.causeDamage(this, getOwner()), getDamage(entity)) && !entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addEffect(new EffectInstance(SUNDERED, getEffectDuration(entity), getEffectAmplifier(entity), false, false));
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

    // region HELPERS
    @Override
    public float getDamage(Entity target) {

        return target instanceof BlitzEntity ? defaultDamage + 3.0F : defaultDamage;
    }

    @Override
    public int getEffectAmplifier(Entity target) {

        return effectAmplifier;
    }

    @Override
    public int getEffectDuration(Entity target) {

        return effectDuration;
    }
    // endregion

    // region DAMAGE SOURCE
    protected static class BasalzDamageSource extends EntityDamageSource {

        public BasalzDamageSource(Entity source) {

            super(ID_BASALZ, source);
        }

        public static DamageSource causeDamage(BasalzProjectileEntity entityProj, Entity entitySource) {

            return (new IndirectEntityDamageSource(ID_BASALZ, entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
        }

    }
    // endregion
}
