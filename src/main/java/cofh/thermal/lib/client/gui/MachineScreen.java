package cofh.thermal.lib.client.gui;

import cofh.core.client.gui.element.panel.ConfigPanel;
import cofh.core.inventory.container.ContainerCoFH;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.lib.block.entity.Reconfigurable4WayBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.createDefaultEnergyStorage;
import static cofh.core.util.helpers.GuiHelper.setClearable;

public class MachineScreen<T extends ContainerCoFH> extends AugmentableScreen<T> {

    protected Reconfigurable4WayBlockEntity tile;

    public MachineScreen(T container, Inventory inv, Reconfigurable4WayBlockEntity tile, Component titleIn) {

        super(container, inv, tile, titleIn);
        this.tile = tile;
    }

    @Override
    public void init() {

        super.init();

        addPanel(new ConfigPanel(this, tile, tile, () -> tile.getFacing())
                .addConditionals(ThermalGuiHelper.createDefaultMachineConfigs(this, name, tile)));

        if (tile.getEnergyStorage() != null && tile.getEnergyStorage().getMaxEnergyStored() > 0) {
            addPanel(ThermalGuiHelper.createDefaultEnergyUserPanel(this, tile));
            addElement(setClearable(createDefaultEnergyStorage(this, 8, 8, tile.getEnergyStorage()), tile, 0));
        }
    }

}
