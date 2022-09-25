package cofh.thermal.core.util.managers.dynamo;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.dynamo.StirlingFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.Constants.RF_PER_FURNACE_UNIT;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class StirlingFuelManager extends SingleItemFuelManager {

    private static final StirlingFuelManager INSTANCE = new StirlingFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static StirlingFuelManager instance() {

        return INSTANCE;
    }

    private StirlingFuelManager() {

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
        convertedFuels.clear();
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFurnaceFuel(stack);
    }

    public int getEnergyFurnaceFuel(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.getItem().hasContainerItem(stack)) {
            return 0;
        }
        int energy = ForgeHooks.getBurnTime(stack, null) * RF_PER_FURNACE_UNIT;
        return energy >= MIN_ENERGY ? energy : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, Recipe<FalseIInventory>> recipes = recipeManager.byType(TCoreRecipeTypes.FUEL_STIRLING);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : recipes.entrySet()) {
            addFuel((ThermalFuel) entry.getValue());
        }
        createConvertedRecipes(recipeManager);
    }
    // endregion

    // region CONVERSION
    protected List<StirlingFuel> convertedFuels = new ArrayList<>();

    public List<StirlingFuel> getConvertedFuels() {

        return convertedFuels;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        ItemStack query;
        for (Item item : ForgeRegistries.ITEMS) {
            query = new ItemStack(item);
            try {
                if (getFuel(query) == null && validFuel(query)) {
                    convertedFuels.add(convert(query, getEnergy(query)));
                }
            } catch (Exception e) { // pokemon!
                ThermalCore.LOG.error(query.getItem().getRegistryName() + " threw an exception when querying the fuel value as the mod author is doing non-standard things in their item code (possibly tag related). It may not display in JEI but should function as fuel.");
            }
        }
    }

    protected StirlingFuel convert(ItemStack item, int energy) {

        return new StirlingFuel(new ResourceLocation(ID_THERMAL, "stirling_" + item.getItem().getRegistryName().getPath()), energy, singletonList(Ingredient.of(item)), emptyList());
    }
    // endregion
}
