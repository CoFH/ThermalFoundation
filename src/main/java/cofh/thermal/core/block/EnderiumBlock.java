package cofh.thermal.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import static cofh.lib.util.constants.Constants.FULL_CUBE_COLLISION;
import static cofh.lib.util.references.CoreReferences.ENDERFERENCE;

public class EnderiumBlock extends Block {

    protected static int duration = 100;

    public EnderiumBlock(Properties properties) {

        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return FULL_CUBE_COLLISION;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {

        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(ENDERFERENCE, duration, 0, false, false));
        }
    }

}
