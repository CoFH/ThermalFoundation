package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.util.recipes.dynamo.*;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.RecipePrintingUtil;

@IRecipeHandler.For(DisenchantmentFuel.class)
@IRecipeHandler.For(GourmandFuel.class)
@IRecipeHandler.For(LapidaryFuel.class)
@IRecipeHandler.For(NumismaticFuel.class)
@IRecipeHandler.For(StirlingFuel.class)
public class CRTItemDynamoRecipeHandler implements IRecipeHandler<ThermalFuel> {

    @Override
    public String dumpToCommandString(IRecipeManager manager, ThermalFuel recipe) {
        return String.format("<recipetype:%s>.addFuel(\"%s\", %s, %s);", recipe.getType().toString(), recipe.getId(), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getEnergy());
    }
}
