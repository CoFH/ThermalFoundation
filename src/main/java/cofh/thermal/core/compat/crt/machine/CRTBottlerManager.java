package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.BottlerRecipe;
import cofh.thermal.lib.compat.crt.RecipePrintingUtil;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.Bottler")
@IRecipeHandler.For (BottlerRecipe.class)
public class CRTBottlerManager implements IRecipeManager, IRecipeHandler<BottlerRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredientWithAmount ingredient, CTFluidIngredient fluidInput, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).input(fluidInput).output(output);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(BottlerRecipe::new)));
    }

    @Override
    public RecipeType<BottlerRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_BOTTLER;
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
    public String dumpToCommandString(IRecipeManager manager, BottlerRecipe recipe) {

        return String.format("<recipetype:%s>.addRecipe(\"%s\", %s, %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyStacks(recipe.getOutputItems(), " | "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), CRTHelper.stringifyFluidIngredients(recipe.getInputFluids()), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, BottlerRecipe>> replaceIngredients(IRecipeManager manager, BottlerRecipe recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {

        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTRecipe(id).energy(recipe.getEnergy()).setInputItems(newIngredients).setInputFluids(recipe.getInputFluids()).setOutputItems(recipe.getOutputItems(), recipe.getOutputItemChances()).recipe(BottlerRecipe::new));
    }

}
