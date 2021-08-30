package cofh.thermal.core.util.managers.machine;

import cofh.core.fluid.PotionFluid;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.fluid.FluidIngredient;
import cofh.lib.fluid.IFluidStackAccess;
import cofh.lib.inventory.FalseIInventory;
import cofh.lib.inventory.IItemStackAccess;
import cofh.lib.util.ComparableItemStack;
import cofh.lib.util.helpers.BrewingRecipeHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.machine.BrewerRecipe;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;
import cofh.thermal.lib.util.recipes.internal.SimpleMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
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
    protected IMachineRecipe getRecipe(List<? extends IItemStackAccess> inputSlots, List<? extends IFluidStackAccess> inputTanks) {

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
        Map<ResourceLocation, IRecipe<FalseIInventory>> recipes = recipeManager.byType(TCoreRecipeTypes.RECIPE_BREWER);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : recipes.entrySet()) {
            addRecipe((ThermalRecipe) entry.getValue());
        }
    }
    // endregion

    // region CONVERSION
    protected List<BrewerRecipe> convertedRecipes = new ArrayList<>();

    public List<BrewerRecipe> getConvertedRecipes() {

        return convertedRecipes;
    }

    protected void createConvertedRecipes() {

        for (Object mixpredicate : PotionBrewing.POTION_MIXES) {
            createConvertedRecipe(mixpredicate);
        }

        //        createConvertedRecipe(Potions.WATER, Items.GLISTERING_MELON_SLICE, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.GHAST_TEAR, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.RABBIT_FOOT, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.BLAZE_POWDER, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.SPIDER_EYE, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.SUGAR, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.MAGMA_CREAM, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.GLOWSTONE_DUST, Potions.THICK);
        //        createConvertedRecipe(Potions.WATER, Items.REDSTONE, Potions.MUNDANE);
        //        createConvertedRecipe(Potions.WATER, Items.NETHER_WART, Potions.AWKWARD);
        //        createConvertedRecipe(Potions.AWKWARD, Items.GOLDEN_CARROT, Potions.NIGHT_VISION);
        //        createConvertedRecipe(Potions.NIGHT_VISION, Items.REDSTONE, Potions.LONG_NIGHT_VISION);
        //        createConvertedRecipe(Potions.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY);
        //        createConvertedRecipe(Potions.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY);
        //        createConvertedRecipe(Potions.INVISIBILITY, Items.REDSTONE, Potions.LONG_INVISIBILITY);
        //        createConvertedRecipe(Potions.AWKWARD, Items.MAGMA_CREAM, Potions.FIRE_RESISTANCE);
        //        createConvertedRecipe(Potions.FIRE_RESISTANCE, Items.REDSTONE, Potions.LONG_FIRE_RESISTANCE);
        //        createConvertedRecipe(Potions.AWKWARD, Items.RABBIT_FOOT, Potions.LEAPING);
        //        createConvertedRecipe(Potions.LEAPING, Items.REDSTONE, Potions.LONG_LEAPING);
        //        createConvertedRecipe(Potions.LEAPING, Items.GLOWSTONE_DUST, Potions.STRONG_LEAPING);
        //        createConvertedRecipe(Potions.LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
        //        createConvertedRecipe(Potions.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
        //        createConvertedRecipe(Potions.SLOWNESS, Items.REDSTONE, Potions.LONG_SLOWNESS);
        //        createConvertedRecipe(Potions.SLOWNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SLOWNESS);
        //        createConvertedRecipe(Potions.AWKWARD, Items.TURTLE_HELMET, Potions.TURTLE_MASTER);
        //        createConvertedRecipe(Potions.TURTLE_MASTER, Items.REDSTONE, Potions.LONG_TURTLE_MASTER);
        //        createConvertedRecipe(Potions.TURTLE_MASTER, Items.GLOWSTONE_DUST, Potions.STRONG_TURTLE_MASTER);
        //        createConvertedRecipe(Potions.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
        //        createConvertedRecipe(Potions.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
        //        createConvertedRecipe(Potions.AWKWARD, Items.SUGAR, Potions.SWIFTNESS);
        //        createConvertedRecipe(Potions.SWIFTNESS, Items.REDSTONE, Potions.LONG_SWIFTNESS);
        //        createConvertedRecipe(Potions.SWIFTNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SWIFTNESS);
        //        createConvertedRecipe(Potions.AWKWARD, Items.PUFFERFISH, Potions.WATER_BREATHING);
        //        createConvertedRecipe(Potions.WATER_BREATHING, Items.REDSTONE, Potions.LONG_WATER_BREATHING);
        //        createConvertedRecipe(Potions.AWKWARD, Items.GLISTERING_MELON_SLICE, Potions.HEALING);
        //        createConvertedRecipe(Potions.HEALING, Items.GLOWSTONE_DUST, Potions.STRONG_HEALING);
        //        createConvertedRecipe(Potions.HEALING, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
        //        createConvertedRecipe(Potions.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
        //        createConvertedRecipe(Potions.HARMING, Items.GLOWSTONE_DUST, Potions.STRONG_HARMING);
        //        createConvertedRecipe(Potions.POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
        //        createConvertedRecipe(Potions.LONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
        //        createConvertedRecipe(Potions.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
        //        createConvertedRecipe(Potions.AWKWARD, Items.SPIDER_EYE, Potions.POISON);
        //        createConvertedRecipe(Potions.POISON, Items.REDSTONE, Potions.LONG_POISON);
        //        createConvertedRecipe(Potions.POISON, Items.GLOWSTONE_DUST, Potions.STRONG_POISON);
        //        createConvertedRecipe(Potions.AWKWARD, Items.GHAST_TEAR, Potions.REGENERATION);
        //        createConvertedRecipe(Potions.REGENERATION, Items.REDSTONE, Potions.LONG_REGENERATION);
        //        createConvertedRecipe(Potions.REGENERATION, Items.GLOWSTONE_DUST, Potions.STRONG_REGENERATION);
        //        createConvertedRecipe(Potions.AWKWARD, Items.BLAZE_POWDER, Potions.STRENGTH);
        //        createConvertedRecipe(Potions.STRENGTH, Items.REDSTONE, Potions.LONG_STRENGTH);
        //        createConvertedRecipe(Potions.STRENGTH, Items.GLOWSTONE_DUST, Potions.STRONG_STRENGTH);
        //        createConvertedRecipe(Potions.WATER, Items.FERMENTED_SPIDER_EYE, Potions.WEAKNESS);
        //        createConvertedRecipe(Potions.WEAKNESS, Items.REDSTONE, Potions.LONG_WEAKNESS);
        //        createConvertedRecipe(Potions.AWKWARD, Items.PHANTOM_MEMBRANE, Potions.SLOW_FALLING);
        //        createConvertedRecipe(Potions.SLOW_FALLING, Items.REDSTONE, Potions.LONG_SLOW_FALLING);
    }

    protected boolean createConvertedRecipe(Object mixpredicate) {

        return createConvertedRecipe(BrewingRecipeHelper.getFromPotion(mixpredicate), BrewingRecipeHelper.getIngredient(mixpredicate), BrewingRecipeHelper.getToPotion(mixpredicate));
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
