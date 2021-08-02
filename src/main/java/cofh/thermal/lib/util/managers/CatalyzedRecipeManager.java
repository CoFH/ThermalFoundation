package cofh.thermal.lib.util.managers;

import cofh.lib.inventory.IItemStackAccess;
import cofh.thermal.lib.util.recipes.internal.IRecipeCatalyst;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface CatalyzedRecipeManager {

    List<ItemStack> getCatalysts();

    IRecipeCatalyst getCatalyst(IItemStackAccess input);

    IRecipeCatalyst getCatalyst(ItemStack input);

}
