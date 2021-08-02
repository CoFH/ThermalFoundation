package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.device.DeviceCollectorContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DeviceCollectorScreen extends ThermalTileScreenBase<DeviceCollectorContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/collector.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceCollectorScreen(DeviceCollectorContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_collector"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_collector");
    }

}
