package cofh.thermal.core.client.gui.device;

import cofh.thermal.core.inventory.container.device.DeviceFisherContainer;
import cofh.thermal.lib.client.gui.AugmentableScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class DeviceFisherScreen extends AugmentableScreen<DeviceFisherContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/fisher.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceFisherScreen(DeviceFisherContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, titleIn);
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_fisher");
    }

}
