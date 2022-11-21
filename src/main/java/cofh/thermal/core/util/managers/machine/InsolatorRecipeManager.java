package cofh.thermal.core.util.managers.machine;

import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.IMachineInventory;
import cofh.thermal.lib.util.recipes.internal.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.thermal.core.init.TCoreRecipeTypes.INSOLATOR_CATALYST;
import static cofh.thermal.core.init.TCoreRecipeTypes.INSOLATOR_RECIPE;

public class InsolatorRecipeManager extends SingleItemRecipeManager.Catalyzed {

    private static final InsolatorRecipeManager INSTANCE = new InsolatorRecipeManager();
    protected static final int DEFAULT_ENERGY = 20000;

    protected int defaultWater = BUCKET_VOLUME / 2;

    public static InsolatorRecipeManager instance() {

        return INSTANCE;
    }

    private InsolatorRecipeManager() {

        super(DEFAULT_ENERGY, 4, 0);
    }

    public int getDefaultWater() {

        return defaultWater;
    }

    // region RECIPES
    @Override
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
            recipeMap.put(makeComparable(input), recipe);
        } else {
            recipe = new InternalInsolatorRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
            recipeMap.put(makeNBTComparable(input), recipe);
        }
        return recipe;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(INSOLATOR_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
        var catalysts = recipeManager.byType(INSOLATOR_CATALYST.get());
        for (var entry : catalysts.entrySet()) {
            addCatalyst(entry.getValue());
        }
    }
    // endregion

    // region CATALYZED RECIPE
    protected static class InternalInsolatorRecipe extends CatalyzedMachineRecipe {

        public InternalInsolatorRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

            super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
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

        @Override
        public IRecipeCatalyst getCatalyst(ItemStack input) {

            return instance().getCatalyst(input);
        }

    }
    // endregion
}
