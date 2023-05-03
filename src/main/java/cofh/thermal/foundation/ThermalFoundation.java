package cofh.thermal.foundation;

import cofh.thermal.foundation.data.TFndFeatures;
import cofh.thermal.foundation.init.TFndBlocks;
import cofh.thermal.foundation.init.TFndEntities;
import cofh.thermal.foundation.init.TFndItems;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.foundation.client.model.geom.ModelLayers.RUBBERWOOD_BOAT_LAYER;
import static cofh.thermal.foundation.client.model.geom.ModelLayers.RUBBERWOOD_CHEST_BOAT_LAYER;
import static cofh.thermal.foundation.init.TFndIDs.ID_RUBBERWOOD_SAPLING;
import static cofh.thermal.lib.common.ThermalFlags.*;

@Mod (ID_THERMAL_FOUNDATION)
public class ThermalFoundation {

    public ThermalFoundation() {

        setFeatureFlags();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::entityLayerSetup);
        modEventBus.addListener(this::entityRendererSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        TFndBlocks.register();
        TFndItems.register();

        TFndEntities.register();
    }

    private void setFeatureFlags() {

        setFlag(FLAG_RESOURCE_NITER, true);
        setFlag(FLAG_RESOURCE_SULFUR, true);

        setFlag(FLAG_RESOURCE_TIN, true);
        setFlag(FLAG_RESOURCE_LEAD, true);
        setFlag(FLAG_RESOURCE_SILVER, true);
        setFlag(FLAG_RESOURCE_NICKEL, true);
    }

    public static final WoodType RUBBERWOOD = WoodType.create("thermal:rubberwood");

    // region INITIALIZATION
    private void entityLayerSetup(final EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(RUBBERWOOD_BOAT_LAYER, () -> BoatModel.createBodyModel(false));
        event.registerLayerDefinition(RUBBERWOOD_CHEST_BOAT_LAYER, () -> BoatModel.createBodyModel(true));
    }

    private void entityRendererSetup(final EntityRenderersEvent.RegisterRenderers event) {

        // event.registerEntityRenderer(RUBBERWOOD_BOAT.get(), (context) -> new BoatRendererCoFH(context, false, ID_THERMAL, "rubberwood", RUBBERWOOD_BOAT_LAYER));
        // event.registerEntityRenderer(RUBBERWOOD_CHEST_BOAT.get(), (context) -> new BoatRendererCoFH(context, true, ID_THERMAL, "rubberwood", RUBBERWOOD_CHEST_BOAT_LAYER));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> WoodType.register(RUBBERWOOD));

        event.enqueueWork(TFndBlocks::setup);
        event.enqueueWork(TFndFeatures::setup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        event.enqueueWork(this::registerRenderLayers);
        event.enqueueWork(() -> {
            Sheets.addWoodType(RUBBERWOOD);
        });
    }
    // endregion

    // region HELPERS
    private void registerRenderLayers() {

        RenderType cutout = RenderType.cutout();

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_RUBBERWOOD_SAPLING), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get("rubberwood_door"), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get("rubberwood_trapdoor"), cutout);
    }
    // endregion
}
