package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.InsolatorRecipe;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Insolator")
@IRecipeHandler.For(InsolatorRecipe.class)
public class CRTInsolatorManager implements IRecipeManager, IRecipeHandler<InsolatorRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IIngredient ingredient, int fluidAmount, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).input(new CTFluidIngredient.FluidStackIngredient(new MCFluidStack(new FluidStack(Fluids.WATER, fluidAmount)))).output(outputs);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(InsolatorRecipe::new)));
    }

    @Override
    public IRecipeType<InsolatorRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_INSOLATOR;
    }

    @Override
    public void removeRecipe(IItemStack output) {

        removeRecipe(new IItemStack[]{output});
    }

    @ZenCodeType.Method
    public void removeRecipe(IItemStack... output) {

        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, output));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, InsolatorRecipe recipe) {
        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getInputFluids().get(0).getFluids()[0].getAmount(), recipe.getEnergy());
    }

}
