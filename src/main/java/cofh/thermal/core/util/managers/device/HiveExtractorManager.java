package cofh.thermal.core.util.managers.device;

import cofh.lib.inventory.FalseIInventory;
import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.HiveExtractorMapping;
import cofh.thermal.lib.util.managers.AbstractManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.IdentityHashMap;
import java.util.Map;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.core.util.references.CoreReferences.FLUID_HONEY;

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

        return specificHive(hive) ? cloneStack(hiveMap.get(hive.getBlock()).getLeft()) : cloneStack(Items.HONEYCOMB, COMB_AMOUNT);
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
        Map<ResourceLocation, Recipe<FalseIInventory>> mappings = recipeManager.byType(TCoreRecipeTypes.MAPPING_HIVE_EXTRACTOR);
        for (Map.Entry<ResourceLocation, Recipe<FalseIInventory>> entry : mappings.entrySet()) {
            addMapping((HiveExtractorMapping) entry.getValue());
        }
    }
    // endregion
}
