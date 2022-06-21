package cofh.thermal.lib.util.recipes;

import cofh.lib.content.fluid.FluidIngredient;
import cofh.lib.util.helpers.MathHelper;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class DynamoFuelSerializer<T extends ThermalFuel> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {

    protected final int defaultEnergy;
    protected final int minEnergy;
    protected final int maxEnergy;
    protected final IFactory<T> factory;

    public DynamoFuelSerializer(IFactory<T> factory, int defaultEnergy, int minEnergy, int maxEnergy) {

        this.factory = factory;
        this.defaultEnergy = defaultEnergy;
        this.minEnergy = minEnergy;
        this.maxEnergy = maxEnergy;
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        int energy = defaultEnergy;

        ArrayList<Ingredient> inputItems = new ArrayList<>();
        ArrayList<FluidIngredient> inputFluids = new ArrayList<>();

        /* INPUT */
        if (json.has(INGREDIENT)) {
            parseInputs(inputItems, inputFluids, json.get(INGREDIENT));
        } else if (json.has(INGREDIENTS)) {
            parseInputs(inputItems, inputFluids, json.get(INGREDIENTS));
        } else if (json.has(INPUT)) {
            parseInputs(inputItems, inputFluids, json.get(INPUT));
        } else if (json.has(INPUTS)) {
            parseInputs(inputItems, inputFluids, json.get(INPUTS));
        }

        /* ENERGY */
        if (json.has(ENERGY)) {
            energy = json.get(ENERGY).getAsInt();
        }
        if (json.has(ENERGY_MOD)) {
            energy *= json.get(ENERGY_MOD).getAsFloat();
        }
        energy = MathHelper.clamp(energy, minEnergy, maxEnergy);

        return factory.create(recipeId, energy, inputItems, inputFluids);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        int energy = buffer.readVarInt();

        int numInputItems = buffer.readVarInt();
        ArrayList<Ingredient> inputItems = new ArrayList<>(numInputItems);
        for (int i = 0; i < numInputItems; ++i) {
            inputItems.add(Ingredient.fromNetwork(buffer));
        }

        int numInputFluids = buffer.readVarInt();
        ArrayList<FluidIngredient> inputFluids = new ArrayList<>(numInputFluids);
        for (int i = 0; i < numInputFluids; ++i) {
            inputFluids.add(FluidIngredient.fromNetwork(buffer));
        }
        return factory.create(recipeId, energy, inputItems, inputFluids);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {

        buffer.writeVarInt(recipe.energy);

        int numInputItems = recipe.inputItems.size();
        buffer.writeVarInt(numInputItems);
        for (int i = 0; i < numInputItems; ++i) {
            recipe.inputItems.get(i).toNetwork(buffer);
        }
        int numInputFluids = recipe.inputFluids.size();
        buffer.writeVarInt(numInputFluids);
        for (int i = 0; i < numInputFluids; ++i) {
            recipe.inputFluids.get(i).toNetwork(buffer);
        }
    }

    public interface IFactory<T extends ThermalFuel> {

        T create(ResourceLocation recipeId, int energy, List<Ingredient> inputItems, List<FluidIngredient> inputFluids);

    }

}
