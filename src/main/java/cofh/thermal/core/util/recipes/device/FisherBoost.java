package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.init.TCoreRecipeTypes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_BOOST_FISHER;

public class FisherBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected ResourceLocation lootTable;
    protected float outputMod;
    protected float useChance;

    protected FisherBoost(ResourceLocation recipeId, Ingredient inputItem, ResourceLocation lootTable, float outputMod, float useChance) {

        super(recipeId);

        this.ingredient = inputItem;

        this.lootTable = lootTable;
        this.outputMod = outputMod;
        this.useChance = useChance;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_BOOST_FISHER);
    }

    @Override
    public IRecipeType<?> getType() {

        return TCoreRecipeTypes.BOOST_FISHER;
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public ResourceLocation getLootTable() {

        return lootTable;
    }

    public float getOutputMod() {

        return outputMod;
    }

    public float getUseChance() {

        return useChance;
    }
    // endregion
}
