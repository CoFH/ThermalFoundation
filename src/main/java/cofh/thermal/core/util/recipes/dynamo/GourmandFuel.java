package cofh.thermal.core.util.recipes.dynamo;

import cofh.lib.fluid.FluidIngredient;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.managers.dynamo.GourmandFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static cofh.thermal.core.init.TCoreRecipeSerializers.GOURMAND_FUEL_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.GOURMAND_FUEL;

public class GourmandFuel extends ThermalFuel {

    public GourmandFuel(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidIngredient> inputFluids) {

        super(recipeId, energy, inputItems, inputFluids);

        int minEnergy = GourmandFuelManager.MIN_ENERGY;
        int maxEnergy = GourmandFuelManager.MAX_ENERGY;

        if (this.energy < minEnergy || this.energy > maxEnergy) {
            ThermalCore.LOG.warn("Energy value for " + recipeId + " was out of allowable range and has been clamped between + " + minEnergy + " and " + maxEnergy + ".");
            this.energy = MathHelper.clamp(this.energy, minEnergy, maxEnergy);
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return GOURMAND_FUEL_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return GOURMAND_FUEL.get();
    }

}
