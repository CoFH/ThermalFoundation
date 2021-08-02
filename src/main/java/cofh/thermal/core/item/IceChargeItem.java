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

        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        //        if (player != null && (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, context.getFace(), context.getItem()))) {
        //            return ActionResultType.FAIL;
        //        }
        boolean used = false;
        // CAMPFIRE/FIRE
        if (AreaUtils.isLitCampfire(state)) {
            world.setBlockState(pos, state.with(BlockStateProperties.LIT, false));
            used = true;
        }
        // SNOW
        pos = pos.offset(context.getFace());
        if (world.isAirBlock(pos) && AreaUtils.isValidSnowPosition(world, pos)) {
            world.setBlockState(pos, SNOW.getDefaultState());
            used = true;
        }
        state = world.getBlockState(pos);
        // FIRE
        if (state.getBlock() == FIRE) {
            world.setBlockState(pos, AIR.getDefaultState());
            used = true;
        }
        // WATER
        boolean isFull = state.getBlock() == WATER && state.get(FlowingFluidBlock.LEVEL) == 0;
        if (state.getMaterial() == Material.WATER && isFull && state.isValidPosition(world, pos) && world.placedBlockCollides(state, pos, ISelectionContext.dummy())) {
            world.setBlockState(pos, permanentWater ? ICE.getDefaultState() : FROSTED_ICE.getDefaultState());
            used = true;
            if (!permanentWater) {
                world.getPendingBlockTicks().scheduleTick(pos, FROSTED_ICE, MathHelper.nextInt(world.rand, 60, 120));
            }
        }
        // LAVA
        isFull = state.getBlock() == LAVA && state.get(FlowingFluidBlock.LEVEL) == 0;
        if (state.getMaterial() == Material.LAVA && isFull && state.isValidPosition(world, pos) && world.placedBlockCollides(state, pos, ISelectionContext.dummy())) {
            world.setBlockState(pos, permanentLava ? OBSIDIAN.getDefaultState() : GLOSSED_MAGMA.getDefaultState());
            used = true;
            if (!permanentLava) {
                world.getPendingBlockTicks().scheduleTick(pos, GLOSSED_MAGMA, MathHelper.nextInt(world.rand, 60, 120));
            }
        }
        if (used) {
            playUseSound(world, pos);
            context.getItem().shrink(1);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    private void playUseSound(World worldIn, BlockPos pos) {

        worldIn.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    // region DISPENSER BEHAVIOR
    private static final IDispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {

            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            IPosition iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.getX() + (double) ((float) direction.getXOffset() * 0.3F);
            double d1 = iposition.getY() + (double) ((float) direction.getYOffset() * 0.3F);
            double d2 = iposition.getZ() + (double) ((float) direction.getZOffset() * 0.3F);
            World world = source.getWorld();
            Random random = world.rand;
            double d3 = random.nextGaussian() * 0.05D + (double) direction.getXOffset();
            double d4 = random.nextGaussian() * 0.05D + (double) direction.getYOffset();
            double d5 = random.nextGaussian() * 0.05D + (double) direction.getZOffset();
            world.addEntity(Util.make(() -> new BlizzProjectileEntity(d0, d1, d2, d3, d4, d5, world)));
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playDispenseSound(IBlockSource source) {

            source.getWorld().playEvent(1018, source.getBlockPos(), 0);
        }
    };
    // endregion
}
