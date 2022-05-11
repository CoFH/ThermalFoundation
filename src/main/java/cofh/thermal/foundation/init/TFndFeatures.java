package cofh.thermal.foundation.init;

import cofh.thermal.lib.common.ThermalFeatures;

public class TFndFeatures {

    private TFndFeatures() {

    }

    public static void register() {

        ThermalFeatures.registerDefaultTriangleOreFeature("niter_ore");
        ThermalFeatures.registerDefaultTriangleOreFeature("sulfur_ore");

        ThermalFeatures.registerDefaultTriangleOreFeature("tin_ore");
        ThermalFeatures.registerDefaultTriangleOreFeature("lead_ore");
        ThermalFeatures.registerDefaultTriangleOreFeature("silver_ore");
        ThermalFeatures.registerDefaultTriangleOreFeature("nickel_ore");
    }

}
