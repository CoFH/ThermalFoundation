package cofh.thermal.core.util.recipes.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;

public class PulverizerRecycleRecipe extends PulverizerRecipe {

    public PulverizerRecycleRecipe(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids) {

        super(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(TCoreRecipeTypes.ID_RECIPE_PULVERIZER_RECYCLE);
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {

        return TCoreRecipeTypes.RECIPE_PULVERIZER_RECYCLE;
    }

}
