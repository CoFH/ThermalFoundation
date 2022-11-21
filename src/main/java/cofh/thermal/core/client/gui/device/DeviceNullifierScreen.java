package cofh.thermal.core.client.gui.device;

import cofh.core.client.gui.element.ElementButton;
import cofh.core.client.gui.element.SimpleTooltip;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.device.DeviceNullifierContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.SoundHelper.playClickSound;

public class DeviceNullifierScreen extends ThermalTileScreenBase<DeviceNullifierContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/nullifier.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final String TEX_TRASH = ID_THERMAL + ":textures/gui/container/nullifier_empty_bin.png";

    public DeviceNullifierScreen(DeviceNullifierContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_nullifier"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_nullifier");
    }

    @Override
    public void init() {

        super.init();

        addElement(new ElementButton(this, 131, 33) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                menu.emptyBin();
                playClickSound(0.7F);
                return true;
            }
        }
                .setSize(20, 20)
                .setTexture(TEX_TRASH, 60, 20)
                .setTooltipFactory(new SimpleTooltip(Component.translatable("info.thermal.device_nullifier_empty_bin")))
                .setEnabled(() -> !menu.tile.binHasItems()));
    }

}
