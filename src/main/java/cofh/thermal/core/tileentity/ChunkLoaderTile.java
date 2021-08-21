package cofh.thermal.core.tileentity;

import cofh.thermal.core.inventory.container.ChunkLoaderContainer;
import cofh.thermal.lib.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ITickableTileEntity;

import javax.annotation.Nullable;

import static cofh.thermal.core.init.TCoreReferences.CHUNK_LOADER_TILE;

public class ChunkLoaderTile extends ThermalTileBase implements ITickableTileEntity {

    protected boolean cached;

    public ChunkLoaderTile() {

        super(CHUNK_LOADER_TILE);
    }

    @Override
    public void tick() {

        //        updateActiveState();
        //
        //        if (isActive) {
        //            tank.modify((int) (GENERATION_RATE * baseMod));
        //            fillFluid();
        //        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new ChunkLoaderContainer(i, world, pos, inventory, player);
    }

}
