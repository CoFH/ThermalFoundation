package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.init.TCoreRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_MAPPING_ROCK_GEN;

public class RockGenMapping extends SerializableRecipe {

    protected final int time;
    protected final Block below;
    protected final Block adjacent;
    protected final ItemStack result;

    protected RockGenMapping(ResourceLocation recipeId, int time, Block below, Block adjacent, ItemStack result) {

        super(recipeId);

        this.time = time;
        this.below = below;
        this.adjacent = adjacent;
        this.result = result;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_MAPPING_ROCK_GEN);
    }

    @Override
    public IRecipeType<?> getType() {

        return TCoreRecipeTypes.MAPPING_ROCK_GEN;
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
