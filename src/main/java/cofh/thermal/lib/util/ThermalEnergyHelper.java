package cofh.thermal.lib.util;

import cofh.lib.capability.CapabilityRedstoneFlux;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * This class contains helper functions related to Redstone Flux, aka the Forge Energy system.
 *
 * @author King Lemming
 */
public class ThermalEnergyHelper {

    public static boolean standaloneRedstoneFlux;

    private ThermalEnergyHelper() {

    }

    public static boolean hasEnergyHandlerCap(ItemStack item) {

        return !item.isEmpty() && item.getCapability(getBaseEnergySystem()).isPresent();
    }

    public static Capability<? extends IEnergyStorage> getBaseEnergySystem() {

        return standaloneRedstoneFlux ? CapabilityRedstoneFlux.RF_ENERGY : CapabilityEnergy.ENERGY;
    }

}
