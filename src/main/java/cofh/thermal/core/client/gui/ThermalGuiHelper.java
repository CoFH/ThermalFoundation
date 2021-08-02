package cofh.thermal.core.client.gui;

import cofh.core.client.gui.CoreTextures;
import cofh.core.client.gui.element.ElementConditionalLayered;
import cofh.core.client.gui.element.ElementScaled;
import cofh.core.client.gui.element.ElementScaledFluid;
import cofh.core.client.gui.element.panel.ResourcePanel;
import cofh.core.util.helpers.GuiHelper;
import cofh.core.util.helpers.RenderHelper;
import cofh.lib.client.gui.IGuiAccess;
import cofh.lib.util.helpers.BlockHelper;
import cofh.thermal.lib.tileentity.CellTileBase;
import cofh.thermal.lib.tileentity.ReconfigurableTile4Way;
import cofh.thermal.lib.tileentity.ThermalTileBase;

import static cofh.core.util.helpers.RenderHelper.getFluidTexture;
import static cofh.core.util.helpers.RenderHelper.textureExists;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.constants.Constants.TRUE;
import static cofh.lib.util.control.IReconfigurable.SideConfig.*;
import static cofh.thermal.core.client.ThermalTextures.*;
import static net.minecraft.util.Direction.DOWN;
import static net.minecraft.util.Direction.UP;

public class ThermalGuiHelper {

    private ThermalGuiHelper() {

    }

    // region MACHINE CONFIG
    public static ElementConditionalLayered createDefaultMachineConfigTop(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        String specificTexture = ID_THERMAL + ":block/machines/machine_" + machine + "_top";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/machines/machine_top", TRUE)
                .addSprite(MACHINE_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_INPUT)
                .addSprite(MACHINE_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_OUTPUT)
                .addSprite(MACHINE_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_BOTH)
                .addSprite(MACHINE_CONFIG_ACCESSIBLE, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_ACCESSIBLE)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultMachineConfigBottom(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        String specificTexture = ID_THERMAL + ":block/machines/machine_" + machine + "_bottom";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/machines/machine_bottom", TRUE)
                .addSprite(MACHINE_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_INPUT)
                .addSprite(MACHINE_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_OUTPUT)
                .addSprite(MACHINE_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_BOTH)
                .addSprite(MACHINE_CONFIG_ACCESSIBLE, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_ACCESSIBLE)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultMachineConfigLeft(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        String specificTexture = ID_THERMAL + ":block/machines/machine_" + machine + "_side";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/machines/machine_side", TRUE)
                .addSprite(MACHINE_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_INPUT)
                .addSprite(MACHINE_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_OUTPUT)
                .addSprite(MACHINE_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_BOTH)
                .addSprite(MACHINE_CONFIG_ACCESSIBLE, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_ACCESSIBLE)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultMachineConfigRight(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        String specificTexture = ID_THERMAL + ":block/machines/machine_" + machine + "_side";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/machines/machine_side", TRUE)
                .addSprite(MACHINE_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_INPUT)
                .addSprite(MACHINE_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_OUTPUT)
                .addSprite(MACHINE_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_BOTH)
                .addSprite(MACHINE_CONFIG_ACCESSIBLE, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_ACCESSIBLE)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultMachineConfigFace(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        String texture = ID_THERMAL + ":block/machines/machine_" + machine;
        String activeTexture = ID_THERMAL + ":block/machines/machine_" + machine + "_active";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(texture) ? texture : "thermal:block/machines/machine_side", () -> !tile.isActive)
                .addSprite(textureExists(activeTexture) ? activeTexture : "thermal:block/machines/machine_side", () -> tile.isActive)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultMachineConfigBack(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        String specificTexture = ID_THERMAL + ":block/machines/machine_" + machine + "_side";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/machines/machine_side", TRUE)
                .addSprite(MACHINE_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_INPUT)
                .addSprite(MACHINE_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_OUTPUT)
                .addSprite(MACHINE_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_BOTH)
                .addSprite(MACHINE_CONFIG_ACCESSIBLE, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_ACCESSIBLE)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered[] createDefaultMachineConfigs(IGuiAccess gui, String machine, ReconfigurableTile4Way tile) {

        return new ElementConditionalLayered[]{
                createDefaultMachineConfigTop(gui, machine, tile),
                createDefaultMachineConfigLeft(gui, machine, tile),
                createDefaultMachineConfigFace(gui, machine, tile),
                createDefaultMachineConfigRight(gui, machine, tile),
                createDefaultMachineConfigBottom(gui, machine, tile),
                createDefaultMachineConfigBack(gui, machine, tile)
        };
    }
    // endregion

    // region CELL CONFIG
    public static ElementConditionalLayered createDefaultCellConfigTop(IGuiAccess gui, String cell, CellTileBase tile) {

        String specificTexture = ID_THERMAL + ":block/cells/" + cell + "_top";
        String centerTexture = ID_THERMAL + ":block/cells/" + cell + "_center";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(centerTexture) ? centerTexture : null, TRUE)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/cells/cell_top", TRUE)
                .addSprite(CELL_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_INPUT)
                .addSprite(CELL_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_OUTPUT)
                .addSprite(CELL_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(UP) == SIDE_BOTH)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultCellConfigBottom(IGuiAccess gui, String cell, CellTileBase tile) {

        String specificTexture = ID_THERMAL + ":block/cells/" + cell + "_bottom";
        String centerTexture = ID_THERMAL + ":block/cells/" + cell + "_center";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(centerTexture) ? centerTexture : null, TRUE)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/cells/cell_bottom", TRUE)
                .addSprite(CELL_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_INPUT)
                .addSprite(CELL_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_OUTPUT)
                .addSprite(CELL_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(DOWN) == SIDE_BOTH)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultCellConfigLeft(IGuiAccess gui, String cell, CellTileBase tile) {

        String specificTexture = ID_THERMAL + ":block/cells/" + cell + "_side";
        String centerTexture = ID_THERMAL + ":block/cells/" + cell + "_center";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(centerTexture) ? centerTexture : null, TRUE)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/cells/cell_side", TRUE)
                .addSprite(CELL_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_INPUT)
                .addSprite(CELL_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_OUTPUT)
                .addSprite(CELL_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(BlockHelper.left(tile.getFacing())) == SIDE_BOTH)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultCellConfigRight(IGuiAccess gui, String cell, CellTileBase tile) {

        String specificTexture = ID_THERMAL + ":block/cells/" + cell + "_side";
        String centerTexture = ID_THERMAL + ":block/cells/" + cell + "_center";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(centerTexture) ? centerTexture : null, TRUE)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/cells/cell_side", TRUE)
                .addSprite(CELL_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_INPUT)
                .addSprite(CELL_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_OUTPUT)
                .addSprite(CELL_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(BlockHelper.right(tile.getFacing())) == SIDE_BOTH)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered createDefaultCellConfigFace(IGuiAccess gui, String cell, CellTileBase tile) {

        String specificTexture = ID_THERMAL + ":block/cells/" + cell + "_side";
        String centerTexture = ID_THERMAL + ":block/cells/" + cell + "_center";
        String levelTexture = ID_THERMAL + ":block/cells/" + cell + "_level_";

        ElementConditionalLayered ret = (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(centerTexture) ? centerTexture : null, TRUE)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/cells/cell_side", TRUE)
                .addSprite(CELL_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(tile.getFacing()) == SIDE_INPUT)
                .addSprite(CELL_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(tile.getFacing()) == SIDE_OUTPUT)
                .addSprite(CELL_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(tile.getFacing()) == SIDE_BOTH)
                .setSize(16, 16);

        for (int i = 0; i < 9; ++i) {
            int level = i;
            String levelTex = levelTexture + level;
            ret.addSprite(textureExists(levelTex) ? levelTex : null, () -> tile.getLevelTracker() == level);
        }
        String creativeTex = levelTexture + "8c";
        ret.addSprite(textureExists(creativeTex) ? creativeTex : null, () -> tile.getLevelTracker() == 9);
        creativeTex = levelTexture + "0c";
        ret.addSprite(textureExists(creativeTex) ? creativeTex : null, () -> tile.getLevelTracker() >= 10);
        return ret;
    }

    public static ElementConditionalLayered createDefaultCellConfigBack(IGuiAccess gui, String cell, CellTileBase tile) {

        String specificTexture = ID_THERMAL + ":block/cells/" + cell + "_side";
        String centerTexture = ID_THERMAL + ":block/cells/" + cell + "_center";
        return (ElementConditionalLayered) new ElementConditionalLayered(gui)
                .addSprite(() -> getFluidTexture(tile.getRenderFluid()), () -> RenderHelper.getFluidColor(tile.getRenderFluid()), () -> !tile.getRenderFluid().isEmpty())
                .addSprite(textureExists(centerTexture) ? centerTexture : null, TRUE)
                .addSprite(textureExists(specificTexture) ? specificTexture : "thermal:block/cells/cell_side", TRUE)
                .addSprite(CELL_CONFIG_INPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_INPUT)
                .addSprite(CELL_CONFIG_OUTPUT, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_OUTPUT)
                .addSprite(CELL_CONFIG_BOTH, () -> tile.reconfigControl().getSideConfig(BlockHelper.opposite(tile.getFacing())) == SIDE_BOTH)
                .setSize(16, 16);
    }

    public static ElementConditionalLayered[] createDefaultCellConfigs(IGuiAccess gui, String cell, CellTileBase tile) {

        return new ElementConditionalLayered[]{
                createDefaultCellConfigTop(gui, cell, tile),
                createDefaultCellConfigLeft(gui, cell, tile),
                createDefaultCellConfigFace(gui, cell, tile),
                createDefaultCellConfigRight(gui, cell, tile),
                createDefaultCellConfigBottom(gui, cell, tile),
                createDefaultCellConfigBack(gui, cell, tile)
        };
    }
    // endregion

    // region COMMON UX
    public static ElementScaled createDefaultProgress(IGuiAccess gui, int posX, int posY, String texture, ThermalTileBase tile) {

        return GuiHelper.createDefaultProgress(gui, posX, posY, texture, tile::getScaledProgress, () -> tile.getRenderFluid().isEmpty());
    }

    public static ElementScaledFluid createDefaultFluidProgress(IGuiAccess gui, int posX, int posY, String texture, ThermalTileBase tile) {

        return GuiHelper.createDefaultFluidProgress(gui, posX, posY, texture, tile::getScaledProgress, tile::getRenderFluid, () -> !tile.getRenderFluid().isEmpty());
    }

    public static ElementScaled createDefaultSpeed(IGuiAccess gui, int posX, int posY, String texture, ThermalTileBase tile) {

        return GuiHelper.createDefaultSpeed(gui, posX, posY, texture, tile::getScaledSpeed);
    }

    public static ElementScaled createDefaultDuration(IGuiAccess gui, int posX, int posY, String texture, ThermalTileBase tile) {

        return GuiHelper.createDefaultDuration(gui, posX, posY, texture, tile::getScaledDuration);
    }

    public static ResourcePanel createDefaultEnergyUserPanel(IGuiAccess gui, ThermalTileBase tile) {

        return new ResourcePanel(gui)
                .setResource(CoreTextures.ICON_ENERGY, "info.cofh.energy", false)
                .setCurrent(tile::getCurSpeed, "info.cofh.energy_use", "RF/t")
                .setMax(tile::getMaxSpeed, "info.cofh.energy_max_use", "RF/t")
                .setEfficiency(tile::getEfficiency);
    }

    public static ResourcePanel createDefaultEnergyProducerPanel(IGuiAccess gui, ThermalTileBase tile) {

        return new ResourcePanel(gui)
                .setResource(CoreTextures.ICON_ENERGY, "info.cofh.energy", true)
                .setCurrent(tile::getCurSpeed, "info.cofh.energy_prod", "RF/t")
                .setMax(tile::getMaxSpeed, "info.cofh.energy_max_prod", "RF/t")
                .setEfficiency(tile::getEfficiency);
    }
    // endregion
}
