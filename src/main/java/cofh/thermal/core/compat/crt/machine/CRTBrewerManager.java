package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.BrewerRecipe;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Brewer")
@IRecipeHandler.For(BrewerRecipe.class)
public class CRTBrewerManager implements IRecipeManager, IRecipeHandler<BrewerRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IFluidStack output, IIngredientWithAmount ingredient, CTFluidIngredient fluidInput, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).input(fluidInput).output(output);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(BrewerRecipe::new)));
    }

    @Override
    public IRecipeType<BrewerRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_BREWER;
    }

    @Override
    public void removeRecipe(IIngredient output) {

        throw new IllegalArgumentException("The Brewer only outputs fluids! Please provide an IFluidStack");
    }

    @ZenCodeType.Method
    public void removeRecipe(IFluidStack output) {

        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, new IFluidStack[]{output}));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, BrewerRecipe recipe) {

        return String.format("<recipetype:%s>.addRecipe(\"%s\", %s, %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyFluidStacks(recipe.getOutputFluids(), " | "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), CRTHelper.stringifyFluidIngredients(recipe.getInputFluids()), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, BrewerRecipe>> replaceIngredients(IRecipeManager manager, BrewerRecipe recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {

        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTRecipe(id).energy(recipe.getEnergy()).setInputItems(newIngredients).setInputFluids(recipe.getInputFluids()).setOutputFluids(recipe.getOutputFluids()).recipe(BrewerRecipe::new));
    }

}
