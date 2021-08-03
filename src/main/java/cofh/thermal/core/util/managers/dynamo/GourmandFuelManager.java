package cofh.thermal.core.util.managers.dynamo;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Map;

public class GourmandFuelManager extends SingleItemFuelManager {

    private static final GourmandFuelManager INSTANCE = new GourmandFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static GourmandFuelManager instance() {

        return INSTANCE;
    }

    private GourmandFuelManager() {

        super(DEFAULT_ENERGY);
    }

    @Override
    public boolean validFuel(ItemStack input) {

        if (input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            return false;
        }
        return getEnergy(input) > 0;
    }

    @Override
    protected void clear() {

        fuelMap.clear();
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFromFood(stack);
    }

    public int getEnergyFromFood(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        Food food = stack.getItem().getFood();
        if (food == null) {
            return 0;
        }
        float energy = (food.getSaturation() * 2 * food.getHealing() + food.getHealing());
        energy *= (DEFAULT_ENERGY >> 3);

        if (food.canEatWhenFull() || food.getEffects().size() > 0) {
            energy *= 2;
        }
        if (food.isFastEating()) {
            energy *= 2;
        }
        if (food.isMeat()) {
            energy /= 2;
        }
        return (int) energy;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.getRecipes(TCoreRecipeTypes.FUEL_GOURMAND);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addFuel((ThermalFuel) entry.getValue());
        }
    }
    // endregion
}
