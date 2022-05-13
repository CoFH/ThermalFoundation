package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.dynamo.CompressionFuel;
import cofh.thermal.lib.compat.crt.actions.ActionRemoveThermalFuelByInput;
import cofh.thermal.lib.compat.crt.base.CRTFuel;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name ("mods.thermal.CompressionFuel")
public class CRTCompressionManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addFuel(String name, CTFluidIngredient ingredient, int energy) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        CompressionFuel recipe = new CRTFuel(resourceLocation, energy).fluid(ingredient).fuel(CompressionFuel::new);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe));
    }

    @Override
    public void remove(IIngredient output) {

        throw new IllegalArgumentException("Compression Fuel only works with fluids! Please provide an IFluidStack");
    }

    @ZenCodeType.Method
    public void removeFuel(IFluidStack outputFluid) {

        CraftTweakerAPI.apply(new ActionRemoveThermalFuelByInput(this, new IFluidStack[]{outputFluid}));
    }

    @Override
    public RecipeType<CompressionFuel> getRecipeType() {

        return TCoreRecipeTypes.FUEL_COMPRESSION;
    }

}

