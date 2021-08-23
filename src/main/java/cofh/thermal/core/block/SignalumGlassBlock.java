package cofh.thermal.core.block;

import cofh.lib.block.impl.HardenedGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import static cofh.lib.util.constants.Constants.FULL_CUBE_COLLISION;

public class SignalumGlassBlock extends HardenedGlassBlock {

    protected static int duration = 100;

    public SignalumGlassBlock(Properties properties) {

        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return FULL_CUBE_COLLISION;
    }

    @Override
    public boolean isSignalSource(BlockState state) {

        return true;
    }

    @Override
    public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {

        return 15;
    }

}
