package cofh.thermal.core.init;

import cofh.thermal.core.util.managers.dynamo.*;
import cofh.thermal.core.util.managers.machine.*;
import cofh.thermal.core.util.recipes.device.*;
import cofh.thermal.core.util.recipes.dynamo.*;
import cofh.thermal.core.util.recipes.machine.*;
import cofh.thermal.lib.util.recipes.DynamoFuelSerializer;
import cofh.thermal.lib.util.recipes.MachineCatalystSerializer;
import cofh.thermal.lib.util.recipes.MachineRecipeSerializer;
import net.minecraftforge.registries.RegistryObject;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.*;

public class TCoreRecipeSerializers {

    private TCoreRecipeSerializers() {

    }

    public static void register() {

    }

    public static final RegistryObject<HiveExtractorMapping.Serializer> HIVE_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(ID_HIVE_EXTRACTOR_MAPPING, HiveExtractorMapping.Serializer::new);
    public static final RegistryObject<TreeExtractorRecipe.Serializer> TREE_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(ID_TREE_EXTRACTOR_RECIPE, TreeExtractorRecipe.Serializer::new);
    public static final RegistryObject<TreeExtractorBoost.Serializer> TREE_EXTRACTOR_BOOST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_TREE_EXTRACTOR_BOOST, TreeExtractorBoost.Serializer::new);
    public static final RegistryObject<FisherBoost.Serializer> FISHER_BOOST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_FISHER_BOOST, FisherBoost.Serializer::new);
    public static final RegistryObject<RockGenMapping.Serializer> ROCK_GEN_SERIALIZER = RECIPE_SERIALIZERS.register(ID_ROCK_GEN_MAPPING, RockGenMapping.Serializer::new);
    public static final RegistryObject<PotionDiffuserBoost.Serializer> POTION_DIFFUSER_BOOST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_POTION_DIFFUSER_BOOST, PotionDiffuserBoost.Serializer::new);

    public static final RegistryObject<MachineRecipeSerializer<FurnaceRecipe>> FURNACE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_FURNACE_RECIPE, () -> new MachineRecipeSerializer<>(FurnaceRecipe::new, FurnaceRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<SawmillRecipe>> SAWMILL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SAWMILL_RECIPE, () -> new MachineRecipeSerializer<>(SawmillRecipe::new, SawmillRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<PulverizerRecipe>> PULVERIZER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PULVERIZER_RECIPE, () -> new MachineRecipeSerializer<>(PulverizerRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<PulverizerRecycleRecipe>> PULVERIZER_RECYCLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PULVERIZER_RECYCLE_RECIPE, () -> new MachineRecipeSerializer<>(PulverizerRecycleRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<SmelterRecipe>> SMELTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SMELTER_RECIPE, () -> new MachineRecipeSerializer<>(SmelterRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<SmelterRecycleRecipe>> SMELTER_RECYCLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SMELTER_RECYCLE_RECIPE, () -> new MachineRecipeSerializer<>(SmelterRecycleRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<InsolatorRecipe>> INSOLATOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_INSOLATOR_RECIPE, () -> new InsolatorRecipe.Serializer<>(InsolatorRecipe::new, InsolatorRecipeManager.instance().getDefaultEnergy(), InsolatorRecipeManager.instance().getDefaultWater()));
    public static final RegistryObject<MachineRecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CENTRIFUGE_RECIPE, () -> new MachineRecipeSerializer<>(CentrifugeRecipe::new, CentrifugeRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<PressRecipe>> PRESS_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PRESS_RECIPE, () -> new MachineRecipeSerializer<>(PressRecipe::new, PressRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<CrucibleRecipe>> CRUCIBLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CRUCIBLE_RECIPE, () -> new MachineRecipeSerializer<>(CrucibleRecipe::new, CrucibleRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<ChillerRecipe>> CHILLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_CHILLER_RECIPE, () -> new MachineRecipeSerializer<>(ChillerRecipe::new, ChillerRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<RefineryRecipe>> REFINERY_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_REFINERY_RECIPE, () -> new MachineRecipeSerializer<>(RefineryRecipe::new, RefineryRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<PyrolyzerRecipe>> PYROLYZER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PYROLYZER_RECIPE, () -> new MachineRecipeSerializer<>(PyrolyzerRecipe::new, PyrolyzerRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<BrewerRecipe>> BREWER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_BREWER_RECIPE, () -> new MachineRecipeSerializer<>(BrewerRecipe::new, BrewerRecipeManager.instance().getDefaultEnergy()));
    public static final RegistryObject<MachineRecipeSerializer<BottlerRecipe>> BOTTLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(ID_BOTTLER_RECIPE, () -> new MachineRecipeSerializer<>(BottlerRecipe::new, BottlerRecipeManager.instance().getDefaultEnergy()));

    public static final RegistryObject<MachineCatalystSerializer<PulverizerCatalyst>> PULVERIZER_CATALYST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_PULVERIZER_CATALYST, () -> new MachineCatalystSerializer<>(PulverizerCatalyst::new));
    public static final RegistryObject<MachineCatalystSerializer<SmelterCatalyst>> SMELTER_CATALYST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_SMELTER_CATALYST, () -> new MachineCatalystSerializer<>(SmelterCatalyst::new));
    public static final RegistryObject<MachineCatalystSerializer<InsolatorCatalyst>> INSOLATOR_CATALYST_SERIALIZER = RECIPE_SERIALIZERS.register(ID_INSOLATOR_CATALYST, () -> new MachineCatalystSerializer<>(InsolatorCatalyst::new));

    public static final RegistryObject<DynamoFuelSerializer<StirlingFuel>> STIRLING_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_STIRLING_FUEL, () -> new DynamoFuelSerializer<>(StirlingFuel::new, StirlingFuelManager.instance().getDefaultEnergy(), StirlingFuelManager.MIN_ENERGY, StirlingFuelManager.MAX_ENERGY));
    public static final RegistryObject<DynamoFuelSerializer<CompressionFuel>> COMPRESSION_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_COMPRESSION_FUEL, () -> new DynamoFuelSerializer<>(CompressionFuel::new, CompressionFuelManager.instance().getDefaultEnergy(), CompressionFuelManager.MIN_ENERGY, CompressionFuelManager.MAX_ENERGY));
    public static final RegistryObject<DynamoFuelSerializer<MagmaticFuel>> MAGMATIC_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_MAGMATIC_FUEL, () -> new DynamoFuelSerializer<>(MagmaticFuel::new, MagmaticFuelManager.instance().getDefaultEnergy(), MagmaticFuelManager.MIN_ENERGY, MagmaticFuelManager.MAX_ENERGY));
    public static final RegistryObject<DynamoFuelSerializer<NumismaticFuel>> NUMISMATIC_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_NUMISMATIC_FUEL, () -> new DynamoFuelSerializer<>(NumismaticFuel::new, NumismaticFuelManager.instance().getDefaultEnergy(), NumismaticFuelManager.MIN_ENERGY, NumismaticFuelManager.MAX_ENERGY));
    public static final RegistryObject<DynamoFuelSerializer<LapidaryFuel>> LAPIDARY_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_LAPIDARY_FUEL, () -> new DynamoFuelSerializer<>(LapidaryFuel::new, LapidaryFuelManager.instance().getDefaultEnergy(), LapidaryFuelManager.MIN_ENERGY, LapidaryFuelManager.MAX_ENERGY));
    public static final RegistryObject<DynamoFuelSerializer<DisenchantmentFuel>> DISENCHANTMENT_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_DISENCHANTMENT_FUEL, () -> new DynamoFuelSerializer<>(DisenchantmentFuel::new, DisenchantmentFuelManager.instance().getDefaultEnergy(), DisenchantmentFuelManager.MIN_ENERGY, DisenchantmentFuelManager.MAX_ENERGY));
    public static final RegistryObject<DynamoFuelSerializer<GourmandFuel>> GOURMAND_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register(ID_GOURMAND_FUEL, () -> new DynamoFuelSerializer<>(GourmandFuel::new, GourmandFuelManager.instance().getDefaultEnergy(), GourmandFuelManager.MIN_ENERGY, GourmandFuelManager.MAX_ENERGY));

}
