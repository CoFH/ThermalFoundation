package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.device.DeviceMagnetBlockerContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class DeviceMagnetBlockerScreen extends ThermalTileScreenBase<DeviceMagnetBlockerContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/magnet_blocker.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceMagnetBlockerScreen(DeviceMagnetBlockerContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_magnet_blocker"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_magnet_blocker");
    }

}
