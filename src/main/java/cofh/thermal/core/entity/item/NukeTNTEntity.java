package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.NukeGrenadeEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreReferences.NUKE_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_NUKE_TNT;

public class NukeTNTEntity extends AbstractTNTEntity {

    public NukeTNTEntity(EntityType<? extends NukeTNTEntity> type, Level worldIn) {

        super(type, worldIn);
    }

    public NukeTNTEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(NUKE_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_NUKE_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(level)) {
            level.setBlockAndUpdate(this.blockPosition(), Blocks.AIR.defaultBlockState());
            NukeGrenadeEntity.affectNearbyEntities(this, level, this.blockPosition(), radius * 3, owner);
            NukeGrenadeEntity.destroyBlocks(this, level, this.blockPosition(), radius * 2);
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float) NukeGrenadeEntity.explosionStrength * 2, true, NukeGrenadeEntity.explosionsDestroyBlocks ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            this.discard();
        }
        this.level.addParticle(ParticleTypes.FLASH, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 2.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

}
