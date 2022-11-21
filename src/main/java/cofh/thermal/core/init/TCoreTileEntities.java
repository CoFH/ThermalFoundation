package cofh.thermal.core.init;

import cofh.thermal.core.block.entity.ChargeBenchTile;
import cofh.thermal.core.block.entity.TinkerBenchTile;
import cofh.thermal.core.block.entity.device.*;
import cofh.thermal.core.block.entity.storage.EnergyCellTile;
import cofh.thermal.core.block.entity.storage.FluidCellTile;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.TILE_ENTITIES;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreTileEntities {

    private TCoreTileEntities() {

    }

    public static void register() {

        // TILE_ENTITIES.register(ID_CHUNK_LOADER, () -> TileEntityType.Builder.of(DeviceChunkLoaderTile::new, CHUNK_LOADER_BLOCK).build(null));

        // TILE_ENTITIES.register(ID_ITEM_CELL, () -> TileEntityType.Builder.of(ItemCellTile::new, ITEM_CELL_BLOCK).build(null));
    }

    public static final RegistryObject<BlockEntityType<?>> DEVICE_HIVE_EXTRACTOR_TILE = TILE_ENTITIES.register(ID_DEVICE_HIVE_EXTRACTOR, () -> BlockEntityType.Builder.of(DeviceHiveExtractorTile::new, BLOCKS.get(ID_DEVICE_HIVE_EXTRACTOR)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_TREE_EXTRACTOR_TILE = TILE_ENTITIES.register(ID_DEVICE_TREE_EXTRACTOR, () -> BlockEntityType.Builder.of(DeviceTreeExtractorTile::new, BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_FISHER_TILE = TILE_ENTITIES.register(ID_DEVICE_FISHER, () -> BlockEntityType.Builder.of(DeviceFisherTile::new, BLOCKS.get(ID_DEVICE_FISHER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_COMPOSTER_TILE = TILE_ENTITIES.register(ID_DEVICE_COMPOSTER, () -> BlockEntityType.Builder.of(DeviceComposterTile::new, BLOCKS.get(ID_DEVICE_COMPOSTER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_SOIL_INFUSER_TILE = TILE_ENTITIES.register(ID_DEVICE_SOIL_INFUSER, () -> BlockEntityType.Builder.of(DeviceSoilInfuserTile::new, BLOCKS.get(ID_DEVICE_SOIL_INFUSER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_WATER_GEN_TILE = TILE_ENTITIES.register(ID_DEVICE_WATER_GEN, () -> BlockEntityType.Builder.of(DeviceWaterGenTile::new, BLOCKS.get(ID_DEVICE_WATER_GEN)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_ROCK_GEN_TILE = TILE_ENTITIES.register(ID_DEVICE_ROCK_GEN, () -> BlockEntityType.Builder.of(DeviceRockGenTile::new, BLOCKS.get(ID_DEVICE_ROCK_GEN)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_COLLECTOR_TILE = TILE_ENTITIES.register(ID_DEVICE_COLLECTOR, () -> BlockEntityType.Builder.of(DeviceCollectorTile::new, BLOCKS.get(ID_DEVICE_COLLECTOR)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_NULLIFIER_TILE = TILE_ENTITIES.register(ID_DEVICE_NULLIFIER, () -> BlockEntityType.Builder.of(DeviceNullifierTile::new, BLOCKS.get(ID_DEVICE_NULLIFIER)).build(null));
    public static final RegistryObject<BlockEntityType<?>> DEVICE_POTION_DIFFUSER_TILE = TILE_ENTITIES.register(ID_DEVICE_POTION_DIFFUSER, () -> BlockEntityType.Builder.of(DevicePotionDiffuserTile::new, BLOCKS.get(ID_DEVICE_POTION_DIFFUSER)).build(null));

    public static final RegistryObject<BlockEntityType<?>> TINKER_BENCH_TILE = TILE_ENTITIES.register(ID_TINKER_BENCH, () -> BlockEntityType.Builder.of(TinkerBenchTile::new, BLOCKS.get(ID_TINKER_BENCH)).build(null));
    public static final RegistryObject<BlockEntityType<?>> CHARGE_BENCH_TILE = TILE_ENTITIES.register(ID_CHARGE_BENCH, () -> BlockEntityType.Builder.of(ChargeBenchTile::new, BLOCKS.get(ID_CHARGE_BENCH)).build(null));

    public static final RegistryObject<BlockEntityType<?>> ENERGY_CELL_TILE = TILE_ENTITIES.register(ID_ENERGY_CELL, () -> BlockEntityType.Builder.of(EnergyCellTile::new, BLOCKS.get(ID_ENERGY_CELL)).build(null));
    public static final RegistryObject<BlockEntityType<?>> FLUID_CELL_TILE = TILE_ENTITIES.register(ID_FLUID_CELL, () -> BlockEntityType.Builder.of(FluidCellTile::new, BLOCKS.get(ID_FLUID_CELL)).build(null));

}
