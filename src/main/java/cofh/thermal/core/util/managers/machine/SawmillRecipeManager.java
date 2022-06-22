package cofh.thermal.core.util.managers.machine;

import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.TCoreRecipeTypes.SAWMILL_RECIPE;

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
        var recipes = recipeManager.byType(SAWMILL_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion
}
