package cofh.thermal.core.client;

import cofh.thermal.core.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ID_THERMAL, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ThermalTextures {

    private ThermalTextures() {

    }

    private static final String BLOCK_ATLAS = "minecraft:textures/atlas/blocks.png";

    @SubscribeEvent
    public static void preStitch(TextureStitchEvent.Pre event) {

        if (!event.getMap().getTextureLocation().toString().equals(BLOCK_ATLAS)) {
            return;
        }
        event.addSprite(DEVICE_COLLECTOR_UNDERLAY_LOC);

        event.addSprite(MACHINE_CONFIG_NONE_LOC);
        event.addSprite(MACHINE_CONFIG_INPUT_LOC);
        event.addSprite(MACHINE_CONFIG_OUTPUT_LOC);
        event.addSprite(MACHINE_CONFIG_BOTH_LOC);
        event.addSprite(MACHINE_CONFIG_ACCESSIBLE_LOC);

        event.addSprite(CELL_CONFIG_NONE_LOC);
        event.addSprite(CELL_CONFIG_INPUT_LOC);
        event.addSprite(CELL_CONFIG_OUTPUT_LOC);
        event.addSprite(CELL_CONFIG_BOTH_LOC);

        for (int i = 0; i < 9; ++i) {
            event.addSprite(new ResourceLocation(ID_THERMAL + ":block/cells/energy_cell_level_" + i));
            event.addSprite(new ResourceLocation(ID_THERMAL + ":block/cells/fluid_cell_level_" + i));
        }
        event.addSprite(new ResourceLocation(ID_THERMAL + ":block/cells/energy_cell_level_8c"));
        event.addSprite(new ResourceLocation(ID_THERMAL + ":block/cells/fluid_cell_level_0c"));
        event.addSprite(new ResourceLocation(ID_THERMAL + ":block/cells/fluid_cell_level_8c"));
    }

    @SubscribeEvent
    public static void postStitch(TextureStitchEvent.Post event) {

        if (!event.getMap().getTextureLocation().toString().equals(BLOCK_ATLAS)) {
            return;
        }
        UnderlayBakedModel.clearCache();
        DynamoBakedModel.clearCache();
        ReconfigurableBakedModel.clearCache();
        EnergyCellBakedModel.clearCache();
        FluidCellBakedModel.clearCache();

        AtlasTexture map = event.getMap();
        MACHINE_CONFIG_NONE = map.getSprite(MACHINE_CONFIG_NONE_LOC);
        MACHINE_CONFIG_INPUT = map.getSprite(MACHINE_CONFIG_INPUT_LOC);
        MACHINE_CONFIG_OUTPUT = map.getSprite(MACHINE_CONFIG_OUTPUT_LOC);
        MACHINE_CONFIG_BOTH = map.getSprite(MACHINE_CONFIG_BOTH_LOC);
        MACHINE_CONFIG_ACCESSIBLE = map.getSprite(MACHINE_CONFIG_ACCESSIBLE_LOC);

        CELL_CONFIG_NONE = map.getSprite(CELL_CONFIG_NONE_LOC);
        CELL_CONFIG_INPUT = map.getSprite(CELL_CONFIG_INPUT_LOC);
        CELL_CONFIG_OUTPUT = map.getSprite(CELL_CONFIG_OUTPUT_LOC);
        CELL_CONFIG_BOTH = map.getSprite(CELL_CONFIG_BOTH_LOC);

        ENERGY_CELL_LEVELS = new TextureAtlasSprite[9];
        FLUID_CELL_LEVELS = new TextureAtlasSprite[9];

        for (int i = 0; i < 9; ++i) {
            ENERGY_CELL_LEVELS[i] = map.getSprite(new ResourceLocation(ID_THERMAL + ":block/cells/energy_cell_level_" + i));
            FLUID_CELL_LEVELS[i] = map.getSprite(new ResourceLocation(ID_THERMAL + ":block/cells/fluid_cell_level_" + i));
        }
        ENERGY_CELL_LEVEL_8_C = map.getSprite(new ResourceLocation(ID_THERMAL + ":block/cells/energy_cell_level_8c"));
        FLUID_CELL_LEVEL_0_C = map.getSprite(new ResourceLocation(ID_THERMAL + ":block/cells/fluid_cell_level_0c"));
        FLUID_CELL_LEVEL_8_C = map.getSprite(new ResourceLocation(ID_THERMAL + ":block/cells/fluid_cell_level_8c"));
    }

    public static ResourceLocation DEVICE_COLLECTOR_UNDERLAY_LOC = new ResourceLocation(ID_THERMAL + ":block/devices/device_collector_underlay");

    // region CONFIG
    private static final String CONFIG_ = ID_THERMAL + ":block/config/";

    public static ResourceLocation MACHINE_CONFIG_NONE_LOC = new ResourceLocation(CONFIG_ + "machine_config_none");
    public static ResourceLocation MACHINE_CONFIG_INPUT_LOC = new ResourceLocation(CONFIG_ + "machine_config_input");
    public static ResourceLocation MACHINE_CONFIG_OUTPUT_LOC = new ResourceLocation(CONFIG_ + "machine_config_output");
    public static ResourceLocation MACHINE_CONFIG_BOTH_LOC = new ResourceLocation(CONFIG_ + "machine_config_both");
    public static ResourceLocation MACHINE_CONFIG_ACCESSIBLE_LOC = new ResourceLocation(CONFIG_ + "machine_config_accessible");

    public static ResourceLocation CELL_CONFIG_NONE_LOC = new ResourceLocation(CONFIG_ + "cell_config_none");
    public static ResourceLocation CELL_CONFIG_INPUT_LOC = new ResourceLocation(CONFIG_ + "cell_config_input");
    public static ResourceLocation CELL_CONFIG_OUTPUT_LOC = new ResourceLocation(CONFIG_ + "cell_config_output");
    public static ResourceLocation CELL_CONFIG_BOTH_LOC = new ResourceLocation(CONFIG_ + "cell_config_both");

    public static TextureAtlasSprite MACHINE_CONFIG_NONE;
    public static TextureAtlasSprite MACHINE_CONFIG_INPUT;
    public static TextureAtlasSprite MACHINE_CONFIG_OUTPUT;
    public static TextureAtlasSprite MACHINE_CONFIG_BOTH;
    public static TextureAtlasSprite MACHINE_CONFIG_ACCESSIBLE;

    public static TextureAtlasSprite CELL_CONFIG_NONE;
    public static TextureAtlasSprite CELL_CONFIG_INPUT;
    public static TextureAtlasSprite CELL_CONFIG_OUTPUT;
    public static TextureAtlasSprite CELL_CONFIG_BOTH;

    public static TextureAtlasSprite[] ENERGY_CELL_LEVELS;
    public static TextureAtlasSprite ENERGY_CELL_LEVEL_8_C;

    public static TextureAtlasSprite[] FLUID_CELL_LEVELS;
    public static TextureAtlasSprite FLUID_CELL_LEVEL_0_C;
    public static TextureAtlasSprite FLUID_CELL_LEVEL_8_C;
    // endregion
}
