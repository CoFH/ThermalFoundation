package cofh.thermal.lib.compat.crt.actions;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.Map;

public class ActionRemoveThermalCatalystByOutput extends ActionRecipeBase {

    private final IItemStack input;

    public ActionRemoveThermalCatalystByOutput(IRecipeManager manager, IItemStack input) {

        super(manager);
        this.input = input;
    }

    @Override
    public void apply() {

        Iterator<Map.Entry<ResourceLocation, IRecipe<?>>> iter = getManager().getRecipes().entrySet().iterator();
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