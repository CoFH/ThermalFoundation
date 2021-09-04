package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.PotionDiffuserBoost;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.PotionDiffuserBoost")
@IRecipeHandler.For(PotionDiffuserBoost.class)
public class CRTPotionDiffuserBoostManager implements IRecipeManager, IRecipeHandler<PotionDiffuserBoost> {

    @ZenCodeType.Method
    public void addBoost(String name, IIngredient inputItem, int amplifier, float durationMod, int cycles) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        PotionDiffuserBoost mapping = new PotionDiffuserBoost(resourceLocation, inputItem.asVanillaIngredient(), amplifier, durationMod, cycles);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public IRecipeType<PotionDiffuserBoost> getRecipeType() {

        return TCoreRecipeTypes.BOOST_POTION_DIFFUSER;
    }

    @Override
    public void removeRecipe(IItemStack input) {
        removeBoost(input);
    }

    @ZenCodeType.Method
    public void removeBoost(IItemStack input) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof PotionDiffuserBoost) {
                return ((PotionDiffuserBoost) recipe).getIngredient().test(input.getInternal());
            }
            return false;
        }));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, PotionDiffuserBoost recipe) {
        return String.format("<recipetype:%s>.addBoost(\"%s\", %s, %s, %s, %s);", recipe.getType(), recipe.getId(), IIngredient.fromIngredient(recipe.getIngredient()).getCommandString(), recipe.getAmplifier(), recipe.getDurationMod(), recipe.getCycles());
    }

}
