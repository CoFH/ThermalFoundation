package cofh.thermal.core.util.recipes.device;

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

public class TreeExtractorMappingSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TreeExtractorMapping> {

    @Override
    public TreeExtractorMapping fromJson(ResourceLocation recipeId, JsonObject json) {

        Block trunk = Blocks.AIR;
        Block leaves = Blocks.AIR;
        FluidStack fluid = FluidStack.EMPTY;

        if (json.has(TRUNK)) {
            trunk = parseBlock(json.get(TRUNK));
        }

        if (json.has(LEAF)) {
            leaves = parseBlock(json.get(LEAF));
        } else if (json.has(LEAVES)) {
            leaves = parseBlock(json.get(LEAVES));
        }

        if (json.has(RESULT)) {
            fluid = parseFluidStack(json.get(RESULT));
        } else if (json.has(FLUID)) {
            fluid = parseFluidStack(json.get(FLUID));
        }
        return new TreeExtractorMapping(recipeId, trunk, leaves, fluid);
    }

    @Nullable
    @Override
    public TreeExtractorMapping fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        Block trunk = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        Block leaves = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        FluidStack fluid = buffer.readFluidStack();

        return new TreeExtractorMapping(recipeId, trunk, leaves, fluid);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, TreeExtractorMapping recipe) {

        buffer.writeResourceLocation(recipe.trunk.getRegistryName());
        buffer.writeResourceLocation(recipe.leaves.getRegistryName());
        buffer.writeFluidStack(recipe.fluid);
    }

}
