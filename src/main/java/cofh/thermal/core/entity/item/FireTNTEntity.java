package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.FireGrenadeEntity;
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
import static cofh.thermal.core.init.TCoreIDs.ID_FIRE_TNT;
import static cofh.thermal.core.init.TCoreReferences.FIRE_TNT_ENTITY;

public class FireTNTEntity extends AbstractTNTEntity {

    public FireTNTEntity(EntityType<? extends FireTNTEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public FireTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(FIRE_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_FIRE_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(world)) {
            if (!this.isInWater()) {
                AreaUtils.igniteNearbyEntities(this, world, this.getPosition(), radius, FireGrenadeEntity.effectDuration);
                AreaUtils.igniteSpecial(this, world, this.getPosition(), radius, true, true, tntPlacedBy);
                AreaUtils.igniteNearbyGround(this, world, this.getPosition(), radius, 0.2);
                makeAreaOfEffectCloud();
            }
            this.remove();
        }
        this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getPosX(), getPosY() + 0.5D, getPosZ());
        cloud.setRadius(1);
        cloud.setParticleData(ParticleTypes.FLAME);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

}
