package cofh.thermal.core.inventory.container;

import cofh.core.inventory.container.TileContainer;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.thermal.core.tileentity.ChunkLoaderTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.CHUNK_LOADER_CONTAINER;

public class ChunkLoaderContainer extends TileContainer {

    public final ChunkLoaderTile tile;

    public ChunkLoaderContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(CHUNK_LOADER_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ChunkLoaderTile) world.getTileEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        bindPlayerInventory(inventory);
    }

}
