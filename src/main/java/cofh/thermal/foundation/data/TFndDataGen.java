package cofh.thermal.foundation.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import static cofh.lib.util.constants.Constants.ID_THERMAL_FOUNDATION;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL_FOUNDATION)
public class TFndDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        if (event.includeServer()) {
            registerServerProviders(event);
        }
        if (event.includeClient()) {
            registerClientProviders(event);
        }
    }

    private static void registerServerProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        //        TFndTagsProvider.Block blockTags = new TFndTagsProvider.Block(gen, exFileHelper);
        //
        //        gen.addProvider(blockTags);
        //        gen.addProvider(new TFndTagsProvider.Item(gen, blockTags, exFileHelper));

        gen.addProvider(new TFndLootTableProvider(gen));
        gen.addProvider(new TFndRecipeProvider(gen));
    }

    private static void registerClientProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        gen.addProvider(new TFndBlockStateProvider(gen, exFileHelper));
        gen.addProvider(new TFndItemModelProvider(gen, exFileHelper));
    }

}
