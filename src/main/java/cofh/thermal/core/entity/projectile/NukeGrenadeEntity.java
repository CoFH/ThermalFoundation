package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

import static cofh.thermal.core.init.TCoreReferences.NUKE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.NUKE_GRENADE_ITEM;
import static net.minecraft.world.effect.MobEffects.WITHER;

public class NukeGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectAmplifier = 2;
    public static int effectDuration = 400;

    public static double explosionStrength = 12.0;
    public static boolean explosionsDestroyBlocks = true;

    public NukeGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public NukeGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(NUKE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public NukeGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(NUKE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return NUKE_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            level.setBlockAndUpdate(this.blockPosition(), Blocks.AIR.defaultBlockState());
            affectNearbyEntities(this, level, this.blockPosition(), radius * 3, getOwner());
            destroyBlocks(this, level, this.blockPosition(), radius * 2);
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float) explosionStrength, true, explosionsDestroyBlocks ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.FLASH, this.getX(), getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void destroyBlocks(Entity entity, Level worldIn, BlockPos pos, int radius) {

        if (radius <= 0) {
            return;
        }
        float f = (float) Math.min(AreaUtils.HORZ_MAX, radius);
        float maxResistance = 400F * radius * radius;
        float f2 = f * f;

        for (BlockPos iterPos : BlockPos.betweenClosed(pos.offset(-f, -f / 2, -f), pos.offset(f, f, f))) {
            double distance = iterPos.distToCenterSqr(entity.position());
            if (distance < f2) {
                BlockState state = worldIn.getBlockState(iterPos);
                if (!state.isAir() && state.getBlock().getExplosionResistance(state, worldIn, iterPos, null) < maxResistance - (maxResistance * distance / f2)) {
                    worldIn.setBlockAndUpdate(iterPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    public static void affectNearbyEntities(Entity entity, Level worldIn, BlockPos pos, int radius, @Nullable Entity source) {

        AABB area = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        double f2 = radius * radius;
        worldIn.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE)
                .forEach(livingEntity -> {
                    double distance = pos.distSqr(livingEntity.blockPosition());
                    if (distance < f2) {
                        float damage = (float) MathHelper.clamp(f2 - distance, radius, f2);
                        livingEntity.hurt(DamageSource.explosion(source instanceof LivingEntity ? (LivingEntity) source : null), damage);
                        livingEntity.addEffect(new MobEffectInstance(WITHER, effectDuration, effectAmplifier, false, false));
                    }
                });
    }

}

