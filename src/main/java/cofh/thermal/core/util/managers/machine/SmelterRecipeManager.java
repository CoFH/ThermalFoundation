package cofh.thermal.core.util.managers.machine;

import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.inventory.FalseIInventory;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.CatalyzedRecipeManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.*;

import static java.util.Arrays.asList;

public class SmelterRecipeManager extends AbstractManager implements IRecipeManager, CatalyzedRecipeManager {

    private static final SmelterRecipeManager INSTANCE = new SmelterRecipeManager();
    protected static final int DEFAULT_ENERGY = 3200;

    protected Map<SmelterMapWrapper, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    protected Map<ComparableItemStack, IRecipeCatalyst> catalystMap = new Object2ObjectOpenHashMap<>();
    protected Set<ComparableItemStack> validItems = new ObjectOpenHashSet<>();

    protected int maxInputItems;
    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static SmelterRecipeManager instance() {

        return INSTANCE;
    }

    private SmelterRecipeManager() {

        super(DEFAULT_ENERGY);
        this.maxInputItems = 3;
        this.maxOutputItems = 4;
        this.maxOutputFluids = 0;
    }

    public void addRecipe(ThermalRecipe recipe, BaseMachineRecipe.RecipeType type) {

        switch (recipe.getInputItems().size()) {
            case 1:
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(firstInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                }
                return;
            case 2:
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    for (ItemStack secondInput : recipe.getInputItems().get(1).getItems()) {
                        addRecipe(recipe.getEnergy(), recipe.getXp(), asList(firstInput, secondInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                    }
                }
                return;
            case 3:
                for (ItemStack firstInput : recipe.getInputItems().get(0).getItems()) {
                    for (ItemStack secondInput : recipe.getInputItems().get(1).getItems()) {
                        for (ItemStack thirdInput : recipe.getInputItems().get(2).getItems()) {
                            addRecipe(recipe.getEnergy(), recipe.getXp(), asList(firstInput, secondInput, thirdInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids(), type);
                        }
                    }
                }
                return;
            default:
        }
    }

    public boolean validItem(ItemStack item) {

        return validItems.contains(convert(item));
    }

    protected void clear() {

        recipeMap.clear();
        catalystMap.clear();
        validItems.clear();
    }

    // region RECIPES
    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty()) {
            return null;
        }
        List<ComparableItemStack> convertedItems = new ArrayList<>(maxInputItems);
        for (int i = 0; i < maxInputItems; ++i) {
            if (!inputSlots.get(i).isEmpty()) {
                ComparableItemStack compStack = convert(inputSlots.get(i).getItemStack());
                convertedItems.add(compStack);
            }
        }
        if (convertedItems.isEmpty()) {
            return null;
        }
        return recipeMap.get(new SmelterMapWrapper(convertedItems));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids, BaseMachineRecipe.RecipeType type) {

        if (inputItems.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        for (ItemStack stack : inputItems) {
            if (stack.isEmpty()) {
                return null;
            }
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

        List<ComparableItemStack> convertedItems = new ArrayList<>(inputItems.size());
        for (ItemStack stack : inputItems) {
            if (!inputItems.isEmpty()) {
                ComparableItemStack compStack = convert(stack);
                validItems.add(compStack);
                convertedItems.add(compStack);
            }
        }
        IMachineRecipe recipe;
        if (type == BaseMachineRecipe.RecipeType.DISENCHANT) {
            recipe = new DisenchantMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        } else {
            recipe = new InternalSmelterRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }
        recipeMap.put(new SmelterMapWrapper(convertedItems), recipe);
        return recipe;
    }
    // endregion

    @Override
    public List<ItemStack> getCatalysts() {

        List<ItemStack> ret = new ArrayList<>(catalystMap.size());
        catalystMap.keySet().forEach(stack -> ret.add(stack.toItemStack()));
        return ret;
    }

    // region CATALYSTS
    @Override
    public IRecipeCatalyst getCatalyst(IItemStackHolder input) {

        return catalystMap.get(convert(input.getItemStack()));
    }

    @Override
    public IRecipeCatalyst getCatalyst(ItemStack input) {

        return catalystMap.get(convert(input));
    }

    public void addCatalyst(ThermalCatalyst catalyst) {

        for (ItemStack ingredient : catalyst.getIngredient().getItems()) {
            addCatalyst(ingredient, catalyst.getPrimaryMod(), catalyst.getSecondaryMod(), catalyst.getEnergyMod(), catalyst.getMinChance(), catalyst.getUseChance());
        }
    }

    public IRecipeCatalyst addCatalyst(ItemStack input, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        if (input == null || input.isEmpty()) {
            return null;
        }
        BaseMachineCatalyst catalyst = new BaseMachineCatalyst(primaryMod, secondaryMod, energyMod, minChance, useChance);
        catalystMap.put(convert(input), catalyst);
        return catalyst;
    }

    public boolean validCatalyst(ItemStack input) {

        return getCatalyst(input) != null;
    }

    public IRecipeCatalyst removeCatalyst(ItemStack input) {

        return catalystMap.remove(convert(input));
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
        Map<ResourceLocation, Recipe<FalseIInventory>> recipes = recipeManager.byType(TCoreRecipeTypes.RECIPE_SMELTER);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue(), BaseMachineRecipe.RecipeType.CATALYZED);
        }
        Map<ResourceLocation, Recipe<FalseIInventory>> recycle = recipeManager.byType(TCoreRecipeTypes.RECIPE_SMELTER_RECYCLE);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : recycle.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue(), BaseMachineRecipe.RecipeType.DISENCHANT);
        }
        Map<ResourceLocation, Recipe<FalseIInventory>> catalysts = recipeManager.byType(TCoreRecipeTypes.CATALYST_SMELTER);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : catalysts.entrySet()) {
            addCatalyst((ThermalCatalyst) entry.getValue());
        }
    }
    // endregion

    // region WRAPPER CLASS
    protected static class SmelterMapWrapper {

        Set<Integer> itemHashes;
        int hashCode;

        SmelterMapWrapper(List<ComparableItemStack> itemStacks) {

            this.itemHashes = new ObjectOpenHashSet<>(itemStacks.size());
            for (ComparableItemStack itemStack : itemStacks) {
                if (itemStack.hashCode() != 0) {
                    this.itemHashes.add(itemStack.hashCode());
                    hashCode += itemStack.hashCode();
                }
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
            SmelterMapWrapper that = (SmelterMapWrapper) o;
            return itemHashes.size() == that.itemHashes.size() && itemHashes.containsAll(that.itemHashes);
        }

        @Override
        public int hashCode() {

            return hashCode;
        }

    }
    // endregion

    // region CATALYZED RECIPE
    protected static class InternalSmelterRecipe extends CatalyzedMachineRecipe {

        public InternalSmelterRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

            super(3, energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }

        @Override
        public IRecipeCatalyst getCatalyst(ItemStack input) {

            return instance().getCatalyst(input);
        }

    }
    // endregion
}
