package cofh.thermal.lib.util.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class MachineCatalystSerializer<T extends ThermalCatalyst> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    protected final IFactory<T> factory;

    public MachineCatalystSerializer(IFactory<T> factory) {

        this.factory = factory;
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {

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
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {

        Ingredient ingredient = Ingredient.read(buffer);

        float primaryMod = buffer.readFloat();
        float secondaryMod = buffer.readFloat();
        float energyMod = buffer.readFloat();
        float minChance = buffer.readFloat();
        float useChance = buffer.readFloat();

        return factory.create(recipeId, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {

        recipe.ingredient.write(buffer);

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
