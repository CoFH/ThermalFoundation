package cofh.thermal.lib.compat.crt.actions;

import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class ActionRemoveThermalFuelByInput extends ActionRecipeBase {

    private final IItemStack[] inputs;
    private final IFluidStack[] fluids;

    public ActionRemoveThermalFuelByInput(IRecipeManager manager, IItemStack[] inputs) {

        super(manager);
        this.inputs = inputs;
        this.fluids = new IFluidStack[0];
    }

    public ActionRemoveThermalFuelByInput(IRecipeManager manager, IFluidStack[] fluids) {

        super(manager);
        this.inputs = new IItemStack[0];
        this.fluids = fluids;
    }

    public ActionRemoveThermalFuelByInput(IRecipeManager manager, IItemStack[] inputs, IFluidStack[] fluids) {

        super(manager);
        this.inputs = inputs;
        this.fluids = fluids;
    }

    @Override
    public void apply() {

        Iterator<Map.Entry<ResourceLocation, Recipe<?>>> iter = getManager().getRecipes().entrySet().iterator();
        while (iter.hasNext()) {
            ThermalFuel fuel = (ThermalFuel) iter.next().getValue();

            if (fuel.getInputItems().size() != inputs.length || fuel.getInputFluids().size() != fluids.length) {
                continue;
            }
            boolean valid = true;
            for (int i = 0; i < fuel.getInputItems().size(); ++i) {
                if (!IIngredient.fromIngredient(fuel.getInputItems().get(i)).matches(inputs[i])) {
                    valid = false;
                    break;
                }
            }
            for (int i = 0; i < fuel.getInputFluids().size(); ++i) {
                if (!fuel.getInputFluids().get(i).test(fluids[i].getInternal())) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                iter.remove();
            }
        }

    }

    @Override
    public String describe() {

        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" fuels with inputs: " + Arrays.toString(inputs) + "\" and fluid inputs: \"" + Arrays.toString(fluids) + "\"";
    }

}
