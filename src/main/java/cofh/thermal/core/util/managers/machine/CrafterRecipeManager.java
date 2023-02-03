package cofh.thermal.core.util.managers.machine;

import cofh.thermal.core.util.recipes.machine.CrafterRecipe;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.IManager;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.IdentityHashMap;

public class CrafterRecipeManager extends AbstractManager implements IManager {

    private static final CrafterRecipeManager INSTANCE = new CrafterRecipeManager();
    protected static final int DEFAULT_ENERGY = 400;

    protected IdentityHashMap<Recipe<?>, CrafterRecipe> recipeMap = new IdentityHashMap<>();

    public static CrafterRecipeManager instance() {

        return INSTANCE;
    }

    private CrafterRecipeManager() {

        super(DEFAULT_ENERGY);
    }

    public boolean validItem(ItemStack item, IMachineRecipe recipe) {

        return recipe instanceof CrafterRecipe crafterRecipe && crafterRecipe.validItem(item);
    }

    public boolean validFluid(FluidStack fluid, IMachineRecipe recipe) {

        return recipe instanceof CrafterRecipe crafterRecipe && crafterRecipe.validFluid(fluid);
    }

    public CrafterRecipe getRecipe(Recipe<?> recipe) {

        if (recipe == null || recipe.isSpecial() || recipe.getResultItem().isEmpty()) {
            return null;
        }
        if (!recipeMap.containsKey(recipe)) {
            recipeMap.put(recipe, new CrafterRecipe(DEFAULT_ENERGY, recipe));
        }
        return recipeMap.get(recipe);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        recipeMap.clear();
    }
    // endregion
}
