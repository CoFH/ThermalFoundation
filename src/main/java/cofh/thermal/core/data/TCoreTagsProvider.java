package cofh.thermal.core.data;

import cofh.lib.tags.BlockTagsCoFH;
import cofh.lib.tags.ItemTagsCoFH;
import cofh.thermal.lib.util.references.ThermalTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.lib.common.ThermalIDs.*;
import static net.minecraftforge.common.Tags.Items.*;

public class TCoreTagsProvider {

    public static class Block extends BlockTagsProvider {

        public Block(DataGenerator gen, ExistingFileHelper existingFileHelper) {

            super(gen, ID_THERMAL, existingFileHelper);
        }

        @Override
        public String getName() {

            return "Thermal Core: Block Tags";
        }

        @SuppressWarnings ("unchecked")
        @Override
        protected void addTags() {

            // region VANILLA BLOCKS
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_BAMBOO_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CHARCOAL_BLOCK));
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_GUNPOWDER_BLOCK));
            tag(BlockTags.MINEABLE_WITH_HOE).add(BLOCKS.get(ID_SUGAR_CANE_BLOCK));

            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_APPLE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_CARROT_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_POTATO_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_BEETROOT_BLOCK));
            // endregion

            tag(BlockTags.BEACON_BASE_BLOCKS).add(
                    BLOCKS.get(ID_SIGNALUM_BLOCK),
                    BLOCKS.get(ID_LUMIUM_BLOCK),
                    BLOCKS.get(ID_ENDERIUM_BLOCK)
            );

            // region STORAGE
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SIGNALUM_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_ENDERIUM_BLOCK));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_SIGNALUM_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_ENDERIUM_BLOCK));

            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_SAWDUST_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_COAL_COKE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_BITUMEN_BLOCK));
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_TAR_BLOCK));
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_ROSIN_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SLAG_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RICH_SLAG_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_ENDERIUM).add(BLOCKS.get(ID_ENDERIUM_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_LUMIUM).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_SIGNALUM).add(BLOCKS.get(ID_SIGNALUM_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_BAMBOO).add(BLOCKS.get(ID_BAMBOO_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_CHARCOAL).add(BLOCKS.get(ID_CHARCOAL_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_GUNPOWDER).add(BLOCKS.get(ID_GUNPOWDER_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE).add(BLOCKS.get(ID_SUGAR_CANE_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_BITUMEN).add(BLOCKS.get(ID_BITUMEN_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_COAL_COKE).add(BLOCKS.get(ID_COAL_COKE_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_SLAG).add(BLOCKS.get(ID_SLAG_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_TAR).add(BLOCKS.get(ID_TAR_BLOCK));

            tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
                    BlockTagsCoFH.STORAGE_BLOCKS_ENDERIUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_LUMIUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_SIGNALUM,

                    BlockTagsCoFH.STORAGE_BLOCKS_BAMBOO,
                    BlockTagsCoFH.STORAGE_BLOCKS_CHARCOAL,
                    BlockTagsCoFH.STORAGE_BLOCKS_GUNPOWDER,
                    BlockTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE,

                    BlockTagsCoFH.STORAGE_BLOCKS_BITUMEN,
                    BlockTagsCoFH.STORAGE_BLOCKS_COAL_COKE,
                    BlockTagsCoFH.STORAGE_BLOCKS_SLAG,
                    BlockTagsCoFH.STORAGE_BLOCKS_TAR
            );
            // endregion

            // region BUILDING BLOCKS
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_MACHINE_FRAME));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_ENERGY_CELL_FRAME));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_FLUID_CELL_FRAME));

            tag(ThermalTags.Blocks.HARDENED_GLASS).add(
                    BLOCKS.get(ID_OBSIDIAN_GLASS),
                    BLOCKS.get(ID_SIGNALUM_GLASS),
                    BLOCKS.get(ID_LUMIUM_GLASS),
                    BLOCKS.get(ID_ENDERIUM_GLASS)
            );

            tag(BlockTags.IMPERMEABLE).add(
                    BLOCKS.get(ID_OBSIDIAN_GLASS),
                    BLOCKS.get(ID_SIGNALUM_GLASS),
                    BLOCKS.get(ID_LUMIUM_GLASS),
                    BLOCKS.get(ID_ENDERIUM_GLASS)
            );

            tag(ThermalTags.Blocks.ROCKWOOL).add(
                    BLOCKS.get(ID_WHITE_ROCKWOOL),
                    BLOCKS.get(ID_ORANGE_ROCKWOOL),
                    BLOCKS.get(ID_MAGENTA_ROCKWOOL),
                    BLOCKS.get(ID_LIGHT_BLUE_ROCKWOOL),
                    BLOCKS.get(ID_YELLOW_ROCKWOOL),
                    BLOCKS.get(ID_LIME_ROCKWOOL),
                    BLOCKS.get(ID_PINK_ROCKWOOL),
                    BLOCKS.get(ID_GRAY_ROCKWOOL),
                    BLOCKS.get(ID_LIGHT_GRAY_ROCKWOOL),
                    BLOCKS.get(ID_CYAN_ROCKWOOL),
                    BLOCKS.get(ID_PURPLE_ROCKWOOL),
                    BLOCKS.get(ID_BLUE_ROCKWOOL),
                    BLOCKS.get(ID_BROWN_ROCKWOOL),
                    BLOCKS.get(ID_GREEN_ROCKWOOL),
                    BLOCKS.get(ID_RED_ROCKWOOL),
                    BLOCKS.get(ID_BLACK_ROCKWOOL)
            );

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_POLISHED_SLAG));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CHISELED_SLAG));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SLAG_BRICKS));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CRACKED_SLAG_BRICKS));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_POLISHED_RICH_SLAG));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CHISELED_RICH_SLAG));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RICH_SLAG_BRICKS));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CRACKED_RICH_SLAG_BRICKS));
            // endregion

            // region TILE BLOCKS
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_DEVICE_HIVE_EXTRACTOR));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_DEVICE_FISHER));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_DEVICE_COMPOSTER));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_DEVICE_SOIL_INFUSER));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_DEVICE_WATER_GEN));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_DEVICE_ROCK_GEN));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_DEVICE_COLLECTOR));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_DEVICE_NULLIFIER));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_DEVICE_POTION_DIFFUSER));

            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_TINKER_BENCH));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CHARGE_BENCH));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_ENERGY_CELL));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_FLUID_CELL));
            // endregion

            // addTFndTags();
        }

        @SuppressWarnings ("unchecked")
        protected void addTFndTags() {

            tag(BlockTags.BEACON_BASE_BLOCKS).add(
                    BLOCKS.get(ID_LEAD_BLOCK),
                    BLOCKS.get(ID_NICKEL_BLOCK),
                    BLOCKS.get(ID_SILVER_BLOCK),
                    BLOCKS.get(ID_TIN_BLOCK),

                    BLOCKS.get(ID_BRONZE_BLOCK),
                    BLOCKS.get(ID_CONSTANTAN_BLOCK),
                    BLOCKS.get(ID_ELECTRUM_BLOCK),
                    BLOCKS.get(ID_INVAR_BLOCK),

                    BLOCKS.get(ID_RUBY_BLOCK),
                    BLOCKS.get(ID_SAPPHIRE_BLOCK)
            );

            // region RESOURCES
            tag(BlockTagsCoFH.ORES_APATITE).add(BLOCKS.get(ID_APATITE_ORE));
            tag(BlockTagsCoFH.ORES_CINNABAR).add(BLOCKS.get(ID_CINNABAR_ORE));
            tag(BlockTagsCoFH.ORES_NITER).add(BLOCKS.get(ID_NITER_ORE));
            tag(BlockTagsCoFH.ORES_SULFUR).add(BLOCKS.get(ID_SULFUR_ORE));

            tag(BlockTagsCoFH.ORES_LEAD).add(BLOCKS.get(ID_LEAD_ORE));
            tag(BlockTagsCoFH.ORES_NICKEL).add(BLOCKS.get(ID_NICKEL_ORE));
            tag(BlockTagsCoFH.ORES_SILVER).add(BLOCKS.get(ID_SILVER_ORE));
            tag(BlockTagsCoFH.ORES_TIN).add(BLOCKS.get(ID_TIN_ORE));

            tag(BlockTagsCoFH.ORES_RUBY).add(BLOCKS.get(ID_RUBY_ORE));
            tag(BlockTagsCoFH.ORES_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_ORE));

            tag(BlockTagsCoFH.ORES_APATITE).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(BlockTagsCoFH.ORES_CINNABAR).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));
            tag(BlockTagsCoFH.ORES_NITER).add(BLOCKS.get(deepslate(ID_NITER_ORE)));
            tag(BlockTagsCoFH.ORES_SULFUR).add(BLOCKS.get(deepslate(ID_SULFUR_ORE)));

            tag(BlockTagsCoFH.ORES_LEAD).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(BlockTagsCoFH.ORES_NICKEL).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));
            tag(BlockTagsCoFH.ORES_SILVER).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(BlockTagsCoFH.ORES_TIN).add(BLOCKS.get(deepslate(ID_TIN_ORE)));

            tag(BlockTagsCoFH.ORES_RUBY).add(BLOCKS.get(deepslate(ID_RUBY_ORE)));
            tag(BlockTagsCoFH.ORES_SAPPHIRE).add(BLOCKS.get(deepslate(ID_SAPPHIRE_ORE)));

            tag(Tags.Blocks.ORES).addTags(
                    BlockTagsCoFH.ORES_APATITE,
                    BlockTagsCoFH.ORES_CINNABAR,
                    BlockTagsCoFH.ORES_NITER,
                    BlockTagsCoFH.ORES_SULFUR,

                    BlockTagsCoFH.ORES_LEAD,
                    BlockTagsCoFH.ORES_NICKEL,
                    BlockTagsCoFH.ORES_SILVER,
                    BlockTagsCoFH.ORES_TIN,

                    BlockTagsCoFH.ORES_RUBY,
                    BlockTagsCoFH.ORES_SAPPHIRE
            );

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_APATITE_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CINNABAR_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NITER_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SULFUR_ORE));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_LEAD_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NICKEL_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SILVER_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_TIN_ORE));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RUBY_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SAPPHIRE_ORE));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_NITER_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_SULFUR_ORE)));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_TIN_ORE)));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_RUBY_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_SAPPHIRE_ORE)));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_APATITE_ORE));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_CINNABAR_ORE));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_LEAD_ORE));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_SILVER_ORE));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_NICKEL_ORE));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_TIN_ORE));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_RUBY_ORE));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_SAPPHIRE_ORE));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_TIN_ORE)));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_RUBY_ORE)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_SAPPHIRE_ORE)));

            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_OIL_SAND));
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_OIL_RED_SAND));
            // endregion

            // region STORAGE
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_APATITE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CINNABAR_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NITER_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SULFUR_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_LEAD_BLOCK)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_NICKEL_BLOCK)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_SILVER_BLOCK)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_TIN_BLOCK)));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_TIN_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_INVAR_BLOCK));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_APATITE_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_CINNABAR_BLOCK));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(raw(ID_LEAD_BLOCK)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(raw(ID_NICKEL_BLOCK)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(raw(ID_SILVER_BLOCK)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(raw(ID_TIN_BLOCK)));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_TIN_BLOCK));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_INVAR_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_APATITE).add(BLOCKS.get(ID_APATITE_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_CINNABAR).add(BLOCKS.get(ID_CINNABAR_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_NITER).add(BLOCKS.get(ID_NITER_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_SULFUR).add(BLOCKS.get(ID_SULFUR_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_RAW_LEAD).add(BLOCKS.get(raw(ID_LEAD_BLOCK)));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_RAW_NICKEL).add(BLOCKS.get(raw(ID_NICKEL_BLOCK)));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_RAW_SILVER).add(BLOCKS.get(raw(ID_SILVER_BLOCK)));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_RAW_TIN).add(BLOCKS.get(raw(ID_TIN_BLOCK)));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_LEAD).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_NICKEL).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_SILVER).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_TIN).add(BLOCKS.get(ID_TIN_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_BRONZE).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_CONSTANTAN).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_ELECTRUM).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_INVAR).add(BLOCKS.get(ID_INVAR_BLOCK));

            tag(BlockTagsCoFH.STORAGE_BLOCKS_RUBY).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(BlockTagsCoFH.STORAGE_BLOCKS_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));


            tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
                    BlockTagsCoFH.STORAGE_BLOCKS_APATITE,
                    BlockTagsCoFH.STORAGE_BLOCKS_CINNABAR,
                    BlockTagsCoFH.STORAGE_BLOCKS_NITER,
                    BlockTagsCoFH.STORAGE_BLOCKS_SULFUR,

                    BlockTagsCoFH.STORAGE_BLOCKS_RAW_LEAD,
                    BlockTagsCoFH.STORAGE_BLOCKS_RAW_NICKEL,
                    BlockTagsCoFH.STORAGE_BLOCKS_RAW_SILVER,
                    BlockTagsCoFH.STORAGE_BLOCKS_RAW_TIN,

                    BlockTagsCoFH.STORAGE_BLOCKS_LEAD,
                    BlockTagsCoFH.STORAGE_BLOCKS_NICKEL,
                    BlockTagsCoFH.STORAGE_BLOCKS_SILVER,
                    BlockTagsCoFH.STORAGE_BLOCKS_TIN,

                    BlockTagsCoFH.STORAGE_BLOCKS_BRONZE,
                    BlockTagsCoFH.STORAGE_BLOCKS_CONSTANTAN,
                    BlockTagsCoFH.STORAGE_BLOCKS_ELECTRUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_INVAR,

                    BlockTagsCoFH.STORAGE_BLOCKS_RUBY,
                    BlockTagsCoFH.STORAGE_BLOCKS_SAPPHIRE
            );
            // endregion
        }

    }

    public static class Item extends ItemTagsProvider {

        public Item(DataGenerator gen, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {

            super(gen, blockTagProvider, ID_THERMAL, existingFileHelper);
        }

        @Override
        public String getName() {

            return "Thermal Core: Item Tags";
        }

        @SuppressWarnings ("unchecked")
        @Override
        protected void addTags() {

            copy(ThermalTags.Blocks.HARDENED_GLASS, ThermalTags.Items.HARDENED_GLASS);
            copy(ThermalTags.Blocks.ROCKWOOL, ThermalTags.Items.ROCKWOOL);

            copy(BlockTagsCoFH.STORAGE_BLOCKS_ENDERIUM, ItemTagsCoFH.STORAGE_BLOCKS_ENDERIUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_LUMIUM, ItemTagsCoFH.STORAGE_BLOCKS_LUMIUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SIGNALUM, ItemTagsCoFH.STORAGE_BLOCKS_SIGNALUM);

            copy(BlockTagsCoFH.STORAGE_BLOCKS_BAMBOO, ItemTagsCoFH.STORAGE_BLOCKS_BAMBOO);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_CHARCOAL, ItemTagsCoFH.STORAGE_BLOCKS_CHARCOAL);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_GUNPOWDER, ItemTagsCoFH.STORAGE_BLOCKS_GUNPOWDER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE, ItemTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE);

            copy(BlockTagsCoFH.STORAGE_BLOCKS_BITUMEN, ItemTagsCoFH.STORAGE_BLOCKS_BITUMEN);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_COAL_COKE, ItemTagsCoFH.STORAGE_BLOCKS_COAL_COKE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SLAG, ItemTagsCoFH.STORAGE_BLOCKS_SLAG);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_TAR, ItemTagsCoFH.STORAGE_BLOCKS_TAR);

            tag(Tags.Items.STORAGE_BLOCKS).addTags(
                    ItemTagsCoFH.STORAGE_BLOCKS_ENDERIUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_LUMIUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_SIGNALUM,

                    ItemTagsCoFH.STORAGE_BLOCKS_BAMBOO,
                    ItemTagsCoFH.STORAGE_BLOCKS_CHARCOAL,
                    ItemTagsCoFH.STORAGE_BLOCKS_GUNPOWDER,
                    ItemTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE,

                    ItemTagsCoFH.STORAGE_BLOCKS_BITUMEN,
                    ItemTagsCoFH.STORAGE_BLOCKS_COAL_COKE,
                    ItemTagsCoFH.STORAGE_BLOCKS_SLAG,
                    ItemTagsCoFH.STORAGE_BLOCKS_TAR
            );

            tag(ItemTagsCoFH.COINS_COPPER).add(ITEMS.get("copper_coin"));
            tag(ItemTagsCoFH.COINS_GOLD).add(ITEMS.get("gold_coin"));
            tag(ItemTagsCoFH.COINS_IRON).add(ITEMS.get("iron_coin"));
            tag(ItemTagsCoFH.COINS_NETHERITE).add(ITEMS.get("netherite_coin"));

            tag(ItemTagsCoFH.COINS_ENDERIUM).add(ITEMS.get("enderium_coin"));
            tag(ItemTagsCoFH.COINS_LUMIUM).add(ITEMS.get("lumium_coin"));
            tag(ItemTagsCoFH.COINS_SIGNALUM).add(ITEMS.get("signalum_coin"));

            tag(ItemTagsCoFH.COINS).addTags(
                    ItemTagsCoFH.COINS_COPPER,
                    ItemTagsCoFH.COINS_GOLD,
                    ItemTagsCoFH.COINS_IRON,
                    ItemTagsCoFH.COINS_NETHERITE,

                    ItemTagsCoFH.COINS_ENDERIUM,
                    ItemTagsCoFH.COINS_LUMIUM,
                    ItemTagsCoFH.COINS_SIGNALUM
            );

            tag(ItemTagsCoFH.DUSTS_COPPER).add(ITEMS.get("copper_dust"));
            tag(ItemTagsCoFH.DUSTS_GOLD).add(ITEMS.get("gold_dust"));
            tag(ItemTagsCoFH.DUSTS_IRON).add(ITEMS.get("iron_dust"));
            tag(ItemTagsCoFH.DUSTS_NETHERITE).add(ITEMS.get("netherite_dust"));

            tag(ItemTagsCoFH.DUSTS_DIAMOND).add(ITEMS.get("diamond_dust"));
            tag(ItemTagsCoFH.DUSTS_EMERALD).add(ITEMS.get("emerald_dust"));
            tag(ItemTagsCoFH.DUSTS_LAPIS).add(ITEMS.get("lapis_dust"));
            tag(ItemTagsCoFH.DUSTS_QUARTZ).add(ITEMS.get("quartz_dust"));

            tag(ItemTagsCoFH.DUSTS_ENDERIUM).add(ITEMS.get("enderium_dust"));
            tag(ItemTagsCoFH.DUSTS_LUMIUM).add(ITEMS.get("lumium_dust"));
            tag(ItemTagsCoFH.DUSTS_SIGNALUM).add(ITEMS.get("signalum_dust"));

            tag(DUSTS).addTags(
                    ItemTagsCoFH.DUSTS_COPPER,
                    ItemTagsCoFH.DUSTS_GOLD,
                    ItemTagsCoFH.DUSTS_IRON,
                    ItemTagsCoFH.DUSTS_NETHERITE,

                    ItemTagsCoFH.DUSTS_DIAMOND,
                    ItemTagsCoFH.DUSTS_EMERALD,
                    ItemTagsCoFH.DUSTS_LAPIS,
                    ItemTagsCoFH.DUSTS_QUARTZ,

                    ItemTagsCoFH.DUSTS_ENDERIUM,
                    ItemTagsCoFH.DUSTS_LUMIUM,
                    ItemTagsCoFH.DUSTS_SIGNALUM
            );

            tag(ItemTagsCoFH.GEARS_COPPER).add(ITEMS.get("copper_gear"));
            tag(ItemTagsCoFH.GEARS_GOLD).add(ITEMS.get("gold_gear"));
            tag(ItemTagsCoFH.GEARS_IRON).add(ITEMS.get("iron_gear"));
            tag(ItemTagsCoFH.GEARS_NETHERITE).add(ITEMS.get("netherite_gear"));

            tag(ItemTagsCoFH.GEARS_DIAMOND).add(ITEMS.get("diamond_gear"));
            tag(ItemTagsCoFH.GEARS_EMERALD).add(ITEMS.get("emerald_gear"));
            tag(ItemTagsCoFH.GEARS_LAPIS).add(ITEMS.get("lapis_gear"));
            tag(ItemTagsCoFH.GEARS_QUARTZ).add(ITEMS.get("quartz_gear"));

            tag(ItemTagsCoFH.GEARS_ENDERIUM).add(ITEMS.get("enderium_gear"));
            tag(ItemTagsCoFH.GEARS_LUMIUM).add(ITEMS.get("lumium_gear"));
            tag(ItemTagsCoFH.GEARS_SIGNALUM).add(ITEMS.get("signalum_gear"));

            tag(ItemTagsCoFH.GEARS).addTags(
                    ItemTagsCoFH.GEARS_COPPER,
                    ItemTagsCoFH.GEARS_GOLD,
                    ItemTagsCoFH.GEARS_IRON,
                    ItemTagsCoFH.GEARS_NETHERITE,

                    ItemTagsCoFH.GEARS_DIAMOND,
                    ItemTagsCoFH.GEARS_EMERALD,
                    ItemTagsCoFH.GEARS_LAPIS,
                    ItemTagsCoFH.GEARS_QUARTZ,

                    ItemTagsCoFH.GEARS_ENDERIUM,
                    ItemTagsCoFH.GEARS_LUMIUM,
                    ItemTagsCoFH.GEARS_SIGNALUM
            );

            tag(ItemTagsCoFH.INGOTS_ENDERIUM).add(ITEMS.get("enderium_ingot"));
            tag(ItemTagsCoFH.INGOTS_LUMIUM).add(ITEMS.get("lumium_ingot"));
            tag(ItemTagsCoFH.INGOTS_SIGNALUM).add(ITEMS.get("signalum_ingot"));

            tag(INGOTS).addTags(
                    ItemTagsCoFH.INGOTS_ENDERIUM,
                    ItemTagsCoFH.INGOTS_LUMIUM,
                    ItemTagsCoFH.INGOTS_SIGNALUM
            );

            tag(ItemTagsCoFH.NUGGETS_COPPER).add(ITEMS.get("copper_nugget"));
            tag(ItemTagsCoFH.NUGGETS_NETHERITE).add(ITEMS.get("netherite_nugget"));

            tag(ItemTagsCoFH.NUGGETS_ENDERIUM).add(ITEMS.get("enderium_nugget"));
            tag(ItemTagsCoFH.NUGGETS_LUMIUM).add(ITEMS.get("lumium_nugget"));
            tag(ItemTagsCoFH.NUGGETS_SIGNALUM).add(ITEMS.get("signalum_nugget"));

            tag(NUGGETS).addTags(
                    ItemTagsCoFH.NUGGETS_COPPER,
                    ItemTagsCoFH.NUGGETS_NETHERITE,

                    ItemTagsCoFH.NUGGETS_ENDERIUM,
                    ItemTagsCoFH.NUGGETS_LUMIUM,
                    ItemTagsCoFH.NUGGETS_SIGNALUM
            );

            tag(ItemTagsCoFH.PLATES_COPPER).add(ITEMS.get("copper_plate"));
            tag(ItemTagsCoFH.PLATES_GOLD).add(ITEMS.get("gold_plate"));
            tag(ItemTagsCoFH.PLATES_IRON).add(ITEMS.get("iron_plate"));
            tag(ItemTagsCoFH.PLATES_NETHERITE).add(ITEMS.get("netherite_plate"));

            tag(ItemTagsCoFH.PLATES_ENDERIUM).add(ITEMS.get("enderium_plate"));
            tag(ItemTagsCoFH.PLATES_LUMIUM).add(ITEMS.get("lumium_plate"));
            tag(ItemTagsCoFH.PLATES_SIGNALUM).add(ITEMS.get("signalum_plate"));

            tag(ItemTagsCoFH.PLATES).addTags(
                    ItemTagsCoFH.PLATES_COPPER,
                    ItemTagsCoFH.PLATES_GOLD,
                    ItemTagsCoFH.PLATES_IRON,
                    ItemTagsCoFH.PLATES_NETHERITE,
                    ItemTagsCoFH.PLATES_ENDERIUM,
                    ItemTagsCoFH.PLATES_LUMIUM,
                    ItemTagsCoFH.PLATES_SIGNALUM
            );

            tag(ItemTagsCoFH.TOOLS_WRENCH).add(ITEMS.get(ID_WRENCH));

            tag(ThermalTags.Items.BITUMEN).add(ITEMS.get("bitumen"));
            tag(ThermalTags.Items.COAL_COKE).add(ITEMS.get("coal_coke"));
            tag(ThermalTags.Items.SAWDUST).add(ITEMS.get("sawdust"));
            tag(ThermalTags.Items.SLAG).add(ITEMS.get("slag"));
            tag(ThermalTags.Items.TAR).add(ITEMS.get("tar"));
            tag(ItemTagsCoFH.DUSTS_WOOD).add(ITEMS.get("sawdust"));

            tag(ItemTagsCoFH.DUSTS_ENDER_PEARL).add(ITEMS.get("ender_pearl_dust"));

            tag(ItemTagsCoFH.LOCKS).add(ITEMS.get(ID_LOCK));
            tag(ItemTagsCoFH.SECURABLE).add(ITEMS.get(ID_SATCHEL));

            // addTFndTags();
        }

    }

    public static class Fluid extends FluidTagsProvider {

        public Fluid(DataGenerator gen, ExistingFileHelper existingFileHelper) {

            super(gen, ID_THERMAL, existingFileHelper);
        }

        @Override
        public String getName() {

            return "Thermal Core: Fluid Tags";
        }

        @Override
        protected void addTags() {

            tag(ThermalTags.Fluids.REDSTONE).add(FLUIDS.get(ID_FLUID_REDSTONE));
            tag(ThermalTags.Fluids.GLOWSTONE).add(FLUIDS.get(ID_FLUID_GLOWSTONE));
            tag(ThermalTags.Fluids.ENDER).add(FLUIDS.get(ID_FLUID_ENDER));

            tag(ThermalTags.Fluids.LATEX).add(FLUIDS.get(ID_FLUID_LATEX));

            tag(ThermalTags.Fluids.CREOSOTE).add(FLUIDS.get(ID_FLUID_CREOSOTE));
            tag(ThermalTags.Fluids.CRUDE_OIL).add(FLUIDS.get(ID_FLUID_CRUDE_OIL));
        }

    }

    public static class Entity extends EntityTypeTagsProvider {

        public Entity(DataGenerator gen, ExistingFileHelper existingFileHelper) {

            super(gen, ID_THERMAL, existingFileHelper);
        }

        @Override
        public String getName() {

            return "Thermal Core: Entity Type Tags";
        }

        @Override
        protected void addTags() {

            tag(EntityTypeTags.IMPACT_PROJECTILES).add(
                    ENTITIES.get(ID_BASALZ_PROJECTILE),
                    ENTITIES.get(ID_BLITZ_PROJECTILE),
                    ENTITIES.get(ID_BLIZZ_PROJECTILE)
            );
            tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(
                    ENTITIES.get(ID_BLIZZ)
            );
            tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(
                    ENTITIES.get(ID_BLIZZ)
            );
        }

    }

}
