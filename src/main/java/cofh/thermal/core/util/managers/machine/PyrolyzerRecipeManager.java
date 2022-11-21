package cofh.thermal.core.util.managers.machine;

import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.TCoreRecipeTypes.PYROLYZER_RECIPE;

public class PyrolyzerRecipeManager extends SingleItemRecipeManager {

    private static final PyrolyzerRecipeManager INSTANCE = new PyrolyzerRecipeManager();
    protected static final int DEFAULT_ENERGY = 2000;

    public static PyrolyzerRecipeManager instance() {

        return INSTANCE;
    }

    private PyrolyzerRecipeManager() {

        super(DEFAULT_ENERGY, 4, 1);
        this.basePower = 5;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(PYROLYZER_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion
}
