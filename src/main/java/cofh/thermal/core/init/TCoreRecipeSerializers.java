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

        // RECIPE_SERIALIZERS.register(ID_MAPPING_HIVE_EXTRACTOR, HiveExtractorMappingSerializer::new);
        RECIPE_SERIALIZERS.register(ID_BOOST_TREE_EXTRACTOR, TreeExtractorBoostSerializer::new);
        RECIPE_SERIALIZERS.register(ID_MAPPING_TREE_EXTRACTOR, TreeExtractorMappingSerializer::new);
        RECIPE_SERIALIZERS.register(ID_BOOST_FISHER, FisherBoostSerializer::new);
        RECIPE_SERIALIZERS.register(ID_MAPPING_ROCK_GEN, RockGenMappingSerializer::new);
        RECIPE_SERIALIZERS.register(ID_BOOST_POTION_DIFFUSER, PotionDiffuserBoostSerializer::new);

        RECIPE_SERIALIZERS.register(ID_RECIPE_FURNACE, () -> new MachineRecipeSerializer<>(FurnaceRecipe::new, FurnaceRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SAWMILL, () -> new MachineRecipeSerializer<>(SawmillRecipe::new, SawmillRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PULVERIZER, () -> new MachineRecipeSerializer<>(PulverizerRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PULVERIZER_RECYCLE, () -> new MachineRecipeSerializer<>(PulverizerRecycleRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SMELTER, () -> new MachineRecipeSerializer<>(SmelterRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SMELTER_RECYCLE, () -> new MachineRecipeSerializer<>(SmelterRecycleRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_INSOLATOR, () -> new InsolatorRecipeSerializer<>(InsolatorRecipe::new, InsolatorRecipeManager.instance().getDefaultEnergy(), InsolatorRecipeManager.instance().getDefaultWater()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CENTRIFUGE, () -> new MachineRecipeSerializer<>(CentrifugeRecipe::new, CentrifugeRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PRESS, () -> new MachineRecipeSerializer<>(PressRecipe::new, PressRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CRUCIBLE, () -> new MachineRecipeSerializer<>(CrucibleRecipe::new, CrucibleRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CHILLER, () -> new MachineRecipeSerializer<>(ChillerRecipe::new, ChillerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_REFINERY, () -> new MachineRecipeSerializer<>(RefineryRecipe::new, RefineryRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PYROLYZER, () -> new MachineRecipeSerializer<>(PyrolyzerRecipe::new, PyrolyzerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_BREWER, () -> new MachineRecipeSerializer<>(BrewerRecipe::new, BrewerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_BOTTLER, () -> new MachineRecipeSerializer<>(BottlerRecipe::new, BottlerRecipeManager.instance().getDefaultEnergy()));

        RECIPE_SERIALIZERS.register(ID_CATALYST_PULVERIZER, () -> new MachineCatalystSerializer<>(PulverizerCatalyst::new));
        RECIPE_SERIALIZERS.register(ID_CATALYST_SMELTER, () -> new MachineCatalystSerializer<>(SmelterCatalyst::new));
        RECIPE_SERIALIZERS.register(ID_CATALYST_INSOLATOR, () -> new MachineCatalystSerializer<>(InsolatorCatalyst::new));

        RECIPE_SERIALIZERS.register(ID_FUEL_STIRLING, () -> new DynamoFuelSerializer<>(StirlingFuel::new, StirlingFuelManager.instance().getDefaultEnergy(), StirlingFuelManager.MIN_ENERGY, StirlingFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_COMPRESSION, () -> new DynamoFuelSerializer<>(CompressionFuel::new, CompressionFuelManager.instance().getDefaultEnergy(), CompressionFuelManager.MIN_ENERGY, CompressionFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_MAGMATIC, () -> new DynamoFuelSerializer<>(MagmaticFuel::new, MagmaticFuelManager.instance().getDefaultEnergy(), MagmaticFuelManager.MIN_ENERGY, MagmaticFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_NUMISMATIC, () -> new DynamoFuelSerializer<>(NumismaticFuel::new, NumismaticFuelManager.instance().getDefaultEnergy(), NumismaticFuelManager.MIN_ENERGY, NumismaticFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_LAPIDARY, () -> new DynamoFuelSerializer<>(LapidaryFuel::new, LapidaryFuelManager.instance().getDefaultEnergy(), LapidaryFuelManager.MIN_ENERGY, LapidaryFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_DISENCHANTMENT, () -> new DynamoFuelSerializer<>(DisenchantmentFuel::new, DisenchantmentFuelManager.instance().getDefaultEnergy(), DisenchantmentFuelManager.MIN_ENERGY, DisenchantmentFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_GOURMAND, () -> new DynamoFuelSerializer<>(GourmandFuel::new, GourmandFuelManager.instance().getDefaultEnergy(), GourmandFuelManager.MIN_ENERGY, GourmandFuelManager.MAX_ENERGY));
    }

    public static final RegistryObject<HiveExtractorMappingSerializer> HIVE_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZERS.register(ID_MAPPING_HIVE_EXTRACTOR, HiveExtractorMappingSerializer::new);

}
