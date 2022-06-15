//package cofh.thermal.foundation.init;
//
//import cofh.core.item.ItemCoFH;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//
//import static cofh.thermal.core.util.RegistrationHelper.*;
//import static cofh.thermal.core.util.RegistrationHelper.registerAlloySet;
//import static cofh.thermal.lib.common.ThermalFlags.*;
//import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_ITEMS;
//
//public class TFndItems {
//
//    private TFndItems() {
//
//    }
//
//    public static void register() {
//
//        CreativeModeTab group = THERMAL_ITEMS;
//
//        registerItem("apatite", group);
//        registerItem("apatite_dust", group);
//        registerItem("cinnabar", group);
//        registerItem("cinnabar_dust", group);
//        registerItem("niter", group);
//        registerItem("niter_dust", group);
//        registerItem("sulfur", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(1200));
//        registerItem("sulfur_dust", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(1200));
//
//        registerMetalSet("tin", group, getFlag(FLAG_RESOURCE_TIN));
//        registerMetalSet("lead", group, getFlag(FLAG_RESOURCE_LEAD));
//        registerMetalSet("silver", group, getFlag(FLAG_RESOURCE_SILVER));
//        registerMetalSet("nickel", group, getFlag(FLAG_RESOURCE_NICKEL));
//
//        registerAlloySet("bronze", group, getFlag(FLAG_RESOURCE_BRONZE));
//        registerAlloySet("electrum", group, getFlag(FLAG_RESOURCE_ELECTRUM));
//        registerAlloySet("invar", group, getFlag(FLAG_RESOURCE_INVAR));
//        registerAlloySet("constantan", group, getFlag(FLAG_RESOURCE_CONSTANTAN));
//
//        registerGemSet("ruby", group, getFlag(FLAG_RESOURCE_RUBY));
//        registerGemSet("sapphire", group, getFlag(FLAG_RESOURCE_SAPPHIRE));
//    }
//
//}
