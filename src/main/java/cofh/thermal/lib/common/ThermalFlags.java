package cofh.thermal.lib.common;

import cofh.lib.util.flags.FlagManager;

import java.util.function.BooleanSupplier;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class ThermalFlags {

    private ThermalFlags() {

    }

    private static final FlagManager FLAG_MANAGER = new FlagManager(ID_THERMAL);

    public static FlagManager manager() {

        return FLAG_MANAGER;
    }

    public static void setFlag(String flag, boolean enable) {

        FLAG_MANAGER.setFlag(flag, enable);
    }

    public static void setFlag(String flag, BooleanSupplier condition) {

        FLAG_MANAGER.setFlag(flag, condition);
    }

    public static BooleanSupplier getFlag(String flag) {

        return FLAG_MANAGER.getFlag(flag);
    }

    // region SPECIFIC FEATURES
    public static String FLAG_BEEKEEPER_ARMOR = "beekeeper_armor";
    public static String FLAG_DIVING_ARMOR = "diving_armor";
    public static String FLAG_HAZMAT_ARMOR = "hazmat_armor";

    public static String FLAG_AREA_AUGMENTS = "area_augments";
    public static String FLAG_CREATIVE_AUGMENTS = "creative_augments";
    public static String FLAG_DYNAMO_AUGMENTS = "dynamo_augments";
    public static String FLAG_FILTER_AUGMENTS = "filter_augments";
    public static String FLAG_MACHINE_AUGMENTS = "machine_augments";
    public static String FLAG_POTION_AUGMENTS = "potion_augments";
    public static String FLAG_STORAGE_AUGMENTS = "storage_augments";
    public static String FLAG_UPGRADE_AUGMENTS = "upgrade_augments";

    public static String FLAG_CREATIVE_STORAGE_AUGMENTS = "creative_storage_augments";
    public static String FLAG_CREATIVE_MACHINE_AUGMENTS = "creative_machine_augments";

    public static String FLAG_RS_CONTROL_AUGMENT = "rs_control_augment";
    public static String FLAG_SIDE_CONFIG_AUGMENT = "side_config_augment";
    public static String FLAG_XP_STORAGE_AUGMENT = "xp_storage_augment";

    public static String FLAG_TOOL_COMPONENTS = "tool_components";

    public static String FLAG_COINS = "coins";
    public static String FLAG_PLATES = "plates";

    public static String FLAG_BASIC_EXPLOSIVES = "basic_explosives";
    public static String FLAG_PHYTOGRO_EXPLOSIVES = "phytogro_explosives";
    public static String FLAG_ELEMENTAL_EXPLOSIVES = "elemental_explosives";
    public static String FLAG_NUCLEAR_EXPLOSIVES = "nuclear_explosives";

    public static String FLAG_RESOURCE_APATITE = "apatite";
    public static String FLAG_RESOURCE_CINNABAR = "cinnabar";
    public static String FLAG_RESOURCE_NITER = "niter";
    public static String FLAG_RESOURCE_SULFUR = "sulfur";

    public static String FLAG_RESOURCE_TIN = "tin";
    public static String FLAG_RESOURCE_LEAD = "lead";
    public static String FLAG_RESOURCE_SILVER = "silver";
    public static String FLAG_RESOURCE_NICKEL = "nickel";

    public static String FLAG_RESOURCE_RUBY = "ruby";
    public static String FLAG_RESOURCE_SAPPHIRE = "sapphire";

    public static String FLAG_RESOURCE_OIL = "oil";

    public static String FLAG_RESOURCE_BRONZE = "bronze";
    public static String FLAG_RESOURCE_ELECTRUM = "electrum";
    public static String FLAG_RESOURCE_INVAR = "invar";
    public static String FLAG_RESOURCE_CONSTANTAN = "constantan";

    public static String FLAG_RESOURCE_STEEL = "steel";
    public static String FLAG_RESOURCE_ROSE_GOLD = "rose_gold";

    public static String FLAG_MOB_BASALZ = "basalz";
    public static String FLAG_MOB_BLITZ = "blitz";
    public static String FLAG_MOB_BLIZZ = "blizz";
    // endregion

    static {
        setFlag(FLAG_RESOURCE_BRONZE, getFlag(FLAG_RESOURCE_TIN));
        setFlag(FLAG_RESOURCE_ELECTRUM, getFlag(FLAG_RESOURCE_SILVER));
        setFlag(FLAG_RESOURCE_INVAR, getFlag(FLAG_RESOURCE_NICKEL));
        setFlag(FLAG_RESOURCE_CONSTANTAN, getFlag(FLAG_RESOURCE_NICKEL));

        setFlag(FLAG_CREATIVE_STORAGE_AUGMENTS, () -> getFlag(FLAG_STORAGE_AUGMENTS).getAsBoolean() && getFlag(FLAG_CREATIVE_AUGMENTS).getAsBoolean());
        setFlag(FLAG_CREATIVE_MACHINE_AUGMENTS, () -> getFlag(FLAG_MACHINE_AUGMENTS).getAsBoolean() && getFlag(FLAG_CREATIVE_AUGMENTS).getAsBoolean());
    }

}
