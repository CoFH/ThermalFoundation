package cofh.thermal.lib.util.recipes;

import cofh.lib.util.helpers.MathHelper;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class MachineRecipeSerializer<T extends ThermalRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    protected final int defaultEnergy;
    protected final IFactory<T> factory;

    public MachineRecipeSerializer(IFactory<T> factory, int defaultEnergy) {

        this.factory = factory;
        this.defaultEnergy = defaultEnergy;
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {

        int energy = defaultEnergy;
        float experience = 0.0F;

        ArrayList<Ingredient> inputItems = new ArrayList<>();
        ArrayList<FluidStack> inputFluids = new ArrayList<>();
        ArrayList<ItemStack> outputItems = new ArrayList<>();
        ArrayList<Float> outputItemChances = new ArrayList<>();
        ArrayList<FluidStack> outputFluids = new ArrayList<>();

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

        /* OUTPUT */
        if (json.has(RESULT)) {
            parseOutputs(outputItems, outputItemChances, outputFluids, json.get(RESULT));
        } else if (json.has(RESULTS)) {
            parseOutputs(outputItems, outputItemChances, outputFluids, json.get(RESULTS));
        } else if (json.has(OUTPUT)) {
            parseOutputs(outputItems, outputItemChances, outputFluids, json.get(OUTPUT));
        } else if (json.has(OUTPUTS)) {
            parseOutputs(outputItems, outputItemChances, outputFluids, json.get(OUTPUTS));
        }

        /* ENERGY */
        if (json.has(ENERGY)) {
            energy = json.get(ENERGY).getAsInt();
        }
        if (json.has(ENERGY_MOD)) {
            energy *= json.get(ENERGY_MOD).getAsFloat();
        }
        energy = MathHelper.clamp(energy, 0, Integer.MAX_VALUE);

        /* XP */
        if (json.has(EXPERIENCE)) {
            experience = json.get(EXPERIENCE).getAsFloat();
        } else if (json.has(XP)) {
            experience = json.get(XP).getAsFloat();
        }
        return factory.create(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {

        int energy = buffer.readVarInt();
        float experience = buffer.readFloat();

        int numInputItems = buffer.readVarInt();
        ArrayList<Ingredient> inputItems = new ArrayList<>(numInputItems);
        for (int i = 0; i < numInputItems; ++i) {
            inputItems.add(Ingredient.fromNetwork(buffer));
        }

        int numInputFluids = buffer.readVarInt();
        ArrayList<FluidStack> inputFluids = new ArrayList<>(numInputFluids);
        for (int i = 0; i < numInputFluids; ++i) {
            inputFluids.add(buffer.readFluidStack());
        }

        int numOutputItems = buffer.readVarInt();
        ArrayList<ItemStack> outputItems = new ArrayList<>(numOutputItems);
        ArrayList<Float> outputItemChances = new ArrayList<>(numOutputItems);
        for (int i = 0; i < numOutputItems; ++i) {
            outputItems.add(buffer.readItem());
            outputItemChances.add(buffer.readFloat());
        }

        int numOutputFluids = buffer.readVarInt();
        ArrayList<FluidStack> outputFluids = new ArrayList<>(numOutputFluids);
        for (int i = 0; i < numOutputFluids; ++i) {
            outputFluids.add(buffer.readFluidStack());
        }
        return factory.create(recipeId, energy, experience, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, T recipe) {

        buffer.writeVarInt(recipe.energy);
        buffer.writeFloat(recipe.xp);

        int numInputItems = recipe.inputItems.size();
        buffer.writeVarInt(numInputItems);
        for (int i = 0; i < numInputItems; ++i) {
            recipe.inputItems.get(i).toNetwork(buffer);
        }
        int numInputFluids = recipe.inputFluids.size();
        buffer.writeVarInt(numInputFluids);
        for (int i = 0; i < numInputFluids; ++i) {
            buffer.writeFluidStack(recipe.inputFluids.get(i));
        }
        int numOutputItems = recipe.outputItems.size();
        buffer.writeVarInt(numOutputItems);
        for (int i = 0; i < numOutputItems; ++i) {
            buffer.writeItem(recipe.outputItems.get(i));
            buffer.writeFloat(recipe.outputItemChances.get(i));
        }
        int numOutputFluids = recipe.outputFluids.size();
        buffer.writeVarInt(numOutputFluids);
        for (int i = 0; i < numOutputFluids; ++i) {
            buffer.writeFluidStack(recipe.outputFluids.get(i));
        }
    }

    public interface IFactory<T extends ThermalRecipe> {

        T create(ResourceLocation recipeId, int energy, float experience, List<Ingredient> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids);

    }

}
