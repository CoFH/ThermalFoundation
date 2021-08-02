package cofh.thermal.core.client.gui.device;

import cofh.core.client.gui.element.ElementScaled;
import cofh.core.util.helpers.GuiHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DeviceSoilInfuserContainer;
import cofh.thermal.core.tileentity.device.DeviceSoilInfuserTile;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.helpers.StringHelper.format;
import static cofh.lib.util.helpers.StringHelper.localize;

public class DeviceSoilInfuserScreen extends ThermalTileScreenBase<DeviceSoilInfuserContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/soil_infuser.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    protected DeviceSoilInfuserTile tile;

    public DeviceSoilInfuserScreen(DeviceSoilInfuserContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_soil_infuser"));
        tile = container.tile;
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_soil_infuser");
    }

    @Override
    public void init() {

        super.init();

        if (tile.getEnergyStorage() != null && tile.getEnergyStorage().getMaxEnergyStored() > 0) {
            addPanel(ThermalGuiHelper.createDefaultEnergyUserPanel(this, tile));
            addElement(GuiHelper.setClearable(createDefaultEnergyStorage(this, 8, 8, tile.getEnergyStorage()), tile, 0));
        }
        addElement(new ElementScaled(this, 44, 34)
                .setQuantity(() -> tile.getScaledProgress(16))
                .setSize(16, 16)
                .setTexture(SCALE_FLAME_GREEN, 32, 16));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {

        String radius = format(1 + 2 * tile.getRadius());

        getFontRenderer().drawString(matrixStack, localize("info.cofh.area") + ": " + radius + " x " + radius, 70, 39, 0x404040);

        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
    }

}
