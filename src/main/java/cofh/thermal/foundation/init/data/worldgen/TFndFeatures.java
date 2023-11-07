package cofh.thermal.foundation.init.data.worldgen;

import cofh.thermal.core.common.world.ConfigPlacementFilter;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.foundation.init.TFndIDs.*;

public class TFndFeatures {

    public static class Configured {

        public static final ResourceKey<ConfiguredFeature<?, ?>> APATITE_ORE = createKey("apatite_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CINNABAR_ORE = createKey("cinnabar_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> NITER_ORE = createKey("niter_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_ORE = createKey("sulfur_ore");

        public static final ResourceKey<ConfiguredFeature<?, ?>> LEAD_ORE = createKey("lead_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> NICKEL_ORE = createKey("nickel_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORE = createKey("silver_ore");
        public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE = createKey("tin_ore");

        public static final ResourceKey<ConfiguredFeature<?, ?>> OIL_SAND = createKey("oil_sand");

        public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBERWOOD_TREE = createKey("rubberwood_tree");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_RUBBERWOOD_TREE = createKey("mega_rubberwood_tree");

        public static void init(BootstapContext<ConfiguredFeature<?, ?>> context) {

            RuleTest baseStoneOverworld = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
            RuleTest baseStoneNether = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
            RuleTest stoneOre = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
            RuleTest deepslateOre = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
            RuleTest netherrack = new BlockMatchTest(Blocks.NETHERRACK);
            RuleTest sand = new BlockMatchTest(Blocks.SAND);
            RuleTest redSand = new BlockMatchTest(Blocks.RED_SAND);

            List<OreConfiguration.TargetBlockState> apatite = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_APATITE_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_APATITE_ORE)).defaultBlockState()));
            List<OreConfiguration.TargetBlockState> cinnabar = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_CINNABAR_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_CINNABAR_ORE)).defaultBlockState()));
            List<OreConfiguration.TargetBlockState> niter = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_NITER_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_NITER_ORE)).defaultBlockState()));
            List<OreConfiguration.TargetBlockState> sulfur = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_SULFUR_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_SULFUR_ORE)).defaultBlockState()));

            List<OreConfiguration.TargetBlockState> lead = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_LEAD_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_LEAD_ORE)).defaultBlockState()));
            List<OreConfiguration.TargetBlockState> nickel = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_NICKEL_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_NICKEL_ORE)).defaultBlockState()));
            List<OreConfiguration.TargetBlockState> silver = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_SILVER_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_SILVER_ORE)).defaultBlockState()));
            List<OreConfiguration.TargetBlockState> tin = List.of(OreConfiguration.target(stoneOre, BLOCKS.get(ID_TIN_ORE).defaultBlockState()), OreConfiguration.target(deepslateOre, BLOCKS.get(deepslate(ID_TIN_ORE)).defaultBlockState()));

            List<OreConfiguration.TargetBlockState> oilSand = List.of(OreConfiguration.target(sand, BLOCKS.get(ID_OIL_SAND).defaultBlockState()), OreConfiguration.target(redSand, BLOCKS.get(ID_OIL_RED_SAND).defaultBlockState()));

            FeatureUtils.register(context, APATITE_ORE, Feature.ORE, new OreConfiguration(apatite, 9));
            FeatureUtils.register(context, CINNABAR_ORE, Feature.ORE, new OreConfiguration(cinnabar, 5));
            FeatureUtils.register(context, NITER_ORE, Feature.ORE, new OreConfiguration(niter, 7));
            FeatureUtils.register(context, SULFUR_ORE, Feature.ORE, new OreConfiguration(sulfur, 7));

            FeatureUtils.register(context, LEAD_ORE, Feature.ORE, new OreConfiguration(lead, 8));
            FeatureUtils.register(context, NICKEL_ORE, Feature.ORE, new OreConfiguration(nickel, 8));
            FeatureUtils.register(context, SILVER_ORE, Feature.ORE, new OreConfiguration(silver, 8));
            FeatureUtils.register(context, TIN_ORE, Feature.ORE, new OreConfiguration(tin, 8));

            FeatureUtils.register(context, OIL_SAND, Feature.ORE, new OreConfiguration(oilSand, 24));

            FeatureUtils.register(context, RUBBERWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LOG)),
                    new StraightTrunkPlacer(4, 2, 2),
                    BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LEAVES)),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .ignoreVines()
                    .build());

            FeatureUtils.register(context, MEGA_RUBBERWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LOG)),
                    new MegaJungleTrunkPlacer(7, 2, 4),
                    BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LEAVES)),
                    new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .ignoreVines()
                    .build());
        }

        // region HELPERS
        private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {

            return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ID_THERMAL, name));
        }
        // endregion
    }

    public static class Placed {

        public static final ResourceKey<PlacedFeature> APATITE_ORE = createKey("apatite_ore");
        public static final ResourceKey<PlacedFeature> CINNABAR_ORE = createKey("cinnabar_ore");
        public static final ResourceKey<PlacedFeature> NITER_ORE = createKey("niter_ore");
        public static final ResourceKey<PlacedFeature> SULFUR_ORE = createKey("sulfur_ore");

        public static final ResourceKey<PlacedFeature> LEAD_ORE = createKey("lead_ore");
        public static final ResourceKey<PlacedFeature> NICKEL_ORE = createKey("nickel_ore");
        public static final ResourceKey<PlacedFeature> SILVER_ORE = createKey("silver_ore");
        public static final ResourceKey<PlacedFeature> TIN_ORE = createKey("tin_ore");

        public static final ResourceKey<PlacedFeature> OIL_SAND = createKey("oil_sand");

        public static final ResourceKey<PlacedFeature> RUBBERWOOD_TREE_CHECKED = createKey("rubberwood_tree_checked");
        public static final ResourceKey<PlacedFeature> MEGA_RUBBERWOOD_TREE_CHECKED = createKey("mega_rubberwood_tree_checked");

        public static final ResourceKey<PlacedFeature> RUBBERWOOD_TREE = createKey("rubberwood_tree");
        public static final ResourceKey<PlacedFeature> MEGA_RUBBERWOOD_TREE = createKey("mega_rubberwood_tree");

        public static void init(BootstapContext<PlacedFeature> context) {

            HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);

            context.register(APATITE_ORE, placedOreTriangle(features, Configured.APATITE_ORE, ID_APATITE_ORE, -16, 96, 3));
            context.register(CINNABAR_ORE, placedOreTriangle(features, Configured.CINNABAR_ORE, ID_CINNABAR_ORE, -16, 48, 1));
            context.register(NITER_ORE, placedOreTriangle(features, Configured.NITER_ORE, ID_NITER_ORE, -16, 64, 2));
            context.register(SULFUR_ORE, placedOreTriangle(features, Configured.SULFUR_ORE, ID_SULFUR_ORE, -16, 32, 2));

            context.register(LEAD_ORE, placedOreUniform(features, Configured.LEAD_ORE, ID_LEAD_ORE, -60, 40, 6));
            context.register(NICKEL_ORE, placedOreUniform(features, Configured.NICKEL_ORE, ID_NICKEL_ORE, -40, 120, 4));
            context.register(SILVER_ORE, placedOreUniform(features, Configured.SILVER_ORE, ID_SILVER_ORE, -60, 40, 4));
            context.register(TIN_ORE, placedOreUniform(features, Configured.TIN_ORE, ID_TIN_ORE, -20, 60, 6));

            context.register(OIL_SAND, placedOreUniform(features, Configured.OIL_SAND, ID_OIL_SAND, 40, 80, 2));

            context.register(RUBBERWOOD_TREE_CHECKED, checkTree(features, Configured.RUBBERWOOD_TREE, ID_RUBBERWOOD_SAPLING));
            context.register(MEGA_RUBBERWOOD_TREE_CHECKED, checkTree(features, Configured.MEGA_RUBBERWOOD_TREE, ID_RUBBERWOOD_SAPLING));

            context.register(RUBBERWOOD_TREE, placedTree(features, Configured.RUBBERWOOD_TREE, 2, 0.25F, 2));
            context.register(MEGA_RUBBERWOOD_TREE, placedTree(features, Configured.MEGA_RUBBERWOOD_TREE, 1, 0.1F, 1));
        }

        // region HELPERS
        private static ResourceKey<PlacedFeature> createKey(String name) {

            return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ID_THERMAL, name));
        }

        private static PlacedFeature registerPlacedFeature(HolderGetter<ConfiguredFeature<?, ?>> getter, ResourceKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {

            return new PlacedFeature(getter.getOrThrow(feature), List.of(modifiers));
        }

        private static PlacedFeature placedOreTriangle(HolderGetter<ConfiguredFeature<?, ?>> getter, ResourceKey<ConfiguredFeature<?, ?>> ore, String name, int minY, int maxY, int count) {

            return registerPlacedFeature(getter, ore,
                    new ConfigPlacementFilter(name),
                    CountPlacement.of(count),
                    InSquarePlacement.spread(),
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)),
                    BiomeFilter.biome());
        }

        private static PlacedFeature placedOreUniform(HolderGetter<ConfiguredFeature<?, ?>> getter, ResourceKey<ConfiguredFeature<?, ?>> ore, String name, int minY, int maxY, int count) {

            return registerPlacedFeature(getter, ore,
                    new ConfigPlacementFilter(name),
                    CountPlacement.of(count),
                    InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)),
                    BiomeFilter.biome());
        }

        private static PlacedFeature checkTree(HolderGetter<ConfiguredFeature<?, ?>> getter, ResourceKey<ConfiguredFeature<?, ?>> feature, String sapling) {

            return new PlacedFeature(getter.getOrThrow(feature), List.of(PlacementUtils.filteredByBlockSurvival(BLOCKS.getSup(sapling).get())));
        }

        public static PlacedFeature placedTree(HolderGetter<ConfiguredFeature<?, ?>> getter, ResourceKey<ConfiguredFeature<?, ?>> tree, int count, float chance, int extra) {

            return registerPlacedFeature(getter, tree,
                    // new ConfigPlacementFilter(name),
                    PlacementUtils.countExtra(count, chance, extra),
                    InSquarePlacement.spread(),
                    SurfaceWaterDepthFilter.forMaxDepth(0),
                    PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                    BiomeFilter.biome());
        }
        // endregion

    }

}
