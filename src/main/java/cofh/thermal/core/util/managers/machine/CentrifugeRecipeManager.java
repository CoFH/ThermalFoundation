package cofh.thermal.core.util.managers.machine;

import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.TCoreRecipeTypes.CENTRIFUGE_RECIPE;

public class CentrifugeRecipeManager extends SingleItemRecipeManager {

    private static final CentrifugeRecipeManager INSTANCE = new CentrifugeRecipeManager();
    protected static final int DEFAULT_ENERGY = 4000;

    public static CentrifugeRecipeManager instance() {

        return INSTANCE;
    }

    private CentrifugeRecipeManager() {

        super(DEFAULT_ENERGY, 4, 1);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(CENTRIFUGE_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion
}
