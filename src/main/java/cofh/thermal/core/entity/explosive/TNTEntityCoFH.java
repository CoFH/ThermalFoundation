package cofh.thermal.core.entity.explosive;

import cofh.lib.entity.AbstractTNTEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static cofh.thermal.core.ThermalCore.BLOCKS;


public class TNTEntityCoFH extends AbstractTNTEntity {

    public static Map<String, RegistryObject<EntityType<? extends AbstractTNTEntity>>> TNT = new HashMap<>();
    protected Block block;
    protected IDetonateAction detonateAction;

    public TNTEntityCoFH(EntityType<? extends AbstractTNTEntity> type, World worldIn, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        block = BLOCKS.get(type.getRegistryName());
    }

    public TNTEntityCoFH(EntityType<? extends AbstractTNTEntity> type, World worldIn, IDetonateAction detonateAction, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(type, worldIn, x, y, z, igniter);
        this.detonateAction = detonateAction;
        block = BLOCKS.get(type.getRegistryName());
    }

    @Override
    public void detonate(Vector3d pos) {

        detonateAction.detonate(level, this, getOwner(), this.position(), radius, effectDuration, effectAmplifier);
    }

    @Override
    public Block getBlock() {

        return block;
    }

}
