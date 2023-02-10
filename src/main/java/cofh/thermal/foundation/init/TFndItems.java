package cofh.thermal.foundation.init;

import cofh.lib.item.SignItemCoFH;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_BLOCKS;
import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_ITEMS;

public class TFndItems {

    private TFndItems() {

    }

    public static void register() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerMetalSet("tin", group, getFlag(FLAG_RESOURCE_TIN), ID_THERMAL_FOUNDATION);
        registerMetalSet("lead", group, getFlag(FLAG_RESOURCE_LEAD), ID_THERMAL_FOUNDATION);
        registerMetalSet("silver", group, getFlag(FLAG_RESOURCE_SILVER), ID_THERMAL_FOUNDATION);
        registerMetalSet("nickel", group, getFlag(FLAG_RESOURCE_NICKEL), ID_THERMAL_FOUNDATION);

        registerAlloySet("bronze", group, getFlag(FLAG_RESOURCE_BRONZE), ID_THERMAL_FOUNDATION);
        registerAlloySet("electrum", group, getFlag(FLAG_RESOURCE_ELECTRUM), ID_THERMAL_FOUNDATION);
        registerAlloySet("invar", group, getFlag(FLAG_RESOURCE_INVAR), ID_THERMAL_FOUNDATION);
        registerAlloySet("constantan", group, getFlag(FLAG_RESOURCE_CONSTANTAN), ID_THERMAL_FOUNDATION);

        registerGemSet("ruby", group, getFlag(FLAG_RESOURCE_RUBY), ID_THERMAL_FOUNDATION);
        registerGemSet("sapphire", group, getFlag(FLAG_RESOURCE_SAPPHIRE), ID_THERMAL_FOUNDATION);

        registerItem("rubberwood_sign", () -> new SignItemCoFH(new Item.Properties().stacksTo(16).tab(THERMAL_BLOCKS), BLOCKS.get("rubberwood_sign"), BLOCKS.get("rubberwood_wall_sign")).setModId(ID_THERMAL_FOUNDATION));
    }

}
