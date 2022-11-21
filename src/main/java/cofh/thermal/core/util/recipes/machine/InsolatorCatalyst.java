package cofh.thermal.core.util.recipes.machine;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;

import static cofh.thermal.core.init.TCoreRecipeSerializers.INSOLATOR_CATALYST_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.INSOLATOR_CATALYST;

public class InsolatorCatalyst extends ThermalCatalyst {

    public InsolatorCatalyst(ResourceLocation recipeId, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        super(recipeId, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return INSOLATOR_CATALYST_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return INSOLATOR_CATALYST.get();
    }

}
