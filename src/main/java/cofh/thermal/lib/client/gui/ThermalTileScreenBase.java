package cofh.thermal.lib.client.gui;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.ElementTexture;
import cofh.core.client.gui.element.ElementXpStorage;
import cofh.core.client.gui.element.panel.AugmentPanel;
import cofh.core.client.gui.element.panel.RSControlPanel;
import cofh.core.client.gui.element.panel.SecurityPanel;
import cofh.core.network.packet.server.FilterGuiOpenPacket;
import cofh.lib.inventory.container.ContainerCoFH;
import cofh.lib.util.helpers.FilterHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.lib.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;

import static cofh.core.util.helpers.GuiHelper.*;

public class ThermalTileScreenBase<T extends ContainerCoFH> extends ContainerScreenCoFH<T> {

    protected ThermalTileBase tile;

    public ThermalTileScreenBase(T container, PlayerInventory inv, ThermalTileBase tile, ITextComponent titleIn) {

        super(container, inv, titleIn);
        this.tile = tile;
    }

    @Override
    public void init() {

        super.init();

        // TODO: Enchantment Panel
        // addPanel(new PanelEnchantment(this, "This block can be enchanted."));
        addPanel(new SecurityPanel(this, tile, SecurityHelper.getID(player)));

        if (container.getAugmentSlots().size() > 0) {
            addPanel(new AugmentPanel(this, container::getNumAugmentSlots, container.getAugmentSlots()));
        }
        addPanel(new RSControlPanel(this, tile));

        if (tile.getXpStorage() != null) {
            addElement(setClaimable((ElementXpStorage) createDefaultXpStorage(this, 152, 65, tile.getXpStorage()).setVisible(() -> tile.getXpStorage().getMaxXpStored() > 0), tile));
        }

        // Filter Tab
        addElement(new ElementTexture(this, 4, -21)
                .setUV(24, 0)
                .setSize(24, 21)
                .setTexture(TAB_TOP, 48, 32)
                .setVisible(() -> FilterHelper.hasFilter(tile)));
        addElement(new ElementTexture(this, 8, -17) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                FilterGuiOpenPacket.openFilterGui(tile);
                return true;
            }
        }
                .setSize(16, 16)
                .setTexture(NAV_FILTER, 16, 16)
                .setTooltipFactory((element, mouseX, mouseY) -> Collections.singletonList(tile.getFilter().getDisplayName()))
                .setVisible(() -> FilterHelper.hasFilter(tile)));

        // TODO: Revisit ItemStack-based
        //        addElement(new ElementItemStack(this, 8, -17) {
        //
        //            @Override
        //            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        //
        //                FilterGuiOpenPacket.openFilterGui(tile);
        //                return true;
        //            }
        //        }
        //                .setItem(() -> new ItemStack(Items.WHEAT))
        //                .setSize(16, 16)
        //                .setVisible(() -> FilterHelper.hasFilter(tile)));
    }

}
