package cofh.thermal.core.entity.explosive;

import cofh.lib.entity.AbstractTNTMinecartEntity;
import cofh.thermal.core.ThermalCore;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import static cofh.thermal.core.ThermalCore.BLOCKS;

public class TNTMinecartEntityCoFH extends AbstractTNTMinecartEntity {

    protected String id;
    protected String blockId;
    protected IDetonateAction detonateAction;

    public TNTMinecartEntityCoFH(EntityType<?> type, World worldIn, String id, String blockId, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        this.id = id;
        this.blockId = blockId;
    }

    public TNTMinecartEntityCoFH(EntityType<?> type, World worldIn, String id, String blockId, IDetonateAction detonateAction, double posX, double posY, double posZ) {

        super(type, worldIn, posX, posY, posZ);
        this.detonateAction = detonateAction;
        this.id = id;
        this.blockId = blockId;
    }

    @Override
    public Block getBlock() {

        return BLOCKS.get(blockId);
    }

    @Override
    public ItemStack getCartItem() {

        return detonated ? new ItemStack(Items.MINECART) : new ItemStack(ThermalCore.ITEMS.get(id));
    }

    @Override
    public void detonate(Vector3d pos) {

        detonateAction.detonate(level, this, null, pos, radius, effectDuration, effectAmplifier);
    }
}
