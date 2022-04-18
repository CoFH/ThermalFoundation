package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static cofh.lib.util.Utils.destroyBlock;

public class EarthChargeItem extends ItemCoFH {

    public EarthChargeItem(Properties builder) {

        super(builder);

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        //        if (player != null && (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, context.getClickedFace(), context.getItemInHand()))) {
        //            return ActionResultType.FAIL;
        //        }
        Material material = state.getMaterial();
        if (material == Material.STONE || material == Material.DIRT || state.getBlock() instanceof SnowyDirtBlock) {
            destroyBlock(world, pos, true, player);
            playUseSound(world, pos);
            context.getItemInHand().shrink(1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    private void playUseSound(World worldIn, BlockPos pos) {

        worldIn.playSound(null, pos, worldIn.getBlockState(pos).getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    // region DISPENSER BEHAVIOR
    private static final IDispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(IBlockSource source, ItemStack stack) {

            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            IPosition iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            World world = source.getLevel();
            Random random = world.random;
            double d3 = random.nextGaussian() * 0.05D + (double) direction.getStepX();
            double d4 = random.nextGaussian() * 0.05D + (double) direction.getStepY();
            double d5 = random.nextGaussian() * 0.05D + (double) direction.getStepZ();
            world.addFreshEntity(Util.make(() -> new BasalzProjectileEntity(d0, d1, d2, d3, d4, d5, world)));
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(IBlockSource source) {

            source.getLevel().levelEvent(1018, source.getPos(), 0);
        }
    };
    // endregion
}
