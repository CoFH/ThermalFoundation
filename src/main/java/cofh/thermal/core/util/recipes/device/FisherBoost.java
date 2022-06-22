package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.BOOST_FISHER;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_BOOST_FISHER;

public class FisherBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected ResourceLocation lootTable;
    protected float outputMod;
    protected float useChance;

    public FisherBoost(ResourceLocation recipeId, Ingredient inputItem, ResourceLocation lootTable, float outputMod, float useChance) {

        super(recipeId);

        this.ingredient = inputItem;

        this.lootTable = lootTable;
        this.outputMod = outputMod;
        this.useChance = useChance;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_BOOST_FISHER);
    }

    @Override
    public RecipeType<?> getType() {

        return BOOST_FISHER.get();
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
