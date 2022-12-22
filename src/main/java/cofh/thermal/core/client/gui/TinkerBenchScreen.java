package cofh.thermal.core.client.gui;

import cofh.core.client.gui.element.ElementAugmentSlots;
import cofh.core.client.gui.element.ElementButton;
import cofh.core.client.gui.element.SimpleTooltip;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.lib.client.gui.ThermalTileScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.lib.util.Constants.PATH_ELEMENTS;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class TinkerBenchScreen extends ThermalTileScreenBase<TinkerBenchContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/container/tinker_bench.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final String TEX_AUGMENT = ID_THERMAL + ":textures/gui/container/tinker_bench_mode_augment.png";
    public static final String TEX_REPLENISH = ID_THERMAL + ":textures/gui/container/tinker_bench_mode_replenish.png";

    public TinkerBenchScreen(TinkerBenchContainer container, Inventory inv, Component titleIn) {

        super(container, inv, container.tile, titleIn);
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.tinker_bench");
    }

    @Override
    public void init() {

        super.init();

        addElement(setClearable(createDefaultEnergyStorage(this, 8, 8, tile.getEnergyStorage()), tile, 0));
        addElement(setClearable(createMediumFluidStorage(this, 151, 8, tile.getTank(0)), tile, 0));
        addElement(new ElementAugmentSlots(this, 80, 17, menu::getNumTinkerAugmentSlots, menu.getTinkerAugmentSlots(),
                PATH_ELEMENTS + "disable_underlay_slot.png", () -> !menu.tile.allowAugmentation()));

        addElement(new ElementButton(this, 42, 51) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                menu.onModeChange();
                return true;
            }
        }
                .setSize(20, 20)
                .setTexture(TEX_AUGMENT, 40, 20)
                .setTooltipFactory(new SimpleTooltip(new TranslatableComponent("info.thermal.tinker_bench_mode_augment")))
                .setVisible(menu.tile::allowAugmentation));

        addElement(new ElementButton(this, 42, 51) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                menu.onModeChange();
                return true;
            }
        }
                .setSize(20, 20)
                .setTexture(TEX_REPLENISH, 40, 20)
                .setTooltipFactory(new SimpleTooltip(new TranslatableComponent("info.thermal.tinker_bench_mode_charge")))
                .setVisible(() -> !menu.tile.allowAugmentation()));
    }

    @Override
    protected boolean checkHotbarKeyPressed(int keyCode, int scanCode) {

        if (menu.isTinkerSlot(hoveredSlot)) {
            return false;
        }
        return super.checkHotbarKeyPressed(keyCode, scanCode);
    }

}
