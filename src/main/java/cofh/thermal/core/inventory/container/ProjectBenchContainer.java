package cofh.thermal.core.inventory.container;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.tileentity.ProjectBenchTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static cofh.thermal.core.init.TCoreReferences.PROJECT_BENCH_CONTAINER;

public class ProjectBenchContainer extends TileContainer {

    public final ProjectBenchTile tile;

    public ProjectBenchContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(PROJECT_BENCH_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ProjectBenchTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        bindAugmentSlots(tileInv, 3, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

}
