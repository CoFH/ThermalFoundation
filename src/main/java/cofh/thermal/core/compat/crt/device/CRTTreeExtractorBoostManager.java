package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.FisherBoost;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipe;
import com.blamejared.crafttweaker.impl_native.util.ExpandResourceLocation;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.TreeExtractorBoost")
@IRecipeHandler.For(TreeExtractorBoost.class)
public class CRTTreeExtractorBoostManager implements IRecipeManager, IRecipeHandler<TreeExtractorBoost> {

    @ZenCodeType.Method
    public void addBoost(String name, IIngredient inputItem, float outputMod, int cycles) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        TreeExtractorBoost mapping = new TreeExtractorBoost(resourceLocation, inputItem.asVanillaIngredient(), outputMod, cycles);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public IRecipeType<TreeExtractorBoost> getRecipeType() {

        return TCoreRecipeTypes.BOOST_TREE_EXTRACTOR;
    }

    @Override
    public void removeRecipe(IItemStack input) {
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

}
