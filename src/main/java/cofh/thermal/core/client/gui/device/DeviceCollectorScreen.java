package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.device.DeviceCollectorContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class DeviceCollectorScreen extends ThermalTileScreenBase<DeviceCollectorContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/collector.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceCollectorScreen(DeviceCollectorContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_collector"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_collector");
    }

}
