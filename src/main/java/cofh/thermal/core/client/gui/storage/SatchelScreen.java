package cofh.thermal.core.client.gui.storage;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.panel.SecurityPanel;
import cofh.core.util.helpers.RenderHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.createSlot;
import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_COFH_CORE;

public class SatchelScreen extends ContainerScreenCoFH<SatchelContainer> {

    public static final String TEX_PATH = ID_COFH_CORE + ":textures/gui/generic.png";
    public static final String TEX_PATH_EXT = ID_COFH_CORE + ":textures/gui/generic_extension.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);
    public static final ResourceLocation TEXTURE_EXT = new ResourceLocation(TEX_PATH_EXT);

    protected int renderExtension;

    public SatchelScreen(SatchelContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, titleIn);

        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.satchel");

        renderExtension = container.getExtraRows() * 18;
        ySize += renderExtension;
    }

    @Override
    public void init() {

        super.init();

        for (int i = 0; i < container.getContainerInventorySize(); ++i) {
            Slot slot = container.inventorySlots.get(i);
            addElement(createSlot(this, slot.xPos, slot.yPos));
        }
        addPanel(new SecurityPanel(this, container, SecurityHelper.getID(player)));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        RenderHelper.resetColor();
        RenderHelper.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        if (renderExtension > 0) {
            RenderHelper.bindTexture(TEXTURE_EXT);
            drawTexturedModalRect(guiLeft, guiTop + renderExtension, 0, 0, xSize, ySize);
        }
        RenderSystem.pushMatrix();
        RenderSystem.translatef(guiLeft, guiTop, 0.0F);

        drawPanels(matrixStack, false);
        drawElements(matrixStack, false);

        RenderSystem.popMatrix();
    }

}
