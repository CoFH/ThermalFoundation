package cofh.thermal.core.init;

import cofh.thermal.core.util.managers.dynamo.*;
import cofh.thermal.core.util.managers.machine.*;
import cofh.thermal.core.util.recipes.device.*;
import cofh.thermal.core.util.recipes.dynamo.*;
import cofh.thermal.core.util.recipes.machine.*;
import cofh.thermal.lib.util.recipes.DynamoFuelSerializer;
import cofh.thermal.lib.util.recipes.MachineCatalystSerializer;
import cofh.thermal.lib.util.recipes.MachineRecipeSerializer;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.*;

public class TCoreRecipeSerializers {

    private TCoreRecipeSerializers() {

    }

    public static void register() {

        RECIPE_SERIALIZERS.register(ID_MAPPING_HIVE_EXTRACTOR.getPath(), HiveExtractorMappingSerializer::new);
        RECIPE_SERIALIZERS.register(ID_BOOST_TREE_EXTRACTOR.getPath(), TreeExtractorBoostSerializer::new);
        RECIPE_SERIALIZERS.register(ID_MAPPING_TREE_EXTRACTOR.getPath(), TreeExtractorMappingSerializer::new);
        RECIPE_SERIALIZERS.register(ID_BOOST_FISHER.getPath(), FisherBoostSerializer::new);
        RECIPE_SERIALIZERS.register(ID_MAPPING_ROCK_GEN.getPath(), RockGenMappingSerializer::new);
        RECIPE_SERIALIZERS.register(ID_BOOST_POTION_DIFFUSER.getPath(), PotionDiffuserBoostSerializer::new);

        RECIPE_SERIALIZERS.register(ID_RECIPE_FURNACE.getPath(), () -> new MachineRecipeSerializer<>(FurnaceRecipe::new, FurnaceRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SAWMILL.getPath(), () -> new MachineRecipeSerializer<>(SawmillRecipe::new, SawmillRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PULVERIZER.getPath(), () -> new MachineRecipeSerializer<>(PulverizerRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PULVERIZER_RECYCLE.getPath(), () -> new MachineRecipeSerializer<>(PulverizerRecycleRecipe::new, PulverizerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SMELTER.getPath(), () -> new MachineRecipeSerializer<>(SmelterRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_SMELTER_RECYCLE.getPath(), () -> new MachineRecipeSerializer<>(SmelterRecycleRecipe::new, SmelterRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_INSOLATOR.getPath(), () -> new InsolatorRecipeSerializer<>(InsolatorRecipe::new, InsolatorRecipeManager.instance().getDefaultEnergy(), InsolatorRecipeManager.instance().getDefaultWater()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CENTRIFUGE.getPath(), () -> new MachineRecipeSerializer<>(CentrifugeRecipe::new, CentrifugeRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PRESS.getPath(), () -> new MachineRecipeSerializer<>(PressRecipe::new, PressRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CRUCIBLE.getPath(), () -> new MachineRecipeSerializer<>(CrucibleRecipe::new, CrucibleRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_CHILLER.getPath(), () -> new MachineRecipeSerializer<>(ChillerRecipe::new, ChillerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_REFINERY.getPath(), () -> new MachineRecipeSerializer<>(RefineryRecipe::new, RefineryRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_PYROLYZER.getPath(), () -> new MachineRecipeSerializer<>(PyrolyzerRecipe::new, PyrolyzerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_BREWER.getPath(), () -> new MachineRecipeSerializer<>(BrewerRecipe::new, BrewerRecipeManager.instance().getDefaultEnergy()));
        RECIPE_SERIALIZERS.register(ID_RECIPE_BOTTLER.getPath(), () -> new MachineRecipeSerializer<>(BottlerRecipe::new, BottlerRecipeManager.instance().getDefaultEnergy()));

        RECIPE_SERIALIZERS.register(ID_CATALYST_PULVERIZER.getPath(), () -> new MachineCatalystSerializer<>(PulverizerCatalyst::new));
        RECIPE_SERIALIZERS.register(ID_CATALYST_SMELTER.getPath(), () -> new MachineCatalystSerializer<>(SmelterCatalyst::new));
        RECIPE_SERIALIZERS.register(ID_CATALYST_INSOLATOR.getPath(), () -> new MachineCatalystSerializer<>(InsolatorCatalyst::new));

        RECIPE_SERIALIZERS.register(ID_FUEL_STIRLING.getPath(), () -> new DynamoFuelSerializer<>(StirlingFuel::new, StirlingFuelManager.instance().getDefaultEnergy(), StirlingFuelManager.MIN_ENERGY, StirlingFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_COMPRESSION.getPath(), () -> new DynamoFuelSerializer<>(CompressionFuel::new, CompressionFuelManager.instance().getDefaultEnergy(), CompressionFuelManager.MIN_ENERGY, CompressionFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_MAGMATIC.getPath(), () -> new DynamoFuelSerializer<>(MagmaticFuel::new, MagmaticFuelManager.instance().getDefaultEnergy(), MagmaticFuelManager.MIN_ENERGY, MagmaticFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_NUMISMATIC.getPath(), () -> new DynamoFuelSerializer<>(NumismaticFuel::new, NumismaticFuelManager.instance().getDefaultEnergy(), NumismaticFuelManager.MIN_ENERGY, NumismaticFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_LAPIDARY.getPath(), () -> new DynamoFuelSerializer<>(LapidaryFuel::new, LapidaryFuelManager.instance().getDefaultEnergy(), LapidaryFuelManager.MIN_ENERGY, LapidaryFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_DISENCHANTMENT.getPath(), () -> new DynamoFuelSerializer<>(DisenchantmentFuel::new, DisenchantmentFuelManager.instance().getDefaultEnergy(), DisenchantmentFuelManager.MIN_ENERGY, DisenchantmentFuelManager.MAX_ENERGY));
        RECIPE_SERIALIZERS.register(ID_FUEL_GOURMAND.getPath(), () -> new DynamoFuelSerializer<>(GourmandFuel::new, GourmandFuelManager.instance().getDefaultEnergy(), GourmandFuelManager.MIN_ENERGY, GourmandFuelManager.MAX_ENERGY));
    }

}
