package cofh.thermal.core.data;

import cofh.lib.data.LootTableProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreLootTableProvider extends LootTableProviderCoFH {

    public TCoreLootTableProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    public String getName() {

        return "Thermal Core: Loot Tables";
    }

    @Override
    protected void addTables() {

        DeferredRegisterCoFH<Block> regBlocks = BLOCKS;
        DeferredRegisterCoFH<Item> regItems = ITEMS;

        createSimpleDropTable(regBlocks.get(ID_BAMBOO_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CHARCOAL_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_GUNPOWDER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SUGAR_CANE_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_APPLE_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_BEETROOT_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CARROT_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_POTATO_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_APATITE_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CINNABAR_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_NITER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SULFUR_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_COPPER_ORE));
        createSimpleDropTable(regBlocks.get(ID_LEAD_ORE));
        createSimpleDropTable(regBlocks.get(ID_NICKEL_ORE));
        createSimpleDropTable(regBlocks.get(ID_SILVER_ORE));
        createSimpleDropTable(regBlocks.get(ID_TIN_ORE));

        blockLootTables.put(regBlocks.get(ID_RUBY_ORE), getSilkTouchOreTable(regBlocks.get(ID_RUBY_ORE), regItems.get("ruby")));
        blockLootTables.put(regBlocks.get(ID_SAPPHIRE_ORE), getSilkTouchOreTable(regBlocks.get(ID_SAPPHIRE_ORE), regItems.get("sapphire")));

        createSimpleDropTable(regBlocks.get(ID_OIL_RED_SAND));
        createSimpleDropTable(regBlocks.get(ID_OIL_SAND));

        createSimpleDropTable(regBlocks.get(ID_COPPER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_LEAD_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_NICKEL_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SILVER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_TIN_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_BRONZE_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CONSTANTAN_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_ELECTRUM_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_INVAR_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_RUBY_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SAPPHIRE_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_SAWDUST_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_COAL_COKE_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_BITUMEN_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_TAR_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_ROSIN_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_RUBBER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CURED_RUBBER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SLAG_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_RICH_SLAG_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_SIGNALUM_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_LUMIUM_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_ENDERIUM_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_MACHINE_FRAME));
        createSimpleDropTable(regBlocks.get(ID_ENERGY_CELL_FRAME));
        createSimpleDropTable(regBlocks.get(ID_FLUID_CELL_FRAME));

        blockLootTables.put(regBlocks.get(ID_OBSIDIAN_GLASS), BlockLootTables.onlyWithSilkTouch(regBlocks.get(ID_OBSIDIAN_GLASS)));
        blockLootTables.put(regBlocks.get(ID_SIGNALUM_GLASS), BlockLootTables.onlyWithSilkTouch(regBlocks.get(ID_SIGNALUM_GLASS)));
        blockLootTables.put(regBlocks.get(ID_LUMIUM_GLASS), BlockLootTables.onlyWithSilkTouch(regBlocks.get(ID_LUMIUM_GLASS)));
        blockLootTables.put(regBlocks.get(ID_ENDERIUM_GLASS), BlockLootTables.onlyWithSilkTouch(regBlocks.get(ID_ENDERIUM_GLASS)));

        createSimpleDropTable(regBlocks.get(ID_WHITE_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_ORANGE_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_MAGENTA_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_LIGHT_BLUE_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_YELLOW_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_LIME_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_PINK_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_GRAY_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_LIGHT_GRAY_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_CYAN_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_PURPLE_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_BLUE_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_BROWN_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_GREEN_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_RED_ROCKWOOL));
        createSimpleDropTable(regBlocks.get(ID_BLACK_ROCKWOOL));

        createSimpleDropTable(regBlocks.get(ID_POLISHED_SLAG));
        createSimpleDropTable(regBlocks.get(ID_CHISELED_SLAG));
        createSimpleDropTable(regBlocks.get(ID_SLAG_BRICKS));
        createSimpleDropTable(regBlocks.get(ID_CRACKED_SLAG_BRICKS));
        createSimpleDropTable(regBlocks.get(ID_POLISHED_RICH_SLAG));
        createSimpleDropTable(regBlocks.get(ID_CHISELED_RICH_SLAG));
        createSimpleDropTable(regBlocks.get(ID_RICH_SLAG_BRICKS));
        createSimpleDropTable(regBlocks.get(ID_CRACKED_RICH_SLAG_BRICKS));

        createSyncDropTable(regBlocks.get(ID_DEVICE_HIVE_EXTRACTOR));
        createSyncDropTable(regBlocks.get(ID_DEVICE_TREE_EXTRACTOR));
        createSyncDropTable(regBlocks.get(ID_DEVICE_FISHER));
        createSyncDropTable(regBlocks.get(ID_DEVICE_SOIL_INFUSER));
        createSyncDropTable(regBlocks.get(ID_DEVICE_WATER_GEN));
        createSyncDropTable(regBlocks.get(ID_DEVICE_ROCK_GEN));
        createSyncDropTable(regBlocks.get(ID_DEVICE_COLLECTOR));
        createSyncDropTable(regBlocks.get(ID_DEVICE_NULLIFIER));
        createSyncDropTable(regBlocks.get(ID_DEVICE_POTION_DIFFUSER));

        createSyncDropTable(regBlocks.get(ID_TINKER_BENCH));
        createSyncDropTable(regBlocks.get(ID_CHARGE_BENCH));

        createSyncDropTable(regBlocks.get(ID_ENERGY_CELL));
        createSyncDropTable(regBlocks.get(ID_FLUID_CELL));

        createSimpleDropTable(regBlocks.get(ID_SLIME_TNT));
        createSimpleDropTable(regBlocks.get(ID_REDSTONE_TNT));
        createSimpleDropTable(regBlocks.get(ID_GLOWSTONE_TNT));
        createSimpleDropTable(regBlocks.get(ID_ENDER_TNT));

        createSimpleDropTable(regBlocks.get(ID_PHYTO_TNT));

        createSimpleDropTable(regBlocks.get(ID_FIRE_TNT));
        createSimpleDropTable(regBlocks.get(ID_EARTH_TNT));
        createSimpleDropTable(regBlocks.get(ID_ICE_TNT));
        createSimpleDropTable(regBlocks.get(ID_LIGHTNING_TNT));

        createSimpleDropTable(regBlocks.get(ID_NUKE_TNT));

        blockLootTables.put(regBlocks.get(ID_APATITE_ORE), BlockLootTables.droppingWithSilkTouch(regBlocks.get(ID_APATITE_ORE), BlockLootTables.withExplosionDecay(regBlocks.get(ID_APATITE_ORE), ItemLootEntry.builder(regItems.get("apatite"))
                .acceptFunction(SetCount.builder(RandomValueRange.of(4.0F, 9.0F)))
                .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));
        blockLootTables.put(regBlocks.get(ID_CINNABAR_ORE), BlockLootTables.droppingWithSilkTouch(regBlocks.get(ID_CINNABAR_ORE), BlockLootTables.withExplosionDecay(regBlocks.get(ID_CINNABAR_ORE), ItemLootEntry.builder(regItems.get("cinnabar"))
                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));
        blockLootTables.put(regBlocks.get(ID_NITER_ORE), BlockLootTables.droppingWithSilkTouch(regBlocks.get(ID_NITER_ORE), BlockLootTables.withExplosionDecay(regBlocks.get(ID_NITER_ORE), ItemLootEntry.builder(regItems.get("niter"))
                .acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 5.0F)))
                .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));
        blockLootTables.put(regBlocks.get(ID_SULFUR_ORE), BlockLootTables.droppingWithSilkTouch(regBlocks.get(ID_SULFUR_ORE), BlockLootTables.withExplosionDecay(regBlocks.get(ID_SULFUR_ORE), ItemLootEntry.builder(regItems.get("sulfur"))
                .acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 5.0F)))
                .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));

    }

}
