package cofh.thermal.core.util.managers.machine;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.item.SlotSealItem;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;
import cofh.thermal.lib.util.recipes.internal.SimpleMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

import static cofh.thermal.core.init.TCoreRecipeTypes.RECIPE_CHILLER;
import static java.util.Arrays.asList;

public class ChillerRecipeManager extends AbstractManager implements IRecipeManager {

    private static final ChillerRecipeManager INSTANCE = new ChillerRecipeManager();
    protected static final int DEFAULT_ENERGY = 4000;

    protected Map<List<Integer>, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    protected Set<Fluid> validFluids = new ObjectOpenHashSet<>();
    protected Set<ComparableItemStack> validItems = new ObjectOpenHashSet<>();

    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static ChillerRecipeManager instance() {

        return INSTANCE;
    }

    private ChillerRecipeManager() {

        super(DEFAULT_ENERGY);
        this.maxOutputItems = 1;
        this.maxOutputFluids = 0;
    }

    public void addRecipe(ThermalRecipe recipe) {

        if (!recipe.getInputFluids().isEmpty()) {
            for (FluidStack fluidInput : recipe.getInputFluids().get(0).getFluids()) {
                if (!recipe.getInputItems().isEmpty()) {
                    for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
                        addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(recipeInput), Collections.singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
                    }
                } else {
                    addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.emptyList(), Collections.singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
                }
            }
        } else if (!recipe.getInputItems().isEmpty()) {
            for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
                addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(recipeInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
            }
        }
    }

    public boolean validItem(ItemStack item) {

        return validItems.contains(convert(item));
    }

    public boolean validFluid(FluidStack fluid) {

        return validFluids.contains(fluid.getFluid());
    }

    protected void clear() {

        recipeMap.clear();
        validFluids.clear();
        validItems.clear();
    }

    // region RECIPES
    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty() && inputTanks.isEmpty() || inputSlots.get(0).isEmpty() && inputTanks.get(0).isEmpty()) {
            return null;
        }
        if (inputTanks.isEmpty() || inputTanks.get(0).isEmpty()) {
            ItemStack inputItem = inputSlots.get(0).getItemStack();
            return recipeMap.get(Collections.singletonList(convert(inputItem).hashCode()));
        }
        if (inputSlots.isEmpty() || inputSlots.get(0).isEmpty() || inputSlots.get(0).getItemStack().getItem() instanceof SlotSealItem) {
            FluidStack inputFluid = inputTanks.get(0).getFluidStack();
            return recipeMap.get(Collections.singletonList(FluidHelper.fluidHashcode(inputFluid)));
        }
        ItemStack inputItem = inputSlots.get(0).getItemStack();
        FluidStack inputFluid = inputTanks.get(0).getFluidStack();
        return recipeMap.get(asList(convert(inputItem).hashCode(), FluidHelper.fluidHashcode(inputFluid)));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids) {

        if (inputItems.isEmpty() && inputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        List<Integer> key;
        if (inputFluids.isEmpty()) {
            ItemStack inputItem = inputItems.get(0);
            if (inputItem.isEmpty()) {
                return null;
            }
            validItems.add(convert(inputItem));
            key = Collections.singletonList(convert(inputItem).hashCode());
        } else if (inputItems.isEmpty()) {
            FluidStack inputFluid = inputFluids.get(0);
            if (inputFluid.isEmpty()) {
                return null;
            }
            validFluids.add(inputFluid.getFluid());
            key = Collections.singletonList(FluidHelper.fluidHashcode(inputFluid));
        } else {
            ItemStack inputItem = inputItems.get(0);
            if (inputItem.isEmpty()) {
                return null;
            }
            FluidStack inputFluid = inputFluids.get(0);
            if (inputFluid.isEmpty()) {
                return null;
            }
            validItems.add(convert(inputItem));
            validFluids.add(inputFluid.getFluid());
            key = asList(convert(inputItem).hashCode(), FluidHelper.fluidHashcode(inputFluid));
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        energy = (int) (energy * getDefaultScale());

        SimpleMachineRecipe recipe = new SimpleMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        recipeMap.put(key, recipe);
        return recipe;
    }
    // endregion

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
        var recipes = recipeManager.byType(RECIPE_CHILLER.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion
}
