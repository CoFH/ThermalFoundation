package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.TCoreRecipeSerializers.TREE_EXTRACTOR_BOOST_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.TREE_EXTRACTOR_BOOST;

public class TreeExtractorBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected float outputMod;
    protected int cycles;

    public TreeExtractorBoost(ResourceLocation recipeId, Ingredient inputItem, float outputMod, int cycles) {

        super(recipeId);

        this.ingredient = inputItem;

        this.outputMod = outputMod;
        this.cycles = cycles;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return TREE_EXTRACTOR_BOOST_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return TREE_EXTRACTOR_BOOST.get();
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public float getOutputMod() {

        return outputMod;
    }

    public int getCycles() {

        return cycles;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<TreeExtractorBoost> {

        @Override
        public TreeExtractorBoost fromJson(ResourceLocation recipeId, JsonObject json) {

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
        public TreeExtractorBoost fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            Ingredient ingredient = Ingredient.fromNetwork(buffer);

            float outputMod = buffer.readFloat();
            int cycles = buffer.readInt();

            return new TreeExtractorBoost(recipeId, ingredient, outputMod, cycles);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TreeExtractorBoost recipe) {

            recipe.ingredient.toNetwork(buffer);

            buffer.writeFloat(recipe.outputMod);
            buffer.writeInt(recipe.cycles);
        }

    }
    // endregion
}
