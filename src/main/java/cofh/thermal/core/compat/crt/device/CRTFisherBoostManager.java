package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.FisherBoost;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.natives.resource.ExpandResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.FisherBoost")
@IRecipeHandler.For (FisherBoost.class)
public class CRTFisherBoostManager implements IRecipeManager, IRecipeHandler<FisherBoost> {

    @ZenCodeType.Method
    public void addBoost(String name, IIngredientWithAmount inputItem, ResourceLocation lootTable, float outputMod, float useChance) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        FisherBoost mapping = new FisherBoost(resourceLocation, CRTHelper.mapIIngredientWithAmount(inputItem), lootTable, outputMod, useChance);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public RecipeType<FisherBoost> getRecipeType() {

        return TCoreRecipeTypes.BOOST_FISHER;
    }

    @Override
    public void removeByInput(IItemStack input) {

        removeBoost(input);
    }

    @ZenCodeType.Method
    public void removeBoost(IItemStack input) {

        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof FisherBoost) {
                return ((FisherBoost) recipe).getIngredient().test(input.getInternal());
            }
            return false;
        }));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, FisherBoost recipe) {

        return String.format("<recipetype:%s>.addBoost(\"%s\", %s, %s, %s, %s);", recipe.getType(), recipe.getId(), IIngredient.fromIngredient(recipe.getIngredient()).getCommandString(), ExpandResourceLocation.getCommandString(recipe.getLootTable()), recipe.getOutputMod(), recipe.getUseChance());
    }

    @Override
    public Optional<Function<ResourceLocation, FisherBoost>> replaceIngredients(IRecipeManager manager, FisherBoost recipe, List<IReplacementRule> rules) {

        final Optional<Ingredient> ingredient = IRecipeHandler.attemptReplacing(recipe.getIngredient(), Ingredient.class, recipe, rules);
        return ingredient.map(value -> id -> new FisherBoost(id, value, recipe.getLootTable(), recipe.getOutputMod(), recipe.getUseChance()));
    }

}
