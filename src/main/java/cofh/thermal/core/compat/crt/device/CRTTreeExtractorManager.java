package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.TreeExtractor")
@IRecipeHandler.For (TreeExtractorMapping.class)
public class CRTTreeExtractorManager implements IRecipeManager, IRecipeHandler<TreeExtractorMapping> {

    @ZenCodeType.Method
    public void addMapping(String name, Block trunk, Block leaves, IFluidStack fluid) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        if (!(trunk instanceof RotatedPillarBlock)) {
            throw new IllegalArgumentException(String.format("Cannot add Tree Extractor Mapping with a non 'RotatedPillarBlock' trunk, Provided: '%s'", ExpandBlock.getCommandString(trunk)));
        }
        if (!(leaves instanceof LeavesBlock)) {
            throw new IllegalArgumentException(String.format("Cannot add Tree Extractor Mapping with a non 'LeavesBlock' leaves. Provided: '%s'", ExpandBlock.getCommandString(leaves)));
        }
        if (fluid.isEmpty()) {
            throw new IllegalArgumentException("Cannot add Tree Extractor Mapping with an empty Fluid.");
        }

        TreeExtractorMapping mapping = new TreeExtractorMapping(resourceLocation, trunk, leaves, fluid.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public RecipeType<TreeExtractorMapping> getRecipeType() {

        return TCoreRecipeTypes.MAPPING_TREE_EXTRACTOR;
    }

    @Override
    public void remove(IIngredient output) {

        throw new IllegalArgumentException("Tree Extractor mappings can only be removed with the output FluidStack or the trunk Block!");
    }

    @ZenCodeType.Method
    public void removeMapping(Block trunk) {

        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof TreeExtractorMapping) {
                return trunk == ((TreeExtractorMapping) recipe).getTrunk();
            }
            return false;
        }));
    }

    @ZenCodeType.Method
    public void removeMapping(CTFluidIngredient fluid) {

        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof TreeExtractorMapping) {
                return CRTHelper.mapFluidIngredient(fluid).test(((TreeExtractorMapping) recipe).getFluid());
            }
            return false;
        }));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, TreeExtractorMapping recipe) {

        return String.format("<recipetype:%s>.addMapping(\"%s\", %s, %s, %s);", recipe.getType(), recipe.getId(), ExpandBlock.getCommandString(recipe.getTrunk()), ExpandBlock.getCommandString(recipe.getLeaves()), new MCFluidStackMutable(recipe.getFluid()).getCommandString());
    }

    @Override
    public Optional<Function<ResourceLocation, TreeExtractorMapping>> replaceIngredients(IRecipeManager manager, TreeExtractorMapping recipe, List<IReplacementRule> rules) {
        // CRT doesn't support replacing blocks right now.
        return Optional.empty();
    }

}
