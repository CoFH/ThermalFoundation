//package cofh.thermal.core.client.gui.storage;
//
//import cofh.core.client.gui.element.ElementBase;
//import cofh.core.client.gui.element.ElementButton;
//import cofh.core.client.gui.element.ElementItem;
//import cofh.core.client.gui.element.ElementTexture;
//import cofh.core.network.packet.server.TileConfigPacket;
//import cofh.lib.util.helpers.StringHelper;
//import cofh.thermal.core.inventory.container.storage.ItemCellContainer;
//import cofh.thermal.core.tileentity.storage.ItemCellTile;
//import cofh.thermal.lib.client.gui.CellScreenReconfigurable;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.StringTextComponent;
//
//import java.util.Collections;
//
//import static cofh.core.util.helpers.GuiHelper.*;
//import static cofh.lib.util.constants.Constants.ID_COFH_CORE;
//import static cofh.lib.util.constants.Constants.ID_THERMAL;
//import static cofh.lib.util.helpers.SoundHelper.playClickSound;
//import static cofh.lib.util.helpers.StringHelper.format;
//import static cofh.lib.util.helpers.StringHelper.localize;
//
//public class ItemCellScreen extends CellScreenReconfigurable<ItemCellContainer> {
//
//    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/item_cell.png";
//    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);
//
//    public static final String TEX_INCREMENT = ID_COFH_CORE + ":textures/gui/elements/button_increment.png";
//    public static final String TEX_DECREMENT = ID_COFH_CORE + ":textures/gui/elements/button_decrement.png";
//
//    protected ItemCellTile tile;
//
//    public ItemCellScreen(ItemCellContainer container, Inventory inv, Component titleIn) {
//
//        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.item_cell"));
//        tile = container.tile;
//        texture = TEXTURE;
//        info = generatePanelInfo("info.thermal.item_cell");
//        name = "item_cell";
//    }
//
//    @Override
//    public void init() {
//
//        super.init();
//
//        addElement(new ElementTexture(this, 24, 16)
//                .setSize(20, 20)
//                .setTexture(INFO_INPUT, 20, 20));
//        addElement(new ElementTexture(this, 132, 16)
//                .setSize(20, 20)
//                .setTexture(INFO_OUTPUT, 20, 20));
//
//        addElement(new ElementItem(this, 80, 16).setItem(() -> tile.getRenderItem()));
//        addElement(setClearable(createDefaultItemStorage(this, 80, 36, tile.getSlot(0)), tile, 0));
//
//        addButtons();
//    }
//
//    @Override
//    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
//
//        String input = format(tile.amountInput);
//        String output = format(tile.amountOutput);
//
//        getFontRenderer().draw(matrixStack, input, getCenteredOffset(input, 34), 42, 0x404040);
//        getFontRenderer().draw(matrixStack, output, getCenteredOffset(output, 142), 42, 0x404040);
//
//        super.renderLabels(matrixStack, mouseX, mouseY);
//    }
//
//    // region ELEMENTS
//    @Override
//    public boolean handleElementButtonClick(String buttonName, int mouseButton) {
//
//        int change = 4;
//        float pitch = 0.7F;
//
//        if (hasShiftDown()) {
//            change = 64;
//            pitch += 0.1F;
//        }
//        if (hasControlDown()) {
//            change /= 2;
//            pitch -= 0.2F;
//        }
//        if (mouseButton == 1) {
//            change = 1;
//            pitch -= 0.1F;
//        }
//        int curInput = tile.amountInput;
//        int curOutput = tile.amountOutput;
//
//        switch (buttonName) {
//            case "DecInput":
//                tile.amountInput -= change;
//                pitch -= 0.1F;
//                break;
//            case "IncInput":
//                tile.amountInput += change;
//                pitch += 0.1F;
//                break;
//            case "DecOutput":
//                tile.amountOutput -= change;
//                pitch -= 0.1F;
//                break;
//            case "IncOutput":
//                tile.amountOutput += change;
//                pitch += 0.1F;
//                break;
//        }
//        playClickSound(pitch);
//        TileConfigPacket.sendToServer(tile);
//
//        tile.amountInput = curInput;
//        tile.amountOutput = curOutput;
//        return true;
//    }
//
//    protected void addButtons() {
//
//        ElementBase decInput = new ElementButton(this, 19, 56)
//                .setTooltipFactory((element, mouseX, mouseY) -> {
//
//                    if (element.enabled()) {
//                        int change = 4;
//
//                        if (hasShiftDown()) {
//                            change = 64;
//                        }
//                        if (hasControlDown()) {
//                            change /= 2;
//                        }
//                        return Collections.singletonList(new StringTextComponent(
//                                localize("info.cofh.decrease_by")
//                                        + " " + format(change)
//                                        + "/" + format(1)));
//                    }
//                    return Collections.emptyList();
//                })
//                .setName("DecInput")
//                .setSize(14, 14)
//                .setTexture(TEX_DECREMENT, 42, 14)
//                .setEnabled(() -> tile.amountInput > 0);
//
//        ElementBase incInput = new ElementButton(this, 35, 56)
//                .setTooltipFactory((element, mouseX, mouseY) -> {
//
//                    if (element.enabled()) {
//                        int change = 4;
//
//                        if (hasShiftDown()) {
//                            change = 64;
//                        }
//                        if (hasControlDown()) {
//                            change /= 2;
//                        }
//                        return Collections.singletonList(new StringTextComponent(
//                                localize("info.cofh.increase_by")
//                                        + " " + format(change)
//                                        + "/" + format(1)));
//                    }
//                    return Collections.emptyList();
//                })
//                .setName("IncInput")
//                .setSize(14, 14)
//                .setTexture(TEX_INCREMENT, 42, 14)
//                .setEnabled(() -> tile.amountInput < tile.getMaxInput());
//
//        ElementBase decOutput = new ElementButton(this, 127, 56)
//                .setTooltipFactory((element, mouseX, mouseY) -> {
//
//                    if (element.enabled()) {
//                        int change = 4;
//
//                        if (hasShiftDown()) {
//                            change = 64;
//                        }
//                        if (hasControlDown()) {
//                            change /= 2;
//                        }
//                        return Collections.singletonList(new StringTextComponent(
//                                localize("info.cofh.decrease_by")
//                                        + " " + format(change)
//                                        + "/" + format(1)));
//                    }
//                    return Collections.emptyList();
//                })
//                .setName("DecOutput")
//                .setSize(14, 14)
//                .setTexture(TEX_DECREMENT, 42, 14)
//                .setEnabled(() -> tile.amountOutput > 0);
//
//        ElementBase incOutput = new ElementButton(this, 143, 56)
//                .setTooltipFactory((element, mouseX, mouseY) -> {
//
//                    if (element.enabled()) {
//                        int change = 4;
//
//                        if (hasShiftDown()) {
//                            change = 64;
//                        }
//                        if (hasControlDown()) {
//                            change /= 2;
//                        }
//                        return Collections.singletonList(new StringTextComponent(
//                                localize("info.cofh.increase_by")
//                                        + " " + format(change)
//                                        + "/" + format(1)));
//                    }
//                    return Collections.emptyList();
//                })
//                .setName("IncOutput")
//                .setSize(14, 14)
//                .setTexture(TEX_INCREMENT, 42, 14)
//                .setEnabled(() -> tile.amountOutput < tile.getMaxOutput());
//
//        addElement(decInput);
//        addElement(incInput);
//        addElement(decOutput);
//        addElement(incOutput);
//    }
//    // endregion
//}
