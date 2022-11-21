package cofh.thermal.lib.util.managers;

import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.inventory.ItemStackHolder;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import cofh.thermal.lib.util.recipes.internal.BaseDynamoFuel;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class SingleItemFuelManager extends AbstractManager implements IFuelManager {

    public static final int MIN_ENERGY = 1000;
    public static final int MAX_ENERGY = 20000000;

    protected Map<ComparableItemStack, IDynamoFuel> fuelMap = new Object2ObjectOpenHashMap<>();

    protected SingleItemFuelManager(int defaultEnergy) {

        super(defaultEnergy);
        this.basePower = 40;
    }

    public void addFuel(ThermalFuel recipe) {

        for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
            addFuel(recipe.getEnergy(), Collections.singletonList(recipeInput), Collections.emptyList());
        }
    }

    public boolean validFuel(ItemStack input) {

        return getFuel(input) != null;
    }

    protected void clear() {

        fuelMap.clear();
    }

    protected IDynamoFuel getFuel(ItemStack input) {

        return getFuel(Collections.singletonList(new ItemStackHolder(input)), Collections.emptyList());
    }

    protected IDynamoFuel getFuel(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty() || inputSlots.get(0).isEmpty()) {
            return null;
        }
        return fuelMap.get(makeComparable(inputSlots.get(0).getItemStack()));
    }

    public IDynamoFuel addFuel(int energy, List<ItemStack> inputItems, List<FluidStack> inputFluids) {

        if (inputItems.isEmpty() || energy <= 0) {
            return null;
        }
        if (energy < MIN_ENERGY || energy > MAX_ENERGY) {
            return null;
        }
        ItemStack input = inputItems.get(0);
        if (input.isEmpty()) {
            return null;
        }
        energy = (int) (energy * getDefaultScale());

        BaseDynamoFuel fuel = new BaseDynamoFuel(energy, inputItems, inputFluids);
        fuelMap.put(makeComparable(input), fuel);
        return fuel;
    }

    @Override
    public int getMinPower() {

        return 10;
    }

    // region IFuelManager
    @Override
    public IDynamoFuel getFuel(IThermalInventory inventory) {

        return getFuel(inventory.inputSlots(), inventory.inputTanks());
    }

    @Override
    public List<IDynamoFuel> getFuelList() {

        return new ArrayList<>(fuelMap.values());
    }
    // endregion
}
