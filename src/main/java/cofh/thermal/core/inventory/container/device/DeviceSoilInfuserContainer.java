package cofh.thermal.core.inventory.container.device;

import cofh.core.content.inventory.container.TileContainer;
import cofh.lib.content.inventory.container.slot.SlotCoFH;
import cofh.lib.content.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.block.entity.device.DeviceSoilInfuserTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreContainers.DEVICE_SOIL_INFUSER_CONTAINER;

public class DeviceSoilInfuserContainer extends TileContainer {

    public final DeviceSoilInfuserTile tile;

    public DeviceSoilInfuserContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(DEVICE_SOIL_INFUSER_CONTAINER.get(), windowId, world, pos, inventory, player);
        this.tile = (DeviceSoilInfuserTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotCoFH(tileInv, 0, 8, 53));

        bindAugmentSlots(tileInv, 1, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
