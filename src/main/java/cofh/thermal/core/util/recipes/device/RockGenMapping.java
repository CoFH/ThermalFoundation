package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_MAPPING_ROCK_GEN;
import static cofh.thermal.core.init.TCoreRecipeTypes.MAPPING_ROCK_GEN;

public class RockGenMapping extends SerializableRecipe {

    protected final int time;
    protected final Block below;
    protected final Block adjacent;
    protected final ItemStack result;

    public RockGenMapping(ResourceLocation recipeId, int time, Block below, Block adjacent, ItemStack result) {

        super(recipeId);

        this.time = time;
        this.below = below;
        this.adjacent = adjacent;
        this.result = result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_MAPPING_ROCK_GEN);
    }

    @Override
    public RecipeType<?> getType() {

        return MAPPING_ROCK_GEN.get();
    }

    // region GETTERS
    public int getTime() {

        return time;
    }

    public Block getBelow() {

        return below;
    }

    public Block getAdjacent() {

        return adjacent;
    }

    public ItemStack getResult() {

        return result;
    }
    // endregion
}
