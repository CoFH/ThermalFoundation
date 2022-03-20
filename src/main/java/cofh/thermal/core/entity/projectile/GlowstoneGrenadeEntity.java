package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.thermal.core.init.TCoreReferences.GLOWSTONE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.GLOWSTONE_GRENADE_ITEM;
import static net.minecraft.world.effect.MobEffects.GLOWING;

public class GlowstoneGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectDuration = 15; // In seconds

    public GlowstoneGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public GlowstoneGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(GLOWSTONE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public GlowstoneGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(GLOWSTONE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return GLOWSTONE_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            if (!this.isInWater()) {
                affectNearbyEntities(this, level, this.blockPosition(), radius, getOwner());
                AreaUtils.transformGlowAir(this, level, this.blockPosition(), radius);
                makeAreaOfEffectCloud();
            }
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloud cloud = new AreaEffectCloud(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.INSTANT_EFFECT);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    public static void affectNearbyEntities(Entity entity, Level worldIn, BlockPos pos, int radius, @Nullable Entity source) {

        AABB area = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> mobs = worldIn.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE);
        for (LivingEntity mob : mobs) {
            mob.addEffect(new MobEffectInstance(GLOWING, effectDuration * 20, 0, false, false));
            if (mob.getMobType() == MobType.UNDEAD) {
                mob.hurt(DamageSource.explosion(source instanceof LivingEntity ? (LivingEntity) source : null), 4.0F);
                mob.setSecondsOnFire(effectDuration);
            }
        }
    }

}
