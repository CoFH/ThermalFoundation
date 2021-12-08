package cofh.thermal.core.client.gui;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.ChunkLoaderContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class ChunkLoaderScreen extends ThermalTileScreenBase<ChunkLoaderContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/chunk_loader.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public ChunkLoaderScreen(ChunkLoaderContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.chunk_loader"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.chunk_loader");
    }

    @Override
    public void init() {

        super.init();

        // addElement(setClearable(createDefaultEnergyStorage(this, 8, 8, tile.getEnergyStorage()), tile, 0));
    }

}
