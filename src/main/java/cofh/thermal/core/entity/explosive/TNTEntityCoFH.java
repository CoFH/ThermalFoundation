package cofh.thermal.core.entity.explosive;

import cofh.lib.entity.AbstractTNTEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static cofh.thermal.core.ThermalCore.BLOCKS;


public class TNTEntityCoFH extends AbstractTNTEntity {

    protected String id;
    protected IDetonateAction detonateAction;

    public TNTEntityCoFH(EntityType<? extends AbstractTNTEntity> type, World worldIn, String id, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        this.id = id;
    }

    public TNTEntityCoFH(EntityType<? extends AbstractTNTEntity> type, World worldIn, String id, IDetonateAction detonateAction, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(type, worldIn, x, y, z, igniter);
        this.detonateAction = detonateAction;
        this.id = id;
    }

    @Override
    public void detonate(Vector3d pos) {

        detonateAction.detonate(level, this, getOwner(), this.position(), radius, effectDuration, effectAmplifier);
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(id);
    }

}
