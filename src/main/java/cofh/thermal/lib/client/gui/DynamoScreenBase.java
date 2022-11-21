package cofh.thermal.lib.client.gui;

import cofh.core.client.gui.element.ElementEnergyStorage;
import cofh.core.inventory.container.ContainerCoFH;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.lib.tileentity.DynamoTileBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

import static cofh.lib.util.Constants.PATH_ELEMENTS;
import static cofh.lib.util.helpers.StringHelper.DF0;

public class DynamoScreenBase<T extends ContainerCoFH> extends ThermalTileScreenBase<T> {

    protected DynamoTileBase tile;

    public DynamoScreenBase(T container, Inventory inv, DynamoTileBase tile, Component titleIn) {

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
                public void addTooltip(List<Component> tooltipList, int mouseX, int mouseY) {

                    tooltipList.add(Component.translatable("info.cofh.output")
                            .append(Component.literal(": " + DF0.format(100 * (double) tile.getCurSpeed() / tile.getMaxSpeed()) + "%")));
                }
            }
                    .setSize(16, 42)
                    .setTexture(PATH_ELEMENTS + "storage_energy.png", 32, 64);
            addElement(throttle);
        }
    }

}
