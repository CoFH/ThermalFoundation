package cofh.thermal.core.data;

import cofh.lib.util.references.BlockTagsCoFH;
import cofh.lib.util.references.FluidTagsCoFH;
import cofh.lib.util.references.ItemTagsCoFH;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.*;
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

        @Override
        protected void registerTags() {

            getOrCreateBuilder(BlockTagsCoFH.HARDENED_GLASS).add(
                    BLOCKS.get(ID_OBSIDIAN_GLASS),
                    BLOCKS.get(ID_SIGNALUM_GLASS),
                    BLOCKS.get(ID_LUMIUM_GLASS),
                    BLOCKS.get(ID_ENDERIUM_GLASS));

            getOrCreateBuilder(BlockTags.IMPERMEABLE).add(
                    BLOCKS.get(ID_OBSIDIAN_GLASS),
                    BLOCKS.get(ID_SIGNALUM_GLASS),
                    BLOCKS.get(ID_LUMIUM_GLASS),
                    BLOCKS.get(ID_ENDERIUM_GLASS));

            getOrCreateBuilder(BlockTagsCoFH.ROCKWOOL).add(
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
                    BLOCKS.get(ID_BLACK_ROCKWOOL));

            getOrCreateBuilder(BlockTagsCoFH.ORES_APATITE).add(BLOCKS.get(ID_APATITE_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_CINNABAR).add(BLOCKS.get(ID_CINNABAR_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_COPPER).add(BLOCKS.get(ID_COPPER_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_LEAD).add(BLOCKS.get(ID_LEAD_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_NICKEL).add(BLOCKS.get(ID_NICKEL_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_NITER).add(BLOCKS.get(ID_NITER_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_RUBY).add(BLOCKS.get(ID_RUBY_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_SILVER).add(BLOCKS.get(ID_SILVER_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_SULFUR).add(BLOCKS.get(ID_SULFUR_ORE));
            getOrCreateBuilder(BlockTagsCoFH.ORES_TIN).add(BLOCKS.get(ID_TIN_ORE));

            getOrCreateBuilder(Tags.Blocks.ORES).addTags(
                    BlockTagsCoFH.ORES_APATITE,
                    BlockTagsCoFH.ORES_CINNABAR,
                    BlockTagsCoFH.ORES_COPPER,
                    BlockTagsCoFH.ORES_LEAD,
                    BlockTagsCoFH.ORES_NICKEL,
                    BlockTagsCoFH.ORES_NITER,
                    BlockTagsCoFH.ORES_RUBY,
                    BlockTagsCoFH.ORES_SAPPHIRE,
                    BlockTagsCoFH.ORES_SILVER,
                    BlockTagsCoFH.ORES_SULFUR,
                    BlockTagsCoFH.ORES_TIN
            );

            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_APATITE).add(BLOCKS.get(ID_APATITE_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_BAMBOO).add(BLOCKS.get(ID_BAMBOO_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_BITUMEN).add(BLOCKS.get(ID_BITUMEN_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_BRONZE).add(BLOCKS.get(ID_BRONZE_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_CHARCOAL).add(BLOCKS.get(ID_CHARCOAL_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_CINNABAR).add(BLOCKS.get(ID_CINNABAR_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_COAL_COKE).add(BLOCKS.get(ID_COAL_COKE_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_CONSTANTAN).add(BLOCKS.get(ID_CONSTANTAN_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_COPPER).add(BLOCKS.get(ID_COPPER_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_ELECTRUM).add(BLOCKS.get(ID_ELECTRUM_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_ENDERIUM).add(BLOCKS.get(ID_ENDERIUM_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_GUNPOWDER).add(BLOCKS.get(ID_GUNPOWDER_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_INVAR).add(BLOCKS.get(ID_INVAR_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_LEAD).add(BLOCKS.get(ID_LEAD_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_LUMIUM).add(BLOCKS.get(ID_LUMIUM_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_NICKEL).add(BLOCKS.get(ID_NICKEL_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_NITER).add(BLOCKS.get(ID_NITER_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_RUBY).add(BLOCKS.get(ID_RUBY_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_SAPPHIRE).add(BLOCKS.get(ID_SAPPHIRE_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_SIGNALUM).add(BLOCKS.get(ID_SIGNALUM_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_SILVER).add(BLOCKS.get(ID_SILVER_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_SLAG).add(BLOCKS.get(ID_SLAG_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE).add(BLOCKS.get(ID_SUGAR_CANE_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_SULFUR).add(BLOCKS.get(ID_SULFUR_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_TAR).add(BLOCKS.get(ID_TAR_BLOCK));
            getOrCreateBuilder(BlockTagsCoFH.STORAGE_BLOCKS_TIN).add(BLOCKS.get(ID_TIN_BLOCK));

            getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).addTags(
                    BlockTagsCoFH.STORAGE_BLOCKS_APATITE,
                    BlockTagsCoFH.STORAGE_BLOCKS_BAMBOO,
                    BlockTagsCoFH.STORAGE_BLOCKS_BITUMEN,
                    BlockTagsCoFH.STORAGE_BLOCKS_BRONZE,
                    BlockTagsCoFH.STORAGE_BLOCKS_CHARCOAL,
                    BlockTagsCoFH.STORAGE_BLOCKS_CINNABAR,
                    BlockTagsCoFH.STORAGE_BLOCKS_COAL_COKE,
                    BlockTagsCoFH.STORAGE_BLOCKS_CONSTANTAN,
                    BlockTagsCoFH.STORAGE_BLOCKS_COPPER,
                    BlockTagsCoFH.STORAGE_BLOCKS_ELECTRUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_ENDERIUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_GUNPOWDER,
                    BlockTagsCoFH.STORAGE_BLOCKS_INVAR,
                    BlockTagsCoFH.STORAGE_BLOCKS_LEAD,
                    BlockTagsCoFH.STORAGE_BLOCKS_LUMIUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_NICKEL,
                    BlockTagsCoFH.STORAGE_BLOCKS_NITER,
                    BlockTagsCoFH.STORAGE_BLOCKS_RUBY,
                    BlockTagsCoFH.STORAGE_BLOCKS_SAPPHIRE,
                    BlockTagsCoFH.STORAGE_BLOCKS_SIGNALUM,
                    BlockTagsCoFH.STORAGE_BLOCKS_SILVER,
                    BlockTagsCoFH.STORAGE_BLOCKS_SLAG,
                    BlockTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE,
                    BlockTagsCoFH.STORAGE_BLOCKS_SULFUR,
                    BlockTagsCoFH.STORAGE_BLOCKS_TAR,
                    BlockTagsCoFH.STORAGE_BLOCKS_TIN
            );
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
        protected void registerTags() {

            copy(BlockTagsCoFH.HARDENED_GLASS, ItemTagsCoFH.HARDENED_GLASS);
            copy(BlockTagsCoFH.ROCKWOOL, ItemTagsCoFH.ROCKWOOL);

            copy(BlockTagsCoFH.ORES_APATITE, ItemTagsCoFH.ORES_APATITE);
            copy(BlockTagsCoFH.ORES_CINNABAR, ItemTagsCoFH.ORES_CINNABAR);
            copy(BlockTagsCoFH.ORES_COPPER, ItemTagsCoFH.ORES_COPPER);
            copy(BlockTagsCoFH.ORES_LEAD, ItemTagsCoFH.ORES_LEAD);
            copy(BlockTagsCoFH.ORES_NICKEL, ItemTagsCoFH.ORES_NICKEL);
            copy(BlockTagsCoFH.ORES_NITER, ItemTagsCoFH.ORES_NITER);
            copy(BlockTagsCoFH.ORES_RUBY, ItemTagsCoFH.ORES_RUBY);
            copy(BlockTagsCoFH.ORES_SAPPHIRE, ItemTagsCoFH.ORES_SAPPHIRE);
            copy(BlockTagsCoFH.ORES_SILVER, ItemTagsCoFH.ORES_SILVER);
            copy(BlockTagsCoFH.ORES_SULFUR, ItemTagsCoFH.ORES_SULFUR);
            copy(BlockTagsCoFH.ORES_TIN, ItemTagsCoFH.ORES_TIN);

            getOrCreateBuilder(Tags.Items.ORES).addTags(
                    ItemTagsCoFH.ORES_APATITE,
                    ItemTagsCoFH.ORES_CINNABAR,
                    ItemTagsCoFH.ORES_COPPER,
                    ItemTagsCoFH.ORES_LEAD,
                    ItemTagsCoFH.ORES_NICKEL,
                    ItemTagsCoFH.ORES_NITER,
                    ItemTagsCoFH.ORES_RUBY,
                    ItemTagsCoFH.ORES_SAPPHIRE,
                    ItemTagsCoFH.ORES_SILVER,
                    ItemTagsCoFH.ORES_SULFUR,
                    ItemTagsCoFH.ORES_TIN
            );

            copy(BlockTagsCoFH.STORAGE_BLOCKS_APATITE, ItemTagsCoFH.STORAGE_BLOCKS_APATITE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_BAMBOO, ItemTagsCoFH.STORAGE_BLOCKS_BAMBOO);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_BITUMEN, ItemTagsCoFH.STORAGE_BLOCKS_BITUMEN);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_BRONZE, ItemTagsCoFH.STORAGE_BLOCKS_BRONZE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_CHARCOAL, ItemTagsCoFH.STORAGE_BLOCKS_CHARCOAL);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_CINNABAR, ItemTagsCoFH.STORAGE_BLOCKS_CINNABAR);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_COAL_COKE, ItemTagsCoFH.STORAGE_BLOCKS_COAL_COKE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_CONSTANTAN, ItemTagsCoFH.STORAGE_BLOCKS_CONSTANTAN);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_COPPER, ItemTagsCoFH.STORAGE_BLOCKS_COPPER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_ELECTRUM, ItemTagsCoFH.STORAGE_BLOCKS_ELECTRUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_ENDERIUM, ItemTagsCoFH.STORAGE_BLOCKS_ENDERIUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_GUNPOWDER, ItemTagsCoFH.STORAGE_BLOCKS_GUNPOWDER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_INVAR, ItemTagsCoFH.STORAGE_BLOCKS_INVAR);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_LEAD, ItemTagsCoFH.STORAGE_BLOCKS_LEAD);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_LUMIUM, ItemTagsCoFH.STORAGE_BLOCKS_LUMIUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_NICKEL, ItemTagsCoFH.STORAGE_BLOCKS_NICKEL);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_NITER, ItemTagsCoFH.STORAGE_BLOCKS_NITER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_RUBY, ItemTagsCoFH.STORAGE_BLOCKS_RUBY);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SAPPHIRE, ItemTagsCoFH.STORAGE_BLOCKS_SAPPHIRE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SIGNALUM, ItemTagsCoFH.STORAGE_BLOCKS_SIGNALUM);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SILVER, ItemTagsCoFH.STORAGE_BLOCKS_SILVER);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SLAG, ItemTagsCoFH.STORAGE_BLOCKS_SLAG);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE, ItemTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_SULFUR, ItemTagsCoFH.STORAGE_BLOCKS_SULFUR);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_TAR, ItemTagsCoFH.STORAGE_BLOCKS_TAR);
            copy(BlockTagsCoFH.STORAGE_BLOCKS_TIN, ItemTagsCoFH.STORAGE_BLOCKS_TIN);

            getOrCreateBuilder(Tags.Items.STORAGE_BLOCKS).addTags(
                    ItemTagsCoFH.STORAGE_BLOCKS_APATITE,
                    ItemTagsCoFH.STORAGE_BLOCKS_BAMBOO,
                    ItemTagsCoFH.STORAGE_BLOCKS_BRONZE,
                    ItemTagsCoFH.STORAGE_BLOCKS_CHARCOAL,
                    ItemTagsCoFH.STORAGE_BLOCKS_CINNABAR,
                    ItemTagsCoFH.STORAGE_BLOCKS_COAL_COKE,
                    ItemTagsCoFH.STORAGE_BLOCKS_CONSTANTAN,
                    ItemTagsCoFH.STORAGE_BLOCKS_COPPER,
                    ItemTagsCoFH.STORAGE_BLOCKS_ELECTRUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_ENDERIUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_GUNPOWDER,
                    ItemTagsCoFH.STORAGE_BLOCKS_INVAR,
                    ItemTagsCoFH.STORAGE_BLOCKS_LEAD,
                    ItemTagsCoFH.STORAGE_BLOCKS_LUMIUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_NICKEL,
                    ItemTagsCoFH.STORAGE_BLOCKS_NITER,
                    ItemTagsCoFH.STORAGE_BLOCKS_RUBY,
                    ItemTagsCoFH.STORAGE_BLOCKS_SAPPHIRE,
                    ItemTagsCoFH.STORAGE_BLOCKS_SIGNALUM,
                    ItemTagsCoFH.STORAGE_BLOCKS_SILVER,
                    ItemTagsCoFH.STORAGE_BLOCKS_SUGAR_CANE,
                    ItemTagsCoFH.STORAGE_BLOCKS_SULFUR,
                    ItemTagsCoFH.STORAGE_BLOCKS_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.COINS_BRONZE).add(ITEMS.get("bronze_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_CONSTANTAN).add(ITEMS.get("constantan_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_COPPER).add(ITEMS.get("copper_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_ELECTRUM).add(ITEMS.get("electrum_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_ENDERIUM).add(ITEMS.get("enderium_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_GOLD).add(ITEMS.get("gold_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_INVAR).add(ITEMS.get("invar_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_IRON).add(ITEMS.get("iron_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_LEAD).add(ITEMS.get("lead_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_LUMIUM).add(ITEMS.get("lumium_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_NICKEL).add(ITEMS.get("nickel_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_SIGNALUM).add(ITEMS.get("signalum_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_SILVER).add(ITEMS.get("silver_coin"));
            getOrCreateBuilder(ItemTagsCoFH.COINS_TIN).add(ITEMS.get("tin_coin"));

            getOrCreateBuilder(ItemTagsCoFH.COINS).addTags(
                    ItemTagsCoFH.COINS_BRONZE,
                    ItemTagsCoFH.COINS_CONSTANTAN,
                    ItemTagsCoFH.COINS_COPPER,
                    ItemTagsCoFH.COINS_ELECTRUM,
                    ItemTagsCoFH.COINS_ENDERIUM,
                    ItemTagsCoFH.COINS_GOLD,
                    ItemTagsCoFH.COINS_INVAR,
                    ItemTagsCoFH.COINS_IRON,
                    ItemTagsCoFH.COINS_LEAD,
                    ItemTagsCoFH.COINS_LUMIUM,
                    ItemTagsCoFH.COINS_NICKEL,
                    ItemTagsCoFH.COINS_SIGNALUM,
                    ItemTagsCoFH.COINS_SILVER,
                    ItemTagsCoFH.COINS_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.DUSTS_BRONZE).add(ITEMS.get("bronze_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_CONSTANTAN).add(ITEMS.get("constantan_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_COPPER).add(ITEMS.get("copper_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_DIAMOND).add(ITEMS.get("diamond_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_ELECTRUM).add(ITEMS.get("electrum_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_EMERALD).add(ITEMS.get("emerald_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_ENDERIUM).add(ITEMS.get("enderium_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_GOLD).add(ITEMS.get("gold_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_INVAR).add(ITEMS.get("invar_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_IRON).add(ITEMS.get("iron_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_LAPIS).add(ITEMS.get("lapis_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_LEAD).add(ITEMS.get("lead_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_LUMIUM).add(ITEMS.get("lumium_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_NICKEL).add(ITEMS.get("nickel_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_QUARTZ).add(ITEMS.get("quartz_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_RUBY).add(ITEMS.get("ruby_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_SAPPHIRE).add(ITEMS.get("sapphire_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_SIGNALUM).add(ITEMS.get("signalum_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_SILVER).add(ITEMS.get("silver_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_TIN).add(ITEMS.get("tin_dust"));

            getOrCreateBuilder(DUSTS).addTags(
                    ItemTagsCoFH.DUSTS_APATITE,
                    ItemTagsCoFH.DUSTS_BRONZE,
                    ItemTagsCoFH.DUSTS_CINNABAR,
                    ItemTagsCoFH.DUSTS_CONSTANTAN,
                    ItemTagsCoFH.DUSTS_COPPER,
                    ItemTagsCoFH.DUSTS_DIAMOND,
                    ItemTagsCoFH.DUSTS_ELECTRUM,
                    ItemTagsCoFH.DUSTS_EMERALD,
                    ItemTagsCoFH.DUSTS_ENDER_PEARL,
                    ItemTagsCoFH.DUSTS_ENDERIUM,
                    ItemTagsCoFH.DUSTS_GOLD,
                    ItemTagsCoFH.DUSTS_INVAR,
                    ItemTagsCoFH.DUSTS_IRON,
                    ItemTagsCoFH.DUSTS_LAPIS,
                    ItemTagsCoFH.DUSTS_LEAD,
                    ItemTagsCoFH.DUSTS_LUMIUM,
                    ItemTagsCoFH.DUSTS_NICKEL,
                    ItemTagsCoFH.DUSTS_NITER,
                    ItemTagsCoFH.DUSTS_QUARTZ,
                    ItemTagsCoFH.DUSTS_RUBY,
                    ItemTagsCoFH.DUSTS_SAPPHIRE,
                    ItemTagsCoFH.DUSTS_SIGNALUM,
                    ItemTagsCoFH.DUSTS_SILVER,
                    ItemTagsCoFH.DUSTS_SULFUR,
                    ItemTagsCoFH.DUSTS_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.GEARS_BRONZE).add(ITEMS.get("bronze_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_CONSTANTAN).add(ITEMS.get("constantan_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_COPPER).add(ITEMS.get("copper_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_DIAMOND).add(ITEMS.get("diamond_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_ELECTRUM).add(ITEMS.get("electrum_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_EMERALD).add(ITEMS.get("emerald_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_ENDERIUM).add(ITEMS.get("enderium_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_GOLD).add(ITEMS.get("gold_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_INVAR).add(ITEMS.get("invar_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_IRON).add(ITEMS.get("iron_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_LAPIS).add(ITEMS.get("lapis_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_LEAD).add(ITEMS.get("lead_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_LUMIUM).add(ITEMS.get("lumium_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_NICKEL).add(ITEMS.get("nickel_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_QUARTZ).add(ITEMS.get("quartz_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_RUBY).add(ITEMS.get("ruby_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_SAPPHIRE).add(ITEMS.get("sapphire_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_SIGNALUM).add(ITEMS.get("signalum_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_SILVER).add(ITEMS.get("silver_gear"));
            getOrCreateBuilder(ItemTagsCoFH.GEARS_TIN).add(ITEMS.get("tin_gear"));

            getOrCreateBuilder(ItemTagsCoFH.GEARS).addTags(
                    ItemTagsCoFH.GEARS_BRONZE,
                    ItemTagsCoFH.GEARS_CONSTANTAN,
                    ItemTagsCoFH.GEARS_COPPER,
                    ItemTagsCoFH.GEARS_DIAMOND,
                    ItemTagsCoFH.GEARS_ELECTRUM,
                    ItemTagsCoFH.GEARS_EMERALD,
                    ItemTagsCoFH.GEARS_ENDERIUM,
                    ItemTagsCoFH.GEARS_GOLD,
                    ItemTagsCoFH.GEARS_INVAR,
                    ItemTagsCoFH.GEARS_IRON,
                    ItemTagsCoFH.GEARS_LAPIS,
                    ItemTagsCoFH.GEARS_LEAD,
                    ItemTagsCoFH.GEARS_LUMIUM,
                    ItemTagsCoFH.GEARS_NICKEL,
                    ItemTagsCoFH.GEARS_QUARTZ,
                    ItemTagsCoFH.GEARS_RUBY,
                    ItemTagsCoFH.GEARS_SAPPHIRE,
                    ItemTagsCoFH.GEARS_SIGNALUM,
                    ItemTagsCoFH.GEARS_SILVER,
                    ItemTagsCoFH.GEARS_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.GEMS_APATITE).add(ITEMS.get("apatite"));
            getOrCreateBuilder(ItemTagsCoFH.GEMS_CINNABAR).add(ITEMS.get("cinnabar"));
            getOrCreateBuilder(ItemTagsCoFH.GEMS_NITER).add(ITEMS.get("niter"));
            getOrCreateBuilder(ItemTagsCoFH.GEMS_RUBY).add(ITEMS.get("ruby"));
            getOrCreateBuilder(ItemTagsCoFH.GEMS_SAPPHIRE).add(ITEMS.get("sapphire"));
            getOrCreateBuilder(ItemTagsCoFH.GEMS_SULFUR).add(ITEMS.get("sulfur"));

            getOrCreateBuilder(GEMS).addTags(
                    ItemTagsCoFH.GEMS_APATITE,
                    ItemTagsCoFH.GEMS_CINNABAR,
                    ItemTagsCoFH.GEMS_NITER,
                    ItemTagsCoFH.GEMS_RUBY,
                    ItemTagsCoFH.GEMS_SAPPHIRE,
                    ItemTagsCoFH.GEMS_SULFUR
            );

            getOrCreateBuilder(ItemTagsCoFH.INGOTS_BRONZE).add(ITEMS.get("bronze_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_CONSTANTAN).add(ITEMS.get("constantan_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_COPPER).add(ITEMS.get("copper_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_ELECTRUM).add(ITEMS.get("electrum_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_ENDERIUM).add(ITEMS.get("enderium_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_INVAR).add(ITEMS.get("invar_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_LEAD).add(ITEMS.get("lead_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_LUMIUM).add(ITEMS.get("lumium_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_NICKEL).add(ITEMS.get("nickel_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_SIGNALUM).add(ITEMS.get("signalum_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_SILVER).add(ITEMS.get("silver_ingot"));
            getOrCreateBuilder(ItemTagsCoFH.INGOTS_TIN).add(ITEMS.get("tin_ingot"));

            getOrCreateBuilder(INGOTS).addTags(
                    ItemTagsCoFH.INGOTS_BRONZE,
                    ItemTagsCoFH.INGOTS_CONSTANTAN,
                    ItemTagsCoFH.INGOTS_COPPER,
                    ItemTagsCoFH.INGOTS_ELECTRUM,
                    ItemTagsCoFH.INGOTS_ENDERIUM,
                    ItemTagsCoFH.INGOTS_INVAR,
                    ItemTagsCoFH.INGOTS_LEAD,
                    ItemTagsCoFH.INGOTS_LUMIUM,
                    ItemTagsCoFH.INGOTS_NICKEL,
                    ItemTagsCoFH.INGOTS_SIGNALUM,
                    ItemTagsCoFH.INGOTS_SILVER,
                    ItemTagsCoFH.INGOTS_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_BRONZE).add(ITEMS.get("bronze_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_CONSTANTAN).add(ITEMS.get("constantan_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_COPPER).add(ITEMS.get("copper_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_DIAMOND).add(ITEMS.get("diamond_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_ELECTRUM).add(ITEMS.get("electrum_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_EMERALD).add(ITEMS.get("emerald_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_ENDERIUM).add(ITEMS.get("enderium_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_INVAR).add(ITEMS.get("invar_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_LAPIS).add(ITEMS.get("lapis_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_LEAD).add(ITEMS.get("lead_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_LUMIUM).add(ITEMS.get("lumium_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_NICKEL).add(ITEMS.get("nickel_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_QUARTZ).add(ITEMS.get("quartz_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_RUBY).add(ITEMS.get("ruby_nugget"));
            // getOrCreateBuilder(CoFHTags.Items.NUGGETS_SAPPHIRE).add(ITEMS.get("sapphire_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_SIGNALUM).add(ITEMS.get("signalum_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_SILVER).add(ITEMS.get("silver_nugget"));
            getOrCreateBuilder(ItemTagsCoFH.NUGGETS_TIN).add(ITEMS.get("tin_nugget"));

            getOrCreateBuilder(NUGGETS).addTags(
                    ItemTagsCoFH.NUGGETS_BRONZE,
                    ItemTagsCoFH.NUGGETS_CONSTANTAN,
                    ItemTagsCoFH.NUGGETS_COPPER,
                    ItemTagsCoFH.NUGGETS_ELECTRUM,
                    ItemTagsCoFH.NUGGETS_ENDERIUM,
                    ItemTagsCoFH.NUGGETS_INVAR,
                    ItemTagsCoFH.NUGGETS_LEAD,
                    ItemTagsCoFH.NUGGETS_LUMIUM,
                    ItemTagsCoFH.NUGGETS_NICKEL,
                    ItemTagsCoFH.NUGGETS_SIGNALUM,
                    ItemTagsCoFH.NUGGETS_SILVER,
                    ItemTagsCoFH.NUGGETS_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.PLATES_BRONZE).add(ITEMS.get("bronze_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_CONSTANTAN).add(ITEMS.get("constantan_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_COPPER).add(ITEMS.get("copper_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_DIAMOND).add(ITEMS.get("diamond_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_ELECTRUM).add(ITEMS.get("electrum_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_EMERALD).add(ITEMS.get("emerald_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_ENDERIUM).add(ITEMS.get("enderium_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_GOLD).add(ITEMS.get("gold_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_INVAR).add(ITEMS.get("invar_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_IRON).add(ITEMS.get("iron_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_LAPIS).add(ITEMS.get("lapis_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_LEAD).add(ITEMS.get("lead_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_LUMIUM).add(ITEMS.get("lumium_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_NICKEL).add(ITEMS.get("nickel_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_QUARTZ).add(ITEMS.get("quartz_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_RUBY).add(ITEMS.get("ruby_plate"));
            // getOrCreateBuilder(CoFHTags.Items.PLATES_SAPPHIRE).add(ITEMS.get("sapphire_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_SIGNALUM).add(ITEMS.get("signalum_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_SILVER).add(ITEMS.get("silver_plate"));
            getOrCreateBuilder(ItemTagsCoFH.PLATES_TIN).add(ITEMS.get("tin_plate"));

            getOrCreateBuilder(ItemTagsCoFH.PLATES).addTags(
                    ItemTagsCoFH.PLATES_BRONZE,
                    ItemTagsCoFH.PLATES_CONSTANTAN,
                    ItemTagsCoFH.PLATES_COPPER,
                    ItemTagsCoFH.PLATES_ELECTRUM,
                    ItemTagsCoFH.PLATES_ENDERIUM,
                    ItemTagsCoFH.PLATES_GOLD,
                    ItemTagsCoFH.PLATES_INVAR,
                    ItemTagsCoFH.PLATES_IRON,
                    ItemTagsCoFH.PLATES_LEAD,
                    ItemTagsCoFH.PLATES_LUMIUM,
                    ItemTagsCoFH.PLATES_NICKEL,
                    ItemTagsCoFH.PLATES_SIGNALUM,
                    ItemTagsCoFH.PLATES_SILVER,
                    ItemTagsCoFH.PLATES_TIN
            );

            getOrCreateBuilder(ItemTagsCoFH.TOOLS_WRENCH).add(ITEMS.get("wrench"));

            getOrCreateBuilder(ItemTagsCoFH.BITUMEN).add(ITEMS.get("bitumen"));
            getOrCreateBuilder(ItemTagsCoFH.COAL_COKE).add(ITEMS.get("coal_coke"));
            getOrCreateBuilder(ItemTagsCoFH.SAWDUST).add(ITEMS.get("sawdust"));
            getOrCreateBuilder(ItemTagsCoFH.SLAG).add(ITEMS.get("slag"));
            getOrCreateBuilder(ItemTagsCoFH.TAR).add(ITEMS.get("tar"));

            getOrCreateBuilder(ItemTagsCoFH.DUSTS_APATITE).add(ITEMS.get("apatite_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_CINNABAR).add(ITEMS.get("cinnabar_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_NITER).add(ITEMS.get("niter_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_SULFUR).add(ITEMS.get("sulfur_dust"));
            getOrCreateBuilder(ItemTagsCoFH.DUSTS_WOOD).add(ITEMS.get("sawdust"));

            getOrCreateBuilder(ItemTagsCoFH.DUSTS_ENDER_PEARL).add(ITEMS.get("ender_pearl_dust"));
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
        protected void registerTags() {

            getOrCreateBuilder(FluidTagsCoFH.REDSTONE).add(FLUIDS.get(ID_FLUID_REDSTONE));
            getOrCreateBuilder(FluidTagsCoFH.GLOWSTONE).add(FLUIDS.get(ID_FLUID_GLOWSTONE));
            getOrCreateBuilder(FluidTagsCoFH.ENDER).add(FLUIDS.get(ID_FLUID_ENDER));

            getOrCreateBuilder(FluidTagsCoFH.LATEX).add(FLUIDS.get(ID_FLUID_LATEX));

            getOrCreateBuilder(FluidTagsCoFH.CREOSOTE).add(FLUIDS.get(ID_FLUID_CREOSOTE));
            getOrCreateBuilder(FluidTagsCoFH.CRUDE_OIL).add(FLUIDS.get(ID_FLUID_CRUDE_OIL));
        }

    }

}
