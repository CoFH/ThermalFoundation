package cofh.thermal.core.util.managers.machine;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.init.TCoreRecipeTypes;
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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class CrystallizerRecipeManager extends AbstractManager implements IRecipeManager {

    private static final CrystallizerRecipeManager INSTANCE = new CrystallizerRecipeManager();
    protected static final int DEFAULT_ENERGY = 20000;

    protected Map<CrystallizerMapWrapper, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    protected Set<Fluid> validFluids = new ObjectOpenHashSet<>();
    protected Set<ComparableItemStack> validItems = new ObjectOpenHashSet<>();

    protected int maxInputItems;
    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static CrystallizerRecipeManager instance() {

        return INSTANCE;
    }

    private CrystallizerRecipeManager() {

        super(DEFAULT_ENERGY);
        this.maxInputItems = 2;
        this.maxOutputItems = 1;
        this.maxOutputFluids = 0;
        this.basePower = 5;
    }

    public void addRecipe(ThermalRecipe recipe) {

        switch (recipe.getInputItems().size()) {
            case 1 -> {
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    for (FluidStack fluidInput : recipe.getInputFluids().get(0).getFluids()) {
                        addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(firstInput), singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
                    }
                }
            }
            case 2 -> {
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    for (ItemStack secondInput : recipe.getInputItems().get(1).getItems()) {
                        for (FluidStack fluidInput : recipe.getInputFluids().get(0).getFluids()) {
                            addRecipe(recipe.getEnergy(), recipe.getXp(), asList(firstInput, secondInput), singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
                        }
                    }
                }
            }
            default -> {
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

        if (inputSlots.isEmpty() || inputTanks.isEmpty() || inputSlots.get(0).isEmpty() && inputTanks.get(0).isEmpty()) {
            return null;
        }
        List<ComparableItemStack> convertedItems = new ArrayList<>(maxInputItems);
        for (int i = 0; i < maxInputItems; ++i) {
            if (!inputSlots.get(i).isEmpty()) {
                ComparableItemStack compStack = convert(inputSlots.get(i).getItemStack());
                convertedItems.add(compStack);
            }
        }
        return recipeMap.get(new CrystallizerMapWrapper(convertedItems, inputTanks.get(0).getFluidStack()));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids) {

        if (inputItems.isEmpty() || inputFluids.isEmpty() || outputItems.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        for (ItemStack stack : inputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        FluidStack inputFluid = inputFluids.get(0);
        if (inputFluid.isEmpty()) {
            return null;
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        List<ComparableItemStack> convertedItems = new ArrayList<>(inputItems.size());
        for (ItemStack stack : inputItems) {
            if (!inputItems.isEmpty()) {
                ComparableItemStack compStack = convert(stack);
                validItems.add(compStack);
                convertedItems.add(compStack);
            }
        }
        validFluids.add(inputFluid.getFluid());
        energy = (int) (energy * getDefaultScale());

        SimpleMachineRecipe recipe = new SimpleMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        recipeMap.put(new CrystallizerMapWrapper(convertedItems, inputFluid), recipe);
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
        var recipes = recipeManager.byType(TCoreRecipeTypes.RECIPE_CRYSTALLIZER);
        for (var entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion

    // region WRAPPER CLASS
    protected static class CrystallizerMapWrapper {

        Set<Integer> hashes;
        int hashCode;

        CrystallizerMapWrapper(List<ComparableItemStack> itemStacks) {

            this.hashes = new ObjectOpenHashSet<>(itemStacks.size());
            for (ComparableItemStack itemStack : itemStacks) {
                if (itemStack.hashCode() != 0) {
                    this.hashes.add(itemStack.hashCode());
                    hashCode += itemStack.hashCode();
                }
            }
        }

        CrystallizerMapWrapper(List<ComparableItemStack> itemStacks, FluidStack fluidStack) {

            this.hashes = new ObjectOpenHashSet<>(itemStacks.size());
            for (ComparableItemStack itemStack : itemStacks) {
                if (itemStack.hashCode() != 0) {
                    this.hashes.add(itemStack.hashCode());
                    hashCode += itemStack.hashCode();
                }
            }
            if (!fluidStack.isEmpty()) {
                int fluidHash = FluidHelper.fluidHashcodeNoTag(fluidStack);
                hashes.add(fluidHash);
                hashCode += fluidHash;
            }
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CrystallizerMapWrapper that = (CrystallizerMapWrapper) o;
            return hashes.size() == that.hashes.size() && hashes.containsAll(that.hashes);
        }

        @Override
        public int hashCode() {

            return hashCode;
        }

    }
    // endregion
}
