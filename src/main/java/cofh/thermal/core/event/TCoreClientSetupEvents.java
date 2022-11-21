package cofh.thermal.core.event;

import cofh.core.client.model.SimpleModel;
import cofh.thermal.core.client.renderer.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

@Mod.EventBusSubscriber (value = Dist.CLIENT, modid = ID_THERMAL, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCoreClientSetupEvents {

    private TCoreClientSetupEvents() {

    }

    @SubscribeEvent
    public static void colorSetupBlock(final RegisterColorHandlersEvent.Block event) {

        // BlockColors colors = event.getBlockColors();

        // colors.register((state, reader, pos, tintIndex) -> (reader == null || pos == null) ? FoliageColors.getDefault() : BiomeColors.getFoliageColor(reader, pos), BLOCKS.get(ID_RUBBER_LEAVES));
    }

    @SubscribeEvent
    public static void registerModels(final RegisterGeometryLoaders event) {

        event.register("underlay", new SimpleModel.Loader(UnderlayBakedModel::new));
        event.register("dynamo", new SimpleModel.Loader(DynamoBakedModel::new));
        event.register("reconfigurable", new SimpleModel.Loader(ReconfigurableBakedModel::new));
        event.register("energy_cell", new SimpleModel.Loader(EnergyCellBakedModel::new));
        event.register("fluid_cell", new SimpleModel.Loader(FluidCellBakedModel::new));
        event.register("item_cell", new SimpleModel.Loader(ItemCellBakedModel::new));
    }

}
