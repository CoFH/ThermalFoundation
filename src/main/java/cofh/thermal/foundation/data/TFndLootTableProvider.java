package cofh.thermal.foundation.data;

import cofh.lib.data.LootTableProviderCoFH;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TFndLootTableProvider extends LootTableProviderCoFH {

    public TFndLootTableProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    public String getName() {

        return "Thermal Foundation: Loot Tables";
    }

    @Override
    protected void addTables() {

        var regBlocks = BLOCKS;
        var regItems = ITEMS;

        blockLootTables.put(regBlocks.get(ID_APATITE_ORE), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(ID_APATITE_ORE), BlockLoot.applyExplosionDecay(regBlocks.get(ID_APATITE_ORE), LootItem.lootTableItem(regItems.get("apatite"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        blockLootTables.put(regBlocks.get(ID_CINNABAR_ORE), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(ID_CINNABAR_ORE), BlockLoot.applyExplosionDecay(regBlocks.get(ID_CINNABAR_ORE), LootItem.lootTableItem(regItems.get("cinnabar"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        blockLootTables.put(regBlocks.get(ID_NITER_ORE), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(ID_NITER_ORE), BlockLoot.applyExplosionDecay(regBlocks.get(ID_NITER_ORE), LootItem.lootTableItem(regItems.get("niter"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        blockLootTables.put(regBlocks.get(ID_SULFUR_ORE), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(ID_SULFUR_ORE), BlockLoot.applyExplosionDecay(regBlocks.get(ID_SULFUR_ORE), LootItem.lootTableItem(regItems.get("sulfur"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        blockLootTables.put(regBlocks.get(deepslate(ID_APATITE_ORE)), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_APATITE_ORE)), BlockLoot.applyExplosionDecay(regBlocks.get(ID_APATITE_ORE), LootItem.lootTableItem(regItems.get("apatite"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        blockLootTables.put(regBlocks.get(deepslate(ID_CINNABAR_ORE)), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_CINNABAR_ORE)), BlockLoot.applyExplosionDecay(regBlocks.get(ID_CINNABAR_ORE), LootItem.lootTableItem(regItems.get("cinnabar"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        blockLootTables.put(regBlocks.get(deepslate(ID_NITER_ORE)), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_NITER_ORE)), BlockLoot.applyExplosionDecay(regBlocks.get(ID_NITER_ORE), LootItem.lootTableItem(regItems.get("niter"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        blockLootTables.put(regBlocks.get(deepslate(ID_SULFUR_ORE)), BlockLoot.createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_SULFUR_ORE)), BlockLoot.applyExplosionDecay(regBlocks.get(ID_SULFUR_ORE), LootItem.lootTableItem(regItems.get("sulfur"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        blockLootTables.put(regBlocks.get(ID_LEAD_ORE), getSilkTouchOreTable(regBlocks.get(ID_LEAD_ORE), regItems.get("raw_lead")));
        blockLootTables.put(regBlocks.get(ID_NICKEL_ORE), getSilkTouchOreTable(regBlocks.get(ID_NICKEL_ORE), regItems.get("raw_nickel")));
        blockLootTables.put(regBlocks.get(ID_SILVER_ORE), getSilkTouchOreTable(regBlocks.get(ID_SILVER_ORE), regItems.get("raw_silver")));
        blockLootTables.put(regBlocks.get(ID_TIN_ORE), getSilkTouchOreTable(regBlocks.get(ID_TIN_ORE), regItems.get("raw_tin")));

        blockLootTables.put(regBlocks.get(ID_RUBY_ORE), getSilkTouchOreTable(regBlocks.get(ID_RUBY_ORE), regItems.get("ruby")));
        blockLootTables.put(regBlocks.get(ID_SAPPHIRE_ORE), getSilkTouchOreTable(regBlocks.get(ID_SAPPHIRE_ORE), regItems.get("sapphire")));

        blockLootTables.put(regBlocks.get(deepslate(ID_LEAD_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_LEAD_ORE)), regItems.get("raw_lead")));
        blockLootTables.put(regBlocks.get(deepslate(ID_NICKEL_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_NICKEL_ORE)), regItems.get("raw_nickel")));
        blockLootTables.put(regBlocks.get(deepslate(ID_SILVER_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_SILVER_ORE)), regItems.get("raw_silver")));
        blockLootTables.put(regBlocks.get(deepslate(ID_TIN_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_TIN_ORE)), regItems.get("raw_tin")));

        blockLootTables.put(regBlocks.get(deepslate(ID_RUBY_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_RUBY_ORE)), regItems.get("ruby")));
        blockLootTables.put(regBlocks.get(deepslate(ID_SAPPHIRE_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_SAPPHIRE_ORE)), regItems.get("sapphire")));

        createSimpleDropTable(regBlocks.get(ID_OIL_RED_SAND));
        createSimpleDropTable(regBlocks.get(ID_OIL_SAND));

        createSimpleDropTable(regBlocks.get(ID_APATITE_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_CINNABAR_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_NITER_BLOCK));
        createSimpleDropTable(regBlocks.get(ID_SULFUR_BLOCK));

        createSimpleDropTable(regBlocks.get(raw(ID_LEAD_BLOCK)));
        createSimpleDropTable(regBlocks.get(raw(ID_NICKEL_BLOCK)));
        createSimpleDropTable(regBlocks.get(raw(ID_SILVER_BLOCK)));
        createSimpleDropTable(regBlocks.get(raw(ID_TIN_BLOCK)));

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
    }

}
