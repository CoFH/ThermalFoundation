package cofh.thermal.lib.common;

import cofh.thermal.core.tileentity.device.DeviceFisherTile;
import cofh.thermal.core.tileentity.device.DeviceTreeExtractorTile;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cofh.thermal.core.init.TCoreIDs.ID_DEVICE_FISHER;
import static cofh.thermal.core.init.TCoreIDs.ID_DEVICE_TREE_EXTRACTOR;
import static cofh.thermal.lib.common.ThermalFlags.*;

public class ThermalConfig {

    private static boolean registered = false;

    public static void register() {

        if (registered) {
            return;
        }
        FMLJavaModLoadingContext.get().getModEventBus().register(ThermalConfig.class);
        registered = true;

        genClientConfig();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }

    public static void setup() {

        // This allows flags to be set before the server configuration is generated.
        genServerConfig();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
    }

    private ThermalConfig() {

    }

    // region CONFIG SPEC
    private static final ForgeConfigSpec.Builder SERVER_CONFIG = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec serverSpec;

    private static final ForgeConfigSpec.Builder CLIENT_CONFIG = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec clientSpec;

    private static void genServerConfig() {

        SERVER_CONFIG.push("Global Options");

        keepEnergy = SERVER_CONFIG
                .comment("If TRUE, most Thermal Blocks will retain Energy when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Energy", true);
        keepItems = SERVER_CONFIG
                .comment("If TRUE, most Thermal Blocks will retain Inventory Contents when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Inventory", false);
        keepFluids = SERVER_CONFIG
                .comment("If TRUE, most Thermal Blocks will retain Tank Contents when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Tank Contents", false);
        keepAugments = SERVER_CONFIG
                .comment("If TRUE, Thermal Blocks will retain Augments when dropped.")
                .define("Blocks Retain Augments", true);
        keepRSControl = SERVER_CONFIG
                .comment("If TRUE, Thermal Blocks will retain Redstone Control configuration when dropped.")
                .define("Blocks Retain Redstone Control", true);
        keepSideConfig = SERVER_CONFIG
                .comment("If TRUE, Thermal Blocks will retain Side configuration when dropped.")
                .define("Blocks Retain Side Configuration", true);
        keepTransferControl = SERVER_CONFIG
                .comment("If TRUE, Thermal Blocks will retain Transfer Control configuration when dropped.")
                .define("Blocks Retain Transfer Control", true);

        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Features");

        flagVanillaBlocks = SERVER_CONFIG
                .comment("If TRUE, various 'Vanilla+' Blocks and Recipes are enabled.")
                .define("Vanilla+", true);
        flagRockwool = SERVER_CONFIG
                .comment("If TRUE, Rockwool Blocks and Recipes are enabled.")
                .define("Rockwool", true);

        flagMobBasalz = SERVER_CONFIG
                .comment("If TRUE, the Basalz Mob is enabled.")
                .define("Basalz", true);
        flagMobBlitz = SERVER_CONFIG
                .comment("If TRUE, the Blitz Mob is enabled.")
                .define("Blitz", true);
        flagMobBlizz = SERVER_CONFIG
                .comment("If TRUE, the Blizz Mob is enabled.")
                .define("Blizz", true);

        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Augments");

        flagReconfigSides = SERVER_CONFIG
                .comment("If TRUE, Side Reconfiguration is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default Side Reconfiguration", true);
        flagRSControl = SERVER_CONFIG
                .comment("If TRUE, Redstone Control is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default Redstone Control", true);
        flagXPStorage = SERVER_CONFIG
                .comment("If TRUE, XP Storage is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default XP Storage", false);

        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Villagers");

        enableVillagerTrades = SERVER_CONFIG
                .comment("If TRUE, trades will be added to various Villagers.")
                .define("Enable Villager Trades", true);

        SERVER_CONFIG.pop();

        genDeviceConfig();
        genWorldConfig();

        serverSpec = SERVER_CONFIG.build();

        refreshServerConfig();
    }

    private static void genClientConfig() {

        CLIENT_CONFIG.push("Sounds");

        flagBlockAmbientSounds = CLIENT_CONFIG
                .comment("If TRUE, some 'Thermal Series' Blocks will have ambient sounds.")
                .define("Ambient Block Sounds", true);

        flagMobAmbientSounds = CLIENT_CONFIG
                .comment("If TRUE, some 'Thermal Series' Mobs will have ambient sounds.")
                .define("Ambient Mob Sounds", true);

        CLIENT_CONFIG.pop();

        clientSpec = CLIENT_CONFIG.build();

        refreshClientConfig();
    }

    private static void genWorldConfig() {

        SERVER_CONFIG.push("World Generation");

        worldGenApatite = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Apatite from generating.")
                .define("Apatite", true);
        worldGenCinnabar = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Cinnabar from generating.")
                .define("Cinnabar", true);
        worldGenNiter = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Niter from generating.")
                .define("Niter", true);
        worldGenSulfur = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Sulfur from generating.")
                .define("Sulfur", true);

        worldGenCopper = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Copper from generating.")
                .define("Copper", true);
        worldGenTin = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Tin from generating.")
                .define("Tin", true);
        worldGenLead = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Lead from generating.")
                .define("Lead", true);
        worldGenSilver = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Silver from generating.")
                .define("Silver", true);
        worldGenNickel = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Nickel from generating.")
                .define("Nickel", true);

        worldGenOil = SERVER_CONFIG
                .comment("Set to FALSE to prevent 'Thermal Series' Oil Sands from generating.")
                .define("Oil", true);

        SERVER_CONFIG.pop();
    }

    private static void genDeviceConfig() {

        SERVER_CONFIG.push("Devices");

        if (getFlag(ID_DEVICE_TREE_EXTRACTOR).getAsBoolean()) {
            deviceTreeExtractorTimeConstant = SERVER_CONFIG
                    .comment("This sets the base time constant (in ticks) for the Arboreal Extractor.")
                    .defineInRange("Time Constant", 500, 20, 72000);
        }
        if (getFlag(ID_DEVICE_FISHER).getAsBoolean()) {
            deviceFisherTimeConstant = SERVER_CONFIG
                    .comment("This sets the base time constant (in ticks) for the Aquatic Entangler.")
                    .defineInRange("Time Constant", 4800, 400, 72000);
            deviceFisherTimeReductionWater = SERVER_CONFIG
                    .comment("This sets the time constant reduction (in ticks) per nearby Water source block for the Aquatic Entangler.")
                    .defineInRange("Water Source Time Constant Reduction", 20, 1, 1000);
        }

        SERVER_CONFIG.pop();
    }

    private static void genMachineConfig() {

    }

    private static void refreshServerConfig() {

        setFlag(FLAG_VANILLA_BLOCKS, flagVanillaBlocks.get());
        setFlag(FLAG_ROCKWOOL, flagRockwool.get());

        setFlag(FLAG_MOB_BASALZ, flagMobBasalz.get());
        setFlag(FLAG_MOB_BLITZ, flagMobBlitz.get());
        setFlag(FLAG_MOB_BLIZZ, flagMobBlizz.get());

        setFlag(FLAG_SIDE_CONFIG_AUGMENT, !flagReconfigSides.get());
        setFlag(FLAG_RS_CONTROL_AUGMENT, !flagRSControl.get());
        setFlag(FLAG_XP_STORAGE_AUGMENT, !flagXPStorage.get());

        refreshDeviceConfig();
        refreshWorldConfig();
    }

    private static void refreshDeviceConfig() {

        if (deviceTreeExtractorTimeConstant != null) {
            DeviceTreeExtractorTile.setTimeConstant(deviceTreeExtractorTimeConstant.get());
        }
        if (deviceFisherTimeConstant != null) {
            DeviceFisherTile.setTimeConstant(deviceFisherTimeConstant.get());
            DeviceFisherTile.setTimeReductionWater(deviceFisherTimeReductionWater.get());
        }
    }

    private static void refreshWorldConfig() {

        setFlag(FLAG_GEN_APATITE, () -> getFlag(FLAG_RESOURCE_APATITE).getAsBoolean() && worldGenApatite.get());
        setFlag(FLAG_GEN_CINNABAR, () -> getFlag(FLAG_RESOURCE_CINNABAR).getAsBoolean() && worldGenCinnabar.get());
        setFlag(FLAG_GEN_NITER, () -> getFlag(FLAG_RESOURCE_NITER).getAsBoolean() && worldGenNiter.get());
        setFlag(FLAG_GEN_SULFUR, () -> getFlag(FLAG_RESOURCE_SULFUR).getAsBoolean() && worldGenSulfur.get());

        setFlag(FLAG_GEN_COPPER, () -> getFlag(FLAG_RESOURCE_COPPER).getAsBoolean() && worldGenCopper.get());
        setFlag(FLAG_GEN_TIN, () -> getFlag(FLAG_RESOURCE_TIN).getAsBoolean() && worldGenTin.get());
        setFlag(FLAG_GEN_LEAD, () -> getFlag(FLAG_RESOURCE_LEAD).getAsBoolean() && worldGenLead.get());
        setFlag(FLAG_GEN_SILVER, () -> getFlag(FLAG_RESOURCE_SILVER).getAsBoolean() && worldGenSilver.get());
        setFlag(FLAG_GEN_NICKEL, () -> getFlag(FLAG_RESOURCE_NICKEL).getAsBoolean() && worldGenNickel.get());

        setFlag(FLAG_GEN_OIL, () -> getFlag(FLAG_RESOURCE_OIL).getAsBoolean() && worldGenOil.get());
    }

    private static void refreshClientConfig() {

        blockAmbientSounds = flagBlockAmbientSounds.get();
        mobAmbientSounds = flagMobAmbientSounds.get();
    }

    // region GLOBALS
    public static final byte[] DEFAULT_MACHINE_SIDES_RAW = new byte[]{0, 0, 0, 0, 0, 0};
    public static final byte[] DEFAULT_CELL_SIDES_RAW = new byte[]{0, 0, 0, 0, 0, 0};
    // endregion

    // region SERVER VARIABLES
    public static int deviceAugments = 3;
    public static int dynamoAugments = 4;
    public static int machineAugments = 4;
    public static int storageAugments = 3;

    public static int toolAugments = 4;

    public static boolean permanentLava = true;
    public static boolean permanentWater = true;

    public static BooleanValue enableVillagerTrades;

    public static BooleanValue keepEnergy;
    public static BooleanValue keepItems;
    public static BooleanValue keepFluids;

    public static BooleanValue keepAugments;
    public static BooleanValue keepRSControl;
    public static BooleanValue keepSideConfig;
    public static BooleanValue keepTransferControl;

    public static BooleanValue flagReconfigSides;
    public static BooleanValue flagRSControl;
    public static BooleanValue flagXPStorage;

    private static BooleanValue flagVanillaBlocks;
    private static BooleanValue flagRockwool;

    private static BooleanValue flagMobBasalz;
    private static BooleanValue flagMobBlitz;
    private static BooleanValue flagMobBlizz;

    private static BooleanValue worldGenApatite;
    private static BooleanValue worldGenCinnabar;
    private static BooleanValue worldGenNiter;
    private static BooleanValue worldGenSulfur;

    private static BooleanValue worldGenCopper;
    private static BooleanValue worldGenTin;
    private static BooleanValue worldGenLead;
    private static BooleanValue worldGenSilver;
    private static BooleanValue worldGenNickel;

    private static BooleanValue worldGenOil;

    private static BooleanValue freezePermanentLava;
    private static BooleanValue freezePermanentWater;

    private static IntValue deviceTreeExtractorTimeConstant;
    private static IntValue deviceFisherTimeConstant;
    private static IntValue deviceFisherTimeReductionWater;
    // endregion

    // region CLIENT VARIABLES
    public static boolean jeiBucketTanks = true;

    public static boolean blockAmbientSounds = true;
    public static boolean mobAmbientSounds = true;

    public static BooleanValue flagBlockAmbientSounds;
    public static BooleanValue flagMobAmbientSounds;
    // endregion

    // region CONFIGURATION
    @SubscribeEvent
    public static void configLoading(final ModConfig.Loading event) {

        switch (event.getConfig().getType()) {
            case CLIENT:
                refreshClientConfig();
                break;
            case SERVER:
                refreshServerConfig();
        }
    }

    @SubscribeEvent
    public static void configReloading(ModConfig.Reloading event) {

        switch (event.getConfig().getType()) {
            case CLIENT:
                refreshClientConfig();
                break;
            case SERVER:
                refreshServerConfig();
        }
    }
    // endregion
}
