package cofh.thermal.core.tileentity;

import cofh.thermal.core.inventory.container.ProjectBenchContainer;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

import javax.annotation.Nullable;

import static cofh.thermal.core.init.TCoreReferences.PROJECT_BENCH_TILE;

public class ProjectBenchTile extends ThermalTileAugmentable {

    public ProjectBenchTile() {

        super(PROJECT_BENCH_TILE);
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new ProjectBenchContainer(i, world, pos, inventory, player);
    }

}
