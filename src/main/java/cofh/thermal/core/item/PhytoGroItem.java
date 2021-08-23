package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.lib.util.Utils;
import net.minecraft.block.*;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class PhytoGroItem extends ItemCoFH {

    protected static final int CLOUD_DURATION = 20;

    protected int strength = 3;

    public PhytoGroItem(Properties builder) {

        super(builder);
    }

    public PhytoGroItem(Properties builder, int strength) {

        super(builder);
        this.strength = strength;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {

        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (attemptGrowPlant(world, pos, context, strength)) {
            if (!world.isClientSide) {
                world.levelEvent(2005, pos, 0);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    protected static boolean attemptGrowPlant(World world, BlockPos pos, ItemUseContext context, int strength) {

        ItemStack stack = context.getItemInHand();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            int hook = ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack);
            if (hook != 0) {
                return hook > 0;
            }
        }
        boolean used;
        used = growPlant(world, pos, state, strength);
        used |= growSeagrass(world, pos, context.getClickedFace());
        if (Utils.isServerWorld(world) && used && world.random.nextInt(strength) == 0) {
            stack.shrink(1);
        }
        return used;
    }

    protected static boolean growPlant(World worldIn, BlockPos pos, BlockState state, int strength) {

        if (state.getBlock() instanceof IGrowable) {
            IGrowable growable = (IGrowable) state.getBlock();
            boolean used = false;
            if (growable.isValidBonemealTarget(worldIn, pos, state, worldIn.isClientSide)) {
                if (worldIn instanceof ServerWorld) {
                    boolean canUse = false;
                    for (int i = 0; i < strength; ++i) {
                        canUse |= growable.isBonemealSuccess(worldIn, worldIn.random, pos, state);
                    }
                    if (canUse) {
                        // TODO: Remove try/catch when Mojang fixes base issue.
                        try {
                            growable.performBonemeal((ServerWorld) worldIn, worldIn.random, pos, state);
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

    public static boolean growSeagrass(World worldIn, BlockPos pos, @Nullable Direction side) {

        if (worldIn.getBlockState(pos).is(Blocks.WATER) && worldIn.getFluidState(pos).getAmount() == 8) {
            if (!(worldIn instanceof ServerWorld)) {
                return true;
            } else {
                label79:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).isCollisionShapeFullBlock(worldIn, blockpos)) {
                            continue label79;
                        }
                    }
                    Optional<RegistryKey<Biome>> optional = worldIn.getBiomeName(blockpos);
                    if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
                        if (i == 0 && side != null && side.getAxis().isHorizontal()) {
                            blockstate = (BlockTags.WALL_CORALS.getRandomElement(worldIn.random)).defaultBlockState().setValue(DeadCoralWallFanBlock.FACING, side);
                        } else if (random.nextInt(4) == 0) {
                            blockstate = (BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random)).defaultBlockState();
                        }
                    }
                    if (blockstate.getBlock().is(BlockTags.WALL_CORALS)) {
                        for (int k = 0; !blockstate.canSurvive(worldIn, blockpos) && k < 4; ++k) {
                            blockstate = blockstate.setValue(DeadCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        }
                    }
                    if (blockstate.canSurvive(worldIn, blockpos)) {
                        BlockState blockstate1 = worldIn.getBlockState(blockpos);
                        if (blockstate1.is(Blocks.WATER) && worldIn.getFluidState(blockpos).getAmount() == 8) {
                            worldIn.setBlock(blockpos, blockstate, 3);
                        } else if (blockstate1.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((IGrowable) Blocks.SEAGRASS).performBonemeal((ServerWorld) worldIn, random, blockpos, blockstate1);
                        }
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    protected static void makeAreaOfEffectCloud(World world, BlockPos pos, int radius) {

        boolean isPlant = world.getBlockState(pos).getBlock() instanceof IPlantable;
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, pos.getX() + 0.5D, pos.getY() + (isPlant ? 0.0D : 1.0D), pos.getZ() + 0.5D);
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.HAPPY_VILLAGER);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((1 + radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addFreshEntity(cloud);
    }

}
