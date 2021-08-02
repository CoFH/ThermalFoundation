package cofh.thermal.core.util.recipes.dynamo;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;

public class LapidaryFuel extends ThermalFuel {

    public LapidaryFuel(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids) {

        super(recipeId, energy, inputItems, inputFluids);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(TCoreRecipeTypes.ID_FUEL_LAPIDARY);
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {

        return TCoreRecipeTypes.FUEL_LAPIDARY;
    }

    //    @Nonnull
    //    @Override
    //    public String getGroup() {
    //
    //        return DYNAMO_LAPIDARY_BLOCK.getTranslationKey();
    //    }
    //
    //    @Nonnull
    //    @Override
    //    public ItemStack getIcon() {
    //
    //        return new ItemStack(DYNAMO_LAPIDARY_BLOCK);
    //    }

}
