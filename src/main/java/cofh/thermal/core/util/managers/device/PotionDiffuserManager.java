package cofh.thermal.core.util.managers.device;

import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.util.recipes.device.PotionDiffuserBoost;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;

import static cofh.thermal.core.init.TCoreRecipeTypes.POTION_DIFFUSER_BOOST;

public class PotionDiffuserManager extends AbstractManager {

    private static final PotionDiffuserManager INSTANCE = new PotionDiffuserManager();

    protected Map<ComparableItemStack, Triple<Integer, Integer, Float>> boostMap = new Object2ObjectOpenHashMap<>();

    protected PotionDiffuserManager() {

        super(16);
    }

    public static PotionDiffuserManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        boostMap.clear();
    }

    // region BOOSTS
    public boolean validBoost(ItemStack item) {

        return boostMap.containsKey(makeComparable(item));
    }

    public void addBoost(PotionDiffuserBoost boost) {

        for (ItemStack ingredient : boost.getIngredient().getItems()) {
            boostMap.put(makeComparable(ingredient), Triple.of(boost.getCycles(), boost.getAmplifier(), boost.getDurationMod()));
        }
    }

    public int getBoostCycles(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeComparable(item)).getLeft() : 0;
    }

    public int getBoostAmplifier(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeComparable(item)).getMiddle() : 0;
    }

    public float getBoostDurationMod(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeComparable(item)).getRight() : 0.0F;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var boosts = recipeManager.byType(POTION_DIFFUSER_BOOST.get());
        for (var entry : boosts.entrySet()) {
            addBoost(entry.getValue());
        }
    }
    // endregion
}
