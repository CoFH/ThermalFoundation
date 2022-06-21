package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

public class LapidaryFuelManager extends SingleItemFuelManager {

    private static final LapidaryFuelManager INSTANCE = new LapidaryFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static LapidaryFuelManager instance() {

        return INSTANCE;
    }

    private LapidaryFuelManager() {

        super(DEFAULT_ENERGY);
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(TCoreRecipeTypes.FUEL_LAPIDARY);
        for (var entry : recipes.entrySet()) {
            addFuel(entry.getValue());
        }
    }
    // endregion
}
