package cofh.thermal.lib.block;

import cofh.core.block.TileBlockActive6Way;
import cofh.core.tileentity.TileCoFH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static cofh.lib.util.constants.Constants.FACING_ALL;

public class TileBlockDynamo extends TileBlockActive6Way implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return DYNAMO_SHAPE[state.getValue(FACING_ALL).ordinal()];
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {

        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

}
