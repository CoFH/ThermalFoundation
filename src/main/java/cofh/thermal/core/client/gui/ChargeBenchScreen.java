package cofh.thermal.core.client.gui;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class ChargeBenchScreen extends ThermalTileScreenBase<ChargeBenchContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/charge_bench.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public ChargeBenchScreen(ChargeBenchContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.charge_bench"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.charge_bench");
    }

    @Override
    public void init() {

        super.init();

        addElement(setClearable(createDefaultEnergyStorage(this, 8, 8, tile.getEnergyStorage()), tile, 0));
    }

}
