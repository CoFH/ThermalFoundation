package cofh.thermal.core.util.managers.machine;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class SawmillRecipeManager extends SingleItemRecipeManager {

    private static final SawmillRecipeManager INSTANCE = new SawmillRecipeManager();
    protected static final int DEFAULT_ENERGY = 2000;

    public static SawmillRecipeManager instance() {

        return INSTANCE;
    }

    private SawmillRecipeManager() {

        super(DEFAULT_ENERGY, 4, 0);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.getRecipes(TCoreRecipeTypes.RECIPE_SAWMILL);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion
}
