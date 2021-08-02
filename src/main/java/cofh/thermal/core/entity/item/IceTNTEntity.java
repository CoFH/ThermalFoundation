package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.IceGrenadeEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreIDs.ID_ICE_TNT;
import static cofh.thermal.core.init.TCoreReferences.ICE_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalConfig.permanentLava;
import static cofh.thermal.lib.common.ThermalConfig.permanentWater;

public class IceTNTEntity extends AbstractTNTEntity {

    public IceTNTEntity(EntityType<? extends IceTNTEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public IceTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(ICE_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_ICE_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(world)) {
            IceGrenadeEntity.affectNearbyEntities(this, world, this.getPosition(), radius, tntPlacedBy);
            AreaUtils.freezeSpecial(this, world, this.getPosition(), radius, true, true);
            AreaUtils.freezeNearbyGround(this, world, this.getPosition(), radius);
            AreaUtils.freezeAllWater(this, world, this.getPosition(), radius, permanentWater);
            AreaUtils.freezeAllLava(this, world, this.getPosition(), radius, permanentLava);
            makeAreaOfEffectCloud();
            this.remove();
        }
        this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getPosX(), getPosY() + 0.5D, getPosZ());
        cloud.setRadius(1);
        cloud.setParticleData(ParticleTypes.ITEM_SNOWBALL);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

}
