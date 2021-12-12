package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.CentrifugeRecipe;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.Centrifuge")
@IRecipeHandler.For (CentrifugeRecipe.class)
public class CRTCentrifugeManager implements IRecipeManager, IRecipeHandler<CentrifugeRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IFluidStack outputFluid, IIngredientWithAmount ingredient, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).output(outputs).output(outputFluid);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(CentrifugeRecipe::new)));
    }

    @Override
    public IRecipeType<CentrifugeRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_CENTRIFUGE;
    }

    @Override
    public void removeRecipe(IItemStack output) {

        removeRecipe(new IItemStack[]{output}, new IFluidStack[0]);
    }

    @ZenCodeType.Method
    public void removeRecipe(IItemStack[] itemOutputs, IFluidStack[] fluidOutputs) {

        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, itemOutputs, fluidOutputs));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, CentrifugeRecipe recipe) {

        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), recipe.getOutputFluids().isEmpty() ? MCFluidStack.EMPTY.get() : RecipePrintingUtil.stringifyFluidStacks(recipe.getOutputFluids(), " | "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, CentrifugeRecipe>> replaceIngredients(IRecipeManager manager, CentrifugeRecipe recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {

        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTRecipe(id).energy(recipe.getEnergy()).setInputItems(newIngredients).setOutputFluids(recipe.getOutputFluids()).setOutputItems(recipe.getOutputItems(), recipe.getOutputItemChances()).recipe(CentrifugeRecipe::new));
    }

}
