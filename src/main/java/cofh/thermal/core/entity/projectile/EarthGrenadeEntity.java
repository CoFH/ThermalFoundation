package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static cofh.lib.util.Utils.destroyBlock;
import static cofh.lib.util.references.CoreReferences.SUNDERED;
import static cofh.thermal.core.init.TCoreReferences.EARTH_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.EARTH_GRENADE_ITEM;

public class EarthGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectAmplifier = 1;
    public static int effectDuration = 200;

    public EarthGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public EarthGrenadeEntity(World worldIn, double x, double y, double z) {

        super(EARTH_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public EarthGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(EARTH_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return EARTH_GRENADE_ITEM;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (Utils.isServerWorld(level)) {
            sunderNearbyEntities(this, level, this.blockPosition(), radius);
            breakBlocks(this, level, this.blockPosition(), radius - 1, getOwner());
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void breakBlocks(Entity entity, World worldIn, BlockPos pos, int radius, @Nullable Entity entityIn) {

        if (radius <= 0) {
            return;
        }
        float f = (float) Math.min(AreaUtils.HORZ_MAX, radius);
        float f2 = f * f;

        for (BlockPos iterPos : BlockPos.betweenClosed(pos.offset(-f, -f, -f), pos.offset(f, f, f))) {
            double distance = iterPos.distSqr(entity.position(), true);
            if (distance < f2) {
                BlockState state = worldIn.getBlockState(iterPos);
                Material material = state.getMaterial();
                if (material == Material.STONE || material == Material.DIRT || state.getBlock() instanceof SnowyDirtBlock) {
                    destroyBlock(worldIn, iterPos, true, entityIn);
                }
            }
        }
    }

    public static void sunderNearbyEntities(Entity entity, World worldIn, BlockPos pos, int radius) {

        AxisAlignedBB area = new AxisAlignedBB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        worldIn.getEntitiesOfClass(LivingEntity.class, area, EntityPredicates.ENTITY_STILL_ALIVE)
                .forEach(livingEntity -> livingEntity.addEffect(new EffectInstance(SUNDERED, effectDuration, effectAmplifier, false, false)));
    }

}
