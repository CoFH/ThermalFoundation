package cofh.thermal.core.util.recipes.dynamo;

import cofh.lib.fluid.FluidIngredient;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.managers.dynamo.CompressionFuelManager;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static cofh.thermal.core.init.TCoreRecipeSerializers.COMPRESSION_FUEL_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.COMPRESSION_FUEL;

public class CompressionFuel extends ThermalFuel {

    public CompressionFuel(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidIngredient> inputFluids) {

        super(recipeId, energy, inputItems, inputFluids);

        int minEnergy = CompressionFuelManager.MIN_ENERGY;
        int maxEnergy = CompressionFuelManager.MAX_ENERGY;

        if (this.energy < minEnergy || this.energy > maxEnergy) {
            ThermalCore.LOG.warn("Energy value for " + recipeId + " was out of allowable range and has been clamped between + " + minEnergy + " and " + maxEnergy + ".");
            this.energy = MathHelper.clamp(this.energy, minEnergy, maxEnergy);
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {

        return COMPRESSION_FUEL_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {

        return COMPRESSION_FUEL.get();
    }

}
