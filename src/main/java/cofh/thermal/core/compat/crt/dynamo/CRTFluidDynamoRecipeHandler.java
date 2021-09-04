package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.util.recipes.dynamo.CompressionFuel;
import cofh.thermal.core.util.recipes.dynamo.MagmaticFuel;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;

@IRecipeHandler.For(CompressionFuel.class)
@IRecipeHandler.For(MagmaticFuel.class)
public class CRTFluidDynamoRecipeHandler implements IRecipeHandler<ThermalFuel> {

    @Override
    public String dumpToCommandString(IRecipeManager manager, ThermalFuel recipe) {
        return String.format("<recipetype:%s>.addFuel(\"%s\", %s, %s);", recipe.getType().toString(), recipe.getId(), CRTHelper.stringifyFluidIngredients(recipe.getInputFluids()), recipe.getEnergy());
    }
}
