//package cofh.thermal.lib.common;
//
//import net.minecraftforge.common.ForgeConfigSpec;
//import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
//import net.minecraftforge.fml.ModLoadingContext;
//import net.minecraftforge.fml.config.ModConfig;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//
//import static cofh.thermal.lib.common.ThermalFlags.*;
//
//public class ThermalConfig {
//
//    private static boolean registered = false;
//
//    public static void register() {
//
//        if (registered) {
//            return;
//        }
//        FMLJavaModLoadingContext.get().getModEventBus().register(ThermalConfig.class);
//        registered = true;
//
//        genClientConfig();
//
//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);
//    }
//
//    public static void setup() {
//
//        // This allows flags to be set before the server configuration is generated.
//        genServerConfig();
//
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
//    }
//
//    private ThermalConfig() {
//
//    }
//
//    // region CONFIG SPEC
//    private static final ForgeConfigSpec.Builder SERVER_CONFIG = new ForgeConfigSpec.Builder();
//    private static ForgeConfigSpec serverSpec;
//
//    private static final ForgeConfigSpec.Builder CLIENT_CONFIG = new ForgeConfigSpec.Builder();
//    private static ForgeConfigSpec clientSpec;
//
//    private static void genServerConfig() {
//
//        genWorldConfig();
//
//        serverSpec = SERVER_CONFIG.build();
//
//        refreshServerConfig();
//    }
//
//    private static void genClientConfig() {
//
//    }
//
//    private static void genWorldConfig() {
//
//        SERVER_CONFIG.push("World Generation");
//
//        worldGenApatite = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Apatite from generating.")
//                .define("Apatite", true);
//        worldGenCinnabar = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Cinnabar from generating.")
//                .define("Cinnabar", true);
//        worldGenNiter = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Niter from generating.")
//                .define("Niter", true);
//        worldGenSulfur = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Sulfur from generating.")
//                .define("Sulfur", true);
//
//        worldGenTin = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Tin from generating.")
//                .define("Tin", true);
//        worldGenLead = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Lead from generating.")
//                .define("Lead", true);
//        worldGenSilver = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Silver from generating.")
//                .define("Silver", true);
//        worldGenNickel = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Nickel from generating.")
//                .define("Nickel", true);
//
//        worldGenOil = SERVER_CONFIG
//                .comment("Set to FALSE to prevent 'Thermal Series' Oil Sands from generating.")
//                .define("Oil", true);
//
//        SERVER_CONFIG.pop();
//    }
//
//    private static void refreshServerConfig() {
//
//        refreshWorldConfig();
//    }
//
//    private static void refreshWorldConfig() {
//
//        setFlag(FLAG_GEN_APATITE, () -> getFlag(FLAG_RESOURCE_APATITE).getAsBoolean() && worldGenApatite.get());
//        setFlag(FLAG_GEN_CINNABAR, () -> getFlag(FLAG_RESOURCE_CINNABAR).getAsBoolean() && worldGenCinnabar.get());
//        setFlag(FLAG_GEN_NITER, () -> getFlag(FLAG_RESOURCE_NITER).getAsBoolean() && worldGenNiter.get());
//        setFlag(FLAG_GEN_SULFUR, () -> getFlag(FLAG_RESOURCE_SULFUR).getAsBoolean() && worldGenSulfur.get());
//
//        setFlag(FLAG_GEN_TIN, () -> getFlag(FLAG_RESOURCE_TIN).getAsBoolean() && worldGenTin.get());
//        setFlag(FLAG_GEN_LEAD, () -> getFlag(FLAG_RESOURCE_LEAD).getAsBoolean() && worldGenLead.get());
//        setFlag(FLAG_GEN_SILVER, () -> getFlag(FLAG_RESOURCE_SILVER).getAsBoolean() && worldGenSilver.get());
//        setFlag(FLAG_GEN_NICKEL, () -> getFlag(FLAG_RESOURCE_NICKEL).getAsBoolean() && worldGenNickel.get());
//
//        setFlag(FLAG_GEN_OIL, () -> getFlag(FLAG_RESOURCE_OIL).getAsBoolean() && worldGenOil.get());
//    }
//    // endregion
//
//    // endregion
//
//    // region SERVER VARIABLES
//    public static BooleanValue keepEnergy;
//    public static BooleanValue keepItems;
//    public static BooleanValue keepFluids;
//
//    public static BooleanValue keepAugments;
//    public static BooleanValue keepRSControl;
//    public static BooleanValue keepSideConfig;
//    public static BooleanValue keepTransferControl;
//
//    private static BooleanValue worldGenApatite;
//    private static BooleanValue worldGenCinnabar;
//    private static BooleanValue worldGenNiter;
//    private static BooleanValue worldGenSulfur;
//
//    private static BooleanValue worldGenTin;
//    private static BooleanValue worldGenLead;
//    private static BooleanValue worldGenSilver;
//    private static BooleanValue worldGenNickel;
//
//    private static BooleanValue worldGenOil;
//    // endregion
//}
