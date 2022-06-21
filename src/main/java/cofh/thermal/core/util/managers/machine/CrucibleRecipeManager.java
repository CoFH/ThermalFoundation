package cofh.thermal.core.util.managers.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.world.item.crafting.RecipeManager;

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
        var recipes = recipeManager.byType(TCoreRecipeTypes.RECIPE_CRUCIBLE);
        for (var entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion
}
