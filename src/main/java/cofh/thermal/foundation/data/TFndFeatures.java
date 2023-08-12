package cofh.thermal.foundation.data;

import cofh.lib.util.constants.ModIds;
import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.DatapackHelper.datapackProvider;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.foundation.init.TFndIDs.*;
import static cofh.thermal.lib.FeatureHelper.createOreFeature;
import static cofh.thermal.lib.FeatureHelper.createTreeFeature;

public final class TFndFeatures {

    public static JsonCodecProvider<PlacedFeature> dataGenFeatures(DataGenerator gen, ExistingFileHelper exFileHelper, RegistryOps<JsonElement> regOps) {

        return datapackProvider(ID_THERMAL, gen, exFileHelper, regOps, Registry.PLACED_FEATURE_REGISTRY, generateFeatures(regOps.registryAccess.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY), regOps.registryAccess.registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY)));
    }

    private static Map<ResourceLocation, PlacedFeature> generateFeatures(Registry<PlacedFeature> featureRegistry, Registry<ConfiguredFeature<?, ?>> configuredRegistry) {

        Map<ResourceLocation, PlacedFeature> featureMap = new HashMap<>();

        generateOres(featureMap);
        generateVegetation(featureMap, configuredRegistry);

        return featureMap;
    }

    private static void generateOres(Map<ResourceLocation, PlacedFeature> featureMap) {

        createOreFeature(featureMap, ID_APATITE_ORE, 3, -16, 96, 9);
        createOreFeature(featureMap, ID_CINNABAR_ORE, 1, -16, 48, 5);
        createOreFeature(featureMap, ID_NITER_ORE, 2, -16, 64, 7);
        createOreFeature(featureMap, ID_SULFUR_ORE, 2, -16, 32, 7);

        createOreFeature(featureMap, ID_TIN_ORE, 6, -20, 60, 9);
        createOreFeature(featureMap, ID_LEAD_ORE, 6, -60, 40, 8);
        createOreFeature(featureMap, ID_SILVER_ORE, 4, -60, 40, 8);
        createOreFeature(featureMap, ID_NICKEL_ORE, 4, -40, 120, 8);

        createOreFeature(featureMap, List.of(
                OreConfiguration.target(SAND, BLOCKS.get(ID_OIL_SAND).defaultBlockState()),
                OreConfiguration.target(RED_SAND, BLOCKS.get(ID_OIL_RED_SAND).defaultBlockState())
        ), ID_OIL_SAND, 2, 40, 80, 24);

    }

    private static void generateVegetation(Map<ResourceLocation, PlacedFeature> featureMap, Registry<ConfiguredFeature<?, ?>> configuredRegistry) {

        createTreeFeature(featureMap, configuredRegistry, ID_RUBBERWOOD_TREE, BLOCKS.get(ID_RUBBERWOOD_SAPLING), 25);
        createTreeFeature(featureMap, configuredRegistry, ID_MEGA_RUBBERWOOD_TREE, BLOCKS.get(ID_RUBBERWOOD_SAPLING), 100);
    }

    public static void setup() {


        RUBBERWOOD_TREE = FeatureUtils.register(new ResourceLocation(ID_THERMAL, ID_RUBBERWOOD_TREE).toString(), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LOG)),
                new StraightTrunkPlacer(4, 2, 2),
                BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LEAVES)),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .build());

        MEGA_RUBBERWOOD_TREE = FeatureUtils.register(new ResourceLocation(ID_THERMAL, ID_MEGA_RUBBERWOOD_TREE).toString(), Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LOG)),
                new MegaJungleTrunkPlacer(7, 2, 4),
                BlockStateProvider.simple(BLOCKS.get(ID_RUBBERWOOD_LEAVES)),
                new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .build());
    }

    public static final RuleTest SAND = new BlockMatchTest(Blocks.SAND);
    public static final RuleTest RED_SAND = new BlockMatchTest(Blocks.RED_SAND);

    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> RUBBERWOOD_TREE;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> MEGA_RUBBERWOOD_TREE;

}
