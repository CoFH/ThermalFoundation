package cofh.thermal.core.util.managers.machine;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.content.fluid.FluidStackHolder;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;
import cofh.thermal.lib.util.recipes.internal.SimpleMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cofh.thermal.core.init.TCoreRecipeTypes.REFINERY_RECIPE;

public class RefineryRecipeManager extends AbstractManager implements IRecipeManager {

    private static final RefineryRecipeManager INSTANCE = new RefineryRecipeManager();
    protected static final int DEFAULT_ENERGY = 8000;

    protected Map<Integer, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();

    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static RefineryRecipeManager instance() {

        return INSTANCE;
    }

    private RefineryRecipeManager() {

        super(DEFAULT_ENERGY);
        this.maxOutputItems = 1;
        this.maxOutputFluids = 2;
    }

    public void addRecipe(ThermalRecipe recipe) {

        if (recipe.getInputFluids().isEmpty()) {
            return;
        }
        for (FluidStack fluidInput : recipe.getInputFluids().get(0).getFluids()) {
            addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.emptyList(), Collections.singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
        }
    }

    public boolean validRecipe(FluidStack input) {

        return getRecipe(input) != null;
    }

    protected void clear() {

        recipeMap.clear();
    }

    protected IMachineRecipe getRecipe(FluidStack input) {

        return getRecipe(Collections.emptyList(), Collections.singletonList(new FluidStackHolder(input)));
    }

    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputTanks.isEmpty() || inputTanks.get(0).isEmpty()) {
            return null;
        }
        FluidStack inputFluid = inputTanks.get(0).getFluidStack();
        return recipeMap.get(FluidHelper.fluidHashcode(inputFluid));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids) {

        if (inputFluids.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        FluidStack input = inputFluids.get(0);
        if (input.isEmpty()) {
            return null;
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        for (FluidStack stack : outputFluids) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        energy = (int) (energy * getDefaultScale());

        SimpleMachineRecipe recipe = new SimpleMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        recipeMap.put(FluidHelper.fluidHashcode(input), recipe);
        return recipe;
    }

    // region IRecipeManager
    @Override
    public IMachineRecipe getRecipe(IThermalInventory inventory) {

        return getRecipe(inventory.inputSlots(), inventory.inputTanks());
    }

    @Override
    public List<IMachineRecipe> getRecipeList() {

        return new ArrayList<>(recipeMap.values());
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(REFINERY_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion
}
