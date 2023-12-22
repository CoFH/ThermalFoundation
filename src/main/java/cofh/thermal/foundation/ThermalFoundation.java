package cofh.thermal.foundation;

import cofh.thermal.foundation.init.registries.TFndBlocks;
import cofh.thermal.foundation.init.registries.TFndEntities;
import cofh.thermal.foundation.init.registries.TFndItems;
import cofh.thermal.foundation.util.TFndProxy;
import cofh.thermal.foundation.util.TFndProxyClient;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.foundation.client.model.geom.ModelLayers.RUBBERWOOD_BOAT_LAYER;
import static cofh.thermal.foundation.client.model.geom.ModelLayers.RUBBERWOOD_CHEST_BOAT_LAYER;
import static cofh.thermal.foundation.init.registries.TFndIDs.ID_POTTED_RUBBERWOOD_SAPLING;
import static cofh.thermal.foundation.init.registries.TFndIDs.ID_RUBBERWOOD_SAPLING;
import static cofh.thermal.lib.util.ThermalFlags.*;

@Mod (ID_THERMAL_FOUNDATION)
public class ThermalFoundation {

    public static final TFndProxy PROXY = DistExecutor.unsafeRunForDist(() -> TFndProxyClient::new, () -> TFndProxy::new);

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

        setFlag(FLAG_RESOURCE_RUBBERWOOD, true);
    }

    public static final BlockSetType BLOCK_SET_TYPE_RUBBERWOOD = BlockSetType.register(new BlockSetType("thermal:rubberwood"));
    public static final WoodType WOOD_TYPE_RUBBERWOOD = WoodType.register(new WoodType("thermal:rubberwood", BLOCK_SET_TYPE_RUBBERWOOD));

    // region INITIALIZATION
    private void entityLayerSetup(final EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(RUBBERWOOD_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(RUBBERWOOD_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
    }

    private void entityRendererSetup(final EntityRenderersEvent.RegisterRenderers event) {

        PROXY.registerBoatModels(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(TFndBlocks::setup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        event.enqueueWork(this::registerRenderLayers);
        event.enqueueWork(() -> {
            Sheets.addWoodType(WOOD_TYPE_RUBBERWOOD);
        });
    }
    // endregion

    // region HELPERS
    private void registerRenderLayers() {

        RenderType cutout = RenderType.cutout();

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_RUBBERWOOD_SAPLING), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_POTTED_RUBBERWOOD_SAPLING), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get("rubberwood_door"), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get("rubberwood_trapdoor"), cutout);
    }
    // endregion
}
