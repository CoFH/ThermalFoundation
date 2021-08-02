package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.init.TCoreReferences;
import cofh.thermal.core.tileentity.device.DeviceSoilInfuserTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DeviceSoilInfuserContainer extends TileContainer {

    public final DeviceSoilInfuserTile tile;

    public DeviceSoilInfuserContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(TCoreReferences.DEVICE_SOIL_INFUSER_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (DeviceSoilInfuserTile) world.getTileEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotCoFH(tileInv, 0, 8, 53));

        bindAugmentSlots(tileInv, 1, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
