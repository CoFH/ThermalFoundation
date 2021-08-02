package cofh.thermal.core.inventory.container.storage;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.tileentity.storage.FluidCellTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.FLUID_CELL_CONTAINER;

public class FluidCellContainer extends TileContainer {

    public final FluidCellTile tile;

    public FluidCellContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(FLUID_CELL_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (FluidCellTile) world.getTileEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        bindAugmentSlots(tileInv, 0, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
