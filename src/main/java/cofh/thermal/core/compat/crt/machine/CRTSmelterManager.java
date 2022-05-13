package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.SmelterRecipe;
import cofh.thermal.lib.compat.crt.RecipePrintingUtil;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.Smelter")
@IRecipeHandler.For (SmelterRecipe.class)
public class CRTSmelterManager implements IRecipeManager, IRecipeHandler<SmelterRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredientWithAmount[] ingredients, float experience, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredients).output(outputs).experience(experience);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(SmelterRecipe::new)));
    }

    @Override
    public RecipeType<SmelterRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_SMELTER;
    }

    @Override
    public void remove(IIngredient output) {

        removeRecipe(output);
    }

    @ZenCodeType.Method
    public void removeRecipe(IIngredient... output) {

        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, output));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, SmelterRecipe recipe) {

        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], [%s], %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), ", "), recipe.getXp(), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, SmelterRecipe>> replaceIngredients(IRecipeManager manager, SmelterRecipe recipe, List<IReplacementRule> rules) {

        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTRecipe(id).energy(recipe.getEnergy()).setInputItems(newIngredients).setOutputItems(recipe.getOutputItems(), recipe.getOutputItemChances()).experience(recipe.getXp()).recipe(SmelterRecipe::new));
    }

}
