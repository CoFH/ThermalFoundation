package cofh.thermal.core.client.gui.device;

import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DeviceTreeExtractorContainer;
import cofh.thermal.lib.client.gui.AugmentableScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class DeviceTreeExtractorScreen extends AugmentableScreen<DeviceTreeExtractorContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/tree_extractor.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceTreeExtractorScreen(DeviceTreeExtractorContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, titleIn);
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_tree_extractor");
    }

    @Override
    public void init() {

        super.init();

        addElement(setClearable(createMediumFluidStorage(this, 116, 22, tile.getTank(0)), tile, 0));
        addElement(ThermalGuiHelper.createDefaultDuration(this, 80, 35, SCALE_FLAME_GREEN, tile));
    }

}
