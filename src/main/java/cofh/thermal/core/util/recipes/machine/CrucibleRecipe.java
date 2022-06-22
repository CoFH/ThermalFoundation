package cofh.thermal.core.util.recipes.machine;

import cofh.lib.content.fluid.FluidIngredient;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.thermal.core.init.TCoreRecipeSerializers.CRUCIBLE_RECIPE_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.CRUCIBLE_RECIPE;

public class CrucibleRecipe extends ThermalRecipe {

    public CrucibleRecipe(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids) {

        super(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return CRUCIBLE_RECIPE_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return CRUCIBLE_RECIPE.get();
    }

}
