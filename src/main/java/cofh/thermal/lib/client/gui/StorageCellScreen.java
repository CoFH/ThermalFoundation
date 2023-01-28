package cofh.thermal.lib.client.gui;

import cofh.core.client.gui.element.panel.ConfigPanel;
import cofh.core.inventory.container.ContainerCoFH;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.lib.block.entity.CellBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StorageCellScreen<T extends ContainerCoFH> extends AugmentableScreen<T> {

    protected CellBlockEntity tile;

    public StorageCellScreen(T container, Inventory inv, CellBlockEntity tile, Component titleIn) {

        super(container, inv, tile, titleIn);
        this.tile = tile;
    }

    @Override
    public void init() {

        super.init();

        addPanel(new ConfigPanel(this, tile, tile, () -> tile.getFacing())
                .allowFacingConfig(true)
                .addConditionals(ThermalGuiHelper.createDefaultCellConfigs(this, name, tile)));
    }

}
