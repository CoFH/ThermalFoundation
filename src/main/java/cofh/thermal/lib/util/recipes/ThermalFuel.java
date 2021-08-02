package cofh.thermal.lib.util.recipes;

import cofh.lib.util.recipes.SerializableRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ThermalFuel extends SerializableRecipe {

    protected final List<Ingredient> inputItems = new ArrayList<>();
    protected final List<FluidStack> inputFluids = new ArrayList<>();

    protected int energy;

    protected ThermalFuel(ResourceLocation recipeId, int energy, List<Ingredient> inputItems, List<FluidStack> inputFluids) {

        super(recipeId);

        this.energy = energy;

        if (inputItems != null) {
            this.inputItems.addAll(inputItems);
        }
        if (inputFluids != null) {
            this.inputFluids.addAll(inputFluids);
        }
        trim();
    }

    private void trim() {

        ((ArrayList<Ingredient>) this.inputItems).trimToSize();
        ((ArrayList<FluidStack>) this.inputFluids).trimToSize();
    }

    // region GETTERS
    public List<Ingredient> getInputItems() {

        return inputItems;
    }

    public List<FluidStack> getInputFluids() {

        return inputFluids;
    }

    public int getEnergy() {

        return energy;
    }
    // endregion
}
