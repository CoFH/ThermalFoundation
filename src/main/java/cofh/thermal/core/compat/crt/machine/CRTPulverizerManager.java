package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.PulverizerRecipe;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Pulverizer")
@IRecipeHandler.For(PulverizerRecipe.class)
public class CRTPulverizerManager implements IRecipeManager, IRecipeHandler<PulverizerRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IIngredient ingredient, float experience, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).output(outputs).experience(experience);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(PulverizerRecipe::new)));
    }

    @Override
    public IRecipeType<PulverizerRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_PULVERIZER;
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
    public String dumpToCommandString(IRecipeManager manager, PulverizerRecipe recipe) {
        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getXp(), recipe.getEnergy());
    }

}
