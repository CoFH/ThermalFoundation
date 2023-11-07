package cofh.thermal.foundation.init.data.providers;

import cofh.lib.init.data.LootTableProviderCoFH;
import cofh.thermal.foundation.init.data.tables.TFndBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class TFndLootTableProvider extends LootTableProviderCoFH {

    public TFndLootTableProvider(PackOutput output) {

        super(output, List.of(
                new LootTableProvider.SubProviderEntry(TFndBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }

}
