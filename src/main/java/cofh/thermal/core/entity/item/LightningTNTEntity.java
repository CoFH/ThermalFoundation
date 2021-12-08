package cofh.thermal.core.entity.item;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.LightningGrenadeEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreReferences.LIGHTNING_TNT_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_LIGHTNING_TNT;

public class LightningTNTEntity extends AbstractTNTEntity {

    public LightningTNTEntity(EntityType<? extends LightningTNTEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public LightningTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(LIGHTNING_TNT_ENTITY, worldIn, x, y, z, igniter);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(ID_LIGHTNING_TNT);
    }

    @Override
    protected void explode() {

        if (Utils.isServerWorld(level)) {
            BlockPos pos = this.blockPosition();
            if (this.level.canSeeSky(pos)) {
                Utils.spawnLightningBolt(level, pos, owner);
            }
            LightningGrenadeEntity.shockNearbyEntities(this, level, this.blockPosition(), radius);
            AreaUtils.zapNearbyGround(this, level, this.blockPosition(), radius, 0.05, 12);
            this.remove();
        }
        this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

}
