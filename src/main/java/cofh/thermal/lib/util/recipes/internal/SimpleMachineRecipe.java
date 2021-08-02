package cofh.thermal.lib.util.recipes.internal;

import cofh.thermal.lib.util.recipes.IMachineInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class should be used for machines where Ingredient order is 100% guaranteed to be known. It's a minor optimization but an elegant one.
 */
public class SimpleMachineRecipe extends BaseMachineRecipe {

    public SimpleMachineRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
    }

    // region IMachineRecipe
    @Override
    public List<Integer> getInputItemCounts(IMachineInventory inventory) {

        // Ingredient order is guaranteed.
        if (inputItems.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Integer> ret = new ArrayList<>(inputItems.size());
        for (ItemStack input : inputItems) {
            ret.add(input.getCount());
        }
        return ret;
    }

    @Override
    public List<Integer> getInputFluidCounts(IMachineInventory inventory) {

        // Ingredient order is guaranteed.
        if (inputFluids.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Integer> ret = new ArrayList<>(inputFluids.size());
        for (FluidStack input : inputFluids) {
            ret.add(input.getAmount());
        }
        return ret;
    }
    // endregion
}
