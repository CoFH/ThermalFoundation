package cofh.thermal.lib.util.recipes.internal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BaseDynamoFuel implements IDynamoFuel {

    protected final List<ItemStack> inputItems = new ArrayList<>();
    protected final List<FluidStack> inputFluids = new ArrayList<>();

    protected final int energy;

    public BaseDynamoFuel(int energy) {

        this.energy = energy;
    }

    public BaseDynamoFuel(int energy, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids) {

        this(energy);

        if (inputItems != null) {
            this.inputItems.addAll(inputItems);
        }
        if (inputFluids != null) {
            this.inputFluids.addAll(inputFluids);
        }
        trim();
    }

    private void trim() {

        ((ArrayList<ItemStack>) this.inputItems).trimToSize();
        ((ArrayList<FluidStack>) this.inputFluids).trimToSize();
    }

    // region IDynamoFuel
    @Override
    public List<ItemStack> getInputItems() {

        return inputItems;
    }

    @Override
    public List<FluidStack> getInputFluids() {

        return inputFluids;
    }

    @Override
    public int getEnergy() {

        return energy;
    }
    // endregion
}
