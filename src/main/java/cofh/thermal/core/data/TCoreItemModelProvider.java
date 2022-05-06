package cofh.thermal.core.data;

import cofh.lib.data.ItemModelProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.deepslate;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreItemModelProvider extends ItemModelProviderCoFH {

    public static final String AUGMENTS = "augments";

    public TCoreItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {

        super(generator, ID_THERMAL, existingFileHelper);
    }

    @Override
    public String getName() {

        return "Thermal Core: Item Models";
    }

    @Override
    protected void registerModels() {

        registerBlockItemModels();

        DeferredRegisterCoFH<Item> reg = ITEMS;

        registerVanilla(reg);
        registerResources(reg);
        registerParts(reg);
        registerTools(reg);
        registerArmor(reg);
        registerAugments(reg);
    }

    // region ITEM HELPERS
    private void registerVanilla(DeferredRegisterCoFH<Item> reg) {

        metalSet(reg, "copper", true);
        metalSet(reg, "iron", true);
        metalSet(reg, "gold", true);
        metalSet(reg, "netherite", true);

        gemSet(reg, "lapis", true);
        gemSet(reg, "diamond", true);
        gemSet(reg, "emerald", true);
        gemSet(reg, "quartz", true);

        generated(reg.getSup("ender_pearl_dust"));
    }

    private void registerResources(DeferredRegisterCoFH<Item> reg) {

        generated(reg.getSup("sawdust"));
        generated(reg.getSup("coal_coke"));
        generated(reg.getSup("bitumen"));
        generated(reg.getSup("tar"));
        generated(reg.getSup("rosin"));
        generated(reg.getSup("rubber"));
        generated(reg.getSup("cured_rubber"));
        generated(reg.getSup("slag"));
        generated(reg.getSup("rich_slag"));

        generated(reg.getSup("syrup_bottle"));

        generated(reg.getSup("junk_net"));
        generated(reg.getSup("aquachow"));
        generated(reg.getSup("deep_aquachow"));
        //        generated(reg.getSup("rich_aquachow"));
        //        generated(reg.getSup("fluxed_aquachow"));

        generated(reg.getSup("beekeeper_fabric"));
        generated(reg.getSup("diving_fabric"));
        generated(reg.getSup("hazmat_fabric"));

        generated(reg.getSup("apatite_dust"));
        generated(reg.getSup("cinnabar_dust"));
        generated(reg.getSup("niter_dust"));
        generated(reg.getSup("sulfur_dust"));

        generated(reg.getSup("apatite"));
        generated(reg.getSup("cinnabar"));
        generated(reg.getSup("niter"));
        generated(reg.getSup("sulfur"));

        generated(reg.getSup("basalz_rod"));
        generated(reg.getSup("basalz_powder"));
        generated(reg.getSup("blitz_rod"));
        generated(reg.getSup("blitz_powder"));
        generated(reg.getSup("blizz_rod"));
        generated(reg.getSup("blizz_powder"));

        generated(reg.getSup("redstone_bucket"));
        generated(reg.getSup("glowstone_bucket"));
        generated(reg.getSup("ender_bucket"));

        generated(reg.getSup("creosote_bucket"));
        generated(reg.getSup("crude_oil_bucket"));
        generated(reg.getSup("heavy_oil_bucket"));
        generated(reg.getSup("light_oil_bucket"));
        generated(reg.getSup("refined_fuel_bucket"));

        generated(reg.getSup("latex_bucket"));
        generated(reg.getSup("resin_bucket"));
        generated(reg.getSup("sap_bucket"));
        generated(reg.getSup("syrup_bucket"));
        generated(reg.getSup("tree_oil_bucket"));

        generated(reg.getSup("copper_nugget"));
        generated(reg.getSup("netherite_nugget"));

        generated(reg.getSup("raw_lead"));
        generated(reg.getSup("raw_nickel"));
        generated(reg.getSup("raw_silver"));
        generated(reg.getSup("raw_tin"));

        metalSet(reg, "lead");
        metalSet(reg, "nickel");
        metalSet(reg, "silver");
        metalSet(reg, "tin");

        alloySet(reg, "bronze");
        alloySet(reg, "constantan");
        alloySet(reg, "electrum");
        alloySet(reg, "invar");

        gemSet(reg, "ruby");
        gemSet(reg, "sapphire");

        alloySet(reg, "signalum");
        alloySet(reg, "lumium");
        alloySet(reg, "enderium");
    }

    private void registerParts(DeferredRegisterCoFH<Item> reg) {

        generated(reg.getSup("redstone_servo"));
        generated(reg.getSup("rf_coil"));

        generated(reg.getSup("drill_head"));
        generated(reg.getSup("saw_blade"));
        generated(reg.getSup("laser_diode"));
    }

    private void registerTools(DeferredRegisterCoFH<Item> reg) {

        handheld(reg.getSup("wrench"));
        // handheld(reg.getSup("redprint"));
        // handheld(reg.getSup("xp_crystal"));
        generated(reg.getSup("rf_potato"));
        generated(reg.getSup("lock"));
        generated(reg.getSup("phytogro"));
        // generated(reg.getSup("fluxed_phytogro"));

        generated(reg.getSup("earth_charge"));
        generated(reg.getSup("ice_charge"));
        generated(reg.getSup("lightning_charge"));
    }

    private void registerArmor(DeferredRegisterCoFH<Item> reg) {

        generated(reg.getSup(ID_BEEKEEPER_HELMET));
        generated(reg.getSup(ID_BEEKEEPER_CHESTPLATE));
        generated(reg.getSup(ID_BEEKEEPER_LEGGINGS));
        generated(reg.getSup(ID_BEEKEEPER_BOOTS));

        generated(reg.getSup(ID_DIVING_HELMET));
        generated(reg.getSup(ID_DIVING_CHESTPLATE));
        generated(reg.getSup(ID_DIVING_LEGGINGS));
        generated(reg.getSup(ID_DIVING_BOOTS));

        generated(reg.getSup(ID_HAZMAT_HELMET));
        generated(reg.getSup(ID_HAZMAT_CHESTPLATE));
        generated(reg.getSup(ID_HAZMAT_LEGGINGS));
        generated(reg.getSup(ID_HAZMAT_BOOTS));
    }

    private void registerAugments(DeferredRegisterCoFH<Item> reg) {

        //        for (int i = 1; i <= 3; ++i) {
        //            generated(reg.getSup("upgrade_augment_" + i), AUGMENTS);
        //        }
        generated(reg.getSup("rs_control_augment"), AUGMENTS);
        generated(reg.getSup("side_config_augment"), AUGMENTS);
        generated(reg.getSup("xp_storage_augment"), AUGMENTS);

        generated(reg.getSup("fluid_tank_augment"), AUGMENTS);
        generated(reg.getSup("rf_coil_augment"), AUGMENTS);
        generated(reg.getSup("rf_coil_storage_augment"), AUGMENTS);
        generated(reg.getSup("rf_coil_xfer_augment"), AUGMENTS);

        generated(reg.getSup("fluid_tank_creative_augment"), AUGMENTS);
        generated(reg.getSup("rf_coil_creative_augment"), AUGMENTS);

        generated(reg.getSup("area_radius_augment"), AUGMENTS);

        // generated(reg.getSup("dual_filter_augment"), AUGMENTS);
        // generated(reg.getSup("fluid_filter_augment"), AUGMENTS);
        generated(reg.getSup("item_filter_augment"), AUGMENTS);

        generated(reg.getSup("potion_amplifier_augment"), AUGMENTS);
        generated(reg.getSup("potion_duration_augment"), AUGMENTS);

        generated(reg.getSup("machine_speed_augment"), AUGMENTS);
        generated(reg.getSup("machine_efficiency_augment"), AUGMENTS);
        generated(reg.getSup("machine_efficiency_creative_augment"), AUGMENTS);
        generated(reg.getSup("machine_output_augment"), AUGMENTS);
        generated(reg.getSup("machine_catalyst_augment"), AUGMENTS);
        generated(reg.getSup("machine_catalyst_creative_augment"), AUGMENTS);
        generated(reg.getSup("machine_cycle_augment"), AUGMENTS);
        generated(reg.getSup("machine_null_augment"), AUGMENTS);

        generated(reg.getSup("dynamo_output_augment"), AUGMENTS);
        generated(reg.getSup("dynamo_fuel_augment"), AUGMENTS);
    }
    // endregion

    private void registerBlockItemModels() {

        DeferredRegisterCoFH<Block> reg = BLOCKS;

        registerVanillaBlocks(reg);
        registerResourceBlocks(reg);
        registerStorageBlocks(reg);
        registerBuildingBlocks(reg);
        registerMiscBlocks(reg);
    }

    // region BLOCK HELPERS
    private void registerVanillaBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_CHARCOAL_BLOCK));
        blockItem(reg.getSup(ID_BAMBOO_BLOCK));
        blockItem(reg.getSup(ID_SUGAR_CANE_BLOCK));
        blockItem(reg.getSup(ID_GUNPOWDER_BLOCK));

        blockItem(reg.getSup(ID_APPLE_BLOCK));
        blockItem(reg.getSup(ID_BEETROOT_BLOCK));
        blockItem(reg.getSup(ID_CARROT_BLOCK));
        blockItem(reg.getSup(ID_POTATO_BLOCK));
    }

    private void registerResourceBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_APATITE_ORE));
        blockItem(reg.getSup(ID_CINNABAR_ORE));
        blockItem(reg.getSup(ID_NITER_ORE));
        blockItem(reg.getSup(ID_SULFUR_ORE));

        blockItem(reg.getSup(ID_LEAD_ORE));
        blockItem(reg.getSup(ID_NICKEL_ORE));
        blockItem(reg.getSup(ID_SILVER_ORE));
        blockItem(reg.getSup(ID_TIN_ORE));

        blockItem(reg.getSup(ID_RUBY_ORE));
        blockItem(reg.getSup(ID_SAPPHIRE_ORE));

        blockItem(reg.getSup(deepslate(ID_APATITE_ORE)));
        blockItem(reg.getSup(deepslate(ID_CINNABAR_ORE)));
        blockItem(reg.getSup(deepslate(ID_NITER_ORE)));
        blockItem(reg.getSup(deepslate(ID_SULFUR_ORE)));

        blockItem(reg.getSup(deepslate(ID_LEAD_ORE)));
        blockItem(reg.getSup(deepslate(ID_NICKEL_ORE)));
        blockItem(reg.getSup(deepslate(ID_SILVER_ORE)));
        blockItem(reg.getSup(deepslate(ID_TIN_ORE)));

        blockItem(reg.getSup(deepslate(ID_RUBY_ORE)));
        blockItem(reg.getSup(deepslate(ID_SAPPHIRE_ORE)));

        blockItem(reg.getSup(ID_OIL_SAND));
        blockItem(reg.getSup(ID_OIL_RED_SAND));
    }

    private void registerStorageBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_APATITE_BLOCK));
        blockItem(reg.getSup(ID_CINNABAR_BLOCK));
        blockItem(reg.getSup(ID_NITER_BLOCK));
        blockItem(reg.getSup(ID_SULFUR_BLOCK));

        blockItem(reg.getSup(ID_RAW_LEAD_BLOCK));
        blockItem(reg.getSup(ID_RAW_NICKEL_BLOCK));
        blockItem(reg.getSup(ID_RAW_SILVER_BLOCK));
        blockItem(reg.getSup(ID_RAW_TIN_BLOCK));

        blockItem(reg.getSup(ID_LEAD_BLOCK));
        blockItem(reg.getSup(ID_NICKEL_BLOCK));
        blockItem(reg.getSup(ID_SILVER_BLOCK));
        blockItem(reg.getSup(ID_TIN_BLOCK));

        blockItem(reg.getSup(ID_RUBY_BLOCK));
        blockItem(reg.getSup(ID_SAPPHIRE_BLOCK));

        blockItem(reg.getSup(ID_BRONZE_BLOCK));
        blockItem(reg.getSup(ID_ELECTRUM_BLOCK));
        blockItem(reg.getSup(ID_INVAR_BLOCK));
        blockItem(reg.getSup(ID_CONSTANTAN_BLOCK));

        blockItem(reg.getSup(ID_ENDERIUM_BLOCK));
        blockItem(reg.getSup(ID_LUMIUM_BLOCK));
        blockItem(reg.getSup(ID_SIGNALUM_BLOCK));

        blockItem(reg.getSup(ID_SAWDUST_BLOCK));
        blockItem(reg.getSup(ID_COAL_COKE_BLOCK));
        blockItem(reg.getSup(ID_BITUMEN_BLOCK));
        blockItem(reg.getSup(ID_TAR_BLOCK));
        blockItem(reg.getSup(ID_ROSIN_BLOCK));

        blockItem(reg.getSup(ID_RUBBER_BLOCK));
        blockItem(reg.getSup(ID_CURED_RUBBER_BLOCK));
        blockItem(reg.getSup(ID_SLAG_BLOCK));
        blockItem(reg.getSup(ID_RICH_SLAG_BLOCK));
    }

    private void registerBuildingBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_OBSIDIAN_GLASS));
        blockItem(reg.getSup(ID_SIGNALUM_GLASS));
        blockItem(reg.getSup(ID_LUMIUM_GLASS));
        blockItem(reg.getSup(ID_ENDERIUM_GLASS));

        blockItem(reg.getSup(ID_WHITE_ROCKWOOL));
        blockItem(reg.getSup(ID_ORANGE_ROCKWOOL));
        blockItem(reg.getSup(ID_MAGENTA_ROCKWOOL));
        blockItem(reg.getSup(ID_LIGHT_BLUE_ROCKWOOL));
        blockItem(reg.getSup(ID_YELLOW_ROCKWOOL));
        blockItem(reg.getSup(ID_LIME_ROCKWOOL));
        blockItem(reg.getSup(ID_PINK_ROCKWOOL));
        blockItem(reg.getSup(ID_GRAY_ROCKWOOL));
        blockItem(reg.getSup(ID_LIGHT_GRAY_ROCKWOOL));
        blockItem(reg.getSup(ID_CYAN_ROCKWOOL));
        blockItem(reg.getSup(ID_PURPLE_ROCKWOOL));
        blockItem(reg.getSup(ID_BLUE_ROCKWOOL));
        blockItem(reg.getSup(ID_BROWN_ROCKWOOL));
        blockItem(reg.getSup(ID_GREEN_ROCKWOOL));
        blockItem(reg.getSup(ID_RED_ROCKWOOL));
        blockItem(reg.getSup(ID_BLACK_ROCKWOOL));

        blockItem(reg.getSup(ID_POLISHED_SLAG));
        blockItem(reg.getSup(ID_CHISELED_SLAG));
        blockItem(reg.getSup(ID_SLAG_BRICKS));
        blockItem(reg.getSup(ID_CRACKED_SLAG_BRICKS));
        blockItem(reg.getSup(ID_POLISHED_RICH_SLAG));
        blockItem(reg.getSup(ID_CHISELED_RICH_SLAG));
        blockItem(reg.getSup(ID_RICH_SLAG_BRICKS));
        blockItem(reg.getSup(ID_CRACKED_RICH_SLAG_BRICKS));
    }

    private void registerMiscBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_SLIME_TNT));
        blockItem(reg.getSup(ID_REDSTONE_TNT));
        blockItem(reg.getSup(ID_GLOWSTONE_TNT));
        blockItem(reg.getSup(ID_ENDER_TNT));

        blockItem(reg.getSup(ID_PHYTO_TNT));

        blockItem(reg.getSup(ID_FIRE_TNT));
        blockItem(reg.getSup(ID_EARTH_TNT));
        blockItem(reg.getSup(ID_ICE_TNT));
        blockItem(reg.getSup(ID_LIGHTNING_TNT));

        blockItem(reg.getSup(ID_NUKE_TNT));
    }
    // endregion
}
