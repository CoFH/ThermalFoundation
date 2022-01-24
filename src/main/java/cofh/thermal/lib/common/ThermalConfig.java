package cofh.thermal.lib.common;

import cofh.thermal.core.item.SatchelItem;
import cofh.thermal.core.tileentity.device.DeviceFisherTile;
import cofh.thermal.core.tileentity.device.DeviceTreeExtractorTile;
import cofh.thermal.core.util.managers.dynamo.*;
import cofh.thermal.core.util.managers.machine.*;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

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

        standaloneRedstoneFlux = SERVER_CONFIG
                .comment("If TRUE, Redstone Flux will act as its own energy system and will NOT be interoperable with 'Forge Energy' - only enable this if you absolutely know what you are doing and want the Thermal Series to use a unique energy system.")
                .define("Standalone Redstone Flux", false);

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

        SERVER_CONFIG.push("Tools");

        flagWrench = SERVER_CONFIG
                .comment("If TRUE, the Crescent Hammer is enabled.")
                .define("Wrench", true);
        flagRedprint = SERVER_CONFIG
                .comment("If TRUE, the Redprint is enabled.")
                .define("Redprint", true);
        flagRFPotato = SERVER_CONFIG
                .comment("If TRUE, the Capacitato is enabled.")
                .define("RF Potato", true);
        flagXPCrystal = SERVER_CONFIG
                .comment("If TRUE, the Insightful Crystal is enabled.")
                .define("XP Crystal", true);
        flagLock = SERVER_CONFIG
                .comment("If TRUE, the Signalum Security Lock is enabled.")
                .define("Lock", true);
        flagDetonator = SERVER_CONFIG
                .comment("If TRUE, the Remote Detonator is enabled.")
                .define("Detonator", true);

        SERVER_CONFIG.push("Satchel");

        flagSatchel = SERVER_CONFIG
                .comment("If TRUE, the Satchel is enabled.")
                .define("Satchel", true);

        String[] shulkerBoxes = new String[]{
                ID_THERMAL + ":" + ID_SATCHEL,
                Items.SHULKER_BOX.getRegistryName().toString(),
                Items.WHITE_SHULKER_BOX.getRegistryName().toString(),
                Items.ORANGE_SHULKER_BOX.getRegistryName().toString(),
                Items.MAGENTA_SHULKER_BOX.getRegistryName().toString(),
                Items.LIGHT_BLUE_SHULKER_BOX.getRegistryName().toString(),
                Items.YELLOW_SHULKER_BOX.getRegistryName().toString(),
                Items.LIME_SHULKER_BOX.getRegistryName().toString(),
                Items.PINK_SHULKER_BOX.getRegistryName().toString(),
                Items.GRAY_SHULKER_BOX.getRegistryName().toString(),
                Items.LIGHT_GRAY_SHULKER_BOX.getRegistryName().toString(),
                Items.CYAN_SHULKER_BOX.getRegistryName().toString(),
                Items.PURPLE_SHULKER_BOX.getRegistryName().toString(),
                Items.BLUE_SHULKER_BOX.getRegistryName().toString(),
                Items.BROWN_SHULKER_BOX.getRegistryName().toString(),
                Items.GREEN_SHULKER_BOX.getRegistryName().toString(),
                Items.RED_SHULKER_BOX.getRegistryName().toString(),
                Items.BLACK_SHULKER_BOX.getRegistryName().toString()
        };

        satchelBans = SERVER_CONFIG
                .comment("A list of Items by Resource Location which are NOT allowed in Satchels.")
                .define("Denylist", new ArrayList<>(Arrays.asList(shulkerBoxes)));

        SERVER_CONFIG.pop(2);

        SERVER_CONFIG.push("Mobs");

        flagMobBasalz = SERVER_CONFIG
                .comment("If TRUE, the Basalz Mob is enabled.")
                .define("Basalz", true);
        flagMobBlitz = SERVER_CONFIG
                .comment("If TRUE, the Blitz Mob is enabled.")
                .define("Blitz", true);
        flagMobBlizz = SERVER_CONFIG
                .comment("If TRUE, the Blizz Mob is enabled.")
                .define("Blizz", true);

        flagMobBlitzLightning = SERVER_CONFIG
                .comment("If TRUE, the Blitz can occasionally call down lightning bolts.")
                .define("Blitz Lightning", true);

        SERVER_CONFIG.pop(2);

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

        enableWandererTrades = SERVER_CONFIG
                .comment("If TRUE, trades will be added to the Wandering Trader.")
                .define("Enable Wandering Trader Trades", true);

        SERVER_CONFIG.pop();

        genDeviceConfig();
        genDynamoConfig();
        genMachineConfig();
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
            SERVER_CONFIG.push("TreeExtractor");

            deviceTreeExtractorTimeConstant = SERVER_CONFIG
                    .comment("This sets the base time constant (in ticks) for the Arboreal Extractor.")
                    .defineInRange("Time Constant", 500, 20, 72000);

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DEVICE_FISHER).getAsBoolean()) {
            SERVER_CONFIG.push("Fisher");

            deviceFisherTimeConstant = SERVER_CONFIG
                    .comment("This sets the base time constant (in ticks) for the Aquatic Entangler.")
                    .defineInRange("Time Constant", 4800, 400, 72000);
            deviceFisherTimeReductionWater = SERVER_CONFIG
                    .comment("This sets the time constant reduction (in ticks) per nearby Water source block for the Aquatic Entangler.")
                    .defineInRange("Water Source Time Constant Reduction", 20, 1, 1000);

            SERVER_CONFIG.pop();
        }
        SERVER_CONFIG.pop();
    }

    private static void genDynamoConfig() {

        SERVER_CONFIG.push("Dynamos");

        if (getFlag(ID_DYNAMO_STIRLING).getAsBoolean()) {
            SERVER_CONFIG.push("Stirling");

            dynamoStirlingPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Stirling Dynamo.")
                    .defineInRange("Base Power", StirlingFuelManager.instance().getBasePower(), StirlingFuelManager.instance().getMinPower(), StirlingFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DYNAMO_COMPRESSION).getAsBoolean()) {
            SERVER_CONFIG.push("Compression");

            dynamoCompressionPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Compression Dynamo.")
                    .defineInRange("Base Power", CompressionFuelManager.instance().getBasePower(), CompressionFuelManager.instance().getMinPower(), CompressionFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DYNAMO_MAGMATIC).getAsBoolean()) {
            SERVER_CONFIG.push("Magmatic");

            dynamoMagmaticPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Magmatic Dynamo.")
                    .defineInRange("Base Power", MagmaticFuelManager.instance().getBasePower(), MagmaticFuelManager.instance().getMinPower(), MagmaticFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DYNAMO_NUMISMATIC).getAsBoolean()) {
            SERVER_CONFIG.push("Numismatic");

            dynamoNumismaticPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Numismatic Dynamo.")
                    .defineInRange("Base Power", NumismaticFuelManager.instance().getBasePower(), NumismaticFuelManager.instance().getMinPower(), NumismaticFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DYNAMO_LAPIDARY).getAsBoolean()) {
            SERVER_CONFIG.push("Lapidary");

            dynamoLapidaryPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Lapidary Dynamo.")
                    .defineInRange("Base Power", LapidaryFuelManager.instance().getBasePower(), LapidaryFuelManager.instance().getMinPower(), LapidaryFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DYNAMO_DISENCHANTMENT).getAsBoolean()) {
            SERVER_CONFIG.push("Disenchantment");

            dynamoDisenchantmentPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Disenchantment Dynamo.")
                    .defineInRange("Base Power", DisenchantmentFuelManager.instance().getBasePower(), DisenchantmentFuelManager.instance().getMinPower(), DisenchantmentFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_DYNAMO_GOURMAND).getAsBoolean()) {
            SERVER_CONFIG.push("Gourmand");

            dynamoGourmandPower = SERVER_CONFIG
                    .comment("This sets the base power generation (RF/t) for the Gourmand Dynamo.")
                    .defineInRange("Base Power", GourmandFuelManager.instance().getBasePower(), GourmandFuelManager.instance().getMinPower(), GourmandFuelManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        SERVER_CONFIG.pop();
    }

    private static void genMachineConfig() {

        SERVER_CONFIG.push("Machines");

        if (getFlag(ID_MACHINE_FURNACE).getAsBoolean()) {
            SERVER_CONFIG.push("Furnace");

            machineFurnacePower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Redstone Furnace.")
                    .defineInRange("Base Power", FurnaceRecipeManager.instance().getBasePower(), FurnaceRecipeManager.instance().getMinPower(), FurnaceRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_SAWMILL).getAsBoolean()) {
            SERVER_CONFIG.push("Sawmill");

            machineSawmillPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Sawmill.")
                    .defineInRange("Base Power", SawmillRecipeManager.instance().getBasePower(), SawmillRecipeManager.instance().getMinPower(), SawmillRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_PULVERIZER).getAsBoolean()) {
            SERVER_CONFIG.push("Pulverizer");

            machinePulverizerPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Pulverizer.")
                    .defineInRange("Base Power", PulverizerRecipeManager.instance().getBasePower(), PulverizerRecipeManager.instance().getMinPower(), PulverizerRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_SMELTER).getAsBoolean()) {
            SERVER_CONFIG.push("Smelter");

            machineSmelterPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Induction Smelter.")
                    .defineInRange("Base Power", SmelterRecipeManager.instance().getBasePower(), SmelterRecipeManager.instance().getMinPower(), SmelterRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_INSOLATOR).getAsBoolean()) {
            SERVER_CONFIG.push("Insolator");

            machineInsolatorPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Phytogenic Insolator.")
                    .defineInRange("Base Power", InsolatorRecipeManager.instance().getBasePower(), InsolatorRecipeManager.instance().getMinPower(), InsolatorRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_CENTRIFUGE).getAsBoolean()) {
            SERVER_CONFIG.push("Centrifuge");

            machineCentrifugePower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Centrifugal Separator.")
                    .defineInRange("Base Power", CentrifugeRecipeManager.instance().getBasePower(), CentrifugeRecipeManager.instance().getMinPower(), CentrifugeRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_PRESS).getAsBoolean()) {
            SERVER_CONFIG.push("Press");

            machinePressPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Multiservo Press.")
                    .defineInRange("Base Power", PressRecipeManager.instance().getBasePower(), PressRecipeManager.instance().getMinPower(), PressRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_CRUCIBLE).getAsBoolean()) {
            SERVER_CONFIG.push("Crucible");

            machineCruciblePower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Magma Crucible.")
                    .defineInRange("Base Power", CrucibleRecipeManager.instance().getBasePower(), CrucibleRecipeManager.instance().getMinPower(), CrucibleRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_CHILLER).getAsBoolean()) {
            SERVER_CONFIG.push("Chiller");

            machineChillerPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Blast Chiller.")
                    .defineInRange("Base Power", ChillerRecipeManager.instance().getBasePower(), ChillerRecipeManager.instance().getMinPower(), ChillerRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_FURNACE).getAsBoolean()) {
            SERVER_CONFIG.push("Refinery");

            machineRefineryPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Fractionating Still.")
                    .defineInRange("Base Power", RefineryRecipeManager.instance().getBasePower(), RefineryRecipeManager.instance().getMinPower(), RefineryRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_PYROLYZER).getAsBoolean()) {
            SERVER_CONFIG.push("Pyrolyzer");

            machinePyrolyzerPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Pyrolyzer.")
                    .defineInRange("Base Power", PyrolyzerRecipeManager.instance().getBasePower(), PyrolyzerRecipeManager.instance().getMinPower(), PyrolyzerRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_BOTTLER).getAsBoolean()) {
            SERVER_CONFIG.push("Bottler");

            machineBottlerPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Fluid Encapsulator.")
                    .defineInRange("Base Power", BottlerRecipeManager.instance().getBasePower(), BottlerRecipeManager.instance().getMinPower(), BottlerRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_BREWER).getAsBoolean()) {
            SERVER_CONFIG.push("Brewer");

            machineBrewerPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Alchemical Imbuer.")
                    .defineInRange("Base Power", BrewerRecipeManager.instance().getBasePower(), BrewerRecipeManager.instance().getMinPower(), BrewerRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        if (getFlag(ID_MACHINE_CRAFTER).getAsBoolean()) {
            SERVER_CONFIG.push("Crafter");

            machineCrafterPower = SERVER_CONFIG
                    .comment("This sets the base power consumption (RF/t) for the Sequential Fabricator.")
                    .defineInRange("Base Power", CrafterRecipeManager.instance().getBasePower(), CrafterRecipeManager.instance().getMinPower(), CrafterRecipeManager.instance().getMaxPower());

            SERVER_CONFIG.pop();
        }
        SERVER_CONFIG.pop();
    }

    private static void refreshServerConfig() {

        ThermalEnergyHelper.standaloneRedstoneFlux = standaloneRedstoneFlux.get();

        setFlag(FLAG_VANILLA_BLOCKS, flagVanillaBlocks.get());
        setFlag(FLAG_ROCKWOOL, flagRockwool.get());

        setFlag(ID_WRENCH, flagWrench.get());
        setFlag(ID_REDPRINT, flagRedprint.get());
        setFlag(ID_RF_POTATO, flagRFPotato.get());
        setFlag(ID_XP_CRYSTAL, flagXPCrystal.get());
        setFlag(ID_LOCK, flagLock.get());
        setFlag(ID_SATCHEL, flagSatchel.get());
        setFlag(ID_DETONATOR, flagDetonator.get());

        setFlag(FLAG_MOB_BASALZ, flagMobBasalz.get());
        setFlag(FLAG_MOB_BLITZ, flagMobBlitz.get());
        setFlag(FLAG_MOB_BLIZZ, flagMobBlizz.get());

        setFlag(FLAG_SIDE_CONFIG_AUGMENT, !flagReconfigSides.get());
        setFlag(FLAG_RS_CONTROL_AUGMENT, !flagRSControl.get());
        setFlag(FLAG_XP_STORAGE_AUGMENT, !flagXPStorage.get());

        SatchelItem.setBannedItems(satchelBans.get());

        mobBlitzLightning = flagMobBlitzLightning.get();

        refreshDeviceConfig();
        refreshDynamoConfig();
        refreshMachineConfig();
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

    private static void refreshDynamoConfig() {

        if (dynamoStirlingPower != null) {
            StirlingFuelManager.instance().setBasePower(dynamoStirlingPower.get());
        }
        if (dynamoCompressionPower != null) {
            CompressionFuelManager.instance().setBasePower(dynamoCompressionPower.get());
        }
        if (dynamoMagmaticPower != null) {
            MagmaticFuelManager.instance().setBasePower(dynamoMagmaticPower.get());
        }
        if (dynamoNumismaticPower != null) {
            NumismaticFuelManager.instance().setBasePower(dynamoNumismaticPower.get());
        }
        if (dynamoLapidaryPower != null) {
            LapidaryFuelManager.instance().setBasePower(dynamoLapidaryPower.get());
        }
        if (dynamoDisenchantmentPower != null) {
            DisenchantmentFuelManager.instance().setBasePower(dynamoDisenchantmentPower.get());
        }
        if (dynamoGourmandPower != null) {
            GourmandFuelManager.instance().setBasePower(dynamoGourmandPower.get());
        }
    }

    private static void refreshMachineConfig() {

        if (machineFurnacePower != null) {
            FurnaceRecipeManager.instance().setBasePower(machineFurnacePower.get());
        }
        if (machineSawmillPower != null) {
            SawmillRecipeManager.instance().setBasePower(machineSawmillPower.get());
        }
        if (machinePulverizerPower != null) {
            PulverizerRecipeManager.instance().setBasePower(machinePulverizerPower.get());
        }
        if (machineSmelterPower != null) {
            SmelterRecipeManager.instance().setBasePower(machineSmelterPower.get());
        }
        if (machineInsolatorPower != null) {
            InsolatorRecipeManager.instance().setBasePower(machineInsolatorPower.get());
        }
        if (machineCentrifugePower != null) {
            CentrifugeRecipeManager.instance().setBasePower(machineCentrifugePower.get());
        }
        if (machinePressPower != null) {
            PressRecipeManager.instance().setBasePower(machinePressPower.get());
        }
        if (machineCruciblePower != null) {
            CrucibleRecipeManager.instance().setBasePower(machineCruciblePower.get());
        }
        if (machineChillerPower != null) {
            ChillerRecipeManager.instance().setBasePower(machineChillerPower.get());
        }
        if (machineRefineryPower != null) {
            RefineryRecipeManager.instance().setBasePower(machineRefineryPower.get());
        }
        if (machinePyrolyzerPower != null) {
            PyrolyzerRecipeManager.instance().setBasePower(machinePyrolyzerPower.get());
        }
        if (machineBottlerPower != null) {
            BottlerRecipeManager.instance().setBasePower(machineBottlerPower.get());
        }
        if (machineBrewerPower != null) {
            BrewerRecipeManager.instance().setBasePower(machineBrewerPower.get());
        }
        if (machineCrafterPower != null) {
            CrafterRecipeManager.instance().setBasePower(machineCrafterPower.get());
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
    // endregion

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

    public static BooleanValue standaloneRedstoneFlux;

    public static BooleanValue enableVillagerTrades;
    public static BooleanValue enableWandererTrades;

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

    private static BooleanValue flagWrench;
    private static BooleanValue flagRedprint;
    private static BooleanValue flagRFPotato;
    private static BooleanValue flagXPCrystal;
    private static BooleanValue flagLock;
    private static BooleanValue flagSatchel;
    private static BooleanValue flagDetonator;

    private static BooleanValue flagMobBasalz;
    private static BooleanValue flagMobBlitz;
    private static BooleanValue flagMobBlizz;

    private static BooleanValue flagMobBlitzLightning;

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

    private static IntValue dynamoStirlingPower;
    private static IntValue dynamoCompressionPower;
    private static IntValue dynamoMagmaticPower;
    private static IntValue dynamoNumismaticPower;
    private static IntValue dynamoLapidaryPower;
    private static IntValue dynamoDisenchantmentPower;
    private static IntValue dynamoGourmandPower;

    private static IntValue machineFurnacePower;
    private static IntValue machineSawmillPower;
    private static IntValue machinePulverizerPower;
    private static IntValue machineSmelterPower;
    private static IntValue machineInsolatorPower;
    private static IntValue machineCentrifugePower;
    private static IntValue machinePressPower;
    private static IntValue machineCruciblePower;
    private static IntValue machineChillerPower;
    private static IntValue machineRefineryPower;
    private static IntValue machinePyrolyzerPower;
    private static IntValue machineBottlerPower;
    private static IntValue machineBrewerPower;
    private static IntValue machineCrafterPower;

    public static ForgeConfigSpec.ConfigValue<List<String>> satchelBans;

    public static boolean mobBlitzLightning;
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
