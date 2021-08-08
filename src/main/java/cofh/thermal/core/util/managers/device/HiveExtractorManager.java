package cofh.thermal.core.util.managers.device;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.HiveExtractorMapping;
import cofh.thermal.lib.util.managers.AbstractManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.IdentityHashMap;
import java.util.Map;

import static cofh.lib.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.references.CoreReferences.FLUID_HONEY;

public class HiveExtractorManager extends AbstractManager {

    private static final int COMB_AMOUNT = 2;
    private static final int HONEY_AMOUNT = 250;

    private static final HiveExtractorManager INSTANCE = new HiveExtractorManager();

    protected IdentityHashMap<Block, Pair<ItemStack, FluidStack>> hiveMap = new IdentityHashMap<>();

    public static HiveExtractorManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        hiveMap.clear();
    }

    // region MAPPINGS
    public boolean specificHive(BlockState hive) {

        return hiveMap.containsKey(hive.getBlock());
    }

    public ItemStack getItem(BlockState hive) {

        return specificHive(hive) ? hiveMap.get(hive.getBlock()).getLeft() : cloneStack(Items.HONEYCOMB, COMB_AMOUNT);
    }

    public FluidStack getFluid(BlockState hive) {

        return specificHive(hive) ? hiveMap.get(hive.getBlock()).getRight() : new FluidStack(FLUID_HONEY, HONEY_AMOUNT);
    }

    public void addMapping(HiveExtractorMapping mapping) {

        if (mapping.getHive() == Blocks.AIR || (mapping.getItem().isEmpty() && mapping.getFluid().isEmpty())) {
            return;
        }
        hiveMap.put(mapping.getHive(), Pair.of(mapping.getItem(), mapping.getFluid()));
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        Map<ResourceLocation, IRecipe<FalseIInventory>> mappings = recipeManager.getRecipes(TCoreRecipeTypes.MAPPING_HIVE_EXTRACTOR);
        for (Map.Entry<ResourceLocation, IRecipe<FalseIInventory>> entry : mappings.entrySet()) {
            addMapping((HiveExtractorMapping) entry.getValue());
        }
    }
    // endregion
}
