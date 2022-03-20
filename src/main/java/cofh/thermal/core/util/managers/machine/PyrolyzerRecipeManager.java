package cofh.thermal.core.util.managers.machine;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Map;

public class PyrolyzerRecipeManager extends SingleItemRecipeManager {

    private static final PyrolyzerRecipeManager INSTANCE = new PyrolyzerRecipeManager();
    protected static final int DEFAULT_ENERGY = 2000;

    public static PyrolyzerRecipeManager instance() {

        return INSTANCE;
    }

    private PyrolyzerRecipeManager() {

        super(DEFAULT_ENERGY, 4, 1);
        basePower = 5;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, Recipe<FalseIInventory>> recipes = recipeManager.byType(TCoreRecipeTypes.RECIPE_PYROLYZER);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion
}
