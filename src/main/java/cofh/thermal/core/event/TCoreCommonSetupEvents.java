package cofh.thermal.core.event;

import cofh.thermal.core.world.biome.ThermalBiomeFeatures;
import cofh.thermal.lib.common.ThermalRecipeManagers;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLModIdMappingEvent;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber(modid = ID_THERMAL)
public class TCoreCommonSetupEvents {

    private TCoreCommonSetupEvents() {

    }

    //    // TODO: Remove in 1.2.0
    //    @SubscribeEvent
    //    public static void remapBlocks(final RegistryEvent.MissingMappings<Block> event) {
    //
    //        ResourceLocation COAL_COKE_OLD = new ResourceLocation("thermal:coal_coke_block");
    //
    //        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
    //            if (mapping.key.equals(COAL_COKE_OLD)) {
    //                mapping.remap(BLOCKS.get("coke_block"));
    //            }
    //        }
    //    }
    //
    //    // TODO: Remove in 1.2.0
    //    @SubscribeEvent
    //    public static void remapItems(final RegistryEvent.MissingMappings<Item> event) {
    //
    //        ResourceLocation COAL_COKE_BLOCK_OLD = new ResourceLocation("thermal:coal_coke_block");
    //        ResourceLocation COAL_COKE_ITEM_OLD = new ResourceLocation("thermal:coal_coke");
    //
    //        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
    //            if (mapping.key.equals(COAL_COKE_BLOCK_OLD)) {
    //                mapping.remap(ITEMS.get("coke_block"));
    //            } else if (mapping.key.equals(COAL_COKE_ITEM_OLD)) {
    //                mapping.remap(ITEMS.get("coke"));
    //            }
    //        }
    //    }

    @SubscribeEvent
    public static void addReloadListener(final AddReloadListenerEvent event) {

        event.addListener(
                new ReloadListener<Void>() {

                    @Override
                    protected Void prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {

                        ThermalRecipeManagers.instance().setServerRecipeManager(event.getDataPackRegistries().getRecipeManager());
                        return null;
                    }

                    @Override
                    protected void apply(Void nothing, IResourceManager resourceManagerIn, IProfiler profilerIn) {

                    }
                }
        );
    }

    // Recipes reload during TagsUpdatedEvent or IdMapping on Server side.
    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent.CustomTagTypes event) {

        ThermalRecipeManagers.instance().refreshServer();
    }

    @SubscribeEvent
    public static void idRemap(final FMLModIdMappingEvent event) {

        ThermalRecipeManagers.instance().refreshServer();
    }

    // Recipes reload during RecipesUpdatedEvent on Client side.
    @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {

        ThermalRecipeManagers.instance().refreshClient(event.getRecipeManager());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void biomeLoadingAdd(final BiomeLoadingEvent event) {

        ThermalBiomeFeatures.addOreGeneration(event);
        ThermalBiomeFeatures.addOilGeneration(event);
        ThermalBiomeFeatures.addHostileSpawns(event);
    }
    // endregion
}
