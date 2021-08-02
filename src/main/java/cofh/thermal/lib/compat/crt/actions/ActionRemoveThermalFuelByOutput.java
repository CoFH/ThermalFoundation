package cofh.thermal.lib.compat.crt.actions;

import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class ActionRemoveThermalFuelByOutput extends ActionRecipeBase {

    private final IItemStack[] outputs;
    private final IFluidStack[] fluids;

    public ActionRemoveThermalFuelByOutput(IRecipeManager manager, IItemStack[] outputs) {

        super(manager);
        this.outputs = outputs;
        this.fluids = new IFluidStack[0];
    }

    public ActionRemoveThermalFuelByOutput(IRecipeManager manager, IFluidStack[] fluids) {

        super(manager);
        this.outputs = new IItemStack[0];
        this.fluids = fluids;
    }

    public ActionRemoveThermalFuelByOutput(IRecipeManager manager, IItemStack[] outputs, IFluidStack[] fluids) {

        super(manager);
        this.outputs = outputs;
        this.fluids = fluids;
    }

    @Override
    public void apply() {

        Iterator<Map.Entry<ResourceLocation, IRecipe<?>>> iter = getManager().getRecipes().entrySet().iterator();
        while (iter.hasNext()) {
            ThermalFuel fuel = (ThermalFuel) iter.next().getValue();

            if (fuel.getInputItems().size() != outputs.length || fuel.getInputFluids().size() != fluids.length) {
                continue;
            }

            boolean valid = true;
            for (int i = 0; i < fuel.getInputItems().size(); ++i) {
                if (!IIngredient.fromIngredient(fuel.getInputItems().get(i)).matches(outputs[i])) {
                    valid = false;
                    break;
                }
            }
            for (int i = 0; i < fuel.getInputFluids().size(); ++i) {
                if (!fluids[i].containsOther(new MCFluidStackMutable(fuel.getInputFluids().get(i))) && !new MCFluidStackMutable(fuel.getInputFluids().get(i)).containsOther(fluids[i])) {
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

        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" fuels with inputs: " + Arrays.toString(outputs) + "\" and fluid inputs: \"" + Arrays.toString(fluids) + "\"";
    }

}