package cofh.thermal.core.init;

import cofh.thermal.core.entity.monster.BasalzEntity;
import cofh.thermal.core.entity.monster.BlitzEntity;
import cofh.thermal.core.entity.monster.BlizzEntity;
import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
import cofh.thermal.core.entity.projectile.BlitzProjectileEntity;
import cofh.thermal.core.entity.projectile.BlizzProjectileEntity;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.core.inventory.container.ProjectBenchContainer;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.core.inventory.container.device.*;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.inventory.container.storage.FluidCellContainer;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import cofh.thermal.core.tileentity.ChargeBenchTile;
import cofh.thermal.core.tileentity.ProjectBenchTile;
import cofh.thermal.core.tileentity.TinkerBenchTile;
import cofh.thermal.core.tileentity.device.*;
import cofh.thermal.core.tileentity.storage.EnergyCellTile;
import cofh.thermal.core.tileentity.storage.FluidCellTile;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.lib.common.ThermalIDs.*;

@ObjectHolder (ID_THERMAL)
public class TCoreReferences {

    private TCoreReferences() {

    }

    // region ENTITIES
    @ObjectHolder (ID_BASALZ)
    public static final EntityType<BasalzEntity> BASALZ_ENTITY = null;
    @ObjectHolder (ID_BLITZ)
    public static final EntityType<BlitzEntity> BLITZ_ENTITY = null;
    @ObjectHolder (ID_BLIZZ)
    public static final EntityType<BlizzEntity> BLIZZ_ENTITY = null;

    @ObjectHolder (ID_BASALZ_PROJECTILE)
    public static final EntityType<BasalzProjectileEntity> BASALZ_PROJECTILE_ENTITY = null;
    @ObjectHolder (ID_BLITZ_PROJECTILE)
    public static final EntityType<BlitzProjectileEntity> BLITZ_PROJECTILE_ENTITY = null;
    @ObjectHolder (ID_BLIZZ_PROJECTILE)
    public static final EntityType<BlizzProjectileEntity> BLIZZ_PROJECTILE_ENTITY = null;
    // endregion

    // region ITEMS

    // endregion

    // region DEVICES
    @ObjectHolder (ID_DEVICE_HIVE_EXTRACTOR)
    public static final Block DEVICE_HIVE_EXTRACTOR_BLOCK = null;
    @ObjectHolder (ID_DEVICE_HIVE_EXTRACTOR)
    public static final TileEntityType<DeviceHiveExtractorTile> DEVICE_HIVE_EXTRACTOR_TILE = null;
    @ObjectHolder (ID_DEVICE_HIVE_EXTRACTOR)
    public static final ContainerType<DeviceHiveExtractorContainer> DEVICE_HIVE_EXTRACTOR_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_TREE_EXTRACTOR)
    public static final Block DEVICE_TREE_EXTRACTOR_BLOCK = null;
    @ObjectHolder (ID_DEVICE_TREE_EXTRACTOR)
    public static final TileEntityType<DeviceTreeExtractorTile> DEVICE_TREE_EXTRACTOR_TILE = null;
    @ObjectHolder (ID_DEVICE_TREE_EXTRACTOR)
    public static final ContainerType<DeviceTreeExtractorContainer> DEVICE_TREE_EXTRACTOR_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_FISHER)
    public static final Block DEVICE_FISHER_BLOCK = null;
    @ObjectHolder (ID_DEVICE_FISHER)
    public static final TileEntityType<DeviceFisherTile> DEVICE_FISHER_TILE = null;
    @ObjectHolder (ID_DEVICE_FISHER)
    public static final ContainerType<DeviceFisherContainer> DEVICE_FISHER_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_SOIL_INFUSER)
    public static final Block DEVICE_SOIL_INFUSER_BLOCK = null;
    @ObjectHolder (ID_DEVICE_SOIL_INFUSER)
    public static final TileEntityType<DeviceSoilInfuserTile> DEVICE_SOIL_INFUSER_TILE = null;
    @ObjectHolder (ID_DEVICE_SOIL_INFUSER)
    public static final ContainerType<DeviceSoilInfuserContainer> DEVICE_SOIL_INFUSER_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_WATER_GEN)
    public static final Block DEVICE_WATER_GEN_BLOCK = null;
    @ObjectHolder (ID_DEVICE_WATER_GEN)
    public static final TileEntityType<DeviceWaterGenTile> DEVICE_WATER_GEN_TILE = null;
    @ObjectHolder (ID_DEVICE_WATER_GEN)
    public static final ContainerType<DeviceWaterGenContainer> DEVICE_WATER_GEN_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_ROCK_GEN)
    public static final Block DEVICE_ROCK_GEN_BLOCK = null;
    @ObjectHolder (ID_DEVICE_ROCK_GEN)
    public static final TileEntityType<DeviceRockGenTile> DEVICE_ROCK_GEN_TILE = null;
    @ObjectHolder (ID_DEVICE_ROCK_GEN)
    public static final ContainerType<DeviceRockGenContainer> DEVICE_ROCK_GEN_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_COLLECTOR)
    public static final Block DEVICE_COLLECTOR_BLOCK = null;
    @ObjectHolder (ID_DEVICE_COLLECTOR)
    public static final TileEntityType<DeviceCollectorTile> DEVICE_COLLECTOR_TILE = null;
    @ObjectHolder (ID_DEVICE_COLLECTOR)
    public static final ContainerType<DeviceCollectorContainer> DEVICE_COLLECTOR_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_NULLIFIER)
    public static final Block DEVICE_NULLIFIER_BLOCK = null;
    @ObjectHolder (ID_DEVICE_NULLIFIER)
    public static final TileEntityType<DeviceNullifierTile> DEVICE_NULLIFIER_TILE = null;
    @ObjectHolder (ID_DEVICE_NULLIFIER)
    public static final ContainerType<DeviceNullifierContainer> DEVICE_NULLIFIER_CONTAINER = null;

    @ObjectHolder (ID_DEVICE_POTION_DIFFUSER)
    public static final Block DEVICE_POTION_DIFFUSER_BLOCK = null;
    @ObjectHolder (ID_DEVICE_POTION_DIFFUSER)
    public static final TileEntityType<DevicePotionDiffuserTile> DEVICE_POTION_DIFFUSER_TILE = null;
    @ObjectHolder (ID_DEVICE_POTION_DIFFUSER)
    public static final ContainerType<DevicePotionDiffuserContainer> DEVICE_POTION_DIFFUSER_CONTAINER = null;
    // endregion

    // region STORAGE
    @ObjectHolder (ID_ENERGY_CELL)
    public static final Block ENERGY_CELL_BLOCK = null;
    @ObjectHolder (ID_ENERGY_CELL)
    public static final TileEntityType<EnergyCellTile> ENERGY_CELL_TILE = null;
    @ObjectHolder (ID_ENERGY_CELL)
    public static final ContainerType<EnergyCellContainer> ENERGY_CELL_CONTAINER = null;

    @ObjectHolder (ID_FLUID_CELL)
    public static final Block FLUID_CELL_BLOCK = null;
    @ObjectHolder (ID_FLUID_CELL)
    public static final TileEntityType<FluidCellTile> FLUID_CELL_TILE = null;
    @ObjectHolder (ID_FLUID_CELL)
    public static final ContainerType<FluidCellContainer> FLUID_CELL_CONTAINER = null;

    //    @ObjectHolder(ID_ITEM_CELL)
    //    public static final Block ITEM_CELL_BLOCK = null;
    //    @ObjectHolder(ID_ITEM_CELL)
    //    public static final TileEntityType<ItemCellTile> ITEM_CELL_TILE = null;
    //    @ObjectHolder(ID_ITEM_CELL)
    //    public static final ContainerType<ItemCellContainer> ITEM_CELL_CONTAINER = null;

    @ObjectHolder (ID_SATCHEL)
    public static final ContainerType<SatchelContainer> SATCHEL_CONTAINER = null;
    // endregion

    // region MISC TILES
    @ObjectHolder (ID_CHARGE_BENCH)
    public static final Block CHARGE_BENCH_BLOCK = null;
    @ObjectHolder (ID_CHARGE_BENCH)
    public static final TileEntityType<ChargeBenchTile> CHARGE_BENCH_TILE = null;
    @ObjectHolder (ID_CHARGE_BENCH)
    public static final ContainerType<ChargeBenchContainer> CHARGE_BENCH_CONTAINER = null;

    @ObjectHolder (ID_PROJECT_BENCH)
    public static final Block PROJECT_BENCH_BLOCK = null;
    @ObjectHolder (ID_PROJECT_BENCH)
    public static final TileEntityType<ProjectBenchTile> PROJECT_BENCH_TILE = null;
    @ObjectHolder (ID_PROJECT_BENCH)
    public static final ContainerType<ProjectBenchContainer> PROJECT_BENCH_CONTAINER = null;

    @ObjectHolder (ID_TINKER_BENCH)
    public static final Block TINKER_BENCH_BLOCK = null;
    @ObjectHolder (ID_TINKER_BENCH)
    public static final TileEntityType<TinkerBenchTile> TINKER_BENCH_TILE = null;
    @ObjectHolder (ID_TINKER_BENCH)
    public static final ContainerType<TinkerBenchContainer> TINKER_BENCH_CONTAINER = null;

    //    @ObjectHolder (ID_CHUNK_LOADER)
    //    public static final Block CHUNK_LOADER_BLOCK = null;
    //    @ObjectHolder (ID_CHUNK_LOADER)
    //    public static final TileEntityType<DeviceChunkLoaderTile> CHUNK_LOADER_TILE = null;
    //    @ObjectHolder (ID_CHUNK_LOADER)
    //    public static final ContainerType<ChunkLoaderContainer> CHUNK_LOADER_CONTAINER = null;
    // endregion
}
