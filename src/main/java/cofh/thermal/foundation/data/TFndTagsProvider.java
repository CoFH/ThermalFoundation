package cofh.thermal.foundation.data;

import cofh.lib.tags.BlockTagsCoFH;
import cofh.lib.tags.ItemTagsCoFH;
import cofh.thermal.lib.util.references.ThermalTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.foundation.init.TFndIDs.*;
import static net.minecraftforge.common.Tags.Items.*;

public class TFndTagsProvider {

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

            // region WOOD
            tag(BlockTags.LEAVES).add(BLOCKS.get("rubberwood_leaves"));
            tag(BlockTags.LOGS).add(BLOCKS.get(ID_RUBBERWOOD_LOG));
            tag(BlockTags.SAPLINGS).add(BLOCKS.get("rubberwood_sapling"));

            tag(BlockTags.DOORS).add(BLOCKS.get("rubberwood_door"));
            tag(BlockTags.FENCES).add(BLOCKS.get("rubberwood_fence"));
            tag(BlockTags.FENCE_GATES).add(BLOCKS.get("rubberwood_fence_gate"));
            tag(BlockTags.PLANKS).add(BLOCKS.get("rubberwood_planks"));
            tag(BlockTags.SLABS).add(BLOCKS.get("rubberwood_slab"));
            tag(BlockTags.STAIRS).add(BLOCKS.get("rubberwood_stairs"));

            tag(BlockTags.WOODEN_DOORS).add(BLOCKS.get("rubberwood_door"));
            tag(BlockTags.WOODEN_FENCES).add(BLOCKS.get("rubberwood_fence"));
            tag(BlockTags.WOODEN_SLABS).add(BLOCKS.get("rubberwood_slab"));
            tag(BlockTags.WOODEN_STAIRS).add(BLOCKS.get("rubberwood_stairs"));

            tag(BlockTags.NON_FLAMMABLE_WOOD).add(
                    BLOCKS.get("rubberwood_door"),
                    BLOCKS.get("rubberwood_fence"),
                    BLOCKS.get("rubberwood_fence_gate"),
                    BLOCKS.get(ID_RUBBERWOOD_LOG),
                    BLOCKS.get("rubberwood_planks"),
                    BLOCKS.get("rubberwood_slab"),
                    BLOCKS.get("rubberwood_stairs"),
                    BLOCKS.get(ID_RUBBERWOOD_WOOD),
                    BLOCKS.get(ID_STRIPPED_RUBBERWOOD_LOG),
                    BLOCKS.get(ID_STRIPPED_RUBBERWOOD_WOOD)
            );

            tag(ThermalTags.Blocks.LOGS_RUBBERWOOD).add(
                    BLOCKS.get(ID_RUBBERWOOD_LOG),
                    BLOCKS.get(ID_RUBBERWOOD_WOOD),
                    BLOCKS.get(ID_STRIPPED_RUBBERWOOD_LOG),
                    BLOCKS.get(ID_STRIPPED_RUBBERWOOD_WOOD)
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

            return "Thermal Foundation: Item Tags";
        }

        @SuppressWarnings ("unchecked")
        @Override
        protected void addTags() {

            copy(BlockTagsCoFH.ORES_APATITE, ItemTagsCoFH.ORES_APATITE);
            copy(BlockTagsCoFH.ORES_CINNABAR, ItemTagsCoFH.ORES_CINNABAR);
            copy(BlockTagsCoFH.ORES_NITER, ItemTagsCoFH.ORES_NITER);
            copy(BlockTagsCoFH.ORES_SULFUR, ItemTagsCoFH.ORES_SULFUR);

            copy(BlockTagsCoFH.ORES_LEAD, ItemTagsCoFH.ORES_LEAD);
            copy(BlockTagsCoFH.ORES_NICKEL, ItemTagsCoFH.ORES_NICKEL);
            copy(BlockTagsCoFH.ORES_SILVER, ItemTagsCoFH.ORES_SILVER);
            copy(BlockTagsCoFH.ORES_TIN, ItemTagsCoFH.ORES_TIN);

            copy(BlockTagsCoFH.ORES_RUBY, ItemTagsCoFH.ORES_RUBY);
            copy(BlockTagsCoFH.ORES_SAPPHIRE, ItemTagsCoFH.ORES_SAPPHIRE);

            tag(Tags.Items.ORES).addTags(
                    ItemTagsCoFH.ORES_APATITE,
                    ItemTagsCoFH.ORES_CINNABAR,
                    ItemTagsCoFH.ORES_NITER,
                    ItemTagsCoFH.ORES_SULFUR,

                    ItemTagsCoFH.ORES_LEAD,
                    ItemTagsCoFH.ORES_NICKEL,
                    ItemTagsCoFH.ORES_SILVER,
                    ItemTagsCoFH.ORES_TIN,

                    ItemTagsCoFH.ORES_RUBY,
                    ItemTagsCoFH.ORES_SAPPHIRE
            );

            copy(BlockTagsCoFH.STORAGE_BLOCKS_RAW_LEAD, ItemTagsCoFH.STORAGE_BLOCKS_RAW_LEAD);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_RAW_NICKEL, ItemTagsCoFH.STORAGE_BLOCKS_RAW_NICKEL);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_RAW_SILVER, ItemTagsCoFH.STORAGE_BLOCKS_RAW_SILVER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_RAW_TIN, ItemTagsCoFH.STORAGE_BLOCKS_RAW_TIN);

            copy(BlockTagsCoFH.STORAGE_BLOCKS_LEAD, ItemTagsCoFH.STORAGE_BLOCKS_LEAD);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_NICKEL, ItemTagsCoFH.STORAGE_BLOCKS_NICKEL);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SILVER, ItemTagsCoFH.STORAGE_BLOCKS_SILVER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_TIN, ItemTagsCoFH.STORAGE_BLOCKS_TIN);

            copy(BlockTagsCoFH.STORAGE_BLOCKS_BRONZE, ItemTagsCoFH.STORAGE_BLOCKS_BRONZE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_CONSTANTAN, ItemTagsCoFH.STORAGE_BLOCKS_CONSTANTAN);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_ELECTRUM, ItemTagsCoFH.STORAGE_BLOCKS_ELECTRUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_INVAR, ItemTagsCoFH.STORAGE_BLOCKS_INVAR);

            copy(BlockTagsCoFH.STORAGE_BLOCKS_RUBY, ItemTagsCoFH.STORAGE_BLOCKS_RUBY);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SAPPHIRE, ItemTagsCoFH.STORAGE_BLOCKS_SAPPHIRE);

            tag(Tags.Items.STORAGE_BLOCKS).addTags(
                    ItemTagsCoFH.STORAGE_BLOCKS_RAW_LEAD,
                    ItemTagsCoFH.STORAGE_BLOCKS_RAW_NICKEL,
                    ItemTagsCoFH.STORAGE_BLOCKS_RAW_SILVER,
                    ItemTagsCoFH.STORAGE_BLOCKS_RAW_TIN,

                    ItemTagsCoFH.STORAGE_BLOCKS_LEAD,
                    ItemTagsCoFH.STORAGE_BLOCKS_NICKEL,
                    ItemTagsCoFH.STORAGE_BLOCKS_SILVER,
                    ItemTagsCoFH.STORAGE_BLOCKS_TIN,

                    ItemTagsCoFH.STORAGE_BLOCKS_BRONZE,
                    ItemTagsCoFH.STORAGE_BLOCKS_CONSTANTAN,
                    ItemTagsCoFH.STORAGE_BLOCKS_ELECTRUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_INVAR,

                    ItemTagsCoFH.STORAGE_BLOCKS_RUBY,
                    ItemTagsCoFH.STORAGE_BLOCKS_SAPPHIRE
            );

            copy(BlockTags.LEAVES, ItemTags.LEAVES);
            copy(BlockTags.LOGS, ItemTags.LOGS);
            copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

            copy(BlockTags.DOORS, ItemTags.DOORS);
            copy(BlockTags.FENCES, ItemTags.FENCES);
            copy(BlockTags.PLANKS, ItemTags.PLANKS);
            copy(BlockTags.SLABS, ItemTags.SLABS);
            copy(BlockTags.STAIRS, ItemTags.STAIRS);

            copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
            copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
            copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
            copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);

            copy(BlockTags.NON_FLAMMABLE_WOOD, ItemTags.NON_FLAMMABLE_WOOD);
            copy(ThermalTags.Blocks.LOGS_RUBBERWOOD, ThermalTags.Items.LOGS_RUBBERWOOD);

            tag(ItemTagsCoFH.COINS_LEAD).add(ITEMS.get("lead_coin"));
            tag(ItemTagsCoFH.COINS_NICKEL).add(ITEMS.get("nickel_coin"));
            tag(ItemTagsCoFH.COINS_SILVER).add(ITEMS.get("silver_coin"));
            tag(ItemTagsCoFH.COINS_TIN).add(ITEMS.get("tin_coin"));

            tag(ItemTagsCoFH.COINS_BRONZE).add(ITEMS.get("bronze_coin"));
            tag(ItemTagsCoFH.COINS_CONSTANTAN).add(ITEMS.get("constantan_coin"));
            tag(ItemTagsCoFH.COINS_ELECTRUM).add(ITEMS.get("electrum_coin"));
            tag(ItemTagsCoFH.COINS_INVAR).add(ITEMS.get("invar_coin"));

            tag(ItemTagsCoFH.COINS).addTags(
                    ItemTagsCoFH.COINS_LEAD,
                    ItemTagsCoFH.COINS_NICKEL,
                    ItemTagsCoFH.COINS_SILVER,
                    ItemTagsCoFH.COINS_TIN,

                    ItemTagsCoFH.COINS_BRONZE,
                    ItemTagsCoFH.COINS_CONSTANTAN,
                    ItemTagsCoFH.COINS_ELECTRUM,
                    ItemTagsCoFH.COINS_INVAR
            );

            tag(ItemTagsCoFH.DUSTS_LEAD).add(ITEMS.get("lead_dust"));
            tag(ItemTagsCoFH.DUSTS_NICKEL).add(ITEMS.get("nickel_dust"));
            tag(ItemTagsCoFH.DUSTS_SILVER).add(ITEMS.get("silver_dust"));
            tag(ItemTagsCoFH.DUSTS_TIN).add(ITEMS.get("tin_dust"));

            tag(ItemTagsCoFH.DUSTS_BRONZE).add(ITEMS.get("bronze_dust"));
            tag(ItemTagsCoFH.DUSTS_CONSTANTAN).add(ITEMS.get("constantan_dust"));
            tag(ItemTagsCoFH.DUSTS_ELECTRUM).add(ITEMS.get("electrum_dust"));
            tag(ItemTagsCoFH.DUSTS_INVAR).add(ITEMS.get("invar_dust"));

            tag(ItemTagsCoFH.DUSTS_RUBY).add(ITEMS.get("ruby_dust"));
            tag(ItemTagsCoFH.DUSTS_SAPPHIRE).add(ITEMS.get("sapphire_dust"));

            tag(DUSTS).addTags(
                    ItemTagsCoFH.DUSTS_LEAD,
                    ItemTagsCoFH.DUSTS_NICKEL,
                    ItemTagsCoFH.DUSTS_SILVER,
                    ItemTagsCoFH.DUSTS_TIN,

                    ItemTagsCoFH.DUSTS_BRONZE,
                    ItemTagsCoFH.DUSTS_CONSTANTAN,
                    ItemTagsCoFH.DUSTS_ELECTRUM,
                    ItemTagsCoFH.DUSTS_INVAR,

                    ItemTagsCoFH.DUSTS_RUBY,
                    ItemTagsCoFH.DUSTS_SAPPHIRE
            );

            tag(ItemTagsCoFH.GEARS_LEAD).add(ITEMS.get("lead_gear"));
            tag(ItemTagsCoFH.GEARS_NICKEL).add(ITEMS.get("nickel_gear"));
            tag(ItemTagsCoFH.GEARS_SILVER).add(ITEMS.get("silver_gear"));
            tag(ItemTagsCoFH.GEARS_TIN).add(ITEMS.get("tin_gear"));

            tag(ItemTagsCoFH.GEARS_BRONZE).add(ITEMS.get("bronze_gear"));
            tag(ItemTagsCoFH.GEARS_CONSTANTAN).add(ITEMS.get("constantan_gear"));
            tag(ItemTagsCoFH.GEARS_ELECTRUM).add(ITEMS.get("electrum_gear"));
            tag(ItemTagsCoFH.GEARS_INVAR).add(ITEMS.get("invar_gear"));

            tag(ItemTagsCoFH.GEARS_RUBY).add(ITEMS.get("ruby_gear"));
            tag(ItemTagsCoFH.GEARS_SAPPHIRE).add(ITEMS.get("sapphire_gear"));

            tag(ItemTagsCoFH.GEARS).addTags(
                    ItemTagsCoFH.GEARS_LEAD,
                    ItemTagsCoFH.GEARS_NICKEL,
                    ItemTagsCoFH.GEARS_SILVER,
                    ItemTagsCoFH.GEARS_TIN,

                    ItemTagsCoFH.GEARS_BRONZE,
                    ItemTagsCoFH.GEARS_CONSTANTAN,
                    ItemTagsCoFH.GEARS_ELECTRUM,
                    ItemTagsCoFH.GEARS_INVAR,

                    ItemTagsCoFH.GEARS_RUBY,
                    ItemTagsCoFH.GEARS_SAPPHIRE
            );

            tag(ItemTagsCoFH.GEMS_RUBY).add(ITEMS.get("ruby"));
            tag(ItemTagsCoFH.GEMS_SAPPHIRE).add(ITEMS.get("sapphire"));

            tag(GEMS).addTags(
                    ItemTagsCoFH.GEMS_RUBY,
                    ItemTagsCoFH.GEMS_SAPPHIRE
            );

            tag(ItemTagsCoFH.INGOTS_LEAD).add(ITEMS.get("lead_ingot"));
            tag(ItemTagsCoFH.INGOTS_NICKEL).add(ITEMS.get("nickel_ingot"));
            tag(ItemTagsCoFH.INGOTS_SILVER).add(ITEMS.get("silver_ingot"));
            tag(ItemTagsCoFH.INGOTS_TIN).add(ITEMS.get("tin_ingot"));

            tag(ItemTagsCoFH.INGOTS_BRONZE).add(ITEMS.get("bronze_ingot"));
            tag(ItemTagsCoFH.INGOTS_CONSTANTAN).add(ITEMS.get("constantan_ingot"));
            tag(ItemTagsCoFH.INGOTS_ELECTRUM).add(ITEMS.get("electrum_ingot"));
            tag(ItemTagsCoFH.INGOTS_INVAR).add(ITEMS.get("invar_ingot"));

            tag(INGOTS).addTags(
                    ItemTagsCoFH.INGOTS_LEAD,
                    ItemTagsCoFH.INGOTS_NICKEL,
                    ItemTagsCoFH.INGOTS_SILVER,
                    ItemTagsCoFH.INGOTS_TIN,

                    ItemTagsCoFH.INGOTS_BRONZE,
                    ItemTagsCoFH.INGOTS_CONSTANTAN,
                    ItemTagsCoFH.INGOTS_ELECTRUM,
                    ItemTagsCoFH.INGOTS_INVAR
            );

            tag(ItemTagsCoFH.NUGGETS_LEAD).add(ITEMS.get("lead_nugget"));
            tag(ItemTagsCoFH.NUGGETS_NICKEL).add(ITEMS.get("nickel_nugget"));
            tag(ItemTagsCoFH.NUGGETS_SILVER).add(ITEMS.get("silver_nugget"));
            tag(ItemTagsCoFH.NUGGETS_TIN).add(ITEMS.get("tin_nugget"));

            tag(ItemTagsCoFH.NUGGETS_BRONZE).add(ITEMS.get("bronze_nugget"));
            tag(ItemTagsCoFH.NUGGETS_CONSTANTAN).add(ITEMS.get("constantan_nugget"));
            tag(ItemTagsCoFH.NUGGETS_ELECTRUM).add(ITEMS.get("electrum_nugget"));
            tag(ItemTagsCoFH.NUGGETS_INVAR).add(ITEMS.get("invar_nugget"));

            tag(NUGGETS).addTags(
                    ItemTagsCoFH.NUGGETS_LEAD,
                    ItemTagsCoFH.NUGGETS_NICKEL,
                    ItemTagsCoFH.NUGGETS_SILVER,
                    ItemTagsCoFH.NUGGETS_TIN,

                    ItemTagsCoFH.NUGGETS_BRONZE,
                    ItemTagsCoFH.NUGGETS_CONSTANTAN,
                    ItemTagsCoFH.NUGGETS_ELECTRUM,
                    ItemTagsCoFH.NUGGETS_INVAR
            );

            tag(ItemTagsCoFH.PLATES_LEAD).add(ITEMS.get("lead_plate"));
            tag(ItemTagsCoFH.PLATES_NICKEL).add(ITEMS.get("nickel_plate"));
            tag(ItemTagsCoFH.PLATES_SILVER).add(ITEMS.get("silver_plate"));
            tag(ItemTagsCoFH.PLATES_TIN).add(ITEMS.get("tin_plate"));

            tag(ItemTagsCoFH.PLATES_BRONZE).add(ITEMS.get("bronze_plate"));
            tag(ItemTagsCoFH.PLATES_CONSTANTAN).add(ITEMS.get("constantan_plate"));
            tag(ItemTagsCoFH.PLATES_ELECTRUM).add(ITEMS.get("electrum_plate"));
            tag(ItemTagsCoFH.PLATES_INVAR).add(ITEMS.get("invar_plate"));

            tag(ItemTagsCoFH.PLATES).addTags(
                    ItemTagsCoFH.PLATES_LEAD,
                    ItemTagsCoFH.PLATES_NICKEL,
                    ItemTagsCoFH.PLATES_SILVER,
                    ItemTagsCoFH.PLATES_TIN,

                    ItemTagsCoFH.PLATES_BRONZE,
                    ItemTagsCoFH.PLATES_CONSTANTAN,
                    ItemTagsCoFH.PLATES_ELECTRUM,
                    ItemTagsCoFH.PLATES_INVAR
            );

            tag(ItemTagsCoFH.RAW_MATERIALS_LEAD).add(ITEMS.get("raw_lead"));
            tag(ItemTagsCoFH.RAW_MATERIALS_NICKEL).add(ITEMS.get("raw_nickel"));
            tag(ItemTagsCoFH.RAW_MATERIALS_SILVER).add(ITEMS.get("raw_silver"));
            tag(ItemTagsCoFH.RAW_MATERIALS_TIN).add(ITEMS.get("raw_tin"));

            tag(RAW_MATERIALS).addTags(
                    ItemTagsCoFH.RAW_MATERIALS_LEAD,
                    ItemTagsCoFH.RAW_MATERIALS_NICKEL,
                    ItemTagsCoFH.RAW_MATERIALS_SILVER,
                    ItemTagsCoFH.RAW_MATERIALS_TIN
            );
        }

    }

}
