package cofh.thermal.core.util.recipes.device;

import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class TreeExtractorBoostSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TreeExtractorBoost> {

    @Override
    public TreeExtractorBoost read(ResourceLocation recipeId, JsonObject json) {

        Ingredient ingredient;
        float outputMod = 1.0F;
        int cycles = TreeExtractorManager.instance().getDefaultEnergy();

        /* INPUT */
        ingredient = parseIngredient(json.get(INGREDIENT));

        if (json.has(OUTPUT)) {
            outputMod = json.get(OUTPUT).getAsFloat();
        } else if (json.has(OUTPUT_MOD)) {
            outputMod = json.get(OUTPUT_MOD).getAsFloat();
        }
        if (json.has(CYCLES)) {
            cycles = json.get(CYCLES).getAsInt();
        }
        return new TreeExtractorBoost(recipeId, ingredient, outputMod, cycles);
    }

    @Nullable
    @Override
    public TreeExtractorBoost read(ResourceLocation recipeId, PacketBuffer buffer) {

        Ingredient ingredient = Ingredient.read(buffer);

        float outputMod = buffer.readFloat();
        int cycles = buffer.readInt();

        return new TreeExtractorBoost(recipeId, ingredient, outputMod, cycles);
    }

    @Override
    public void write(PacketBuffer buffer, TreeExtractorBoost recipe) {

        recipe.ingredient.write(buffer);

        buffer.writeFloat(recipe.outputMod);
        buffer.writeInt(recipe.cycles);
    }

}
