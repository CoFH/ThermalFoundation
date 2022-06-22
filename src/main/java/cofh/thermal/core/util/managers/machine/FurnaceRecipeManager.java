package cofh.thermal.core.util.managers.machine;

import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.machine.FurnaceRecipe;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.TCoreRecipeTypes.RECIPE_FURNACE;

public class FurnaceRecipeManager extends SingleItemRecipeManager {

    private static final FurnaceRecipeManager INSTANCE = new FurnaceRecipeManager();
    protected static final int DEFAULT_ENERGY = 2000;

    protected static boolean defaultFurnaceRecipes = true;
    protected static boolean defaultFoodRecipes = true;

    public static FurnaceRecipeManager instance() {

        return INSTANCE;
    }

    private FurnaceRecipeManager() {

        super(DEFAULT_ENERGY, 1, 0);
    }

    @Override
    protected void clear() {

        recipeMap.clear();
        convertedRecipes.clear();
    }

    // region IManager
    @Override
    public void config() {

        //        String category = "Machines.Furnace";
        //        String comment;
        //
        //        comment = "Adjust this value to change the default energy value for this machine's recipes.";
        //        int defaultEnergy = config.getInt("Default Energy", category, DEFAULT_ENERGY, DEFAULT_ENERGY_MIN, DEFAULT_ENERGY_MAX, comment);
        //
        //        comment = "Adjust this value to change the relative energy cost of all of this machine's recipes. Scale is in percentage.";
        //        int scaleFactor = config.getInt("Energy Factor", category, DEFAULT_SCALE, DEFAULT_SCALE_MIN, DEFAULT_SCALE_MAX, comment);
        //
        //        comment = "If TRUE, default Minecraft Furnace recipes will be available for this machine.";
        //        defaultFurnaceRecipes = config.getBoolean("Default Furnace Recipes", category, defaultFurnaceRecipes, comment);
        //
        //        comment = "If TRUE, reduced cost Dust -> Ingot recipes will be created.";
        //        defaultDustRecipes = config.getBoolean("Reduced Dust -> Ingot Cost", category, defaultDustRecipes, comment);
        //
        //        comment = "If TRUE, reduced cost Food recipes will be created.";
        //        defaultFoodRecipes = config.getBoolean("Reduced Food Cost", category, defaultFoodRecipes, comment);
        //
        //        setDefaultEnergy(defaultEnergy);
        //        setScaleFactor(scaleFactor);
    }

    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        if (defaultFurnaceRecipes) {
            ThermalCore.LOG.debug("Adding default Furnace recipes to the Redstone Furnace...");
            createConvertedRecipes(recipeManager);
            for (ThermalRecipe recipe : getConvertedRecipes()) {
                addRecipe(recipe);
            }
        }
        var recipes = recipeManager.byType(RECIPE_FURNACE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion

    // region CONVERSION
    protected List<FurnaceRecipe> convertedRecipes = new ArrayList<>();

    public List<FurnaceRecipe> getConvertedRecipes() {

        return convertedRecipes;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        for (Recipe<Container> recipe : recipeManager.byType(RecipeType.SMELTING).values()) {
            createConvertedRecipe((AbstractCookingRecipe) recipe);
        }
    }

    protected boolean createConvertedRecipe(AbstractCookingRecipe recipe) {

        if (recipe.isSpecial() || recipe.getResultItem().isEmpty()) {
            return false;
        }
        convertedRecipes.add(convert(recipe));
        return true;
    }

    protected FurnaceRecipe convert(AbstractCookingRecipe recipe) {

        ItemStack recipeOutput = recipe.getResultItem();
        float experience = recipe.getExperience();
        int energy = defaultFoodRecipes && recipeOutput.getItem().isEdible() ? defaultEnergy / 2 : defaultEnergy;
        return new FurnaceRecipe(new ResourceLocation(ID_THERMAL, "furnace_" + recipe.getIngredients().get(0).hashCode()), energy, experience, recipe);
    }
    // endregion
}
