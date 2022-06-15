package cofh.thermal.core.data;

import cofh.lib.data.LootTableProviderCoFH;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;

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

        var regBlocks = BLOCKS;
        var regItems = ITEMS;

        createSimpleDropTable(regBlocks.get(ID_BAMBOO_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CHARCOAL_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_GUNPOWDER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SUGAR_CANE_BLOCK));

        createSimpleDropTable(regBlocks.get(ID_APPLE_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_BEETROOT_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CARROT_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_POTATO_BLOCK));

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

        blockLootTables.put(regBlocks.get(ID_OBSIDIAN_GLASS), BlockLoot.createSilkTouchOnlyTable(regBlocks.get(ID_OBSIDIAN_GLASS)));
        blockLootTables.put(regBlocks.get(ID_SIGNALUM_GLASS), BlockLoot.createSilkTouchOnlyTable(regBlocks.get(ID_SIGNALUM_GLASS)));
        blockLootTables.put(regBlocks.get(ID_LUMIUM_GLASS), BlockLoot.createSilkTouchOnlyTable(regBlocks.get(ID_LUMIUM_GLASS)));
        blockLootTables.put(regBlocks.get(ID_ENDERIUM_GLASS), BlockLoot.createSilkTouchOnlyTable(regBlocks.get(ID_ENDERIUM_GLASS)));

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
        createSyncDropTable(regBlocks.get(ID_DEVICE_COMPOSTER));
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
    }

}
