package cofh.thermal.lib.util.recipes.internal;

import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.lib.util.recipes.IMachineInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cofh.lib.util.helpers.ItemHelper.itemsEqual;

public abstract class CatalyzedMachineRecipe extends BaseMachineRecipe {

    protected final int catalystSlot;
    protected boolean catalyzable;

    protected CatalyzedMachineRecipe(int catalystSlot, int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        this.catalystSlot = catalystSlot;
        // If all of the output chances are locked, then the recipe is not catalyzable.
        for (float f : outputItemChances) {
            catalyzable |= f >= 0.0F;
        }
    }

    public CatalyzedMachineRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

        this(1, energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
    }

    public abstract IRecipeCatalyst getCatalyst(ItemStack input);

    /**
     * Okay so there's a bit of trickery happening here - internally "unmodifiable" chance is stored as a negative. Saves some memory and is kinda clever.
     * This shouldn't ever cause problems because you're relying on this method call and not hacking around in the recipe, right? ;)
     */
    @Override
    public List<Float> getOutputItemChances(IMachineInventory inventory) {

        ArrayList<Float> modifiedChances = new ArrayList<>(outputItemChances);

        // Catalyst Logic
        if (catalyzable && inventory.inputSlots().size() > catalystSlot) {
            IRecipeCatalyst catalyst = getCatalyst(inventory.inputSlots().get(catalystSlot).getItemStack());
            if (catalyst == null) {
                return super.getOutputItemChances(inventory);
            }
            for (int i = 0; i < modifiedChances.size(); ++i) {
                if (modifiedChances.get(i) < 0.0F) {
                    modifiedChances.set(i, Math.abs(modifiedChances.get(i)));
                } else {
                    modifiedChances.set(i, Math.max(modifiedChances.get(i) * (i == 0 ? catalyst.getPrimaryMod() * inventory.getMachineProperties().getPrimaryMod() : catalyst.getSecondaryMod() * inventory.getMachineProperties().getSecondaryMod()), Math.max(catalyst.getMinOutputChance(), inventory.getMachineProperties().getMinOutputChance())));
                }
            }
            return modifiedChances;
        }
        return super.getOutputItemChances(inventory);
    }

    @Override
    public List<Integer> getInputItemCounts(IMachineInventory inventory) {

        if (inputItems.isEmpty()) {
            return Collections.emptyList();
        }
        if (catalyzable && inventory.inputSlots().size() > catalystSlot) {
            int[] ret = new int[inventory.inputSlots().size()];
            for (ItemStack input : inputItems) {
                for (int j = 0; j < ret.length; ++j) {
                    if (itemsEqual(input, inventory.inputSlots().get(j).getItemStack())) {
                        ret[j] = input.getCount();
                        break;
                    }
                }
            }
            // Catalyst Logic
            IRecipeCatalyst catalyst = getCatalyst(inventory.inputSlots().get(catalystSlot).getItemStack());
            if (catalyst != null && MathHelper.RANDOM.nextFloat() < catalyst.getUseChance() * inventory.getMachineProperties().getUseChance()) {
                ret[catalystSlot] = 1;
            }
            return IntStream.of(ret).boxed().collect(Collectors.toList());
        }
        return super.getInputItemCounts(inventory);
    }

    @Override
    public int getEnergy(IMachineInventory inventory) {

        // Catalyst Logic
        if (catalyzable && inventory.inputSlots().size() > catalystSlot) {
            IRecipeCatalyst catalyst = getCatalyst(inventory.inputSlots().get(catalystSlot).getItemStack());
            return catalyst == null ? super.getEnergy(inventory) : Math.abs(Math.round(catalyst.getEnergyMod() * super.getEnergy(inventory)));
        }
        return super.getEnergy(inventory);
    }

    @Override
    public float getXp(IMachineInventory inventory) {

        if (catalyzable && inventory.inputSlots().size() > catalystSlot) {
            IRecipeCatalyst catalyst = getCatalyst(inventory.inputSlots().get(catalystSlot).getItemStack());
            return catalyst == null ? super.getXp(inventory) : Math.abs(catalyst.getXpMod() * super.getXp(inventory));
        }
        return super.getXp(inventory);
    }
    // endregion
}
