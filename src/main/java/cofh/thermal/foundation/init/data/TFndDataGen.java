package cofh.thermal.foundation.init.data;

import cofh.thermal.foundation.init.data.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL_FOUNDATION)
public class TFndDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        TFndTagsProvider.Block blockTags = new TFndTagsProvider.Block(output, event.getLookupProvider(), exFileHelper);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new TFndTagsProvider.Item(output, event.getLookupProvider(), blockTags.contentsGetter(), exFileHelper));

        gen.addProvider(event.includeServer(), new TFndLootTableProvider(output));
        gen.addProvider(event.includeServer(), new TFndRecipeProvider(output));
        gen.addProvider(event.includeServer(), new TFndDatapackRegistryProvider(output, event.getLookupProvider()));

        gen.addProvider(event.includeClient(), new TFndBlockStateProvider(output, exFileHelper));
        gen.addProvider(event.includeClient(), new TFndItemModelProvider(output, exFileHelper));
    }

}
