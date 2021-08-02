package cofh.thermal.core.world.gen.feature;

import cofh.core.world.gen.feature.ConfiguredFeatureCoFH;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreIDs.*;
import static cofh.thermal.lib.common.ThermalFlags.*;

public class ThermalFeatures {

    private ThermalFeatures() {

    }

    public static void setup() {

        ORE_APATITE = register("ore_apatite",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_APATITE_ORE).getDefaultState(), 9), getFlag(FLAG_GEN_APATITE))
                        .withPlacement(Placement.DEPTH_AVERAGE.configure(depthRange(48, 24)))
                        .square()
                        .func_242731_b(2));

        ORE_CINNABAR = register("ore_cinnabar",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_CINNABAR_ORE).getDefaultState(), 5), getFlag(FLAG_GEN_CINNABAR))
                        .withPlacement(Placement.DEPTH_AVERAGE.configure(depthRange(16, 16)))
                        .square()
                        .func_242731_b(1));

        ORE_NITER = register("ore_niter",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_NITER_ORE).getDefaultState(), 7), getFlag(FLAG_GEN_NITER))
                        .withPlacement(Placement.DEPTH_AVERAGE.configure(depthRange(40, 12)))
                        .square()
                        .func_242731_b(1));

        ORE_SULFUR = register("ore_sulfur",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_SULFUR_ORE).getDefaultState(), 7), getFlag(FLAG_GEN_SULFUR))
                        .withPlacement(Placement.DEPTH_AVERAGE.configure(depthRange(24, 12)))
                        .square()
                        .func_242731_b(1));

        ORE_COPPER = register("ore_copper",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_COPPER_ORE).getDefaultState(), 9), getFlag(FLAG_GEN_COPPER))
                        .withPlacement(Placement.RANGE.configure(topRange(40, 80)))
                        .square()
                        .func_242731_b(6));

        ORE_TIN = register("ore_tin",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_TIN_ORE).getDefaultState(), 9), getFlag(FLAG_GEN_TIN))
                        .withPlacement(Placement.RANGE.configure(topRange(20, 60)))
                        .square()
                        .func_242731_b(4));

        ORE_LEAD = register("ore_lead",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_LEAD_ORE).getDefaultState(), 8), getFlag(FLAG_GEN_LEAD))
                        .withPlacement(Placement.RANGE.configure(topRange(0, 40)))
                        .square()
                        .func_242731_b(2));

        ORE_SILVER = register("ore_silver",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_SILVER_ORE).getDefaultState(), 8), getFlag(FLAG_GEN_SILVER))
                        .withPlacement(Placement.RANGE.configure(topRange(0, 40)))
                        .square()
                        .func_242731_b(2));

        ORE_NICKEL = register("ore_nickel",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BLOCKS.get(ID_NICKEL_ORE).getDefaultState(), 8), getFlag(FLAG_GEN_NICKEL))
                        .withPlacement(Placement.RANGE.configure(topRange(0, 120)))
                        .square()
                        .func_242731_b(2));

        OIL_SAND = register("oil_sand",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(SAND, BLOCKS.get(ID_OIL_SAND).getDefaultState(), 24), getFlag(FLAG_GEN_OIL))
                        .setChance(0.3F)
                        .withPlacement(Placement.RANGE.configure(topRange(40, 80)))
                        .square()
                        .func_242731_b(2));

        OIL_RED_SAND = register("oil_red_sand",
                new ConfiguredFeatureCoFH<>(Feature.ORE, new OreFeatureConfig(RED_SAND, BLOCKS.get(ID_OIL_RED_SAND).getDefaultState(), 24), getFlag(FLAG_GEN_OIL))
                        .setChance(0.3F)
                        .withPlacement(Placement.RANGE.configure(topRange(40, 80)))
                        .square()
                        .func_242731_b(2));
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {

        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(ID_THERMAL, key), configuredFeature);
    }

    private static DepthAverageConfig depthRange(int base, int spread) {

        return new DepthAverageConfig(base, spread);
    }

    private static TopSolidRangeConfig topRange(int min, int max) {

        return new TopSolidRangeConfig(min, min, max);
    }

    public static ConfiguredFeature<?, ?> ORE_APATITE;
    public static ConfiguredFeature<?, ?> ORE_CINNABAR;
    public static ConfiguredFeature<?, ?> ORE_NITER;
    public static ConfiguredFeature<?, ?> ORE_SULFUR;

    public static ConfiguredFeature<?, ?> ORE_COPPER;
    public static ConfiguredFeature<?, ?> ORE_TIN;
    public static ConfiguredFeature<?, ?> ORE_LEAD;
    public static ConfiguredFeature<?, ?> ORE_SILVER;
    public static ConfiguredFeature<?, ?> ORE_NICKEL;

    public static ConfiguredFeature<?, ?> ORE_RUBY;
    public static ConfiguredFeature<?, ?> ORE_SAPPHIRE;

    public static ConfiguredFeature<?, ?> OIL_SAND;
    public static ConfiguredFeature<?, ?> OIL_RED_SAND;

    public static final RuleTest SAND = new BlockMatchRuleTest(Blocks.SAND);
    public static final RuleTest RED_SAND = new BlockMatchRuleTest(Blocks.RED_SAND);

}
