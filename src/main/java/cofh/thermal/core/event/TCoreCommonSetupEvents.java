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

    // Recipes reload during TagsUpdatedEvent (and IdMapping on Server side)
    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent event) {

        ThermalRecipeManagers.instance().refreshServer();
        ThermalRecipeManagers.instance().refreshClient();
    }

    @SubscribeEvent
    public static void idRemap(RegistryEvent.IdMappingEvent event) {

        ThermalRecipeManagers.instance().refreshServer();
    }

    // Capture RecipeManager when Recipes update on Client side.
    @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {

        ThermalRecipeManagers.instance().setClientRecipeManager(event.getRecipeManager());
    }
    // endregion
}
