package cofh.thermal.foundation.data;

import cofh.lib.util.references.CoFHTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.lib.common.ThermalIDs.*;
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
            tag(CoFHTags.Blocks.ORES_APATITE).add(BLOCKS.get(ID_APATITE_ORE));
            tag(CoFHTags.Blocks.ORES_CINNABAR).add(BLOCKS.get(ID_CINNABAR_ORE));
            tag(CoFHTags.Blocks.ORES_NITER).add(BLOCKS.get(ID_NITER_ORE));
            tag(CoFHTags.Blocks.ORES_SULFUR).add(BLOCKS.get(ID_SULFUR_ORE));

            tag(CoFHTags.Blocks.ORES_LEAD).add(BLOCKS.get(ID_LEAD_ORE));
            tag(CoFHTags.Blocks.ORES_NICKEL).add(BLOCKS.get(ID_NICKEL_ORE));
            tag(CoFHTags.Blocks.ORES_SILVER).add(BLOCKS.get(ID_SILVER_ORE));
            tag(CoFHTags.Blocks.ORES_TIN).add(BLOCKS.get(ID_TIN_ORE));

            tag(CoFHTags.Blocks.ORES_RUBY).add(BLOCKS.get(ID_RUBY_ORE));
            tag(CoFHTags.Blocks.ORES_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_ORE));

            tag(CoFHTags.Blocks.ORES_APATITE).add(BLOCKS.get(deepslate(ID_APATITE_ORE)));
            tag(CoFHTags.Blocks.ORES_CINNABAR).add(BLOCKS.get(deepslate(ID_CINNABAR_ORE)));
            tag(CoFHTags.Blocks.ORES_NITER).add(BLOCKS.get(deepslate(ID_NITER_ORE)));
            tag(CoFHTags.Blocks.ORES_SULFUR).add(BLOCKS.get(deepslate(ID_SULFUR_ORE)));

            tag(CoFHTags.Blocks.ORES_LEAD).add(BLOCKS.get(deepslate(ID_LEAD_ORE)));
            tag(CoFHTags.Blocks.ORES_NICKEL).add(BLOCKS.get(deepslate(ID_NICKEL_ORE)));
            tag(CoFHTags.Blocks.ORES_SILVER).add(BLOCKS.get(deepslate(ID_SILVER_ORE)));
            tag(CoFHTags.Blocks.ORES_TIN).add(BLOCKS.get(deepslate(ID_TIN_ORE)));

            tag(CoFHTags.Blocks.ORES_RUBY).add(BLOCKS.get(deepslate(ID_RUBY_ORE)));
            tag(CoFHTags.Blocks.ORES_SAPPHIRE).add(BLOCKS.get(deepslate(ID_SAPPHIRE_ORE)));

            tag(Tags.Blocks.ORES).addTags(
                    CoFHTags.Blocks.ORES_APATITE,
                    CoFHTags.Blocks.ORES_CINNABAR,
                    CoFHTags.Blocks.ORES_NITER,
                    CoFHTags.Blocks.ORES_SULFUR,

                    CoFHTags.Blocks.ORES_LEAD,
                    CoFHTags.Blocks.ORES_NICKEL,
                    CoFHTags.Blocks.ORES_SILVER,
                    CoFHTags.Blocks.ORES_TIN,

                    CoFHTags.Blocks.ORES_RUBY,
                    CoFHTags.Blocks.ORES_SAPPHIRE
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

            tag(CoFHTags.Blocks.STORAGE_BLOCKS_APATITE).add(BLOCKS.get(ID_APATITE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_CINNABAR).add(BLOCKS.get(ID_CINNABAR_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_NITER).add(BLOCKS.get(ID_NITER_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SULFUR).add(BLOCKS.get(ID_SULFUR_BLOCK));

            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_LEAD).add(BLOCKS.get(raw(ID_LEAD_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_NICKEL).add(BLOCKS.get(raw(ID_NICKEL_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_SILVER).add(BLOCKS.get(raw(ID_SILVER_BLOCK)));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_TIN).add(BLOCKS.get(raw(ID_TIN_BLOCK)));

            tag(CoFHTags.Blocks.STORAGE_BLOCKS_LEAD).add(BLOCKS.get(ID_LEAD_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_NICKEL).add(BLOCKS.get(ID_NICKEL_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SILVER).add(BLOCKS.get(ID_SILVER_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_TIN).add(BLOCKS.get(ID_TIN_BLOCK));

            tag(CoFHTags.Blocks.STORAGE_BLOCKS_BRONZE).add(BLOCKS.get(ID_BRONZE_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_CONSTANTAN).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_ELECTRUM).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_INVAR).add(BLOCKS.get(ID_INVAR_BLOCK));

            tag(CoFHTags.Blocks.STORAGE_BLOCKS_RUBY).add(BLOCKS.get(ID_RUBY_BLOCK));
            tag(CoFHTags.Blocks.STORAGE_BLOCKS_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));


            tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
                    CoFHTags.Blocks.STORAGE_BLOCKS_APATITE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_CINNABAR,
                    CoFHTags.Blocks.STORAGE_BLOCKS_NITER,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SULFUR,

                    CoFHTags.Blocks.STORAGE_BLOCKS_RAW_LEAD,
                    CoFHTags.Blocks.STORAGE_BLOCKS_RAW_NICKEL,
                    CoFHTags.Blocks.STORAGE_BLOCKS_RAW_SILVER,
                    CoFHTags.Blocks.STORAGE_BLOCKS_RAW_TIN,

                    CoFHTags.Blocks.STORAGE_BLOCKS_LEAD,
                    CoFHTags.Blocks.STORAGE_BLOCKS_NICKEL,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SILVER,
                    CoFHTags.Blocks.STORAGE_BLOCKS_TIN,

                    CoFHTags.Blocks.STORAGE_BLOCKS_BRONZE,
                    CoFHTags.Blocks.STORAGE_BLOCKS_CONSTANTAN,
                    CoFHTags.Blocks.STORAGE_BLOCKS_ELECTRUM,
                    CoFHTags.Blocks.STORAGE_BLOCKS_INVAR,

                    CoFHTags.Blocks.STORAGE_BLOCKS_RUBY,
                    CoFHTags.Blocks.STORAGE_BLOCKS_SAPPHIRE
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

            copy(CoFHTags.Blocks.ORES_APATITE, CoFHTags.Items.ORES_APATITE);
            copy(CoFHTags.Blocks.ORES_CINNABAR, CoFHTags.Items.ORES_CINNABAR);
            copy(CoFHTags.Blocks.ORES_NITER, CoFHTags.Items.ORES_NITER);
            copy(CoFHTags.Blocks.ORES_SULFUR, CoFHTags.Items.ORES_SULFUR);

            copy(CoFHTags.Blocks.ORES_LEAD, CoFHTags.Items.ORES_LEAD);
            copy(CoFHTags.Blocks.ORES_NICKEL, CoFHTags.Items.ORES_NICKEL);
            copy(CoFHTags.Blocks.ORES_SILVER, CoFHTags.Items.ORES_SILVER);
            copy(CoFHTags.Blocks.ORES_TIN, CoFHTags.Items.ORES_TIN);

            copy(CoFHTags.Blocks.ORES_RUBY, CoFHTags.Items.ORES_RUBY);
            copy(CoFHTags.Blocks.ORES_SAPPHIRE, CoFHTags.Items.ORES_SAPPHIRE);

            tag(Tags.Items.ORES).addTags(
                    CoFHTags.Items.ORES_APATITE,
                    CoFHTags.Items.ORES_CINNABAR,
                    CoFHTags.Items.ORES_NITER,
                    CoFHTags.Items.ORES_SULFUR,

                    CoFHTags.Items.ORES_LEAD,
                    CoFHTags.Items.ORES_NICKEL,
                    CoFHTags.Items.ORES_SILVER,
                    CoFHTags.Items.ORES_TIN,

                    CoFHTags.Items.ORES_RUBY,
                    CoFHTags.Items.ORES_SAPPHIRE
            );

            copy(CoFHTags.Blocks.STORAGE_BLOCKS_APATITE, CoFHTags.Items.STORAGE_BLOCKS_APATITE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_CINNABAR, CoFHTags.Items.STORAGE_BLOCKS_CINNABAR);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_NITER, CoFHTags.Items.STORAGE_BLOCKS_NITER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SULFUR, CoFHTags.Items.STORAGE_BLOCKS_SULFUR);

            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_LEAD, CoFHTags.Items.STORAGE_BLOCKS_RAW_LEAD);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_NICKEL, CoFHTags.Items.STORAGE_BLOCKS_RAW_NICKEL);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, CoFHTags.Items.STORAGE_BLOCKS_RAW_SILVER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RAW_TIN, CoFHTags.Items.STORAGE_BLOCKS_RAW_TIN);

            copy(CoFHTags.Blocks.STORAGE_BLOCKS_LEAD, CoFHTags.Items.STORAGE_BLOCKS_LEAD);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_NICKEL, CoFHTags.Items.STORAGE_BLOCKS_NICKEL);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SILVER, CoFHTags.Items.STORAGE_BLOCKS_SILVER);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_TIN, CoFHTags.Items.STORAGE_BLOCKS_TIN);

            copy(CoFHTags.Blocks.STORAGE_BLOCKS_BRONZE, CoFHTags.Items.STORAGE_BLOCKS_BRONZE);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_CONSTANTAN, CoFHTags.Items.STORAGE_BLOCKS_CONSTANTAN);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_ELECTRUM, CoFHTags.Items.STORAGE_BLOCKS_ELECTRUM);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_INVAR, CoFHTags.Items.STORAGE_BLOCKS_INVAR);

            copy(CoFHTags.Blocks.STORAGE_BLOCKS_RUBY, CoFHTags.Items.STORAGE_BLOCKS_RUBY);
            copy(CoFHTags.Blocks.STORAGE_BLOCKS_SAPPHIRE, CoFHTags.Items.STORAGE_BLOCKS_SAPPHIRE);

            tag(Tags.Items.STORAGE_BLOCKS).addTags(
                    CoFHTags.Items.STORAGE_BLOCKS_APATITE,
                    CoFHTags.Items.STORAGE_BLOCKS_CINNABAR,
                    CoFHTags.Items.STORAGE_BLOCKS_NITER,
                    CoFHTags.Items.STORAGE_BLOCKS_SULFUR,

                    CoFHTags.Items.STORAGE_BLOCKS_RAW_LEAD,
                    CoFHTags.Items.STORAGE_BLOCKS_RAW_NICKEL,
                    CoFHTags.Items.STORAGE_BLOCKS_RAW_SILVER,
                    CoFHTags.Items.STORAGE_BLOCKS_RAW_TIN,

                    CoFHTags.Items.STORAGE_BLOCKS_LEAD,
                    CoFHTags.Items.STORAGE_BLOCKS_NICKEL,
                    CoFHTags.Items.STORAGE_BLOCKS_SILVER,
                    CoFHTags.Items.STORAGE_BLOCKS_TIN,

                    CoFHTags.Items.STORAGE_BLOCKS_BRONZE,
                    CoFHTags.Items.STORAGE_BLOCKS_CONSTANTAN,
                    CoFHTags.Items.STORAGE_BLOCKS_ELECTRUM,
                    CoFHTags.Items.STORAGE_BLOCKS_INVAR,

                    CoFHTags.Items.STORAGE_BLOCKS_RUBY,
                    CoFHTags.Items.STORAGE_BLOCKS_SAPPHIRE
            );

            tag(CoFHTags.Items.COINS_LEAD).add(ITEMS.get("lead_coin"));
            tag(CoFHTags.Items.COINS_NICKEL).add(ITEMS.get("nickel_coin"));
            tag(CoFHTags.Items.COINS_SILVER).add(ITEMS.get("silver_coin"));
            tag(CoFHTags.Items.COINS_TIN).add(ITEMS.get("tin_coin"));

            tag(CoFHTags.Items.COINS_BRONZE).add(ITEMS.get("bronze_coin"));
            tag(CoFHTags.Items.COINS_CONSTANTAN).add(ITEMS.get("constantan_coin"));
            tag(CoFHTags.Items.COINS_ELECTRUM).add(ITEMS.get("electrum_coin"));
            tag(CoFHTags.Items.COINS_INVAR).add(ITEMS.get("invar_coin"));

            tag(CoFHTags.Items.COINS).addTags(
                    CoFHTags.Items.COINS_LEAD,
                    CoFHTags.Items.COINS_NICKEL,
                    CoFHTags.Items.COINS_SILVER,
                    CoFHTags.Items.COINS_TIN,

                    CoFHTags.Items.COINS_BRONZE,
                    CoFHTags.Items.COINS_CONSTANTAN,
                    CoFHTags.Items.COINS_ELECTRUM,
                    CoFHTags.Items.COINS_INVAR
            );


            tag(CoFHTags.Items.DUSTS_APATITE).add(ITEMS.get("apatite_dust"));
            tag(CoFHTags.Items.DUSTS_CINNABAR).add(ITEMS.get("cinnabar_dust"));
            tag(CoFHTags.Items.DUSTS_NITER).add(ITEMS.get("niter_dust"));
            tag(CoFHTags.Items.DUSTS_SULFUR).add(ITEMS.get("sulfur_dust"));

            tag(CoFHTags.Items.DUSTS_LEAD).add(ITEMS.get("lead_dust"));
            tag(CoFHTags.Items.DUSTS_NICKEL).add(ITEMS.get("nickel_dust"));
            tag(CoFHTags.Items.DUSTS_SILVER).add(ITEMS.get("silver_dust"));
            tag(CoFHTags.Items.DUSTS_TIN).add(ITEMS.get("tin_dust"));

            tag(CoFHTags.Items.DUSTS_BRONZE).add(ITEMS.get("bronze_dust"));
            tag(CoFHTags.Items.DUSTS_CONSTANTAN).add(ITEMS.get("constantan_dust"));
            tag(CoFHTags.Items.DUSTS_ELECTRUM).add(ITEMS.get("electrum_dust"));
            tag(CoFHTags.Items.DUSTS_INVAR).add(ITEMS.get("invar_dust"));

            tag(CoFHTags.Items.DUSTS_RUBY).add(ITEMS.get("ruby_dust"));
            tag(CoFHTags.Items.DUSTS_SAPPHIRE).add(ITEMS.get("sapphire_dust"));

            tag(DUSTS).addTags(
                    CoFHTags.Items.DUSTS_APATITE,
                    CoFHTags.Items.DUSTS_CINNABAR,
                    CoFHTags.Items.DUSTS_NITER,
                    CoFHTags.Items.DUSTS_SULFUR,

                    CoFHTags.Items.DUSTS_LEAD,
                    CoFHTags.Items.DUSTS_NICKEL,
                    CoFHTags.Items.DUSTS_SILVER,
                    CoFHTags.Items.DUSTS_TIN,

                    CoFHTags.Items.DUSTS_BRONZE,
                    CoFHTags.Items.DUSTS_CONSTANTAN,
                    CoFHTags.Items.DUSTS_ELECTRUM,
                    CoFHTags.Items.DUSTS_INVAR,

                    CoFHTags.Items.DUSTS_RUBY,
                    CoFHTags.Items.DUSTS_SAPPHIRE
            );

            tag(CoFHTags.Items.GEARS_LEAD).add(ITEMS.get("lead_gear"));
            tag(CoFHTags.Items.GEARS_NICKEL).add(ITEMS.get("nickel_gear"));
            tag(CoFHTags.Items.GEARS_SILVER).add(ITEMS.get("silver_gear"));
            tag(CoFHTags.Items.GEARS_TIN).add(ITEMS.get("tin_gear"));

            tag(CoFHTags.Items.GEARS_BRONZE).add(ITEMS.get("bronze_gear"));
            tag(CoFHTags.Items.GEARS_CONSTANTAN).add(ITEMS.get("constantan_gear"));
            tag(CoFHTags.Items.GEARS_ELECTRUM).add(ITEMS.get("electrum_gear"));
            tag(CoFHTags.Items.GEARS_INVAR).add(ITEMS.get("invar_gear"));

            tag(CoFHTags.Items.GEARS_RUBY).add(ITEMS.get("ruby_gear"));
            tag(CoFHTags.Items.GEARS_SAPPHIRE).add(ITEMS.get("sapphire_gear"));

            tag(CoFHTags.Items.GEARS).addTags(
                    CoFHTags.Items.GEARS_LEAD,
                    CoFHTags.Items.GEARS_NICKEL,
                    CoFHTags.Items.GEARS_SILVER,
                    CoFHTags.Items.GEARS_TIN,

                    CoFHTags.Items.GEARS_BRONZE,
                    CoFHTags.Items.GEARS_CONSTANTAN,
                    CoFHTags.Items.GEARS_ELECTRUM,
                    CoFHTags.Items.GEARS_INVAR,

                    CoFHTags.Items.GEARS_RUBY,
                    CoFHTags.Items.GEARS_SAPPHIRE
            );

            tag(CoFHTags.Items.GEMS_APATITE).add(ITEMS.get("apatite"));
            tag(CoFHTags.Items.GEMS_CINNABAR).add(ITEMS.get("cinnabar"));
            tag(CoFHTags.Items.GEMS_NITER).add(ITEMS.get("niter"));
            tag(CoFHTags.Items.GEMS_SULFUR).add(ITEMS.get("sulfur"));

            tag(CoFHTags.Items.GEMS_RUBY).add(ITEMS.get("ruby"));
            tag(CoFHTags.Items.GEMS_SAPPHIRE).add(ITEMS.get("sapphire"));

            tag(GEMS).addTags(
                    CoFHTags.Items.GEMS_APATITE,
                    CoFHTags.Items.GEMS_CINNABAR,
                    CoFHTags.Items.GEMS_NITER,
                    CoFHTags.Items.GEMS_SULFUR,

                    CoFHTags.Items.GEMS_RUBY,
                    CoFHTags.Items.GEMS_SAPPHIRE
            );

            tag(CoFHTags.Items.INGOTS_LEAD).add(ITEMS.get("lead_ingot"));
            tag(CoFHTags.Items.INGOTS_NICKEL).add(ITEMS.get("nickel_ingot"));
            tag(CoFHTags.Items.INGOTS_SILVER).add(ITEMS.get("silver_ingot"));
            tag(CoFHTags.Items.INGOTS_TIN).add(ITEMS.get("tin_ingot"));

            tag(CoFHTags.Items.INGOTS_BRONZE).add(ITEMS.get("bronze_ingot"));
            tag(CoFHTags.Items.INGOTS_CONSTANTAN).add(ITEMS.get("constantan_ingot"));
            tag(CoFHTags.Items.INGOTS_ELECTRUM).add(ITEMS.get("electrum_ingot"));
            tag(CoFHTags.Items.INGOTS_INVAR).add(ITEMS.get("invar_ingot"));

            tag(INGOTS).addTags(
                    CoFHTags.Items.INGOTS_LEAD,
                    CoFHTags.Items.INGOTS_NICKEL,
                    CoFHTags.Items.INGOTS_SILVER,
                    CoFHTags.Items.INGOTS_TIN,

                    CoFHTags.Items.INGOTS_BRONZE,
                    CoFHTags.Items.INGOTS_CONSTANTAN,
                    CoFHTags.Items.INGOTS_ELECTRUM,
                    CoFHTags.Items.INGOTS_INVAR
            );

            tag(CoFHTags.Items.NUGGETS_LEAD).add(ITEMS.get("lead_nugget"));
            tag(CoFHTags.Items.NUGGETS_NICKEL).add(ITEMS.get("nickel_nugget"));
            tag(CoFHTags.Items.NUGGETS_SILVER).add(ITEMS.get("silver_nugget"));
            tag(CoFHTags.Items.NUGGETS_TIN).add(ITEMS.get("tin_nugget"));

            tag(CoFHTags.Items.NUGGETS_BRONZE).add(ITEMS.get("bronze_nugget"));
            tag(CoFHTags.Items.NUGGETS_CONSTANTAN).add(ITEMS.get("constantan_nugget"));
            tag(CoFHTags.Items.NUGGETS_ELECTRUM).add(ITEMS.get("electrum_nugget"));
            tag(CoFHTags.Items.NUGGETS_INVAR).add(ITEMS.get("invar_nugget"));

            tag(NUGGETS).addTags(
                    CoFHTags.Items.NUGGETS_LEAD,
                    CoFHTags.Items.NUGGETS_NICKEL,
                    CoFHTags.Items.NUGGETS_SILVER,
                    CoFHTags.Items.NUGGETS_TIN,

                    CoFHTags.Items.NUGGETS_BRONZE,
                    CoFHTags.Items.NUGGETS_CONSTANTAN,
                    CoFHTags.Items.NUGGETS_ELECTRUM,
                    CoFHTags.Items.NUGGETS_INVAR
            );

            tag(CoFHTags.Items.PLATES_LEAD).add(ITEMS.get("lead_plate"));
            tag(CoFHTags.Items.PLATES_NICKEL).add(ITEMS.get("nickel_plate"));
            tag(CoFHTags.Items.PLATES_SILVER).add(ITEMS.get("silver_plate"));
            tag(CoFHTags.Items.PLATES_TIN).add(ITEMS.get("tin_plate"));

            tag(CoFHTags.Items.PLATES_BRONZE).add(ITEMS.get("bronze_plate"));
            tag(CoFHTags.Items.PLATES_CONSTANTAN).add(ITEMS.get("constantan_plate"));
            tag(CoFHTags.Items.PLATES_ELECTRUM).add(ITEMS.get("electrum_plate"));
            tag(CoFHTags.Items.PLATES_INVAR).add(ITEMS.get("invar_plate"));

            tag(CoFHTags.Items.PLATES).addTags(
                    CoFHTags.Items.PLATES_LEAD,
                    CoFHTags.Items.PLATES_NICKEL,
                    CoFHTags.Items.PLATES_SILVER,
                    CoFHTags.Items.PLATES_TIN,

                    CoFHTags.Items.PLATES_BRONZE,
                    CoFHTags.Items.PLATES_CONSTANTAN,
                    CoFHTags.Items.PLATES_ELECTRUM,
                    CoFHTags.Items.PLATES_INVAR
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
        }

    }

}
