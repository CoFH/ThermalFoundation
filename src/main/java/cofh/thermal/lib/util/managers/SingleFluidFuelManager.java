package cofh.thermal.lib.util.managers;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.fluid.FluidStackHolder;
import cofh.lib.fluid.IFluidStackAccess;
import cofh.lib.inventory.IItemStackAccess;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import cofh.thermal.lib.util.recipes.internal.BaseDynamoFuel;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;

public abstract class SingleFluidFuelManager extends AbstractManager implements IFuelManager {

    public static final int MIN_ENERGY = 10000;
    public static final int MAX_ENERGY = 200000000;

    public static final int FLUID_FUEL_AMOUNT = 100;
    public static final int ENERGY_FACTOR = BUCKET_VOLUME / FLUID_FUEL_AMOUNT;

    protected Map<Integer, IDynamoFuel> fuelMap = new Object2ObjectOpenHashMap<>();

    protected SingleFluidFuelManager(int defaultEnergy) {

        super(defaultEnergy);
    }

    public void addFuel(ThermalFuel recipe) {

        addFuel(recipe.getEnergy(), Collections.emptyList(), recipe.getInputFluids());
    }

    public boolean validFuel(FluidStack input) {

        return getFuel(input) != null;
    }

    protected void clear() {

        fuelMap.clear();
    }

    protected IDynamoFuel getFuel(FluidStack input) {

        return getFuel(Collections.emptyList(), Collections.singletonList(new FluidStackHolder(input)));
    }

    protected IDynamoFuel getFuel(List<? extends IItemStackAccess> inputSlots, List<? extends IFluidStackAccess> inputTanks) {

        if (inputTanks.isEmpty() || inputTanks.get(0).isEmpty()) {
            return null;
        }
        return fuelMap.get(FluidHelper.fluidHashcode(inputTanks.get(0).getFluidStack()));
    }

    public IDynamoFuel addFuel(int energy, List<ItemStack> inputItems, List<FluidStack> inputFluids) {

        if (inputFluids.isEmpty() || energy <= 0) {
            return null;
        }
        if (energy < MIN_ENERGY || energy > MAX_ENERGY) {
            return null;
        }
        FluidStack input = inputFluids.get(0);
        if (input.isEmpty()) {
            return null;
        }
        int amount = input.getAmount();
        if (amount != FLUID_FUEL_AMOUNT) {
            if (amount != BUCKET_VOLUME) {
                long normEnergy = energy * BUCKET_VOLUME / amount;
                input.setAmount(FLUID_FUEL_AMOUNT);
                energy = (int) normEnergy;
            }
            energy /= ENERGY_FACTOR;
        }
        energy = (int) (energy * getDefaultScale());

        BaseDynamoFuel fuel = new BaseDynamoFuel(energy, inputItems, inputFluids);
        fuelMap.put(FluidHelper.fluidHashcode(input), fuel);
        return fuel;
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
