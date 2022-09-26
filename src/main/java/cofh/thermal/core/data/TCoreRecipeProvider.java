package cofh.thermal.core.data;

import cofh.lib.data.RecipeProviderCoFH;
import cofh.lib.tags.ItemTagsCoFH;
import cofh.thermal.lib.common.ThermalFlags;
import cofh.thermal.lib.util.references.ThermalTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreRecipeProvider extends RecipeProviderCoFH {

    public TCoreRecipeProvider(DataGenerator generatorIn) {

        super(generatorIn, ID_THERMAL);
        manager = ThermalFlags.manager();
    }

    @Override
    public String getName() {

        return "Thermal Core: Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_CHARCOAL_BLOCK), Items.CHARCOAL);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_BAMBOO_BLOCK), Items.BAMBOO);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_SUGAR_CANE_BLOCK), Items.SUGAR_CANE);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_GUNPOWDER_BLOCK), Items.GUNPOWDER);

        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_APPLE_BLOCK), Items.APPLE);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_BEETROOT_BLOCK), Items.BEETROOT);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_CARROT_BLOCK), Items.CARROT);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_POTATO_BLOCK), Items.POTATO);

        generateGearRecipe(consumer, reg.get("copper_gear"), Items.COPPER_INGOT, forgeTag("ingots/copper"));
        generateGearRecipe(consumer, reg.get("iron_gear"), Items.IRON_INGOT, forgeTag("ingots/iron"));
        generateGearRecipe(consumer, reg.get("gold_gear"), Items.GOLD_INGOT, forgeTag("ingots/gold"));
        generateGearRecipe(consumer, reg.get("netherite_gear"), Items.NETHERITE_INGOT, forgeTag("ingots/netherite"));

        generateGearRecipe(consumer, reg.get("diamond_gear"), Items.IRON_INGOT, forgeTag("gems/diamond"));
        generateGearRecipe(consumer, reg.get("emerald_gear"), Items.IRON_INGOT, forgeTag("gems/emerald"));
        generateGearRecipe(consumer, reg.get("quartz_gear"), Items.IRON_INGOT, forgeTag("gems/quartz"));
        generateGearRecipe(consumer, reg.get("lapis_gear"), Items.IRON_INGOT, forgeTag("gems/lapis"));

        generateStorageRecipes(consumer, reg.get(ID_SAWDUST_BLOCK), reg.get("sawdust"));
        generateStorageRecipes(consumer, reg.get(ID_COAL_COKE_BLOCK), reg.get("coal_coke"));
        generateStorageRecipes(consumer, reg.get(ID_BITUMEN_BLOCK), reg.get("bitumen"));
        generateStorageRecipes(consumer, reg.get(ID_TAR_BLOCK), reg.get("tar"));
        generateStorageRecipes(consumer, reg.get(ID_ROSIN_BLOCK), reg.get("rosin"));

        generateSmallStorageRecipes(consumer, reg.get(ID_RUBBER_BLOCK), reg.get("rubber"));
        generateSmallStorageRecipes(consumer, reg.get(ID_CURED_RUBBER_BLOCK), reg.get("cured_rubber"));
        generateSmallStorageRecipes(consumer, reg.get(ID_SLAG_BLOCK), reg.get("slag"));
        generateSmallStorageRecipes(consumer, reg.get(ID_RICH_SLAG_BLOCK), reg.get("rich_slag"));

        generateTypeRecipes(reg, consumer, "signalum");
        generateTypeRecipes(reg, consumer, "lumium");
        generateTypeRecipes(reg, consumer, "enderium");

        generateSmeltingAndBlastingRecipes(reg, consumer, "signalum", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "lumium", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "enderium", 0);

        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("copper_dust"), Items.COPPER_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("iron_dust"), Items.IRON_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("gold_dust"), Items.GOLD_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("netherite_dust"), Items.NETHERITE_INGOT, 0.0F, "smelting", "_dust");

        generatePackingRecipe(consumer, Items.COPPER_INGOT, reg.get("copper_nugget"), "_from_nuggets");
        generatePackingRecipe(consumer, Items.NETHERITE_INGOT, reg.get("netherite_nugget"), "_from_nuggets");

        generateUnpackingRecipe(consumer, Items.COPPER_INGOT, reg.get("copper_nugget"), "_from_ingot");
        generateUnpackingRecipe(consumer, Items.NETHERITE_INGOT, reg.get("netherite_nugget"), "_from_ingot");

        generateSmeltingRecipe(reg, consumer, Items.GRAVEL, reg.get("slag"), 0.1F, "smelting");
        generateSmeltingRecipe(reg, consumer, reg.get("rubber"), reg.get("cured_rubber"), 0.2F, "smelting");

        generateAlloyRecipes(consumer);
        generateArmorRecipes(consumer);
        generateAugmentRecipes(consumer);
        generateBasicRecipes(consumer);
        generateChargeRecipes(consumer);
        generateComponentRecipes(consumer);
        generateExplosiveRecipes(consumer);
        generateRockwoolRecipes(withConditions(consumer).flag(FLAG_ROCKWOOL));
        generateSlagRecipes(consumer);
        generateTileRecipes(consumer);
    }

    // region HELPERS
    private void generateAlloyRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        ShapelessRecipeBuilder.shapeless(reg.get("signalum_dust"), 4)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_SILVER)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer, ID_THERMAL + ":signalum_dust_4");

        ShapelessRecipeBuilder.shapeless(reg.get("lumium_dust"), 4)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .requires(ItemTagsCoFH.DUSTS_SILVER)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_glowstone_dust", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(consumer, ID_THERMAL + ":lumium_dust_4");

        ShapelessRecipeBuilder.shapeless(reg.get("enderium_dust"), 2)
                .requires(ItemTagsCoFH.DUSTS_LEAD)
                .requires(ItemTagsCoFH.DUSTS_LEAD)
                .requires(ItemTagsCoFH.DUSTS_LEAD)
                .requires(ItemTagsCoFH.DUSTS_DIAMOND)
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(consumer, ID_THERMAL + ":enderium_dust_2");

        ShapelessRecipeBuilder.shapeless(reg.get("signalum_ingot"), 4)
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer, ID_THERMAL + ":fire_charge/signalum_ingot_4");

        ShapelessRecipeBuilder.shapeless(reg.get("lumium_ingot"), 4)
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_glowstone_dust", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(consumer, ID_THERMAL + ":fire_charge/lumium_ingot_4");

        ShapelessRecipeBuilder.shapeless(reg.get("enderium_ingot"), 2)
                .requires(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .requires(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .requires(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .requires(ItemTagsCoFH.DUSTS_DIAMOND)
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(consumer, ID_THERMAL + ":fire_charge/enderium_ingot_2");

        ShapelessRecipeBuilder.shapeless(reg.get("obsidian_glass"), 2)
                .requires(Tags.Items.OBSIDIAN)
                .requires(fromTags(ItemTagsCoFH.DUSTS_QUARTZ, Tags.Items.GEMS_QUARTZ))
                .requires(Tags.Items.SAND)
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN))
                .save(consumer, ID_THERMAL + ":fire_charge/obsidian_glass_2");

        ShapelessRecipeBuilder.shapeless(reg.get("signalum_glass"), 2)
                .requires(reg.get("obsidian_glass"))
                .requires(reg.get("obsidian_glass"))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SIGNALUM, ItemTagsCoFH.INGOTS_SIGNALUM))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_signalum_dust", has(ItemTagsCoFH.DUSTS_SIGNALUM))
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(consumer, ID_THERMAL + ":fire_charge/signalum_glass_2");

        ShapelessRecipeBuilder.shapeless(reg.get("lumium_glass"), 2)
                .requires(reg.get("obsidian_glass"))
                .requires(reg.get("obsidian_glass"))
                .requires(fromTags(ItemTagsCoFH.DUSTS_LUMIUM, ItemTagsCoFH.INGOTS_LUMIUM))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_lumium_dust", has(ItemTagsCoFH.DUSTS_LUMIUM))
                .unlockedBy("has_lumium_ingot", has(ItemTagsCoFH.INGOTS_LUMIUM))
                .save(consumer, ID_THERMAL + ":fire_charge/lumium_glass_2");

        ShapelessRecipeBuilder.shapeless(reg.get("enderium_glass"), 2)
                .requires(reg.get("obsidian_glass"))
                .requires(reg.get("obsidian_glass"))
                .requires(fromTags(ItemTagsCoFH.DUSTS_ENDERIUM, ItemTagsCoFH.INGOTS_ENDERIUM))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_enderium_dust", has(ItemTagsCoFH.DUSTS_ENDERIUM))
                .unlockedBy("has_enderium_ingot", has(ItemTagsCoFH.INGOTS_ENDERIUM))
                .save(consumer, ID_THERMAL + ":fire_charge/enderium_glass_2");
    }

    private void generateArmorRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;
        String folder = "armor";
        Item result;

        Item beekeeperFabric = reg.get("beekeeper_fabric");
        Item divingFabric = reg.get("diving_fabric");
        Item hazmatFabric = reg.get("hazmat_fabric");

        ShapedRecipeBuilder.shaped(beekeeperFabric)
                .define('S', Tags.Items.STRING)
                .define('H', Items.HONEYCOMB)
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .save(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR));

        result = reg.get(ID_BEEKEEPER_HELMET);
        ShapedRecipeBuilder.shaped(result)
                .define('X', beekeeperFabric)
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_CHESTPLATE);
        ShapedRecipeBuilder.shaped(result)
                .define('X', beekeeperFabric)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_LEGGINGS);
        ShapedRecipeBuilder.shaped(result)
                .define('X', beekeeperFabric)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_BOOTS);
        ShapedRecipeBuilder.shaped(result)
                .define('L', Tags.Items.LEATHER)
                .define('X', beekeeperFabric)
                .pattern("X X")
                .pattern("L L")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        ShapedRecipeBuilder.shaped(divingFabric)
                .define('S', Tags.Items.STRING)
                .define('H', Tags.Items.GEMS_PRISMARINE)
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .unlockedBy("has_prismarine", has(Tags.Items.GEMS_PRISMARINE))
                .save(withConditions(consumer).flag(FLAG_DIVING_ARMOR));

        result = reg.get(ID_DIVING_HELMET);
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("XIX")
                .pattern("IGI")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_CHESTPLATE);
        ShapedRecipeBuilder.shaped(result)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("X X")
                .pattern("IXI")
                .pattern("XXX")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_LEGGINGS);
        ShapedRecipeBuilder.shaped(result)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("XXX")
                .pattern("I I")
                .pattern("X X")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_BOOTS);
        ShapedRecipeBuilder.shaped(result)
                .define('L', Tags.Items.LEATHER)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("X X")
                .pattern("LIL")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        ShapedRecipeBuilder.shaped(hazmatFabric)
                .define('S', Tags.Items.STRING)
                .define('H', reg.get("cured_rubber"))
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .unlockedBy("has_cured_rubber", has(reg.get("cured_rubber")))
                .save(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR));

        result = reg.get(ID_HAZMAT_HELMET);
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('X', hazmatFabric)
                .pattern("XIX")
                .pattern("IGI")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_CHESTPLATE);
        ShapedRecipeBuilder.shaped(result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('X', hazmatFabric)
                .pattern("X X")
                .pattern("IXI")
                .pattern("XXX")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_LEGGINGS);
        ShapedRecipeBuilder.shaped(result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('X', hazmatFabric)
                .pattern("XXX")
                .pattern("I I")
                .pattern("X X")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_BOOTS);
        ShapedRecipeBuilder.shaped(result)
                .define('L', Tags.Items.LEATHER)
                .define('R', reg.get("cured_rubber"))
                .define('X', hazmatFabric)
                .pattern("X X")
                .pattern("LRL")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));
    }

    private void generateAugmentRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;
        String folder = "augments";
        Item result;

        Item redstoneServo = reg.get("redstone_servo");
        Item rfCoil = reg.get("rf_coil");

        result = reg.get("area_radius_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', ItemTagsCoFH.GEARS_IRON)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('X', redstoneServo)
                .pattern(" G ")
                .pattern("IXI")
                .pattern(" G ")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(FLAG_AREA_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_output_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', ItemTagsCoFH.GEARS_SILVER)
                .define('S', ItemTagsCoFH.PLATES_SIGNALUM)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("SXS")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(withConditions(consumer).flag(FLAG_DYNAMO_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_fuel_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', ItemTagsCoFH.GEARS_LEAD)
                .define('L', ItemTagsCoFH.PLATES_LUMIUM)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("LXL")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(withConditions(consumer).flag(FLAG_DYNAMO_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_throttle_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('E', ItemTagsCoFH.INGOTS_ELECTRUM)
                .define('l', ItemTagsCoFH.NUGGETS_LEAD)
                .pattern(" l ")
                .pattern("lEl")
                .pattern(" l ")
                .unlockedBy("has_electrum_ingot", has(ItemTagsCoFH.INGOTS_ELECTRUM))
                .save(withConditions(consumer).flag(FLAG_DYNAMO_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        //        result = reg.get("dual_filter_augment");
        //        ShapedRecipeBuilder.shapedRecipe(result)
        //                .key('c', ItemTagsCoFH.NUGGETS_COPPER)
        //                .key('t', ItemTagsCoFH.NUGGETS_TIN)
        //                .key('S', ItemTagsCoFH.INGOTS_SIGNALUM)
        //                .patternLine(" c ")
        //                .patternLine("tSt")
        //                .patternLine(" c ")
        //                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
        //                .build(withConditions(consumer).flag(FLAG_FILTER_AUGMENTS), this.modid + ":" + folder + "/" + name(result));
        //
        //        result = reg.get("fluid_filter_augment");
        //        ShapedRecipeBuilder.shapedRecipe(result)
        //                .key('c', ItemTagsCoFH.NUGGETS_COPPER)
        //                .key('S', ItemTagsCoFH.INGOTS_SIGNALUM)
        //                .patternLine(" c ")
        //                .patternLine("cSc")
        //                .patternLine(" c ")
        //                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
        //                .build(withConditions(consumer).flag(FLAG_FILTER_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("item_filter_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('t', ItemTagsCoFH.NUGGETS_TIN)
                .define('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .pattern(" t ")
                .pattern("tSt")
                .pattern(" t ")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(withConditions(consumer).flag(FLAG_FILTER_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_speed_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('E', ItemTagsCoFH.PLATES_ELECTRUM)
                .define('L', ItemTagsCoFH.GEARS_LEAD)
                .define('X', rfCoil)
                .pattern(" L ")
                .pattern("EXE")
                .pattern(" L ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_efficiency_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('L', ItemTagsCoFH.PLATES_LUMIUM)
                .define('N', ItemTagsCoFH.GEARS_NICKEL)
                .define('X', rfCoil)
                .pattern(" N ")
                .pattern("LXL")
                .pattern(" N ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_output_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('B', ItemTagsCoFH.GEARS_BRONZE)
                .define('I', ItemTagsCoFH.PLATES_INVAR)
                .define('X', redstoneServo)
                .pattern(" B ")
                .pattern("IXI")
                .pattern(" B ")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_catalyst_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('C', ItemTagsCoFH.GEARS_CONSTANTAN)
                .define('L', ItemTagsCoFH.PLATES_LEAD)
                .define('X', redstoneServo)
                .pattern(" C ")
                .pattern("LXL")
                .pattern(" C ")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_cycle_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('C', ItemTagsCoFH.PLATES_CONSTANTAN)
                .define('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('S', ItemTagsCoFH.PLATES_SILVER)
                .define('X', redstoneServo)
                .pattern("SGS")
                .pattern("CXC")
                .pattern("SGS")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_null_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('C', Items.CACTUS)
                .pattern(" i ")
                .pattern("iCi")
                .pattern(" i ")
                .unlockedBy("has_cactus", has(Items.CACTUS))
                .save(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("potion_amplifier_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("IXI")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(withConditions(consumer).flag(FLAG_POTION_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("potion_duration_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', ItemTagsCoFH.GEARS_LUMIUM)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("IXI")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(withConditions(consumer).flag(FLAG_POTION_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .define('X', rfCoil)
                .pattern(" G ")
                .pattern("SXS")
                .pattern(" G ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_storage_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .define('X', rfCoil)
                .pattern(" S ")
                .pattern("GXG")
                .pattern(" G ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_xfer_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .define('X', rfCoil)
                .pattern(" S ")
                .pattern("SXS")
                .pattern(" G ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("fluid_tank_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', ITEMS.get("cured_rubber"))
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern("RIR")
                .pattern("IXI")
                .pattern("RIR")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_1");
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTagsCoFH.INGOTS_INVAR)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('X', ItemTagsCoFH.GEARS_GOLD)
                .pattern("IGI")
                .pattern("RXR")
                .pattern("IGI")
                .unlockedBy("has_invar_ingot", has(ItemTagsCoFH.INGOTS_INVAR))
                .save(withConditions(consumer).flag(FLAG_UPGRADE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_2");
        ShapedRecipeBuilder.shaped(result)
                .define('G', Tags.Items.GEMS_QUARTZ)
                .define('I', ItemTagsCoFH.INGOTS_ELECTRUM)
                .define('R', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('X', reg.get("upgrade_augment_1"))
                .pattern("IGI")
                .pattern("RXR")
                .pattern("IGI")
                .unlockedBy("has_electrum_ingot", has(ItemTagsCoFH.INGOTS_ELECTRUM))
                .save(withConditions(consumer).flag(FLAG_UPGRADE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_3");
        ShapedRecipeBuilder.shaped(result)
                .define('G', ThermalTags.Items.HARDENED_GLASS)
                .define('I', ItemTagsCoFH.INGOTS_ENDERIUM)
                .define('R', ItemTagsCoFH.GEARS_LUMIUM)
                .define('X', reg.get("upgrade_augment_2"))
                .pattern("IGI")
                .pattern("RXR")
                .pattern("IGI")
                .unlockedBy("has_enderium_ingot", has(ItemTagsCoFH.INGOTS_ENDERIUM))
                .save(withConditions(consumer).flag(FLAG_UPGRADE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rs_control_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .pattern(" i ")
                .pattern("iri")
                .pattern(" i ")
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(withConditions(consumer).flag(FLAG_RS_CONTROL_AUGMENT), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("side_config_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('i', ItemTagsCoFH.NUGGETS_TIN)
                .define('G', Tags.Items.INGOTS_GOLD)
                .pattern(" i ")
                .pattern("iGi")
                .pattern(" i ")
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .save(withConditions(consumer).flag(FLAG_SIDE_CONFIG_AUGMENT), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("xp_storage_augment");
        ShapedRecipeBuilder.shaped(result)
                .define('i', Tags.Items.NUGGETS_GOLD)
                .define('C', reg.get("xp_crystal"))
                .pattern(" i ")
                .pattern("iCi")
                .pattern(" i ")
                .unlockedBy("has_crystal", has(reg.get("xp_crystal")))
                .save(withConditions(consumer).flag(FLAG_XP_STORAGE_AUGMENT), this.modid + ":" + folder + "/" + name(result));
    }

    private void generateBasicRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;
        String folder = "tools";
        Item result;

        result = reg.get(ID_WRENCH);
        ShapedRecipeBuilder.shaped(result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', ItemTagsCoFH.GEARS_IRON)
                .pattern("I I")
                .pattern(" G ")
                .pattern(" I ")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(withConditions(consumer).flag(ID_WRENCH), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_REDPRINT);
        ShapelessRecipeBuilder.shapeless(result)
                .requires(Items.PAPER)
                .requires(Items.PAPER)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(withConditions(consumer).flag(ID_REDPRINT), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_LOCK);
        ShapedRecipeBuilder.shaped(result)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .pattern(" i ")
                .pattern("iSi")
                .pattern("iii")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(withConditions(consumer).flag(ID_LOCK), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_SATCHEL);
        ShapedRecipeBuilder.shaped(result)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('L', Tags.Items.LEATHER)
                .define('W', ThermalTags.Items.ROCKWOOL)
                .pattern("LWL")
                .pattern("WIW")
                .pattern("LWL")
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(withConditions(consumer).flag(ID_SATCHEL), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DETONATOR);
        ShapedRecipeBuilder.shaped(result)
                .define('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .pattern(" S ")
                .pattern("IGI")
                .pattern("III")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(withConditions(consumer).flag(ID_DETONATOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_RF_POTATO);
        ShapedRecipeBuilder.shaped(result)
                .define('D', Tags.Items.DUSTS_REDSTONE)
                .define('L', ItemTagsCoFH.NUGGETS_LEAD)
                .define('P', Tags.Items.CROPS_POTATO)
                .define('R', reg.get("cured_rubber"))
                .pattern("LDL")
                .pattern("RPR")
                .pattern("DLD")
                .unlockedBy("has_potato", has(Tags.Items.CROPS_POTATO))
                .save(withConditions(consumer).flag(ID_RF_POTATO), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_XP_CRYSTAL);
        ShapedRecipeBuilder.shaped(result)
                .define('B', Items.EXPERIENCE_BOTTLE)
                .define('E', Tags.Items.GEMS_EMERALD)
                .define('L', Tags.Items.GEMS_LAPIS)
                .pattern(" L ")
                .pattern("EBE")
                .pattern(" L ")
                .unlockedBy("has_experience_bottle", has(Items.EXPERIENCE_BOTTLE))
                .save(withConditions(consumer).flag(ID_XP_CRYSTAL), this.modid + ":" + folder + "/" + name(result));

        ShapelessRecipeBuilder.shapeless(reg.get("phytogro"), 8)
                .requires(Tags.Items.SAND)
                .requires(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .requires(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(consumer, ID_THERMAL + ":phytogro_8");

        ShapelessRecipeBuilder.shapeless(reg.get("phytogro"), 4)
                .requires(Tags.Items.SAND)
                .requires(Items.BONE_MEAL)
                .requires(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(consumer, ID_THERMAL + ":phytogro_4");

        ShapelessRecipeBuilder.shapeless(reg.get("phytogro"), 2)
                .requires(Tags.Items.SAND)
                .requires(Items.BONE_MEAL)
                .requires(reg.get("rich_slag"))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .unlockedBy("rich_slag", has(reg.get("rich_slag")))
                .save(consumer, ID_THERMAL + ":phytogro_2");

        ShapedRecipeBuilder.shaped(reg.get("junk_net"), 1)
                .define('#', Tags.Items.STRING)
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('S', Items.STICK)
                .pattern("n#n")
                .pattern("#S#")
                .pattern("n#n")
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .save(withConditions(consumer).flag(ID_DEVICE_FISHER));

        ShapelessRecipeBuilder.shapeless(reg.get("aquachow"), 4)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.SLIME_BALL)
                .unlockedBy("has_wheat", has(Tags.Items.CROPS_WHEAT))
                .save(withConditions(consumer).flag(ID_DEVICE_FISHER), ID_THERMAL + ":aquachow_4");

        ShapelessRecipeBuilder.shapeless(reg.get("deep_aquachow"), 4)
                .requires(Items.WHEAT)
                .requires(Items.BEETROOT)
                .requires(Items.SLIME_BALL)
                .requires(ItemTagsCoFH.NUGGETS_LEAD)
                .unlockedBy("has_beetroot", has(Tags.Items.CROPS_BEETROOT))
                .save(withConditions(consumer).flag(ID_DEVICE_FISHER), ID_THERMAL + ":deep_aquachow_4");

        ShapelessRecipeBuilder.shapeless(reg.get("basalz_powder"), 2)
                .requires(reg.get("basalz_rod"))
                .unlockedBy("has_basalz_rod", has(reg.get("basalz_rod")))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(reg.get("blitz_powder"), 2)
                .requires(reg.get("blitz_rod"))
                .unlockedBy("has_blitz_rod", has(reg.get("blitz_rod")))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(reg.get("blizz_powder"), 2)
                .requires(reg.get("blizz_rod"))
                .unlockedBy("has_blizz_rod", has(reg.get("blizz_rod")))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE)
                .requires(ItemTagsCoFH.GEMS_APATITE)
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(consumer, ID_THERMAL + ":cyan_dye_from_apatite");

        ShapelessRecipeBuilder.shapeless(reg.get("rubber"), 3)
                .requires(reg.get("latex_bucket"))
                .unlockedBy("latex_bucket", has(reg.get("latex_bucket")))
                .save(consumer, ID_THERMAL + ":rubber_3");

        ShapedRecipeBuilder.shaped(reg.get("rubber"), 1)
                .define('B', Items.WATER_BUCKET)
                .define('#', Items.DANDELION)
                .pattern("###")
                .pattern("#B#")
                .pattern("###")
                .unlockedBy("has_dandelion", has(Items.DANDELION))
                .save(consumer, ID_THERMAL + ":rubber_from_dandelion");

        ShapedRecipeBuilder.shaped(reg.get("rubber"), 1)
                .define('B', Items.WATER_BUCKET)
                .define('#', Items.VINE)
                .pattern("###")
                .pattern("#B#")
                .pattern("###")
                .unlockedBy("has_vine", has(Items.VINE))
                .save(consumer, ID_THERMAL + ":rubber_from_vine");

        ShapelessRecipeBuilder.shapeless(Blocks.DIRT)
                .requires(reg.get("compost"))
                .requires(Tags.Items.SAND)
                .requires(ThermalTags.Items.SLAG)
                .unlockedBy("has_compost", has(reg.get("compost")))
                .save(consumer, ID_THERMAL + ":dirt_crafting");

        ShapelessRecipeBuilder.shapeless(Blocks.PODZOL)
                .requires(reg.get("compost"))
                .requires(ItemTags.LEAVES)
                .requires(Tags.Items.SAND)
                .requires(ThermalTags.Items.SLAG)
                .unlockedBy("has_compost", has(reg.get("compost")))
                .save(consumer, ID_THERMAL + ":podzol_crafting");

        ShapelessRecipeBuilder.shapeless(Blocks.MYCELIUM)
                .requires(reg.get("compost"))
                .requires(Tags.Items.MUSHROOMS)
                .requires(Tags.Items.SAND)
                .requires(ThermalTags.Items.SLAG)
                .unlockedBy("has_compost", has(reg.get("compost")))
                .save(consumer, ID_THERMAL + ":mycelium_crafting");
    }

    private void generateChargeRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        Item earthCharge = reg.get("earth_charge");
        Item iceCharge = reg.get("ice_charge");
        Item lightningCharge = reg.get("lightning_charge");

        ShapelessRecipeBuilder.shapeless(earthCharge, 3)
                .requires(Tags.Items.GUNPOWDER)
                .requires(reg.get("basalz_powder"))
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy("has_basalz_powder", has(reg.get("basalz_powder")))
                .save(consumer, ID_THERMAL + ":earth_charge_3");

        ShapelessRecipeBuilder.shapeless(iceCharge, 3)
                .requires(Tags.Items.GUNPOWDER)
                .requires(reg.get("blizz_powder"))
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy("has_blizz_powder", has(reg.get("blizz_powder")))
                .save(consumer, ID_THERMAL + ":ice_charge_3");

        ShapelessRecipeBuilder.shapeless(lightningCharge, 3)
                .requires(Tags.Items.GUNPOWDER)
                .requires(reg.get("blitz_powder"))
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy("has_blitz_powder", has(reg.get("blitz_powder")))
                .save(consumer, ID_THERMAL + ":lightning_charge_3");

        // region EARTH CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(Items.PRISMARINE_SHARD, 4)
                .requires(Items.PRISMARINE)
                .requires(earthCharge)
                .unlockedBy("has_prismarine", has(Items.PRISMARINE))
                .save(consumer, ID_THERMAL + ":earth_charge/prismarine_shard_from_prismarine");

        ShapelessRecipeBuilder.shapeless(Items.PRISMARINE_SHARD, 9)
                .requires(Items.PRISMARINE_BRICKS)
                .requires(earthCharge)
                .unlockedBy("has_prismarine_bricks", has(Items.PRISMARINE_BRICKS))
                .save(consumer, ID_THERMAL + ":earth_charge/prismarine_shard_from_prismarine_bricks");

        ShapelessRecipeBuilder.shapeless(Items.QUARTZ, 4)
                .requires(Items.QUARTZ_BLOCK)
                .requires(earthCharge)
                .unlockedBy("has_quartz_block", has(Items.QUARTZ_BLOCK))
                .save(consumer, ID_THERMAL + ":earth_charge/quartz_from_quartz_block");

        ShapelessRecipeBuilder.shapeless(reg.get("diamond_dust"))
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(earthCharge)
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer, ID_THERMAL + ":earth_charge/diamond_dust_from_diamond");

        ShapelessRecipeBuilder.shapeless(reg.get("emerald_dust"))
                .requires(Tags.Items.GEMS_EMERALD)
                .requires(earthCharge)
                .unlockedBy("has_emerald", has(Tags.Items.GEMS_EMERALD))
                .save(consumer, ID_THERMAL + ":earth_charge/emerald_dust_from_emerald");

        ShapelessRecipeBuilder.shapeless(reg.get("ender_pearl_dust"))
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(earthCharge)
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(consumer, ID_THERMAL + ":earth_charge/ender_pearl_dust_from_ender_pearl");

        ShapelessRecipeBuilder.shapeless(reg.get("lapis_dust"))
                .requires(Tags.Items.GEMS_LAPIS)
                .requires(earthCharge)
                .unlockedBy("has_lapis", has(Tags.Items.GEMS_LAPIS))
                .save(consumer, ID_THERMAL + ":earth_charge/lapis_dust_from_lapis");

        ShapelessRecipeBuilder.shapeless(reg.get("quartz_dust"))
                .requires(Tags.Items.GEMS_QUARTZ)
                .requires(earthCharge)
                .unlockedBy("has_quartz", has(Tags.Items.GEMS_QUARTZ))
                .save(consumer, ID_THERMAL + ":earth_charge/quartz_dust_from_quartz");
        // endregion

        // region ICE CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(Items.OBSIDIAN)
                .requires(Items.LAVA_BUCKET)
                .requires(iceCharge)
                .unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
                .save(consumer, ID_THERMAL + ":ice_charge/obsidian_from_lava_bucket");

        ShapelessRecipeBuilder.shapeless(Items.ICE)
                .requires(Items.WATER_BUCKET)
                .requires(iceCharge)
                .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
                .save(consumer, ID_THERMAL + ":ice_charge/ice_from_water_bucket");
        // endregion

        // region LIGHTNING CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(Items.WITCH_SPAWN_EGG)
                .requires(Items.VILLAGER_SPAWN_EGG)
                .requires(lightningCharge)
                .unlockedBy("has_villager_spawn_egg", has(Items.VILLAGER_SPAWN_EGG))
                .save(consumer, ID_THERMAL + ":lightning_charge/witch_from_villager");

        ShapelessRecipeBuilder.shapeless(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG)
                .requires(Items.PIG_SPAWN_EGG)
                .requires(lightningCharge)
                .unlockedBy("has_pig_spawn_egg", has(Items.PIG_SPAWN_EGG))
                .save(consumer, ID_THERMAL + ":lightning_charge/zombified_piglin_from_pig");
        // endregion
    }

    private void generateComponentRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        ShapedRecipeBuilder.shaped(reg.get("redstone_servo"))
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern(" R ")
                .pattern(" I ")
                .pattern(" R ")
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(reg.get("rf_coil"))
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("  R")
                .pattern(" I ")
                .pattern("R  ")
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(reg.get("drill_head"))
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern(" I ")
                .pattern("ICI")
                .pattern("III")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(withConditions(consumer).flag(FLAG_TOOL_COMPONENTS));

        ShapedRecipeBuilder.shaped(reg.get("saw_blade"))
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("II ")
                .pattern("ICI")
                .pattern(" II")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(withConditions(consumer).flag(FLAG_TOOL_COMPONENTS));

        ShapedRecipeBuilder.shaped(reg.get(ID_MACHINE_FRAME))
                .define('G', Tags.Items.GLASS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('T', ItemTagsCoFH.GEARS_TIN)
                .pattern("IGI")
                .pattern("GTG")
                .pattern("IGI")
                .unlockedBy("has_tin", has(ItemTagsCoFH.INGOTS_TIN))
                .save(withConditions(consumer).flag(ID_MACHINE_FRAME));

        ShapedRecipeBuilder.shaped(reg.get(ID_ENERGY_CELL_FRAME))
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTagsCoFH.INGOTS_LEAD)
                .define('E', ItemTagsCoFH.GEARS_ELECTRUM)
                .pattern("IGI")
                .pattern("GEG")
                .pattern("IGI")
                .unlockedBy("has_lead", has(ItemTagsCoFH.INGOTS_LEAD))
                .save(withConditions(consumer).flag(ID_ENERGY_CELL_FRAME));

        ShapedRecipeBuilder.shaped(reg.get(ID_FLUID_CELL_FRAME))
                .define('G', Tags.Items.GLASS)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('E', ItemTagsCoFH.GEARS_BRONZE)
                .pattern("IGI")
                .pattern("GEG")
                .pattern("IGI")
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .save(withConditions(consumer).flag(ID_FLUID_CELL_FRAME));

        //        ShapedRecipeBuilder.shaped(reg.get(ID_ITEM_CELL_FRAME))
        //                .define('G', Tags.Items.GLASS)
        //                .define('I', ItemTagsCoFH.INGOTS_TIN)
        //                .define('E', ItemTagsCoFH.GEARS_IRON)
        //                .pattern("IGI")
        //                .pattern("GEG")
        //                .pattern("IGI")
        //                .unlockedBy("has_copper", has(ItemTagsCoFH.INGOTS_TIN))
        //                .save(withConditions(consumer).flag(ID_ITEM_CELL_FRAME));
    }

    private void generateExplosiveRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        ShapelessRecipeBuilder.shapeless(Items.GUNPOWDER, 4)
                .requires(Items.CHARCOAL)
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .requires(fromTags(ItemTagsCoFH.GEMS_SULFUR, ItemTagsCoFH.DUSTS_SULFUR))
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .save(consumer, ID_THERMAL + ":gunpowder_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_EXPLOSIVE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.SAND)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDER))
                .save(consumer, ID_THERMAL + ":explosive_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_SLIME_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.SLIMEBALLS)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_slimeball", has(Tags.Items.SLIMEBALLS))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":slime_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_REDSTONE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.DUSTS_REDSTONE)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":redstone_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_GLOWSTONE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.DUSTS_GLOWSTONE)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":glowstone_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_ENDER_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.ENDER_PEARLS)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":ender_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_PHYTO_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("phytogro"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_phytogro", has(reg.get("phytogro")))
                .save(withConditions(consumer).flag(FLAG_PHYTOGRO_EXPLOSIVES), ID_THERMAL + ":phyto_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_EARTH_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("basalz_powder"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_basalz_powder", has(reg.get("basalz_powder")))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":earth_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_FIRE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Items.BLAZE_POWDER)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":fire_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_ICE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("blizz_powder"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_blizz_powder", has(reg.get("blizz_powder")))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":ice_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_LIGHTNING_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("blitz_powder"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_blitz_powder", has(reg.get("blitz_powder")))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":lightning_grenade_4");

        ShapedRecipeBuilder.shaped(reg.get(ID_SLIME_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Tags.Items.SLIMEBALLS)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_slimeball", has(Tags.Items.SLIMEBALLS))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_REDSTONE_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Tags.Items.DUSTS_REDSTONE)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_GLOWSTONE_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Tags.Items.DUSTS_GLOWSTONE)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_ENDER_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Tags.Items.ENDER_PEARLS)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_PHYTO_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', reg.get("phytogro"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_phytogro", has(reg.get("phytogro")))
                .save(withConditions(consumer).flag(FLAG_PHYTOGRO_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_EARTH_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', reg.get("basalz_powder"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_basalz_powder", has(reg.get("basalz_powder")))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_FIRE_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Items.BLAZE_POWDER)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_ICE_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', reg.get("blizz_powder"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_blizz_powder", has(reg.get("blizz_powder")))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));

        ShapedRecipeBuilder.shaped(reg.get(ID_LIGHTNING_TNT))
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', reg.get("blitz_powder"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_blitz_powder", has(reg.get("blitz_powder")))
                .save(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));
    }

    private void generateRockwoolRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        Item rockwool = reg.get(ID_WHITE_ROCKWOOL);
        String folder = "rockwool";
        String recipeId;
        Item result;

        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("slag"), rockwool, 0.1F, "rockwool");

        //        ShapelessRecipeBuilder.shapelessRecipe(reg.get(ID_WHITE_ROCKWOOL))
        //                .addIngredient(rockwool)
        //                .addIngredient(Tags.Items.DYES_WHITE)
        //                .addCriterion("has_" + path(rockwool), hasItem(rockwool))
        //                .build(consumer);

        result = reg.get(ID_ORANGE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_ORANGE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_MAGENTA_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_MAGENTA)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_LIGHT_BLUE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_YELLOW_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_LIME_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_LIME)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_PINK_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_PINK)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_GRAY_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_GRAY)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_LIGHT_GRAY_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_LIGHT_GRAY)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_CYAN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_CYAN)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_PURPLE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_PURPLE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_BLUE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_BROWN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_BROWN)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_GREEN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_GREEN)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_RED_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);

        result = reg.get(ID_BLACK_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_BLACK)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(consumer, recipeId);
    }

    private void generateSlagRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        generateSmeltingRecipe(reg, consumer, reg.get(ID_SLAG_BLOCK), reg.get(ID_POLISHED_SLAG), 0.1F, "smelting");
        generateSmeltingRecipe(reg, consumer, reg.get(ID_RICH_SLAG_BLOCK), reg.get(ID_POLISHED_RICH_SLAG), 0.1F, "smelting");

        generateSmeltingRecipe(reg, consumer, reg.get(ID_SLAG_BRICKS), reg.get(ID_CRACKED_SLAG_BRICKS), 0.1F, "smelting");
        generateSmeltingRecipe(reg, consumer, reg.get(ID_RICH_SLAG_BRICKS), reg.get(ID_CRACKED_RICH_SLAG_BRICKS), 0.1F, "smelting");

        ShapedRecipeBuilder.shaped(reg.get(ID_SLAG_BRICKS), 4)
                .define('#', reg.get(ID_POLISHED_SLAG))
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_slag", has(reg.get("slag")))
                .save(consumer);

        ShapedRecipeBuilder.shaped(reg.get(ID_RICH_SLAG_BRICKS), 4)
                .define('#', reg.get(ID_POLISHED_RICH_SLAG))
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_rich_slag", has(reg.get("rich_slag")))
                .save(consumer);

        generateStonecuttingRecipe(reg, consumer, reg.get(ID_POLISHED_SLAG), reg.get(ID_CHISELED_SLAG), "stonecutting");
        generateStonecuttingRecipe(reg, consumer, reg.get(ID_POLISHED_RICH_SLAG), reg.get(ID_CHISELED_RICH_SLAG), "stonecutting");
    }

    private void generateTileRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        Item energyCellFrame = reg.get(ID_ENERGY_CELL_FRAME);
        Item fluidCellFrame = reg.get(ID_FLUID_CELL_FRAME);
        // Item itemCellFrame = reg.get(ID_ITEM_CELL_FRAME);
        Item redstoneServo = reg.get("redstone_servo");
        Item rfCoil = reg.get("rf_coil");

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_HIVE_EXTRACTOR))
                .define('C', Items.SHEARS)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_HIVE_EXTRACTOR));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_TREE_EXTRACTOR))
                .define('C', Items.BUCKET)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_TREE_EXTRACTOR));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_FISHER))
                .define('C', Items.FISHING_ROD)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_COPPER)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_FISHER));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_COMPOSTER))
                .define('C', Blocks.COMPOSTER)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_COMPOSTER));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_SOIL_INFUSER))
                .define('C', reg.get("phytogro"))
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTags.PLANKS)
                .define('P', rfCoil)
                .define('X', ItemTagsCoFH.GEARS_LUMIUM)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(ID_DEVICE_SOIL_INFUSER));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_ROCK_GEN))
                .define('C', Items.PISTON)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTagsCoFH.INGOTS_INVAR)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_CONSTANTAN)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_ROCK_GEN));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_WATER_GEN))
                .define('C', Items.BUCKET)
                .define('G', Tags.Items.GLASS)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('P', redstoneServo)
                .define('X', Tags.Items.INGOTS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_WATER_GEN));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_COLLECTOR))
                .define('C', Items.HOPPER)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('P', redstoneServo)
                .define('X', Tags.Items.ENDER_PEARLS)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_COLLECTOR));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_NULLIFIER))
                .define('C', Items.LAVA_BUCKET)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('P', redstoneServo)
                .define('X', Tags.Items.DUSTS_REDSTONE)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_NULLIFIER));

        ShapedRecipeBuilder.shaped(reg.get(ID_DEVICE_POTION_DIFFUSER))
                .define('C', Items.GLASS_BOTTLE)
                .define('G', Tags.Items.GLASS)
                .define('I', ItemTagsCoFH.INGOTS_SILVER)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_CONSTANTAN)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(withConditions(consumer).flag(ID_DEVICE_POTION_DIFFUSER));

        ShapedRecipeBuilder.shaped(reg.get(ID_ENERGY_CELL))
                .define('C', energyCellFrame)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', rfCoil)
                .define('R', reg.get("cured_rubber"))
                .define('X', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .pattern("RXR")
                .pattern("ICI")
                .pattern("RPR")
                .unlockedBy("has_energy_cell_frame", has(energyCellFrame))
                .save(withConditions(consumer).flag(ID_ENERGY_CELL));

        ShapedRecipeBuilder.shaped(reg.get(ID_FLUID_CELL))
                .define('C', fluidCellFrame)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', redstoneServo)
                .define('R', reg.get("cured_rubber"))
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern("RXR")
                .pattern("ICI")
                .pattern("RPR")
                .unlockedBy("has_fluid_cell_frame", has(fluidCellFrame))
                .save(withConditions(consumer).flag(ID_FLUID_CELL));

        //        ShapedRecipeBuilder.shaped(reg.get(ID_ITEM_CELL))
        //                .define('C', itemCellFrame)
        //                .define('I', Tags.Items.INGOTS_IRON)
        //                .define('P', redstoneServo)
        //                .define('R', reg.get("cured_rubber"))
        //                .define('X', Items.CHEST)
        //                .pattern("RXR")
        //                .pattern("ICI")
        //                .pattern("RPR")
        //                .unlockedBy("has_item_cell_frame", has(itemCellFrame))
        //                .save(withConditions(consumer).flag(ID_ITEM_CELL));

        ShapedRecipeBuilder.shaped(reg.get(ID_TINKER_BENCH))
                .define('C', Blocks.CRAFTING_TABLE)
                .define('G', Tags.Items.GLASS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', rfCoil)
                .define('X', ItemTags.PLANKS)
                .pattern("III")
                .pattern("GCG")
                .pattern("XPX")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(ID_TINKER_BENCH));

        ShapedRecipeBuilder.shaped(reg.get(ID_CHARGE_BENCH))
                .define('C', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('I', ItemTagsCoFH.INGOTS_ELECTRUM)
                .define('P', rfCoil)
                .define('X', ItemTagsCoFH.INGOTS_LEAD)
                .pattern("III")
                .pattern("PCP")
                .pattern("XPX")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(withConditions(consumer).flag(ID_CHARGE_BENCH));
    }
    // endregion
}
