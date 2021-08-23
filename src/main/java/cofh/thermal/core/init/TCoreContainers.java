package cofh.thermal.core.init;

import cofh.core.util.ProxyUtils;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.core.inventory.container.ChunkLoaderContainer;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.core.inventory.container.device.*;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.inventory.container.storage.FluidCellContainer;
import cofh.thermal.core.inventory.container.storage.ItemCellContainer;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import net.minecraftforge.common.extensions.IForgeContainerType;

import static cofh.thermal.core.ThermalCore.CONTAINERS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreContainers {

    private TCoreContainers() {

    }

    public static void register() {

        CONTAINERS.register(ID_DEVICE_HIVE_EXTRACTOR, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceHiveExtractorContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_TREE_EXTRACTOR, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceTreeExtractorContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_FISHER, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceFisherContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_SOIL_INFUSER, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceSoilInfuserContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_WATER_GEN, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceWaterGenContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_ROCK_GEN, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceRockGenContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_COLLECTOR, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceCollectorContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_POTION_DIFFUSER, () -> IForgeContainerType.create((windowId, inv, data) -> new DevicePotionDiffuserContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_DEVICE_NULLIFIER, () -> IForgeContainerType.create((windowId, inv, data) -> new DeviceNullifierContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_CHUNK_LOADER, () -> IForgeContainerType.create((windowId, inv, data) -> new ChunkLoaderContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_TINKER_BENCH, () -> IForgeContainerType.create((windowId, inv, data) -> new TinkerBenchContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_CHARGE_BENCH, () -> IForgeContainerType.create((windowId, inv, data) -> new ChargeBenchContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_ENERGY_CELL, () -> IForgeContainerType.create((windowId, inv, data) -> new EnergyCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_FLUID_CELL, () -> IForgeContainerType.create((windowId, inv, data) -> new FluidCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        CONTAINERS.register(ID_ITEM_CELL, () -> IForgeContainerType.create((windowId, inv, data) -> new ItemCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));

        CONTAINERS.register(ID_SATCHEL, () -> IForgeContainerType.create((windowId, inv, data) -> new SatchelContainer(windowId, inv, ProxyUtils.getClientPlayer())));
    }

}
