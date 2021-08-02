package cofh.thermal.core.util.recipes.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;

public class SmelterCatalyst extends ThermalCatalyst {

    public SmelterCatalyst(ResourceLocation recipeId, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        super(recipeId, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(TCoreRecipeTypes.ID_CATALYST_SMELTER);
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {

        return TCoreRecipeTypes.CATALYST_SMELTER;
    }

}
