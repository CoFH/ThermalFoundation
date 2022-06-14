package cofh.thermal.core.init;

import cofh.thermal.core.block.entity.ChargeBenchTile;
import cofh.thermal.core.block.entity.TinkerBenchTile;
import cofh.thermal.core.block.entity.device.*;
import cofh.thermal.core.block.entity.storage.EnergyCellTile;
import cofh.thermal.core.block.entity.storage.FluidCellTile;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static cofh.thermal.core.ThermalCore.TILE_ENTITIES;
import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreTileEntities {

    private TCoreTileEntities() {

    }

    public static void register() {

        TILE_ENTITIES.register(ID_DEVICE_HIVE_EXTRACTOR, () -> BlockEntityType.Builder.of(DeviceHiveExtractorTile::new, DEVICE_HIVE_EXTRACTOR_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_TREE_EXTRACTOR, () -> BlockEntityType.Builder.of(DeviceTreeExtractorTile::new, DEVICE_TREE_EXTRACTOR_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_FISHER, () -> BlockEntityType.Builder.of(DeviceFisherTile::new, DEVICE_FISHER_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_COMPOSTER, () -> BlockEntityType.Builder.of(DeviceComposterTile::new, DEVICE_COMPOSTER_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_SOIL_INFUSER, () -> BlockEntityType.Builder.of(DeviceSoilInfuserTile::new, DEVICE_SOIL_INFUSER_BLOCK).build(null));

        TILE_ENTITIES.register(ID_DEVICE_WATER_GEN, () -> BlockEntityType.Builder.of(DeviceWaterGenTile::new, DEVICE_WATER_GEN_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_ROCK_GEN, () -> BlockEntityType.Builder.of(DeviceRockGenTile::new, DEVICE_ROCK_GEN_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_COLLECTOR, () -> BlockEntityType.Builder.of(DeviceCollectorTile::new, DEVICE_COLLECTOR_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_NULLIFIER, () -> BlockEntityType.Builder.of(DeviceNullifierTile::new, DEVICE_NULLIFIER_BLOCK).build(null));
        TILE_ENTITIES.register(ID_DEVICE_POTION_DIFFUSER, () -> BlockEntityType.Builder.of(DevicePotionDiffuserTile::new, DEVICE_POTION_DIFFUSER_BLOCK).build(null));

        // TILE_ENTITIES.register(ID_CHUNK_LOADER, () -> TileEntityType.Builder.of(DeviceChunkLoaderTile::new, CHUNK_LOADER_BLOCK).build(null));

        TILE_ENTITIES.register(ID_TINKER_BENCH, () -> BlockEntityType.Builder.of(TinkerBenchTile::new, TINKER_BENCH_BLOCK).build(null));
        TILE_ENTITIES.register(ID_CHARGE_BENCH, () -> BlockEntityType.Builder.of(ChargeBenchTile::new, CHARGE_BENCH_BLOCK).build(null));

        TILE_ENTITIES.register(ID_ENERGY_CELL, () -> BlockEntityType.Builder.of(EnergyCellTile::new, ENERGY_CELL_BLOCK).build(null));
        TILE_ENTITIES.register(ID_FLUID_CELL, () -> BlockEntityType.Builder.of(FluidCellTile::new, FLUID_CELL_BLOCK).build(null));
        // TILE_ENTITIES.register(ID_ITEM_CELL, () -> TileEntityType.Builder.of(ItemCellTile::new, ITEM_CELL_BLOCK).build(null));
    }

}
