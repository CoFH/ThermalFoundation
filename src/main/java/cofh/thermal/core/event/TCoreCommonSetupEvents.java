package cofh.thermal.core.event;

import cofh.thermal.lib.common.ThermalRecipeManagers;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

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

    // Recipes reload during TagsUpdatedEvent
    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent event) {

        ThermalRecipeManagers.instance().refreshServer();
        ThermalRecipeManagers.instance().refreshClient();
    }

    // Capture RecipeManager and reload when Recipes update on Client side - this is stupid but necessary since Mojang sends this and TagsUpdate in different orders at different times.
    @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {

        ThermalRecipeManagers.instance().setClientRecipeManager(event.getRecipeManager());
        ThermalRecipeManagers.instance().refreshClient();
    }
    // endregion
}
