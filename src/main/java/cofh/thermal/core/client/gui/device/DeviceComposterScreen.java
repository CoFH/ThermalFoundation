package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DeviceComposterContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.PROG_ARROW_RIGHT;
import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DeviceComposterScreen extends ThermalTileScreenBase<DeviceComposterContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/composter.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceComposterScreen(DeviceComposterContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_composter"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_composter");
    }

    @Override
    public void init() {

        super.init();

        addElement(ThermalGuiHelper.createDefaultProgress(this, 87, 35, PROG_ARROW_RIGHT, tile));
    }

}
