package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.dynamo.MagmaticFuel;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalFuelByOutput;
import cofh.thermal.lib.compat.crt.base.CRTFuel;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.MagmaticFuel")
public class CRTMagmaticManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addFuel(String name, CTFluidIngredient ingredient, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        MagmaticFuel recipe = new CRTFuel(resourceLocation, energy).fluid(ingredient).fuel(MagmaticFuel::new);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
    }

    @Override
    public void removeRecipe(IIngredient output) {

        throw new IllegalArgumentException("Magmatic Fuel only works with fluids! Please provide an IFluidStack");
    }

    @ZenCodeType.Method
    public void removeFuel(IFluidStack outputFluid) {

        CraftTweakerAPI.apply(new ActionRemoveThermalFuelByOutput(this, new IFluidStack[]{outputFluid}));
    }

    @Override
    public IRecipeType<MagmaticFuel> getRecipeType() {

        return TCoreRecipeTypes.FUEL_MAGMATIC;
    }

}
