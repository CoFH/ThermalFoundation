package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.container.slot.SlotRemoveOnly;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.lib.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.DEVICE_COLLECTOR_CONTAINER;

public class DeviceCollectorContainer extends TileContainer {

    public final ThermalTileBase tile;

    public DeviceCollectorContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(DEVICE_COLLECTOR_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ThermalTileBase) world.getTileEntity(pos);
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
