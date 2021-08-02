package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DevicePotionDiffuserContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DevicePotionDiffuserScreen extends ThermalTileScreenBase<DevicePotionDiffuserContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/potion_diffuser.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DevicePotionDiffuserScreen(DevicePotionDiffuserContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_potion_diffuser"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_potion_diffuser");
    }

    @Override
    public void init() {

        super.init();

        addElement(setClearable(createMediumFluidStorage(this, 116, 22, tile.getTank(0)), tile, 0));
        addElement(ThermalGuiHelper.createDefaultDuration(this, 80, 35, SCALE_ALCHEMY, tile));
    }

}
