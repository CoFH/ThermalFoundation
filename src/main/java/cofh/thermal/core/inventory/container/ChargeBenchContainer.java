package cofh.thermal.core.inventory.container;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.tileentity.ChargeBenchTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.CHARGE_BENCH_CONTAINER;

public class ChargeBenchContainer extends TileContainer {

    public final ChargeBenchTile tile;

    public ChargeBenchContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(CHARGE_BENCH_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ChargeBenchTile) world.getTileEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlot(new SlotCoFH(tileInv, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }
        addSlot(new SlotCoFH(tileInv, 9, 8, 53));

        bindAugmentSlots(tileInv, 10, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
