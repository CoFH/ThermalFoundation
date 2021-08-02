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
    protected void onImpact(RayTraceResult result) {

        if (Utils.isServerWorld(world)) {
            sunderNearbyEntities(this, world, this.getPosition(), radius);
            breakBlocks(this, world, this.getPosition(), radius - 1, func_234616_v_());
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.ticksExisted < 10) {
            return;
        }
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void breakBlocks(Entity entity, World worldIn, BlockPos pos, int radius, @Nullable Entity entityIn) {

        if (radius <= 0) {
            return;
        }
        float f = (float) Math.min(AreaUtils.HORZ_MAX, radius);
        float f2 = f * f;

        for (BlockPos iterPos : BlockPos.getAllInBoxMutable(pos.add(-f, -f, -f), pos.add(f, f, f))) {
            double distance = iterPos.distanceSq(entity.getPositionVec(), true);
            if (distance < f2) {
                BlockState state = worldIn.getBlockState(iterPos);
                Material material = state.getMaterial();
                if (material == Material.ROCK || material == Material.EARTH || state.getBlock() instanceof SnowyDirtBlock) {
                    destroyBlock(worldIn, iterPos, true, entityIn);
                }
            }
        }
    }

    public static void sunderNearbyEntities(Entity entity, World worldIn, BlockPos pos, int radius) {

        AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
        worldIn.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE)
                .forEach(livingEntity -> livingEntity.addPotionEffect(new EffectInstance(SUNDERED, effectDuration, effectAmplifier, false, false)));
    }

}
