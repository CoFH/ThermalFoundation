package cofh.thermal.core.util.managers.machine;

import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.TCoreRecipeTypes.RECIPE_CRUCIBLE;

public class CrucibleRecipeManager extends SingleItemRecipeManager {

    private static final CrucibleRecipeManager INSTANCE = new CrucibleRecipeManager();
    protected static final int DEFAULT_ENERGY = 40000;

    public static CrucibleRecipeManager instance() {

        return INSTANCE;
    }

    private CrucibleRecipeManager() {

        super(DEFAULT_ENERGY, 0, 1);
        this.basePower = 80;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(RECIPE_CRUCIBLE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion
}
