package cofh.thermal.lib.util.managers;

import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.content.inventory.ItemStackHolder;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Simple recipe manager - single item key'd. Fluids NOT part of key.
 */
public abstract class SingleItemRecipeManager extends AbstractManager implements IRecipeManager {

    protected Map<ComparableItemStack, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();

    protected int maxOutputItems;
    protected int maxOutputFluids;

    protected SingleItemRecipeManager(int defaultEnergy, int maxOutputItems, int maxOutputFluids) {

        super(defaultEnergy);
        this.maxOutputItems = maxOutputItems;
        this.maxOutputFluids = maxOutputFluids;
    }

    public final void addRecipe(ThermalRecipe recipe) {

        addRecipe(recipe, BaseMachineRecipe.RecipeType.STANDARD);
    }

    public void addRecipe(ThermalRecipe recipe, BaseMachineRecipe.RecipeType type) {

        if (!recipe.getInputFluids().isEmpty()) {
            for (FluidStack fluidInput : recipe.getInputFluids().get(0).getFluids()) {
                for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
                    addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(recipeInput), Collections.singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                }
            }
        } else {
            for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
                addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(recipeInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
            }
        }
    }

    public boolean validRecipe(ItemStack input) {

        return getRecipe(input) != null;
    }

    protected void clear() {

        recipeMap.clear();
    }

    protected IMachineRecipe getRecipe(ItemStack input) {

        return getRecipe(Collections.singletonList(new ItemStackHolder(input)), Collections.emptyList());
    }

    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty() || inputSlots.get(0).isEmpty()) {
            return null;
        }
        return recipeMap.get(convert(inputSlots.get(0).getItemStack()));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids, BaseMachineRecipe.RecipeType type) {

        if (inputItems.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        ItemStack input = inputItems.get(0);
        if (input.isEmpty()) {
            return null;
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        for (FluidStack stack : outputFluids) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        energy = (int) (energy * getDefaultScale());

        IMachineRecipe recipe;
        if (type == BaseMachineRecipe.RecipeType.DISENCHANT) {
            recipe = new DisenchantMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        } else {
            recipe = new SimpleMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }
        recipeMap.put(convert(input), recipe);
        return recipe;
    }

    //    protected IMachineRecipe addRecipe(int energy, float experience, ItemStack input, ItemStack output) {
    //
    //        return addRecipe(energy, experience, Collections.singletonList(input), Collections.emptyList(), Collections.singletonList(output), Collections.singletonList(BASE_CHANCE_LOCKED), Collections.emptyList());
    //    }
    //
    //    protected IMachineRecipe addRecipe(int energy, float experience, ItemStack input, ItemStack output, float chance) {
    //
    //        return addRecipe(energy, experience, Collections.singletonList(input), Collections.emptyList(), Collections.singletonList(output), Collections.singletonList(chance), Collections.emptyList());
    //    }
    //
    //    protected IMachineRecipe addRecipe(int energy, float experience, ItemStack input, List<ItemStack> output, List<Float> chance) {
    //
    //        return addRecipe(energy, experience, Collections.singletonList(input), Collections.emptyList(), output, chance, Collections.emptyList());
    //    }

    // region IRecipeManager
    @Override
    public IMachineRecipe getRecipe(IThermalInventory inventory) {

        return getRecipe(inventory.inputSlots(), inventory.inputTanks());
    }

    @Override
    public List<IMachineRecipe> getRecipeList() {

        return new ArrayList<>(recipeMap.values());
    }
    // endregion

    // region CATALYZED CLASS
    public static abstract class Catalyzed extends SingleItemRecipeManager implements CatalyzedRecipeManager {

        protected Map<ComparableItemStack, IRecipeCatalyst> catalystMap = new Object2ObjectOpenHashMap<>();

        protected Catalyzed(int defaultEnergy, int maxOutputItems, int maxOutputFluids) {

            super(defaultEnergy, maxOutputItems, maxOutputFluids);
        }

        protected void clear() {

            super.clear();
            catalystMap.clear();
        }

        @Override
        public List<ItemStack> getCatalysts() {

            List<ItemStack> ret = new ArrayList<>(catalystMap.size());
            catalystMap.keySet().forEach(stack -> ret.add(stack.toItemStack()));
            return ret;
        }

        // region CATALYSTS
        @Override
        public IRecipeCatalyst getCatalyst(IItemStackHolder input) {

            return catalystMap.get(convert(input.getItemStack()));
        }

        @Override
        public IRecipeCatalyst getCatalyst(ItemStack input) {

            return catalystMap.get(convert(input));
        }

        public void addCatalyst(ThermalCatalyst catalyst) {

            for (ItemStack ingredient : catalyst.getIngredient().getItems()) {
                addCatalyst(ingredient, catalyst.getPrimaryMod(), catalyst.getSecondaryMod(), catalyst.getEnergyMod(), catalyst.getMinChance(), catalyst.getUseChance());
            }
        }

        public IRecipeCatalyst addCatalyst(ItemStack input, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

            if (input == null || input.isEmpty()) {
                return null;
            }
            BaseMachineCatalyst catalyst = new BaseMachineCatalyst(primaryMod, secondaryMod, energyMod, minChance, useChance);
            catalystMap.put(convert(input), catalyst);
            return catalyst;
        }

        public boolean validCatalyst(ItemStack input) {

            return getCatalyst(input) != null;
        }

        public IRecipeCatalyst removeCatalyst(ItemStack input) {

            return catalystMap.remove(convert(input));
        }
        // endregion
    }
    // endregion
}
