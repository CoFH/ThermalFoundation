package cofh.thermal.core.init;

import cofh.core.util.ProxyUtils;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.core.inventory.container.device.*;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.inventory.container.storage.FluidCellContainer;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import net.minecraftforge.common.extensions.IForgeMenuType;

import static cofh.thermal.core.ThermalCore.CONTAINERS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreContainers {

    private TCoreContainers() {

    }

    public static void register() {

        CONTAINERS.register(ID_DEVICE_HIVE_EXTRACTOR, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceHiveExtractorContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_TREE_EXTRACTOR, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceTreeExtractorContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_FISHER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceFisherContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_COMPOSTER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceComposterContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_SOIL_INFUSER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceSoilInfuserContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        
        CONTAINERS.register(ID_DEVICE_WATER_GEN, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceWaterGenContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_ROCK_GEN, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceRockGenContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_COLLECTOR, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceCollectorContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_POTION_DIFFUSER, () -> IForgeMenuType.create((windowId, inv, data) -> new DevicePotionDiffuserContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_NULLIFIER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceNullifierContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        // CONTAINERS.register(ID_CHUNK_LOADER, () -> IForgeMenuType.create((windowId, inv, data) -> new ChunkLoaderContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_TINKER_BENCH, () -> IForgeMenuType.create((windowId, inv, data) -> new TinkerBenchContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_CHARGE_BENCH, () -> IForgeMenuType.create((windowId, inv, data) -> new ChargeBenchContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_ENERGY_CELL, () -> IForgeMenuType.create((windowId, inv, data) -> new EnergyCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_FLUID_CELL, () -> IForgeMenuType.create((windowId, inv, data) -> new FluidCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        // CONTAINERS.register(ID_ITEM_CELL, () -> IForgeMenuType.create((windowId, inv, data) -> new ItemCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_SATCHEL, () -> IForgeMenuType.create((windowId, inv, data) -> new SatchelContainer(windowId, inv, ProxyUtils.getClientPlayer())));
    }

}
