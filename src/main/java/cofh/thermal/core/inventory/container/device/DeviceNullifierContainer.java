package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.core.network.packet.server.ContainerPacket;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.tileentity.device.DeviceNullifierTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.DEVICE_NULLIFIER_CONTAINER;

public class DeviceNullifierContainer extends TileContainer {

    public final DeviceNullifierTile tile;

    public DeviceNullifierContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(DEVICE_NULLIFIER_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (DeviceNullifierTile) world.getTileEntity(pos);

        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlot(new SlotCoFH(tileInv, i * 3 + j + 1, 44 + j * 18, 17 + i * 18));
            }
        }
        bindAugmentSlots(tileInv, 10, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

    public void emptyBin() {

        ContainerPacket.sendToServer(this);
    }

    @Override
    protected int getMergeableSlotCount() {

        return baseTile.invSize() - 1;
    }

    // region NETWORK
    @Override
    public void handleContainerPacket(PacketBuffer buffer) {

        tile.emptyBin();
    }
    // endregion
}
