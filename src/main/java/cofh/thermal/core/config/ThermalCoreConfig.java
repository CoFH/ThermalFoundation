package cofh.thermal.core.config;

import cofh.core.config.IBaseConfig;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.item.SatchelItem;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class ThermalCoreConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("Global Options");

        boolStandaloneRedstoneFlux = builder
                .comment("If TRUE, Redstone Flux will act as its own energy system and will NOT be interoperable with 'Forge Energy' - only enable this if you absolutely know what you are doing and want the Thermal Series to use a unique energy system.")
                .define("Standalone Redstone Flux", false);

        boolKeepEnergy = builder
                .comment("If TRUE, most Thermal Blocks will retain Energy when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Energy", true);
        boolKeepItems = builder
                .comment("If TRUE, most Thermal Blocks will retain Inventory Contents when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Inventory", false);
        boolKeepFluids = builder
                .comment("If TRUE, most Thermal Blocks will retain Tank Contents when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Tank Contents", false);
        boolKeepAugments = builder
                .comment("If TRUE, Thermal Blocks will retain Augments when dropped.")
                .define("Blocks Retain Augments", true);
        boolKeepRSControl = builder
                .comment("If TRUE, Thermal Blocks will retain Redstone Control configuration when dropped.")
                .define("Blocks Retain Redstone Control", true);
        boolKeepSideConfig = builder
                .comment("If TRUE, Thermal Blocks will retain Side configuration when dropped.")
                .define("Blocks Retain Side Configuration", true);
        boolKeepTransferControl = builder
                .comment("If TRUE, Thermal Blocks will retain Transfer Control configuration when dropped.")
                .define("Blocks Retain Transfer Control", true);

        builder.pop();

        builder.push("Features");

        boolVanillaBlocks = builder
                .comment("If TRUE, various 'Vanilla+' Blocks and Recipes are enabled.")
                .define("Vanilla+", true);
        boolRockwool = builder
                .comment("If TRUE, Rockwool Blocks and Recipes are enabled.")
                .define("Rockwool", true);

        builder.push("Tools");

        boolWrench = builder
                .comment("If TRUE, the Crescent Hammer is enabled.")
                .define("Wrench", true);
        boolRedprint = builder
                .comment("If TRUE, the Redprint is enabled.")
                .define("Redprint", true);
        boolRFPotato = builder
                .comment("If TRUE, the Capacitato is enabled.")
                .define("RF Potato", true);
        boolXPCrystal = builder
                .comment("If TRUE, the Insightful Crystal is enabled.")
                .define("XP Crystal", true);
        boolLock = builder
                .comment("If TRUE, the Signalum Security Lock is enabled.")
                .define("Lock", true);
        boolDetonator = builder
                .comment("If TRUE, the Remote Detonator is enabled.")
                .define("Detonator", true);

        builder.push("Satchel");

        boolSatchel = builder
                .comment("If TRUE, the Satchel is enabled.")
                .define("Satchel", true);

        String[] shulkerBoxes = new String[0];
        try {
            shulkerBoxes = new String[]{
                    ID_THERMAL + ":" + ID_SATCHEL,
                    getRegistryName(Items.SHULKER_BOX).toString(),
                    getRegistryName(Items.WHITE_SHULKER_BOX).toString(),
                    getRegistryName(Items.ORANGE_SHULKER_BOX).toString(),
                    getRegistryName(Items.MAGENTA_SHULKER_BOX).toString(),
                    getRegistryName(Items.LIGHT_BLUE_SHULKER_BOX).toString(),
                    getRegistryName(Items.YELLOW_SHULKER_BOX).toString(),
                    getRegistryName(Items.LIME_SHULKER_BOX).toString(),
                    getRegistryName(Items.PINK_SHULKER_BOX).toString(),
                    getRegistryName(Items.GRAY_SHULKER_BOX).toString(),
                    getRegistryName(Items.LIGHT_GRAY_SHULKER_BOX).toString(),
                    getRegistryName(Items.CYAN_SHULKER_BOX).toString(),
                    getRegistryName(Items.PURPLE_SHULKER_BOX).toString(),
                    getRegistryName(Items.BLUE_SHULKER_BOX).toString(),
                    getRegistryName(Items.BROWN_SHULKER_BOX).toString(),
                    getRegistryName(Items.GREEN_SHULKER_BOX).toString(),
                    getRegistryName(Items.RED_SHULKER_BOX).toString(),
                    getRegistryName(Items.BLACK_SHULKER_BOX).toString()
            };
        } catch (Throwable t) {
            ThermalCore.LOG.error("A Shulker Box was NULL. This is really bad.", t);
        }
        listSatchelBans = builder
                .comment("A list of Items by Resource Location which are NOT allowed in Satchels.")
                .define("Denylist", new ArrayList<>(Arrays.asList(shulkerBoxes)));

        builder.pop(2);

        builder.push("Mobs");

        boolMobBasalz = builder
                .comment("If TRUE, the Basalz Mob is enabled.")
                .define("Basalz", true);
        boolMobBlitz = builder
                .comment("If TRUE, the Blitz Mob is enabled.")
                .define("Blitz", true);
        boolMobBlizz = builder
                .comment("If TRUE, the Blizz Mob is enabled.")
                .define("Blizz", true);

        boolMobBlitzLightning = builder
                .comment("If TRUE, the Blitz can occasionally call down lightning bolts.")
                .define("Blitz Lightning", mobBlitzLightning);

        builder.pop(2);

        builder.push("Augments");

        boolReconfigSides = builder
                .comment("If TRUE, Side Reconfiguration is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default Side Reconfiguration", defaultReconfigSides);
        boolRSControl = builder
                .comment("If TRUE, Redstone Control is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default Redstone Control", defaultRSControl);
        boolXPStorage = builder
                .comment("If TRUE, XP Storage is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default XP Storage", defaultXPStorage);

        builder.pop();

        builder.push("Villagers");

        boolVillagerTrades = builder
                .comment("If TRUE, trades will be added to various Villagers.")
                .define("Enable Villager Trades", true);

        boolWandererTrades = builder
                .comment("If TRUE, trades will be added to the Wandering Trader.")
                .define("Enable Wandering Trader Trades", true);

        builder.pop();
    }

    @Override
    public void refresh() {

        ThermalEnergyHelper.standaloneRedstoneFlux = boolStandaloneRedstoneFlux.get();

        keepEnergy = boolKeepEnergy.get();
        keepItems = boolKeepItems.get();
        keepFluids = boolKeepFluids.get();

        keepAugments = boolKeepAugments.get();
        keepRSControl = boolKeepRSControl.get();
        keepSideConfig = boolKeepSideConfig.get();
        keepTransferControl = boolKeepTransferControl.get();

        defaultReconfigSides = boolReconfigSides.get();
        defaultRSControl = boolRSControl.get();
        defaultXPStorage = boolXPStorage.get();

        enableVillagerTrades = boolVillagerTrades.get();
        enableWandererTrades = boolWandererTrades.get();

        mobBlitzLightning = boolMobBlitzLightning.get();

        setFlag(FLAG_VANILLA_BLOCKS, boolVanillaBlocks.get());
        setFlag(FLAG_ROCKWOOL, boolRockwool.get());

        setFlag(ID_WRENCH, boolWrench.get());
        setFlag(ID_REDPRINT, boolRedprint.get());
        setFlag(ID_RF_POTATO, boolRFPotato.get());
        setFlag(ID_XP_CRYSTAL, boolXPCrystal.get());
        setFlag(ID_LOCK, boolLock.get());
        setFlag(ID_SATCHEL, boolSatchel.get());
        setFlag(ID_DETONATOR, boolDetonator.get());

        setFlag(FLAG_MOB_BASALZ, boolMobBasalz.get());
        setFlag(FLAG_MOB_BLITZ, boolMobBlitz.get());
        setFlag(FLAG_MOB_BLIZZ, boolMobBlizz.get());

        setFlag(FLAG_SIDE_CONFIG_AUGMENT, !boolReconfigSides.get());
        setFlag(FLAG_RS_CONTROL_AUGMENT, !boolRSControl.get());
        setFlag(FLAG_XP_STORAGE_AUGMENT, !boolXPStorage.get());

        SatchelItem.setBannedItems(listSatchelBans.get());
    }

    // region VARIABLES
    public static int deviceAugments = 3;
    public static int dynamoAugments = 4;
    public static int machineAugments = 4;
    public static int storageAugments = 3;

    public static int toolAugments = 4;

    public static boolean keepEnergy;
    public static boolean keepItems;
    public static boolean keepFluids;

    public static boolean keepAugments;
    public static boolean keepRSControl;
    public static boolean keepSideConfig;
    public static boolean keepTransferControl;

    public static boolean defaultReconfigSides = true;
    public static boolean defaultRSControl = true;
    public static boolean defaultXPStorage = false;

    public static boolean permanentLava = true;
    public static boolean permanentWater = true;

    public static boolean enableVillagerTrades = true;
    public static boolean enableWandererTrades = true;

    public static boolean mobBlitzLightning = true;
    // endregion

    // region CONFIG VARIABLES
    private ForgeConfigSpec.BooleanValue boolStandaloneRedstoneFlux;

    private ForgeConfigSpec.BooleanValue boolKeepEnergy;
    private ForgeConfigSpec.BooleanValue boolKeepItems;
    private ForgeConfigSpec.BooleanValue boolKeepFluids;
    private ForgeConfigSpec.BooleanValue boolKeepAugments;
    private ForgeConfigSpec.BooleanValue boolKeepRSControl;
    private ForgeConfigSpec.BooleanValue boolKeepSideConfig;
    private ForgeConfigSpec.BooleanValue boolKeepTransferControl;

    private ForgeConfigSpec.BooleanValue boolReconfigSides;
    private ForgeConfigSpec.BooleanValue boolRSControl;
    private ForgeConfigSpec.BooleanValue boolXPStorage;

    private ForgeConfigSpec.BooleanValue boolPermanentLava;
    private ForgeConfigSpec.BooleanValue boolPermanentWater;

    private ForgeConfigSpec.BooleanValue boolVillagerTrades;
    private ForgeConfigSpec.BooleanValue boolWandererTrades;

    private ForgeConfigSpec.BooleanValue boolVanillaBlocks;
    private ForgeConfigSpec.BooleanValue boolRockwool;
    private ForgeConfigSpec.BooleanValue boolWrench;
    private ForgeConfigSpec.BooleanValue boolRedprint;
    private ForgeConfigSpec.BooleanValue boolRFPotato;
    private ForgeConfigSpec.BooleanValue boolXPCrystal;
    private ForgeConfigSpec.BooleanValue boolLock;
    private ForgeConfigSpec.BooleanValue boolSatchel;
    private ForgeConfigSpec.BooleanValue boolDetonator;

    private ForgeConfigSpec.BooleanValue boolMobBasalz;
    private ForgeConfigSpec.BooleanValue boolMobBlitz;
    private ForgeConfigSpec.BooleanValue boolMobBlizz;

    private ForgeConfigSpec.BooleanValue boolMobBlitzLightning;

    private ForgeConfigSpec.ConfigValue<List<String>> listSatchelBans;
    // endregion
}
