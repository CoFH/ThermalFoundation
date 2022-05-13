package cofh.thermal.core.compat.crt.dynamo;

import cofh.thermal.core.util.recipes.dynamo.*;
import cofh.thermal.lib.compat.crt.RecipePrintingUtil;
import cofh.thermal.lib.compat.crt.base.CRTFuel;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@IRecipeHandler.For (DisenchantmentFuel.class)
@IRecipeHandler.For (GourmandFuel.class)
@IRecipeHandler.For (LapidaryFuel.class)
@IRecipeHandler.For (NumismaticFuel.class)
@IRecipeHandler.For (StirlingFuel.class)
public class CRTItemDynamoRecipeHandler implements IRecipeHandler<ThermalFuel> {

    @Override
    public String dumpToCommandString(IRecipeManager manager, ThermalFuel recipe) {

        return String.format("<recipetype:%s>.addFuel(\"%s\", %s, %s);", recipe.getType().toString(), recipe.getId(), RecipePrintingUtil.stringifyIngredients(recipe.getInputItems(), " | "), recipe.getEnergy());
    }

    @Override
    public Optional<Function<ResourceLocation, ThermalFuel>> replaceIngredients(IRecipeManager manager, ThermalFuel recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {

        CRTFuel.IFuelBuilder<?> builder;

        if (recipe instanceof GourmandFuel) {
            builder = GourmandFuel::new;
        } else if (recipe instanceof LapidaryFuel) {
            builder = LapidaryFuel::new;
        } else if (recipe instanceof NumismaticFuel) {
            builder = NumismaticFuel::new;
        } else if (recipe instanceof StirlingFuel) {
            builder = StirlingFuel::new;
        } else {
            return IRecipeHandler.super.replaceIngredients(manager, recipe, rules);
        }

        final CRTFuel.IFuelBuilder<?> finalBuilder = builder;
        return ReplacementHandlerHelper.replaceIngredientList(
                recipe.getInputItems(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CRTFuel(id, recipe.getEnergy()).item(newIngredients).fuel(finalBuilder));
    }

}

