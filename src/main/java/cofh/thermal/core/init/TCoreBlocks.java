package cofh.thermal.core.init;

import cofh.core.block.TileBlockActive;
import cofh.core.block.TileBlockActive4Way;
import cofh.core.block.TileBlockCoFH;
import cofh.core.item.BlockItemCoFH;
import cofh.lib.block.impl.*;
import cofh.thermal.core.entity.item.*;
import cofh.thermal.core.item.BlockItemEnergyCell;
import cofh.thermal.core.item.BlockItemFluidCell;
import cofh.thermal.core.tileentity.device.*;
import cofh.thermal.core.tileentity.storage.EnergyCellTile;
import cofh.thermal.core.tileentity.storage.FluidCellTile;
import cofh.thermal.core.tileentity.workbench.ChargeBenchTile;
import cofh.thermal.core.tileentity.workbench.TinkerBenchTile;
import cofh.thermal.lib.block.TileBlockCell;
import cofh.thermal.lib.common.ThermalConfig;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static cofh.lib.util.constants.Constants.ACTIVE;
import static cofh.lib.util.helpers.BlockHelper.lightValue;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.*;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;
import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_BLOCKS;
import static net.minecraft.block.AbstractBlock.Properties.create;
import static net.minecraft.block.AbstractBlock.Properties.from;

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
        registerTileEntities();
    }

    public static void setup() {

        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFireInfo(BLOCKS.get(ID_CHARCOAL_BLOCK), 5, 5);
        fire.setFireInfo(BLOCKS.get(ID_GUNPOWDER_BLOCK), 15, 100);
        fire.setFireInfo(BLOCKS.get(ID_SUGAR_CANE_BLOCK), 60, 20);
        fire.setFireInfo(BLOCKS.get(ID_BAMBOO_BLOCK), 60, 20);

        fire.setFireInfo(BLOCKS.get(ID_SAWDUST_BLOCK), 10, 10);
        fire.setFireInfo(BLOCKS.get(ID_COAL_COKE_BLOCK), 5, 5);
        fire.setFireInfo(BLOCKS.get(ID_BITUMEN_BLOCK), 5, 5);
        fire.setFireInfo(BLOCKS.get(ID_TAR_BLOCK), 5, 5);
        fire.setFireInfo(BLOCKS.get(ID_ROSIN_BLOCK), 5, 5);

        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_SLIME_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_REDSTONE_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_GLOWSTONE_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_ENDER_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);

        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_PHYTO_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);

        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_EARTH_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_FIRE_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_ICE_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_LIGHTNING_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);

        DispenserBlock.registerDispenseBehavior(BLOCKS.get(ID_NUKE_TNT), TNTBlockCoFH.DISPENSER_BEHAVIOR);
    }

    // region HELPERS
    private static void registerVanilla() {

        registerBlockAndItem(ID_CHARCOAL_BLOCK, () -> new Block(create(Material.WOOD, MaterialColor.BLACK).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).setRequiresTool()),
                () -> new BlockItemCoFH(BLOCKS.get(ID_CHARCOAL_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(16000).setShowInGroups(getFlag(FLAG_VANILLA_BLOCKS)));
        registerBlock(ID_GUNPOWDER_BLOCK, () -> new GunpowderBlock(create(Material.TNT, MaterialColor.GRAY).hardnessAndResistance(0.5F).sound(SoundType.SAND).harvestTool(ToolType.SHOVEL)), getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_SUGAR_CANE_BLOCK, () -> new RotatedPillarBlock(create(Material.ORGANIC, MaterialColor.FOLIAGE).hardnessAndResistance(1.0F).sound(SoundType.CROP).harvestTool(ToolType.HOE)) {

            @Override
            public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.onLivingFall(fallDistance, 0.6F);
            }
        }, getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_BAMBOO_BLOCK, () -> new RotatedPillarBlock(create(Material.ORGANIC, MaterialColor.FOLIAGE).hardnessAndResistance(1.0F).sound(SoundType.BAMBOO).harvestTool(ToolType.AXE)) {

            @Override
            public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.onLivingFall(fallDistance, 0.8F);
            }
        }, getFlag(FLAG_VANILLA_BLOCKS));

        registerBlock(ID_APPLE_BLOCK, () -> new Block(create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)), getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_CARROT_BLOCK, () -> new Block(create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)), getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_POTATO_BLOCK, () -> new Block(create(Material.WOOD, MaterialColor.BROWN_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)), getFlag(FLAG_VANILLA_BLOCKS));
        registerBlock(ID_BEETROOT_BLOCK, () -> new Block(create(Material.WOOD, MaterialColor.RED_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.SCAFFOLDING).harvestTool(ToolType.AXE)), getFlag(FLAG_VANILLA_BLOCKS));
    }

    private static void registerResources() {

        registerBlock(ID_APATITE_ORE, () -> new OreBlockCoFH(1).xp(0, 2), getFlag(FLAG_RESOURCE_APATITE));
        registerBlock(ID_CINNABAR_ORE, () -> new OreBlockCoFH(1).xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR));
        registerBlock(ID_NITER_ORE, () -> new OreBlockCoFH(1).xp(0, 2), getFlag(FLAG_RESOURCE_NITER));
        registerBlock(ID_SULFUR_ORE, () -> new OreBlockCoFH(1).xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR));

        registerBlock(ID_COPPER_ORE, () -> new OreBlockCoFH(1), getFlag(FLAG_RESOURCE_COPPER));
        registerBlock(ID_TIN_ORE, () -> new OreBlockCoFH(1), getFlag(FLAG_RESOURCE_TIN));
        registerBlock(ID_LEAD_ORE, () -> new OreBlockCoFH(2), getFlag(FLAG_RESOURCE_LEAD));
        registerBlock(ID_SILVER_ORE, () -> new OreBlockCoFH(2), getFlag(FLAG_RESOURCE_SILVER));
        registerBlock(ID_NICKEL_ORE, () -> new OreBlockCoFH(2), getFlag(FLAG_RESOURCE_NICKEL));

        registerBlock(ID_RUBY_ORE, () -> new OreBlockCoFH(2).xp(3, 7), getFlag(FLAG_RESOURCE_RUBY));
        registerBlock(ID_SAPPHIRE_ORE, () -> new OreBlockCoFH(2).xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE));

        registerBlockAndItem(ID_OIL_SAND, () -> new OreBlockCoFH(from(Blocks.SAND).harvestTool(ToolType.SHOVEL)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_SAND), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)));

        registerBlockAndItem(ID_OIL_RED_SAND, () -> new OreBlockCoFH(from(Blocks.RED_SAND).harvestTool(ToolType.SHOVEL)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_RED_SAND), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)));
    }

    private static void registerStorage() {

        registerBlock(ID_APATITE_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.LIGHT_BLUE_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).setRequiresTool()));
        registerBlock(ID_CINNABAR_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.RED_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).setRequiresTool()));
        registerBlock(ID_NITER_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).setRequiresTool()));
        registerBlockAndItem(ID_SULFUR_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.YELLOW_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).setRequiresTool()) {

            @Override
            public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {

                return side == Direction.UP;
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_SULFUR_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(12000));

        registerBlock(ID_COPPER_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_COPPER));
        registerBlock(ID_TIN_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_TIN));
        registerBlock(ID_LEAD_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_LEAD));
        registerBlock(ID_SILVER_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_SILVER));
        registerBlock(ID_NICKEL_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_NICKEL));

        registerBlock(ID_BRONZE_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_BRONZE));
        registerBlock(ID_ELECTRUM_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_ELECTRUM));
        registerBlock(ID_INVAR_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_INVAR));
        registerBlock(ID_CONSTANTAN_BLOCK, () -> storageBlock(1), getFlag(FLAG_RESOURCE_CONSTANTAN));

        registerBlock(ID_SIGNALUM_BLOCK, () -> new Block(create(Material.IRON, MaterialColor.RED).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().setLightLevel(lightValue(7)).notSolid()) {

            @Override
            public boolean canProvidePower(BlockState state) {

                return true;
            }

            @Override
            public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {

                return 15;
            }
        }, Rarity.UNCOMMON);
        registerBlock(ID_LUMIUM_BLOCK, () -> new Block(create(Material.IRON, MaterialColor.YELLOW).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().setLightLevel(lightValue(15)).notSolid()), Rarity.UNCOMMON);
        registerBlock(ID_ENDERIUM_BLOCK, () -> new Block(create(Material.IRON, MaterialColor.CYAN).hardnessAndResistance(25.0F, 30.0F).sound(SoundType.LODESTONE).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().setLightLevel(lightValue(3)).notSolid()), Rarity.UNCOMMON);

        registerBlock(ID_RUBY_BLOCK, () -> storageBlock(MaterialColor.RED, 1), getFlag(FLAG_RESOURCE_RUBY));
        registerBlock(ID_SAPPHIRE_BLOCK, () -> storageBlock(MaterialColor.BLUE, 1), getFlag(FLAG_RESOURCE_SAPPHIRE));

        registerBlockAndItem(ID_SAWDUST_BLOCK, () -> new FallingBlock(create(Material.WOOD).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.SAND).harvestTool(ToolType.SHOVEL)) {

            @OnlyIn(Dist.CLIENT)
            @Override
            public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {

                return 11507581;
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_SAWDUST_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(2400));

        registerBlockAndItem(ID_COAL_COKE_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(5.0F, 6.0F).setRequiresTool()),
                () -> new BlockItemCoFH(BLOCKS.get(ID_COAL_COKE_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(32000));

        registerBlockAndItem(ID_BITUMEN_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.NETHERRACK).setRequiresTool()),
                () -> new BlockItemCoFH(BLOCKS.get(ID_BITUMEN_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(16000));

        registerBlockAndItem(ID_TAR_BLOCK, () -> new Block(create(Material.CLAY, MaterialColor.BLACK).hardnessAndResistance(2.0F, 4.0F).speedFactor(0.8F).jumpFactor(0.8F).sound(SoundType.NETHERRACK).harvestTool(ToolType.SHOVEL)) {

            @Override
            public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.onLivingFall(fallDistance, 0.8F);
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_TAR_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(8000));

        registerBlockAndItem(ID_ROSIN_BLOCK, () -> new Block(create(Material.CLAY, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 4.0F).speedFactor(0.8F).jumpFactor(0.8F).sound(SoundType.HONEY).harvestTool(ToolType.SHOVEL)) {

            @Override
            public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {

                entityIn.onLivingFall(fallDistance, 0.8F);
            }
        }, () -> new BlockItemCoFH(BLOCKS.get(ID_ROSIN_BLOCK), new Item.Properties().group(THERMAL_BLOCKS)).setBurnTime(8000));

        registerBlock(ID_RUBBER_BLOCK, () -> new RubberBlock(create(Material.CLAY, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).jumpFactor(1.25F).sound(SoundType.FUNGUS)));
        registerBlock(ID_CURED_RUBBER_BLOCK, () -> new RubberBlock(create(Material.CLAY, MaterialColor.BLACK_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).jumpFactor(1.25F).sound(SoundType.FUNGUS)));
        registerBlock(ID_SLAG_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.BLACK_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.BASALT).harvestTool(ToolType.PICKAXE).setRequiresTool()));
        registerBlock(ID_RICH_SLAG_BLOCK, () -> new Block(create(Material.ROCK, MaterialColor.BLACK_TERRACOTTA).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.BASALT).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    }

    private static void registerBuildingBlocks() {

        registerBlock(ID_MACHINE_FRAME, () -> new Block(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).notSolid()), getFlag(ID_MACHINE_FRAME));
        registerBlock(ID_ENERGY_CELL_FRAME, () -> new Block(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).notSolid()), getFlag(ID_ENERGY_CELL_FRAME));
        registerBlock(ID_FLUID_CELL_FRAME, () -> new Block(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).notSolid()), getFlag(ID_FLUID_CELL_FRAME));

        registerBlock(ID_OBSIDIAN_GLASS, () -> new HardenedGlassBlock(create(Material.GLASS, MaterialColor.OBSIDIAN).hardnessAndResistance(5.0F, 1000.0F).sound(SoundType.GLASS).notSolid()));
        registerBlock(ID_SIGNALUM_GLASS, () -> new HardenedGlassBlock(create(Material.GLASS, MaterialColor.RED).hardnessAndResistance(5.0F, 1000.0F).sound(SoundType.GLASS).setLightLevel(lightValue(7)).notSolid()) {

            @Override
            public boolean canProvidePower(BlockState state) {

                return true;
            }

            @Override
            public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {

                return 15;
            }
        }, Rarity.UNCOMMON);
        registerBlock(ID_LUMIUM_GLASS, () -> new HardenedGlassBlock(create(Material.GLASS, MaterialColor.YELLOW).hardnessAndResistance(5.0F, 1000.0F).sound(SoundType.GLASS).setLightLevel(lightValue(15)).notSolid()), Rarity.UNCOMMON);
        registerBlock(ID_ENDERIUM_GLASS, () -> new HardenedGlassBlock(create(Material.GLASS, MaterialColor.CYAN).hardnessAndResistance(5.0F, 1000.0F).sound(SoundType.GLASS).setLightLevel(lightValue(3)).notSolid()), Rarity.UNCOMMON);

        registerBlock(ID_WHITE_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.SNOW).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_ORANGE_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_MAGENTA_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.MAGENTA).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_LIGHT_BLUE_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.LIGHT_BLUE).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_YELLOW_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.YELLOW).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_LIME_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.LIME).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_PINK_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.PINK).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_GRAY_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.GRAY).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_LIGHT_GRAY_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.LIGHT_GRAY).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_CYAN_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.CYAN).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_PURPLE_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.PURPLE).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_BLUE_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.BLUE).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_BROWN_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.BROWN).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_GREEN_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.GREEN).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_RED_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.RED).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
        registerBlock(ID_BLACK_ROCKWOOL, () -> new Block(create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.CLOTH)), getFlag(FLAG_ROCKWOOL));
    }

    private static void registerMisc() {

        registerBlock(ID_SLIME_TNT, () -> new TNTBlockCoFH(SlimeTNTEntity::new, create(Material.TNT, MaterialColor.GREEN_TERRACOTTA).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_BASIC_EXPLOSIVES));
        registerBlock(ID_REDSTONE_TNT, () -> new TNTBlockCoFH(RedstoneTNTEntity::new, create(Material.TNT, MaterialColor.RED).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_BASIC_EXPLOSIVES));
        registerBlock(ID_GLOWSTONE_TNT, () -> new TNTBlockCoFH(GlowstoneTNTEntity::new, create(Material.TNT, MaterialColor.YELLOW).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_BASIC_EXPLOSIVES));
        registerBlock(ID_ENDER_TNT, () -> new TNTBlockCoFH(EnderTNTEntity::new, create(Material.TNT, MaterialColor.GREEN).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_BASIC_EXPLOSIVES));

        registerBlock(ID_PHYTO_TNT, () -> new TNTBlockCoFH(PhytoTNTEntity::new, create(Material.TNT, MaterialColor.GREEN).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_PHYTOGRO_EXPLOSIVES));

        registerBlock(ID_EARTH_TNT, () -> new TNTBlockCoFH(EarthTNTEntity::new, create(Material.TNT, MaterialColor.OBSIDIAN).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerBlock(ID_FIRE_TNT, () -> new TNTBlockCoFH(FireTNTEntity::new, create(Material.TNT, MaterialColor.RED).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerBlock(ID_ICE_TNT, () -> new TNTBlockCoFH(IceTNTEntity::new, create(Material.TNT, MaterialColor.ICE).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerBlock(ID_LIGHTNING_TNT, () -> new TNTBlockCoFH(LightningTNTEntity::new, create(Material.TNT, MaterialColor.YELLOW).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), getFlag(FLAG_ELEMENTAL_EXPLOSIVES));

        registerBlock(ID_NUKE_TNT, () -> new TNTBlockCoFH(NukeTNTEntity::new, create(Material.TNT, MaterialColor.LIME_TERRACOTTA).hardnessAndResistance(0.0F).sound(SoundType.PLANT)), Rarity.UNCOMMON, getFlag(FLAG_NUCLEAR_EXPLOSIVES));
    }

    private static void registerTileBlocks() {

        IntSupplier deviceAugs = () -> ThermalConfig.deviceAugments;

        registerAugBlock(ID_DEVICE_HIVE_EXTRACTOR, () -> new TileBlockActive4Way(create(Material.WOOD).sound(SoundType.SCAFFOLDING).hardnessAndResistance(2.5F).harvestTool(ToolType.AXE), DeviceHiveExtractorTile::new), deviceAugs, DeviceHiveExtractorTile.AUG_VALIDATOR, getFlag(ID_DEVICE_HIVE_EXTRACTOR));
        registerAugBlock(ID_DEVICE_TREE_EXTRACTOR, () -> new TileBlockActive4Way(create(Material.WOOD).sound(SoundType.SCAFFOLDING).hardnessAndResistance(2.5F).harvestTool(ToolType.AXE), DeviceTreeExtractorTile::new), deviceAugs, DeviceTreeExtractorTile.AUG_VALIDATOR, getFlag(ID_DEVICE_TREE_EXTRACTOR));
        registerAugBlock(ID_DEVICE_FISHER, () -> new TileBlockActive4Way(create(Material.WOOD).sound(SoundType.SCAFFOLDING).hardnessAndResistance(2.5F).harvestTool(ToolType.AXE), DeviceFisherTile::new), deviceAugs, DeviceFisherTile.AUG_VALIDATOR, getFlag(ID_DEVICE_FISHER));
        registerAugBlock(ID_DEVICE_SOIL_INFUSER, () -> new TileBlockActive4Way(create(Material.WOOD).sound(SoundType.SCAFFOLDING).hardnessAndResistance(2.5F).harvestTool(ToolType.AXE).setLightLevel(lightValue(ACTIVE, 10)), DeviceSoilInfuserTile::new), deviceAugs, DeviceSoilInfuserTile.AUG_VALIDATOR, getFlag(ID_DEVICE_SOIL_INFUSER));

        registerAugBlock(ID_DEVICE_WATER_GEN, () -> new TileBlockActive4Way(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE), DeviceWaterGenTile::new), deviceAugs, DeviceWaterGenTile.AUG_VALIDATOR, getFlag(ID_DEVICE_WATER_GEN));
        registerAugBlock(ID_DEVICE_ROCK_GEN, () -> new TileBlockActive4Way(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).setLightLevel(lightValue(ACTIVE, 14)), DeviceRockGenTile::new), deviceAugs, DeviceRockGenTile.AUG_VALIDATOR, getFlag(ID_DEVICE_ROCK_GEN));
        registerAugBlock(ID_DEVICE_COLLECTOR, () -> new TileBlockActive4Way(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE), DeviceCollectorTile::new), deviceAugs, DeviceCollectorTile.AUG_VALIDATOR, getFlag(ID_DEVICE_COLLECTOR));
        registerAugBlock(ID_DEVICE_NULLIFIER, () -> new TileBlockActive4Way(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).setLightLevel(lightValue(ACTIVE, 7)), DeviceNullifierTile::new), deviceAugs, DeviceNullifierTile.AUG_VALIDATOR, getFlag(ID_DEVICE_NULLIFIER));
        registerAugBlock(ID_DEVICE_POTION_DIFFUSER, () -> new TileBlockActive4Way(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE), DevicePotionDiffuserTile::new), deviceAugs, DevicePotionDiffuserTile.AUG_VALIDATOR, getFlag(ID_DEVICE_POTION_DIFFUSER));

        IntSupplier storageAugs = () -> ThermalConfig.storageAugments;

        registerAugBlock(ID_TINKER_BENCH, () -> new TileBlockCoFH(create(Material.WOOD).sound(SoundType.SCAFFOLDING).hardnessAndResistance(2.5F).harvestTool(ToolType.AXE), TinkerBenchTile::new), storageAugs, STORAGE_VALIDATOR, getFlag(ID_TINKER_BENCH));
        registerAugBlock(ID_CHARGE_BENCH, () -> new TileBlockActive(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).setLightLevel(lightValue(ACTIVE, 7)), ChargeBenchTile::new), storageAugs, ChargeBenchTile.AUG_VALIDATOR, getFlag(ID_CHARGE_BENCH));

        BLOCKS.register(ID_ENERGY_CELL, () -> new TileBlockCell(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).notSolid(), EnergyCellTile::new));
        ITEMS.register(ID_ENERGY_CELL, (Supplier<Item>) () -> new BlockItemEnergyCell(BLOCKS.get(ID_ENERGY_CELL), new Item.Properties().group(THERMAL_BLOCKS)).setNumSlots(storageAugs).setAugValidator(ENERGY_VALIDATOR).setShowInGroups(getFlag(ID_ENERGY_CELL)));

        BLOCKS.register(ID_FLUID_CELL, () -> new TileBlockCell(create(Material.IRON).sound(SoundType.LANTERN).hardnessAndResistance(2.0F).harvestTool(ToolType.PICKAXE).notSolid(), FluidCellTile::new));
        ITEMS.register(ID_FLUID_CELL, (Supplier<Item>) () -> new BlockItemFluidCell(BLOCKS.get(ID_FLUID_CELL), new Item.Properties().group(THERMAL_BLOCKS)).setNumSlots(storageAugs).setAugValidator(FLUID_VALIDATOR).setShowInGroups(getFlag(ID_FLUID_CELL)));
    }

    private static void registerTileEntities() {

        TILE_ENTITIES.register(ID_DEVICE_HIVE_EXTRACTOR, () -> TileEntityType.Builder.create(DeviceHiveExtractorTile::new, DEVICE_HIVE_EXTRACTOR_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_TREE_EXTRACTOR, () -> TileEntityType.Builder.create(DeviceTreeExtractorTile::new, DEVICE_TREE_EXTRACTOR_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_FISHER, () -> TileEntityType.Builder.create(DeviceFisherTile::new, DEVICE_FISHER_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_SOIL_INFUSER, () -> TileEntityType.Builder.create(DeviceSoilInfuserTile::new, DEVICE_SOIL_INFUSER_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_WATER_GEN, () -> TileEntityType.Builder.create(DeviceWaterGenTile::new, DEVICE_WATER_GEN_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_ROCK_GEN, () -> TileEntityType.Builder.create(DeviceRockGenTile::new, DEVICE_ROCK_GEN_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_COLLECTOR, () -> TileEntityType.Builder.create(DeviceCollectorTile::new, DEVICE_COLLECTOR_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_NULLIFIER, () -> TileEntityType.Builder.create(DeviceNullifierTile::new, DEVICE_NULLIFIER_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_POTION_DIFFUSER, () -> TileEntityType.Builder.create(DevicePotionDiffuserTile::new, DEVICE_POTION_DIFFUSER_BLOCK).build(null));

        TILE_ENTITIES.register(ID_TINKER_BENCH, () -> TileEntityType.Builder.create(TinkerBenchTile::new, TINKER_BENCH_BLOCK).build(null));
        TILE_ENTITIES.register(ID_CHARGE_BENCH, () -> TileEntityType.Builder.create(ChargeBenchTile::new, CHARGE_BENCH_BLOCK).build(null));

        TILE_ENTITIES.register(ID_ENERGY_CELL, () -> TileEntityType.Builder.create(EnergyCellTile::new, ENERGY_CELL_BLOCK).build(null));
        TILE_ENTITIES.register(ID_FLUID_CELL, () -> TileEntityType.Builder.create(FluidCellTile::new, FLUID_CELL_BLOCK).build(null));
    }

    private static Block storageBlock(int harvestLevel) {

        return new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON)
                .hardnessAndResistance(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .harvestLevel(harvestLevel)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    private static Block storageBlock(MaterialColor color, int harvestLevel) {

        return new Block(AbstractBlock.Properties.create(Material.IRON, color)
                .hardnessAndResistance(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .harvestLevel(harvestLevel)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }
    // endregion
}
