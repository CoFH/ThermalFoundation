package cofh.thermal.lib.util.managers;

import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;

import java.util.List;

public interface IRecipeManager extends IManager {

    default boolean validRecipe(IThermalInventory inventory) {

        return getRecipe(inventory) != null;
    }

    IMachineRecipe getRecipe(IThermalInventory inventory);

    List<IMachineRecipe> getRecipeList();

}
