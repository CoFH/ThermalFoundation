package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.PyrolyzerRecipe;
import cofh.thermal.lib.compat.crt.RecipePrintingUtil;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
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
@ZenCodeType.Name ("mods.thermal.Pyrolyzer")
@IRecipeHandler.For (PyrolyzerRecipe.class)
public class CRTPyrolyzerManager implements IRecipeManager, IRecipeHandler<PyrolyzerRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IFluidStack outputFluid, IIngredientWithAmount ingredient, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).output(outputs).output(outputFluid);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(PyrolyzerRecipe::new)));
    }

    @Override
    public RecipeType<PyrolyzerRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_PYROLYZER;
    }

    @Override
    public void remove(IIngredient output) {

        removeRecipe(new IIngredient[]{output}, new IFluidStack[0]);
    }

    @ZenCodeType.Method
    public void removeRecipe(IIngredient[] itemOutputs, IFluidStack[] fluidOutputs) {

        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, itemOutputs, fluidOutputs));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, PyrolyzerRecipe recipe) {

        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), RecipePrintingUtil.stringifyFluidStacks(recipe.getOutputFluids(), " | "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, PyrolyzerRecipe>> replaceIngredients(IRecipeManager manager, PyrolyzerRecipe recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {

        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTRecipe(id).energy(recipe.getEnergy()).setInputItems(newIngredients).setOutputItems(recipe.getOutputItems(), recipe.getOutputItemChances()).setOutputFluids(recipe.getOutputFluids()).recipe(PyrolyzerRecipe::new));
    }

}
