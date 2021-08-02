package cofh.thermal.lib.util.recipes;

import cofh.lib.fluid.IFluidStackAccess;
import cofh.lib.inventory.IItemStackAccess;

import java.util.List;

/**
 * Simple interface to allow access to input slots and tanks when handed the containing object. Used in Recipes/Fuels.
 */
public interface IThermalInventory {

    List<? extends IItemStackAccess> inputSlots();

    List<? extends IFluidStackAccess> inputTanks();

}
