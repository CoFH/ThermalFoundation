package cofh.thermal.core.block;

import cofh.lib.block.impl.HardenedGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import static cofh.lib.util.constants.Constants.FULL_CUBE_COLLISION;

public class LumiumGlassBlock extends HardenedGlassBlock {

    protected static int duration = 40;

    public LumiumGlassBlock(Properties properties) {

        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return FULL_CUBE_COLLISION;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {

        if (entityIn instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) entityIn;
            if (mob.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                mob.setFire(duration);
            }
        }
    }

}