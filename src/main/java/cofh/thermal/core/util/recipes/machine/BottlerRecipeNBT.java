package cofh.thermal.core.util.recipes.machine;

import cofh.thermal.lib.util.recipes.IMachineInventory;
import cofh.thermal.lib.util.recipes.internal.BaseMachineRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static cofh.lib.util.Constants.BASE_CHANCE_LOCKED;

public class BottlerRecipeNBT extends BaseMachineRecipe {

    public BottlerRecipeNBT(int energy, float experience, @Nonnull ItemStack inputItem, @Nonnull FluidStack inputFluid, @Nonnull ItemStack outputItem) {

        super(energy, experience);
        this.inputItems.add(inputItem);
        this.inputFluids.add(inputFluid);
        this.outputItems.add(outputItem);
        this.outputItemChances.add(BASE_CHANCE_LOCKED);
    }

    @Override
    public List<ItemStack> getOutputItems(IMachineInventory inventory) {

        FluidStack fluid = inventory.inputTanks().get(0).getFluidStack();
        ItemStack item = outputItems.get(0).copy();
        if (fluid.hasTag()) {
            item.setTag(fluid.getTag().copy());
        }
        return Collections.singletonList(item);
    }

    @Override
    public List<Integer> getInputFluidCounts(IMachineInventory inventory) {

        if (inputFluids.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(inputFluids.get(0).getAmount());
    }

}
