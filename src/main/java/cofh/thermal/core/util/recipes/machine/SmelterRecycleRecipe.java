package cofh.thermal.core.util.recipes.machine;

import cofh.lib.fluid.FluidIngredient;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.managers.machine.SmelterRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

import static cofh.thermal.core.init.TCoreRecipeSerializers.SMELTER_RECYCLE_RECIPE_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.SMELTER_RECYCLE_RECIPE;

public class SmelterRecycleRecipe extends SmelterRecipe {

    public SmelterRecycleRecipe(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputItemChances, List<FluidStack> outputFluids) {

        super(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);

        if (this.energy <= 0) {
            int defaultEnergy = SmelterRecipeManager.instance().getDefaultEnergy();
            ThermalCore.LOG.warn("Energy value for " + recipeId + " was out of allowable range and has been set to a default value of " + defaultEnergy + ".");
            this.energy = defaultEnergy;
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return SMELTER_RECYCLE_RECIPE_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return SMELTER_RECYCLE_RECIPE.get();
    }

}
