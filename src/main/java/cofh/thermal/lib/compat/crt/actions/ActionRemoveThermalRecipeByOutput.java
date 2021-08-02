package cofh.thermal.lib.compat.crt.actions;

import cofh.thermal.lib.util.recipes.ThermalRecipe;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class ActionRemoveThermalRecipeByOutput extends ActionRecipeBase {

    private final IIngredient[] outputs;
    private final IFluidStack[] fluids;

    public ActionRemoveThermalRecipeByOutput(IRecipeManager manager, IIngredient[] outputs) {

        super(manager);
        this.outputs = outputs;
        this.fluids = new IFluidStack[0];
    }

    public ActionRemoveThermalRecipeByOutput(IRecipeManager manager, IFluidStack[] fluids) {

        super(manager);
        this.outputs = new IIngredient[0];
        this.fluids = fluids;
    }

    public ActionRemoveThermalRecipeByOutput(IRecipeManager manager, IIngredient[] outputs, IFluidStack[] fluids) {

        super(manager);
        this.outputs = outputs;
        this.fluids = fluids;
    }

    @Override
    public void apply() {

        Iterator<Map.Entry<ResourceLocation, IRecipe<?>>> iter = getManager().getRecipes().entrySet().iterator();
        while (iter.hasNext()) {
            ThermalRecipe recipe = (ThermalRecipe) iter.next().getValue();

            if (recipe.getOutputItems().size() != outputs.length || recipe.getOutputFluids().size() != fluids.length) {
                continue;
            }

            boolean valid = true;
            for (int i = 0; i < recipe.getOutputItems().size(); ++i) {
                if (!outputs[i].matches(new MCItemStackMutable(recipe.getOutputItems().get(i)))) {
                    valid = false;
                    break;
                }
            }
            for (int i = 0; i < recipe.getOutputFluids().size(); ++i) {
                if (!fluids[i].containsOther(new MCFluidStackMutable(recipe.getOutputFluids().get(i))) && !new MCFluidStackMutable(recipe.getOutputFluids().get(i)).containsOther(fluids[i])) {
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

        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipes with outputs: " + Arrays.toString(outputs) + "\" and fluid outputs: \"" + Arrays.toString(fluids) + "\"";
    }

}