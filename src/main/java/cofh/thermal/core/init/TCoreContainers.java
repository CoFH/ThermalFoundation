package cofh.thermal.core.init;

import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.core.inventory.container.device.*;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.inventory.container.storage.FluidCellContainer;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

import static cofh.core.util.ProxyUtils.getClientPlayer;
import static cofh.core.util.ProxyUtils.getClientWorld;
import static cofh.thermal.core.ThermalCore.CONTAINERS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreContainers {

    private TCoreContainers() {

    }

    public static void register() {

        // CONTAINERS.register(ID_CHUNK_LOADER, () -> IForgeMenuType.create((windowId, inv, data) -> new ChunkLoaderContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
        // CONTAINERS.register(ID_ITEM_CELL, () -> IForgeMenuType.create((windowId, inv, data) -> new ItemCellContainer(windowId, ProxyUtils.getClientWorld(), data.readBlockPos(), inv, ProxyUtils.getClientPlayer())));
    }

    public static final RegistryObject<MenuType<DeviceHiveExtractorContainer>> DEVICE_HIVE_EXTRACTOR_CONTAINER = CONTAINERS.register(ID_DEVICE_HIVE_EXTRACTOR, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceHiveExtractorContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceTreeExtractorContainer>> DEVICE_TREE_EXTRACTOR_CONTAINER = CONTAINERS.register(ID_DEVICE_TREE_EXTRACTOR, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceTreeExtractorContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceFisherContainer>> DEVICE_FISHER_CONTAINER = CONTAINERS.register(ID_DEVICE_FISHER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceFisherContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceComposterContainer>> DEVICE_COMPOSTER_CONTAINER = CONTAINERS.register(ID_DEVICE_COMPOSTER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceComposterContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceSoilInfuserContainer>> DEVICE_SOIL_INFUSER_CONTAINER = CONTAINERS.register(ID_DEVICE_SOIL_INFUSER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceSoilInfuserContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceWaterGenContainer>> DEVICE_WATER_GEN_CONTAINER = CONTAINERS.register(ID_DEVICE_WATER_GEN, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceWaterGenContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceRockGenContainer>> DEVICE_ROCK_GEN_CONTAINER = CONTAINERS.register(ID_DEVICE_ROCK_GEN, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceRockGenContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceCollectorContainer>> DEVICE_COLLECTOR_CONTAINER = CONTAINERS.register(ID_DEVICE_COLLECTOR, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceCollectorContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceMagnetBlockerContainer>> DEVICE_MAGNET_BLOCKER_CONTAINER = CONTAINERS.register(ID_DEVICE_MAGNET_BLOCKER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceMagnetBlockerContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DevicePotionDiffuserContainer>> DEVICE_POTION_DIFFUSER_CONTAINER = CONTAINERS.register(ID_DEVICE_POTION_DIFFUSER, () -> IForgeMenuType.create((windowId, inv, data) -> new DevicePotionDiffuserContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<DeviceNullifierContainer>> DEVICE_NULLIFIER_CONTAINER = CONTAINERS.register(ID_DEVICE_NULLIFIER, () -> IForgeMenuType.create((windowId, inv, data) -> new DeviceNullifierContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));

    public static final RegistryObject<MenuType<TinkerBenchContainer>> TINKER_BENCH_CONTAINER = CONTAINERS.register(ID_TINKER_BENCH, () -> IForgeMenuType.create((windowId, inv, data) -> new TinkerBenchContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<ChargeBenchContainer>> CHARGE_BENCH_CONTAINER = CONTAINERS.register(ID_CHARGE_BENCH, () -> IForgeMenuType.create((windowId, inv, data) -> new ChargeBenchContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));

    public static final RegistryObject<MenuType<EnergyCellContainer>> ENERGY_CELL_CONTAINER = CONTAINERS.register(ID_ENERGY_CELL, () -> IForgeMenuType.create((windowId, inv, data) -> new EnergyCellContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));
    public static final RegistryObject<MenuType<FluidCellContainer>> FLUID_CELL_CONTAINER = CONTAINERS.register(ID_FLUID_CELL, () -> IForgeMenuType.create((windowId, inv, data) -> new FluidCellContainer(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));

    public static final RegistryObject<MenuType<SatchelContainer>> SATCHEL_CONTAINER = CONTAINERS.register(ID_SATCHEL, () -> IForgeMenuType.create((windowId, inv, data) -> new SatchelContainer(windowId, inv, getClientPlayer())));

}
