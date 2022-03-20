package cofh.thermal.core.entity.projectile;

import cofh.lib.util.Utils;
import cofh.thermal.core.entity.monster.BlitzEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static cofh.lib.util.references.CoreReferences.SUNDERED;
import static cofh.thermal.core.init.TCoreReferences.BASALZ_PROJECTILE_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_BASALZ;

public class BasalzProjectileEntity extends ElementalProjectileEntity {

    public static float defaultDamage = 6.0F;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;
    public static float knockbackStrength = 1.2F;

    public BasalzProjectileEntity(EntityType<? extends AbstractHurtingProjectile> type, Level world) {

        super(type, world);
    }

    public BasalzProjectileEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, Level world) {

        super(BASALZ_PROJECTILE_ENTITY, shooter, accelX, accelY, accelZ, world);
    }

    public BasalzProjectileEntity(double x, double y, double z, double accelX, double accelY, double accelZ, Level world) {

        super(BASALZ_PROJECTILE_ENTITY, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    protected ParticleOptions getTrailParticle() {

        return ParticleTypes.FALLING_LAVA;
    }

    @Override
    public void onHit(HitResult result) {

        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity.hurt(BasalzDamageSource.causeDamage(this, getOwner()), getDamage(entity)) && !entity.isInvulnerable() && entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(SUNDERED, getEffectDuration(entity), getEffectAmplifier(entity), false, false));
                Vec3 velocity = this.getDeltaMovement();
                if (velocity.lengthSqr() > 0.01) {
                    living.knockback(knockbackStrength, -velocity.x, -velocity.z);
                }
            }
        }
        if (Utils.isServerWorld(level)) {
            this.discard();
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
