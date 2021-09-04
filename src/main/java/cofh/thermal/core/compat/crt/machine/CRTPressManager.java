package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.PressRecipe;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Press")
@IRecipeHandler.For(PressRecipe.class)
public class CRTPressManager implements IRecipeManager, IRecipeHandler<PressRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IFluidStack outputFluid, IIngredient[] ingredients, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredients).output(outputs).output(outputFluid);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(PressRecipe::new)));
    }

    @Override
    public IRecipeType<PressRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_PRESS;
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
    public String dumpToCommandString(IRecipeManager manager, PressRecipe recipe) {
        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], %s, [%s], %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), recipe.getOutputFluids().isEmpty() ? MCFluidStack.EMPTY.get().getCommandString() : RecipePrintingUtil.stringifyFluidStacks(recipe.getOutputFluids(), " | "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), ", "), recipe.getEnergy());
    }

}
