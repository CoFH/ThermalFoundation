package cofh.thermal.lib.block;

import cofh.core.block.TileBlock4Way;
import cofh.core.tileentity.TileCoFH;
import cofh.lib.tileentity.ITileCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class TileBlockCell extends TileBlock4Way {

    public TileBlockCell(Properties builder, Class<? extends TileCoFH> tileClass, Supplier<BlockEntityType<? extends TileCoFH>> blockEntityType) {

        super(builder, tileClass, blockEntityType);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {

        return true;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {

        if (state.getLightEmission() > 0) {
            return state.getLightEmission();
        }
        if (world.getBlockEntity(pos) instanceof ITileCallback tile) {
            return tile.getLightValue();
        }
        return state.getLightEmission();
    }

}
