package cofh.thermal.core.data;

import cofh.lib.data.AdvancementProviderCoFH;
import cofh.thermal.lib.common.ThermalFlags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.ID_TINKER_BENCH;

public class TCoreAdvancementProvider extends AdvancementProviderCoFH implements Consumer<Consumer<Advancement>> {

    public TCoreAdvancementProvider(DataGenerator generatorIn) {

        super(generatorIn);
        manager = ThermalFlags.manager();
        advancements.add(this);
    }

    @Override
    public String getName() {

        return "Thermal Core: Advancements";
    }

    @Override
    public void accept(Consumer<Advancement> advancementConsumer) {

        Advancement root = Advancement.Builder.advancement().display(BLOCKS.get(ID_TINKER_BENCH), new TranslationTextComponent("advancements.thermal.root.title"), new TranslationTextComponent("advancements.thermal.root.description"), new ResourceLocation("thermal:textures/gui/advancements/backgrounds/blueprint.png"), FrameType.TASK, false, false, false).addCriterion("has_tinker_bench", InventoryChangeTrigger.Instance.hasItems(BLOCKS.get(ID_TINKER_BENCH))).save(advancementConsumer, "thermal:root");
        Advancement wrench = Advancement.Builder.advancement().parent(root).display(ITEMS.get("wrench"), new TranslationTextComponent("advancements.thermal.wrench.title"), new TranslationTextComponent("advancements.thermal.wrench.description"), null, FrameType.TASK, true, true, false).addCriterion("has_wrench", InventoryChangeTrigger.Instance.hasItems(ITEMS.get("wrench"))).save(advancementConsumer, "thermal:wrench");
    }

}
