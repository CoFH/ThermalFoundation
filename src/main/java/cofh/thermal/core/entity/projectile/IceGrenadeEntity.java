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

import static cofh.lib.util.references.CoreReferences.CHILLED;
import static cofh.thermal.core.init.TCoreReferences.ICE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.ICE_GRENADE_ITEM;
import static cofh.thermal.lib.common.ThermalConfig.permanentLava;
import static cofh.thermal.lib.common.ThermalConfig.permanentWater;

public class IceGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectAmplifier = 1;
    public static int effectDuration = 300;

    public IceGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public IceGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(ICE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public IceGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(ICE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return ICE_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            affectNearbyEntities(this, level, this.blockPosition(), radius, getOwner());
            AreaUtils.freezeSpecial(this, level, this.blockPosition(), radius, true, true);
            AreaUtils.freezeNearbyGround(this, level, this.blockPosition(), radius);
            AreaUtils.freezeAllWater(this, level, this.blockPosition(), radius, permanentWater);
            AreaUtils.freezeAllLava(this, level, this.blockPosition(), radius, permanentLava);
            makeAreaOfEffectCloud();
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
        cloud.setParticle(ParticleTypes.ITEM_SNOWBALL);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    public static void affectNearbyEntities(Entity entity, Level worldIn, BlockPos pos, int radius, @Nullable Entity source) {

        AABB area = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> mobs = worldIn.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE);

        for (LivingEntity mob : mobs) {
            mob.hurt(DamageSource.explosion(source instanceof LivingEntity ? (LivingEntity) source : null), mob.fireImmune() ? 4.0F : 1.0F);
            mob.addEffect(new MobEffectInstance(CHILLED, effectDuration, effectAmplifier, false, false));
        }
    }

}
