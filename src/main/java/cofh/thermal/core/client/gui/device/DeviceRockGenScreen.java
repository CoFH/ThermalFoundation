package cofh.thermal.core.client.gui.device;

import cofh.core.client.gui.element.ElementBlock;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.block.entity.device.DeviceRockGenTile;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DeviceRockGenContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class DeviceRockGenScreen extends ThermalTileScreenBase<DeviceRockGenContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/rock_gen.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final FluidStack LAVA = new FluidStack(Fluids.LAVA, BUCKET_VOLUME);

    protected DeviceRockGenTile myTile;

    public DeviceRockGenScreen(DeviceRockGenContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_rock_gen"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_rock_gen");
        myTile = (DeviceRockGenTile) container.tile;
    }

    @Override
    public void init() {

        super.init();

        addElement(ThermalGuiHelper.createDefaultFluidProgress(this, 84, 34, PROG_ARROW_FLUID_RIGHT, tile));

        addElement(createSlot(this, 44, 46).setVisible(() -> myTile.getBelow() != Blocks.AIR));

        addElement(new ElementBlock(this, 33, 24).setBlock(() -> Blocks.LAVA).setVisible(() -> myTile.getAdjLava() > 0));
        addElement(new ElementBlock(this, 55, 24).setBlock(() -> myTile.getAdjacent()).setVisible(() -> myTile.getAdjacent() != Blocks.AIR));
        addElement(new ElementBlock(this, 44, 46).setBlock(() -> myTile.getBelow()).setVisible(() -> myTile.getBelow() != Blocks.AIR));
    }

}
