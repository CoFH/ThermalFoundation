package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.dynamo.NumismaticFuel;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalFuelByInput;
import cofh.thermal.lib.compat.crt.base.CRTFuel;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.NumismaticFuel")
public class CRTNumismaticManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addFuel(String name, IIngredientWithAmount ingredient, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        NumismaticFuel recipe = new CRTFuel(resourceLocation, energy).item(ingredient).fuel(NumismaticFuel::new);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
    }

    @Override
    public void removeByInput(IItemStack input) {

        removeFuel(input);
    }

    @ZenCodeType.Method
    public void removeFuel(IItemStack outputItem) {

        CraftTweakerAPI.apply(new ActionRemoveThermalFuelByInput(this, new IItemStack[]{outputItem}));
    }

    @Override
    public RecipeType<NumismaticFuel> getRecipeType() {

        return TCoreRecipeTypes.FUEL_NUMISMATIC;
    }

}
