package cofh.thermal.core.inventory.container.device;

import cofh.core.content.inventory.container.TileContainer;
import cofh.lib.content.inventory.container.slot.SlotCoFH;
import cofh.lib.content.inventory.container.slot.SlotRemoveOnly;
import cofh.lib.content.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreReferences.DEVICE_COMPOSTER_CONTAINER;

public class DeviceComposterContainer extends TileContainer {

    public final ThermalTileAugmentable tile;

    public DeviceComposterContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(DEVICE_COMPOSTER_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ThermalTileAugmentable) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlot(new SlotCoFH(tileInv, i * 3 + j, 26 + j * 18, 17 + i * 18));
            }
        }
        addSlot(new SlotRemoveOnly(tileInv, 9, 125, 35));

        bindAugmentSlots(tileInv, 10, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
