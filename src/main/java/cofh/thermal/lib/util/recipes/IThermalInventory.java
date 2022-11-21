package cofh.thermal.lib.util.recipes;

import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;

import java.util.List;

/**
 * Simple interface to allow access to input slots and tanks when handed the containing object. Used in Recipes/Fuels.
 */
public interface IThermalInventory {

    List<? extends IItemStackHolder> inputSlots();

    List<? extends IFluidStackHolder> inputTanks();

}
