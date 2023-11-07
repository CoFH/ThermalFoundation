package cofh.thermal.foundation.common.event;

import cofh.thermal.core.common.config.ThermalCoreConfig;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.foundation.init.TFndIDs.ID_RUBBERWOOD_SAPLING;

@Mod.EventBusSubscriber (modid = ID_THERMAL_FOUNDATION)
public class TFndCommonSetupEvents {

    //    @SubscribeEvent
    //    public static void setupVillagerTrades(final VillagerTradesEvent event) {
    //
    //        if (!ThermalConfig.enableVillagerTrades.get()) {
    //            return;
    //        }
    //    }

    @SubscribeEvent
    public static void setupWandererTrades(final WandererTradesEvent event) {

        if (!ThermalCoreConfig.enableWandererTrades.get()) {
            return;
        }
        event.getRareTrades().add(new BasicItemListing(cloneStack(Items.EMERALD, 8), cloneStack(ITEMS.get(ID_RUBBERWOOD_SAPLING)), 8, 1, 0.05F));
    }

}
