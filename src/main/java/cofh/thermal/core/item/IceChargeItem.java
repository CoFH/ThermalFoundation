package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.entity.projectile.BlizzProjectileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

import java.util.Random;

import static cofh.lib.util.references.CoreReferences.GLOSSED_MAGMA;
import static cofh.thermal.lib.common.ThermalConfig.permanentLava;
import static cofh.thermal.lib.common.ThermalConfig.permanentWater;
import static net.minecraft.block.Blocks.*;

public class IceChargeItem extends ItemCoFH {

    public IceChargeItem(Properties builder) {

        super(builder);

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        //        if (player != null && (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, context.getFace(), context.getItem()))) {
        //            return ActionResultType.FAIL;
        //        }
        boolean used = false;
        // CAMPFIRE/FIRE
        if (AreaUtils.isLitCampfire(state)) {
            world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false));
            used = true;
        }
        // SNOW
        pos = pos.relative(context.getClickedFace());
        if (world.isEmptyBlock(pos) && AreaUtils.isValidSnowPosition(world, pos)) {
            world.setBlockAndUpdate(pos, SNOW.defaultBlockState());
            used = true;
        }
        state = world.getBlockState(pos);
        // FIRE
        if (state.getBlock() == FIRE) {
            world.setBlockAndUpdate(pos, AIR.defaultBlockState());
            used = true;
        }
        // WATER
        boolean isFull = state.getBlock() == WATER && state.getValue(FlowingFluidBlock.LEVEL) == 0;
        if (state.getMaterial() == Material.WATER && isFull && state.canSurvive(world, pos) && world.isUnobstructed(state, pos, ISelectionContext.empty())) {
            world.setBlockAndUpdate(pos, permanentWater ? ICE.defaultBlockState() : FROSTED_ICE.defaultBlockState());
            used = true;
            if (!permanentWater) {
                world.getBlockTicks().scheduleTick(pos, FROSTED_ICE, MathHelper.nextInt(world.random, 60, 120));
            }
        }
        // LAVA
        isFull = state.getBlock() == LAVA && state.getValue(FlowingFluidBlock.LEVEL) == 0;
        if (state.getMaterial() == Material.LAVA && isFull && state.canSurvive(world, pos) && world.isUnobstructed(state, pos, ISelectionContext.empty())) {
            world.setBlockAndUpdate(pos, permanentLava ? OBSIDIAN.defaultBlockState() : GLOSSED_MAGMA.defaultBlockState());
            used = true;
            if (!permanentLava) {
                world.getBlockTicks().scheduleTick(pos, GLOSSED_MAGMA, MathHelper.nextInt(world.random, 60, 120));
            }
        }
        if (used) {
            playUseSound(world, pos);
            context.getItemInHand().shrink(1);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    private void playUseSound(World worldIn, BlockPos pos) {

        worldIn.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
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
            world.addFreshEntity(Util.make(() -> new BlizzProjectileEntity(d0, d1, d2, d3, d4, d5, world)));
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
