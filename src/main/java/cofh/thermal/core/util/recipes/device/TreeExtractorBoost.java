package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.BOOST_TREE_EXTRACTOR;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_BOOST_TREE_EXTRACTOR;

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

        return RECIPE_SERIALIZERS.get(ID_BOOST_TREE_EXTRACTOR);
    }

    @Override
    public RecipeType<?> getType() {

        return BOOST_TREE_EXTRACTOR.get();
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
}
