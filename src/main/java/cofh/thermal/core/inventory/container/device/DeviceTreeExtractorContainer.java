package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.lib.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.DEVICE_TREE_EXTRACTOR_CONTAINER;

public class DeviceTreeExtractorContainer extends TileContainer {

    public final ThermalTileBase tile;

    public DeviceTreeExtractorContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(DEVICE_TREE_EXTRACTOR_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ThermalTileBase) world.getTileEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotCoFH(tileInv, 0, 44, 35));

        bindAugmentSlots(tileInv, 1, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
