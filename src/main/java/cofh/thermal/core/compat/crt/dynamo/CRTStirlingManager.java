package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.dynamo.StirlingFuel;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalFuelByOutput;
import cofh.thermal.lib.compat.crt.base.CRTFuel;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.StirlingFuel")
public class CRTStirlingManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addFuel(String name, IIngredient ingredient, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        StirlingFuel recipe = new CRTFuel(resourceLocation, energy).item(ingredient).fuel(StirlingFuel::new);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
    }

    @Override
    public void removeRecipe(IItemStack output) {

        removeFuel(output);
    }

    @ZenCodeType.Method
    public void removeFuel(IItemStack outputItem) {

        CraftTweakerAPI.apply(new ActionRemoveThermalFuelByOutput(this, new IItemStack[]{outputItem}));
    }

    @Override
    public IRecipeType<StirlingFuel> getRecipeType() {

        return TCoreRecipeTypes.FUEL_STIRLING;
    }

}
