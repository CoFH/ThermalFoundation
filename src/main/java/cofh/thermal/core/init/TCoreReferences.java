package cofh.thermal.core.init;

import cofh.thermal.core.entity.item.*;
import cofh.thermal.core.entity.monster.BasalzEntity;
import cofh.thermal.core.entity.monster.BlitzEntity;
import cofh.thermal.core.entity.monster.BlizzEntity;
import cofh.thermal.core.entity.projectile.*;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.core.inventory.container.ChunkLoaderContainer;
import cofh.thermal.core.inventory.container.ProjectBenchContainer;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.core.inventory.container.device.*;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.inventory.container.storage.FluidCellContainer;
import cofh.thermal.core.inventory.container.storage.ItemCellContainer;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import cofh.thermal.core.tileentity.ChargeBenchTile;
import cofh.thermal.core.tileentity.DeviceChunkLoaderTile;
import cofh.thermal.core.tileentity.ProjectBenchTile;
import cofh.thermal.core.tileentity.TinkerBenchTile;
import cofh.thermal.core.tileentity.device.*;
import cofh.thermal.core.tileentity.storage.EnergyCellTile;
import cofh.thermal.core.tileentity.storage.FluidCellTile;
import cofh.thermal.core.tileentity.storage.ItemCellTile;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.lib.common.ThermalIDs.*;

@ObjectHolder(ID_THERMAL)
public class TCoreReferences {

    private TCoreReferences() {

    }

    // region ENTITIES
    @ObjectHolder(ID_BASALZ)
    public static final EntityType<BasalzEntity> BASALZ_ENTITY = null;
    @ObjectHolder(ID_BLITZ)
    public static final EntityType<BlitzEntity> BLITZ_ENTITY = null;
    @ObjectHolder(ID_BLIZZ)
    public static final EntityType<BlizzEntity> BLIZZ_ENTITY = null;

    @ObjectHolder(ID_BASALZ_PROJECTILE)
    public static final EntityType<BasalzProjectileEntity> BASALZ_PROJECTILE_ENTITY = null;
    @ObjectHolder(ID_BLITZ_PROJECTILE)
    public static final EntityType<BlitzProjectileEntity> BLITZ_PROJECTILE_ENTITY = null;
    @ObjectHolder(ID_BLIZZ_PROJECTILE)
    public static final EntityType<BlizzProjectileEntity> BLIZZ_PROJECTILE_ENTITY = null;

    @ObjectHolder(ID_EXPLOSIVE_GRENADE)
    public static final EntityType<ExplosiveGrenadeEntity> EXPLOSIVE_GRENADE_ENTITY = null;

    @ObjectHolder(ID_SLIME_GRENADE)
    public static final EntityType<SlimeGrenadeEntity> SLIME_GRENADE_ENTITY = null;
    @ObjectHolder(ID_REDSTONE_GRENADE)
    public static final EntityType<RedstoneGrenadeEntity> REDSTONE_GRENADE_ENTITY = null;
    @ObjectHolder(ID_GLOWSTONE_GRENADE)
    public static final EntityType<GlowstoneGrenadeEntity> GLOWSTONE_GRENADE_ENTITY = null;
    @ObjectHolder(ID_ENDER_GRENADE)
    public static final EntityType<EnderGrenadeEntity> ENDER_GRENADE_ENTITY = null;

    @ObjectHolder(ID_PHYTO_GRENADE)
    public static final EntityType<PhytoGrenadeEntity> PHYTO_GRENADE_ENTITY = null;

    @ObjectHolder(ID_FIRE_GRENADE)
    public static final EntityType<FireGrenadeEntity> FIRE_GRENADE_ENTITY = null;
    @ObjectHolder(ID_EARTH_GRENADE)
    public static final EntityType<EarthGrenadeEntity> EARTH_GRENADE_ENTITY = null;
    @ObjectHolder(ID_ICE_GRENADE)
    public static final EntityType<IceGrenadeEntity> ICE_GRENADE_ENTITY = null;
    @ObjectHolder(ID_LIGHTNING_GRENADE)
    public static final EntityType<LightningGrenadeEntity> LIGHTNING_GRENADE_ENTITY = null;

    @ObjectHolder(ID_NUKE_GRENADE)
    public static final EntityType<LightningGrenadeEntity> NUKE_GRENADE_ENTITY = null;

    @ObjectHolder(ID_SLIME_TNT)
    public static final EntityType<SlimeTNTEntity> SLIME_TNT_ENTITY = null;
    @ObjectHolder(ID_REDSTONE_TNT)
    public static final EntityType<RedstoneTNTEntity> REDSTONE_TNT_ENTITY = null;
    @ObjectHolder(ID_GLOWSTONE_TNT)
    public static final EntityType<GlowstoneTNTEntity> GLOWSTONE_TNT_ENTITY = null;
    @ObjectHolder(ID_ENDER_TNT)
    public static final EntityType<EnderTNTEntity> ENDER_TNT_ENTITY = null;

    @ObjectHolder(ID_PHYTO_TNT)
    public static final EntityType<PhytoTNTEntity> PHYTO_TNT_ENTITY = null;

    @ObjectHolder(ID_FIRE_TNT)
    public static final EntityType<FireTNTEntity> FIRE_TNT_ENTITY = null;
    @ObjectHolder(ID_EARTH_TNT)
    public static final EntityType<EarthTNTEntity> EARTH_TNT_ENTITY = null;
    @ObjectHolder(ID_ICE_TNT)
    public static final EntityType<IceTNTEntity> ICE_TNT_ENTITY = null;
    @ObjectHolder(ID_LIGHTNING_TNT)
    public static final EntityType<LightningTNTEntity> LIGHTNING_TNT_ENTITY = null;

    @ObjectHolder(ID_NUKE_TNT)
    public static final EntityType<NukeTNTEntity> NUKE_TNT_ENTITY = null;
    // endregion

    // region ITEMS
    @ObjectHolder(ID_EXPLOSIVE_GRENADE)
    public static final Item EXPLOSIVE_GRENADE_ITEM = null;

    @ObjectHolder(ID_SLIME_GRENADE)
    public static final Item SLIME_GRENADE_ITEM = null;
    @ObjectHolder(ID_REDSTONE_GRENADE)
    public static final Item REDSTONE_GRENADE_ITEM = null;
    @ObjectHolder(ID_GLOWSTONE_GRENADE)
    public static final Item GLOWSTONE_GRENADE_ITEM = null;
    @ObjectHolder(ID_ENDER_GRENADE)
    public static final Item ENDER_GRENADE_ITEM = null;

    @ObjectHolder(ID_PHYTO_GRENADE)
    public static final Item PHYTO_GRENADE_ITEM = null;

    @ObjectHolder(ID_FIRE_GRENADE)
    public static final Item FIRE_GRENADE_ITEM = null;
    @ObjectHolder(ID_EARTH_GRENADE)
    public static final Item EARTH_GRENADE_ITEM = null;
    @ObjectHolder(ID_ICE_GRENADE)
    public static final Item ICE_GRENADE_ITEM = null;
    @ObjectHolder(ID_LIGHTNING_GRENADE)
    public static final Item LIGHTNING_GRENADE_ITEM = null;

    @ObjectHolder(ID_NUKE_GRENADE)
    public static final Item NUKE_GRENADE_ITEM = null;
    // endregion

    // region DEVICES
    @ObjectHolder(ID_DEVICE_HIVE_EXTRACTOR)
    public static final Block DEVICE_HIVE_EXTRACTOR_BLOCK = null;
    @ObjectHolder(ID_DEVICE_HIVE_EXTRACTOR)
    public static final TileEntityType<DeviceHiveExtractorTile> DEVICE_HIVE_EXTRACTOR_TILE = null;
    @ObjectHolder(ID_DEVICE_HIVE_EXTRACTOR)
    public static final ContainerType<DeviceHiveExtractorContainer> DEVICE_HIVE_EXTRACTOR_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_TREE_EXTRACTOR)
    public static final Block DEVICE_TREE_EXTRACTOR_BLOCK = null;
    @ObjectHolder(ID_DEVICE_TREE_EXTRACTOR)
    public static final TileEntityType<DeviceTreeExtractorTile> DEVICE_TREE_EXTRACTOR_TILE = null;
    @ObjectHolder(ID_DEVICE_TREE_EXTRACTOR)
    public static final ContainerType<DeviceTreeExtractorContainer> DEVICE_TREE_EXTRACTOR_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_FISHER)
    public static final Block DEVICE_FISHER_BLOCK = null;
    @ObjectHolder(ID_DEVICE_FISHER)
    public static final TileEntityType<DeviceFisherTile> DEVICE_FISHER_TILE = null;
    @ObjectHolder(ID_DEVICE_FISHER)
    public static final ContainerType<DeviceFisherContainer> DEVICE_FISHER_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_SOIL_INFUSER)
    public static final Block DEVICE_SOIL_INFUSER_BLOCK = null;
    @ObjectHolder(ID_DEVICE_SOIL_INFUSER)
    public static final TileEntityType<DeviceSoilInfuserTile> DEVICE_SOIL_INFUSER_TILE = null;
    @ObjectHolder(ID_DEVICE_SOIL_INFUSER)
    public static final ContainerType<DeviceSoilInfuserContainer> DEVICE_SOIL_INFUSER_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_WATER_GEN)
    public static final Block DEVICE_WATER_GEN_BLOCK = null;
    @ObjectHolder(ID_DEVICE_WATER_GEN)
    public static final TileEntityType<DeviceWaterGenTile> DEVICE_WATER_GEN_TILE = null;
    @ObjectHolder(ID_DEVICE_WATER_GEN)
    public static final ContainerType<DeviceWaterGenContainer> DEVICE_WATER_GEN_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_ROCK_GEN)
    public static final Block DEVICE_ROCK_GEN_BLOCK = null;
    @ObjectHolder(ID_DEVICE_ROCK_GEN)
    public static final TileEntityType<DeviceRockGenTile> DEVICE_ROCK_GEN_TILE = null;
    @ObjectHolder(ID_DEVICE_ROCK_GEN)
    public static final ContainerType<DeviceRockGenContainer> DEVICE_ROCK_GEN_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_COLLECTOR)
    public static final Block DEVICE_COLLECTOR_BLOCK = null;
    @ObjectHolder(ID_DEVICE_COLLECTOR)
    public static final TileEntityType<DeviceCollectorTile> DEVICE_COLLECTOR_TILE = null;
    @ObjectHolder(ID_DEVICE_COLLECTOR)
    public static final ContainerType<DeviceCollectorContainer> DEVICE_COLLECTOR_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_POTION_DIFFUSER)
    public static final Block DEVICE_POTION_DIFFUSER_BLOCK = null;
    @ObjectHolder(ID_DEVICE_POTION_DIFFUSER)
    public static final TileEntityType<DevicePotionDiffuserTile> DEVICE_POTION_DIFFUSER_TILE = null;
    @ObjectHolder(ID_DEVICE_POTION_DIFFUSER)
    public static final ContainerType<DevicePotionDiffuserContainer> DEVICE_POTION_DIFFUSER_CONTAINER = null;

    @ObjectHolder(ID_DEVICE_NULLIFIER)
    public static final Block DEVICE_NULLIFIER_BLOCK = null;
    @ObjectHolder(ID_DEVICE_NULLIFIER)
    public static final TileEntityType<DeviceNullifierTile> DEVICE_NULLIFIER_TILE = null;
    @ObjectHolder(ID_DEVICE_NULLIFIER)
    public static final ContainerType<DeviceNullifierContainer> DEVICE_NULLIFIER_CONTAINER = null;
    // endregion

    // region STORAGE
    @ObjectHolder(ID_ENERGY_CELL)
    public static final Block ENERGY_CELL_BLOCK = null;
    @ObjectHolder(ID_ENERGY_CELL)
    public static final TileEntityType<EnergyCellTile> ENERGY_CELL_TILE = null;
    @ObjectHolder(ID_ENERGY_CELL)
    public static final ContainerType<EnergyCellContainer> ENERGY_CELL_CONTAINER = null;

    @ObjectHolder(ID_FLUID_CELL)
    public static final Block FLUID_CELL_BLOCK = null;
    @ObjectHolder(ID_FLUID_CELL)
    public static final TileEntityType<FluidCellTile> FLUID_CELL_TILE = null;
    @ObjectHolder(ID_FLUID_CELL)
    public static final ContainerType<FluidCellContainer> FLUID_CELL_CONTAINER = null;

    @ObjectHolder(ID_ITEM_CELL)
    public static final Block ITEM_CELL_BLOCK = null;
    @ObjectHolder(ID_ITEM_CELL)
    public static final TileEntityType<ItemCellTile> ITEM_CELL_TILE = null;
    @ObjectHolder(ID_ITEM_CELL)
    public static final ContainerType<ItemCellContainer> ITEM_CELL_CONTAINER = null;

    @ObjectHolder(ID_SATCHEL)
    public static final ContainerType<SatchelContainer> SATCHEL_CONTAINER = null;
    // endregion

    // region MISC TILES
    @ObjectHolder(ID_CHARGE_BENCH)
    public static final Block CHARGE_BENCH_BLOCK = null;
    @ObjectHolder(ID_CHARGE_BENCH)
    public static final TileEntityType<ChargeBenchTile> CHARGE_BENCH_TILE = null;
    @ObjectHolder(ID_CHARGE_BENCH)
    public static final ContainerType<ChargeBenchContainer> CHARGE_BENCH_CONTAINER = null;

    @ObjectHolder(ID_PROJECT_BENCH)
    public static final Block PROJECT_BENCH_BLOCK = null;
    @ObjectHolder(ID_PROJECT_BENCH)
    public static final TileEntityType<ProjectBenchTile> PROJECT_BENCH_TILE = null;
    @ObjectHolder(ID_PROJECT_BENCH)
    public static final ContainerType<ProjectBenchContainer> PROJECT_BENCH_CONTAINER = null;

    @ObjectHolder(ID_TINKER_BENCH)
    public static final Block TINKER_BENCH_BLOCK = null;
    @ObjectHolder(ID_TINKER_BENCH)
    public static final TileEntityType<TinkerBenchTile> TINKER_BENCH_TILE = null;
    @ObjectHolder(ID_TINKER_BENCH)
    public static final ContainerType<TinkerBenchContainer> TINKER_BENCH_CONTAINER = null;

    @ObjectHolder(ID_CHUNK_LOADER)
    public static final Block CHUNK_LOADER_BLOCK = null;
    @ObjectHolder(ID_CHUNK_LOADER)
    public static final TileEntityType<DeviceChunkLoaderTile> CHUNK_LOADER_TILE = null;
    @ObjectHolder(ID_CHUNK_LOADER)
    public static final ContainerType<ChunkLoaderContainer> CHUNK_LOADER_CONTAINER = null;
    // endregion
}
