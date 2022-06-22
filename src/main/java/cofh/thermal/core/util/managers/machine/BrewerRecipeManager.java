package cofh.thermal.core.util.managers.machine;

import cofh.core.content.fluid.PotionFluid;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.content.fluid.FluidIngredient;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.machine.BrewerRecipe;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;
import cofh.thermal.lib.util.recipes.internal.SimpleMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.TCoreRecipeTypes.BREWER_RECIPE;
import static java.util.Arrays.asList;

public class BrewerRecipeManager extends AbstractManager implements IRecipeManager {

    private static final BrewerRecipeManager INSTANCE = new BrewerRecipeManager();
    protected static final int DEFAULT_ENERGY = 4000;

    protected static boolean defaultPotionRecipes = true;

    protected int defaultPotion = BUCKET_VOLUME;

    protected Map<List<Integer>, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    protected Set<Fluid> validFluids = new ObjectOpenHashSet<>();
    protected Set<ComparableItemStack> validItems = new ObjectOpenHashSet<>();

    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static BrewerRecipeManager instance() {

        return INSTANCE;
    }

    private BrewerRecipeManager() {

        super(DEFAULT_ENERGY);
        this.maxOutputItems = 0;
        this.maxOutputFluids = 1;
    }

    public void addRecipe(ThermalRecipe recipe) {

        for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
            for (FluidStack fluidInput : recipe.getInputFluids().get(0).getFluids()) {
                addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(recipeInput), Collections.singletonList(fluidInput), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
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
        convertedRecipes.clear();
    }

    // region RECIPES
    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty() || inputSlots.get(0).isEmpty() || inputTanks.isEmpty() || inputTanks.get(0).isEmpty()) {
            return null;
        }
        ItemStack inputItem = inputSlots.get(0).getItemStack();
        FluidStack inputFluid = inputTanks.get(0).getFluidStack();
        return recipeMap.get(asList(convert(inputItem).hashCode(), FluidHelper.fluidHashcode(inputFluid)));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids) {

        if (inputItems.isEmpty() || inputFluids.isEmpty() || outputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        ItemStack inputItem = inputItems.get(0);
        if (inputItem.isEmpty()) {
            return null;
        }
        FluidStack inputFluid = inputFluids.get(0);
        if (inputFluid.isEmpty()) {
            return null;
        }
        for (FluidStack stack : outputFluids) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        energy = (int) (energy * getDefaultScale());

        SimpleMachineRecipe recipe = new SimpleMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        recipeMap.put(asList(convert(inputItem).hashCode(), FluidHelper.fluidHashcode(inputFluid)), recipe);
        validItems.add(convert(inputItem));
        validFluids.add(inputFluid.getFluid());
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
        if (defaultPotionRecipes) {
            // TODO: Solve this nonsense with Forge.
            ThermalCore.LOG.debug("Adding default Brewing Stand recipes to the Alchemical Imbuer...");
            createConvertedRecipes();
            for (ThermalRecipe recipe : getConvertedRecipes()) {
                addRecipe(recipe);
            }
        }
        var recipes = recipeManager.byType(BREWER_RECIPE.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
    // endregion

    // region CONVERSION
    protected List<BrewerRecipe> convertedRecipes = new ArrayList<>();

    public List<BrewerRecipe> getConvertedRecipes() {

        return convertedRecipes;
    }

    protected void createConvertedRecipes() {

        for (PotionBrewing.Mix<Potion> mix : PotionBrewing.POTION_MIXES) {
            createConvertedRecipe(mix.from.get(), mix.ingredient, mix.to.get());
        }
    }

    protected boolean createConvertedRecipe(Potion inputPotion, Ingredient reagent, Potion outputPotion) {

        if (inputPotion == null || reagent == null || outputPotion == null) {
            return false;
        }
        convertedRecipes.add(convert(inputPotion, reagent, outputPotion));
        return true;
    }

    protected BrewerRecipe convert(Potion inputPotion, Ingredient reagent, Potion outputPotion) {

        return new BrewerRecipe(new ResourceLocation(ID_THERMAL, "brewer_" + inputPotion.hashCode()), defaultEnergy, 0.0F,
                Collections.singletonList(reagent),
                Collections.singletonList(FluidIngredient.of(PotionFluid.getPotionAsFluid(defaultPotion, inputPotion))),
                Collections.emptyList(), Collections.emptyList(),
                Collections.singletonList(PotionFluid.getPotionAsFluid(defaultPotion, outputPotion)));
    }
    // endregion
}
