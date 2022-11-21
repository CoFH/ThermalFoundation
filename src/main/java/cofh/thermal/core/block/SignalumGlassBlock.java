package cofh.thermal.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static cofh.lib.util.Constants.FULL_CUBE_COLLISION;

public class SignalumGlassBlock extends HardenedGlassBlock {

    protected static int duration = 100;

    public SignalumGlassBlock(Properties properties) {

        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {

        return FULL_CUBE_COLLISION;
    }

    @Override
    public boolean isSignalSource(BlockState state) {

        return true;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {

        return 15;
    }

}
