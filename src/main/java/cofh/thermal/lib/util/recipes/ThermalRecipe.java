package cofh.thermal.lib.util.recipes;

import cofh.lib.util.recipes.SerializableRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.constants.Constants.BASE_CHANCE_LOCKED;

public abstract class ThermalRecipe extends SerializableRecipe {

    protected final List<Ingredient> inputItems = new ArrayList<>();
    protected final List<FluidStack> inputFluids = new ArrayList<>();

    protected final List<ItemStack> outputItems = new ArrayList<>();
    protected final List<FluidStack> outputFluids = new ArrayList<>();
    protected final List<Float> outputItemChances = new ArrayList<>();

    protected int energy;
    protected float xp;

    protected boolean catalyzable;

    protected ThermalRecipe(ResourceLocation recipeId, int energy, float xp, List<Ingredient> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids) {

        super(recipeId);

        this.energy = energy;
        this.xp = Math.max(0.0F, xp);

        if (inputItems != null) {
            this.inputItems.addAll(inputItems);
        }
        if (inputFluids != null) {
            this.inputFluids.addAll(inputFluids);
        }
        if (outputItems != null) {
            this.outputItems.addAll(outputItems);

            if (outputItemChances != null) {
                this.outputItemChances.addAll(outputItemChances);
            }
            if (this.outputItemChances.size() < this.outputItems.size()) {
                for (int i = this.outputItemChances.size(); i < this.outputItems.size(); ++i) {
                    this.outputItemChances.add(BASE_CHANCE_LOCKED);
                }
            }
            for (float f : this.outputItemChances) {
                catalyzable |= f >= 0.0F;
            }
        }
        if (outputFluids != null) {
            this.outputFluids.addAll(outputFluids);
        }
        trim();
    }

    private void trim() {

        ((ArrayList<Ingredient>) this.inputItems).trimToSize();
        ((ArrayList<FluidStack>) this.inputFluids).trimToSize();

        ((ArrayList<ItemStack>) this.outputItems).trimToSize();
        ((ArrayList<FluidStack>) this.outputFluids).trimToSize();
        ((ArrayList<Float>) this.outputItemChances).trimToSize();
    }

    // region GETTERS
    public List<Ingredient> getInputItems() {

        return inputItems;
    }

    public List<FluidStack> getInputFluids() {

        return inputFluids;
    }

    public List<ItemStack> getOutputItems() {

        return outputItems;
    }

    public List<FluidStack> getOutputFluids() {

        return outputFluids;
    }

    public List<Float> getOutputItemChances() {

        return outputItemChances;
    }

    public int getEnergy() {

        return energy;
    }

    public float getXp() {

        return xp;
    }

    public boolean isCatalyzable() {

        return catalyzable;
    }
    // endregion
}
