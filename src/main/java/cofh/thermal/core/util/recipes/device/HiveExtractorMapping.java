package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.init.TCoreRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;

import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.init.TCoreRecipeTypes.ID_MAPPING_HIVE_EXTRACTOR;

public class HiveExtractorMapping extends SerializableRecipe {

    protected final Block hive;
    protected final ItemStack item;
    protected final FluidStack fluid;

    public HiveExtractorMapping(ResourceLocation recipeId, Block hive, ItemStack item, FluidStack fluid) {

        super(recipeId);

        this.hive = hive;
        this.item = item;
        this.fluid = fluid;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return RECIPE_SERIALIZERS.get(ID_MAPPING_HIVE_EXTRACTOR);
    }

    @Override
    public RecipeType<?> getType() {

        return TCoreRecipeTypes.MAPPING_HIVE_EXTRACTOR;
    }

    // region GETTERS
    public Block getHive() {

        return hive;
    }

    public ItemStack getItem() {

        return item;
    }

    public FluidStack getFluid() {

        return fluid;
    }
    // endregion
}
