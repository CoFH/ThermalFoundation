package cofh.thermal.lib.compat.crt.actions;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Iterator;
import java.util.Map;

public class ActionRemoveThermalCatalystByInput extends ActionRecipeBase {

    private final IItemStack input;

    public ActionRemoveThermalCatalystByInput(IRecipeManager manager, IItemStack input) {

        super(manager);
        this.input = input;
    }

    @Override
    public void apply() {

        Iterator<Map.Entry<ResourceLocation, Recipe<?>>> iter = getManager().getRecipes().entrySet().iterator();
        while (iter.hasNext()) {
            ThermalCatalyst fuel = (ThermalCatalyst) iter.next().getValue();

            if (IIngredient.fromIngredient(fuel.getIngredient()).matches(input)) {
                iter.remove();
            }
        }

    }

    @Override
    public String describe() {

        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" fuels with inputs: " + input.getCommandString() + "\"";
    }

}
