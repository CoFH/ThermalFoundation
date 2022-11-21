package cofh.thermal.core.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL)
public class TCoreDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        TCoreTagsProvider.Block blockTags = new TCoreTagsProvider.Block(gen, exFileHelper);

        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new TCoreTagsProvider.Item(gen, blockTags, exFileHelper));
        gen.addProvider(event.includeServer(), new TCoreTagsProvider.Fluid(gen, exFileHelper));
        gen.addProvider(event.includeServer(), new TCoreTagsProvider.Entity(gen, exFileHelper));

        // gen.addProvider(event.includeServer(), new TCoreAdvancementProvider(gen));
        gen.addProvider(event.includeServer(), new TCoreLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new TCoreRecipeProvider(gen));

        gen.addProvider(event.includeClient(), new TCoreBlockStateProvider(gen, exFileHelper));
        gen.addProvider(event.includeClient(), new TCoreItemModelProvider(gen, exFileHelper));

        RegistryOps<JsonElement> regOps = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());

        gen.addProvider(event.includeServer(), TCoreBiomeModifiers.dataGenBiomeModifiers(gen, exFileHelper, regOps));
    }

}
