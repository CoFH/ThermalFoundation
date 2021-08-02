package cofh.thermal.lib.client.gui;

import cofh.core.client.gui.element.panel.ConfigPanel;
import cofh.lib.inventory.container.ContainerCoFH;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.lib.tileentity.ReconfigurableTile4Way;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.createDefaultEnergyStorage;
import static cofh.core.util.helpers.GuiHelper.setClearable;

public class MachineScreenReconfigurable<T extends ContainerCoFH> extends ThermalTileScreenBase<T> {

    protected ReconfigurableTile4Way tile;

    public MachineScreenReconfigurable(T container, PlayerInventory inv, ReconfigurableTile4Way tile, ITextComponent titleIn) {

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
