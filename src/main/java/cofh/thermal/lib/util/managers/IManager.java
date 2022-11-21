package cofh.thermal.lib.util.managers;

import net.minecraft.world.item.crafting.RecipeManager;

public interface IManager {

    default void config() {

    }

    void refresh(RecipeManager recipeManager);

}
