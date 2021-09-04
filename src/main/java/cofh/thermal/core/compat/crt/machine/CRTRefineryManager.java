package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.PyrolyzerRecipe;
import cofh.thermal.core.util.recipes.machine.RefineryRecipe;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.*;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Refinery")
@IRecipeHandler.For(RefineryRecipe.class)
public class CRTRefineryManager implements IRecipeManager, IRecipeHandler<RefineryRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack itemOutput, IFluidStack[] fluidsOutput, CTFluidIngredient inputFluid, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(inputFluid).output(fluidsOutput).output(itemOutput);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(RefineryRecipe::new)));
    }

    @Override
    public IRecipeType<RefineryRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_REFINERY;
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
    public String dumpToCommandString(IRecipeManager manager, RefineryRecipe recipe) {
        return String.format("<recipetype:%s>.addRecipe(\"%s\", %s, [%s], %s, %s);", recipe.getType(), recipe.getId(), recipe.getOutputItems().isEmpty() ? MCItemStack.EMPTY.get().getCommandString() : RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), " | "), RecipePrintingUtil.stringifyFluidStacks(recipe.getOutputFluids(), ", "), CRTHelper.stringifyFluidIngredients(recipe.getInputFluids()), recipe.getEnergy());
    }

}
