package cofh.thermal.lib.item;

import cofh.lib.api.item.IEnergyContainerItem;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

public interface IFlexibleEnergyContainerItem extends IEnergyContainerItem {

    default Capability<? extends IEnergyStorage> getEnergyCapability() {

        return ThermalEnergyHelper.getBaseEnergySystem();
    }

}
