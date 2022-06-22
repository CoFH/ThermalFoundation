package cofh.thermal.core.init;

import cofh.core.content.item.EnergyContainerItem;
import cofh.core.content.item.ItemCoFH;
import cofh.core.content.item.SpawnEggItemCoFH;
import cofh.core.util.filter.FilterRegistry;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.content.block.TntBlockCoFH;
import cofh.lib.content.item.ArmorMaterialCoFH;
import cofh.thermal.core.item.*;
import cofh.thermal.lib.item.AugmentItem;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

import static cofh.lib.util.Constants.FALSE;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreEntities.*;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.flagUniqueAugment;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;
import static cofh.thermal.lib.common.ThermalItemGroups.*;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class TCoreItems {

    private TCoreItems() {

    }

    public static void register() {

        registerResources();
        registerParts();
        registerAugments();
        registerMaterials();
        registerTools();
        registerArmor();

        registerSpawnEggs();
    }

    public static void setup() {

        DetonatorItem.registerTNT(Blocks.TNT, PrimedTnt::new);

        DetonatorItem.registerTNT(BLOCKS.get(ID_SLIME_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_SLIME_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_REDSTONE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_REDSTONE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_GLOWSTONE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_GLOWSTONE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_ENDER_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_ENDER_TNT))).getFactory());

        DetonatorItem.registerTNT(BLOCKS.get(ID_FIRE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_FIRE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_EARTH_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_EARTH_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_ICE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_ICE_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_LIGHTNING_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_LIGHTNING_TNT))).getFactory());

        DetonatorItem.registerTNT(BLOCKS.get(ID_PHYTO_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_PHYTO_TNT))).getFactory());
        DetonatorItem.registerTNT(BLOCKS.get(ID_NUKE_TNT), ((TntBlockCoFH) (BLOCKS.get(ID_NUKE_TNT))).getFactory());

        ((DivingArmorItem) ITEMS.get(ID_DIVING_HELMET)).setup();
        ((DivingArmorItem) ITEMS.get(ID_DIVING_CHESTPLATE)).setup();
        ((DivingArmorItem) ITEMS.get(ID_DIVING_LEGGINGS)).setup();
        ((DivingArmorItem) ITEMS.get(ID_DIVING_BOOTS)).setup();

        flagUniqueAugment(ITEMS.get("rs_control_augment"));
        flagUniqueAugment(ITEMS.get("side_config_augment"));
        flagUniqueAugment(ITEMS.get("xp_storage_augment"));

        flagUniqueAugment(ITEMS.get("upgrade_augment_1"));
        flagUniqueAugment(ITEMS.get("upgrade_augment_2"));
        flagUniqueAugment(ITEMS.get("upgrade_augment_3"));

        flagUniqueAugment(ITEMS.get("rf_coil_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_storage_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_xfer_augment"));
        flagUniqueAugment(ITEMS.get("rf_coil_creative_augment"));

        flagUniqueAugment(ITEMS.get("fluid_tank_augment"));
        flagUniqueAugment(ITEMS.get("fluid_tank_creative_augment"));

        flagUniqueAugment(ITEMS.get("item_filter_augment"));

        flagUniqueAugment(ITEMS.get("machine_efficiency_creative_augment"));
        flagUniqueAugment(ITEMS.get("machine_catalyst_creative_augment"));
        flagUniqueAugment(ITEMS.get("machine_cycle_augment"));
        flagUniqueAugment(ITEMS.get("machine_null_augment"));

        flagUniqueAugment(ITEMS.get("dynamo_throttle_augment"));
    }

    // region HELPERS
    private static void registerResources() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("sawdust", group);
        registerItem("coal_coke", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(3200));
        registerItem("bitumen", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(1600));
        registerItem("tar", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(800));
        registerItem("rosin", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(800));
        registerItem("rubber", group);
        registerItem("cured_rubber", group);
        registerItem("slag", group);
        registerItem("rich_slag", group);

        registerItem("syrup_bottle", () -> new HoneyBottleItem(new Item.Properties().tab(THERMAL_FOODS).craftRemainder(GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).stacksTo(16)));

        //        registerItem("biomass", group);
        //        registerItem("rich_biomass", group);

        registerItem("basalz_rod", group);
        registerItem("basalz_powder", group);
        registerItem("blitz_rod", group);
        registerItem("blitz_powder", group);
        registerItem("blizz_rod", group);
        registerItem("blizz_powder", group);

        registerItem("beekeeper_fabric", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_BEEKEEPER_ARMOR)));
        registerItem("diving_fabric", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_DIVING_ARMOR)));
        registerItem("hazmat_fabric", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_HAZMAT_ARMOR)));

        registerItem("apatite", group);
        registerItem("apatite_dust", group);
        registerItem("cinnabar", group);
        registerItem("cinnabar_dust", group);
        registerItem("niter", group);
        registerItem("niter_dust", group);
        registerItem("sulfur", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(1200));
        registerItem("sulfur_dust", () -> new ItemCoFH(new Item.Properties().tab(group)).setBurnTime(1200));
    }

    private static void registerParts() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("redstone_servo", group);
        registerItem("rf_coil", group);

        registerItem("drill_head", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_TOOL_COMPONENTS)));
        registerItem("saw_blade", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_TOOL_COMPONENTS)));
        registerItem("laser_diode", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(FALSE));//.setShowInGroups(getFeature(FLAG_TOOL_COMPONENTS))); // TODO: Implement
    }

    private static void registerMaterials() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("ender_pearl_dust", group);

        registerVanillaMetalSet("iron", group);
        registerVanillaMetalSet("gold", group);
        registerVanillaMetalSet("copper", group);
        registerVanillaMetalSet("netherite", group);

        registerVanillaGemSet("lapis", group);
        registerVanillaGemSet("diamond", group);
        registerVanillaGemSet("emerald", group);
        registerVanillaGemSet("quartz", group);

        registerMetalSet("tin", group, getFlag(FLAG_RESOURCE_TIN));
        registerMetalSet("lead", group, getFlag(FLAG_RESOURCE_LEAD));
        registerMetalSet("silver", group, getFlag(FLAG_RESOURCE_SILVER));
        registerMetalSet("nickel", group, getFlag(FLAG_RESOURCE_NICKEL));

        registerAlloySet("bronze", group, getFlag(FLAG_RESOURCE_BRONZE));
        registerAlloySet("electrum", group, getFlag(FLAG_RESOURCE_ELECTRUM));
        registerAlloySet("invar", group, getFlag(FLAG_RESOURCE_INVAR));
        registerAlloySet("constantan", group, getFlag(FLAG_RESOURCE_CONSTANTAN));

        Rarity rarity = Rarity.UNCOMMON;

        registerAlloySet("signalum", group, rarity);
        registerAlloySet("lumium", group, rarity);
        registerAlloySet("enderium", group, rarity);

        registerGemSet("ruby", group, getFlag(FLAG_RESOURCE_RUBY));
        registerGemSet("sapphire", group, getFlag(FLAG_RESOURCE_SAPPHIRE));
    }

    private static void registerTools() {

        CreativeModeTab group = THERMAL_TOOLS;

        // TODO Wrench tool type.
        registerItem(ID_WRENCH, () -> new WrenchItem(new Item.Properties().stacksTo(1).tab(group)).setShowInGroups(getFlag(ID_WRENCH)));
        registerItem(ID_REDPRINT, () -> new RedprintItem(new Item.Properties().stacksTo(1).tab(group)).setShowInGroups(getFlag(ID_REDPRINT)));
        registerItem(ID_RF_POTATO, () -> new EnergyContainerItem(new Item.Properties().stacksTo(1).tab(group), 100000, 40) {

            @Override
            public Capability<? extends IEnergyStorage> getEnergyCapability() {

                return ThermalEnergyHelper.getBaseEnergySystem();
            }
        }.setShowInGroups(getFlag(ID_RF_POTATO)));
        registerItem(ID_XP_CRYSTAL, () -> new XpCrystalItem(new Item.Properties().stacksTo(1).tab(group), 10000).setShowInGroups(getFlag(ID_XP_CRYSTAL)));
        registerItem(ID_LOCK, () -> new LockItem(new Item.Properties().tab(group)).setShowInGroups(getFlag(ID_LOCK)));
        registerItem(ID_SATCHEL, () -> new SatchelItem(new Item.Properties().stacksTo(1).tab(group), 9).setShowInGroups(getFlag(ID_SATCHEL)));

        registerItem("compost", () -> new FertilizerItem(new Item.Properties().tab(group), 2));
        registerItem("phytogro", () -> new FertilizerItem(new Item.Properties().tab(group)));
        // registerItem("fluxed_phytogro", () -> new FertilizerItem(new Item.Properties().group(group), 5));

        registerItem("junk_net", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(ID_DEVICE_FISHER)));
        registerItem("aquachow", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(ID_DEVICE_FISHER)));
        registerItem("deep_aquachow", () -> new ItemCoFH(new Item.Properties().tab(group)).setShowInGroups(getFlag(ID_DEVICE_FISHER)));
        //        registerItem("rich_aquachow", group);
        //        registerItem("fluxed_aquachow", group);

        registerItem("earth_charge", () -> new EarthChargeItem(new Item.Properties().tab(group)));
        registerItem("ice_charge", () -> new IceChargeItem(new Item.Properties().tab(group)));
        registerItem("lightning_charge", () -> new LightningChargeItem(new Item.Properties().tab(group)));

        registerItem(ID_DETONATOR, () -> new DetonatorItem(new Item.Properties().stacksTo(1).tab(group)).setShowInGroups(getFlag(ID_DETONATOR)));
    }

    private static void registerArmor() {

        CreativeModeTab group = THERMAL_TOOLS;

        ITEMS.register(ID_BEEKEEPER_HELMET, () -> new BeekeeperArmorItem(BEEKEEPER, EquipmentSlot.HEAD, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_BEEKEEPER_ARMOR)));
        ITEMS.register(ID_BEEKEEPER_CHESTPLATE, () -> new BeekeeperArmorItem(BEEKEEPER, EquipmentSlot.CHEST, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_BEEKEEPER_ARMOR)));
        ITEMS.register(ID_BEEKEEPER_LEGGINGS, () -> new BeekeeperArmorItem(BEEKEEPER, EquipmentSlot.LEGS, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_BEEKEEPER_ARMOR)));
        ITEMS.register(ID_BEEKEEPER_BOOTS, () -> new BeekeeperArmorItem(BEEKEEPER, EquipmentSlot.FEET, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_BEEKEEPER_ARMOR)));

        ITEMS.register(ID_DIVING_HELMET, () -> new DivingArmorItem(DIVING, EquipmentSlot.HEAD, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_DIVING_ARMOR)));
        ITEMS.register(ID_DIVING_CHESTPLATE, () -> new DivingArmorItem(DIVING, EquipmentSlot.CHEST, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_DIVING_ARMOR)));
        ITEMS.register(ID_DIVING_LEGGINGS, () -> new DivingArmorItem(DIVING, EquipmentSlot.LEGS, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_DIVING_ARMOR)));
        ITEMS.register(ID_DIVING_BOOTS, () -> new DivingArmorItem(DIVING, EquipmentSlot.FEET, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_DIVING_ARMOR)));

        ITEMS.register(ID_HAZMAT_HELMET, () -> new HazmatArmorItem(HAZMAT, EquipmentSlot.HEAD, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_HAZMAT_ARMOR)));
        ITEMS.register(ID_HAZMAT_CHESTPLATE, () -> new HazmatArmorItem(HAZMAT, EquipmentSlot.CHEST, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_HAZMAT_ARMOR)));
        ITEMS.register(ID_HAZMAT_LEGGINGS, () -> new HazmatArmorItem(HAZMAT, EquipmentSlot.LEGS, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_HAZMAT_ARMOR)));
        ITEMS.register(ID_HAZMAT_BOOTS, () -> new HazmatArmorItem(HAZMAT, EquipmentSlot.FEET, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_HAZMAT_ARMOR)));
    }

    // region AUGMENTS
    private static void registerAugments() {

        registerUpgradeAugments();
        registerFeatureAugments();
        registerStorageAugments();
        registerFilterAugments();
        registerMachineAugments();
        registerDynamoAugments();
        registerAreaAugments();
        registerPotionAugments();
    }

    private static void registerUpgradeAugments() {

        CreativeModeTab group = THERMAL_ITEMS;
        final float[] upgradeMods = new float[]{1.0F, 2.0F, 3.0F, 4.0F, 6.0F, 8.5F};
        // final float[] upgradeMods = new float[]{1.0F, 1.5F, 2.0F, 2.5F, 3.0F, 3.5F};

        for (int i = 1; i <= 3; ++i) {
            int tier = i;
            registerItem("upgrade_augment_" + i, () -> new AugmentItem(new Item.Properties().tab(group),
                    AugmentDataHelper.builder()
                            .type(TAG_AUGMENT_TYPE_UPGRADE)
                            .mod(TAG_AUGMENT_BASE_MOD, upgradeMods[tier])
                            .build()).setShowInGroups(getFlag(FLAG_UPGRADE_AUGMENTS)));
        }
    }

    private static void registerFeatureAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("rs_control_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_RS_CONTROL, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_RS_CONTROL_AUGMENT)));

        registerItem("side_config_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_SIDE_CONFIG, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_SIDE_CONFIG_AUGMENT)));

        registerItem("xp_storage_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .mod(TAG_AUGMENT_FEATURE_XP_STORAGE, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_XP_STORAGE_AUGMENT)));
    }

    private static void registerStorageAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("rf_coil_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 4.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 4.0F)
                        .build()).setShowInGroups(getFlag(FLAG_STORAGE_AUGMENTS)));

        registerItem("rf_coil_storage_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 6.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 2.0F)
                        .build()).setShowInGroups(getFlag(FLAG_STORAGE_AUGMENTS)));

        registerItem("rf_coil_xfer_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 2.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 6.0F)
                        .build()).setShowInGroups(getFlag(FLAG_STORAGE_AUGMENTS)));

        registerItem("rf_coil_creative_augment", () -> new AugmentItem(new Item.Properties().tab(group).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_RF)
                        .mod(TAG_AUGMENT_RF_STORAGE, 16.0F)
                        .mod(TAG_AUGMENT_RF_XFER, 16.0F)
                        .mod(TAG_AUGMENT_RF_CREATIVE, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_CREATIVE_STORAGE_AUGMENTS)));

        registerItem("fluid_tank_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FLUID)
                        .mod(TAG_AUGMENT_FLUID_STORAGE, 4.0F)
                        .build()).setShowInGroups(getFlag(FLAG_STORAGE_AUGMENTS)));

        registerItem("fluid_tank_creative_augment", () -> new AugmentItem(new Item.Properties().tab(group).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FLUID)
                        .mod(TAG_AUGMENT_FLUID_STORAGE, 16.0F)
                        .mod(TAG_AUGMENT_FLUID_CREATIVE, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_CREATIVE_STORAGE_AUGMENTS)));
    }

    private static void registerFilterAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("item_filter_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_FILTER)
                        .feature(TAG_FILTER_TYPE, FilterRegistry.ITEM_FILTER_TYPE)
                        .build()).setShowInGroups(getFlag(FLAG_FILTER_AUGMENTS)));

        //        registerItem("fluid_filter_augment", () -> new AugmentItem(new Item.Properties().group(group),
        //                AugmentDataHelper.builder()
        //                        .type(TAG_AUGMENT_TYPE_FILTER)
        //                        .feature(TAG_FILTER_TYPE, FilterRegistry.FLUID_FILTER_TYPE)
        //                        .build()).setShowInGroups(getFlag(FLAG_FILTER_AUGMENTS)));
        //
        //        registerItem("dual_filter_augment", () -> new AugmentItem(new Item.Properties().group(group),
        //                AugmentDataHelper.builder()
        //                        .type(TAG_AUGMENT_TYPE_FILTER)
        //                        .feature(TAG_FILTER_TYPE, FilterRegistry.DUAL_FILTER_TYPE)
        //                        .build()).setShowInGroups(getFlag(FLAG_FILTER_AUGMENTS)));
    }

    private static void registerMachineAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("machine_speed_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_POWER, 1.0F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.1F)
                        .build()).setShowInGroups(getFlag(FLAG_MACHINE_AUGMENTS)));

        registerItem("machine_efficiency_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_SPEED, -0.1F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 0.9F)
                        .build()).setShowInGroups(getFlag(FLAG_MACHINE_AUGMENTS)));

        registerItem("machine_efficiency_creative_augment", () -> new AugmentItem(new Item.Properties().tab(group).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 0.0F)
                        .build()).setShowInGroups(getFlag(FLAG_CREATIVE_MACHINE_AUGMENTS)));

        registerItem("machine_output_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_SECONDARY, 0.15F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.25F)
                        .build()).setShowInGroups(getFlag(FLAG_MACHINE_AUGMENTS)));

        registerItem("machine_catalyst_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_CATALYST, 0.8F)
                        .mod(TAG_AUGMENT_MACHINE_ENERGY, 1.25F)
                        .build()).setShowInGroups(getFlag(FLAG_MACHINE_AUGMENTS)));

        registerItem("machine_catalyst_creative_augment", () -> new AugmentItem(new Item.Properties().tab(group).rarity(Rarity.EPIC),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_MACHINE_CATALYST, 0.0F)
                        .build()).setShowInGroups(getFlag(FLAG_CREATIVE_MACHINE_AUGMENTS)));

        registerItem("machine_cycle_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_FEATURE_CYCLE_PROCESS, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_MACHINE_AUGMENTS)));

        registerItem("machine_null_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_MACHINE)
                        .mod(TAG_AUGMENT_FEATURE_SECONDARY_NULL, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_MACHINE_AUGMENTS)));
    }

    private static void registerDynamoAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("dynamo_output_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_POWER, 1.0F)
                        .mod(TAG_AUGMENT_DYNAMO_ENERGY, 0.9F)
                        .build()).setShowInGroups(getFlag(FLAG_DYNAMO_AUGMENTS)));

        registerItem("dynamo_fuel_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_ENERGY, 1.1F)
                        .build()).setShowInGroups(getFlag(FLAG_DYNAMO_AUGMENTS)));

        registerItem("dynamo_throttle_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_DYNAMO)
                        .mod(TAG_AUGMENT_DYNAMO_THROTTLE, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_DYNAMO_AUGMENTS)));
    }

    private static void registerAreaAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("area_radius_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_AREA_EFFECT)
                        .mod(TAG_AUGMENT_RADIUS, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_AREA_AUGMENTS)));
    }

    private static void registerPotionAugments() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("potion_amplifier_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_POTION)
                        .mod(TAG_AUGMENT_POTION_AMPLIFIER, 1.0F)
                        .mod(TAG_AUGMENT_POTION_DURATION, -0.25F)
                        .build()).setShowInGroups(getFlag(FLAG_POTION_AUGMENTS)));

        registerItem("potion_duration_augment", () -> new AugmentItem(new Item.Properties().tab(group),
                AugmentDataHelper.builder()
                        .type(TAG_AUGMENT_TYPE_POTION)
                        .mod(TAG_AUGMENT_POTION_DURATION, 1.0F)
                        .build()).setShowInGroups(getFlag(FLAG_POTION_AUGMENTS)));
    }
    // endregion

    private static void registerSpawnEggs() {

        CreativeModeTab group = THERMAL_ITEMS;

        registerItem("basalz_spawn_egg", () -> new SpawnEggItemCoFH(BASALZ::get, 0x363840, 0x080407, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_MOB_BASALZ)));
        registerItem("blizz_spawn_egg", () -> new SpawnEggItemCoFH(BLIZZ::get, 0xD8DBE5, 0x91D9FC, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_MOB_BLIZZ)));
        registerItem("blitz_spawn_egg", () -> new SpawnEggItemCoFH(BLITZ::get, 0xC9EEFF, 0xFFD97E, new Item.Properties().tab(group)).setShowInGroups(getFlag(FLAG_MOB_BLITZ)));
    }
    // endregion

    public static final ArmorMaterialCoFH BEEKEEPER = new ArmorMaterialCoFH("thermal:beekeeper", 4, new int[]{1, 2, 3, 1}, 16, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.0F, 0.0F, () -> Ingredient.of(ITEMS.get("beekeeper_fabric")));
    public static final ArmorMaterialCoFH DIVING = new ArmorMaterialCoFH("thermal:diving", 12, new int[]{1, 4, 5, 2}, 20, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(ITEMS.get("diving_fabric")));
    public static final ArmorMaterialCoFH HAZMAT = new ArmorMaterialCoFH("thermal:hazmat", 6, new int[]{1, 4, 5, 2}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ITEMS.get("hazmat_fabric")));

}
