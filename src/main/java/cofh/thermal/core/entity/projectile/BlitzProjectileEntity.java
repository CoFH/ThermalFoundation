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
import net.minecraft.world.World;

import static cofh.lib.util.references.CoreReferences.SHOCKED;
import static cofh.thermal.core.init.TCoreReferences.BLITZ_PROJECTILE_ENTITY;

public class BlitzProjectileEntity extends ElementalProjectileEntity {

    public static float defaultDamage = 7.0F;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;

    public BlitzProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {

        super(type, world);
    }

    public BlitzProjectileEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {

        super(BLITZ_PROJECTILE_ENTITY, shooter, accelX, accelY, accelZ, world);
    }

    public BlitzProjectileEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {

        super(BLITZ_PROJECTILE_ENTITY, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    protected IParticleData getTrailParticle() {

        return ParticleTypes.INSTANT_EFFECT;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity.hurt(BlitzDamageSource.causeDamage(this, getOwner()), entity.isInWaterOrRain() ? defaultDamage + 3.0F : defaultDamage) && !entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addEffect(new EffectInstance(SHOCKED, effectDuration, effectAmplifier, false, false));
            }
        }
        if (Utils.isServerWorld(level)) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected float getInertia() {

        return 1.0F;
    }

    // region DAMAGE SOURCE
    protected static class BlitzDamageSource extends EntityDamageSource {

        public BlitzDamageSource(Entity source) {

            super("blitz", source);
        }

        public static DamageSource causeDamage(BlitzProjectileEntity entityProj, Entity entitySource) {

            return (new IndirectEntityDamageSource("blitz", entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
        }

    }
    // endregion
}
