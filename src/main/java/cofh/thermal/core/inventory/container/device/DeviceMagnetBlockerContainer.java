package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.block.entity.device.DeviceMagnetBlockerTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreContainers.DEVICE_MAGNET_BLOCKER_CONTAINER;

public class DeviceMagnetBlockerContainer extends TileContainer {

    public final DeviceMagnetBlockerTile tile;

    public DeviceMagnetBlockerContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(DEVICE_MAGNET_BLOCKER_CONTAINER.get(), windowId, world, pos, inventory, player);
        this.tile = (DeviceMagnetBlockerTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        bindAugmentSlots(tileInv, 0, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
