package cofh.thermal.core;

import cofh.core.init.CoreEnchantments;
import cofh.lib.capability.CapabilityRedstoneFlux;
import cofh.lib.client.renderer.entity.SpriteRendererCoFH;
import cofh.lib.client.renderer.entity.TNTRendererCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import cofh.thermal.core.client.gui.ChargeBenchScreen;
import cofh.thermal.core.client.gui.TinkerBenchScreen;
import cofh.thermal.core.client.gui.device.*;
import cofh.thermal.core.client.gui.storage.EnergyCellScreen;
import cofh.thermal.core.client.gui.storage.FluidCellScreen;
import cofh.thermal.core.client.gui.storage.SatchelScreen;
import cofh.thermal.core.client.renderer.entity.*;
import cofh.thermal.core.entity.monster.BasalzEntity;
import cofh.thermal.core.init.*;
import cofh.thermal.core.world.gen.feature.ThermalFeatures;
import cofh.thermal.lib.common.ThermalConfig;
import cofh.thermal.lib.common.ThermalProxy;
import cofh.thermal.lib.common.ThermalProxyClient;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

@Mod(ID_THERMAL)
public class ThermalCore {

    public static final Logger LOG = LogManager.getLogger(ID_THERMAL);
    public static final ThermalProxy PROXY = DistExecutor.unsafeRunForDist(() -> ThermalProxyClient::new, () -> ThermalProxy::new);

    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Fluid> FLUIDS = DeferredRegisterCoFH.create(ForgeRegistries.FLUIDS, ID_THERMAL);

    public static final DeferredRegisterCoFH<ContainerType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.CONTAINERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<EntityType<?>> ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.ENTITIES, ID_THERMAL).preventDataFixers(true);
    public static final DeferredRegisterCoFH<GlobalLootModifierSerializer<?>> LOOT_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<SoundEvent> SOUND_EVENTS = DeferredRegisterCoFH.create(ForgeRegistries.SOUND_EVENTS, ID_THERMAL);
    public static final DeferredRegisterCoFH<TileEntityType<?>> TILE_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.TILE_ENTITIES, ID_THERMAL);

    static {
        TCoreBlocks.register();
        TCoreItems.register();
        TCoreFluids.register();

        TCoreContainers.register();
        TCoreEntities.register();
        TCoreSounds.register();

        TCoreRecipeManagers.register();
        TCoreRecipeSerializers.register();
        TCoreRecipeTypes.register();
    }

    public ThermalCore() {

        setFeatureFlags();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::entitySetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        FLUIDS.register(modEventBus);

        CONTAINERS.register(modEventBus);
        ENTITIES.register(modEventBus);
        LOOT_SERIALIZERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);

        ThermalConfig.register();

        CoreEnchantments.registerHoldingEnchantment();
    }

    private void setFeatureFlags() {

        setFlag(FLAG_AREA_AUGMENTS, true);
        setFlag(FLAG_FILTER_AUGMENTS, true);
        setFlag(FLAG_STORAGE_AUGMENTS, true);
        setFlag(FLAG_UPGRADE_AUGMENTS, true);

        setFlag(FLAG_CREATIVE_AUGMENTS, true);

        setFlag(ID_TINKER_BENCH, true);

        // setFlag(ID_CHUNK_LOADER, true);
    }

    // region INITIALIZATION
    private void entitySetup(final EntityAttributeCreationEvent event) {

        event.put(BASALZ_ENTITY, BasalzEntity.registerAttributes().build());
        event.put(BLITZ_ENTITY, BasalzEntity.registerAttributes().build());
        event.put(BLIZZ_ENTITY, BasalzEntity.registerAttributes().build());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        CapabilityRedstoneFlux.register();

        // DO NOT ENQUEUE
        ThermalConfig.setup();

        event.enqueueWork(TCoreBlocks::setup);
        event.enqueueWork(TCoreItems::setup);
        event.enqueueWork(TCoreEntities::setup);
        event.enqueueWork(ThermalFeatures::setup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        registerGuiFactories();
        registerRenderLayers();
        registerEntityRenderingHandlers();
        registerTileEntityRenderers();

        // ThermalItemGroups.setup();
    }
    // endregion

    // region HELPERS
    private void registerGuiFactories() {

        ScreenManager.register(DEVICE_HIVE_EXTRACTOR_CONTAINER, DeviceHiveExtractorScreen::new);
        ScreenManager.register(DEVICE_TREE_EXTRACTOR_CONTAINER, DeviceTreeExtractorScreen::new);
        ScreenManager.register(DEVICE_SOIL_INFUSER_CONTAINER, DeviceSoilInfuserScreen::new);
        ScreenManager.register(DEVICE_FISHER_CONTAINER, DeviceFisherScreen::new);
        ScreenManager.register(DEVICE_WATER_GEN_CONTAINER, DeviceWaterGenScreen::new);
        ScreenManager.register(DEVICE_ROCK_GEN_CONTAINER, DeviceRockGenScreen::new);
        ScreenManager.register(DEVICE_COLLECTOR_CONTAINER, DeviceCollectorScreen::new);
        ScreenManager.register(DEVICE_POTION_DIFFUSER_CONTAINER, DevicePotionDiffuserScreen::new);
        ScreenManager.register(DEVICE_NULLIFIER_CONTAINER, DeviceNullifierScreen::new);

        // ScreenManager.register(CHUNK_LOADER_CONTAINER, ChunkLoaderScreen::new);

        ScreenManager.register(TINKER_BENCH_CONTAINER, TinkerBenchScreen::new);
        ScreenManager.register(CHARGE_BENCH_CONTAINER, ChargeBenchScreen::new);

        ScreenManager.register(SATCHEL_CONTAINER, SatchelScreen::new);

        ScreenManager.register(ENERGY_CELL_CONTAINER, EnergyCellScreen::new);
        ScreenManager.register(FLUID_CELL_CONTAINER, FluidCellScreen::new);
        // ScreenManager.register(ITEM_CELL_CONTAINER, ItemCellScreen::new);
    }

    private void registerRenderLayers() {

        RenderType cutout = RenderType.cutout();

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_OBSIDIAN_GLASS), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_SIGNALUM_GLASS), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_LUMIUM_GLASS), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_ENDERIUM_GLASS), cutout);

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_MACHINE_FRAME), cutout);

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_ENERGY_CELL_FRAME), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_ENERGY_CELL), cutout);

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_FLUID_CELL_FRAME), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_FLUID_CELL), cutout);

        //        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_ITEM_CELL_FRAME), cutout);
        //        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_ITEM_CELL), cutout);

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_WATER_GEN), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_ROCK_GEN), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_COLLECTOR), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_POTION_DIFFUSER), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_NULLIFIER), cutout);
    }

    private void registerEntityRenderingHandlers() {

        RenderingRegistry.registerEntityRenderingHandler(BASALZ_ENTITY, BasalzRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLITZ_ENTITY, BlitzRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLIZZ_ENTITY, BlizzRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(BASALZ_PROJECTILE_ENTITY, BasalzProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLITZ_PROJECTILE_ENTITY, BlitzProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLIZZ_PROJECTILE_ENTITY, BlizzProjectileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EXPLOSIVE_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(SLIME_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(REDSTONE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(GLOWSTONE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(ENDER_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(PHYTO_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(FIRE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(EARTH_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(ICE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(LIGHTNING_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(NUKE_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(SLIME_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(REDSTONE_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(GLOWSTONE_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(ENDER_TNT_ENTITY, TNTRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(PHYTO_TNT_ENTITY, TNTRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(FIRE_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(EARTH_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(ICE_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(LIGHTNING_TNT_ENTITY, TNTRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(NUKE_TNT_ENTITY, TNTRendererCoFH::new);
    }

    private void registerTileEntityRenderers() {

        // ClientRegistry.bindTileEntityRenderer(ITEM_CELL_TILE, ItemCellRenderer::new);
    }
    // endregion
}
