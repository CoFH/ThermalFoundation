package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DeviceTreeExtractorContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DeviceTreeExtractorScreen extends ThermalTileScreenBase<DeviceTreeExtractorContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/tree_extractor.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceTreeExtractorScreen(DeviceTreeExtractorContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_tree_extractor"));
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
