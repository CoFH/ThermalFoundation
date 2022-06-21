package cofh.thermal.foundation.data;

import cofh.lib.data.RecipeProviderCoFH;
import cofh.lib.tags.ItemTagsCoFH;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TFndRecipeProvider extends RecipeProviderCoFH {

    public TFndRecipeProvider(DataGenerator generatorIn) {

        super(generatorIn, ID_THERMAL);
        manager = ThermalFlags.manager();
    }

    @Override
    public String getName() {

        return "Thermal Foundation: Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        generateStorageRecipes(consumer, reg.get(ID_APATITE_BLOCK), reg.get("apatite"));
        generateStorageRecipes(consumer, reg.get(ID_CINNABAR_BLOCK), reg.get("cinnabar"));
        generateStorageRecipes(consumer, reg.get(ID_NITER_BLOCK), reg.get("niter"));
        generateStorageRecipes(consumer, reg.get(ID_SULFUR_BLOCK), reg.get("sulfur"));

        generateTypeRecipes(reg, consumer, "lead");
        generateTypeRecipes(reg, consumer, "nickel");
        generateTypeRecipes(reg, consumer, "silver");
        generateTypeRecipes(reg, consumer, "tin");

        generateTypeRecipes(reg, consumer, "bronze");
        generateTypeRecipes(reg, consumer, "constantan");
        generateTypeRecipes(reg, consumer, "electrum");
        generateTypeRecipes(reg, consumer, "invar");

        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_APATITE_ORE), reg.get("apatite"), 0.5F, "smelting");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_CINNABAR_ORE), reg.get("cinnabar"), 0.5F, "smelting");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_NITER_ORE), reg.get("niter"), 0.5F, "smelting");
        generateSmeltingAndBlastingRecipes(reg, consumer, reg.get(ID_SULFUR_ORE), reg.get("sulfur"), 0.5F, "smelting");

        generateSmeltingAndBlastingRecipes(reg, consumer, "lead", 0.8F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "nickel", 1.0F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "silver", 1.0F);
        generateSmeltingAndBlastingRecipes(reg, consumer, "tin", 0.6F);

        generateSmeltingAndBlastingRecipes(reg, consumer, "bronze", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "constantan", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "electrum", 0);
        generateSmeltingAndBlastingRecipes(reg, consumer, "invar", 0);

        generateAlloyRecipes(consumer);
        generateChargeRecipes(consumer);
    }

    // region HELPERS
    private void generateAlloyRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        ShapelessRecipeBuilder.shapeless(reg.get("bronze_dust"), 4)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .unlockedBy("has_copper_dust", has(ItemTagsCoFH.DUSTS_COPPER))
                .unlockedBy("has_tin_dust", has(ItemTagsCoFH.DUSTS_TIN))
                .save(consumer, ID_THERMAL + ":bronze_dust_4");

        ShapelessRecipeBuilder.shapeless(reg.get("constantan_dust"), 2)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_NICKEL)
                .unlockedBy("has_copper_dust", has(ItemTagsCoFH.DUSTS_COPPER))
                .unlockedBy("has_nickel_dust", has(ItemTagsCoFH.DUSTS_NICKEL))
                .save(consumer, ID_THERMAL + ":constantan_dust_2");

        ShapelessRecipeBuilder.shapeless(reg.get("electrum_dust"), 2)
                .requires(ItemTagsCoFH.DUSTS_GOLD)
                .requires(ItemTagsCoFH.DUSTS_SILVER)
                .unlockedBy("has_gold_dust", has(ItemTagsCoFH.DUSTS_GOLD))
                .unlockedBy("has_silver_dust", has(ItemTagsCoFH.DUSTS_SILVER))
                .save(consumer, ID_THERMAL + ":electrum_dust_2");

        ShapelessRecipeBuilder.shapeless(reg.get("invar_dust"), 3)
                .requires(ItemTagsCoFH.DUSTS_IRON)
                .requires(ItemTagsCoFH.DUSTS_IRON)
                .requires(ItemTagsCoFH.DUSTS_NICKEL)
                .unlockedBy("has_iron_dust", has(ItemTagsCoFH.DUSTS_IRON))
                .unlockedBy("has_nickel_dust", has(ItemTagsCoFH.DUSTS_NICKEL))
                .save(consumer, ID_THERMAL + ":invar_dust_3");

        ShapelessRecipeBuilder.shapeless(reg.get("bronze_ingot"), 4)
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_copper_dust", has(ItemTagsCoFH.DUSTS_COPPER))
                .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_tin_dust", has(ItemTagsCoFH.DUSTS_TIN))
                .unlockedBy("has_tin_ingot", has(ItemTagsCoFH.INGOTS_TIN))
                .save(consumer, ID_THERMAL + ":fire_charge/bronze_ingot_4");

        ShapelessRecipeBuilder.shapeless(reg.get("constantan_ingot"), 2)
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_NICKEL, ItemTagsCoFH.INGOTS_NICKEL))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_copper_dust", has(ItemTagsCoFH.DUSTS_COPPER))
                .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_nickel_dust", has(ItemTagsCoFH.DUSTS_NICKEL))
                .unlockedBy("has_nickel_ingot", has(ItemTagsCoFH.INGOTS_NICKEL))
                .save(consumer, ID_THERMAL + ":fire_charge/constantan_ingot_2");

        ShapelessRecipeBuilder.shapeless(reg.get("electrum_ingot"), 2)
                .requires(fromTags(ItemTagsCoFH.DUSTS_GOLD, Tags.Items.INGOTS_GOLD))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_gold_dust", has(ItemTagsCoFH.DUSTS_GOLD))
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_silver_dust", has(ItemTagsCoFH.DUSTS_SILVER))
                .unlockedBy("has_silver_ingot", has(ItemTagsCoFH.INGOTS_SILVER))
                .save(consumer, ID_THERMAL + ":fire_charge/electrum_ingot_2");

        ShapelessRecipeBuilder.shapeless(reg.get("invar_ingot"), 3)
                .requires(fromTags(ItemTagsCoFH.DUSTS_IRON, Tags.Items.INGOTS_IRON))
                .requires(fromTags(ItemTagsCoFH.DUSTS_IRON, Tags.Items.INGOTS_IRON))
                .requires(fromTags(ItemTagsCoFH.DUSTS_NICKEL, ItemTagsCoFH.INGOTS_NICKEL))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_iron_dust", has(ItemTagsCoFH.DUSTS_IRON))
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_nickel_dust", has(ItemTagsCoFH.DUSTS_NICKEL))
                .unlockedBy("has_nickel_ingot", has(ItemTagsCoFH.INGOTS_NICKEL))
                .save(consumer, ID_THERMAL + ":fire_charge/invar_ingot_3");
    }

    private void generateChargeRecipes(Consumer<FinishedRecipe> consumer) {

        var reg = ITEMS;

        Item earthCharge = reg.get("earth_charge");

        // region EARTH CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(reg.get("apatite_dust"))
                .requires(ItemTagsCoFH.GEMS_APATITE)
                .requires(earthCharge)
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(consumer, ID_THERMAL + ":earth_charge/apatite_dust_from_apatite");

        ShapelessRecipeBuilder.shapeless(reg.get("cinnabar_dust"))
                .requires(ItemTagsCoFH.GEMS_CINNABAR)
                .requires(earthCharge)
                .unlockedBy("has_cinnabar", has(ItemTagsCoFH.GEMS_CINNABAR))
                .save(consumer, ID_THERMAL + ":earth_charge/cinnabar_dust_from_cinnabar");

        ShapelessRecipeBuilder.shapeless(reg.get("niter_dust"))
                .requires(ItemTagsCoFH.GEMS_NITER)
                .requires(earthCharge)
                .unlockedBy("has_niter", has(ItemTagsCoFH.GEMS_NITER))
                .save(consumer, ID_THERMAL + ":earth_charge/niter_dust_from_niter");

        ShapelessRecipeBuilder.shapeless(reg.get("sulfur_dust"))
                .requires(ItemTagsCoFH.GEMS_SULFUR)
                .requires(earthCharge)
                .unlockedBy("has_sulfur", has(ItemTagsCoFH.GEMS_SULFUR))
                .save(consumer, ID_THERMAL + ":earth_charge/sulfur_dust_from_sulfur");
        // endregion
    }
    // endregion
}
