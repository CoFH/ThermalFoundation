package cofh.thermal.lib.common;

import cofh.thermal.lib.util.managers.IManager;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class ThermalRecipeManagers {

    private static final ThermalRecipeManagers INSTANCE = new ThermalRecipeManagers();

    private RecipeManager clientRecipeManager;
    private RecipeManager serverRecipeManager;
    private final List<IManager> managers = new ArrayList<>();

    public static ThermalRecipeManagers instance() {

        return INSTANCE;
    }

    public void setClientRecipeManager(RecipeManager recipeManager) {

        this.clientRecipeManager = recipeManager;
    }

    public void setServerRecipeManager(RecipeManager recipeManager) {

        this.serverRecipeManager = recipeManager;
    }

    public static void registerManager(IManager manager) {

        if (!instance().managers.contains(manager)) {
            instance().managers.add(manager);
        }
    }

    public void config() {

        for (IManager sub : managers) {
            sub.config();
        }
    }

    public void refreshServer() {

        if (this.serverRecipeManager == null) {
            return;
        }
        for (IManager sub : managers) {
            sub.refresh(this.serverRecipeManager);
        }
    }

    public void refreshClient() {

        if (this.clientRecipeManager == null) {
            return;
        }
        for (IManager sub : managers) {
            sub.refresh(this.clientRecipeManager);
        }
    }
    // endregion
}
