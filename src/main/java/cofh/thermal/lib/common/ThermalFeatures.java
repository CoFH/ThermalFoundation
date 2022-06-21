package cofh.thermal.lib.common;

import cofh.lib.config.world.OreConfig;
import cofh.thermal.core.config.ThermalWorldConfig;
import cofh.thermal.lib.world.DimensionPlacement;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.core.util.RegistrationHelper.netherrack;

@Mod.EventBusSubscriber (modid = ID_THERMAL)
public class ThermalFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<RuleTestType<?>> RULE_TESTS = DeferredRegister.create(Registry.RULE_TEST_REGISTRY, ID_THERMAL);

    public static final RegistryObject<PlacementModifierType<?>> DIMENSION_PLACEMENT = PLACEMENT_MODIFIERS.register("dimension_placement", DimensionPlacement.Type::new);

    public static void register(IEventBus bus) {

        FEATURES.register(bus);
        CONFIGURED_FEATURES.register(bus);
        PLACED_FEATURES.register(bus);
        PLACEMENT_MODIFIERS.register(bus);
        RULE_TESTS.register(bus);
    }

    public static void register() {

        registerDefaultTriangleOreFeature("niter_ore");
        registerDefaultTriangleOreFeature("sulfur_ore");

        registerDefaultTriangleOreFeature("tin_ore");
        registerDefaultTriangleOreFeature("lead_ore");
        registerDefaultTriangleOreFeature("silver_ore");
        registerDefaultTriangleOreFeature("nickel_ore");

        registerDefaultTriangleOreFeature("apatite_ore");

        registerDefaultTriangleOreFeature("cinnabar_ore");

        final OreConfig oilSandConfig = ThermalWorldConfig.getOreConfig("oil_sand");
        RegistryObject<ConfiguredFeature<?, ?>> configuredOilSand = CONFIGURED_FEATURES.register("oil_sand", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(getOilSandReplacements(), oilSandConfig.getSize())));
        placedOilSand = PLACED_FEATURES.register("oil_sand", () -> new PlacedFeature(configuredOilSand.getHolder().get(),
                List.of(CountPlacement.of(oilSandConfig.getCount()),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        // DimensionPlacement.of(oreConfig.get().getDimensions()),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(oilSandConfig.getMinY()), VerticalAnchor.absolute(oilSandConfig.getMaxY()))
                )
        ));
    }

    // region REGISTRATION
    public static void registerDefaultTriangleOreFeature(final String oreName) {

        registerDefaultOreFeature(oreName, true);
    }

    public static void registerDefaultUniformOreFeature(final String oreName) {

        registerDefaultOreFeature(oreName, false);
    }

    public static void registerDefaultOreFeature(final String oreName, boolean triangle) {

        final OreConfig oreConfig = ThermalWorldConfig.getOreConfig(oreName);

        RegistryObject<ConfiguredFeature<?, ?>> configuredOre = CONFIGURED_FEATURES.register(oreName, () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(getOreReplacements(oreName), oreConfig.getSize())));

        oreFeatures.add(Pair.of(oreName, PLACED_FEATURES.register(oreName, () -> new PlacedFeature(configuredOre.getHolder().get(),
                List.of(CountPlacement.of(oreConfig.getCount()),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome(),
                        // DimensionPlacement.of(oreConfig.get().getDimensions()),
                        triangle ? HeightRangePlacement.triangle(VerticalAnchor.absolute(oreConfig.getMinY()), VerticalAnchor.absolute(oreConfig.getMaxY())) :
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(oreConfig.getMinY()), VerticalAnchor.absolute(oreConfig.getMaxY()))
                )
        ))));
    }
    // endregion

    public static List<Pair<String, RegistryObject<PlacedFeature>>> oreFeatures = new ArrayList<>();
    public static List<Holder<PlacedFeature>> oresToGenerate = null;

    @SubscribeEvent (priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {

        if (isOverworldBiome(event.getCategory())) {
            if (oresToGenerate == null) {
                oresToGenerate = new ArrayList<>();
                for (Pair<String, RegistryObject<PlacedFeature>> entry : oreFeatures) {
                    OreConfig config = ThermalWorldConfig.getOreConfig(entry.left());
                    if (config != null && config.shouldGenerate()) {
                        oresToGenerate.add(entry.right().getHolder().get());
                    }
                }
            }
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).addAll(oresToGenerate);

            Biome.BiomeCategory category = event.getCategory();
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(placedOilSand.getHolder().get());
            }
        }
        addHostileSpawns(event);
    }

    public static void addHostileSpawns(BiomeLoadingEvent event) {

        MobSpawnSettingsBuilder builder = event.getSpawns();

        if (builder.getSpawner(MobCategory.MONSTER).isEmpty()) {
            return;
        }
        ResourceLocation name = event.getName();
        Biome.BiomeCategory category = event.getCategory();
        Biome.ClimateSettings climate = event.getClimate();

        if (isOverworldBiome(category)) {
            if (category == Biome.BiomeCategory.EXTREME_HILLS || category == Biome.BiomeCategory.MESA) {
                builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BASALZ_ENTITY, 10, 1, 3));
            }
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA || category == Biome.BiomeCategory.SAVANNA) {
                builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BLITZ_ENTITY, 10, 1, 3));
            }
            if (climate.precipitation == Biome.Precipitation.SNOW & climate.temperature <= 0.3F) {
                builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BLIZZ_ENTITY, 10, 1, 3));
            }
        } else if (isNetherBiome(category)) {
            if (name != null && name.toString().equals("minecraft:basalt_deltas")) {
                builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BASALZ_ENTITY, 10, 1, 3));
            }
        }
    }

    private static RegistryObject<PlacedFeature> placedOilSand;

    public static final RuleTest SAND = new BlockMatchTest(Blocks.SAND);
    public static final RuleTest RED_SAND = new BlockMatchTest(Blocks.RED_SAND);

    // region HELPERS
    public static List<OreConfiguration.TargetBlockState> getOreReplacements(String oreName) {

        final List<OreConfiguration.TargetBlockState> oreReplacements = new ArrayList<>();

        if (BLOCKS.get(oreName) != null) {
            oreReplacements.add(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BLOCKS.get(oreName).defaultBlockState()));
        }
        if (BLOCKS.get(deepslate(oreName)) != null) {
            oreReplacements.add(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BLOCKS.get(deepslate(oreName)).defaultBlockState()));
        }
        if (BLOCKS.get(netherrack(oreName)) != null) {
            oreReplacements.add(OreConfiguration.target(OreFeatures.NETHERRACK, BLOCKS.get(netherrack(oreName)).defaultBlockState()));
        }
        return oreReplacements;
    }

    private static List<OreConfiguration.TargetBlockState> getOilSandReplacements() {

        return List.of(OreConfiguration.target(SAND, BLOCKS.get("oil_sand").defaultBlockState()), OreConfiguration.target(RED_SAND, BLOCKS.get("oil_red_sand").defaultBlockState()));
    }

    public static boolean isOverworldBiome(Biome.BiomeCategory category) {

        return category != Biome.BiomeCategory.NONE && category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NETHER;
    }

    public static boolean isNetherBiome(Biome.BiomeCategory category) {

        return category == Biome.BiomeCategory.NETHER;
    }

    //    private static void tryPlaceFeature(BiomeLoadingEvent event, Set<BiomeDictionary.Type> types, FeatureConfig config, RegistryObject<PlacedFeature> feature) {
    //
    //        if (!config.shouldSpawn()) {
    //            return;
    //        }
    //        for (BiomeDictionary.Type rest : config.getBiomeRestrictions()) {
    //            if (types.contains(rest)) {
    //                return;
    //            }
    //        }
    //        boolean place = false;
    //        if (config.getBiomes().isEmpty()) {
    //            place = true;
    //        } else {
    //            Set<BiomeDictionary.Type> biomeTypes = config.getBiomes();
    //            for (BiomeDictionary.Type poss : biomeTypes) {
    //                if (types.contains(poss)) {
    //                    place = true;
    //                    break;
    //                }
    //            }
    //        }
    //        if (!place) {
    //            return;
    //        }
    //        if (config.isFeature()) {
    //            event.getGeneration().getFeatures(config.getStage()).add(feature.getHolder().get());
    //        }
    //    }
    // endregion
}
