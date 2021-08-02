package cofh.thermal.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL)
public class TCoreDataGen {

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

        TCoreTagsProvider.Block blockTags = new TCoreTagsProvider.Block(gen, exFileHelper);

        gen.addProvider(blockTags);
        gen.addProvider(new TCoreTagsProvider.Item(gen, blockTags, exFileHelper));
        gen.addProvider(new TCoreTagsProvider.Fluid(gen, exFileHelper));

        gen.addProvider(new TCoreLootTableProvider(gen));
        gen.addProvider(new TCoreRecipeProvider(gen));
    }

    private static void registerClientProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        gen.addProvider(new TCoreBlockStateProvider(gen, exFileHelper));
        gen.addProvider(new TCoreItemModelProvider(gen, exFileHelper));
    }

}
