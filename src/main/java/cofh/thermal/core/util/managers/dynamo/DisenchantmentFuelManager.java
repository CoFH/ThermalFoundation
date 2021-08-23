package cofh.thermal.core.util.managers.dynamo;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Map;

public class DisenchantmentFuelManager extends SingleItemFuelManager {

    private static final DisenchantmentFuelManager INSTANCE = new DisenchantmentFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static DisenchantmentFuelManager instance() {

        return INSTANCE;
    }

    private DisenchantmentFuelManager() {

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
        return fuel != null ? fuel.getEnergy() : getEnergyFromEnchantments(stack);
    }

    public int getEnergyFromEnchantments(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
        int energy = 0;

        for (Enchantment enchant : enchants.keySet()) {
            energy += enchant.getMinCost(enchants.get(enchant));
        }
        energy += (enchants.size() * (enchants.size() + 1)) / 2;
        energy *= (DEFAULT_ENERGY / 2);

        return energy;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.byType(TCoreRecipeTypes.FUEL_DISENCHANTMENT);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addFuel((ThermalFuel) entry.getValue());
        }
    }
    // endregion
}
