package cofh.thermal.lib.client.gui;

import cofh.core.client.gui.element.ElementEnergyStorage;
import cofh.lib.inventory.container.ContainerCoFH;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.lib.tileentity.DynamoTileBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

import static cofh.lib.util.constants.Constants.PATH_ELEMENTS;
import static cofh.lib.util.helpers.StringHelper.DF0;

public class DynamoScreenBase<T extends ContainerCoFH> extends ThermalTileScreenBase<T> {

    protected DynamoTileBase tile;

    public DynamoScreenBase(T container, PlayerInventory inv, DynamoTileBase tile, ITextComponent titleIn) {

        super(container, inv, tile, titleIn);
        this.tile = tile;
    }

    @Override
    public void init() {

        super.init();

        addPanel(ThermalGuiHelper.createDefaultEnergyProducerPanel(this, tile));

        if (tile.getEnergyStorage() != null && tile.getEnergyStorage().getMaxEnergyStored() > 0) {
            ElementEnergyStorage throttle = (ElementEnergyStorage) new ElementEnergyStorage(this, 125, 22, tile.getEnergyStorage()) {

                @Override
                public void addTooltip(List<ITextComponent> tooltipList, int mouseX, int mouseY) {

                    tooltipList.add(new TranslationTextComponent("info.cofh.output")
                            .append(new StringTextComponent(": " + DF0.format(100 * (double) tile.getCurSpeed() / tile.getMaxSpeed()) + "%")));
                }
            }
                    .setSize(16, 42)
                    .setTexture(PATH_ELEMENTS + "storage_energy.png", 32, 64);
            addElement(throttle);
        }
    }

}
