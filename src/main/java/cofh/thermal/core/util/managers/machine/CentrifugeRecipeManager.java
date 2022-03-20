package cofh.thermal.core.util.managers.machine;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Map;

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
        Map<ResourceLocation, Recipe<FalseIInventory>> recipes = recipeManager.byType(TCoreRecipeTypes.RECIPE_CENTRIFUGE);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion
}
