package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.dynamo.DisenchantmentFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.Utils.getName;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.TCoreRecipeTypes.DISENCHANTMENT_FUEL;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class DisenchantmentFuelManager extends SingleItemFuelManager {

    private static final DisenchantmentFuelManager INSTANCE = new DisenchantmentFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static DisenchantmentFuelManager instance() {

        return INSTANCE;
    }

    private DisenchantmentFuelManager() {

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
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFromEnchantments(stack);
    }

    public int getEnergyFromEnchantments(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);
        int energy = 0;

        for (Enchantment enchant : enchants.keySet()) {
            energy += enchant.getMinCost(enchants.get(enchant));
        }
        energy += (enchants.size() * (enchants.size() + 1)) / 2;
        energy *= (DEFAULT_ENERGY / 2);

        return energy;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(DISENCHANTMENT_FUEL.get());
        for (var entry : recipes.entrySet()) {
            addFuel(entry.getValue());
        }
        createConvertedRecipes(recipeManager);
    }
    // endregion

    // region CONVERSION
    protected List<DisenchantmentFuel> convertedFuels = new ArrayList<>();

    public List<DisenchantmentFuel> getConvertedFuels() {

        return convertedFuels;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        List<ItemStack> books = new ArrayList<>();
        for (Enchantment enchant : ForgeRegistries.ENCHANTMENTS) {
            books.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchant, enchant.getMaxLevel())));
        }
        for (ItemStack book : books) {
            try {
                if (getFuel(book) == null && validFuel(book)) {
                    convertedFuels.add(convert(book, getEnergy(book)));
                }
            } catch (Exception e) { // pokemon!
                ThermalCore.LOG.error(getRegistryName(book.getItem()) + " threw an exception when querying the fuel value as the mod author is doing non-standard things in their item code (possibly tag related). It may not display in JEI but should function as fuel.");
            }
        }
    }

    protected DisenchantmentFuel convert(ItemStack item, int energy) {

        return new DisenchantmentFuel(new ResourceLocation(ID_THERMAL, "disenchantment_" + getName(item)), energy, singletonList(Ingredient.of(item)), emptyList());
    }
    // endregion
}
