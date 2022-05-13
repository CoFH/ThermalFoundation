package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.InsolatorRecipe;
import cofh.thermal.lib.compat.crt.RecipePrintingUtil;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.lib.compat.crt.base.CRTRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.Insolator")
@IRecipeHandler.For (InsolatorRecipe.class)
public class CRTInsolatorManager implements IRecipeManager, IRecipeHandler<InsolatorRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredientWithAmount ingredient, int fluidAmount, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CRTRecipe crtRecipe = new CRTRecipe(resourceLocation).energy(energy).input(ingredient).input(new CTFluidIngredient.FluidStackIngredient(new MCFluidStack(new FluidStack(Fluids.WATER, fluidAmount)))).output(outputs);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, crtRecipe.recipe(InsolatorRecipe::new)));
    }

    @Override
    public RecipeType<InsolatorRecipe> getRecipeType() {

        return TCoreRecipeTypes.RECIPE_INSOLATOR;
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
    public String dumpToCommandString(IRecipeManager manager, InsolatorRecipe recipe) {

        return String.format("<recipetype:%s>.addRecipe(\"%s\", [%s], %s, %s, %s);", recipe.getType(), recipe.getId(), RecipePrintingUtil.stringifyWeightedStacks(recipe.getOutputItems(), recipe.getOutputItemChances(), ", "), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getInputFluids().get(0).getFluids()[0].getAmount(), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, InsolatorRecipe>> replaceIngredients(IRecipeManager manager, InsolatorRecipe recipe, List<IReplacementRule> rules) {

        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTRecipe(id).energy(recipe.getEnergy()).setInputItems(newIngredients).setOutputItems(recipe.getOutputItems(), recipe.getOutputItemChances()).setInputFluids(recipe.getInputFluids()).recipe(InsolatorRecipe::new));
    }

}
