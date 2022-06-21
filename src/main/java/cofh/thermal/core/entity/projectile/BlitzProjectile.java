package cofh.thermal.core.entity.projectile;

import cofh.core.content.entity.ElectricArc;
import cofh.lib.util.Utils;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.entity.monster.Basalz;
import net.minecraft.core.BlockPos;
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

import static cofh.core.init.CoreMobEffects.SHOCKED;
import static cofh.thermal.core.init.TCoreReferences.BLITZ_PROJECTILE_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_BLITZ;

public class BlitzProjectile extends ElementalProjectile {

    public static float defaultDamage = 5.0F;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;

    public BlitzProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level world) {

        super(type, world);
    }

    public BlitzProjectile(LivingEntity shooter, double accelX, double accelY, double accelZ, Level world) {

        super(BLITZ_PROJECTILE_ENTITY, shooter, accelX, accelY, accelZ, world);
    }

    public BlitzProjectile(double x, double y, double z, double accelX, double accelY, double accelZ, Level world) {

        super(BLITZ_PROJECTILE_ENTITY, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    protected ParticleOptions getTrailParticle() {

        return ParticleTypes.INSTANT_EFFECT;
    }

    @Override
    protected void onHit(HitResult result) {

        Entity owner = getOwner();
        level.addFreshEntity((new ElectricArc(level, result.getLocation())).setCosmetic(true).setOwner(owner instanceof LivingEntity ? (LivingEntity) owner : null));
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity.hurt(BlitzDamageSource.causeDamage(this, owner), getDamage(entity)) && !entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addEffect(new MobEffectInstance(SHOCKED.get(), getEffectDuration(entity), getEffectAmplifier(entity), false, false));
            }
        }
        if (ThermalCoreConfig.mobBlitzLightning && Utils.isServerWorld(level)) {
            BlockPos pos = new BlockPos(result.getLocation());
            if (level.canSeeSky(pos) && random.nextFloat() < (level.isRainingAt(pos) ? (level.isThundering() ? 0.2F : 0.1F) : 0.04F)) {
                Utils.spawnLightningBolt(level, pos);
            }
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected float getInertia() {

        return 1.0F;
    }

    // region HELPERS
    @Override
    public float getDamage(Entity target) {

        return target.isInWaterOrRain() || target instanceof Basalz ? defaultDamage + 3.0F : defaultDamage;
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
    protected static class BlitzDamageSource extends EntityDamageSource {

        public BlitzDamageSource(Entity source) {

            super(ID_BLITZ, source);
        }

        public static DamageSource causeDamage(BlitzProjectile entityProj, Entity entitySource) {

            return (new IndirectEntityDamageSource(ID_BLITZ, entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
        }

    }
    // endregion
}
