package cofh.thermal.lib.compat.crt.base;

import cofh.lib.fluid.FluidIngredient;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CRTRecipe {

    private final ResourceLocation name;

    private List<Ingredient> inputItems;
    private List<FluidIngredient> inputFluids;

    private List<ItemStack> outputItems;
    private List<FluidStack> outputFluids;
    private List<Float> outputItemChances;

    private int energy = 0;
    private float experience = 0;

    public CRTRecipe(ResourceLocation name) {

        this.name = name;
    }

    public CRTRecipe input(IIngredientWithAmount... ingredient) {

        this.inputItems = Arrays.stream(ingredient).map(CRTHelper::mapIIngredientWithAmount).collect(Collectors.toList());
        return this;
    }

    public CRTRecipe input(CTFluidIngredient... ingredient) {

        this.inputFluids = Arrays.stream(ingredient).map(CRTHelper::mapFluidIngredient).collect(Collectors.toList());
        return this;
    }

    public CRTRecipe output(Percentaged<IItemStack>... stack) {

        this.outputItems = Arrays.stream(stack).filter(weightedStack -> !weightedStack.getData().isEmpty()).map(weightedStack -> weightedStack.getData().getInternal()).collect(Collectors.toList());
        this.outputItemChances = Arrays.stream(stack).filter(weightedStack -> !weightedStack.getData().isEmpty()).map(weightedStack -> (float) weightedStack.getPercentage()).collect(Collectors.toList());
        return this;
    }

    public CRTRecipe output(IItemStack... stack) {

        return output(Arrays.stream(stack).map(IItemStack::asWeightedItemStack).toArray(Percentaged[]::new));
    }

    public CRTRecipe output(FluidStack... stack) {

        List<FluidStack> newList = Arrays.stream(stack).filter(fluidStack -> !fluidStack.isEmpty()).collect(Collectors.toList());
        // Not sure if an empty list vs a null list makes a difference, but if there are no entries I'm just not going to assign it
        if (!newList.isEmpty()) {
            this.outputFluids = newList;
        }
        return this;
    }

    public CRTRecipe output(IFluidStack... stack) {

        List<FluidStack> newList = Arrays.stream(stack).map(IFluidStack::getInternal).filter(fluidStack -> !fluidStack.isEmpty()).collect(Collectors.toList());
        // Not sure if an empty list vs a null list makes a difference, but if there are no entries I'm just not going to assign it
        if (!newList.isEmpty()) {
            this.outputFluids = newList;
        }
        return this;
    }

    public CRTRecipe energy(int energy) {

        this.energy = energy;
        return this;
    }

    public CRTRecipe experience(float experience) {

        this.experience = experience;
        return this;
    }

    // Helpers for Recipe Replacements
    public CRTRecipe setInputItems(List<Ingredient> ingredients) {

        this.inputItems = ingredients;
        return this;
    }

    public CRTRecipe setInputFluids(List<FluidIngredient> ingredient) {

        this.inputFluids = ingredient;
        return this;
    }

    public CRTRecipe setOutputItems(List<ItemStack> outputItems, List<Float> outputItemChances) {

        this.outputItems = outputItems;
        this.outputItemChances = outputItemChances;
        return this;
    }

    public CRTRecipe setOutputFluids(List<FluidStack> outputFluids) {

        this.outputFluids = outputFluids;
        return this;
    }

    public <T extends ThermalRecipe> T recipe(IRecipeBuilder<T> builder) {

        return builder.apply(name, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    public interface IRecipeBuilder<T extends ThermalRecipe> {

        T apply(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids);

    }

}
