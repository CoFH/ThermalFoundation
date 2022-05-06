package cofh.thermal.lib.common;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class ThermalFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, ID_THERMAL);
    public static final DeferredRegister<RuleTestType<?>> RULE_TESTS = DeferredRegister.create(Registry.RULE_TEST_REGISTRY, ID_THERMAL);

    public static void register(IEventBus bus) {

        FEATURES.register(bus);
        CONFIGURED_FEATURES.register(bus);
        PLACED_FEATURES.register(bus);
        PLACEMENT_MODIFIERS.register(bus);
        RULE_TESTS.register(bus);
    }

    //    private static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_LEAD_ORE = CONFIGURED_FEATURES.register("lead_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.LEAD_ORE.getDefaultState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_LEAD_ORE.getDefaultState())), ConfigManager.LEAD_ORE.getSize())));
    //
    //    public static final RegistryObject<PlacedFeature> LEAD_ORE = PLACED_FEATURES.register("lead_ore", () -> new PlacedFeature(CONFIGURED_LEAD_ORE.getHolder().get(), List.of(CountPlacement.of(ConfigManager.LEAD_ORE.getChance()), InSquarePlacement.spread(), DimensionPlacement.of(ConfigManager.LEAD_ORE.getDimensions()), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(ConfigManager.LEAD_ORE.getMinY()), VerticalAnchor.absolute(ConfigManager.LEAD_ORE.getMaxY())))));


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
    //
    //    public static List<Holder<PlacedFeature>> ORE_FEATURES = null;
    //
    //    @SubscribeEvent
    //    public static void onBiomeLoad(BiomeLoadingEvent event) {
    //
    //        if (ORE_FEATURES == null) {
    //            ORE_FEATURES = new ArrayList<>();
    //            if (ConfigManager.TIN_ORE.shouldGenerate()) {
    //                ORE_FEATURES.add(TIN_ORE.getHolder().get());
    //            }
    //            if (ConfigManager.SILVER_ORE.shouldGenerate()) {
    //                ORE_FEATURES.add(SILVER_ORE.getHolder().get());
    //            }
    //            if (ConfigManager.LEAD_ORE.shouldGenerate()) {
    //                ORE_FEATURES.add(LEAD_ORE.getHolder().get());
    //            }
    //            if (ConfigManager.SAPPHIRE_ORE.shouldGenerate()) {
    //                ORE_FEATURES.add(SAPPHIRE_ORE.getHolder().get());
    //            }
    //            if (ConfigManager.GRANITE_QUARTZ_ORE.shouldGenerate()) {
    //                ORE_FEATURES.add(GRANITE_QUARTZ_ORE.getHolder().get());
    //            }
    //        }
    //        event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).addAll(ORE_FEATURES);
    //        if (event.getName() != null) {
    //            ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
    //            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
    //            ModEntities.registerEntity(event, types);
    //            if (!ModList.get().isLoaded("dynamictrees")) {
    //                tryPlaceFeature(event, types, ConfigManager.DEAD_TREE_CONFIG, CHARRED_TREE);
    //            }
    //            tryPlaceFeature(event, types, ConfigManager.STONEPETAL_CONFIG, STONEPETAL_PATCH);
    //            tryPlaceFeature(event, types, ConfigManager.WILD_AUBERGINE, WILD_AUBERGINE_PATCH);
    //        }
    //    }

}
