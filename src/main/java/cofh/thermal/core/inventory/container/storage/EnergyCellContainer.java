package cofh.thermal.core.inventory.container.storage;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.block.entity.storage.EnergyCellTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreReferences.ENERGY_CELL_CONTAINER;

public class EnergyCellContainer extends TileContainer {

    public final EnergyCellTile tile;

    public EnergyCellContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(ENERGY_CELL_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (EnergyCellTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        bindAugmentSlots(tileInv, 0, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
