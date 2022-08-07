package cofh.thermal.core.init;

import cofh.lib.util.recipes.SerializableRecipeType;
import cofh.thermal.core.util.recipes.device.*;
import cofh.thermal.core.util.recipes.dynamo.*;
import cofh.thermal.core.util.recipes.machine.*;
import net.minecraftforge.registries.RegistryObject;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.RECIPE_TYPES;

public class TCoreRecipeTypes {

    private TCoreRecipeTypes() {

    }

    public static void register() {

    }

    // region DEVICES
    public static final String ID_HIVE_EXTRACTOR_MAPPING = "hive_extractor";
    public static final String ID_TREE_EXTRACTOR_RECIPE = "tree_extractor";
    public static final String ID_TREE_EXTRACTOR_BOOST = "tree_extractor_boost";
    public static final String ID_FISHER_BOOST = "fisher_boost";
    public static final String ID_ROCK_GEN_MAPPING = "rock_gen";
    public static final String ID_POTION_DIFFUSER_BOOST = "potion_diffuser_boost";

    public static final RegistryObject<SerializableRecipeType<HiveExtractorMapping>> HIVE_EXTRACTOR_MAPPING = RECIPE_TYPES.register(ID_HIVE_EXTRACTOR_MAPPING, () -> new SerializableRecipeType<>(ID_THERMAL, ID_HIVE_EXTRACTOR_MAPPING));
    public static final RegistryObject<SerializableRecipeType<TreeExtractorRecipe>> TREE_EXTRACTOR_RECIPE = RECIPE_TYPES.register(ID_TREE_EXTRACTOR_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_TREE_EXTRACTOR_RECIPE));
    public static final RegistryObject<SerializableRecipeType<TreeExtractorBoost>> TREE_EXTRACTOR_BOOST = RECIPE_TYPES.register(ID_TREE_EXTRACTOR_BOOST, () -> new SerializableRecipeType<>(ID_THERMAL, ID_TREE_EXTRACTOR_BOOST));
    public static final RegistryObject<SerializableRecipeType<FisherBoost>> FISHER_BOOST = RECIPE_TYPES.register(ID_FISHER_BOOST, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FISHER_BOOST));
    public static final RegistryObject<SerializableRecipeType<RockGenMapping>> ROCK_GEN_MAPPING = RECIPE_TYPES.register(ID_ROCK_GEN_MAPPING, () -> new SerializableRecipeType<>(ID_THERMAL, ID_ROCK_GEN_MAPPING));
    public static final RegistryObject<SerializableRecipeType<PotionDiffuserBoost>> POTION_DIFFUSER_BOOST = RECIPE_TYPES.register(ID_POTION_DIFFUSER_BOOST, () -> new SerializableRecipeType<>(ID_THERMAL, ID_POTION_DIFFUSER_BOOST));
    // endregion

    // region FUELS
    public static final String ID_STIRLING_FUEL = "stirling_fuel";
    public static final String ID_COMPRESSION_FUEL = "compression_fuel";
    public static final String ID_MAGMATIC_FUEL = "magmatic_fuel";
    public static final String ID_NUMISMATIC_FUEL = "numismatic_fuel";
    public static final String ID_LAPIDARY_FUEL = "lapidary_fuel";
    public static final String ID_DISENCHANTMENT_FUEL = "disenchantment_fuel";
    public static final String ID_GOURMAND_FUEL = "gourmand_fuel";

    public static final RegistryObject<SerializableRecipeType<StirlingFuel>> STIRLING_FUEL = RECIPE_TYPES.register(ID_STIRLING_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_STIRLING_FUEL));
    public static final RegistryObject<SerializableRecipeType<CompressionFuel>> COMPRESSION_FUEL = RECIPE_TYPES.register(ID_COMPRESSION_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_COMPRESSION_FUEL));
    public static final RegistryObject<SerializableRecipeType<MagmaticFuel>> MAGMATIC_FUEL = RECIPE_TYPES.register(ID_MAGMATIC_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_MAGMATIC_FUEL));
    public static final RegistryObject<SerializableRecipeType<NumismaticFuel>> NUMISMATIC_FUEL = RECIPE_TYPES.register(ID_NUMISMATIC_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_NUMISMATIC_FUEL));
    public static final RegistryObject<SerializableRecipeType<LapidaryFuel>> LAPIDARY_FUEL = RECIPE_TYPES.register(ID_LAPIDARY_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_LAPIDARY_FUEL));
    public static final RegistryObject<SerializableRecipeType<DisenchantmentFuel>> DISENCHANTMENT_FUEL = RECIPE_TYPES.register(ID_DISENCHANTMENT_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_DISENCHANTMENT_FUEL));
    public static final RegistryObject<SerializableRecipeType<GourmandFuel>> GOURMAND_FUEL = RECIPE_TYPES.register(ID_GOURMAND_FUEL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_GOURMAND_FUEL));
    // endregion

    // region RECIPES
    public static final String ID_FURNACE_RECIPE = "furnace";
    public static final String ID_SAWMILL_RECIPE = "sawmill";
    public static final String ID_PULVERIZER_RECIPE = "pulverizer";
    public static final String ID_PULVERIZER_RECYCLE_RECIPE = "pulverizer_recycle";
    public static final String ID_SMELTER_RECIPE = "smelter";
    public static final String ID_SMELTER_RECYCLE_RECIPE = "smelter_recycle";
    public static final String ID_INSOLATOR_RECIPE = "insolator";
    public static final String ID_CENTRIFUGE_RECIPE = "centrifuge";
    public static final String ID_PRESS_RECIPE = "press";
    public static final String ID_CRUCIBLE_RECIPE = "crucible";
    public static final String ID_CHILLER_RECIPE = "chiller";
    public static final String ID_REFINERY_RECIPE = "refinery";
    public static final String ID_PYROLYZER_RECIPE = "pyrolyzer";
    public static final String ID_BOTTLER_RECIPE = "bottler";
    public static final String ID_BREWER_RECIPE = "brewer";

    public static final String ID_PULVERIZER_CATALYST = "pulverizer_catalyst";
    public static final String ID_SMELTER_CATALYST = "smelter_catalyst";
    public static final String ID_INSOLATOR_CATALYST = "insolator_catalyst";

    public static final RegistryObject<SerializableRecipeType<FurnaceRecipe>> FURNACE_RECIPE = RECIPE_TYPES.register(ID_FURNACE_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FURNACE_RECIPE));
    public static final RegistryObject<SerializableRecipeType<SawmillRecipe>> SAWMILL_RECIPE = RECIPE_TYPES.register(ID_SAWMILL_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_SAWMILL_RECIPE));
    public static final RegistryObject<SerializableRecipeType<PulverizerRecipe>> PULVERIZER_RECIPE = RECIPE_TYPES.register(ID_PULVERIZER_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_PULVERIZER_RECIPE));
    public static final RegistryObject<SerializableRecipeType<PulverizerRecycleRecipe>> PULVERIZER_RECYCLE_RECIPE = RECIPE_TYPES.register(ID_PULVERIZER_RECYCLE_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_PULVERIZER_RECYCLE_RECIPE));
    public static final RegistryObject<SerializableRecipeType<SmelterRecipe>> SMELTER_RECIPE = RECIPE_TYPES.register(ID_SMELTER_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_SMELTER_RECIPE));
    public static final RegistryObject<SerializableRecipeType<SmelterRecycleRecipe>> SMELTER_RECYCLE_RECIPE = RECIPE_TYPES.register(ID_SMELTER_RECYCLE_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_SMELTER_RECYCLE_RECIPE));
    public static final RegistryObject<SerializableRecipeType<InsolatorRecipe>> INSOLATOR_RECIPE = RECIPE_TYPES.register(ID_INSOLATOR_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_INSOLATOR_RECIPE));
    public static final RegistryObject<SerializableRecipeType<CentrifugeRecipe>> CENTRIFUGE_RECIPE = RECIPE_TYPES.register(ID_CENTRIFUGE_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_CENTRIFUGE_RECIPE));
    public static final RegistryObject<SerializableRecipeType<PressRecipe>> PRESS_RECIPE = RECIPE_TYPES.register(ID_PRESS_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_PRESS_RECIPE));
    public static final RegistryObject<SerializableRecipeType<CrucibleRecipe>> CRUCIBLE_RECIPE = RECIPE_TYPES.register(ID_CRUCIBLE_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_CRUCIBLE_RECIPE));
    public static final RegistryObject<SerializableRecipeType<ChillerRecipe>> CHILLER_RECIPE = RECIPE_TYPES.register(ID_CHILLER_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_CHILLER_RECIPE));
    public static final RegistryObject<SerializableRecipeType<RefineryRecipe>> REFINERY_RECIPE = RECIPE_TYPES.register(ID_REFINERY_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_REFINERY_RECIPE));
    public static final RegistryObject<SerializableRecipeType<PyrolyzerRecipe>> PYROLYZER_RECIPE = RECIPE_TYPES.register(ID_PYROLYZER_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_PYROLYZER_RECIPE));
    public static final RegistryObject<SerializableRecipeType<BottlerRecipe>> BOTTLER_RECIPE = RECIPE_TYPES.register(ID_BOTTLER_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_BOTTLER_RECIPE));
    public static final RegistryObject<SerializableRecipeType<BrewerRecipe>> BREWER_RECIPE = RECIPE_TYPES.register(ID_BREWER_RECIPE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_BREWER_RECIPE));

    public static final RegistryObject<SerializableRecipeType<PulverizerCatalyst>> PULVERIZER_CATALYST = RECIPE_TYPES.register(ID_PULVERIZER_CATALYST, () -> new SerializableRecipeType<>(ID_THERMAL, ID_PULVERIZER_CATALYST));
    public static final RegistryObject<SerializableRecipeType<SmelterCatalyst>> SMELTER_CATALYST = RECIPE_TYPES.register(ID_SMELTER_CATALYST, () -> new SerializableRecipeType<>(ID_THERMAL, ID_SMELTER_CATALYST));
    public static final RegistryObject<SerializableRecipeType<InsolatorCatalyst>> INSOLATOR_CATALYST = RECIPE_TYPES.register(ID_INSOLATOR_CATALYST, () -> new SerializableRecipeType<>(ID_THERMAL, ID_INSOLATOR_CATALYST));
    // endregion
}
