package cofh.thermal.foundation.init.data.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.DatapackHelper.holderSetIntersection;
import static cofh.lib.util.helpers.DatapackHelper.holderSetUnion;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.UNDERGROUND_ORES;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;

public class TFndBiomeModifiers {

    public static final ResourceKey<BiomeModifier> APATITE_ORE = createKey("ore_apatite");
    public static final ResourceKey<BiomeModifier> CINNABAR_ORE = createKey("ore_cinnabar");
    public static final ResourceKey<BiomeModifier> NITER_ORE = createKey("ore_niter");
    public static final ResourceKey<BiomeModifier> SULFUR_ORE = createKey("ore_sulfur");

    public static final ResourceKey<BiomeModifier> LEAD_ORE = createKey("ore_lead");
    public static final ResourceKey<BiomeModifier> NICKEL_ORE = createKey("ore_nickel");
    public static final ResourceKey<BiomeModifier> SILVER_ORE = createKey("ore_silver");
    public static final ResourceKey<BiomeModifier> TIN_ORE = createKey("ore_tin");

    public static final ResourceKey<BiomeModifier> OIL_SAND = createKey("oil_sand");

    public static final ResourceKey<BiomeModifier> RUBBERWOOD_TREES = createKey("trees_rubberwood");

    public static void init(BootstapContext<BiomeModifier> context) {

        var isBadlandsTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_BADLANDS);
        var isOverworldTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD);

        var isBambooJungle = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.BAMBOO_JUNGLE));
        var isDesert = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.DESERT));
        var isFlowerForest = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.FLOWER_FOREST));
        var isSparseJungle = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.SPARSE_JUNGLE));

        var oilSandsBiomes = holderSetIntersection(isOverworldTag, holderSetUnion(isDesert, isBadlandsTag));
        var rubberwoodTreeBiomes = holderSetUnion(isFlowerForest, isBambooJungle, isSparseJungle);

        registerOre(context, APATITE_ORE, isOverworldTag, TFndFeatures.Placed.APATITE_ORE);
        registerOre(context, CINNABAR_ORE, isOverworldTag, TFndFeatures.Placed.CINNABAR_ORE);
        registerOre(context, NITER_ORE, isOverworldTag, TFndFeatures.Placed.NITER_ORE);
        registerOre(context, SULFUR_ORE, isOverworldTag, TFndFeatures.Placed.SULFUR_ORE);

        registerOre(context, LEAD_ORE, isOverworldTag, TFndFeatures.Placed.LEAD_ORE);
        registerOre(context, NICKEL_ORE, isOverworldTag, TFndFeatures.Placed.NICKEL_ORE);
        registerOre(context, SILVER_ORE, isOverworldTag, TFndFeatures.Placed.SILVER_ORE);
        registerOre(context, TIN_ORE, isOverworldTag, TFndFeatures.Placed.TIN_ORE);

        registerOre(context, OIL_SAND, oilSandsBiomes, TFndFeatures.Placed.OIL_SAND);

        var treeRubberwood = HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(TFndFeatures.Placed.RUBBERWOOD_TREE));
        var treeMegaRubberwood = HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(TFndFeatures.Placed.MEGA_RUBBERWOOD_TREE));

        context.register(RUBBERWOOD_TREES, new AddFeaturesBiomeModifier(rubberwoodTreeBiomes,
                holderSetUnion(treeRubberwood, treeMegaRubberwood),
                VEGETAL_DECORATION));
    }

    // region HELPERS
    private static ResourceKey<BiomeModifier> createKey(String name) {

        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ID_THERMAL, name));
    }

    private static void registerOre(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> biomeMod, HolderSet<Biome> biomes, ResourceKey<PlacedFeature> feature) {

        context.register(biomeMod, new AddFeaturesBiomeModifier(biomes,
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(feature)),
                UNDERGROUND_ORES));
    }
    // endregion
}
