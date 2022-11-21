package cofh.thermal.lib.util.managers;

import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;

import java.util.List;

public interface IFuelManager extends IManager {

    default boolean validFuel(IThermalInventory inventory) {

        return getFuel(inventory) != null;
    }

    IDynamoFuel getFuel(IThermalInventory inventory);

    List<IDynamoFuel> getFuelList();

}
