package cofh.thermal.foundation.data;

import cofh.thermal.foundation.data.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL_FOUNDATION)
public class TFndDataGen {

    //    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
    //            .add(Registries.FEATURE, context -> context.register(TEST_SETTINGS, NoiseGeneratorSettings.floatingIslands(context)))
    //            .add(Registries.LEVEL_STEM, DataGeneratorTest::levelStem);

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

        gen.addProvider(event.includeClient(), new TFndBlockStateProvider(output, exFileHelper));
        gen.addProvider(event.includeClient(), new TFndItemModelProvider(output, exFileHelper));

        //        RegistryOps<JsonElement> regOps = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
        //
        //        gen.addProvider(event.includeServer(), TFndFeatures.dataGenFeatures(gen, exFileHelper, regOps));
        //        gen.addProvider(event.includeServer(), TFndBiomeModifiers.dataGenBiomeModifiers(gen, exFileHelper, regOps));
        gen.addProvider(event.includeServer(), new TFndDatapackRegistryProvider(output, event.getLookupProvider()));
    }

}
