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
import static cofh.thermal.core.init.TCoreIDs.ID_NUKE_TNT;
import static cofh.thermal.core.init.TCoreReferences.NUKE_TNT_ENTITY;

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

        if (Utils.isServerWorld(world)) {
            world.setBlockState(this.getPosition(), Blocks.AIR.getDefaultState());
            NukeGrenadeEntity.affectNearbyEntities(this, world, this.getPosition(), radius * 2, tntPlacedBy);
            NukeGrenadeEntity.destroyBlocks(this, world, this.getPosition(), radius + radius / 2);
            world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float) NukeGrenadeEntity.explosionStrength * 2, !this.isInWater(), NukeGrenadeEntity.explosionsBreakBlocks ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
            this.remove();
        }
        this.world.addParticle(ParticleTypes.FLASH, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

}
