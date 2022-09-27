package cofh.thermal.core.init;

import cofh.core.block.TileBlockActive;
import cofh.core.block.TileBlockActive4Way;
import cofh.core.block.TileBlockCoFH;
import cofh.core.item.BlockItemCoFH;
import cofh.lib.block.*;
import cofh.thermal.core.block.*;
import cofh.thermal.core.block.device.TileBlockComposter;
import cofh.thermal.core.block.entity.ChargeBenchTile;
import cofh.thermal.core.block.entity.TinkerBenchTile;
import cofh.thermal.core.block.entity.device.*;
import cofh.thermal.core.block.entity.storage.EnergyCellTile;
import cofh.thermal.core.block.entity.storage.FluidCellTile;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.item.EnergyCellBlockItem;
import cofh.thermal.core.item.FluidCellBlockItem;
import cofh.thermal.lib.block.TileBlockCell;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static cofh.lib.util.constants.BlockStatePropertiesCoFH.ACTIVE;
import static cofh.lib.util.helpers.BlockHelper.lightValue;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreTileEntities.*;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.ENERGY_STORAGE_VALIDATOR;
import static cofh.thermal.lib.common.ThermalAugmentRules.FLUID_STORAGE_VALIDATOR;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;
import static cofh.thermal.lib.common.ThermalItemGroups.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

public class TCoreBlocks {

    private TCoreBlocks() {

    }

    public static void register() {

        registerVanilla();
        registerResources();
        registerStorage();
        registerBuildingBlocks();
        registerMisc();

        registerTileBlocks();
    }

    public static void setup() {

        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(BLOCKS.get(ID_CHARCOAL_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_GUNPOWDER_BLOCK), 15, 100);
        fire.setFlammable(BLOCKS.get(ID_SUGAR_CANE_BLOCK), 60, 20);
        fire.setFlammable(BLOCKS.get(ID_BAMBOO_BLOCK), 60, 20);

        fire.setFlammable(BLOCKS.get(ID_SAWDUST_BLOCK), 10, 10);
        fire.setFlammable(BLOCKS.get(ID_COAL_COKE_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_BITUMEN_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_TAR_BLOCK), 5, 5);
        fire.setFlammable(BLOCKS.get(ID_ROSIN_BLOCK), 5, 5);

        DispenserBlock.registerBehavior(BLOCKS.get(ID_SLIME_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_REDSTONE_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_GLOWSTONE_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_ENDER_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);

        DispenserBlock.registerBehavior(BLOCKS.get(ID_PHYTO_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);

        DispenserBlock.registerBehavior(BLOCKS.get(ID_EARTH_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_FIRE_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_ICE_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_LIGHTNING_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);

        DispenserBlock.registerBehavior(BLOCKS.get(ID_NUKE_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerBehavior(BLOCKS.get(ID_GRAVITY_TNT), TntBlockCoFH.DISPENSER_BEHAVIOR);
    }

    // region HELPERS
    private static void registerVanilla() {

        registerBlockAndItem(ID_CHARCOAL_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(5.0F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
                () -> new BlockItemCoFH(BLOCKS.get(ID_CHARCOAL_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(16000).setShowInGroups(getFlag(FLAG_VANILLA_BLOCKS)));
        registerBlock(ID_GUNPOWDER_BLOCK, () -> new GunpowderBlock(of(Material.EXPLOSIVE, MaterialColor.COLOR_GRAY).strength(0.5F).sound(SoundType.SAND)), getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_SUGAR_CANE_BLOCK, () -> new RotatedPillarBlock(of(Material.GRASS, MaterialColor.PLANT).strength(1.0F).sound(SoundType.CROP)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.6F, DamageSource.FALL);
            }
        }, getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_BAMBOO_BLOCK, () -> new RotatedPillarBlock(of(Material.GRASS, MaterialColor.PLANT).strength(1.0F).sound(SoundType.BAMBOO)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.8F, DamageSource.FALL);
            }
        }, getFlag(FLAG_VANILLA_BLOCKS));

        registerBlock(ID_APPLE_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.COLOR_RED).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_CARROT_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_POTATO_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.TERRACOTTA_BROWN).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_BEETROOT_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
    }

    private static void registerResources() {

        registerBlock(ID_APATITE_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_APATITE));
        registerBlock(deepslate(ID_APATITE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_APATITE));
        registerBlock(ID_CINNABAR_ORE, () -> OreBlockCoFH.createStoneOre().xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR));
        registerBlock(deepslate(ID_CINNABAR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR));
        registerBlock(ID_NITER_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_NITER));
        registerBlock(deepslate(ID_NITER_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_NITER));
        registerBlock(ID_SULFUR_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR));
        registerBlock(deepslate(ID_SULFUR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR));


        registerBlock(ID_TIN_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_TIN));
        registerBlock(deepslate(ID_TIN_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_TIN));
        registerBlock(ID_LEAD_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_LEAD));
        registerBlock(deepslate(ID_LEAD_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_LEAD));
        registerBlock(ID_SILVER_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_SILVER));
        registerBlock(deepslate(ID_SILVER_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_SILVER));
        registerBlock(ID_NICKEL_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_NICKEL));
        registerBlock(deepslate(ID_NICKEL_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_NICKEL));

        registerBlock(ID_RUBY_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), getFlag(FLAG_RESOURCE_RUBY));
        registerBlock(deepslate(ID_RUBY_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), getFlag(FLAG_RESOURCE_RUBY));
        registerBlock(ID_SAPPHIRE_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE));
        registerBlock(deepslate(ID_SAPPHIRE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE));

        registerBlockAndItem(ID_OIL_SAND, () -> new SandBlock(14406560, copy(Blocks.SAND)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_SAND), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)));

        registerBlockAndItem(ID_OIL_RED_SAND, () -> new SandBlock(11098145, copy(Blocks.RED_SAND)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_RED_SAND), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)));
    }

    private static void registerStorage() {

        registerBlock(ID_APATITE_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
        registerBlock(ID_CINNABAR_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_RED).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
        registerBlock(ID_NITER_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
        registerBlockAndItem(ID_SULFUR_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).strength(3.0F, 3.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()) {

            @Override
            public boolean isFireSource(BlockState state, LevelReader world, BlockPos pos, Direction side) {

                return side == Direction.UP;
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_SULFUR_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(12000));

        registerBlock(raw(ID_TIN_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
        registerBlock(raw(ID_LEAD_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
        registerBlock(raw(ID_SILVER_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_GRAY).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
        registerBlock(raw(ID_NICKEL_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));

        registerBlock(ID_TIN_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_TIN));
        registerBlock(ID_LEAD_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_LEAD));
        registerBlock(ID_SILVER_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_SILVER));
        registerBlock(ID_NICKEL_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_NICKEL));

        registerBlock(ID_BRONZE_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_BRONZE));
        registerBlock(ID_ELECTRUM_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_ELECTRUM));
        registerBlock(ID_INVAR_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_INVAR));
        registerBlock(ID_CONSTANTAN_BLOCK, TCoreBlocks::storageBlock, getFlag(FLAG_RESOURCE_CONSTANTAN));

        registerBlock(ID_SIGNALUM_BLOCK, () -> new SignalumBlock(of(Material.METAL, MaterialColor.COLOR_RED).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(lightValue(7)).noOcclusion()), Rarity.UNCOMMON);
        registerBlock(ID_LUMIUM_BLOCK, () -> new LumiumBlock(of(Material.METAL, MaterialColor.COLOR_YELLOW).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(lightValue(15)).noOcclusion()), Rarity.UNCOMMON);
        registerBlock(ID_ENDERIUM_BLOCK, () -> new EnderiumBlock(of(Material.METAL, MaterialColor.COLOR_CYAN).strength(25.0F, 30.0F).sound(SoundType.LODESTONE).requiresCorrectToolForDrops().lightLevel(lightValue(3)).noOcclusion()), Rarity.UNCOMMON);

        registerBlock(ID_RUBY_BLOCK, () -> storageBlock(MaterialColor.COLOR_RED), getFlag(FLAG_RESOURCE_RUBY));
        registerBlock(ID_SAPPHIRE_BLOCK, () -> storageBlock(MaterialColor.COLOR_BLUE), getFlag(FLAG_RESOURCE_SAPPHIRE));

        registerBlockAndItem(ID_SAWDUST_BLOCK, () -> new FallingBlock(of(Material.WOOD).strength(1.0F, 1.0F).sound(SoundType.SAND)) {

            @OnlyIn (Dist.CLIENT)
            @Override
            public int getDustColor(BlockState state, BlockGetter reader, BlockPos pos) {

                return 11507581;
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_SAWDUST_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400));

        registerBlockAndItem(ID_COAL_COKE_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.COLOR_BLACK).strength(5.0F, 6.0F).requiresCorrectToolForDrops()),
                () -> new BlockItemCoFH(BLOCKS.get(ID_COAL_COKE_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(32000));

        registerBlockAndItem(ID_BITUMEN_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.COLOR_BLACK).strength(5.0F, 10.0F).sound(SoundType.NETHERRACK).requiresCorrectToolForDrops()),
                () -> new BlockItemCoFH(BLOCKS.get(ID_BITUMEN_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(16000));

        registerBlockAndItem(ID_TAR_BLOCK, () -> new Block(of(Material.CLAY, MaterialColor.COLOR_BLACK).strength(2.0F, 4.0F).speedFactor(0.8F).jumpFactor(0.8F).sound(SoundType.NETHERRACK)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.8F, DamageSource.FALL);
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_TAR_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(8000));

        registerBlockAndItem(ID_ROSIN_BLOCK, () -> new Block(of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(2.0F, 4.0F).speedFactor(0.8F).jumpFactor(0.8F).sound(SoundType.HONEY_BLOCK)) {

            @Override
            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.causeFallDamage(fallDistance, 0.8F, DamageSource.FALL);
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_ROSIN_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(8000));

        registerBlock(ID_RUBBER_BLOCK, () -> new RubberBlock(of(Material.CLAY, MaterialColor.TERRACOTTA_WHITE).strength(3.0F, 3.0F).jumpFactor(1.25F).sound(SoundType.FUNGUS)));
        registerBlock(ID_CURED_RUBBER_BLOCK, () -> new RubberBlock(of(Material.CLAY, MaterialColor.TERRACOTTA_BLACK).strength(3.0F, 3.0F).jumpFactor(1.25F).sound(SoundType.FUNGUS)));
        registerBlock(ID_SLAG_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_RICH_SLAG_BLOCK, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
    }

    private static void registerBuildingBlocks() {

        registerBlock(ID_MACHINE_FRAME, () -> new Block(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).noOcclusion()), getFlag(ID_MACHINE_FRAME));
        registerBlock(ID_ENERGY_CELL_FRAME, () -> new Block(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).noOcclusion()), getFlag(ID_ENERGY_CELL_FRAME));
        registerBlock(ID_FLUID_CELL_FRAME, () -> new Block(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).noOcclusion()), getFlag(ID_FLUID_CELL_FRAME));
        // registerBlock(ID_ITEM_CELL_FRAME, () -> new Block(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).harvestTool(ToolType.PICKAXE).noOcclusion()), getFlag(ID_ITEM_CELL_FRAME));

        registerBlock(ID_OBSIDIAN_GLASS, () -> new HardenedGlassBlock(of(Material.GLASS, MaterialColor.PODZOL).strength(5.0F, 1000.0F).sound(SoundType.GLASS).noOcclusion()));
        registerBlock(ID_SIGNALUM_GLASS, () -> new SignalumGlassBlock(of(Material.GLASS, MaterialColor.COLOR_RED).strength(5.0F, 1000.0F).sound(SoundType.GLASS).lightLevel(lightValue(7)).noOcclusion()), Rarity.UNCOMMON);
        registerBlock(ID_LUMIUM_GLASS, () -> new LumiumGlassBlock(of(Material.GLASS, MaterialColor.COLOR_YELLOW).strength(5.0F, 1000.0F).sound(SoundType.GLASS).lightLevel(lightValue(15)).noOcclusion()), Rarity.UNCOMMON);
        registerBlock(ID_ENDERIUM_GLASS, () -> new EnderiumGlassBlock(of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(5.0F, 1000.0F).sound(SoundType.GLASS).lightLevel(lightValue(3)).noOcclusion()), Rarity.UNCOMMON);

        registerBlock(ID_WHITE_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.SNOW).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_ORANGE_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_ORANGE).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_MAGENTA_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_MAGENTA).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_LIGHT_BLUE_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_YELLOW_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_YELLOW).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_LIME_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_PINK_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_PINK).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_GRAY_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_GRAY).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_LIGHT_GRAY_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_CYAN_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_CYAN).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_PURPLE_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_PURPLE).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_BLUE_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_BLUE).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_BROWN_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_BROWN).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_GREEN_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_GREEN).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_RED_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_RED).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_BLACK_ROCKWOOL, () -> new Block(of(Material.STONE, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOL)), getFlag(FLAG_ROCKWOOL));

        registerBlock(ID_POLISHED_SLAG, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_CHISELED_SLAG, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_SLAG_BRICKS, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_CRACKED_SLAG_BRICKS, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_POLISHED_RICH_SLAG, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_CHISELED_RICH_SLAG, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_RICH_SLAG_BRICKS, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
        registerBlock(ID_CRACKED_RICH_SLAG_BRICKS, () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).strength(1.5F, 6.0F).sound(SoundType.BASALT).requiresCorrectToolForDrops()));
    }

    private static void registerMisc() {

    }

    private static void registerTileBlocks() {

        IntSupplier deviceAugs = () -> ThermalCoreConfig.deviceAugments;

        registerAugmentableBlock(ID_DEVICE_HIVE_EXTRACTOR, () -> new TileBlockActive4Way(of(Material.WOOD).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceHiveExtractorTile.class, DEVICE_HIVE_EXTRACTOR_TILE), deviceAugs, DeviceHiveExtractorTile.AUG_VALIDATOR, getFlag(ID_DEVICE_HIVE_EXTRACTOR));
        registerAugmentableBlock(ID_DEVICE_TREE_EXTRACTOR, () -> new TileBlockActive4Way(of(Material.WOOD).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceTreeExtractorTile.class, DEVICE_TREE_EXTRACTOR_TILE), deviceAugs, DeviceTreeExtractorTile.AUG_VALIDATOR, getFlag(ID_DEVICE_TREE_EXTRACTOR));
        registerAugmentableBlock(ID_DEVICE_FISHER, () -> new TileBlockActive4Way(of(Material.WOOD).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceFisherTile.class, DEVICE_FISHER_TILE), deviceAugs, DeviceFisherTile.AUG_VALIDATOR, getFlag(ID_DEVICE_FISHER));
        registerAugmentableBlock(ID_DEVICE_COMPOSTER, () -> new TileBlockComposter(of(Material.WOOD).sound(SoundType.SCAFFOLDING).strength(2.5F), DeviceComposterTile.class, DEVICE_COMPOSTER_TILE), deviceAugs, DeviceComposterTile.AUG_VALIDATOR, getFlag(ID_DEVICE_COMPOSTER));
        registerAugmentableBlock(ID_DEVICE_SOIL_INFUSER, () -> new TileBlockActive4Way(of(Material.WOOD).sound(SoundType.SCAFFOLDING).strength(2.5F).lightLevel(lightValue(ACTIVE, 10)), DeviceSoilInfuserTile.class, DEVICE_SOIL_INFUSER_TILE), deviceAugs, DeviceSoilInfuserTile.AUG_VALIDATOR, getFlag(ID_DEVICE_SOIL_INFUSER));
        registerAugmentableBlock(ID_DEVICE_WATER_GEN, () -> new TileBlockActive4Way(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F), DeviceWaterGenTile.class, DEVICE_WATER_GEN_TILE), deviceAugs, DeviceWaterGenTile.AUG_VALIDATOR, getFlag(ID_DEVICE_WATER_GEN));
        registerAugmentableBlock(ID_DEVICE_ROCK_GEN, () -> new TileBlockActive4Way(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 14)), DeviceRockGenTile.class, DEVICE_ROCK_GEN_TILE), deviceAugs, DeviceRockGenTile.AUG_VALIDATOR, getFlag(ID_DEVICE_ROCK_GEN));
        registerAugmentableBlock(ID_DEVICE_COLLECTOR, () -> new TileBlockActive4Way(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F), DeviceCollectorTile.class, DEVICE_COLLECTOR_TILE), deviceAugs, DeviceCollectorTile.AUG_VALIDATOR, getFlag(ID_DEVICE_COLLECTOR));
        registerAugmentableBlock(ID_DEVICE_NULLIFIER, () -> new TileBlockActive4Way(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 7)), DeviceNullifierTile.class, DEVICE_NULLIFIER_TILE), deviceAugs, DeviceNullifierTile.AUG_VALIDATOR, getFlag(ID_DEVICE_NULLIFIER));
        registerAugmentableBlock(ID_DEVICE_POTION_DIFFUSER, () -> new TileBlockActive4Way(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F), DevicePotionDiffuserTile.class, DEVICE_POTION_DIFFUSER_TILE), deviceAugs, DevicePotionDiffuserTile.AUG_VALIDATOR, getFlag(ID_DEVICE_POTION_DIFFUSER));

        // registerBlock(ID_CHUNK_LOADER, () -> new TileBlockActive(of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(10.0F).harvestTool(ToolType.PICKAXE), DeviceChunkLoaderTile::new), getFlag(ID_CHUNK_LOADER));

        IntSupplier storageAugs = () -> ThermalCoreConfig.storageAugments;

        registerAugmentableBlock(ID_TINKER_BENCH, () -> new TileBlockCoFH(of(Material.WOOD).sound(SoundType.SCAFFOLDING).strength(2.5F), TinkerBenchTile.class, TINKER_BENCH_TILE), storageAugs, TinkerBenchTile.AUG_VALIDATOR, getFlag(ID_TINKER_BENCH));
        registerAugmentableBlock(ID_CHARGE_BENCH, () -> new TileBlockActive(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).lightLevel(lightValue(ACTIVE, 7)), ChargeBenchTile.class, CHARGE_BENCH_TILE), storageAugs, ChargeBenchTile.AUG_VALIDATOR, getFlag(ID_CHARGE_BENCH));

        BLOCKS.register(ID_ENERGY_CELL, () -> new TileBlockCell(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).noOcclusion(), EnergyCellTile.class, ENERGY_CELL_TILE));
        ITEMS.register(ID_ENERGY_CELL, (Supplier<Item>) () -> new EnergyCellBlockItem(BLOCKS.get(ID_ENERGY_CELL), new Item.Properties().tab(THERMAL_DEVICES)).setNumSlots(storageAugs).setAugValidator(ENERGY_STORAGE_VALIDATOR).setShowInGroups(getFlag(ID_ENERGY_CELL)));

        BLOCKS.register(ID_FLUID_CELL, () -> new TileBlockCell(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).noOcclusion(), FluidCellTile.class, FLUID_CELL_TILE));
        ITEMS.register(ID_FLUID_CELL, (Supplier<Item>) () -> new FluidCellBlockItem(BLOCKS.get(ID_FLUID_CELL), new Item.Properties().tab(THERMAL_DEVICES)).setNumSlots(storageAugs).setAugValidator(FLUID_STORAGE_VALIDATOR).setShowInGroups(getFlag(ID_FLUID_CELL)));

        //        BLOCKS.register(ID_ITEM_CELL, () -> new TileBlockCell(of(Material.METAL).sound(SoundType.LANTERN).strength(2.0F).harvestTool(ToolType.PICKAXE).noOcclusion(), ItemCellTile::new));
        //        ITEMS.register(ID_ITEM_CELL, (Supplier<Item>) () -> new ItemCellBlockItem(BLOCKS.get(ID_ITEM_CELL), new Item.Properties().tab(THERMAL_BLOCKS)).setNumSlots(storageAugs).setAugValidator(ITEM_STORAGE_VALIDATOR).setShowInGroups(getFlag(ID_ITEM_CELL)));
    }

    public static Block storageBlock() {

        return new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL)
                .strength(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }

    public static Block storageBlock(MaterialColor color) {

        return new Block(BlockBehaviour.Properties.of(Material.METAL, color)
                .strength(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }
    // endregion
}
