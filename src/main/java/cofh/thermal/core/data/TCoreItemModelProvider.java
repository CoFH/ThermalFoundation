package cofh.thermal.core.data;

import cofh.lib.data.ItemModelProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreItemModelProvider extends ItemModelProviderCoFH {

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

        metalSet(reg, "iron", true);
        metalSet(reg, "gold", true);

        gemSet(reg, "lapis", true);
        gemSet(reg, "diamond", true);
        gemSet(reg, "emerald", true);
        gemSet(reg, "quartz", true);

        generated(reg.getSup("ender_pearl_dust"), DUSTS);
    }

    private void registerResources(DeferredRegisterCoFH<Item> reg) {

        generated(reg.getSup("sawdust"), RESOURCES);
        generated(reg.getSup("coal_coke"), RESOURCES);
        generated(reg.getSup("bitumen"), RESOURCES);
        generated(reg.getSup("tar"), RESOURCES);
        generated(reg.getSup("rosin"), RESOURCES);
        generated(reg.getSup("rubber"), RESOURCES);
        generated(reg.getSup("cured_rubber"), RESOURCES);
        generated(reg.getSup("slag"), RESOURCES);
        generated(reg.getSup("rich_slag"), RESOURCES);

        generated(reg.getSup("junk_net"), TOOLS);
        generated(reg.getSup("aquachow"), TOOLS);
        generated(reg.getSup("deep_aquachow"), TOOLS);
        //        generated(reg.getSup("rich_aquachow"), TOOLS);
        //        generated(reg.getSup("fluxed_aquachow"), TOOLS);

        generated(reg.getSup("beekeeper_fabric"), CRAFTING);
        generated(reg.getSup("diving_fabric"), CRAFTING);
        generated(reg.getSup("hazmat_fabric"), CRAFTING);

        generated(reg.getSup("apatite_dust"), DUSTS);
        generated(reg.getSup("cinnabar_dust"), DUSTS);
        generated(reg.getSup("niter_dust"), DUSTS);
        generated(reg.getSup("sulfur_dust"), DUSTS);

        generated(reg.getSup("apatite"), RESOURCES);
        generated(reg.getSup("cinnabar"), RESOURCES);
        generated(reg.getSup("niter"), RESOURCES);
        generated(reg.getSup("sulfur"), RESOURCES);

        generated(reg.getSup("basalz_rod"), RESOURCES);
        generated(reg.getSup("basalz_powder"), RESOURCES);
        generated(reg.getSup("blitz_rod"), RESOURCES);
        generated(reg.getSup("blitz_powder"), RESOURCES);
        generated(reg.getSup("blizz_rod"), RESOURCES);
        generated(reg.getSup("blizz_powder"), RESOURCES);

        generated(reg.getSup("redstone_bucket"), RESOURCES);
        generated(reg.getSup("glowstone_bucket"), RESOURCES);
        generated(reg.getSup("ender_bucket"), RESOURCES);

        generated(reg.getSup("creosote_bucket"), RESOURCES);
        generated(reg.getSup("crude_oil_bucket"), RESOURCES);
        generated(reg.getSup("heavy_oil_bucket"), RESOURCES);
        generated(reg.getSup("light_oil_bucket"), RESOURCES);
        generated(reg.getSup("refined_fuel_bucket"), RESOURCES);

        generated(reg.getSup("latex_bucket"), RESOURCES);
        generated(reg.getSup("resin_bucket"), RESOURCES);
        generated(reg.getSup("sap_bucket"), RESOURCES);
        generated(reg.getSup("syrup_bucket"), RESOURCES);
        generated(reg.getSup("tree_oil_bucket"), RESOURCES);

        metalSet(reg, "copper");
        metalSet(reg, "lead");
        metalSet(reg, "nickel");
        metalSet(reg, "silver");
        metalSet(reg, "tin");

        metalSet(reg, "bronze");
        metalSet(reg, "constantan");
        metalSet(reg, "electrum");
        metalSet(reg, "invar");

        gemSet(reg, "ruby");
        gemSet(reg, "sapphire");

        metalSet(reg, "signalum");
        metalSet(reg, "lumium");
        metalSet(reg, "enderium");
    }

    private void registerParts(DeferredRegisterCoFH<Item> reg) {

        generated(reg.getSup("redstone_servo"), CRAFTING);
        generated(reg.getSup("rf_coil"), CRAFTING);

        generated(reg.getSup("drill_head"), CRAFTING);
        generated(reg.getSup("saw_blade"), CRAFTING);
        generated(reg.getSup("laser_diode"), CRAFTING);
    }

    private void registerTools(DeferredRegisterCoFH<Item> reg) {

        handheld(reg.getSup("wrench"), TOOLS);
        // handheld(reg.getSup("redprint"), TOOLS);
        // handheld(reg.getSup("xp_crystal"), TOOLS);
        generated(reg.getSup("rf_potato"), TOOLS);
        generated(reg.getSup("lock"), TOOLS);
        generated(reg.getSup("phytogro"), TOOLS);
        // generated(reg.getSup("fluxed_phytogro"), TOOLS);

        generated(reg.getSup("earth_charge"), TOOLS);
        generated(reg.getSup("ice_charge"), TOOLS);
        generated(reg.getSup("lightning_charge"), TOOLS);
    }

    private void registerArmor(DeferredRegisterCoFH<Item> reg) {

        generated(reg.getSup(ID_BEEKEEPER_HELMET), ARMOR);
        generated(reg.getSup(ID_BEEKEEPER_CHESTPLATE), ARMOR);
        generated(reg.getSup(ID_BEEKEEPER_LEGGINGS), ARMOR);
        generated(reg.getSup(ID_BEEKEEPER_BOOTS), ARMOR);

        generated(reg.getSup(ID_DIVING_HELMET), ARMOR);
        generated(reg.getSup(ID_DIVING_CHESTPLATE), ARMOR);
        generated(reg.getSup(ID_DIVING_LEGGINGS), ARMOR);
        generated(reg.getSup(ID_DIVING_BOOTS), ARMOR);

        generated(reg.getSup(ID_HAZMAT_HELMET), ARMOR);
        generated(reg.getSup(ID_HAZMAT_CHESTPLATE), ARMOR);
        generated(reg.getSup(ID_HAZMAT_LEGGINGS), ARMOR);
        generated(reg.getSup(ID_HAZMAT_BOOTS), ARMOR);
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

        blockItem(reg.getSup(ID_COPPER_ORE));
        blockItem(reg.getSup(ID_LEAD_ORE));
        blockItem(reg.getSup(ID_NICKEL_ORE));
        blockItem(reg.getSup(ID_SILVER_ORE));
        blockItem(reg.getSup(ID_TIN_ORE));

        blockItem(reg.getSup(ID_RUBY_ORE));
        blockItem(reg.getSup(ID_SAPPHIRE_ORE));

        blockItem(reg.getSup(ID_OIL_SAND));
        blockItem(reg.getSup(ID_OIL_RED_SAND));
    }

    private void registerStorageBlocks(DeferredRegisterCoFH<Block> reg) {

        blockItem(reg.getSup(ID_APATITE_BLOCK));
        blockItem(reg.getSup(ID_CINNABAR_BLOCK));
        blockItem(reg.getSup(ID_NITER_BLOCK));
        blockItem(reg.getSup(ID_SULFUR_BLOCK));

        blockItem(reg.getSup(ID_COPPER_BLOCK));
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
