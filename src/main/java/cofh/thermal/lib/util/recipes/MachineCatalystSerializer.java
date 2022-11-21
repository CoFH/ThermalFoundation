package cofh.thermal.lib.util.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class MachineCatalystSerializer<T extends ThermalCatalyst> implements RecipeSerializer<T> {

    protected final IFactory<T> factory;

    public MachineCatalystSerializer(IFactory<T> factory) {

        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        Ingredient ingredient;

        float primaryMod = 1.0F;
        float secondaryMod = 1.0F;
        float energyMod = 1.0F;
        float minChance = 0.0F;
        float useChance = 1.0F;

        /* INPUT */
        ingredient = parseIngredient(json.get(INGREDIENT));

        if (json.has(PRIMARY_MOD)) {
            primaryMod = json.get(PRIMARY_MOD).getAsFloat();
        }
        if (json.has(SECONDARY_MOD)) {
            secondaryMod = json.get(SECONDARY_MOD).getAsFloat();
        }
        if (json.has(ENERGY_MOD)) {
            energyMod = json.get(ENERGY_MOD).getAsFloat();
        }
        if (json.has(MIN_CHANCE)) {
            minChance = json.get(MIN_CHANCE).getAsFloat();
        }
        if (json.has(USE_CHANCE)) {
            useChance = json.get(USE_CHANCE).getAsFloat();
        }
        return factory.create(recipeId, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        Ingredient ingredient = Ingredient.fromNetwork(buffer);

        float primaryMod = buffer.readFloat();
        float secondaryMod = buffer.readFloat();
        float energyMod = buffer.readFloat();
        float minChance = buffer.readFloat();
        float useChance = buffer.readFloat();

        return factory.create(recipeId, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {

        recipe.ingredient.toNetwork(buffer);

        buffer.writeFloat(recipe.primaryMod);
        buffer.writeFloat(recipe.secondaryMod);
        buffer.writeFloat(recipe.energyMod);
        buffer.writeFloat(recipe.minChance);
        buffer.writeFloat(recipe.useChance);
    }

    public interface IFactory<T extends ThermalCatalyst> {

        T create(ResourceLocation recipeId, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance);

    }

}
