package cofh.thermal.core.data;

import cofh.thermal.core.init.TCoreEntities;
import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.DatapackHelper.datapackProvider;
import static cofh.thermal.lib.FeatureHelper.addMobToBiomes;

public final class TCoreBiomeModifiers {

    public static JsonCodecProvider<BiomeModifier> dataGenBiomeModifiers(DataGenerator gen, ExistingFileHelper exFileHelper, RegistryOps<JsonElement> regOps) {

        return datapackProvider(ID_THERMAL, gen, exFileHelper, regOps, ForgeRegistries.Keys.BIOME_MODIFIERS, generateBiomeModifiers(regOps.registryAccess));
    }

    private static Map<ResourceLocation, BiomeModifier> generateBiomeModifiers(RegistryAccess registryAccess) {

        Map<ResourceLocation, BiomeModifier> biomeModifierMap = new HashMap<>();

        generateBiomeMobSpawns(registryAccess.registryOrThrow(Registry.BIOME_REGISTRY), biomeModifierMap);

        return biomeModifierMap;
    }

    private static void generateBiomeMobSpawns(Registry<Biome> biomeRegistry, Map<ResourceLocation, BiomeModifier> map) {

        HolderSet<Biome> basalzBasaltDelta = HolderSet.direct(Holder.Reference.createStandAlone(biomeRegistry, Biomes.BASALT_DELTAS));
        HolderSet<Biome> basalzUnderground = biomeRegistry.getOrCreateTag(Tags.Biomes.IS_UNDERGROUND);

        HolderSet<Biome> blitzBadlands = biomeRegistry.getOrCreateTag(BiomeTags.IS_BADLANDS);
        HolderSet<Biome> blitzSandy = biomeRegistry.getOrCreateTag(Tags.Biomes.IS_SANDY);
        HolderSet<Biome> blitzSavanna = biomeRegistry.getOrCreateTag(BiomeTags.IS_SAVANNA);

        HolderSet<Biome> blizzSnowy = biomeRegistry.getOrCreateTag(Tags.Biomes.IS_SNOWY);

        map.put(new ResourceLocation(ID_THERMAL, "basalz_spawn_basalt_deltas"), addMobToBiomes(basalzBasaltDelta, TCoreEntities.BASALZ.get(), 50, 2, 4));
        map.put(new ResourceLocation(ID_THERMAL, "basalz_spawn_underground"), addMobToBiomes(basalzUnderground, TCoreEntities.BASALZ.get(), 25, 1, 3));

        map.put(new ResourceLocation(ID_THERMAL, "blitz_spawn_badlands"), addMobToBiomes(blitzBadlands, TCoreEntities.BLITZ.get(), 35, 1, 3));
        map.put(new ResourceLocation(ID_THERMAL, "blitz_spawn_sandy"), addMobToBiomes(blitzSandy, TCoreEntities.BLITZ.get(), 25, 1, 3));
        map.put(new ResourceLocation(ID_THERMAL, "blitz_spawn_savanna"), addMobToBiomes(blitzSavanna, TCoreEntities.BLITZ.get(), 50, 2, 4));

        map.put(new ResourceLocation(ID_THERMAL, "blizz_spawn_snowy"), addMobToBiomes(blizzSnowy, TCoreEntities.BLIZZ.get(), 50, 1, 4));
    }

}
