package cofh.thermal.core.event;

import cofh.lib.client.model.SimpleModel;
import cofh.thermal.core.client.renderer.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber (value = Dist.CLIENT, modid = ID_THERMAL, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCoreClientSetupEvents {

    private TCoreClientSetupEvents() {

    }

    @SubscribeEvent
    public static void colorSetupBlock(final ColorHandlerEvent.Block event) {

        // BlockColors colors = event.getBlockColors();

        // colors.register((state, reader, pos, tintIndex) -> (reader == null || pos == null) ? FoliageColors.getDefault() : BiomeColors.getFoliageColor(reader, pos), BLOCKS.get(ID_RUBBER_LEAVES));
    }

    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event) {

        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "underlay"), new SimpleModel.Loader(UnderlayBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "dynamo"), new SimpleModel.Loader(DynamoBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "reconfigurable"), new SimpleModel.Loader(ReconfigurableBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "energy_cell"), new SimpleModel.Loader(EnergyCellBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "fluid_cell"), new SimpleModel.Loader(FluidCellBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "item_cell"), new SimpleModel.Loader(ItemCellBakedModel::new));
    }

}
