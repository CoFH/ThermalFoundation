package cofh.thermal.foundation.data.tables;

import cofh.lib.data.loot.BlockLootSubProviderCoFH;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.raw;
import static cofh.thermal.foundation.init.TFndIDs.*;

public class TFndBlockLootTables extends BlockLootSubProviderCoFH {

    @Override
    protected void generate() {

        var regBlocks = BLOCKS;
        var regItems = ITEMS;

        add(regBlocks.get(ID_APATITE_ORE), createSilkTouchDispatchTable(regBlocks.get(ID_APATITE_ORE), applyExplosionDecay(regBlocks.get(ID_APATITE_ORE), LootItem.lootTableItem(regItems.get("apatite"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(regBlocks.get(ID_CINNABAR_ORE), createSilkTouchDispatchTable(regBlocks.get(ID_CINNABAR_ORE), applyExplosionDecay(regBlocks.get(ID_CINNABAR_ORE), LootItem.lootTableItem(regItems.get("cinnabar"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(regBlocks.get(ID_NITER_ORE), createSilkTouchDispatchTable(regBlocks.get(ID_NITER_ORE), applyExplosionDecay(regBlocks.get(ID_NITER_ORE), LootItem.lootTableItem(regItems.get("niter"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(regBlocks.get(ID_SULFUR_ORE), createSilkTouchDispatchTable(regBlocks.get(ID_SULFUR_ORE), applyExplosionDecay(regBlocks.get(ID_SULFUR_ORE), LootItem.lootTableItem(regItems.get("sulfur"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        add(regBlocks.get(deepslate(ID_APATITE_ORE)), createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_APATITE_ORE)), applyExplosionDecay(regBlocks.get(ID_APATITE_ORE), LootItem.lootTableItem(regItems.get("apatite"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(regBlocks.get(deepslate(ID_CINNABAR_ORE)), createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_CINNABAR_ORE)), applyExplosionDecay(regBlocks.get(ID_CINNABAR_ORE), LootItem.lootTableItem(regItems.get("cinnabar"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(regBlocks.get(deepslate(ID_NITER_ORE)), createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_NITER_ORE)), applyExplosionDecay(regBlocks.get(ID_NITER_ORE), LootItem.lootTableItem(regItems.get("niter"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(regBlocks.get(deepslate(ID_SULFUR_ORE)), createSilkTouchDispatchTable(regBlocks.get(deepslate(ID_SULFUR_ORE)), applyExplosionDecay(regBlocks.get(ID_SULFUR_ORE), LootItem.lootTableItem(regItems.get("sulfur"))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        add(regBlocks.get(ID_LEAD_ORE), getSilkTouchOreTable(regBlocks.get(ID_LEAD_ORE), regItems.get("raw_lead")));
        add(regBlocks.get(ID_NICKEL_ORE), getSilkTouchOreTable(regBlocks.get(ID_NICKEL_ORE), regItems.get("raw_nickel")));
        add(regBlocks.get(ID_SILVER_ORE), getSilkTouchOreTable(regBlocks.get(ID_SILVER_ORE), regItems.get("raw_silver")));
        add(regBlocks.get(ID_TIN_ORE), getSilkTouchOreTable(regBlocks.get(ID_TIN_ORE), regItems.get("raw_tin")));

        add(regBlocks.get(ID_RUBY_ORE), getSilkTouchOreTable(regBlocks.get(ID_RUBY_ORE), regItems.get("ruby")));
        add(regBlocks.get(ID_SAPPHIRE_ORE), getSilkTouchOreTable(regBlocks.get(ID_SAPPHIRE_ORE), regItems.get("sapphire")));

        add(regBlocks.get(deepslate(ID_LEAD_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_LEAD_ORE)), regItems.get("raw_lead")));
        add(regBlocks.get(deepslate(ID_NICKEL_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_NICKEL_ORE)), regItems.get("raw_nickel")));
        add(regBlocks.get(deepslate(ID_SILVER_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_SILVER_ORE)), regItems.get("raw_silver")));
        add(regBlocks.get(deepslate(ID_TIN_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_TIN_ORE)), regItems.get("raw_tin")));

        add(regBlocks.get(deepslate(ID_RUBY_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_RUBY_ORE)), regItems.get("ruby")));
        add(regBlocks.get(deepslate(ID_SAPPHIRE_ORE)), getSilkTouchOreTable(regBlocks.get(deepslate(ID_SAPPHIRE_ORE)), regItems.get("sapphire")));

        createSimpleDropTable(regBlocks.get(ID_OIL_RED_SAND));
        createSimpleDropTable(regBlocks.get(ID_OIL_SAND));

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
