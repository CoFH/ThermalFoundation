package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.TreeExtractorBoost")
@IRecipeHandler.For (TreeExtractorBoost.class)
public class CRTTreeExtractorBoostManager implements IRecipeManager, IRecipeHandler<TreeExtractorBoost> {

    @ZenCodeType.Method
    public void addBoost(String name, IIngredientWithAmount inputItem, float outputMod, int cycles) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        TreeExtractorBoost mapping = new TreeExtractorBoost(resourceLocation, CRTHelper.mapIIngredientWithAmount(inputItem), outputMod, cycles);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public RecipeType<TreeExtractorBoost> getRecipeType() {

        return TCoreRecipeTypes.BOOST_TREE_EXTRACTOR;
    }

    @Override
    public void removeByInput(IItemStack input) {

        removeBoost(input);
    }

    @ZenCodeType.Method
    public void removeBoost(IItemStack input) {

        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof TreeExtractorBoost) {
                return ((TreeExtractorBoost) recipe).getIngredient().test(input.getInternal());
            }
            return false;
        }));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, TreeExtractorBoost recipe) {

        return String.format("<recipetype:%s>.addBoost(\"%s\", %s, %s, %s);", recipe.getType(), recipe.getId(), IIngredient.fromIngredient(recipe.getIngredient()).getCommandString(), recipe.getOutputMod(), recipe.getCycles());
    }

    @Override
    public Optional<Function<ResourceLocation, TreeExtractorBoost>> replaceIngredients(IRecipeManager manager, TreeExtractorBoost recipe, List<IReplacementRule> rules) {

        final Optional<Ingredient> ingredient = IRecipeHandler.attemptReplacing(recipe.getIngredient(), Ingredient.class, recipe, rules);
        return ingredient.map(value -> id -> new TreeExtractorBoost(id, value, recipe.getOutputMod(), recipe.getCycles()));
    }

}
