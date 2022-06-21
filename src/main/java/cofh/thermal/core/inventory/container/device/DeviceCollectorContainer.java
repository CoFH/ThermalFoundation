package cofh.thermal.core.inventory.container.device;

import cofh.core.content.inventory.container.TileContainer;
import cofh.lib.content.inventory.container.slot.SlotRemoveOnly;
import cofh.lib.content.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreReferences.DEVICE_COLLECTOR_CONTAINER;

public class DeviceCollectorContainer extends TileContainer {

    public final ThermalTileAugmentable tile;

    public DeviceCollectorContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(DEVICE_COLLECTOR_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ThermalTileAugmentable) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 5; ++j) {
                addSlot(new SlotRemoveOnly(tileInv, i * 5 + j, 44 + j * 18, 17 + i * 18));
            }
        }
        bindAugmentSlots(tileInv, 15, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
