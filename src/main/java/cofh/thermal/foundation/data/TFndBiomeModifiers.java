package cofh.thermal.foundation.data;

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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.holdersets.AnyHolderSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.DatapackHelper.*;
import static cofh.thermal.foundation.init.TFndIDs.*;
import static cofh.thermal.lib.FeatureHelper.addFeatureToBiomes;
import static cofh.thermal.lib.FeatureHelper.addFeaturesToBiomes;

public final class TFndBiomeModifiers {

    public static JsonCodecProvider<BiomeModifier> dataGenBiomeModifiers(DataGenerator gen, ExistingFileHelper exFileHelper, RegistryOps<JsonElement> regOps) {

        return datapackProvider(ID_THERMAL, gen, exFileHelper, regOps, ForgeRegistries.Keys.BIOME_MODIFIERS, generateBiomeModifiers(regOps.registryAccess));
    }

    private static Map<ResourceLocation, BiomeModifier> generateBiomeModifiers(RegistryAccess registryAccess) {

        Map<ResourceLocation, BiomeModifier> biomeModifierMap = new HashMap<>();

        generateBiomeOres(registryAccess.registryOrThrow(Registry.BIOME_REGISTRY), registryAccess.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY), biomeModifierMap);

        return biomeModifierMap;
    }

    private static void generateBiomeOres(Registry<Biome> biomeRegistry, Registry<PlacedFeature> placedFeatureRegistry, Map<ResourceLocation, BiomeModifier> map) {

        HolderSet<Biome> allBiomes = new AnyHolderSet<>(biomeRegistry);

        HolderSet<Biome> oilSandsBiomes = holderSetIntersection(
                biomeRegistry.getOrCreateTag(BiomeTags.IS_OVERWORLD),
                holderSetUnion(
                        HolderSet.direct(Holder.Reference.createStandAlone(biomeRegistry, Biomes.DESERT)),
                        tagsOr(biomeRegistry, BiomeTags.IS_BADLANDS)
                )
        );

        HolderSet<Biome> rubberwoodTreeBiomes = holderSetUnion(
                HolderSet.direct(Holder.Reference.createStandAlone(biomeRegistry, Biomes.FLOWER_FOREST)),
                HolderSet.direct(Holder.Reference.createStandAlone(biomeRegistry, Biomes.BAMBOO_JUNGLE)),
                HolderSet.direct(Holder.Reference.createStandAlone(biomeRegistry, Biomes.SPARSE_JUNGLE))
        );

        addOreToBiomeGen(map, ID_APATITE_ORE, allBiomes, placedFeatureRegistry);
        addOreToBiomeGen(map, ID_CINNABAR_ORE, allBiomes, placedFeatureRegistry);
        addOreToBiomeGen(map, ID_NITER_ORE, allBiomes, placedFeatureRegistry);
        addOreToBiomeGen(map, ID_SULFUR_ORE, allBiomes, placedFeatureRegistry);

        addOreToBiomeGen(map, ID_TIN_ORE, allBiomes, placedFeatureRegistry);
        addOreToBiomeGen(map, ID_LEAD_ORE, allBiomes, placedFeatureRegistry);
        addOreToBiomeGen(map, ID_SILVER_ORE, allBiomes, placedFeatureRegistry);
        addOreToBiomeGen(map, ID_NICKEL_ORE, allBiomes, placedFeatureRegistry);

        addOreToBiomeGen(map, ID_OIL_SAND, oilSandsBiomes, placedFeatureRegistry);

        addVegetationToBiomeGen(map, ID_RUBBERWOOD_TREE, List.of(ID_RUBBERWOOD_TREE, ID_MEGA_RUBBERWOOD_TREE), rubberwoodTreeBiomes, placedFeatureRegistry);
    }

    public static void addOreToBiomeGen(Map<ResourceLocation, BiomeModifier> map, String name, HolderSet<Biome> biomes, Registry<PlacedFeature> placedFeatureRegistry) {

        map.put(new ResourceLocation(ID_THERMAL, name + "_biome_spawns"), addFeatureToBiomes(name, biomes, placedFeatureRegistry, GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    public static void addVegetationToBiomeGen(Map<ResourceLocation, BiomeModifier> map, String name, List<String> names, HolderSet<Biome> biomes, Registry<PlacedFeature> placedFeatureRegistry) {

        map.put(new ResourceLocation(ID_THERMAL, name + "_biome_spawns"), addFeaturesToBiomes(names, biomes, placedFeatureRegistry, GenerationStep.Decoration.VEGETAL_DECORATION));
    }

}
