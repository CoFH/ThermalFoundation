package cofh.thermal.foundation;

import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.lib.common.ThermalFlags.*;

@Mod (ID_THERMAL_FOUNDATION)
public class ThermalFoundation {

    public ThermalFoundation() {

        setFeatureFlags();
    }

    private void setFeatureFlags() {

        setFlag(FLAG_RESOURCE_NITER, true);
        setFlag(FLAG_RESOURCE_SULFUR, true);

        setFlag(FLAG_RESOURCE_TIN, true);
        setFlag(FLAG_RESOURCE_LEAD, true);
        setFlag(FLAG_RESOURCE_SILVER, true);
        setFlag(FLAG_RESOURCE_NICKEL, true);
    }

}
