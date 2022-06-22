package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.dynamo.GourmandFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.Utils.getName;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.TCoreRecipeTypes.FUEL_GOURMAND;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class GourmandFuelManager extends SingleItemFuelManager {

    private static final GourmandFuelManager INSTANCE = new GourmandFuelManager();
    protected static final int DEFAULT_ENERGY = 1600;

    public static GourmandFuelManager instance() {

        return INSTANCE;
    }

    private GourmandFuelManager() {

        super(DEFAULT_ENERGY);
    }

    @Override
    public boolean validFuel(ItemStack input) {

        if (input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            return false;
        }
        return getEnergy(input) > 0;
    }

    @Override
    protected void clear() {

        fuelMap.clear();
        convertedFuels.clear();
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFromFood(stack);
    }

    public int getEnergyFromFood(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.getItem().hasContainerItem(stack)) {
            return 0;
        }
        FoodProperties food = stack.getItem().getFoodProperties();
        if (food == null) {
            return 0;
        }
        int energy = food.getNutrition() * DEFAULT_ENERGY;

        if (food.getEffects().size() > 0) {
            for (Pair<MobEffectInstance, Float> effect : food.getEffects()) {
                if (effect.getFirst().getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                    return 0;
                }
            }
            energy *= 2;
        }
        if (food.getSaturationModifier() > 1.0F) {
            energy *= 4;
        }
        if (food.isFastFood()) {
            energy *= 2;
        }
        return energy >= MIN_ENERGY ? energy : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(FUEL_GOURMAND.get());
        for (var entry : recipes.entrySet()) {
            addFuel(entry.getValue());
        }
        createConvertedRecipes(recipeManager);
    }
    // endregion

    // region CONVERSION
    protected List<GourmandFuel> convertedFuels = new ArrayList<>();

    public List<GourmandFuel> getConvertedFuels() {

        return convertedFuels;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        ItemStack query;
        for (Item item : ForgeRegistries.ITEMS) {
            query = new ItemStack(item);
            try {
                if (getFuel(query) == null && validFuel(query)) {
                    convertedFuels.add(convert(query, getEnergy(query)));
                }
            } catch (Exception e) { // pokemon!
                ThermalCore.LOG.error(getRegistryName(query.getItem()) + " threw an exception when querying the fuel value as the mod author is doing non-standard things in their item code (possibly tag related). It may not display in JEI but should function as fuel.");
            }
        }
    }

    protected GourmandFuel convert(ItemStack item, int energy) {

        return new GourmandFuel(new ResourceLocation(ID_THERMAL, "gourmand_" + getName(item)), energy, singletonList(Ingredient.of(item)), emptyList());
    }
    // endregion
}
