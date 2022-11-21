package cofh.thermal.core.config;

import cofh.core.config.IBaseConfig;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.item.SatchelItem;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static cofh.lib.util.Constants.FALSE;
import static cofh.lib.util.Constants.TRUE;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.ID_SATCHEL;

public class ThermalCoreConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("Global Options");

        boolStandaloneRedstoneFlux = builder
                .comment("If TRUE, Redstone Flux will act as its own energy system and will NOT be interoperable with 'Forge Energy' - only enable this if you absolutely know what you are doing and want the Thermal Series to use a unique energy system.")
                .define("Standalone Redstone Flux", boolStandaloneRedstoneFlux);

        keepEnergy = builder
                .comment("If TRUE, most Thermal Blocks will retain Energy when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Energy", keepEnergy);
        keepItems = builder
                .comment("If TRUE, most Thermal Blocks will retain Inventory Contents when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Inventory", keepItems);
        keepFluids = builder
                .comment("If TRUE, most Thermal Blocks will retain Tank Contents when dropped.\nThis setting does not control ALL blocks.")
                .define("Blocks Retain Tank Contents", keepFluids);
        keepAugments = builder
                .comment("If TRUE, Thermal Blocks will retain Augments when dropped.")
                .define("Blocks Retain Augments", keepAugments);
        keepRSControl = builder
                .comment("If TRUE, Thermal Blocks will retain Redstone Control configuration when dropped.")
                .define("Blocks Retain Redstone Control", keepRSControl);
        keepSideConfig = builder
                .comment("If TRUE, Thermal Blocks will retain Side configuration when dropped.")
                .define("Blocks Retain Side Configuration", keepSideConfig);
        keepTransferControl = builder
                .comment("If TRUE, Thermal Blocks will retain Transfer Control configuration when dropped.")
                .define("Blocks Retain Transfer Control", keepTransferControl);

        builder.pop();

        builder.push("Tools");

        builder.push("Satchel");

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
                .define("Basalz", boolMobBasalz);
        boolMobBlitz = builder
                .comment("If TRUE, the Blitz Mob is enabled.")
                .define("Blitz", boolMobBlitz);
        boolMobBlizz = builder
                .comment("If TRUE, the Blizz Mob is enabled.")
                .define("Blizz", boolMobBlizz);

        mobBlitzLightning = builder
                .comment("If TRUE, the Blitz can occasionally call down lightning bolts.")
                .define("Blitz Lightning", mobBlitzLightning);

        builder.pop();

        builder.push("Augments");

        defaultReconfigSides = builder
                .comment("If TRUE, Side Reconfiguration is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default Side Reconfiguration", defaultReconfigSides);
        defaultRSControl = builder
                .comment("If TRUE, Redstone Control is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default Redstone Control", defaultRSControl);
        defaultXPStorage = builder
                .comment("If TRUE, XP Storage is enabled by default on most augmentable blocks which support it.\nIf FALSE, an augment is required.\nThis setting does not control ALL blocks.")
                .define("Default XP Storage", defaultXPStorage);

        builder.pop();

        builder.push("Villagers");

        enableVillagerTrades = builder
                .comment("If TRUE, trades will be added to various Villagers.")
                .define("Enable Villager Trades", enableVillagerTrades);

        enableWandererTrades = builder
                .comment("If TRUE, trades will be added to the Wandering Trader.")
                .define("Enable Wandering Trader Trades", enableWandererTrades);

        builder.pop();
    }

    @Override
    public void refresh() {

        ThermalEnergyHelper.standaloneRedstoneFlux = boolStandaloneRedstoneFlux.get();

        setFlag(FLAG_MOB_BASALZ, boolMobBasalz.get());
        setFlag(FLAG_MOB_BLITZ, boolMobBlitz.get());
        setFlag(FLAG_MOB_BLIZZ, boolMobBlizz.get());

        setFlag(FLAG_SIDE_CONFIG_AUGMENT, !defaultReconfigSides.get());
        setFlag(FLAG_RS_CONTROL_AUGMENT, !defaultRSControl.get());
        setFlag(FLAG_XP_STORAGE_AUGMENT, !defaultXPStorage.get());

        SatchelItem.setBannedItems(listSatchelBans.get());
    }

    // region VARIABLES
    public static int deviceAugments = 3;
    public static int dynamoAugments = 4;
    public static int machineAugments = 4;
    public static int storageAugments = 3;
    public static int toolAugments = 4;
    // endregion

    // region CONFIG VARIABLES
    public static Supplier<Boolean> keepEnergy = TRUE;
    public static Supplier<Boolean> keepItems = FALSE;
    public static Supplier<Boolean> keepFluids = FALSE;
    public static Supplier<Boolean> keepAugments = TRUE;
    public static Supplier<Boolean> keepRSControl = TRUE;
    public static Supplier<Boolean> keepSideConfig = TRUE;
    public static Supplier<Boolean> keepTransferControl = TRUE;

    public static Supplier<Boolean> defaultReconfigSides = TRUE;
    public static Supplier<Boolean> defaultRSControl = TRUE;
    public static Supplier<Boolean> defaultXPStorage = FALSE;

    public static Supplier<Boolean> permanentLava = FALSE;
    public static Supplier<Boolean> permanentWater = FALSE;

    public static Supplier<Boolean> enableVillagerTrades = TRUE;
    public static Supplier<Boolean> enableWandererTrades = TRUE;

    public static Supplier<Boolean> mobBlitzLightning = TRUE;

    private Supplier<Boolean> boolMobBasalz = TRUE;
    private Supplier<Boolean> boolMobBlitz = TRUE;
    private Supplier<Boolean> boolMobBlizz = TRUE;

    private Supplier<Boolean> boolStandaloneRedstoneFlux = FALSE;

    private Supplier<List<String>> listSatchelBans = Collections::emptyList;
    // endregion
}
