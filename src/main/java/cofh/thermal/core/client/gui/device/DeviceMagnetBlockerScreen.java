package cofh.thermal.core.client.gui.device;

import cofh.thermal.core.inventory.container.device.DeviceMagnetBlockerContainer;
import cofh.thermal.lib.client.gui.AugmentableScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.StringHelper.format;
import static cofh.lib.util.helpers.StringHelper.localize;

public class DeviceMagnetBlockerScreen extends AugmentableScreen<DeviceMagnetBlockerContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/magnet_blocker.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceMagnetBlockerScreen(DeviceMagnetBlockerContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, titleIn);
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_magnet_blocker");
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {

        String radius = format(1 + 2L * menu.tile.getRadius());
        // String radiusV = format(1 + menu.tile.getRadius());

        getFontRenderer().draw(matrixStack, localize("info.cofh.area") + ": " + radius + " x " + radius, 70, 39, 0x404040);

        super.renderLabels(matrixStack, mouseX, mouseY);
    }

}
