package cofh.thermal.core.inventory.container.storage;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.block.entity.storage.FluidCellTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreContainers.FLUID_CELL_CONTAINER;

public class FluidCellContainer extends TileContainer {

    public final FluidCellTile tile;

    public FluidCellContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(FLUID_CELL_CONTAINER.get(), windowId, world, pos, inventory, player);
        this.tile = (FluidCellTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        bindAugmentSlots(tileInv, 0, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
