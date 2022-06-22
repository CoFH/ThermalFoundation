package cofh.thermal.core.util.managers.device;

import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.util.recipes.device.FisherBoost;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;

import static cofh.thermal.core.init.TCoreRecipeTypes.FISHER_BOOST;

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

        return validBoost(item) ? boostMap.get(convert(item)).getLeft() : BuiltInLootTables.FISHING_FISH;
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
        var boosts = recipeManager.byType(FISHER_BOOST.get());
        for (var entry : boosts.entrySet()) {
            addBoost(entry.getValue());
        }
    }
    // endregion
}
