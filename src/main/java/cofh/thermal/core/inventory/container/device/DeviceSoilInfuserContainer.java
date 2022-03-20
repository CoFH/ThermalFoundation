package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.init.TCoreReferences;
import cofh.thermal.core.tileentity.device.DeviceSoilInfuserTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DeviceSoilInfuserContainer extends TileContainer {

    public final DeviceSoilInfuserTile tile;

    public DeviceSoilInfuserContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(TCoreReferences.DEVICE_SOIL_INFUSER_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (DeviceSoilInfuserTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotCoFH(tileInv, 0, 8, 53));

        bindAugmentSlots(tileInv, 1, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
