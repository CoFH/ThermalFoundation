package cofh.thermal.lib.util.managers;

import cofh.lib.api.inventory.IItemStackHolder;
import cofh.thermal.lib.util.recipes.internal.IRecipeCatalyst;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface CatalyzedRecipeManager {

    List<ItemStack> getCatalysts();

    IRecipeCatalyst getCatalyst(IItemStackHolder input);

    IRecipeCatalyst getCatalyst(ItemStack input);

}
