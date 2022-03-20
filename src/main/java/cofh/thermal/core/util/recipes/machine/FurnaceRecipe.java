package cofh.thermal.core.util.recipes.machine;

import cofh.lib.fluid.FluidIngredient;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static cofh.lib.util.constants.Constants.BASE_CHANCE_LOCKED;
import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;

public class FurnaceRecipe extends ThermalRecipe {

    public FurnaceRecipe(ResourceLocation recipeId, int energy, float experience, @Nullable List<Ingredient> inputItems, @Nullable List<FluidIngredient> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> outputItemChances, @Nullable List<FluidStack> outputFluids) {

        super(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    public FurnaceRecipe(ResourceLocation recipeId, int energy, float experience, AbstractCookingRecipe recipe) {

        this(recipeId, energy, experience, recipe.getIngredients(), Collections.emptyList(), Collections.singletonList(recipe.getResultItem()), Collections.singletonList(BASE_CHANCE_LOCKED), Collections.emptyList());
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(TCoreRecipeTypes.ID_RECIPE_FURNACE);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return TCoreRecipeTypes.RECIPE_FURNACE;
    }

}
