package cofh.thermal.core;

import cofh.core.capability.CapabilityRedstoneFlux;
import cofh.core.client.renderer.entity.TNTMinecartRendererCoFH;
import cofh.core.config.ConfigManager;
import cofh.core.config.world.OreConfig;
import cofh.core.content.entity.AbstractGrenade;
import cofh.core.content.entity.AbstractTNTMinecart;
import cofh.lib.client.renderer.entity.TntRendererCoFH;
import cofh.lib.content.entity.PrimedTntCoFH;
import cofh.lib.util.DeferredRegisterCoFH;
import cofh.thermal.core.client.gui.ChargeBenchScreen;
import cofh.thermal.core.client.gui.TinkerBenchScreen;
import cofh.thermal.core.client.gui.device.*;
import cofh.thermal.core.client.gui.storage.EnergyCellScreen;
import cofh.thermal.core.client.gui.storage.FluidCellScreen;
import cofh.thermal.core.client.gui.storage.SatchelScreen;
import cofh.thermal.core.client.renderer.entity.*;
import cofh.thermal.core.client.renderer.entity.model.BasalzModel;
import cofh.thermal.core.client.renderer.entity.model.BlitzModel;
import cofh.thermal.core.client.renderer.entity.model.BlizzModel;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.config.ThermalClientConfig;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.config.ThermalDeviceConfig;
import cofh.thermal.core.config.ThermalWorldConfig;
import cofh.thermal.core.entity.explosive.DetonateUtils;
import cofh.thermal.core.entity.monster.Basalz;
import cofh.thermal.core.entity.monster.Blitz;
import cofh.thermal.core.entity.monster.Blizz;
import cofh.thermal.core.init.*;
import cofh.thermal.lib.common.ThermalFlags;
import cofh.thermal.lib.common.ThermalProxy;
import cofh.thermal.lib.common.ThermalProxyClient;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_COFH_CORE;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.TCoreContainers.*;
import static cofh.thermal.core.init.TCoreEntities.*;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

@Mod (ID_THERMAL)
public class ThermalCore {

    public static final Logger LOG = LogManager.getLogger(ID_THERMAL);
    public static final ThermalProxy PROXY = DistExecutor.unsafeRunForDist(() -> ThermalProxyClient::new, () -> ThermalProxy::new);
    public static final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Fluid> FLUIDS = DeferredRegisterCoFH.create(ForgeRegistries.FLUIDS, ID_THERMAL);

    public static final DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.CONTAINERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<EntityType<?>> ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.ENTITIES, ID_THERMAL);
    public static final DeferredRegisterCoFH<GlobalLootModifierSerializer<?>> LOOT_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<RecipeType<?>> RECIPE_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_TYPES, ID_THERMAL);
    public static final DeferredRegisterCoFH<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<SoundEvent> SOUND_EVENTS = DeferredRegisterCoFH.create(ForgeRegistries.SOUND_EVENTS, ID_THERMAL);
    public static final DeferredRegisterCoFH<BlockEntityType<?>> TILE_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.BLOCK_ENTITIES, ID_THERMAL);

    public static final DeferredRegisterCoFH<FluidType> FLUID_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.Keys.FLUID_TYPES, ID_COFH_CORE);

    static {
        TCoreBlocks.register();
        TCoreItems.register();
        TCoreFluids.register();

        TCoreContainers.register();
        TCoreEntities.register();
        TCoreRecipeSerializers.register();
        TCoreRecipeTypes.register();
        TCoreSounds.register();
        TCoreTileEntities.register();

        TCoreRecipeManagers.register();
    }

    public ThermalCore() {

        setFeatureFlags();
        addWorldConfigs();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CONFIG_MANAGER.register(modEventBus)
                .addClientConfig(new ThermalClientConfig())
                .addServerConfig(new ThermalCoreConfig())
                .addServerConfig(new ThermalDeviceConfig())
                .addCommonConfig(new ThermalWorldConfig());

        modEventBus.addListener(this::entityAttributeSetup);
        modEventBus.addListener(this::entityLayerSetup);
        modEventBus.addListener(this::entityRendererSetup);
        modEventBus.addListener(this::capSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerLootData);
        modEventBus.addListener(this::registrySetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        FLUIDS.register(modEventBus);

        CONTAINERS.register(modEventBus);
        ENTITIES.register(modEventBus);
        LOOT_SERIALIZERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
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

    private void addWorldConfigs() {

        List<ResourceKey<Level>> defaultDimensions = Collections.singletonList(Level.OVERWORLD);

        ThermalWorldConfig.addOreConfig("niter_ore", new OreConfig("Niter", 2, -16, 64, 7, defaultDimensions, getFlag(FLAG_RESOURCE_NITER)));
        ThermalWorldConfig.addOreConfig("sulfur_ore", new OreConfig("Sulfur", 2, -16, 32, 7, defaultDimensions, getFlag(FLAG_RESOURCE_NITER)));

        ThermalWorldConfig.addOreConfig("tin_ore", new OreConfig("Tin", 6, -20, 60, 9, defaultDimensions, getFlag(FLAG_RESOURCE_TIN)));
        ThermalWorldConfig.addOreConfig("lead_ore", new OreConfig("Lead", 6, -60, 40, 8, defaultDimensions, getFlag(FLAG_RESOURCE_LEAD)));
        ThermalWorldConfig.addOreConfig("silver_ore", new OreConfig("Silver", 4, -60, 40, 8, defaultDimensions, getFlag(FLAG_RESOURCE_SILVER)));
        ThermalWorldConfig.addOreConfig("nickel_ore", new OreConfig("Nickel", 4, -40, 120, 8, defaultDimensions, getFlag(FLAG_RESOURCE_NICKEL)));

        ThermalWorldConfig.addOreConfig("apatite_ore", new OreConfig("Apatite", 4, -16, 96, 9, defaultDimensions, getFlag(FLAG_RESOURCE_APATITE)));

        ThermalWorldConfig.addOreConfig("cinnabar_ore", new OreConfig("Cinnabar", 1, -16, 48, 5, defaultDimensions, getFlag(FLAG_RESOURCE_CINNABAR)));
        ThermalWorldConfig.addOreConfig("oil_sand", new OreConfig("Oil Sand", 2, 40, 80, 24, defaultDimensions, getFlag(FLAG_RESOURCE_OIL)));
    }

    // region INITIALIZATION
    private void registerLootData(final RegisterEvent event) {

        if (event.getRegistryKey() == ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS) {
            ThermalFlags.manager().setup();
        }
    }

    private void entityAttributeSetup(final EntityAttributeCreationEvent event) {

        event.put(BASALZ.get(), Basalz.registerAttributes().build());
        event.put(BLITZ.get(), Blitz.registerAttributes().build());
        event.put(BLIZZ.get(), Blizz.registerAttributes().build());
    }

    private void entityLayerSetup(final EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(BasalzModel.BASALZ_LAYER, BasalzModel::createBodyLayer);
        event.registerLayerDefinition(BlitzModel.BLITZ_LAYER, BlitzModel::createBodyLayer);
        event.registerLayerDefinition(BlizzModel.BLIZZ_LAYER, BlizzModel::createBodyLayer);

        event.registerLayerDefinition(ElementalProjectileModel.PROJECTILE_LAYER, ElementalProjectileModel::createBodyLayer);
    }

    private void entityRendererSetup(final EntityRenderersEvent.RegisterRenderers event) {

        for (RegistryObject<EntityType<? extends AbstractGrenade>> grenade : DetonateUtils.GRENADES) {
            event.registerEntityRenderer(grenade.get(), ThrownItemRenderer::new);
        }
        for (RegistryObject<EntityType<? extends PrimedTntCoFH>> tnt : DetonateUtils.TNT) {
            event.registerEntityRenderer(tnt.get(), TntRendererCoFH::new);
        }
        for (RegistryObject<EntityType<? extends AbstractTNTMinecart>> cart : DetonateUtils.CARTS) {
            event.registerEntityRenderer(cart.get(), TNTMinecartRendererCoFH::new);
        }
        event.registerEntityRenderer(BASALZ.get(), BasalzRenderer::new);
        event.registerEntityRenderer(BLITZ.get(), BlitzRenderer::new);
        event.registerEntityRenderer(BLIZZ.get(), BlizzRenderer::new);

        event.registerEntityRenderer(BASALZ_PROJECTILE.get(), BasalzProjectileRenderer::new);
        event.registerEntityRenderer(BLITZ_PROJECTILE.get(), BlitzProjectileRenderer::new);
        event.registerEntityRenderer(BLIZZ_PROJECTILE.get(), BlizzProjectileRenderer::new);
    }

    private void capSetup(RegisterCapabilitiesEvent event) {

        CapabilityRedstoneFlux.register(event);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(TCoreBlocks::setup);
        event.enqueueWork(TCoreItems::setup);
        event.enqueueWork(TCoreEntities::setup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        event.enqueueWork(this::registerGuiFactories);
        event.enqueueWork(this::registerRenderLayers);
    }

    private void registrySetup(final NewRegistryEvent event) {

        CONFIG_MANAGER.setupClient();
        CONFIG_MANAGER.setupServer();
        CONFIG_MANAGER.setupCommon();
    }
    // endregion

    // region HELPERS
    private void registerGuiFactories() {

        MenuScreens.register(DEVICE_HIVE_EXTRACTOR_CONTAINER.get(), DeviceHiveExtractorScreen::new);
        MenuScreens.register(DEVICE_TREE_EXTRACTOR_CONTAINER.get(), DeviceTreeExtractorScreen::new);
        MenuScreens.register(DEVICE_FISHER_CONTAINER.get(), DeviceFisherScreen::new);
        MenuScreens.register(DEVICE_COMPOSTER_CONTAINER.get(), DeviceComposterScreen::new);
        MenuScreens.register(DEVICE_SOIL_INFUSER_CONTAINER.get(), DeviceSoilInfuserScreen::new);
        MenuScreens.register(DEVICE_WATER_GEN_CONTAINER.get(), DeviceWaterGenScreen::new);
        MenuScreens.register(DEVICE_ROCK_GEN_CONTAINER.get(), DeviceRockGenScreen::new);
        MenuScreens.register(DEVICE_COLLECTOR_CONTAINER.get(), DeviceCollectorScreen::new);
        MenuScreens.register(DEVICE_POTION_DIFFUSER_CONTAINER.get(), DevicePotionDiffuserScreen::new);
        MenuScreens.register(DEVICE_NULLIFIER_CONTAINER.get(), DeviceNullifierScreen::new);
        MenuScreens.register(TINKER_BENCH_CONTAINER.get(), TinkerBenchScreen::new);
        MenuScreens.register(CHARGE_BENCH_CONTAINER.get(), ChargeBenchScreen::new);
        MenuScreens.register(SATCHEL_CONTAINER.get(), SatchelScreen::new);
        MenuScreens.register(ENERGY_CELL_CONTAINER.get(), EnergyCellScreen::new);
        MenuScreens.register(FLUID_CELL_CONTAINER.get(), FluidCellScreen::new);

        // MenuScreens.register(ITEM_CELL_CONTAINER, ItemCellScreen::new);
    }

    private void registerRenderLayers() {

        RenderType cutout = RenderType.cutout();

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_OBSIDIAN_GLASS), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_SIGNALUM_GLASS), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_LUMIUM_GLASS), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_ENDERIUM_GLASS), cutout);

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_MACHINE_FRAME), cutout);

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_ENERGY_CELL_FRAME), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_ENERGY_CELL), cutout);

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_FLUID_CELL_FRAME), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_FLUID_CELL), cutout);

        //        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_ITEM_CELL_FRAME), cutout);
        //        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_ITEM_CELL), cutout);

        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_COMPOSTER), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_WATER_GEN), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_ROCK_GEN), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_COLLECTOR), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_POTION_DIFFUSER), cutout);
        ItemBlockRenderTypes.setRenderLayer(BLOCKS.get(ID_DEVICE_NULLIFIER), cutout);
    }
    // endregion
}
