package cofh.thermal.core.util.managers.dynamo;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleFluidFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class MagmaticFuelManager extends SingleFluidFuelManager {

    private static final MagmaticFuelManager INSTANCE = new MagmaticFuelManager();
    protected static int DEFAULT_ENERGY = 100000;

    public static MagmaticFuelManager instance() {

        return INSTANCE;
    }

    private MagmaticFuelManager() {

        super(DEFAULT_ENERGY);
    }

    public int getEnergy(FluidStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.getRecipes(TCoreRecipeTypes.FUEL_MAGMATIC);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addFuel((ThermalFuel) entry.getValue());
        }
    }
    // endregion
}
