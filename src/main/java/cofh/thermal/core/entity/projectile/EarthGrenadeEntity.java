package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

import static cofh.lib.util.Utils.destroyBlock;
import static cofh.lib.util.references.CoreReferences.SUNDERED;
import static cofh.thermal.core.init.TCoreReferences.EARTH_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.EARTH_GRENADE_ITEM;

public class EarthGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectAmplifier = 1;
    public static int effectDuration = 200;

    public EarthGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public EarthGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(EARTH_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public EarthGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(EARTH_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return EARTH_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            sunderNearbyEntities(this, level, this.blockPosition(), radius);
            breakBlocks(this, level, this.blockPosition(), radius - 1, getOwner());
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void breakBlocks(Entity entity, Level worldIn, BlockPos pos, int radius, @Nullable Entity entityIn) {

        if (radius <= 0) {
            return;
        }
        float f = (float) Math.min(AreaUtils.HORZ_MAX, radius);
        float f2 = f * f;

        for (BlockPos iterPos : BlockPos.betweenClosed(pos.offset(-f, -f, -f), pos.offset(f, f, f))) {
            double distance = iterPos.distToCenterSqr(entity.position());
            if (distance < f2) {
                BlockState state = worldIn.getBlockState(iterPos);
                Material material = state.getMaterial();
                if (material == Material.STONE || material == Material.DIRT || state.getBlock() instanceof SnowyDirtBlock) {
                    destroyBlock(worldIn, iterPos, true, entityIn);
                }
            }
        }
    }

    public static void sunderNearbyEntities(Entity entity, Level worldIn, BlockPos pos, int radius) {

        AABB area = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        worldIn.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE)
                .forEach(livingEntity -> livingEntity.addEffect(new MobEffectInstance(SUNDERED, effectDuration, effectAmplifier, false, false)));
    }

}
