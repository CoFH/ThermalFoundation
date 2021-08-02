package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.init.TCoreRecipeTypes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_BOOST_POTION_DIFFUSER;

public class PotionDiffuserBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected int amplifier;
    protected float durationMod;
    protected int cycles;

    protected PotionDiffuserBoost(ResourceLocation recipeId, Ingredient inputItem, int amplifier, float durationMod, int cycles) {

        super(recipeId);

        this.ingredient = inputItem;

        this.amplifier = amplifier;
        this.durationMod = durationMod;
        this.cycles = cycles;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_BOOST_POTION_DIFFUSER);
    }

    @Override
    public IRecipeType<?> getType() {

        return TCoreRecipeTypes.BOOST_POTION_DIFFUSER;
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public int getAmplifier() {

        return amplifier;
    }

    public float getDurationMod() {

        return durationMod;
    }

    public int getCycles() {

        return cycles;
    }
    // endregion
}
