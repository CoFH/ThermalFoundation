package cofh.thermal.core.item;

import cofh.core.content.item.ItemCoFH;
import cofh.core.util.AreaUtils;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.entity.projectile.BlizzProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

import static cofh.core.init.CoreBlocks.GLOSSED_MAGMA;
import static cofh.thermal.core.config.ThermalCoreConfig.permanentLava;
import static cofh.thermal.core.config.ThermalCoreConfig.permanentWater;
import static net.minecraft.world.level.block.Blocks.*;

public class IceChargeItem extends ItemCoFH {

    public IceChargeItem(Properties builder) {

        super(builder);

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        //        if (player != null && (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, context.getClickedFace(), context.getItemInHand()))) {
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
        boolean isFull = state.getBlock() == WATER && state.getValue(LiquidBlock.LEVEL) == 0;
        if (state.getMaterial() == Material.WATER && isFull && state.canSurvive(world, pos) && world.isUnobstructed(state, pos, CollisionContext.empty())) {
            world.setBlockAndUpdate(pos, permanentWater ? ICE.defaultBlockState() : FROSTED_ICE.defaultBlockState());
            used = true;
            if (!permanentWater) {
                world.scheduleTick(pos, FROSTED_ICE, MathHelper.nextInt(world.random, 60, 120));
            }
        }
        // LAVA
        isFull = state.getBlock() == LAVA && state.getValue(LiquidBlock.LEVEL) == 0;
        if (state.getMaterial() == Material.LAVA && isFull && state.canSurvive(world, pos) && world.isUnobstructed(state, pos, CollisionContext.empty())) {
            world.setBlockAndUpdate(pos, permanentLava ? OBSIDIAN.defaultBlockState() : GLOSSED_MAGMA.get().defaultBlockState());
            used = true;
            if (!permanentLava) {
                world.scheduleTick(pos, GLOSSED_MAGMA.get(), MathHelper.nextInt(world.random, 60, 120));
            }
        }
        if (used) {
            playUseSound(world, pos);
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    private void playUseSound(Level worldIn, BlockPos pos) {

        worldIn.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 1.0F, (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F + 1.0F);
    }

    // region DISPENSER BEHAVIOR
    private static final DispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {

            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.getLevel();
            double d3 = world.random.nextGaussian() * 0.05D + (double) direction.getStepX();
            double d4 = world.random.nextGaussian() * 0.05D + (double) direction.getStepY();
            double d5 = world.random.nextGaussian() * 0.05D + (double) direction.getStepZ();
            world.addFreshEntity(new BlizzProjectile(d0, d1, d2, d3, d4, d5, world));
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {

            source.getLevel().levelEvent(1018, source.getPos(), 0);
        }
    };
    // endregion
}
