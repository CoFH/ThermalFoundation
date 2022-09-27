package cofh.thermal.core.init;

import cofh.thermal.core.util.managers.device.*;
import cofh.thermal.core.util.managers.dynamo.*;
import cofh.thermal.core.util.managers.machine.*;

import static cofh.thermal.lib.common.ThermalRecipeManagers.registerManager;

public class TCoreRecipeManagers {

    private TCoreRecipeManagers() {

    }

    public static void register() {

        registerManager(HiveExtractorManager.instance());
        registerManager(TreeExtractorManager.instance());
        registerManager(FisherManager.instance());
        registerManager(RockGenManager.instance());
        registerManager(PotionDiffuserManager.instance());

        registerManager(FurnaceRecipeManager.instance());
        registerManager(SawmillRecipeManager.instance());
        registerManager(PulverizerRecipeManager.instance());
        registerManager(SmelterRecipeManager.instance());
        registerManager(InsolatorRecipeManager.instance());
        registerManager(CentrifugeRecipeManager.instance());
        registerManager(PressRecipeManager.instance());
        registerManager(CrucibleRecipeManager.instance());
        registerManager(ChillerRecipeManager.instance());
        registerManager(RefineryRecipeManager.instance());
        registerManager(PyrolyzerRecipeManager.instance());
        registerManager(BottlerRecipeManager.instance());
        registerManager(BrewerRecipeManager.instance());
        registerManager(CrystallizerRecipeManager.instance());
        registerManager(CrafterRecipeManager.instance());

        registerManager(StirlingFuelManager.instance());
        registerManager(CompressionFuelManager.instance());
        registerManager(MagmaticFuelManager.instance());
        registerManager(NumismaticFuelManager.instance());
        registerManager(LapidaryFuelManager.instance());
        registerManager(DisenchantmentFuelManager.instance());
        registerManager(GourmandFuelManager.instance());
    }

}
