package cofh.thermal.foundation.init.data.providers;

import cofh.lib.init.data.DatapackRegistryProviderCoFH;
import cofh.thermal.foundation.init.data.worldgen.TFndBiomeModifiers;
import cofh.thermal.foundation.init.data.worldgen.TFndFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class TFndDatapackRegistryProvider extends DatapackRegistryProviderCoFH {

    public static final RegistrySetBuilder REGISTRIES = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, TFndFeatures.Configured::init)
            .add(Registries.PLACED_FEATURE, TFndFeatures.Placed::init)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, TFndBiomeModifiers::init);

    public TFndDatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {

        super(output, provider, REGISTRIES, ID_THERMAL);
    }

}
