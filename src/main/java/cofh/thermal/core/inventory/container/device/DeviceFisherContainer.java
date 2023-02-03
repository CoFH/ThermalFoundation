package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.container.slot.SlotRemoveOnly;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.lib.block.entity.AugmentableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreContainers.DEVICE_FISHER_CONTAINER;

public class DeviceFisherContainer extends TileContainer {

    public final AugmentableBlockEntity tile;

    public DeviceFisherContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(DEVICE_FISHER_CONTAINER.get(), windowId, world, pos, inventory, player);
        this.tile = (AugmentableBlockEntity) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotCoFH(tileInv, 0, 26, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 5; ++j) {
                addSlot(new SlotRemoveOnly(tileInv, 1 + j + i * 5, 62 + j * 18, 17 + i * 18));
            }
        }
        bindAugmentSlots(tileInv, 16, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
