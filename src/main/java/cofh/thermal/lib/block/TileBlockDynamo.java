package cofh.thermal.lib.block;

import cofh.core.block.TileBlockActive6Way;
import cofh.core.tileentity.TileCoFH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

import static cofh.lib.util.constants.Constants.FACING_ALL;

public class TileBlockDynamo extends TileBlockActive6Way {

    private static final VoxelShape[] BASE_SHAPE = new VoxelShape[]{
            Block.makeCuboidShape(0, 6, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 10, 16),
            Block.makeCuboidShape(0, 0, 6, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 16, 10),
            Block.makeCuboidShape(6, 0, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 10, 16, 16)
    };

    private static final VoxelShape[] COIL_SHAPE = new VoxelShape[]{
            Block.makeCuboidShape(4, 0, 4, 12, 6, 12),
            Block.makeCuboidShape(4, 10, 4, 12, 16, 12),
            Block.makeCuboidShape(4, 4, 0, 12, 12, 6),
            Block.makeCuboidShape(4, 4, 10, 12, 12, 16),
            Block.makeCuboidShape(0, 4, 4, 6, 12, 12),
            Block.makeCuboidShape(10, 4, 4, 16, 12, 12)
    };

    private static final VoxelShape[] DYNAMO_SHAPE = new VoxelShape[]{
            VoxelShapes.or(BASE_SHAPE[0], COIL_SHAPE[0]),
            VoxelShapes.or(BASE_SHAPE[1], COIL_SHAPE[1]),
            VoxelShapes.or(BASE_SHAPE[2], COIL_SHAPE[2]),
            VoxelShapes.or(BASE_SHAPE[3], COIL_SHAPE[3]),
            VoxelShapes.or(BASE_SHAPE[4], COIL_SHAPE[4]),
            VoxelShapes.or(BASE_SHAPE[5], COIL_SHAPE[5])
    };

    public TileBlockDynamo(Properties builder, Supplier<? extends TileCoFH> supplier) {

        super(builder, supplier);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return DYNAMO_SHAPE[state.get(FACING_ALL).ordinal()];
    }

}
