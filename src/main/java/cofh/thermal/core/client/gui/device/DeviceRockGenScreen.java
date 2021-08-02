package cofh.thermal.core.client.gui.device;

import cofh.core.client.gui.element.ElementBlock;
import cofh.core.client.gui.element.ElementFluid;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.client.gui.ThermalGuiHelper;
import cofh.thermal.core.inventory.container.device.DeviceRockGenContainer;
import cofh.thermal.core.tileentity.device.DeviceRockGenTile;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DeviceRockGenScreen extends ThermalTileScreenBase<DeviceRockGenContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/rock_gen.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final FluidStack LAVA = new FluidStack(Fluids.LAVA, BUCKET_VOLUME);

    protected DeviceRockGenTile myTile;

    public DeviceRockGenScreen(DeviceRockGenContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_rock_gen"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_rock_gen");
        myTile = (DeviceRockGenTile) container.tile;
    }

    @Override
    public void init() {

        super.init();

        addElement(ThermalGuiHelper.createDefaultFluidProgress(this, 84, 34, PROG_ARROW_FLUID_RIGHT, tile));

        addElement(createSlot(this, 44, 46).setVisible(() -> myTile.isActive && myTile.getBelow() != Blocks.AIR));

        addElement(new ElementFluid(this, 33, 24).setFluid(LAVA).setSize(16, 16).setVisible(() -> myTile.getAdjLava() > 0));
        addElement(new ElementBlock(this, 55, 24).setBlock(() -> myTile.getAdjacent()).setVisible(() -> myTile.isActive && myTile.getAdjacent() != Blocks.AIR));
        addElement(new ElementBlock(this, 44, 46).setBlock(() -> myTile.getBelow()).setVisible(() -> myTile.isActive && myTile.getBelow() != Blocks.AIR));
    }

}
