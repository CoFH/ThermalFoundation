package cofh.thermal.foundation.data;

import net.minecraft.data.DataGenerator;
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
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        //        TFndTagsProvider.Block blockTags = new TFndTagsProvider.Block(gen, exFileHelper);
        //
        //        gen.addProvider(event.includeServer(), blockTags);
        //        gen.addProvider(event.includeServer(), new TFndTagsProvider.Item(gen, blockTags, exFileHelper));

        gen.addProvider(event.includeServer(), new TFndLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new TFndRecipeProvider(gen));

        gen.addProvider(event.includeClient(), new TFndBlockStateProvider(gen, exFileHelper));
        gen.addProvider(event.includeClient(), new TFndItemModelProvider(gen, exFileHelper));
    }

}
