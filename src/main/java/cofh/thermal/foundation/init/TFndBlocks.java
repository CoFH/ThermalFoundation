//package cofh.thermal.foundation.init;
//
//import cofh.core.item.BlockItemCoFH;
//import cofh.lib.block.impl.OreBlockCoFH;
//import cofh.thermal.core.init.TCoreBlocks;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.SandBlock;
//import net.minecraft.world.level.block.SoundType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.level.material.MaterialColor;
//
//import static cofh.thermal.core.ThermalCore.BLOCKS;
//import static cofh.thermal.core.init.TCoreBlocks.storageBlock;
//import static cofh.thermal.core.util.RegistrationHelper.*;
//import static cofh.thermal.lib.common.ThermalFlags.*;
//import static cofh.thermal.lib.common.ThermalIDs.*;
//import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_BLOCKS;
//import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;
//import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
//
//public class TFndBlocks {
//
//    private TFndBlocks() {
//
//    }
//
//    public static void register() {
//
//        registerResources();
//        registerStorage();
//    }
//
//    public static void setup() {
//
//    }
//
//    // region HELPERS
//    private static void registerResources() {
//
//        registerBlock(ID_APATITE_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_APATITE));
//        registerBlock(deepslate(ID_APATITE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_APATITE));
//        registerBlock(ID_CINNABAR_ORE, () -> OreBlockCoFH.createStoneOre().xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR));
//        registerBlock(deepslate(ID_CINNABAR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR));
//        registerBlock(ID_NITER_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_NITER));
//        registerBlock(deepslate(ID_NITER_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_NITER));
//        registerBlock(ID_SULFUR_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR));
//        registerBlock(deepslate(ID_SULFUR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR));
//
//        registerBlock(ID_TIN_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_TIN));
//        registerBlock(deepslate(ID_TIN_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_TIN));
//        registerBlock(ID_LEAD_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_LEAD));
//        registerBlock(deepslate(ID_LEAD_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_LEAD));
//        registerBlock(ID_SILVER_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_SILVER));
//        registerBlock(deepslate(ID_SILVER_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_SILVER));
//        registerBlock(ID_NICKEL_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_NICKEL));
//        registerBlock(deepslate(ID_NICKEL_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_NICKEL));
//
//        registerBlock(ID_RUBY_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), getFlag(FLAG_RESOURCE_RUBY));
//        registerBlock(deepslate(ID_RUBY_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), getFlag(FLAG_RESOURCE_RUBY));
//        registerBlock(ID_SAPPHIRE_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE));
//        registerBlock(deepslate(ID_SAPPHIRE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE));
//
//        registerBlockAndItem(ID_OIL_SAND, () -> new SandBlock(14406560, copy(Blocks.SAND)),
//                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_SAND), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)));
//
//        registerBlockAndItem(ID_OIL_RED_SAND, () -> new SandBlock(11098145, copy(Blocks.RED_SAND)),
//                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_RED_SAND), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)));
//    }
//
//    private static void registerStorage() {
//
//        registerBlock(ID_APATITE_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
//        registerBlock(ID_CINNABAR_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_RED).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
//        registerBlock(ID_NITER_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
//        registerBlockAndItem(ID_SULFUR_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()) {
//
//            @Override
//            public boolean isFireSource(BlockState state, LevelReader world, BlockPos pos, Direction side) {
//
//                return side == Direction.UP;
//            }
//        }, () -> new BlockItemCoFH(BLOCKS.get(ID_SULFUR_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(12000));
//
//        registerBlock(raw(ID_TIN_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
//        registerBlock(raw(ID_LEAD_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
//        registerBlock(raw(ID_SILVER_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_GRAY).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
//        registerBlock(raw(ID_NICKEL_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
//
//        registerBlock(ID_TIN_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_TIN));
//        registerBlock(ID_LEAD_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_LEAD));
//        registerBlock(ID_SILVER_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_SILVER));
//        registerBlock(ID_NICKEL_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_NICKEL));
//
//        registerBlock(ID_BRONZE_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_BRONZE));
//        registerBlock(ID_ELECTRUM_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_ELECTRUM));
//        registerBlock(ID_INVAR_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_INVAR));
//        registerBlock(ID_CONSTANTAN_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_CONSTANTAN));
//
//        registerBlock(ID_RUBY_BLOCK, () -> storageBlock(MaterialColor.COLOR_RED), getFlag(FLAG_RESOURCE_RUBY));
//        registerBlock(ID_SAPPHIRE_BLOCK, () -> storageBlock(MaterialColor.COLOR_BLUE), getFlag(FLAG_RESOURCE_SAPPHIRE));
//    }
//    // endregion
//}
