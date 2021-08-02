package cofh.thermal.core.init;

import cofh.thermal.core.fluid.*;

public class TCoreFluids {

    private TCoreFluids() {

    }

    public static void register() {

        RedstoneFluid.create();
        GlowstoneFluid.create();
        EnderFluid.create();

        SapFluid.create();
        SyrupFluid.create();
        ResinFluid.create();
        TreeOilFluid.create();
        LatexFluid.create();

        //        SeedOilFluid.create();
        //        BiocrudeFluid.create();
        //        BiofuelFluid.create();

        CreosoteFluid.create();
        CrudeOilFluid.create();
        HeavyOilFluid.create();
        LightOilFluid.create();
        RefinedFuelFluid.create();
    }

}
