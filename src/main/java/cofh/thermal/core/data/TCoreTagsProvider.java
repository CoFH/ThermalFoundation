package cofh.thermal.core.data;

import cofh.lib.util.references.CoFHTags;
import cofh.thermal.lib.util.references.ThermalTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
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

        @SuppressWarnings ("unchecked") // eww, thanks Forge for pushing your Generics varargs warnings down to us.
        @Override
        protected void addTags() {

            // region VANILLA BLOCKS
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CHARCOAL_BLOCK));

            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_GUNPOWDER_BLOCK));
            tag(BlockTags.MINEABLE_WITH_HOE).add(BLOCKS.get(ID_SUGAR_CANE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_BAMBOO_BLOCK));

            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_APPLE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_CARROT_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_POTATO_BLOCK));
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_BEETROOT_BLOCK));
            // endregion

            tag(BlockTags.BEACON_BASE_BLOCKS).add(
                    BLOCKS.get(ID_TIN_BLOCK),
                    BLOCKS.get(ID_LEAD_BLOCK),
                    BLOCKS.get(ID_SILVER_BLOCK),
                    BLOCKS.get(ID_NICKEL_BLOCK),

                    BLOCKS.get(ID_RUBY_BLOCK),
                    BLOCKS.get(ID_SAPPHIRE_BLOCK),

                    BLOCKS.get(ID_BRONZE_BLOCK),
                    BLOCKS.get(ID_ELECTRUM_BLOCK),
                    BLOCKS.get(ID_INVAR_BLOCK),
                    BLOCKS.get(ID_CONSTANTAN_BLOCK),

                    BLOCKS.get(ID_SIGNALUM_BLOCK),
                    BLOCKS.get(ID_LUMIUM_BLOCK),
                    BLOCKS.get(ID_ENDERIUM_BLOCK)
            );

            // region RESOURCES
            tag(CoFHTags.Blocks.ORES_APATITE).add(BLOCKS.get(ID_APATITE_ORE));
            tag(CoFHTags.Blocks.ORES_CINNABAR).add(BLOCKS.get(ID_CINNABAR_ORE));
            tag(CoFHTags.Blocks.ORES_LEAD).add(BLOCKS.get(ID_LEAD_ORE));
            tag(CoFHTags.Blocks.ORES_NICKEL).add(BLOCKS.get(ID_NICKEL_ORE));
            tag(CoFHTags.Blocks.ORES_NITER).add(BLOCKS.get(ID_NITER_ORE));
            tag(CoFHTags.Blocks.ORES_RUBY).add(BLOCKS.get(ID_RUBY_ORE));
            tag(CoFHTags.Blocks.ORES_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_ORE));
            tag(CoFHTags.Blocks.ORES_SILVER).add(BLOCKS.get(ID_SILVER_ORE));
            tag(CoFHTags.Blocks.ORES_SULFUR).add(BLOCKS.get(ID_SULFUR_ORE));
            tag(CoFHTags.Blocks.ORES_TIN).add(BLOCKS.get(ID_TIN_ORE));

            tag(CoFHTags.Blocks.ORES_APATITE).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(CoFHTags.Blocks.ORES_CINNABAR).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));
            tag(CoFHTags.Blocks.ORES_LEAD).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(CoFHTags.Blocks.ORES_NICKEL).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));
            tag(CoFHTags.Blocks.ORES_NITER).add(BLOCKS.get(deepslate(ID_NITER_ORE)));
            tag(CoFHTags.Blocks.ORES_RUBY).add(BLOCKS.get(deepslate(ID_RUBY_ORE)));
            tag(CoFHTags.Blocks.ORES_SAPPHIRE).add(BLOCKS.get(deepslate(ID_SAPPHIRE_ORE)));
            tag(CoFHTags.Blocks.ORES_SILVER).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(CoFHTags.Blocks.ORES_SULFUR).add(BLOCKS.get(deepslate(ID_SULFUR_ORE)));
            tag(CoFHTags.Blocks.ORES_TIN).add(BLOCKS.get(deepslate(ID_TIN_ORE)));

            tag(Tags.Blocks.ORES).addTags(
                    CoFHTags.Blocks.ORES_APATITE,
                    CoFHTags.Blocks.ORES_CINNABAR,
                    CoFHTags.Blocks.ORES_LEAD,
                    CoFHTags.Blocks.ORES_NICKEL,
                    CoFHTags.Blocks.ORES_NITER,
                    CoFHTags.Blocks.ORES_RUBY,
                    CoFHTags.Blocks.ORES_SAPPHIRE,
                    CoFHTags.Blocks.ORES_SILVER,
                    CoFHTags.Blocks.ORES_SULFUR,
                    CoFHTags.Blocks.ORES_TIN
            );

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_APATITE_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CINNABAR_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NITER_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SULFUR_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_TIN_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_LEAD_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SILVER_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NICKEL_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RUBY_ORE));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SAPPHIRE_ORE));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_NITER_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_SULFUR_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_TIN_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_RUBY_ORE)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(deepslate(ID_SAPPHIRE_ORE)));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_LEAD_BLOCK)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_NICKEL_BLOCK)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_SILVER_BLOCK)));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(raw(ID_TIN_BLOCK)));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_APATITE_ORE));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_CINNABAR_ORE));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_NITER_ORE));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_SULFUR_ORE));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_TIN_ORE));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_LEAD_ORE));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_SILVER_ORE));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_NICKEL_ORE));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_RUBY_ORE));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_SAPPHIRE_ORE));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_NITER_ORE)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_SULFUR_ORE)));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(deepslate(ID_TIN_ORE)));

            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));

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

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_TIN_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_INVAR_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));

            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_TIN_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_INVAR_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SIGNALUM_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_ENDERIUM_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_SIGNALUM_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            tag(BlockTags.NEEDS_IRON_TOOL).add(BLOCKS.get(ID_ENDERIUM_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(BlockTags.NEEDS_STONE_TOOL).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));

            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_SAWDUST_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_COAL_COKE_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_BITUMEN_BLOCK));

            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_TAR_BLOCK));
            tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BLOCKS.get(ID_ROSIN_BLOCK));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_SLAG_BLOCK));
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_RICH_SLAG_BLOCK));

            tag(CoFHTags.Blocks.STORAGE_BLOCKS_APATITE).add(BLOCKS.get(ID_APATITE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_BAMBOO).add(BLOCKS.get(ID_BAMBOO_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_BITUMEN).add(BLOCKS.get(ID_BITUMEN_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_BRONZE).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_CHARCOAL).add(BLOCKS.get(ID_CHARCOAL_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_CINNABAR).add(BLOCKS.get(ID_CINNABAR_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_COAL_COKE).add(BLOCKS.get(ID_COAL_COKE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_CONSTANTAN).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_ELECTRUM).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_ENDERIUM).add(BLOCKS.get(ID_ENDERIUM_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_GUNPOWDER).add(BLOCKS.get(ID_GUNPOWDER_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_INVAR).add(BLOCKS.get(ID_INVAR_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_LEAD).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_LUMIUM).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_NICKEL).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_NITER).add(BLOCKS.get(ID_NITER_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_LEAD).add(BLOCKS.get(raw(ID_LEAD_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_NICKEL).add(BLOCKS.get(raw(ID_NICKEL_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_SILVER).add(BLOCKS.get(raw(ID_SILVER_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_TIN).add(BLOCKS.get(raw(ID_TIN_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RUBY).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SIGNALUM).add(BLOCKS.get(ID_SIGNALUM_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SILVER).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SLAG).add(BLOCKS.get(ID_SLAG_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SUGAR_CANE).add(BLOCKS.get(ID_SUGAR_CANE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SULFUR).add(BLOCKS.get(ID_SULFUR_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_TAR).add(BLOCKS.get(ID_TAR_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_TIN).add(BLOCKS.get(ID_TIN_BLOCK));

            tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
                    CoFHTags.Blocks.STORAGE_BLOCKS_APATITE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_BAMBOO,
                    CoFHTags.Blocks.STORAGE_BLOCKS_BITUMEN,
                    CoFHTags.Blocks.STORAGE_BLOCKS_BRONZE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_CHARCOAL,
                    CoFHTags.Blocks.STORAGE_BLOCKS_CINNABAR,
                    CoFHTags.Blocks.STORAGE_BLOCKS_COAL_COKE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_CONSTANTAN,
                    CoFHTags.Blocks.STORAGE_BLOCKS_ELECTRUM,
                    CoFHTags.Blocks.STORAGE_BLOCKS_ENDERIUM,
                    CoFHTags.Blocks.STORAGE_BLOCKS_GUNPOWDER,
                    CoFHTags.Blocks.STORAGE_BLOCKS_INVAR,
                    CoFHTags.Blocks.STORAGE_BLOCKS_LEAD,
                    CoFHTags.Blocks.STORAGE_BLOCKS_LUMIUM,
                    CoFHTags.Blocks.STORAGE_BLOCKS_NICKEL,
                    CoFHTags.Blocks.STORAGE_BLOCKS_NITER,
                    CoFHTags.Blocks.STORAGE_BLOCKS_RUBY,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SAPPHIRE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SIGNALUM,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SILVER,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SLAG,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SUGAR_CANE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SULFUR,
                    CoFHTags.Blocks.STORAGE_BLOCKS_TAR,
                    CoFHTags.Blocks.STORAGE_BLOCKS_TIN
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
            tag(BlockTags.MINEABLE_WITH_AXE).add(BLOCKS.get(ID_DEVICE_SOIL_INFUSER));

            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BLOCKS.get(ID_DEVICE_SOIL_INFUSER));
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

        @Override
        protected void addTags() {

            copy(ThermalTags.Blocks.HARDENED_GLASS, ThermalTags.Items.HARDENED_GLASS);
            copy(ThermalTags.Blocks.ROCKWOOL, ThermalTags.Items.ROCKWOOL);

            copy(CoFHTags.Blocks.ORES_APATITE, CoFHTags.Items.ORES_APATITE);
            copy(CoFHTags.Blocks.ORES_CINNABAR, CoFHTags.Items.ORES_CINNABAR);
            copy(CoFHTags.Blocks.ORES_LEAD, CoFHTags.Items.ORES_LEAD);
            copy(CoFHTags.Blocks.ORES_NICKEL, CoFHTags.Items.ORES_NICKEL);
            copy(CoFHTags.Blocks.ORES_NITER, CoFHTags.Items.ORES_NITER);
            copy(CoFHTags.Blocks.ORES_RUBY, CoFHTags.Items.ORES_RUBY);
            copy(CoFHTags.Blocks.ORES_SAPPHIRE, CoFHTags.Items.ORES_SAPPHIRE);
            copy(CoFHTags.Blocks.ORES_SILVER, CoFHTags.Items.ORES_SILVER);
            copy(CoFHTags.Blocks.ORES_SULFUR, CoFHTags.Items.ORES_SULFUR);
            copy(CoFHTags.Blocks.ORES_TIN, CoFHTags.Items.ORES_TIN);

            tag(Tags.Items.ORES).addTags(
                    CoFHTags.Items.ORES_APATITE,
                    CoFHTags.Items.ORES_CINNABAR,
                    CoFHTags.Items.ORES_LEAD,
                    CoFHTags.Items.ORES_NICKEL,
                    CoFHTags.Items.ORES_NITER,
                    CoFHTags.Items.ORES_RUBY,
                    CoFHTags.Items.ORES_SAPPHIRE,
                    CoFHTags.Items.ORES_SILVER,
                    CoFHTags.Items.ORES_SULFUR,
                    CoFHTags.Items.ORES_TIN
            );

            copy(CoFHTags.Blocks.STORAGE_BLOCKS_APATITE, CoFHTags.Items.STORAGE_BLOCKS_APATITE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_BAMBOO, CoFHTags.Items.STORAGE_BLOCKS_BAMBOO);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_BITUMEN, CoFHTags.Items.STORAGE_BLOCKS_BITUMEN);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_BRONZE, CoFHTags.Items.STORAGE_BLOCKS_BRONZE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_CHARCOAL, CoFHTags.Items.STORAGE_BLOCKS_CHARCOAL);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_CINNABAR, CoFHTags.Items.STORAGE_BLOCKS_CINNABAR);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_COAL_COKE, CoFHTags.Items.STORAGE_BLOCKS_COAL_COKE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_CONSTANTAN, CoFHTags.Items.STORAGE_BLOCKS_CONSTANTAN);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_ELECTRUM, CoFHTags.Items.STORAGE_BLOCKS_ELECTRUM);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_ENDERIUM, CoFHTags.Items.STORAGE_BLOCKS_ENDERIUM);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_GUNPOWDER, CoFHTags.Items.STORAGE_BLOCKS_GUNPOWDER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_INVAR, CoFHTags.Items.STORAGE_BLOCKS_INVAR);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_LEAD, CoFHTags.Items.STORAGE_BLOCKS_LEAD);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_LUMIUM, CoFHTags.Items.STORAGE_BLOCKS_LUMIUM);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_NICKEL, CoFHTags.Items.STORAGE_BLOCKS_NICKEL);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_NITER, CoFHTags.Items.STORAGE_BLOCKS_NITER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_LEAD, CoFHTags.Items.STORAGE_BLOCKS_RAW_LEAD);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_NICKEL, CoFHTags.Items.STORAGE_BLOCKS_RAW_NICKEL);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, CoFHTags.Items.STORAGE_BLOCKS_RAW_SILVER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_TIN, CoFHTags.Items.STORAGE_BLOCKS_RAW_TIN);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RUBY, CoFHTags.Items.STORAGE_BLOCKS_RUBY);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SAPPHIRE, CoFHTags.Items.STORAGE_BLOCKS_SAPPHIRE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SIGNALUM, CoFHTags.Items.STORAGE_BLOCKS_SIGNALUM);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SILVER, CoFHTags.Items.STORAGE_BLOCKS_SILVER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SLAG, CoFHTags.Items.STORAGE_BLOCKS_SLAG);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SUGAR_CANE, CoFHTags.Items.STORAGE_BLOCKS_SUGAR_CANE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SULFUR, CoFHTags.Items.STORAGE_BLOCKS_SULFUR);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_TAR, CoFHTags.Items.STORAGE_BLOCKS_TAR);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_TIN, CoFHTags.Items.STORAGE_BLOCKS_TIN);

            tag(Tags.Items.STORAGE_BLOCKS).addTags(
                    CoFHTags.Items.STORAGE_BLOCKS_APATITE,
                    CoFHTags.Items.STORAGE_BLOCKS_BAMBOO,
                    CoFHTags.Items.STORAGE_BLOCKS_BRONZE,
                    CoFHTags.Items.STORAGE_BLOCKS_CHARCOAL,
                    CoFHTags.Items.STORAGE_BLOCKS_CINNABAR,
                    CoFHTags.Items.STORAGE_BLOCKS_COAL_COKE,
                    CoFHTags.Items.STORAGE_BLOCKS_CONSTANTAN,
                    CoFHTags.Items.STORAGE_BLOCKS_ELECTRUM,
                    CoFHTags.Items.STORAGE_BLOCKS_ENDERIUM,
                    CoFHTags.Items.STORAGE_BLOCKS_GUNPOWDER,
                    CoFHTags.Items.STORAGE_BLOCKS_INVAR,
                    CoFHTags.Items.STORAGE_BLOCKS_LEAD,
                    CoFHTags.Items.STORAGE_BLOCKS_LUMIUM,
                    CoFHTags.Items.STORAGE_BLOCKS_NICKEL,
                    CoFHTags.Items.STORAGE_BLOCKS_NITER,
                    CoFHTags.Items.STORAGE_BLOCKS_RUBY,
                    CoFHTags.Items.STORAGE_BLOCKS_SAPPHIRE,
                    CoFHTags.Items.STORAGE_BLOCKS_SIGNALUM,
                    CoFHTags.Items.STORAGE_BLOCKS_SILVER,
                    CoFHTags.Items.STORAGE_BLOCKS_SUGAR_CANE,
                    CoFHTags.Items.STORAGE_BLOCKS_SULFUR,
                    CoFHTags.Items.STORAGE_BLOCKS_TIN
            );

            tag(CoFHTags.Items.COINS_BRONZE).add(ITEMS.get("bronze_coin"));
            tag(CoFHTags.Items.COINS_CONSTANTAN).add(ITEMS.get("constantan_coin"));
            tag(CoFHTags.Items.COINS_COPPER).add(ITEMS.get("copper_coin"));
            tag(CoFHTags.Items.COINS_ELECTRUM).add(ITEMS.get("electrum_coin"));
            tag(CoFHTags.Items.COINS_ENDERIUM).add(ITEMS.get("enderium_coin"));
            tag(CoFHTags.Items.COINS_GOLD).add(ITEMS.get("gold_coin"));
            tag(CoFHTags.Items.COINS_INVAR).add(ITEMS.get("invar_coin"));
            tag(CoFHTags.Items.COINS_IRON).add(ITEMS.get("iron_coin"));
            tag(CoFHTags.Items.COINS_LEAD).add(ITEMS.get("lead_coin"));
            tag(CoFHTags.Items.COINS_LUMIUM).add(ITEMS.get("lumium_coin"));
            tag(CoFHTags.Items.COINS_NETHERITE).add(ITEMS.get("netherite_coin"));
            tag(CoFHTags.Items.COINS_NICKEL).add(ITEMS.get("nickel_coin"));
            tag(CoFHTags.Items.COINS_SIGNALUM).add(ITEMS.get("signalum_coin"));
            tag(CoFHTags.Items.COINS_SILVER).add(ITEMS.get("silver_coin"));
            tag(CoFHTags.Items.COINS_TIN).add(ITEMS.get("tin_coin"));

            tag(CoFHTags.Items.COINS).addTags(
                    CoFHTags.Items.COINS_BRONZE,
                    CoFHTags.Items.COINS_CONSTANTAN,
                    CoFHTags.Items.COINS_COPPER,
                    CoFHTags.Items.COINS_ELECTRUM,
                    CoFHTags.Items.COINS_ENDERIUM,
                    CoFHTags.Items.COINS_GOLD,
                    CoFHTags.Items.COINS_INVAR,
                    CoFHTags.Items.COINS_IRON,
                    CoFHTags.Items.COINS_LEAD,
                    CoFHTags.Items.COINS_LUMIUM,
                    CoFHTags.Items.COINS_NETHERITE,
                    CoFHTags.Items.COINS_NICKEL,
                    CoFHTags.Items.COINS_SIGNALUM,
                    CoFHTags.Items.COINS_SILVER,
                    CoFHTags.Items.COINS_TIN
            );

            tag(CoFHTags.Items.DUSTS_BRONZE).add(ITEMS.get("bronze_dust"));
            tag(CoFHTags.Items.DUSTS_CONSTANTAN).add(ITEMS.get("constantan_dust"));
            tag(CoFHTags.Items.DUSTS_COPPER).add(ITEMS.get("copper_dust"));
            tag(CoFHTags.Items.DUSTS_DIAMOND).add(ITEMS.get("diamond_dust"));
            tag(CoFHTags.Items.DUSTS_ELECTRUM).add(ITEMS.get("electrum_dust"));
            tag(CoFHTags.Items.DUSTS_EMERALD).add(ITEMS.get("emerald_dust"));
            tag(CoFHTags.Items.DUSTS_ENDERIUM).add(ITEMS.get("enderium_dust"));
            tag(CoFHTags.Items.DUSTS_GOLD).add(ITEMS.get("gold_dust"));
            tag(CoFHTags.Items.DUSTS_INVAR).add(ITEMS.get("invar_dust"));
            tag(CoFHTags.Items.DUSTS_IRON).add(ITEMS.get("iron_dust"));
            tag(CoFHTags.Items.DUSTS_LAPIS).add(ITEMS.get("lapis_dust"));
            tag(CoFHTags.Items.DUSTS_LEAD).add(ITEMS.get("lead_dust"));
            tag(CoFHTags.Items.DUSTS_LUMIUM).add(ITEMS.get("lumium_dust"));
            tag(CoFHTags.Items.DUSTS_NETHERITE).add(ITEMS.get("netherite_dust"));
            tag(CoFHTags.Items.DUSTS_NICKEL).add(ITEMS.get("nickel_dust"));
            tag(CoFHTags.Items.DUSTS_QUARTZ).add(ITEMS.get("quartz_dust"));
            tag(CoFHTags.Items.DUSTS_RUBY).add(ITEMS.get("ruby_dust"));
            tag(CoFHTags.Items.DUSTS_SAPPHIRE).add(ITEMS.get("sapphire_dust"));
            tag(CoFHTags.Items.DUSTS_SIGNALUM).add(ITEMS.get("signalum_dust"));
            tag(CoFHTags.Items.DUSTS_SILVER).add(ITEMS.get("silver_dust"));
            tag(CoFHTags.Items.DUSTS_TIN).add(ITEMS.get("tin_dust"));

            tag(DUSTS).addTags(
                    CoFHTags.Items.DUSTS_APATITE,
                    CoFHTags.Items.DUSTS_BRONZE,
                    CoFHTags.Items.DUSTS_CINNABAR,
                    CoFHTags.Items.DUSTS_CONSTANTAN,
                    CoFHTags.Items.DUSTS_COPPER,
                    CoFHTags.Items.DUSTS_DIAMOND,
                    CoFHTags.Items.DUSTS_ELECTRUM,
                    CoFHTags.Items.DUSTS_EMERALD,
                    CoFHTags.Items.DUSTS_ENDER_PEARL,
                    CoFHTags.Items.DUSTS_ENDERIUM,
                    CoFHTags.Items.DUSTS_GOLD,
                    CoFHTags.Items.DUSTS_INVAR,
                    CoFHTags.Items.DUSTS_IRON,
                    CoFHTags.Items.DUSTS_LAPIS,
                    CoFHTags.Items.DUSTS_LEAD,
                    CoFHTags.Items.DUSTS_LUMIUM,
                    CoFHTags.Items.DUSTS_NETHERITE,
                    CoFHTags.Items.DUSTS_NICKEL,
                    CoFHTags.Items.DUSTS_NITER,
                    CoFHTags.Items.DUSTS_QUARTZ,
                    CoFHTags.Items.DUSTS_RUBY,
                    CoFHTags.Items.DUSTS_SAPPHIRE,
                    CoFHTags.Items.DUSTS_SIGNALUM,
                    CoFHTags.Items.DUSTS_SILVER,
                    CoFHTags.Items.DUSTS_SULFUR,
                    CoFHTags.Items.DUSTS_TIN
            );

            tag(CoFHTags.Items.GEARS_BRONZE).add(ITEMS.get("bronze_gear"));
            tag(CoFHTags.Items.GEARS_CONSTANTAN).add(ITEMS.get("constantan_gear"));
            tag(CoFHTags.Items.GEARS_COPPER).add(ITEMS.get("copper_gear"));
            tag(CoFHTags.Items.GEARS_DIAMOND).add(ITEMS.get("diamond_gear"));
            tag(CoFHTags.Items.GEARS_ELECTRUM).add(ITEMS.get("electrum_gear"));
            tag(CoFHTags.Items.GEARS_EMERALD).add(ITEMS.get("emerald_gear"));
            tag(CoFHTags.Items.GEARS_ENDERIUM).add(ITEMS.get("enderium_gear"));
            tag(CoFHTags.Items.GEARS_GOLD).add(ITEMS.get("gold_gear"));
            tag(CoFHTags.Items.GEARS_INVAR).add(ITEMS.get("invar_gear"));
            tag(CoFHTags.Items.GEARS_IRON).add(ITEMS.get("iron_gear"));
            tag(CoFHTags.Items.GEARS_LAPIS).add(ITEMS.get("lapis_gear"));
            tag(CoFHTags.Items.GEARS_LEAD).add(ITEMS.get("lead_gear"));
            tag(CoFHTags.Items.GEARS_LUMIUM).add(ITEMS.get("lumium_gear"));
            tag(CoFHTags.Items.GEARS_NETHERITE).add(ITEMS.get("netherite_gear"));
            tag(CoFHTags.Items.GEARS_NICKEL).add(ITEMS.get("nickel_gear"));
            tag(CoFHTags.Items.GEARS_QUARTZ).add(ITEMS.get("quartz_gear"));
            tag(CoFHTags.Items.GEARS_RUBY).add(ITEMS.get("ruby_gear"));
            tag(CoFHTags.Items.GEARS_SAPPHIRE).add(ITEMS.get("sapphire_gear"));
            tag(CoFHTags.Items.GEARS_SIGNALUM).add(ITEMS.get("signalum_gear"));
            tag(CoFHTags.Items.GEARS_SILVER).add(ITEMS.get("silver_gear"));
            tag(CoFHTags.Items.GEARS_TIN).add(ITEMS.get("tin_gear"));

            tag(CoFHTags.Items.GEARS).addTags(
                    CoFHTags.Items.GEARS_BRONZE,
                    CoFHTags.Items.GEARS_CONSTANTAN,
                    CoFHTags.Items.GEARS_COPPER,
                    CoFHTags.Items.GEARS_DIAMOND,
                    CoFHTags.Items.GEARS_ELECTRUM,
                    CoFHTags.Items.GEARS_EMERALD,
                    CoFHTags.Items.GEARS_ENDERIUM,
                    CoFHTags.Items.GEARS_GOLD,
                    CoFHTags.Items.GEARS_INVAR,
                    CoFHTags.Items.GEARS_IRON,
                    CoFHTags.Items.GEARS_LAPIS,
                    CoFHTags.Items.GEARS_LEAD,
                    CoFHTags.Items.GEARS_LUMIUM,
                    CoFHTags.Items.GEARS_NETHERITE,
                    CoFHTags.Items.GEARS_NICKEL,
                    CoFHTags.Items.GEARS_QUARTZ,
                    CoFHTags.Items.GEARS_RUBY,
                    CoFHTags.Items.GEARS_SAPPHIRE,
                    CoFHTags.Items.GEARS_SIGNALUM,
                    CoFHTags.Items.GEARS_SILVER,
                    CoFHTags.Items.GEARS_TIN
            );

            tag(CoFHTags.Items.GEMS_APATITE).add(ITEMS.get("apatite"));
            tag(CoFHTags.Items.GEMS_CINNABAR).add(ITEMS.get("cinnabar"));
            tag(CoFHTags.Items.GEMS_NITER).add(ITEMS.get("niter"));
            tag(CoFHTags.Items.GEMS_RUBY).add(ITEMS.get("ruby"));
            tag(CoFHTags.Items.GEMS_SAPPHIRE).add(ITEMS.get("sapphire"));
            tag(CoFHTags.Items.GEMS_SULFUR).add(ITEMS.get("sulfur"));

            tag(GEMS).addTags(
                    CoFHTags.Items.GEMS_APATITE,
                    CoFHTags.Items.GEMS_CINNABAR,
                    CoFHTags.Items.GEMS_NITER,
                    CoFHTags.Items.GEMS_RUBY,
                    CoFHTags.Items.GEMS_SAPPHIRE,
                    CoFHTags.Items.GEMS_SULFUR
            );

            tag(CoFHTags.Items.INGOTS_BRONZE).add(ITEMS.get("bronze_ingot"));
            tag(CoFHTags.Items.INGOTS_CONSTANTAN).add(ITEMS.get("constantan_ingot"));
            tag(CoFHTags.Items.INGOTS_ELECTRUM).add(ITEMS.get("electrum_ingot"));
            tag(CoFHTags.Items.INGOTS_ENDERIUM).add(ITEMS.get("enderium_ingot"));
            tag(CoFHTags.Items.INGOTS_INVAR).add(ITEMS.get("invar_ingot"));
            tag(CoFHTags.Items.INGOTS_LEAD).add(ITEMS.get("lead_ingot"));
            tag(CoFHTags.Items.INGOTS_LUMIUM).add(ITEMS.get("lumium_ingot"));
            tag(CoFHTags.Items.INGOTS_NICKEL).add(ITEMS.get("nickel_ingot"));
            tag(CoFHTags.Items.INGOTS_SIGNALUM).add(ITEMS.get("signalum_ingot"));
            tag(CoFHTags.Items.INGOTS_SILVER).add(ITEMS.get("silver_ingot"));
            tag(CoFHTags.Items.INGOTS_TIN).add(ITEMS.get("tin_ingot"));

            tag(INGOTS).addTags(
                    CoFHTags.Items.INGOTS_BRONZE,
                    CoFHTags.Items.INGOTS_CONSTANTAN,
                    CoFHTags.Items.INGOTS_ELECTRUM,
                    CoFHTags.Items.INGOTS_ENDERIUM,
                    CoFHTags.Items.INGOTS_INVAR,
                    CoFHTags.Items.INGOTS_LEAD,
                    CoFHTags.Items.INGOTS_LUMIUM,
                    CoFHTags.Items.INGOTS_NICKEL,
                    CoFHTags.Items.INGOTS_SIGNALUM,
                    CoFHTags.Items.INGOTS_SILVER,
                    CoFHTags.Items.INGOTS_TIN
            );

            tag(CoFHTags.Items.NUGGETS_BRONZE).add(ITEMS.get("bronze_nugget"));
            tag(CoFHTags.Items.NUGGETS_CONSTANTAN).add(ITEMS.get("constantan_nugget"));
            tag(CoFHTags.Items.NUGGETS_COPPER).add(ITEMS.get("copper_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_DIAMOND).add(ITEMS.get("diamond_nugget"));
            tag(CoFHTags.Items.NUGGETS_ELECTRUM).add(ITEMS.get("electrum_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_EMERALD).add(ITEMS.get("emerald_nugget"));
            tag(CoFHTags.Items.NUGGETS_ENDERIUM).add(ITEMS.get("enderium_nugget"));
            tag(CoFHTags.Items.NUGGETS_INVAR).add(ITEMS.get("invar_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_LAPIS).add(ITEMS.get("lapis_nugget"));
            tag(CoFHTags.Items.NUGGETS_LEAD).add(ITEMS.get("lead_nugget"));
            tag(CoFHTags.Items.NUGGETS_LUMIUM).add(ITEMS.get("lumium_nugget"));
            tag(CoFHTags.Items.NUGGETS_NETHERITE).add(ITEMS.get("netherite_nugget"));
            tag(CoFHTags.Items.NUGGETS_NICKEL).add(ITEMS.get("nickel_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_QUARTZ).add(ITEMS.get("quartz_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_RUBY).add(ITEMS.get("ruby_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_SAPPHIRE).add(ITEMS.get("sapphire_nugget"));
            tag(CoFHTags.Items.NUGGETS_SIGNALUM).add(ITEMS.get("signalum_nugget"));
            tag(CoFHTags.Items.NUGGETS_SILVER).add(ITEMS.get("silver_nugget"));
            tag(CoFHTags.Items.NUGGETS_TIN).add(ITEMS.get("tin_nugget"));

            tag(NUGGETS).addTags(
                    CoFHTags.Items.NUGGETS_BRONZE,
                    CoFHTags.Items.NUGGETS_CONSTANTAN,
                    CoFHTags.Items.NUGGETS_COPPER,
                    CoFHTags.Items.NUGGETS_ELECTRUM,
                    CoFHTags.Items.NUGGETS_ENDERIUM,
                    CoFHTags.Items.NUGGETS_INVAR,
                    CoFHTags.Items.NUGGETS_LEAD,
                    CoFHTags.Items.NUGGETS_LUMIUM,
                    CoFHTags.Items.NUGGETS_NETHERITE,
                    CoFHTags.Items.NUGGETS_NICKEL,
                    CoFHTags.Items.NUGGETS_SIGNALUM,
                    CoFHTags.Items.NUGGETS_SILVER,
                    CoFHTags.Items.NUGGETS_TIN
            );

            tag(CoFHTags.Items.PLATES_BRONZE).add(ITEMS.get("bronze_plate"));
            tag(CoFHTags.Items.PLATES_CONSTANTAN).add(ITEMS.get("constantan_plate"));
            tag(CoFHTags.Items.PLATES_COPPER).add(ITEMS.get("copper_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_DIAMOND).add(ITEMS.get("diamond_plate"));
            tag(CoFHTags.Items.PLATES_ELECTRUM).add(ITEMS.get("electrum_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_EMERALD).add(ITEMS.get("emerald_plate"));
            tag(CoFHTags.Items.PLATES_ENDERIUM).add(ITEMS.get("enderium_plate"));
            tag(CoFHTags.Items.PLATES_GOLD).add(ITEMS.get("gold_plate"));
            tag(CoFHTags.Items.PLATES_INVAR).add(ITEMS.get("invar_plate"));
            tag(CoFHTags.Items.PLATES_IRON).add(ITEMS.get("iron_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_LAPIS).add(ITEMS.get("lapis_plate"));
            tag(CoFHTags.Items.PLATES_LEAD).add(ITEMS.get("lead_plate"));
            tag(CoFHTags.Items.PLATES_LUMIUM).add(ITEMS.get("lumium_plate"));
            tag(CoFHTags.Items.PLATES_NETHERITE).add(ITEMS.get("netherite_plate"));
            tag(CoFHTags.Items.PLATES_NICKEL).add(ITEMS.get("nickel_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_QUARTZ).add(ITEMS.get("quartz_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_RUBY).add(ITEMS.get("ruby_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_SAPPHIRE).add(ITEMS.get("sapphire_plate"));
            tag(CoFHTags.Items.PLATES_SIGNALUM).add(ITEMS.get("signalum_plate"));
            tag(CoFHTags.Items.PLATES_SILVER).add(ITEMS.get("silver_plate"));
            tag(CoFHTags.Items.PLATES_TIN).add(ITEMS.get("tin_plate"));

            tag(CoFHTags.Items.PLATES).addTags(
                    CoFHTags.Items.PLATES_BRONZE,
                    CoFHTags.Items.PLATES_CONSTANTAN,
                    CoFHTags.Items.PLATES_COPPER,
                    CoFHTags.Items.PLATES_ELECTRUM,
                    CoFHTags.Items.PLATES_ENDERIUM,
                    CoFHTags.Items.PLATES_GOLD,
                    CoFHTags.Items.PLATES_INVAR,
                    CoFHTags.Items.PLATES_IRON,
                    CoFHTags.Items.PLATES_LEAD,
                    CoFHTags.Items.PLATES_LUMIUM,
                    CoFHTags.Items.PLATES_NETHERITE,
                    CoFHTags.Items.PLATES_NICKEL,
                    CoFHTags.Items.PLATES_SIGNALUM,
                    CoFHTags.Items.PLATES_SILVER,
                    CoFHTags.Items.PLATES_TIN
            );

            tag(CoFHTags.Items.RAW_MATERIALS_LEAD).add(ITEMS.get("raw_lead"));
            tag(CoFHTags.Items.RAW_MATERIALS_NICKEL).add(ITEMS.get("raw_nickel"));
            tag(CoFHTags.Items.RAW_MATERIALS_SILVER).add(ITEMS.get("raw_silver"));
            tag(CoFHTags.Items.RAW_MATERIALS_TIN).add(ITEMS.get("raw_tin"));

            tag(RAW_MATERIALS).addTags(
                    CoFHTags.Items.RAW_MATERIALS_LEAD,
                    CoFHTags.Items.RAW_MATERIALS_NICKEL,
                    CoFHTags.Items.RAW_MATERIALS_SILVER,
                    CoFHTags.Items.RAW_MATERIALS_TIN
            );

            tag(CoFHTags.Items.TOOLS_WRENCH).add(ITEMS.get(ID_WRENCH));

            tag(ThermalTags.Items.BITUMEN).add(ITEMS.get("bitumen"));
            tag(ThermalTags.Items.COAL_COKE).add(ITEMS.get("coal_coke"));
            tag(ThermalTags.Items.SAWDUST).add(ITEMS.get("sawdust"));
            tag(ThermalTags.Items.SLAG).add(ITEMS.get("slag"));
            tag(ThermalTags.Items.TAR).add(ITEMS.get("tar"));

            tag(CoFHTags.Items.DUSTS_APATITE).add(ITEMS.get("apatite_dust"));
            tag(CoFHTags.Items.DUSTS_CINNABAR).add(ITEMS.get("cinnabar_dust"));
            tag(CoFHTags.Items.DUSTS_NITER).add(ITEMS.get("niter_dust"));
            tag(CoFHTags.Items.DUSTS_SULFUR).add(ITEMS.get("sulfur_dust"));
            tag(CoFHTags.Items.DUSTS_WOOD).add(ITEMS.get("sawdust"));

            tag(CoFHTags.Items.DUSTS_ENDER_PEARL).add(ITEMS.get("ender_pearl_dust"));

            tag(CoFHTags.Items.LOCKS).add(ITEMS.get(ID_LOCK));
            tag(CoFHTags.Items.SECURABLE).add(ITEMS.get(ID_SATCHEL));
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

}
