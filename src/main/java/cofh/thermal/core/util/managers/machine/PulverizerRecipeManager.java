package cofh.thermal.core.util.managers.machine;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class PulverizerRecipeManager extends SingleItemRecipeManager.Catalyzed {

    private static final PulverizerRecipeManager INSTANCE = new PulverizerRecipeManager();
    protected static final int DEFAULT_ENERGY = 4000;

    public static PulverizerRecipeManager instance() {

        return INSTANCE;
    }

    private PulverizerRecipeManager() {

        super(DEFAULT_ENERGY, 4, 0);
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
        } else {
            recipe = new InternalPulverizerRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }
        recipeMap.put(convert(input), recipe);
        return recipe;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.getRecipes(TCoreRecipeTypes.RECIPE_PULVERIZER);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
        Map<ResourceLocation, IRecipe<FalseIInventory>> recycle = recipeManager.getRecipes(TCoreRecipeTypes.RECIPE_PULVERIZER_RECYCLE);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recycle.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue(), BaseMachineRecipe.RecipeType.DISENCHANT);
        }
        Map<ResourceLocation, IRecipe<FalseIInventory>> catalysts = recipeManager.getRecipes(TCoreRecipeTypes.CATALYST_PULVERIZER);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : catalysts.entrySet()) {
            addCatalyst((ThermalCatalyst) entry.getValue());
        }
    }
    // endregion

    // region CATALYZED RECIPE
    protected static class InternalPulverizerRecipe extends CatalyzedMachineRecipe {

        public InternalPulverizerRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

            super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }

        @Override
        public IRecipeCatalyst getCatalyst(ItemStack input) {

            return instance().getCatalyst(input);
        }

    }
    // endregion
}
