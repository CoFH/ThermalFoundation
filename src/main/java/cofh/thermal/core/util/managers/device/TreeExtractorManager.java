package cofh.thermal.core.util.managers.device;

import cofh.lib.inventory.FalseIInventory;
import cofh.lib.util.ComparableItemStack;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.lib.util.managers.AbstractManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class TreeExtractorManager extends AbstractManager {

    private static final TreeExtractorManager INSTANCE = new TreeExtractorManager();

    protected Map<ComparableItemStack, FluidStack> itemMap = new Object2ObjectOpenHashMap<>();
    protected Map<ComparableItemStack, Pair<Integer, Float>> boostMap = new Object2ObjectOpenHashMap<>();

    protected IdentityHashMap<BlockState, FluidStack> trunkMap = new IdentityHashMap<>();
    protected SetMultimap<BlockState, BlockState> leafMap = HashMultimap.create();

    protected TreeExtractorManager() {

        super(8);
    }

    public static TreeExtractorManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        itemMap.clear();
        boostMap.clear();

        trunkMap.clear();
        leafMap.clear();
    }

    // region MAPPINGS
    public Set<BlockState> getMatchingLeaves(BlockState trunk) {

        return leafMap.get(trunk);
    }

    public boolean validTrunk(BlockState state) {

        return trunkMap.containsKey(state);
    }

    public FluidStack getFluid(BlockState trunk) {

        return validTrunk(trunk) ? trunkMap.get(trunk) : FluidStack.EMPTY;
    }

    public boolean addTrunkMapping(BlockState trunk, FluidStack stack) {

        if (stack.isEmpty() || trunk == null || trunk.getBlock() == Blocks.AIR) {
            return false;
        }
        trunkMap.put(trunk, stack);
        return true;
    }

    public boolean addLeafMapping(BlockState trunk, BlockState leaf) {

        if (trunk == null || trunk.getBlock() == Blocks.AIR || leaf == null || leaf.getBlock() == Blocks.AIR) {
            return false;
        }
        leafMap.put(trunk, leaf);
        return true;
    }

    public void addMapping(TreeExtractorMapping mapping) {

        addDefaultMappings(mapping.getTrunk(), mapping.getLeaves(), mapping.getFluid());
    }
    // endregion

    // region BOOSTS
    public boolean validBoost(ItemStack item) {

        return boostMap.containsKey(convert(item));
    }

    public void addBoost(TreeExtractorBoost boost) {

        for (ItemStack ingredient : boost.getIngredient().getMatchingStacks()) {
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
        Map<ResourceLocation, IRecipe<FalseIInventory>> mappings = recipeManager.getRecipes(TCoreRecipeTypes.MAPPING_TREE_EXTRACTOR);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : mappings.entrySet()) {
            addMapping((TreeExtractorMapping) entry.getValue());
        }

        Map<ResourceLocation, IRecipe<FalseIInventory>> boosts = recipeManager.getRecipes(TCoreRecipeTypes.BOOST_TREE_EXTRACTOR);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : boosts.entrySet()) {
            addBoost((TreeExtractorBoost) entry.getValue());
        }
    }
    // endregion

    // region HELPERS
    private void addDefaultMappings(Block trunk, Block leaf, FluidStack stack) {

        if (trunk instanceof RotatedPillarBlock && leaf instanceof LeavesBlock && !stack.isEmpty()) {
            BlockState state = trunk.getDefaultState();
            addTrunkMapping(state, stack);
            for (int i = 1; i <= 7; ++i) {
                addLeafMapping(state, leaf.getDefaultState().with(LeavesBlock.DISTANCE, i).with(LeavesBlock.PERSISTENT, false));
            }
        }
    }
    // endregion
}
