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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.FIRE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.FIRE_GRENADE_ITEM;

public class FireGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectDuration = 15; // In seconds

    public FireGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public FireGrenadeEntity(World worldIn, double x, double y, double z) {

        super(FIRE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public FireGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(FIRE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return FIRE_GRENADE_ITEM;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (Utils.isServerWorld(world)) {
            if (!this.isInWater()) {
                AreaUtils.igniteNearbyEntities(this, world, this.getPosition(), radius, effectDuration);
                AreaUtils.igniteSpecial(this, world, this.getPosition(), radius, true, true, func_234616_v_());
                AreaUtils.igniteNearbyGround(this, world, this.getPosition(), radius, 0.2);
                makeAreaOfEffectCloud();
            }
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.ticksExisted < 10) {
            return;
        }
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getPosX(), getPosY(), getPosZ());
        cloud.setRadius(1);
        cloud.setParticleData(ParticleTypes.FLAME);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

}
