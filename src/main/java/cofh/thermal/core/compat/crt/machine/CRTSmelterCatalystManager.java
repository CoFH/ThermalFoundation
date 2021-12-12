package cofh.thermal.core.compat.crt.machine;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.SmelterCatalyst;
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
@ZenCodeType.Name ("mods.thermal.SmelterCatalyst")
@IRecipeHandler.For (SmelterCatalyst.class)
public class CRTSmelterCatalystManager implements IRecipeManager, IRecipeHandler<SmelterCatalyst> {

    @ZenCodeType.Method
    public void addCatalyst(String name, IIngredientWithAmount ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        SmelterCatalyst catalyst = new CRTCatalyst(resourceLocation, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance).catalyst(SmelterCatalyst::new);
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
    public IRecipeType<SmelterCatalyst> getRecipeType() {

        return TCoreRecipeTypes.CATALYST_SMELTER;
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, SmelterCatalyst recipe) {

        return String.format("<recipetype:%s>.addCatalyst(\"%s\", %s, %s, %s, %s, %s, %s);", recipe.getType(), recipe.getId(), IIngredient.fromIngredient(recipe.getIngredient()).getCommandString(), recipe.getPrimaryMod(), recipe.getSecondaryMod(), recipe.getEnergyMod(), recipe.getMinChance(), recipe.getUseChance());
    }

    @Override
    public Optional<Function<ResourceLocation, SmelterCatalyst>> replaceIngredients(IRecipeManager manager, SmelterCatalyst recipe, List<IReplacementRule> rules) {

        final Optional<Ingredient> ingredient = IRecipeHandler.attemptReplacing(recipe.getIngredient(), Ingredient.class, recipe, rules);
        return ingredient.map(value -> id -> new CRTCatalyst(id, value, recipe.getPrimaryMod(), recipe.getSecondaryMod(), recipe.getEnergyMod(), recipe.getMinChance(), recipe.getUseChance()).catalyst(SmelterCatalyst::new));
    }

}
