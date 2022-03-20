package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.init.TCoreRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_MAPPING_TREE_EXTRACTOR;

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

        return RECIPE_SERIALIZERS.get(ID_MAPPING_TREE_EXTRACTOR);
    }

    @Override
    public RecipeType<?> getType() {

        return TCoreRecipeTypes.MAPPING_TREE_EXTRACTOR;
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
}
