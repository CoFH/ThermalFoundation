package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.device.DeviceWaterGenContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.createMediumFluidStorage;
import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DeviceWaterGenScreen extends ThermalTileScreenBase<DeviceWaterGenContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/water_gen.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceWaterGenScreen(DeviceWaterGenContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_water_gen"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_water_gen");
    }

    @Override
    public void init() {

        super.init();

        addElement(createMediumFluidStorage(this, 116, 22, tile.getTank(0)));
    }

}
