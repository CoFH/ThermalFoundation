package cofh.thermal.core.inventory.container.device;

import cofh.core.content.inventory.container.TileContainer;
import cofh.lib.content.inventory.container.slot.SlotCoFH;
import cofh.lib.content.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreContainers.DEVICE_TREE_EXTRACTOR_CONTAINER;

public class DeviceTreeExtractorContainer extends TileContainer {

    public final ThermalTileAugmentable tile;

    public DeviceTreeExtractorContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(DEVICE_TREE_EXTRACTOR_CONTAINER.get(), windowId, world, pos, inventory, player);
        this.tile = (ThermalTileAugmentable) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotCoFH(tileInv, 0, 44, 35));

        bindAugmentSlots(tileInv, 1, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
