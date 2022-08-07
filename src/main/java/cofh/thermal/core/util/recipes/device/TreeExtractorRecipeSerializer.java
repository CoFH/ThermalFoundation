package cofh.thermal.core.util.recipes.device;

import cofh.lib.block.BlockIngredient;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class TreeExtractorRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TreeExtractorRecipe> {

    @Override
    public TreeExtractorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

        BlockIngredient logs = BlockIngredient.EMPTY;
        BlockIngredient leaves = BlockIngredient.EMPTY;
        Block sapling = Blocks.AIR;
        FluidStack fluid = FluidStack.EMPTY;
        int minLeaves = 3;
        int maxLeaves = 3;
        int minHeight = 3;
        int maxHeight = 3;

        if (json.has(TRUNK)) {
            logs = parseBlockIngredient(json.get(TRUNK));
        }

        if (json.has(LEAF)) {
            leaves = parseBlockIngredient(json.get(LEAF));
        } else if (json.has(LEAVES)) {
            leaves = parseBlockIngredient(json.get(LEAVES));
        }

        if (json.has(SAPLING)) {
            sapling = parseBlock(json.get(SAPLING));
        }

        if (json.has(RESULT)) {
            fluid = parseFluidStack(json.get(RESULT));
        } else if (json.has(FLUID)) {
            fluid = parseFluidStack(json.get(FLUID));
        }

        if (json.has(MIN_HEIGHT)) {
            minHeight = json.get(MIN_HEIGHT).getAsInt();
        }
        if (json.has(MAX_HEIGHT)) {
            maxHeight = json.get(MAX_HEIGHT).getAsInt();
        }
        if (json.has(MIN_LEAVES)) {
            minLeaves = json.get(MIN_LEAVES).getAsInt();
        }
        if (json.has(MAX_LEAVES)) {
            maxLeaves = json.get(MAX_LEAVES).getAsInt();
        }
        return new TreeExtractorRecipe(recipeId, logs, leaves, sapling, fluid, minHeight, Math.max(maxHeight, minHeight), minLeaves, Math.max(maxLeaves, minLeaves));
    }

    @Nullable
    @Override
    public TreeExtractorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        BlockIngredient logs = BlockIngredient.fromNetwork(buffer);
        BlockIngredient leaves = BlockIngredient.fromNetwork(buffer);
        Block sapling = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        FluidStack fluid = buffer.readFluidStack();
        int minHeight = buffer.readInt();
        int maxHeight = buffer.readInt();
        int minLeaves = buffer.readInt();
        int maxLeaves = buffer.readInt();

        return new TreeExtractorRecipe(recipeId, logs, leaves, sapling, fluid, minHeight, maxHeight, minLeaves, maxLeaves);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, TreeExtractorRecipe recipe) {

        recipe.trunk.toNetwork(buffer);
        recipe.leaves.toNetwork(buffer);
        buffer.writeResourceLocation(recipe.sapling.getRegistryName());
        buffer.writeFluidStack(recipe.fluid);
        buffer.writeInt(recipe.minHeight);
        buffer.writeInt(recipe.maxHeight);
        buffer.writeInt(recipe.minLeaves);
        buffer.writeInt(recipe.maxLeaves);
    }

}
