package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.NukeGrenadeEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreReferences.NUKE_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_NUKE_TNT;

public class NukeTNTEntity extends AbstractTNTEntity {

    public NukeTNTEntity(EntityType<? extends NukeTNTEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public NukeTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

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
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float) NukeGrenadeEntity.explosionStrength * 2, true, NukeGrenadeEntity.explosionsDestroyBlocks ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            this.remove();
        }
        this.level.addParticle(ParticleTypes.FLASH, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

}
