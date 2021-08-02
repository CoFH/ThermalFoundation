package cofh.thermal.core.client.gui.device;

import cofh.core.client.gui.element.ElementButton;
import cofh.core.client.gui.element.SimpleTooltip;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.device.DeviceNullifierContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.helpers.SoundHelper.playClickSound;

public class DeviceNullifierScreen extends ThermalTileScreenBase<DeviceNullifierContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/nullifier.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final String TEX_TRASH = ID_THERMAL + ":textures/gui/devices/nullifier_empty_bin.png";

    public DeviceNullifierScreen(DeviceNullifierContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_nullifier"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_nullifier");
    }

    @Override
    public void init() {

        super.init();

        addElement(new ElementButton(this, 132, 34) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.emptyBin();
                playClickSound(0.7F);
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture(TEX_TRASH, 54, 18)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.thermal.device_nullifier_empty_bin")))
                .setEnabled(() -> !container.tile.binHasItems()));
    }

}
