package cofh.thermal.core.util.managers.device;

import cofh.lib.inventory.FalseIInventory;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.FisherBoost;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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

        return boostMap.containsKey(makeComparable(item));
    }

    public void addBoost(FisherBoost boost) {

        for (ItemStack ingredient : boost.getIngredient().getItems()) {
            boostMap.put(makeComparable(ingredient), Triple.of(boost.getLootTable(), boost.getOutputMod(), boost.getUseChance()));
        }
    }

    public ResourceLocation getBoostLootTable(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeComparable(item)).getLeft() : BuiltInLootTables.FISHING_FISH;
    }

    public float getBoostOutputMod(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeComparable(item)).getMiddle() : 1.0F;
    }

    public float getBoostUseChance(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeComparable(item)).getRight() : 1.0F;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, Recipe<FalseIInventory>> boosts = recipeManager.byType(TCoreRecipeTypes.BOOST_FISHER);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : boosts.entrySet()) {
            addBoost((FisherBoost) entry.getValue());
        }
    }
    // endregion
}
