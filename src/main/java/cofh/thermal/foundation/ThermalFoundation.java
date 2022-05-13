package cofh.thermal.foundation;

import cofh.lib.config.world.OreConfig;
import cofh.thermal.core.config.ThermalWorldConfig;
import cofh.thermal.foundation.init.TFndFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;

import static cofh.lib.util.constants.Constants.ID_THERMAL_FOUNDATION;
import static cofh.thermal.lib.common.ThermalFlags.*;

@Mod (ID_THERMAL_FOUNDATION)
public class ThermalFoundation {

    public ThermalFoundation() {

        setFeatureFlags();
        addWorldConfigs();

        TFndFeatures.register();
    }

    private void setFeatureFlags() {

        setFlag(FLAG_RESOURCE_NITER, true);
        setFlag(FLAG_RESOURCE_SULFUR, true);

        setFlag(FLAG_RESOURCE_TIN, true);
        setFlag(FLAG_RESOURCE_LEAD, true);
        setFlag(FLAG_RESOURCE_SILVER, true);
        setFlag(FLAG_RESOURCE_NICKEL, true);
    }

    private void addWorldConfigs() {

        List<ResourceKey<Level>> defaultDimensions = Collections.singletonList(Level.OVERWORLD);

        ThermalWorldConfig.addOreConfig("niter_ore", new OreConfig("Niter", 2, -16, 64, 7, defaultDimensions));
        ThermalWorldConfig.addOreConfig("sulfur_ore", new OreConfig("Sulfur", 2, -16, 32, 7, defaultDimensions));

        ThermalWorldConfig.addOreConfig("tin_ore", new OreConfig("Tin", 6, -20, 60, 9, defaultDimensions));
        ThermalWorldConfig.addOreConfig("lead_ore", new OreConfig("Lead", 6, -60, 40, 8, defaultDimensions));
        ThermalWorldConfig.addOreConfig("silver_ore", new OreConfig("Silver", 4, -60, 40, 8, defaultDimensions));
        ThermalWorldConfig.addOreConfig("nickel_ore", new OreConfig("Nickel", 4, -40, 120, 8, defaultDimensions));
    }

}
