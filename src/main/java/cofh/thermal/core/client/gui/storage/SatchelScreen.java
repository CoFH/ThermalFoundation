package cofh.thermal.core.client.gui.storage;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.panel.SecurityPanel;
import cofh.core.util.helpers.RenderHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import static cofh.core.util.helpers.GuiHelper.createSlot;
import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.Constants.PATH_ELEMENTS;
import static cofh.lib.util.Constants.PATH_GUI;

public class SatchelScreen extends ContainerScreenCoFH<SatchelContainer> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(PATH_GUI + "generic.png");
    public static final ResourceLocation TEXTURE_EXT = new ResourceLocation(PATH_GUI + "generic_extension.png");
    public static final ResourceLocation SLOT_OVERLAY = new ResourceLocation(PATH_ELEMENTS + "locked_overlay_slot.png");

    protected int renderExtension;

    public SatchelScreen(SatchelContainer container, Inventory inv, Component titleIn) {

        super(container, inv, titleIn);

        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.satchel");

        renderExtension = container.getExtraRows() * 18;
        imageHeight += renderExtension;
    }

    @Override
    public void init() {

        super.init();

        for (int i = 0; i < menu.getContainerInventorySize(); ++i) {
            Slot slot = menu.slots.get(i);
            addElement(createSlot(this, slot.x, slot.y));
        }
        addPanel(new SecurityPanel(this, menu, SecurityHelper.getID(player)));
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {

        RenderHelper.resetShaderColor();
        RenderHelper.setPosTexShader();
        RenderHelper.setShaderTexture0(texture);

        drawTexturedModalRect(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        if (renderExtension > 0) {
            RenderHelper.setShaderTexture0(TEXTURE_EXT);
            drawTexturedModalRect(poseStack, leftPos, topPos + renderExtension, 0, 0, imageWidth, imageHeight);
        }
        poseStack.pushPose();
        poseStack.translate(leftPos, topPos, 0.0F);

        drawPanels(poseStack, false);
        drawElements(poseStack, false);

        poseStack.popPose();
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {

        super.renderLabels(poseStack, mouseX, mouseY);

        GlStateManager._enableBlend();
        RenderHelper.setPosTexShader();
        RenderHelper.setShaderTexture0(SLOT_OVERLAY);
        drawTexturedModalRect(poseStack, menu.lockedSlot.x, menu.lockedSlot.y, 0, 0, 16, 16, 16, 16);
        GlStateManager._disableBlend();
    }

}
