package cofh.thermal.core.util.managers.device;

import cofh.lib.inventory.FalseIInventory;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
import cofh.thermal.core.util.recipes.device.TreeExtractorRecipe;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static cofh.thermal.core.init.TCoreRecipeTypes.TREE_EXTRACTOR_BOOST;
import static cofh.thermal.core.init.TCoreRecipeTypes.TREE_EXTRACTOR_RECIPE;

public class TreeExtractorManager extends AbstractManager {

    private static final TreeExtractorManager INSTANCE = new TreeExtractorManager();

    protected Map<ComparableItemStack, Pair<Integer, Float>> boostMap = new Object2ObjectOpenHashMap<>();

    protected List<TreeExtractorRecipe> recipes = new ArrayList<>();
    protected Set<BlockState> validLogs = new ObjectOpenHashSet<>();

    protected TreeExtractorManager() {

        super(8);
    }

    public static TreeExtractorManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        boostMap.clear();
        recipes.clear();
        validLogs.clear();
    }

    public Collection<TreeExtractorRecipe> getRecipes() {

        return recipes;
    }

    public Collection<BlockState> getValidLogs() {

        return validLogs;
    }

    // region BOOSTS
    public boolean validBoost(ItemStack item) {

        return boostMap.containsKey(convert(item));
    }

    public void addBoost(TreeExtractorBoost boost) {

        for (ItemStack ingredient : boost.getIngredient().getItems()) {
            boostMap.put(convert(ingredient), Pair.of(boost.getCycles(), boost.getOutputMod()));
        }
    }

    public int getBoostCycles(ItemStack item) {

        return validBoost(item) ? boostMap.get(convert(item)).getLeft() : 0;
    }

    public float getBoostOutputMod(ItemStack item) {

        return validBoost(item) ? boostMap.get(convert(item)).getRight() : 1.0F;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, Recipe<FalseIInventory>> mappings = recipeManager.byType(TREE_EXTRACTOR_RECIPE.get());
        for (Recipe<FalseIInventory> rec : mappings.values()) {
            TreeExtractorRecipe recipe = (TreeExtractorRecipe) rec;
            recipes.add(recipe);
            validLogs.addAll(recipe.getTrunk().getBlockStates());
        }
        var boosts = recipeManager.byType(TREE_EXTRACTOR_BOOST.get());
        for (var entry : boosts.entrySet()) {
            addBoost((TreeExtractorBoost) entry.getValue());
        }
    }
    // endregion
}
