package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.EarthGrenadeEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreReferences.EARTH_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_EARTH_TNT;

public class EarthTNTEntity extends AbstractTNTEntity {

    public EarthTNTEntity(EntityType<? extends EarthTNTEntity> type, Level worldIn) {

        super(type, worldIn);
    }

    public EarthTNTEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(EARTH_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_EARTH_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(level)) {
            EarthGrenadeEntity.sunderNearbyEntities(this, level, this.blockPosition(), radius);
            EarthGrenadeEntity.breakBlocks(this, level, this.blockPosition(), radius - 1, owner);
            this.discard();
        }
        this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 2.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

}
