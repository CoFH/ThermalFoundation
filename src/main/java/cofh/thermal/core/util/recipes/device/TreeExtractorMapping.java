package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.TCoreRecipeSerializers.TREE_EXTRACTOR_SERIALIZER;
import static cofh.thermal.core.init.TCoreRecipeTypes.TREE_EXTRACTOR_MAPPING;

public class TreeExtractorMapping extends SerializableRecipe {

    protected final Block trunk;
    protected final Block leaves;
    protected final FluidStack fluid;

    public TreeExtractorMapping(ResourceLocation recipeId, Block trunk, Block leaves, FluidStack fluid) {

        super(recipeId);

        this.trunk = trunk;
        this.leaves = leaves;
        this.fluid = fluid;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return TREE_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return TREE_EXTRACTOR_MAPPING.get();
    }

    // region GETTERS
    public Block getTrunk() {

        return trunk;
    }

    public Block getLeaves() {

        return leaves;
    }

    public FluidStack getFluid() {

        return fluid;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<TreeExtractorMapping> {

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

            buffer.writeResourceLocation(getRegistryName(recipe.trunk));
            buffer.writeResourceLocation(getRegistryName(recipe.leaves));
            buffer.writeFluidStack(recipe.fluid);
        }

    }
    // endregion
}
