package cofh.thermal.core.data;

import cofh.lib.data.RecipeProviderCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import cofh.lib.util.references.ItemTagsCoFH;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreIDs.*;
import static cofh.thermal.lib.common.ThermalFlags.*;

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
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_CHARCOAL_BLOCK), Items.CHARCOAL);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_BAMBOO_BLOCK), Items.BAMBOO);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_SUGAR_CANE_BLOCK), Items.SUGAR_CANE);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_GUNPOWDER_BLOCK), Items.GUNPOWDER);

        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_APPLE_BLOCK), Items.APPLE);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_BEETROOT_BLOCK), Items.BEETROOT);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_CARROT_BLOCK), Items.CARROT);
        generateStorageRecipes(withConditions(consumer).flag(FLAG_VANILLA_BLOCKS), reg.get(ID_POTATO_BLOCK), Items.POTATO);

        generateStorageRecipes(consumer, reg.get(ID_APATITE_BLOCK), reg.get("apatite"));
        generateStorageRecipes(consumer, reg.get(ID_CINNABAR_BLOCK), reg.get("cinnabar"));
        generateStorageRecipes(consumer, reg.get(ID_NITER_BLOCK), reg.get("niter"));
        generateStorageRecipes(consumer, reg.get(ID_SULFUR_BLOCK), reg.get("sulfur"));

        generateTypeRecipes(reg, consumer, "copper");
        generateTypeRecipes(reg, consumer, "tin");
        generateTypeRecipes(reg, consumer, "lead");
        generateTypeRecipes(reg, consumer, "silver");
        generateTypeRecipes(reg, consumer, "nickel");

        generateTypeRecipes(reg, consumer, "bronze");
        generateTypeRecipes(reg, consumer, "electrum");
        generateTypeRecipes(reg, consumer, "invar");
        generateTypeRecipes(reg, consumer, "constantan");

        generateSmeltingAndBlastingRecipes(reg, consumer, "copper", 0.6F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "tin", 0.6F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "lead", 0.8F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "silver", 1.0F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "nickel", 1.0F);

        generateSmeltingAndBlastingRecipes(reg, consumer, "bronze", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "electrum", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "invar", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "constantan", 0);

        generateGearRecipe(consumer, reg.get("iron_gear"), Items.IRON_INGOT, forgeTag("ingots/iron"));
        generateGearRecipe(consumer, reg.get("gold_gear"), Items.GOLD_INGOT, forgeTag("ingots/gold"));

        generateGearRecipe(consumer, reg.get("diamond_gear"), Items.IRON_INGOT, forgeTag("gems/diamond"));
        generateGearRecipe(consumer, reg.get("emerald_gear"), Items.IRON_INGOT, forgeTag("gems/emerald"));
        generateGearRecipe(consumer, reg.get("quartz_gear"), Items.IRON_INGOT, forgeTag("gems/quartz"));
        generateGearRecipe(consumer, reg.get("lapis_gear"), Items.IRON_INGOT, forgeTag("gems/lapis"));

        generateStorageRecipes(consumer, reg.get(ID_SAWDUST_BLOCK), reg.get("sawdust"));
        generateStorageRecipes(consumer, reg.get(ID_COAL_COKE_BLOCK), reg.get("coal_coke"));
        generateStorageRecipes(consumer, reg.get(ID_BITUMEN_BLOCK), reg.get("bitumen"));
        generateStorageRecipes(consumer, reg.get(ID_TAR_BLOCK), reg.get("tar"));
        generateStorageRecipes(consumer, reg.get(ID_ROSIN_BLOCK), reg.get("rosin"));
        generateStorageRecipes(consumer, reg.get(ID_RUBBER_BLOCK), reg.get("rubber"));
        generateStorageRecipes(consumer, reg.get(ID_CURED_RUBBER_BLOCK), reg.get("cured_rubber"));
        generateStorageRecipes(consumer, reg.get(ID_SLAG_BLOCK), reg.get("slag"));
        generateStorageRecipes(consumer, reg.get(ID_RICH_SLAG_BLOCK), reg.get("rich_slag"));

        generateTypeRecipes(reg, consumer, "signalum");
        generateTypeRecipes(reg, consumer, "lumium");
        generateTypeRecipes(reg, consumer, "enderium");

        generateSmeltingAndBlastingRecipes(reg, consumer, "signalum", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "lumium", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "enderium", 0);

        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("iron_dust"), Items.IRON_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get("gold_dust"), Items.GOLD_INGOT, 0.0F, "smelting", "_dust");

        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_APATITE_ORE), reg.get("apatite"), 0.5F, "smelting");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_CINNABAR_ORE), reg.get("cinnabar"), 0.5F, "smelting");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_NITER_ORE), reg.get("niter"), 0.5F, "smelting");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_SULFUR_ORE), reg.get("sulfur"), 0.5F, "smelting");

        generateSmeltingRecipe(reg, consumer, reg.get("rubber"), reg.get("cured_rubber"), 0.2F, "smelting");

        generateAlloyRecipes(consumer);
        generateArmorRecipes(consumer);
        generateAugmentRecipes(consumer);
        generateBasicRecipes(consumer);
        generateChargeRecipes(consumer);
        generateComponentRecipes(consumer);
        generateExplosiveRecipes(consumer);
        generateRockwoolRecipes(withConditions(consumer).flag(FLAG_ROCKWOOL));
        generateTileRecipes(consumer);
    }

    // region HELPERS
    private void generateAlloyRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("bronze_dust"), 4)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_TIN)
                .addCriterion("has_copper_dust", hasItem(ItemTagsCoFH.DUSTS_COPPER))
                .addCriterion("has_tin_dust", hasItem(ItemTagsCoFH.DUSTS_TIN))
                .build(consumer, ID_THERMAL + ":bronze_dust_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("electrum_dust"), 2)
                .addIngredient(ItemTagsCoFH.DUSTS_GOLD)
                .addIngredient(ItemTagsCoFH.DUSTS_SILVER)
                .addCriterion("has_gold_dust", hasItem(ItemTagsCoFH.DUSTS_GOLD))
                .addCriterion("has_silver_dust", hasItem(ItemTagsCoFH.DUSTS_SILVER))
                .build(consumer, ID_THERMAL + ":electrum_dust_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("invar_dust"), 3)
                .addIngredient(ItemTagsCoFH.DUSTS_IRON)
                .addIngredient(ItemTagsCoFH.DUSTS_IRON)
                .addIngredient(ItemTagsCoFH.DUSTS_NICKEL)
                .addCriterion("has_iron_dust", hasItem(ItemTagsCoFH.DUSTS_IRON))
                .addCriterion("has_nickel_dust", hasItem(ItemTagsCoFH.DUSTS_NICKEL))
                .build(consumer, ID_THERMAL + ":invar_dust_3");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("constantan_dust"), 2)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_NICKEL)
                .addCriterion("has_copper_dust", hasItem(ItemTagsCoFH.DUSTS_COPPER))
                .addCriterion("has_nickel_dust", hasItem(ItemTagsCoFH.DUSTS_NICKEL))
                .build(consumer, ID_THERMAL + ":constantan_dust_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("signalum_dust"), 4)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_COPPER)
                .addIngredient(ItemTagsCoFH.DUSTS_SILVER)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone_dust", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer, ID_THERMAL + ":signalum_dust_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("lumium_dust"), 4)
                .addIngredient(ItemTagsCoFH.DUSTS_TIN)
                .addIngredient(ItemTagsCoFH.DUSTS_TIN)
                .addIngredient(ItemTagsCoFH.DUSTS_TIN)
                .addIngredient(ItemTagsCoFH.DUSTS_SILVER)
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                .addCriterion("has_glowstone_dust", hasItem(Tags.Items.DUSTS_GLOWSTONE))
                .build(consumer, ID_THERMAL + ":lumium_dust_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("enderium_dust"), 2)
                .addIngredient(ItemTagsCoFH.DUSTS_LEAD)
                .addIngredient(ItemTagsCoFH.DUSTS_LEAD)
                .addIngredient(ItemTagsCoFH.DUSTS_LEAD)
                .addIngredient(ItemTagsCoFH.DUSTS_DIAMOND)
                .addIngredient(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .addIngredient(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .addCriterion("has_ender_pearl", hasItem(Tags.Items.ENDER_PEARLS))
                .build(consumer, ID_THERMAL + ":enderium_dust_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("bronze_ingot"), 4)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_copper_dust", hasItem(ItemTagsCoFH.DUSTS_COPPER))
                .addCriterion("has_copper_ingot", hasItem(ItemTagsCoFH.INGOTS_COPPER))
                .addCriterion("has_tin_dust", hasItem(ItemTagsCoFH.DUSTS_TIN))
                .addCriterion("has_tin_ingot", hasItem(ItemTagsCoFH.INGOTS_TIN))
                .build(consumer, ID_THERMAL + ":fire_charge/bronze_ingot_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("electrum_ingot"), 2)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_GOLD, Tags.Items.INGOTS_GOLD))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_gold_dust", hasItem(ItemTagsCoFH.DUSTS_GOLD))
                .addCriterion("has_gold_ingot", hasItem(Tags.Items.INGOTS_GOLD))
                .addCriterion("has_silver_dust", hasItem(ItemTagsCoFH.DUSTS_SILVER))
                .addCriterion("has_silver_ingot", hasItem(ItemTagsCoFH.INGOTS_SILVER))
                .build(consumer, ID_THERMAL + ":fire_charge/electrum_ingot_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("invar_ingot"), 3)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_IRON, Tags.Items.INGOTS_IRON))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_IRON, Tags.Items.INGOTS_IRON))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_NICKEL, ItemTagsCoFH.INGOTS_NICKEL))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_iron_dust", hasItem(ItemTagsCoFH.DUSTS_IRON))
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_nickel_dust", hasItem(ItemTagsCoFH.DUSTS_NICKEL))
                .addCriterion("has_nickel_ingot", hasItem(ItemTagsCoFH.INGOTS_NICKEL))
                .build(consumer, ID_THERMAL + ":fire_charge/invar_ingot_3");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("constantan_ingot"), 2)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_NICKEL, ItemTagsCoFH.INGOTS_NICKEL))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_copper_dust", hasItem(ItemTagsCoFH.DUSTS_COPPER))
                .addCriterion("has_copper_ingot", hasItem(ItemTagsCoFH.INGOTS_COPPER))
                .addCriterion("has_nickel_dust", hasItem(ItemTagsCoFH.DUSTS_NICKEL))
                .addCriterion("has_nickel_ingot", hasItem(ItemTagsCoFH.INGOTS_NICKEL))
                .build(consumer, ID_THERMAL + ":fire_charge/constantan_ingot_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("signalum_ingot"), 4)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_COPPER, ItemTagsCoFH.INGOTS_COPPER))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_redstone_dust", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer, ID_THERMAL + ":fire_charge/signalum_ingot_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("lumium_ingot"), 4)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_glowstone_dust", hasItem(Tags.Items.DUSTS_GLOWSTONE))
                .build(consumer, ID_THERMAL + ":fire_charge/lumium_ingot_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("enderium_ingot"), 2)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .addIngredient(ItemTagsCoFH.DUSTS_DIAMOND)
                .addIngredient(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .addIngredient(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_ender_pearl", hasItem(Tags.Items.ENDER_PEARLS))
                .build(consumer, ID_THERMAL + ":fire_charge/enderium_ingot_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("obsidian_glass"), 2)
                .addIngredient(Tags.Items.OBSIDIAN)
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_QUARTZ, Tags.Items.GEMS_QUARTZ))
                .addIngredient(Tags.Items.SAND)
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_obsidian", hasItem(Tags.Items.OBSIDIAN))
                .build(consumer, ID_THERMAL + ":fire_charge/obsidian_glass_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("signalum_glass"), 2)
                .addIngredient(reg.get("obsidian_glass"))
                .addIngredient(reg.get("obsidian_glass"))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_SIGNALUM, ItemTagsCoFH.INGOTS_SIGNALUM))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_signalum_dust", hasItem(ItemTagsCoFH.DUSTS_SIGNALUM))
                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
                .build(consumer, ID_THERMAL + ":fire_charge/signalum_glass_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("lumium_glass"), 2)
                .addIngredient(reg.get("obsidian_glass"))
                .addIngredient(reg.get("obsidian_glass"))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_LUMIUM, ItemTagsCoFH.INGOTS_LUMIUM))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_lumium_dust", hasItem(ItemTagsCoFH.DUSTS_LUMIUM))
                .addCriterion("has_lumium_ingot", hasItem(ItemTagsCoFH.INGOTS_LUMIUM))
                .build(consumer, ID_THERMAL + ":fire_charge/lumium_glass_2");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("enderium_glass"), 2)
                .addIngredient(reg.get("obsidian_glass"))
                .addIngredient(reg.get("obsidian_glass"))
                .addIngredient(fromTags(ItemTagsCoFH.DUSTS_ENDERIUM, ItemTagsCoFH.INGOTS_ENDERIUM))
                .addIngredient(Items.FIRE_CHARGE)
                .addCriterion("has_enderium_dust", hasItem(ItemTagsCoFH.DUSTS_ENDERIUM))
                .addCriterion("has_enderium_ingot", hasItem(ItemTagsCoFH.INGOTS_ENDERIUM))
                .build(consumer, ID_THERMAL + ":fire_charge/enderium_glass_2");
    }

    private void generateArmorRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;
        String folder = "armor";
        Item result;

        Item beekeeperFabric = reg.get("beekeeper_fabric");
        Item divingFabric = reg.get("diving_fabric");
        Item hazmatFabric = reg.get("hazmat_fabric");

        ShapedRecipeBuilder.shapedRecipe(beekeeperFabric)
                .key('S', Tags.Items.STRING)
                .key('H', Items.HONEYCOMB)
                .patternLine(" S ")
                .patternLine("SHS")
                .patternLine(" S ")
                .addCriterion("has_honeycomb", hasItem(Items.HONEYCOMB))
                .build(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR));

        result = reg.get(ID_BEEKEEPER_HELMET);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('X', beekeeperFabric)
                .patternLine("XXX")
                .patternLine("X X")
                .addCriterion("has_fabric", hasItem(beekeeperFabric))
                .build(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_CHESTPLATE);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('X', beekeeperFabric)
                .patternLine("X X")
                .patternLine("XXX")
                .patternLine("XXX")
                .addCriterion("has_fabric", hasItem(beekeeperFabric))
                .build(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_LEGGINGS);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('X', beekeeperFabric)
                .patternLine("XXX")
                .patternLine("X X")
                .patternLine("X X")
                .addCriterion("has_fabric", hasItem(beekeeperFabric))
                .build(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_BOOTS);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('L', Tags.Items.LEATHER)
                .key('X', beekeeperFabric)
                .patternLine("X X")
                .patternLine("L L")
                .addCriterion("has_fabric", hasItem(beekeeperFabric))
                .build(withConditions(consumer).flag(FLAG_BEEKEEPER_ARMOR), this.modid + ":" + folder + "/" + name(result));

        ShapedRecipeBuilder.shapedRecipe(divingFabric)
                .key('S', Tags.Items.STRING)
                .key('H', Tags.Items.GEMS_PRISMARINE)
                .patternLine(" S ")
                .patternLine("SHS")
                .patternLine(" S ")
                .addCriterion("has_prismarine", hasItem(Tags.Items.GEMS_PRISMARINE))
                .build(withConditions(consumer).flag(FLAG_DIVING_ARMOR));

        result = reg.get(ID_DIVING_HELMET);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.GLASS_PANES)
                .key('I', Tags.Items.INGOTS_GOLD)
                .key('X', divingFabric)
                .patternLine("XIX")
                .patternLine("IGI")
                .addCriterion("has_fabric", hasItem(divingFabric))
                .build(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_CHESTPLATE);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('I', Tags.Items.INGOTS_GOLD)
                .key('X', divingFabric)
                .patternLine("X X")
                .patternLine("IXI")
                .patternLine("XXX")
                .addCriterion("has_fabric", hasItem(divingFabric))
                .build(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_LEGGINGS);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('I', Tags.Items.INGOTS_GOLD)
                .key('X', divingFabric)
                .patternLine("XXX")
                .patternLine("I I")
                .patternLine("X X")
                .addCriterion("has_fabric", hasItem(divingFabric))
                .build(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_BOOTS);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('L', Tags.Items.LEATHER)
                .key('I', Tags.Items.INGOTS_GOLD)
                .key('X', divingFabric)
                .patternLine("X X")
                .patternLine("LIL")
                .addCriterion("has_fabric", hasItem(divingFabric))
                .build(withConditions(consumer).flag(FLAG_DIVING_ARMOR), this.modid + ":" + folder + "/" + name(result));

        ShapedRecipeBuilder.shapedRecipe(hazmatFabric)
                .key('S', Tags.Items.STRING)
                .key('H', reg.get("cured_rubber"))
                .patternLine(" S ")
                .patternLine("SHS")
                .patternLine(" S ")
                .addCriterion("has_cured_rubber", hasItem(reg.get("cured_rubber")))
                .build(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR));

        result = reg.get(ID_HAZMAT_HELMET);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.GLASS_PANES)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('X', hazmatFabric)
                .patternLine("XIX")
                .patternLine("IGI")
                .addCriterion("has_fabric", hasItem(hazmatFabric))
                .build(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_CHESTPLATE);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('X', hazmatFabric)
                .patternLine("X X")
                .patternLine("IXI")
                .patternLine("XXX")
                .addCriterion("has_fabric", hasItem(hazmatFabric))
                .build(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_LEGGINGS);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('X', hazmatFabric)
                .patternLine("XXX")
                .patternLine("I I")
                .patternLine("X X")
                .addCriterion("has_fabric", hasItem(hazmatFabric))
                .build(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_BOOTS);
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('L', Tags.Items.LEATHER)
                .key('R', reg.get("cured_rubber"))
                .key('X', hazmatFabric)
                .patternLine("X X")
                .patternLine("LRL")
                .addCriterion("has_fabric", hasItem(hazmatFabric))
                .build(withConditions(consumer).flag(FLAG_HAZMAT_ARMOR), this.modid + ":" + folder + "/" + name(result));
    }

    private void generateAugmentRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;
        String folder = "augments";
        Item result;

        Item redstoneServo = reg.get("redstone_servo");
        Item rfCoil = reg.get("rf_coil");

        result = reg.get("area_radius_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.GEARS_IRON)
                .key('I', ItemTagsCoFH.INGOTS_TIN)
                .key('X', redstoneServo)
                .patternLine(" G ")
                .patternLine("IXI")
                .patternLine(" G ")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(FLAG_AREA_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_output_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.GEARS_SILVER)
                .key('S', ItemTagsCoFH.PLATES_SIGNALUM)
                .key('X', ItemTagsCoFH.HARDENED_GLASS)
                .patternLine(" G ")
                .patternLine("SXS")
                .patternLine(" G ")
                .addCriterion("has_hardened_glass", hasItem(ItemTagsCoFH.HARDENED_GLASS))
                .build(withConditions(consumer).flag(FLAG_DYNAMO_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_fuel_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.GEARS_LEAD)
                .key('L', ItemTagsCoFH.PLATES_LUMIUM)
                .key('X', ItemTagsCoFH.HARDENED_GLASS)
                .patternLine(" G ")
                .patternLine("LXL")
                .patternLine(" G ")
                .addCriterion("has_hardened_glass", hasItem(ItemTagsCoFH.HARDENED_GLASS))
                .build(withConditions(consumer).flag(FLAG_DYNAMO_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

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
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('t', ItemTagsCoFH.NUGGETS_TIN)
                .key('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .patternLine(" t ")
                .patternLine("tSt")
                .patternLine(" t ")
                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
                .build(withConditions(consumer).flag(FLAG_FILTER_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_speed_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('E', ItemTagsCoFH.PLATES_ELECTRUM)
                .key('L', ItemTagsCoFH.GEARS_LEAD)
                .key('X', rfCoil)
                .patternLine(" L ")
                .patternLine("EXE")
                .patternLine(" L ")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_efficiency_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('L', ItemTagsCoFH.PLATES_LUMIUM)
                .key('N', ItemTagsCoFH.GEARS_NICKEL)
                .key('X', rfCoil)
                .patternLine(" N ")
                .patternLine("LXL")
                .patternLine(" N ")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_output_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('B', ItemTagsCoFH.GEARS_BRONZE)
                .key('I', ItemTagsCoFH.PLATES_INVAR)
                .key('X', redstoneServo)
                .patternLine(" B ")
                .patternLine("IXI")
                .patternLine(" B ")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_catalyst_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('C', ItemTagsCoFH.GEARS_CONSTANTAN)
                .key('L', ItemTagsCoFH.PLATES_LEAD)
                .key('X', redstoneServo)
                .patternLine(" C ")
                .patternLine("LXL")
                .patternLine(" C ")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_cycle_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('C', ItemTagsCoFH.PLATES_CONSTANTAN)
                .key('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .key('S', ItemTagsCoFH.PLATES_SILVER)
                .key('X', redstoneServo)
                .patternLine("SGS")
                .patternLine("CXC")
                .patternLine("SGS")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(FLAG_MACHINE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("potion_amplifier_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .key('I', ItemTagsCoFH.INGOTS_COPPER)
                .key('X', ItemTagsCoFH.HARDENED_GLASS)
                .patternLine(" G ")
                .patternLine("IXI")
                .patternLine(" G ")
                .addCriterion("has_hardened_glass", hasItem(ItemTagsCoFH.HARDENED_GLASS))
                .build(withConditions(consumer).flag(FLAG_POTION_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("potion_duration_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.GEARS_LUMIUM)
                .key('I', ItemTagsCoFH.INGOTS_COPPER)
                .key('X', ItemTagsCoFH.HARDENED_GLASS)
                .patternLine(" G ")
                .patternLine("IXI")
                .patternLine(" G ")
                .addCriterion("has_hardened_glass", hasItem(ItemTagsCoFH.HARDENED_GLASS))
                .build(withConditions(consumer).flag(FLAG_POTION_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.INGOTS_GOLD)
                .key('S', ItemTagsCoFH.INGOTS_SILVER)
                .key('X', rfCoil)
                .patternLine(" G ")
                .patternLine("SXS")
                .patternLine(" G ")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_storage_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.INGOTS_GOLD)
                .key('S', ItemTagsCoFH.INGOTS_SILVER)
                .key('X', rfCoil)
                .patternLine(" S ")
                .patternLine("GXG")
                .patternLine(" G ")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_xfer_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.INGOTS_GOLD)
                .key('S', ItemTagsCoFH.INGOTS_SILVER)
                .key('X', rfCoil)
                .patternLine(" S ")
                .patternLine("SXS")
                .patternLine(" G ")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("fluid_tank_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('R', ITEMS.get("cured_rubber"))
                .key('X', ItemTagsCoFH.HARDENED_GLASS)
                .patternLine("RIR")
                .patternLine("IXI")
                .patternLine("RIR")
                .addCriterion("has_hardened_glass", hasItem(ItemTagsCoFH.HARDENED_GLASS))
                .build(withConditions(consumer).flag(FLAG_STORAGE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_1");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_INVAR)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .key('X', ItemTagsCoFH.GEARS_GOLD)
                .patternLine("IGI")
                .patternLine("RXR")
                .patternLine("IGI")
                .addCriterion("has_invar_ingot", hasItem(ItemTagsCoFH.INGOTS_INVAR))
                .build(withConditions(consumer).flag(FLAG_UPGRADE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_2");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', Tags.Items.GEMS_QUARTZ)
                .key('I', ItemTagsCoFH.INGOTS_ELECTRUM)
                .key('R', ItemTagsCoFH.GEARS_SIGNALUM)
                .key('X', reg.get("upgrade_augment_1"))
                .patternLine("IGI")
                .patternLine("RXR")
                .patternLine("IGI")
                .addCriterion("has_electrum_ingot", hasItem(ItemTagsCoFH.INGOTS_ELECTRUM))
                .build(withConditions(consumer).flag(FLAG_UPGRADE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_3");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.HARDENED_GLASS)
                .key('I', ItemTagsCoFH.INGOTS_ENDERIUM)
                .key('R', ItemTagsCoFH.GEARS_LUMIUM)
                .key('X', reg.get("upgrade_augment_2"))
                .patternLine("IGI")
                .patternLine("RXR")
                .patternLine("IGI")
                .addCriterion("has_enderium_ingot", hasItem(ItemTagsCoFH.INGOTS_ENDERIUM))
                .build(withConditions(consumer).flag(FLAG_UPGRADE_AUGMENTS), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rs_control_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('i', Tags.Items.NUGGETS_IRON)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .patternLine(" i ")
                .patternLine("iri")
                .patternLine(" i ")
                .addCriterion("has_redstone_dust", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(withConditions(consumer).flag("rs_control_augment"), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("side_config_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('i', ItemTagsCoFH.NUGGETS_TIN)
                .key('G', Tags.Items.INGOTS_GOLD)
                .patternLine(" i ")
                .patternLine("iGi")
                .patternLine(" i ")
                .addCriterion("has_gold_ingot", hasItem(Tags.Items.INGOTS_GOLD))
                .build(withConditions(consumer).flag("side_config_augment"), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("xp_storage_augment");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('i', Tags.Items.NUGGETS_GOLD)
                .key('C', reg.get("xp_crystal"))
                .patternLine(" i ")
                .patternLine("iCi")
                .patternLine(" i ")
                .addCriterion("has_crystal", hasItem(reg.get("xp_crystal")))
                .build(withConditions(consumer).flag("xp_storage_augment"), this.modid + ":" + folder + "/" + name(result));
    }

    private void generateBasicRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;
        String folder = "tools";
        Item result;

        result = reg.get("wrench");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('G', ItemTagsCoFH.GEARS_IRON)
                .patternLine("I I")
                .patternLine(" G ")
                .patternLine(" I ")
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer, this.modid + ":" + folder + "/" + name(result));
        //                .key('I', Tags.Items.INGOTS_IRON)
        //                .key('T', CoFHTags.Items.INGOTS_TIN)
        //                .patternLine("I I")
        //                .patternLine(" T ")
        //                .patternLine(" I ")
        //                .addCriterion("has_tin", hasItem(CoFHTags.Items.INGOTS_TIN))
        //                .build(consumer);

        result = reg.get("redprint");
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(Items.PAPER)
                .addIngredient(Items.PAPER)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone_dust", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer, this.modid + ":" + folder + "/" + name(result));

        result = reg.get("lock");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('i', Tags.Items.NUGGETS_IRON)
                .key('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .patternLine(" i ")
                .patternLine("iSi")
                .patternLine("iii")
                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
                .build(consumer, this.modid + ":" + folder + "/" + name(result));

        result = reg.get("detonator");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('S', ItemTagsCoFH.INGOTS_SILVER)
                .patternLine(" S ")
                .patternLine("IGI")
                .patternLine("III")
                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
                .build(consumer, this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_potato");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('D', Tags.Items.DUSTS_REDSTONE)
                .key('L', ItemTagsCoFH.NUGGETS_LEAD)
                .key('P', Tags.Items.CROPS_POTATO)
                .key('R', reg.get("cured_rubber"))
                .patternLine("LDL")
                .patternLine("RPR")
                .patternLine("DLD")
                .addCriterion("has_potato", hasItem(Tags.Items.CROPS_POTATO))
                .build(consumer, this.modid + ":" + folder + "/" + name(result));

        result = reg.get("xp_crystal");
        ShapedRecipeBuilder.shapedRecipe(result)
                .key('B', Items.EXPERIENCE_BOTTLE)
                .key('E', Tags.Items.GEMS_EMERALD)
                .key('L', Tags.Items.GEMS_LAPIS)
                .patternLine(" L ")
                .patternLine("EBE")
                .patternLine(" L ")
                .addCriterion("has_experience_bottle", hasItem(Items.EXPERIENCE_BOTTLE))
                .build(consumer, this.modid + ":" + folder + "/" + name(result));

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("phytogro"), 8)
                .addIngredient(Tags.Items.SAND)
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .addCriterion("has_apatite", hasItem(ItemTagsCoFH.GEMS_APATITE))
                .build(consumer, ID_THERMAL + ":phytogro_8");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("phytogro"), 4)
                .addIngredient(Tags.Items.SAND)
                .addIngredient(Items.BONE_MEAL)
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .addCriterion("has_apatite", hasItem(ItemTagsCoFH.GEMS_APATITE))
                .build(consumer, ID_THERMAL + ":phytogro_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("phytogro"), 2)
                .addIngredient(Tags.Items.SAND)
                .addIngredient(Items.BONE_MEAL)
                .addIngredient(reg.get("rich_slag"))
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .addCriterion("rich_slag", hasItem(reg.get("rich_slag")))
                .build(consumer, ID_THERMAL + ":phytogro_2");

        ShapedRecipeBuilder.shapedRecipe(reg.get("junk_net"), 1)
                .key('#', Tags.Items.STRING)
                .key('n', Tags.Items.NUGGETS_IRON)
                .key('S', Items.STICK)
                .patternLine("n#n")
                .patternLine("#S#")
                .patternLine("n#n")
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .build(withConditions(consumer).flag(ID_DEVICE_FISHER));

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("aquachow"), 4)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.SLIME_BALL)
                .addCriterion("has_wheat", hasItem(Tags.Items.CROPS_WHEAT))
                .build(withConditions(consumer).flag(ID_DEVICE_FISHER), ID_THERMAL + ":aquachow_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("deep_aquachow"), 4)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.SLIME_BALL)
                .addIngredient(ItemTagsCoFH.NUGGETS_LEAD)
                .addCriterion("has_beetroot", hasItem(Tags.Items.CROPS_BEETROOT))
                .build(withConditions(consumer).flag(ID_DEVICE_FISHER), ID_THERMAL + ":deep_aquachow_4");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("basalz_powder"), 2)
                .addIngredient(reg.get("basalz_rod"))
                .addCriterion("has_basalz_rod", hasItem(reg.get("basalz_rod")))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("blitz_powder"), 2)
                .addIngredient(reg.get("blitz_rod"))
                .addCriterion("has_blitz_rod", hasItem(reg.get("blitz_rod")))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("blizz_powder"), 2)
                .addIngredient(reg.get("blizz_rod"))
                .addCriterion("has_blizz_rod", hasItem(reg.get("blizz_rod")))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Items.CYAN_DYE)
                .addIngredient(ItemTagsCoFH.GEMS_APATITE)
                .addCriterion("has_apatite", hasItem(ItemTagsCoFH.GEMS_APATITE))
                .build(consumer, ID_THERMAL + ":cyan_dye_from_apatite");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("rubber"), 3)
                .addIngredient(reg.get("latex_bucket"))
                .addCriterion("latex_bucket", hasItem(reg.get("latex_bucket")))
                .build(consumer, ID_THERMAL + ":rubber_3");

        ShapedRecipeBuilder.shapedRecipe(reg.get("rubber"), 1)
                .key('B', Items.WATER_BUCKET)
                .key('#', Items.DANDELION)
                .patternLine("###")
                .patternLine("#B#")
                .patternLine("###")
                .addCriterion("has_dandelion", hasItem(Items.DANDELION))
                .build(consumer, ID_THERMAL + ":rubber_from_dandelion");

        ShapedRecipeBuilder.shapedRecipe(reg.get("rubber"), 1)
                .key('B', Items.WATER_BUCKET)
                .key('#', Items.VINE)
                .patternLine("###")
                .patternLine("#B#")
                .patternLine("###")
                .addCriterion("has_vine", hasItem(Items.VINE))
                .build(consumer, ID_THERMAL + ":rubber_from_vine");
    }

    private void generateChargeRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

        Item earthCharge = reg.get("earth_charge");
        Item iceCharge = reg.get("ice_charge");
        Item lightningCharge = reg.get("lightning_charge");

        ShapelessRecipeBuilder.shapelessRecipe(earthCharge, 3)
                .addIngredient(Tags.Items.GUNPOWDER)
                .addIngredient(reg.get("basalz_powder"))
                .addIngredient(Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
                .addCriterion("has_basalz_powder", hasItem(reg.get("basalz_powder")))
                .build(consumer, ID_THERMAL + ":earth_charge_3");

        ShapelessRecipeBuilder.shapelessRecipe(iceCharge, 3)
                .addIngredient(Tags.Items.GUNPOWDER)
                .addIngredient(reg.get("blizz_powder"))
                .addIngredient(Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
                .addCriterion("has_blizz_powder", hasItem(reg.get("blizz_powder")))
                .build(consumer, ID_THERMAL + ":ice_charge_3");

        ShapelessRecipeBuilder.shapelessRecipe(lightningCharge, 3)
                .addIngredient(Tags.Items.GUNPOWDER)
                .addIngredient(reg.get("blitz_powder"))
                .addIngredient(Ingredient.fromItems(Items.COAL, Items.CHARCOAL))
                .addCriterion("has_blitz_powder", hasItem(reg.get("blitz_powder")))
                .build(consumer, ID_THERMAL + ":lightning_charge_3");

        // region EARTH CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapelessRecipe(Items.PRISMARINE_SHARD, 4)
                .addIngredient(Items.PRISMARINE)
                .addIngredient(earthCharge)
                .addCriterion("has_prismarine", hasItem(Items.PRISMARINE))
                .build(consumer, ID_THERMAL + ":earth_charge/prismarine_shard_from_prismarine");

        ShapelessRecipeBuilder.shapelessRecipe(Items.PRISMARINE_SHARD, 9)
                .addIngredient(Items.PRISMARINE_BRICKS)
                .addIngredient(earthCharge)
                .addCriterion("has_prismarine_bricks", hasItem(Items.PRISMARINE_BRICKS))
                .build(consumer, ID_THERMAL + ":earth_charge/prismarine_shard_from_prismarine_bricks");

        ShapelessRecipeBuilder.shapelessRecipe(Items.QUARTZ, 4)
                .addIngredient(Items.QUARTZ_BLOCK)
                .addIngredient(earthCharge)
                .addCriterion("has_quartz_block", hasItem(Items.QUARTZ_BLOCK))
                .build(consumer, ID_THERMAL + ":earth_charge/quartz_from_quartz_block");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("diamond_dust"))
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(earthCharge)
                .addCriterion("has_diamond", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(consumer, ID_THERMAL + ":earth_charge/diamond_dust_from_diamond");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("emerald_dust"))
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addIngredient(earthCharge)
                .addCriterion("has_emerald", hasItem(Tags.Items.GEMS_EMERALD))
                .build(consumer, ID_THERMAL + ":earth_charge/emerald_dust_from_emerald");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("ender_pearl_dust"))
                .addIngredient(Tags.Items.ENDER_PEARLS)
                .addIngredient(earthCharge)
                .addCriterion("has_ender_pearl", hasItem(Tags.Items.ENDER_PEARLS))
                .build(consumer, ID_THERMAL + ":earth_charge_ender_pearl_dust_from_ender_pearl");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("lapis_dust"))
                .addIngredient(Tags.Items.GEMS_LAPIS)
                .addIngredient(earthCharge)
                .addCriterion("has_lapis", hasItem(Tags.Items.GEMS_LAPIS))
                .build(consumer, ID_THERMAL + ":earth_charge/lapis_dust_from_lapis");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("quartz_dust"))
                .addIngredient(Tags.Items.GEMS_QUARTZ)
                .addIngredient(earthCharge)
                .addCriterion("has_quartz", hasItem(Tags.Items.GEMS_QUARTZ))
                .build(consumer, ID_THERMAL + ":earth_charge/quartz_dust_from_quartz");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("apatite_dust"))
                .addIngredient(ItemTagsCoFH.GEMS_APATITE)
                .addIngredient(earthCharge)
                .addCriterion("has_apatite", hasItem(ItemTagsCoFH.GEMS_APATITE))
                .build(consumer, ID_THERMAL + ":earth_charge/apatite_dust_from_apatite");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("cinnabar_dust"))
                .addIngredient(ItemTagsCoFH.GEMS_CINNABAR)
                .addIngredient(earthCharge)
                .addCriterion("has_cinnabar", hasItem(ItemTagsCoFH.GEMS_CINNABAR))
                .build(consumer, ID_THERMAL + ":earth_charge/cinnabar_dust_from_cinnabar");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("niter_dust"))
                .addIngredient(ItemTagsCoFH.GEMS_NITER)
                .addIngredient(earthCharge)
                .addCriterion("has_niter", hasItem(ItemTagsCoFH.GEMS_NITER))
                .build(consumer, ID_THERMAL + ":earth_charge/niter_dust_from_niter");

        ShapelessRecipeBuilder.shapelessRecipe(reg.get("sulfur_dust"))
                .addIngredient(ItemTagsCoFH.GEMS_SULFUR)
                .addIngredient(earthCharge)
                .addCriterion("has_sulfur", hasItem(ItemTagsCoFH.GEMS_SULFUR))
                .build(consumer, ID_THERMAL + ":earth_charge/sulfur_dust_from_sulfur");
        // endregion

        // region ICE CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapelessRecipe(Items.OBSIDIAN)
                .addIngredient(Items.LAVA_BUCKET)
                .addIngredient(iceCharge)
                .addCriterion("has_lava_bucket", hasItem(Items.LAVA_BUCKET))
                .build(consumer, ID_THERMAL + ":ice_charge/obsidian_from_lava_bucket");

        ShapelessRecipeBuilder.shapelessRecipe(Items.ICE)
                .addIngredient(Items.WATER_BUCKET)
                .addIngredient(iceCharge)
                .addCriterion("has_water_bucket", hasItem(Items.WATER_BUCKET))
                .build(consumer, ID_THERMAL + ":ice_charge/ice_from_water_bucket");
        // endregion

        // region LIGHTNING CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapelessRecipe(Items.WITCH_SPAWN_EGG)
                .addIngredient(Items.VILLAGER_SPAWN_EGG)
                .addIngredient(lightningCharge)
                .addCriterion("has_villager_spawn_egg", hasItem(Items.VILLAGER_SPAWN_EGG))
                .build(consumer, ID_THERMAL + ":lightning_charge/witch_from_villager");

        ShapelessRecipeBuilder.shapelessRecipe(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG)
                .addIngredient(Items.PIG_SPAWN_EGG)
                .addIngredient(lightningCharge)
                .addCriterion("has_pig_spawn_egg", hasItem(Items.PIG_SPAWN_EGG))
                .build(consumer, ID_THERMAL + ":lightning_charge/zombified_piglin_from_pig");
        // endregion
    }

    private void generateComponentRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

        ShapedRecipeBuilder.shapedRecipe(reg.get("redstone_servo"))
                .key('I', Tags.Items.INGOTS_IRON)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .patternLine(" R ")
                .patternLine(" I ")
                .patternLine(" R ")
                .addCriterion("has_redstone_dust", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(reg.get("rf_coil"))
                .key('I', Tags.Items.INGOTS_GOLD)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .patternLine("  R")
                .patternLine(" I ")
                .patternLine("R  ")
                .addCriterion("has_redstone_dust", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(reg.get("drill_head"))
                .key('C', ItemTagsCoFH.INGOTS_COPPER)
                .key('I', Tags.Items.INGOTS_IRON)
                .patternLine(" I ")
                .patternLine("ICI")
                .patternLine("III")
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(withConditions(consumer).flag(FLAG_TOOL_COMPONENTS));

        ShapedRecipeBuilder.shapedRecipe(reg.get("saw_blade"))
                .key('C', ItemTagsCoFH.INGOTS_COPPER)
                .key('I', Tags.Items.INGOTS_IRON)
                .patternLine("II ")
                .patternLine("ICI")
                .patternLine(" II")
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(withConditions(consumer).flag(FLAG_TOOL_COMPONENTS));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_MACHINE_FRAME))
                .key('G', Tags.Items.GLASS)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('T', ItemTagsCoFH.GEARS_TIN)
                .patternLine("IGI")
                .patternLine("GTG")
                .patternLine("IGI")
                .addCriterion("has_tin", hasItem(ItemTagsCoFH.INGOTS_TIN))
                .build(withConditions(consumer).flag(ID_MACHINE_FRAME));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_ENERGY_CELL_FRAME))
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_LEAD)
                .key('E', ItemTagsCoFH.GEARS_ELECTRUM)
                .patternLine("IGI")
                .patternLine("GEG")
                .patternLine("IGI")
                .addCriterion("has_lead", hasItem(ItemTagsCoFH.INGOTS_LEAD))
                .build(withConditions(consumer).flag(ID_ENERGY_CELL_FRAME));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_FLUID_CELL_FRAME))
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_COPPER)
                .key('E', ItemTagsCoFH.GEARS_BRONZE)
                .patternLine("IGI")
                .patternLine("GEG")
                .patternLine("IGI")
                .addCriterion("has_copper", hasItem(ItemTagsCoFH.INGOTS_COPPER))
                .build(withConditions(consumer).flag(ID_FLUID_CELL_FRAME));
    }

    private void generateExplosiveRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

        ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER, 4)
                .addIngredient(Items.CHARCOAL)
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .addIngredient(fromTags(ItemTagsCoFH.GEMS_SULFUR, ItemTagsCoFH.DUSTS_SULFUR))
                .addCriterion("has_gunpowder", hasItem(Tags.Items.GUNPOWDER))
                .build(consumer, ID_THERMAL + ":gunpowder_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("explosive_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Tags.Items.SAND)
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_gunpowder", hasItem(Tags.Items.GUNPOWDER))
                .build(consumer, ID_THERMAL + ":explosive_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("slime_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Tags.Items.SLIMEBALLS)
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_slimeball", hasItem(Tags.Items.SLIMEBALLS))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":slime_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("redstone_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Tags.Items.DUSTS_REDSTONE)
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_redstone", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":redstone_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("glowstone_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Tags.Items.DUSTS_GLOWSTONE)
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_glowstone", hasItem(Tags.Items.DUSTS_GLOWSTONE))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":glowstone_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("ender_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Tags.Items.ENDER_PEARLS)
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_ender_pearl", hasItem(Tags.Items.ENDER_PEARLS))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES), ID_THERMAL + ":ender_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("phyto_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', reg.get("phytogro"))
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_phytogro", hasItem(reg.get("phytogro")))
                .build(withConditions(consumer).flag(FLAG_PHYTOGRO_EXPLOSIVES), ID_THERMAL + ":phyto_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("earth_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', reg.get("basalz_powder"))
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_basalz_powder", hasItem(reg.get("basalz_powder")))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":earth_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("fire_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', Items.BLAZE_POWDER)
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_blaze_powder", hasItem(Items.BLAZE_POWDER))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":fire_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("ice_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', reg.get("blizz_powder"))
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_blizz_powder", hasItem(reg.get("blizz_powder")))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":ice_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("lightning_grenade"), 4)
                .key('G', Tags.Items.GUNPOWDER)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', reg.get("blitz_powder"))
                .patternLine("GPG")
                .patternLine("PIP")
                .patternLine("GPG")
                .addCriterion("has_blitz_powder", hasItem(reg.get("blitz_powder")))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES), ID_THERMAL + ":lightning_grenade_4");

        ShapedRecipeBuilder.shapedRecipe(reg.get("slime_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', Tags.Items.SLIMEBALLS)
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_slimeball", hasItem(Tags.Items.SLIMEBALLS))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("redstone_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', Tags.Items.DUSTS_REDSTONE)
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_redstone", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("glowstone_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', Tags.Items.DUSTS_GLOWSTONE)
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_glowstone", hasItem(Tags.Items.DUSTS_GLOWSTONE))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("ender_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', Tags.Items.ENDER_PEARLS)
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_ender_pearl", hasItem(Tags.Items.ENDER_PEARLS))
                .build(withConditions(consumer).flag(FLAG_BASIC_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("phyto_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', reg.get("phytogro"))
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_phytogro", hasItem(reg.get("phytogro")))
                .build(withConditions(consumer).flag(FLAG_PHYTOGRO_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("earth_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', reg.get("basalz_powder"))
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_basalz_powder", hasItem(reg.get("basalz_powder")))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("fire_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', Items.BLAZE_POWDER)
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_blaze_powder", hasItem(Items.BLAZE_POWDER))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("ice_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', reg.get("blizz_powder"))
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_blizz_powder", hasItem(reg.get("blizz_powder")))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));

        ShapedRecipeBuilder.shapedRecipe(reg.get("lightning_tnt"))
                .key('G', Tags.Items.GUNPOWDER)
                .key('P', reg.get("blitz_powder"))
                .patternLine("GPG")
                .patternLine("PGP")
                .patternLine("GPG")
                .addCriterion("has_blitz_powder", hasItem(reg.get("blitz_powder")))
                .build(withConditions(consumer).flag(FLAG_ELEMENTAL_EXPLOSIVES));
    }

    private void generateRockwoolRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

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
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_ORANGE)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_MAGENTA_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_MAGENTA)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_LIGHT_BLUE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_LIGHT_BLUE)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_YELLOW_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_YELLOW)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_LIME_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_LIME)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_PINK_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_PINK)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_GRAY_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_GRAY)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_LIGHT_GRAY_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_LIGHT_GRAY)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_CYAN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_CYAN)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_PURPLE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_PURPLE)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_BLUE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_BLUE)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_BROWN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_BROWN)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_GREEN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_GREEN)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_RED_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_RED)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);

        result = reg.get(ID_BLACK_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(rockwool)
                .addIngredient(Tags.Items.DYES_BLACK)
                .addCriterion("has_" + name(rockwool), hasItem(rockwool))
                .build(consumer, recipeId);
    }

    private void generateTileRecipes(Consumer<IFinishedRecipe> consumer) {

        DeferredRegisterCoFH<Item> reg = ITEMS;

        Item energyCellFrame = reg.get(ID_ENERGY_CELL_FRAME);
        Item fluidCellFrame = reg.get(ID_FLUID_CELL_FRAME);
        Item redstoneServo = reg.get("redstone_servo");
        Item rfCoil = reg.get("rf_coil");

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_HIVE_EXTRACTOR))
                .key('C', Items.SHEARS)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTags.PLANKS)
                .key('P', redstoneServo)
                .key('X', ItemTagsCoFH.GEARS_IRON)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_HIVE_EXTRACTOR));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_TREE_EXTRACTOR))
                .key('C', Items.BUCKET)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTags.PLANKS)
                .key('P', redstoneServo)
                .key('X', ItemTagsCoFH.GEARS_IRON)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_TREE_EXTRACTOR));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_FISHER))
                .key('C', Items.FISHING_ROD)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTags.PLANKS)
                .key('P', redstoneServo)
                .key('X', ItemTagsCoFH.GEARS_COPPER)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_FISHER));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_SOIL_INFUSER))
                .key('C', reg.get("phytogro"))
                .key('G', Tags.Items.GLASS)
                .key('P', rfCoil)
                .key('X', ItemTagsCoFH.GEARS_LUMIUM)
                .key('W', ItemTags.PLANKS)
                .patternLine("WXW")
                .patternLine("GCG")
                .patternLine("WPW")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(ID_DEVICE_SOIL_INFUSER));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_ROCK_GEN))
                .key('C', Items.PISTON)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_INVAR)
                .key('P', redstoneServo)
                .key('X', ItemTagsCoFH.GEARS_CONSTANTAN)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_ROCK_GEN));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_WATER_GEN))
                .key('C', Items.BUCKET)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_COPPER)
                .key('P', redstoneServo)
                .key('X', Tags.Items.INGOTS_IRON)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_WATER_GEN));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_COLLECTOR))
                .key('C', Items.HOPPER)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_TIN)
                .key('P', redstoneServo)
                .key('X', Tags.Items.ENDER_PEARLS)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_COLLECTOR));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_NULLIFIER))
                .key('C', Items.LAVA_BUCKET)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_TIN)
                .key('P', redstoneServo)
                .key('X', Tags.Items.DUSTS_REDSTONE)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_NULLIFIER));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_DEVICE_POTION_DIFFUSER))
                .key('C', Items.GLASS_BOTTLE)
                .key('G', Tags.Items.GLASS)
                .key('I', ItemTagsCoFH.INGOTS_SILVER)
                .key('P', redstoneServo)
                .key('X', ItemTagsCoFH.GEARS_CONSTANTAN)
                .patternLine("IXI")
                .patternLine("GCG")
                .patternLine("IPI")
                .addCriterion("has_redstone_servo", hasItem(redstoneServo))
                .build(withConditions(consumer).flag(ID_DEVICE_POTION_DIFFUSER));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_ENERGY_CELL))
                .key('C', energyCellFrame)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', rfCoil)
                .key('R', reg.get("cured_rubber"))
                .key('X', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .patternLine("RXR")
                .patternLine("ICI")
                .patternLine("RPR")
                .addCriterion("has_energy_cell_frame", hasItem(energyCellFrame))
                .build(withConditions(consumer).flag(ID_ENERGY_CELL));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_FLUID_CELL))
                .key('C', fluidCellFrame)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', redstoneServo)
                .key('R', reg.get("cured_rubber"))
                .key('X', ItemTagsCoFH.HARDENED_GLASS)
                .patternLine("RXR")
                .patternLine("ICI")
                .patternLine("RPR")
                .addCriterion("has_fluid_cell_frame", hasItem(fluidCellFrame))
                .build(withConditions(consumer).flag(ID_FLUID_CELL));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_TINKER_BENCH))
                .key('C', Blocks.CRAFTING_TABLE)
                .key('G', Tags.Items.GLASS)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('P', rfCoil)
                .key('X', ItemTags.PLANKS)
                .patternLine("III")
                .patternLine("GCG")
                .patternLine("XPX")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(ID_TINKER_BENCH));

        ShapedRecipeBuilder.shapedRecipe(reg.get(ID_CHARGE_BENCH))
                .key('C', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .key('I', ItemTagsCoFH.INGOTS_ELECTRUM)
                .key('P', rfCoil)
                .key('X', ItemTagsCoFH.INGOTS_LEAD)
                .patternLine("III")
                .patternLine("PCP")
                .patternLine("XPX")
                .addCriterion("has_rf_coil", hasItem(rfCoil))
                .build(withConditions(consumer).flag(ID_CHARGE_BENCH));
    }
    // endregion
}
