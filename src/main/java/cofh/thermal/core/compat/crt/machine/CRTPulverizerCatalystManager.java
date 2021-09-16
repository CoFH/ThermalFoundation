package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.PulverizerCatalyst;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalCatalystByOutput;
import cofh.thermal.lib.compat.crt.base.CRTCatalyst;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("mods.thermal.PulverizerCatalyst")
@IRecipeHandler.For(PulverizerCatalyst.class)
public class CRTPulverizerCatalystManager implements IRecipeManager, IRecipeHandler<PulverizerCatalyst> {

    @ZenCodeType.Method
    public void addCatalyst(String name, IIngredientWithAmount ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        PulverizerCatalyst catalyst = new CRTCatalyst(resourceLocation, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance).catalyst(PulverizerCatalyst::new);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, catalyst));
    }

    @Override
    public void removeRecipe(IItemStack output) {

        removeCatalyst(output);
    }

    @ZenCodeType.Method
    public void removeCatalyst(IItemStack input) {

        CraftTweakerAPI.apply(new ActionRemoveThermalCatalystByOutput(this, input));
    }

    @Override
    public IRecipeType<PulverizerCatalyst> getRecipeType() {

        return TCoreRecipeTypes.CATALYST_PULVERIZER;
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, PulverizerCatalyst recipe) {

        return String.format("<recipetype:%s>.addCatalyst(\"%s\", %s, %s, %s, %s, %s, %s);", recipe.getType(), recipe.getId(), IIngredient.fromIngredient(recipe.getIngredient()).getCommandString(), recipe.getPrimaryMod(), recipe.getSecondaryMod(), recipe.getEnergyMod(), recipe.getMinChance(), recipe.getUseChance());
    }

    @Override
    public Optional<Function<ResourceLocation, PulverizerCatalyst>> replaceIngredients(IRecipeManager manager, PulverizerCatalyst recipe, List<IReplacementRule> rules) {

        final Optional<Ingredient> ingredient = IRecipeHandler.attemptReplacing(recipe.getIngredient(), Ingredient.class, recipe, rules);
        return ingredient.map(value -> id -> new CRTCatalyst(id, value, recipe.getPrimaryMod(), recipe.getSecondaryMod(), recipe.getEnergyMod(), recipe.getMinChance(), recipe.getUseChance()).catalyst(PulverizerCatalyst::new));
    }

}
