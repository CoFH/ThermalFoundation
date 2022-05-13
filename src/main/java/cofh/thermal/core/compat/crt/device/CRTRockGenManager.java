package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.RockGenMapping;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.RockGen")
@IRecipeHandler.For (RockGenMapping.class)
public class CRTRockGenManager implements IRecipeManager, IRecipeHandler<RockGenMapping> {

    @ZenCodeType.Method
    public void addMapping(String name, IItemStack result, Block adjacent, Block below, int time) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        RockGenMapping mapping = new RockGenMapping(resourceLocation, time, below, adjacent, result.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public RecipeType<RockGenMapping> getRecipeType() {

        return TCoreRecipeTypes.MAPPING_ROCK_GEN;
    }

    @Override
    public void remove(IIngredient output) {

        removeMapping(output);
    }

    @ZenCodeType.Method
    public void removeMapping(IIngredient output) {

        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof RockGenMapping) {
                return output.matches(new MCItemStack(((RockGenMapping) recipe).getResult()));
            }
            return false;
        }));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, RockGenMapping recipe) {

        return String.format("<recipetype:%s>.addMapping(\"%s\", %s, %s, %s, %s);", recipe.getType(), recipe.getId(), new MCItemStack(recipe.getResult()).getCommandString(), ExpandBlock.getCommandString(recipe.getAdjacent()), ExpandBlock.getCommandString(recipe.getBelow()), recipe.getTime());
    }

    @Override
    public Optional<Function<ResourceLocation, RockGenMapping>> replaceIngredients(IRecipeManager manager, RockGenMapping recipe, List<IReplacementRule> rules) {
        // CRT doesn't support replacing blocks right now.
        return Optional.empty();
    }

}
