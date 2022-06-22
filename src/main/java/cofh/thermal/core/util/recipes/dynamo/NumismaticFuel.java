package cofh.thermal.core.util.recipes.dynamo;

import cofh.lib.content.fluid.FluidIngredient;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.ThermalCore.RECIPE_TYPES;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_FUEL_NUMISMATIC;

public class NumismaticFuel extends ThermalFuel {

    public NumismaticFuel(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidIngredient> inputFluids) {

        super(recipeId, energy, inputItems, inputFluids);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_FUEL_NUMISMATIC);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return RECIPE_TYPES.get(ID_FUEL_NUMISMATIC);
    }

    //    @Nonnull
    //    @Override
    //    public String getGroup() {
    //
    //        return DYNAMO_NUMISMATIC_BLOCK.getTranslationKey();
    //    }
    //
    //    @Nonnull
    //    @Override
    //    public ItemStack getIcon() {
    //
    //        return new ItemStack(DYNAMO_NUMISMATIC_BLOCK);
    //    }

}
