package cofh.thermal.core.event;

import cofh.core.util.ProxyUtils;
import cofh.lib.client.model.SimpleModelLoader;
import cofh.lib.client.renderer.entity.model.ArmorModelFullSuit;
import cofh.thermal.core.client.renderer.model.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreIDs.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ID_THERMAL, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCoreClientSetupEvents {

    private TCoreClientSetupEvents() {

    }

    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event) {

        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "underlay"), new SimpleModelLoader(UnderlayBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "dynamo"), new SimpleModelLoader(DynamoBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "reconfigurable"), new SimpleModelLoader(ReconfigurableBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "energy_cell"), new SimpleModelLoader(EnergyCellBakedModel::new));
        ModelLoaderRegistry.registerLoader(new ResourceLocation(ID_THERMAL, "fluid_cell"), new SimpleModelLoader(FluidCellBakedModel::new));

        ProxyUtils.addModel(ITEMS.get(ID_BEEKEEPER_HELMET), ArmorModelFullSuit.LARGE);
        ProxyUtils.addModel(ITEMS.get(ID_BEEKEEPER_CHESTPLATE), ArmorModelFullSuit.DEFAULT);

        ProxyUtils.addModel(ITEMS.get(ID_DIVING_HELMET), ArmorModelFullSuit.LARGE);
        ProxyUtils.addModel(ITEMS.get(ID_DIVING_CHESTPLATE), ArmorModelFullSuit.DEFAULT);

        ProxyUtils.addModel(ITEMS.get(ID_HAZMAT_HELMET), ArmorModelFullSuit.LARGE);
        ProxyUtils.addModel(ITEMS.get(ID_HAZMAT_CHESTPLATE), ArmorModelFullSuit.DEFAULT);
    }

}
