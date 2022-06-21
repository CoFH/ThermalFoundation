package cofh.thermal.core.item;

import cofh.core.content.item.ItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class FertilizerItem extends ItemCoFH {

    protected static final int CLOUD_DURATION = 20;

    protected int strength = 4;

    public FertilizerItem(Properties builder) {

        super(builder);
    }

    public FertilizerItem(Properties builder, int strength) {

        super(builder);
        this.strength = strength;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (attemptGrowPlant(world, pos, context, strength)) {
            if (!world.isClientSide) {
                world.levelEvent(2005, pos, 0);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    protected static boolean attemptGrowPlant(Level world, BlockPos pos, UseOnContext context, int strength) {

        ItemStack stack = context.getItemInHand();
        BlockState state = world.getBlockState(pos);
        Player player = context.getPlayer();
        if (player != null) {
            int hook = ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack);
            if (hook != 0) {
                return hook > 0;
            }
        }
        boolean used;
        used = growPlant(world, pos, state, strength);
        used |= growWaterPlant(world, pos, context.getClickedFace());
        if (Utils.isServerWorld(world) && used && world.random.nextInt(strength) == 0) {
            stack.shrink(1);
        }
        return used;
    }

    protected static boolean growPlant(Level worldIn, BlockPos pos, BlockState state, int strength) {

        if (state.getBlock() instanceof BonemealableBlock growable) {
            boolean used = false;
            if (growable.isValidBonemealTarget(worldIn, pos, state, worldIn.isClientSide)) {
                if (worldIn instanceof ServerLevel) {
                    boolean canUse = false;
                    for (int i = 0; i < strength; ++i) {
                        canUse |= growable.isBonemealSuccess(worldIn, worldIn.random, pos, state);
                    }
                    if (canUse) {
                        // TODO: Remove try/catch when Mojang fixes base issue.
                        try {
                            growable.performBonemeal((ServerLevel) worldIn, worldIn.random, pos, state);
                        } catch (Exception e) {
                            // Vanilla issue causes bamboo to crash if grown close to world height
                            if (!(growable instanceof BambooBlock)) {
                                throw e;
                            }
                        }
                    }
                }
                used = true;
            }
            return used;
        }
        return false;
    }

    public static boolean growWaterPlant(Level worldIn, BlockPos pos, @Nullable Direction side) {

        if (worldIn.getBlockState(pos).is(Blocks.WATER) && worldIn.getFluidState(pos).getAmount() == 8) {
            if (!(worldIn instanceof ServerLevel)) {
                return true;
            } else {
                RandomSource random = worldIn.getRandom();
                label78:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();
                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).isCollisionShapeFullBlock(worldIn, blockpos)) {
                            continue label78;
                        }
                    }
                    Holder<Biome> holder = worldIn.getBiome(blockpos);
                    if (holder.is(Biomes.WARM_OCEAN)) {
                        if (i == 0 && side != null && side.getAxis().isHorizontal()) {
                            blockstate = Registry.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap((p_204098_) -> p_204098_.getRandomElement(worldIn.random)).map((p_204100_) -> p_204100_.value().defaultBlockState()).orElse(blockstate);
                            if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, side);
                            }
                        } else if (random.nextInt(4) == 0) {
                            blockstate = Registry.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap((p_204091_) -> p_204091_.getRandomElement(worldIn.random)).map((p_204095_) -> p_204095_.value().defaultBlockState()).orElse(blockstate);
                        }
                    }
                    if (blockstate.is(BlockTags.WALL_CORALS, (p_204093_) -> p_204093_.hasProperty(BaseCoralWallFanBlock.FACING))) {
                        for (int k = 0; !blockstate.canSurvive(worldIn, blockpos) && k < 4; ++k) {
                            blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        }
                    }
                    if (blockstate.canSurvive(worldIn, blockpos)) {
                        BlockState blockstate1 = worldIn.getBlockState(blockpos);
                        if (blockstate1.is(Blocks.WATER) && worldIn.getFluidState(blockpos).getAmount() == 8) {
                            worldIn.setBlock(blockpos, blockstate, 3);
                        } else if (blockstate1.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((BonemealableBlock) Blocks.SEAGRASS).performBonemeal((ServerLevel) worldIn, random, blockpos, blockstate1);
                        }
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    protected static void makeAreaOfEffectCloud(Level world, BlockPos pos, int radius) {

        boolean isPlant = world.getBlockState(pos).getBlock() instanceof IPlantable;
        AreaEffectCloud cloud = new AreaEffectCloud(world, pos.getX() + 0.5D, pos.getY() + (isPlant ? 0.0D : 1.0D), pos.getZ() + 0.5D);
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.HAPPY_VILLAGER);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((1 + radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addFreshEntity(cloud);
    }

}
