package cofh.thermal.lib.block;

import cofh.core.block.TileBlockActive6Way;
import cofh.core.block.entity.TileCoFH;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

import static cofh.lib.util.constants.Constants.FACING_ALL;

public class TileBlockDynamo extends TileBlockActive6Way {

    private static final VoxelShape[] BASE_SHAPE = new VoxelShape[]{
            Block.box(0, 6, 0, 16, 16, 16),
            Block.box(0, 0, 0, 16, 10, 16),
            Block.box(0, 0, 6, 16, 16, 16),
            Block.box(0, 0, 0, 16, 16, 10),
            Block.box(6, 0, 0, 16, 16, 16),
            Block.box(0, 0, 0, 10, 16, 16)
    };

    private static final VoxelShape[] COIL_SHAPE = new VoxelShape[]{
            Block.box(4, 0, 4, 12, 6, 12),
            Block.box(4, 10, 4, 12, 16, 12),
            Block.box(4, 4, 0, 12, 12, 6),
            Block.box(4, 4, 10, 12, 12, 16),
            Block.box(0, 4, 4, 6, 12, 12),
            Block.box(10, 4, 4, 16, 12, 12)
    };

    private static final VoxelShape[] DYNAMO_SHAPE = new VoxelShape[]{
            Shapes.or(BASE_SHAPE[0], COIL_SHAPE[0]),
            Shapes.or(BASE_SHAPE[1], COIL_SHAPE[1]),
            Shapes.or(BASE_SHAPE[2], COIL_SHAPE[2]),
            Shapes.or(BASE_SHAPE[3], COIL_SHAPE[3]),
            Shapes.or(BASE_SHAPE[4], COIL_SHAPE[4]),
            Shapes.or(BASE_SHAPE[5], COIL_SHAPE[5])
    };

    public TileBlockDynamo(Properties builder, Class<? extends TileCoFH> tileClass, Supplier<BlockEntityType<? extends TileCoFH>> blockEntityType) {

        super(builder, tileClass, blockEntityType);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {

        return DYNAMO_SHAPE[state.getValue(FACING_ALL).ordinal()];
    }

}
