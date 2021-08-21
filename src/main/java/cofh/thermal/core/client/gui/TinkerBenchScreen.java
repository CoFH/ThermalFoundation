package cofh.thermal.core.client.gui;

import cofh.core.client.gui.element.ElementAugmentSlots;
import cofh.core.client.gui.element.ElementButton;
import cofh.core.client.gui.element.SimpleTooltip;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.constants.Constants.PATH_ELEMENTS;

public class TinkerBenchScreen extends ThermalTileScreenBase<TinkerBenchContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/tinker_bench.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final String TEX_AUGMENT = ID_THERMAL + ":textures/gui/container/tinker_bench_mode_augment.png";
    public static final String TEX_REPLENISH = ID_THERMAL + ":textures/gui/container/tinker_bench_mode_replenish.png";

    public TinkerBenchScreen(TinkerBenchContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.tinker_bench"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.tinker_bench");
    }

    @Override
    public void init() {

        super.init();

        addElement(setClearable(createDefaultEnergyStorage(this, 8, 8, tile.getEnergyStorage()), tile, 0));
        addElement(setClearable(createMediumFluidStorage(this, 151, 8, tile.getTank(0)), tile, 0));
        addElement(new ElementAugmentSlots(this, 80, 17, container::getNumTinkerAugmentSlots, container.getTinkerAugmentSlots(),
                PATH_ELEMENTS + "disable_underlay_slot.png", () -> !container.tile.allowAugmentation()));

        addElement(new ElementButton(this, 42, 51) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.onModeChange();
                return true;
            }
        }
                .setSize(20, 20)
                .setTexture(TEX_AUGMENT, 40, 20)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.thermal.tinker_bench_mode_augment")))
                .setVisible(container.tile::allowAugmentation));

        addElement(new ElementButton(this, 42, 51) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.onModeChange();
                return true;
            }
        }
                .setSize(20, 20)
                .setTexture(TEX_REPLENISH, 40, 20)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.thermal.tinker_bench_mode_charge")))
                .setVisible(() -> !container.tile.allowAugmentation()));
    }

    @Override
    protected boolean itemStackMoved(int keyCode, int scanCode) {

        if (container.isTinkerSlot(hoveredSlot)) {
            return false;
        }
        return super.itemStackMoved(keyCode, scanCode);
    }

}