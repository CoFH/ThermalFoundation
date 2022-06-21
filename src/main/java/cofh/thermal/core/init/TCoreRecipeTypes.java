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
    public static final String ID_MAPPING_TREE_EXTRACTOR = "tree_extractor";
    public static final String ID_MAPPING_HIVE_EXTRACTOR = "hive_extractor";
    public static final String ID_BOOST_TREE_EXTRACTOR = "tree_extractor_boost";
    public static final String ID_BOOST_FISHER = "fisher_boost";
    public static final String ID_MAPPING_ROCK_GEN = "rock_gen";
    public static final String ID_BOOST_POTION_DIFFUSER = "potion_diffuser_boost";

    public static final RegistryObject<SerializableRecipeType<HiveExtractorMapping>> MAPPING_HIVE_EXTRACTOR = RECIPE_TYPES.register(ID_MAPPING_TREE_EXTRACTOR, () -> new SerializableRecipeType<>(ID_THERMAL, ID_MAPPING_TREE_EXTRACTOR));
    public static final RegistryObject<SerializableRecipeType<TreeExtractorMapping>> MAPPING_TREE_EXTRACTOR = RECIPE_TYPES.register(ID_MAPPING_HIVE_EXTRACTOR, () -> new SerializableRecipeType<>(ID_THERMAL, ID_MAPPING_HIVE_EXTRACTOR));
    public static final RegistryObject<SerializableRecipeType<TreeExtractorBoost>> BOOST_TREE_EXTRACTOR = RECIPE_TYPES.register(ID_BOOST_TREE_EXTRACTOR, () -> new SerializableRecipeType<>(ID_THERMAL, ID_BOOST_TREE_EXTRACTOR));
    public static final RegistryObject<SerializableRecipeType<FisherBoost>> BOOST_FISHER = RECIPE_TYPES.register(ID_BOOST_FISHER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_BOOST_FISHER));
    public static final RegistryObject<SerializableRecipeType<RockGenMapping>> MAPPING_ROCK_GEN = RECIPE_TYPES.register(ID_MAPPING_ROCK_GEN, () -> new SerializableRecipeType<>(ID_THERMAL, ID_MAPPING_ROCK_GEN));
    public static final RegistryObject<SerializableRecipeType<PotionDiffuserBoost>> BOOST_POTION_DIFFUSER = RECIPE_TYPES.register(ID_BOOST_POTION_DIFFUSER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_BOOST_POTION_DIFFUSER));
    // endregion

    // region FUELS
    public static final String ID_FUEL_STIRLING = "stirling_fuel";
    public static final String ID_FUEL_COMPRESSION = "compression_fuel";
    public static final String ID_FUEL_MAGMATIC = "magmatic_fuel";
    public static final String ID_FUEL_NUMISMATIC = "numismatic_fuel";
    public static final String ID_FUEL_LAPIDARY = "lapidary_fuel";
    public static final String ID_FUEL_DISENCHANTMENT = "disenchantment_fuel";
    public static final String ID_FUEL_GOURMAND = "gourmand_fuel";

    public static final RegistryObject<SerializableRecipeType<StirlingFuel>> FUEL_STIRLING = RECIPE_TYPES.register(ID_FUEL_STIRLING, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_STIRLING));
    public static final RegistryObject<SerializableRecipeType<CompressionFuel>> FUEL_COMPRESSION = RECIPE_TYPES.register(ID_FUEL_COMPRESSION, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_COMPRESSION));
    public static final RegistryObject<SerializableRecipeType<MagmaticFuel>> FUEL_MAGMATIC = RECIPE_TYPES.register(ID_FUEL_MAGMATIC, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_MAGMATIC));
    public static final RegistryObject<SerializableRecipeType<NumismaticFuel>> FUEL_NUMISMATIC = RECIPE_TYPES.register(ID_FUEL_NUMISMATIC, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_NUMISMATIC));
    public static final RegistryObject<SerializableRecipeType<LapidaryFuel>> FUEL_LAPIDARY = RECIPE_TYPES.register(ID_FUEL_LAPIDARY, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_LAPIDARY));
    public static final RegistryObject<SerializableRecipeType<DisenchantmentFuel>> FUEL_DISENCHANTMENT = RECIPE_TYPES.register(ID_FUEL_DISENCHANTMENT, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_DISENCHANTMENT));
    public static final RegistryObject<SerializableRecipeType<GourmandFuel>> FUEL_GOURMAND = RECIPE_TYPES.register(ID_FUEL_GOURMAND, () -> new SerializableRecipeType<>(ID_THERMAL, ID_FUEL_GOURMAND));
    // endregion

    // region RECIPES
    public static final String ID_RECIPE_FURNACE = "furnace";
    public static final String ID_RECIPE_SAWMILL = "sawmill";
    public static final String ID_RECIPE_PULVERIZER = "pulverizer";
    public static final String ID_RECIPE_PULVERIZER_RECYCLE = "pulverizer_recycle";
    public static final String ID_RECIPE_SMELTER = "smelter";
    public static final String ID_RECIPE_SMELTER_RECYCLE = "smelter_recycle";
    public static final String ID_RECIPE_INSOLATOR = "insolator";
    public static final String ID_RECIPE_CENTRIFUGE = "centrifuge";
    public static final String ID_RECIPE_PRESS = "press";
    public static final String ID_RECIPE_CRUCIBLE = "crucible";
    public static final String ID_RECIPE_CHILLER = "chiller";
    public static final String ID_RECIPE_REFINERY = "refinery";
    public static final String ID_RECIPE_PYROLYZER = "pyrolyzer";
    public static final String ID_RECIPE_BOTTLER = "bottler";
    public static final String ID_RECIPE_BREWER = "brewer";

    public static final String ID_CATALYST_PULVERIZER = "pulverizer_catalyst";
    public static final String ID_CATALYST_SMELTER = "smelter_catalyst";
    public static final String ID_CATALYST_INSOLATOR = "insolator_catalyst";

    public static final RegistryObject<SerializableRecipeType<FurnaceRecipe>> RECIPE_FURNACE = RECIPE_TYPES.register(ID_RECIPE_FURNACE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_FURNACE));
    public static final RegistryObject<SerializableRecipeType<SawmillRecipe>> RECIPE_SAWMILL = RECIPE_TYPES.register(ID_RECIPE_SAWMILL, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_SAWMILL));
    public static final RegistryObject<SerializableRecipeType<PulverizerRecipe>> RECIPE_PULVERIZER = RECIPE_TYPES.register(ID_RECIPE_PULVERIZER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_PULVERIZER));
    public static final RegistryObject<SerializableRecipeType<PulverizerRecycleRecipe>> RECIPE_PULVERIZER_RECYCLE = RECIPE_TYPES.register(ID_RECIPE_PULVERIZER_RECYCLE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_PULVERIZER_RECYCLE));
    public static final RegistryObject<SerializableRecipeType<SmelterRecipe>> RECIPE_SMELTER = RECIPE_TYPES.register(ID_RECIPE_SMELTER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_SMELTER));
    public static final RegistryObject<SerializableRecipeType<SmelterRecycleRecipe>> RECIPE_SMELTER_RECYCLE = RECIPE_TYPES.register(ID_RECIPE_SMELTER_RECYCLE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_SMELTER_RECYCLE));
    public static final RegistryObject<SerializableRecipeType<InsolatorRecipe>> RECIPE_INSOLATOR = RECIPE_TYPES.register(ID_RECIPE_INSOLATOR, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_INSOLATOR));
    public static final RegistryObject<SerializableRecipeType<CentrifugeRecipe>> RECIPE_CENTRIFUGE = RECIPE_TYPES.register(ID_RECIPE_CENTRIFUGE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_CENTRIFUGE));
    public static final RegistryObject<SerializableRecipeType<PressRecipe>> RECIPE_PRESS = RECIPE_TYPES.register(ID_RECIPE_PRESS, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_PRESS));
    public static final RegistryObject<SerializableRecipeType<CrucibleRecipe>> RECIPE_CRUCIBLE = RECIPE_TYPES.register(ID_RECIPE_CRUCIBLE, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_CRUCIBLE));
    public static final RegistryObject<SerializableRecipeType<ChillerRecipe>> RECIPE_CHILLER = RECIPE_TYPES.register(ID_RECIPE_CHILLER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_CHILLER));
    public static final RegistryObject<SerializableRecipeType<RefineryRecipe>> RECIPE_REFINERY = RECIPE_TYPES.register(ID_RECIPE_REFINERY, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_REFINERY));
    public static final RegistryObject<SerializableRecipeType<PyrolyzerRecipe>> RECIPE_PYROLYZER = RECIPE_TYPES.register(ID_RECIPE_PYROLYZER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_PYROLYZER));
    public static final RegistryObject<SerializableRecipeType<BottlerRecipe>> RECIPE_BOTTLER = RECIPE_TYPES.register(ID_RECIPE_BOTTLER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_BOTTLER));
    public static final RegistryObject<SerializableRecipeType<BrewerRecipe>> RECIPE_BREWER = RECIPE_TYPES.register(ID_RECIPE_BREWER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_RECIPE_BREWER));

    public static final RegistryObject<SerializableRecipeType<PulverizerCatalyst>> CATALYST_PULVERIZER = RECIPE_TYPES.register(ID_CATALYST_PULVERIZER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_CATALYST_PULVERIZER));
    public static final RegistryObject<SerializableRecipeType<SmelterCatalyst>> CATALYST_SMELTER = RECIPE_TYPES.register(ID_CATALYST_SMELTER, () -> new SerializableRecipeType<>(ID_THERMAL, ID_CATALYST_SMELTER));
    public static final RegistryObject<SerializableRecipeType<InsolatorCatalyst>> CATALYST_INSOLATOR = RECIPE_TYPES.register(ID_CATALYST_INSOLATOR, () -> new SerializableRecipeType<>(ID_THERMAL, ID_CATALYST_INSOLATOR));
    // endregion
}
