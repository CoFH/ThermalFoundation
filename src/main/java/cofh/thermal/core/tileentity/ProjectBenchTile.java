package cofh.thermal.core.tileentity;

import cofh.thermal.core.inventory.container.ProjectBenchContainer;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static cofh.thermal.core.init.TCoreReferences.PROJECT_BENCH_TILE;

public class ProjectBenchTile extends ThermalTileAugmentable {

    public ProjectBenchTile(BlockPos pos, BlockState state) {

        super(PROJECT_BENCH_TILE, pos, state);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new ProjectBenchContainer(i, level, worldPosition, inventory, player);
    }

}
