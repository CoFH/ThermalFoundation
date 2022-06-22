package cofh.thermal.core.util.recipes.machine;

import cofh.lib.content.fluid.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.thermal.core.init.TCoreRecipeSerializers.PULVERIZER_RECYCLE_RECIPE_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.PULVERIZER_RECYCLE_RECIPE;

public class PulverizerRecycleRecipe extends PulverizerRecipe {

    public PulverizerRecycleRecipe(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids) {

        super(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return PULVERIZER_RECYCLE_RECIPE_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return PULVERIZER_RECYCLE_RECIPE.get();
    }

}
