package cofh.thermal.core.entity.explosive;

import cofh.lib.content.entity.PrimedTntCoFH;
import cofh.lib.util.Utils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static cofh.thermal.core.ThermalCore.BLOCKS;

public class ThermalTNTEntity extends PrimedTntCoFH {

    public static Map<String, RegistryObject<EntityType<? extends PrimedTntCoFH>>> TNT = new HashMap<>();
    protected Block block;
    protected IDetonateAction detonateAction;

    public ThermalTNTEntity(EntityType<? extends PrimedTntCoFH> type, Level worldIn, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        block = BLOCKS.get(Utils.getRegistryName(type));
    }

    public ThermalTNTEntity(EntityType<? extends PrimedTntCoFH> type, Level worldIn, IDetonateAction detonateAction, double x, double y, double z, @Nullable LivingEntity igniter) {

        super(type, worldIn, x, y, z, igniter);
        this.detonateAction = detonateAction;
        block = BLOCKS.get(Utils.getRegistryName(type));
    }

    @Override
    public void detonate(Vec3 pos) {

        detonateAction.detonate(level, this, getOwner(), this.position(), radius, effectDuration, effectAmplifier);
    }

    @Override
    public Block getBlock() {

        return block;
    }

}
