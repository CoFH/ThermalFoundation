package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.SlimeGrenadeEntity;
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
import static cofh.thermal.core.init.TCoreReferences.SLIME_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_SLIME_TNT;

public class SlimeTNTEntity extends AbstractTNTEntity {

    public SlimeTNTEntity(EntityType<? extends SlimeTNTEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public SlimeTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(SLIME_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_SLIME_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(world)) {
            SlimeGrenadeEntity.affectNearbyEntities(this, world, this.getPosition(), radius, tntPlacedBy);
            makeAreaOfEffectCloud();
            this.remove();
        }
        this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getPosX(), getPosY() + 0.5D, getPosZ());
        cloud.setRadius(1);
        cloud.setParticleData(ParticleTypes.ITEM_SLIME);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

}
