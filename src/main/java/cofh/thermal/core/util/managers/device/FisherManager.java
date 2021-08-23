package cofh.thermal.core.util.managers.device;

import cofh.lib.inventory.FalseIInventory;
import cofh.lib.util.ComparableItemStack;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.FisherBoost;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.loot.LootTables;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;

public class FisherManager extends AbstractManager {

    private static final FisherManager INSTANCE = new FisherManager();

    protected Map<ComparableItemStack, Triple<ResourceLocation, Float, Float>> boostMap = new Object2ObjectOpenHashMap<>();

    public static FisherManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        boostMap.clear();
    }

    // region BOOSTS
    public boolean validBoost(ItemStack item) {

        return boostMap.containsKey(convert(item));
    }

    public void addBoost(FisherBoost boost) {

        for (ItemStack ingredient : boost.getIngredient().getItems()) {
            boostMap.put(convert(ingredient), Triple.of(boost.getLootTable(), boost.getOutputMod(), boost.getUseChance()));
        }
    }

    public ResourceLocation getBoostLootTable(ItemStack item) {

        return validBoost(item) ? boostMap.get(convert(item)).getLeft() : LootTables.FISHING_FISH;
    }

    public float getBoostOutputMod(ItemStack item) {

        return validBoost(item) ? boostMap.get(convert(item)).getMiddle() : 1.0F;
    }

    public float getBoostUseChance(ItemStack item) {

        return validBoost(item) ? boostMap.get(convert(item)).getRight() : 1.0F;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> boosts = recipeManager.byType(TCoreRecipeTypes.BOOST_FISHER);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : boosts.entrySet()) {
            addBoost((FisherBoost) entry.getValue());
        }
    }
    // endregion
}
