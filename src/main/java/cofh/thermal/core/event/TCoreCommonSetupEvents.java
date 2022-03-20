package cofh.thermal.core.event;

import cofh.thermal.lib.common.ThermalRecipeManagers;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber (modid = ID_THERMAL)
public class TCoreCommonSetupEvents {

    private TCoreCommonSetupEvents() {

    }

    //    @SubscribeEvent
    //    public static void setupVillagerTrades(final VillagerTradesEvent event) {
    //
    //        if (!ThermalConfig.enableVillagerTrades.get()) {
    //            return;
    //        }
    //    }

    // region RELOAD
    @SubscribeEvent
    public static void addReloadListener(final AddReloadListenerEvent event) {

        event.addListener((ResourceManagerReloadListener) manager ->
                ThermalRecipeManagers.instance().setServerRecipeManager(event.getServerResources().getRecipeManager())
        );
    }

    // Recipes reload during TagsUpdatedEvent or IdMapping on Server side.
    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent event) {
        // TODO Lemming, CustomTagTypes sub-event was removed, is this still fine?

        ThermalRecipeManagers.instance().refreshServer();
    }

    @SubscribeEvent
    public static void idRemap(RegistryEvent.IdMappingEvent event) {

        ThermalRecipeManagers.instance().refreshServer();
    }

    // Recipes reload during RecipesUpdatedEvent on Client side.
    @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {

        ThermalRecipeManagers.instance().refreshClient(event.getRecipeManager());
    }

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void biomeLoadingAdd(final BiomeLoadingEvent event) {

        // TODO Lemming, See ConfiguredFeatureCoFH.
//        ThermalBiomeFeatures.addOreGeneration(event);
//        ThermalBiomeFeatures.addOilGeneration(event);
//        ThermalBiomeFeatures.addHostileSpawns(event);
    }
    // endregion
}
