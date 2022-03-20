package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import cofh.thermal.core.item.PhytoGroItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreReferences.PHYTO_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_PHYTO_TNT;

public class PhytoTNTEntity extends AbstractTNTEntity {

    public PhytoTNTEntity(EntityType<? extends PhytoTNTEntity> type, Level worldIn) {

        super(type, worldIn);
    }

    public PhytoTNTEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(PHYTO_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_PHYTO_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(level)) {
            AreaUtils.growPlants(this, level, this.blockPosition(), radius);
            for (int i = 0; i < 4; ++i) {
                PhytoGroItem.growSeagrass(level, this.blockPosition(), null);
            }
            makeAreaOfEffectCloud();
            this.discard();
        }
        this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 2.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloud cloud = new AreaEffectCloud(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.HAPPY_VILLAGER);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

}
