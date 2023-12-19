package cofh.thermal.foundation.util;

import cofh.core.client.renderer.entity.BoatRendererCoFH;
import net.minecraftforge.client.event.EntityRenderersEvent;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.foundation.client.model.geom.ModelLayers.RUBBERWOOD_BOAT_LAYER;
import static cofh.thermal.foundation.client.model.geom.ModelLayers.RUBBERWOOD_CHEST_BOAT_LAYER;
import static cofh.thermal.foundation.init.registries.TFndEntities.RUBBERWOOD_BOAT;
import static cofh.thermal.foundation.init.registries.TFndEntities.RUBBERWOOD_CHEST_BOAT;

public class TFndProxyClient extends TFndProxy {

    @Override
    public void registerBoatModels(final EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(RUBBERWOOD_BOAT.get(), (context) -> new BoatRendererCoFH(context, false, ID_THERMAL, "rubberwood", RUBBERWOOD_BOAT_LAYER));
        event.registerEntityRenderer(RUBBERWOOD_CHEST_BOAT.get(), (context) -> new BoatRendererCoFH(context, true, ID_THERMAL, "rubberwood", RUBBERWOOD_CHEST_BOAT_LAYER));
    }

}
