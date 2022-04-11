package cofh.thermal.core.entity.explosive;

import cofh.lib.entity.AbstractTNTEntity;
import cofh.lib.entity.AbstractTNTMinecartEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;

import static cofh.thermal.core.ThermalCore.ITEMS;

public class TNTMinecartEntityCoFH extends AbstractTNTMinecartEntity {

    public static Map<String, RegistryObject<EntityType<? extends AbstractTNTEntity>>> TNT = new HashMap<>();
    protected Item item;
    protected Block block;
    protected IDetonateAction detonateAction;

    public TNTMinecartEntityCoFH(EntityType<?> type, World worldIn, IDetonateAction detonateAction, Block block) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        this.block = block;
        item = ITEMS.get(type.getRegistryName());
    }

    public TNTMinecartEntityCoFH(EntityType<?> type, World worldIn, IDetonateAction detonateAction, Block block, double posX, double posY, double posZ) {

        super(type, worldIn, posX, posY, posZ);
        this.detonateAction = detonateAction;
        this.block = block;
        item = ITEMS.get(type.getRegistryName());
    }

    @Override
    public Block getBlock() {

        return block;
    }

    @Override
    public ItemStack getCartItem() {

        return detonated ? new ItemStack(Items.MINECART) : new ItemStack(item);
    }

    @Override
    public void detonate(Vector3d pos) {

        detonateAction.detonate(level, this, null, pos, radius, effectDuration, effectAmplifier);
    }

}
