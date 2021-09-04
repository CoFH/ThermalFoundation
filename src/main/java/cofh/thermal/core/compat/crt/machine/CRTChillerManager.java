package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.ChillerRecipe;
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
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Chiller")
@IRecipeHandler.For(ChillerRecipe.class)
public class CRTChillerManager implements IRecipeManager, IRecipeHandler<ChillerRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient ingredient, CTFluidIngredient inputFluid, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).input(inputFluid).output(output);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(ChillerRecipe::new)));
    }

    @Override
    public IRecipeType<ChillerRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_CHILLER;
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
    public String dumpToCommandString(IRecipeManager manager, ChillerRecipe recipe) {
        return String.format("<recipetype:%s>.addRecipe(\"%s\", %s, %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyStacks(recipe.getOutputItems(), " | "), recipe.getInputItems().isEmpty() ? MCItemStack.EMPTY.get().getCommandString() : RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), CRTHelper.stringifyFluidIngredients(recipe.getInputFluids()), recipe.getEnergy());
    }
}
