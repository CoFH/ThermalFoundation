package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.REDSTONE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.REDSTONE_GRENADE_ITEM;

public class RedstoneGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectDuration = 300;

    public RedstoneGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public RedstoneGrenadeEntity(World worldIn, double x, double y, double z) {

        super(REDSTONE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public RedstoneGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(REDSTONE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return REDSTONE_GRENADE_ITEM;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (Utils.isServerWorld(level)) {
            if (!this.isInWater()) {
                AreaUtils.transformSignalAir(this, level, this.blockPosition(), radius);
                makeAreaOfEffectCloud();
            }
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(RedstoneParticleData.REDSTONE);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

}
