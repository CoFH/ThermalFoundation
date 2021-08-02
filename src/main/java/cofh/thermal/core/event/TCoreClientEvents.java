package cofh.thermal.core.event;

import cofh.lib.util.helpers.AugmentDataHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.constants.NBTTags.TAG_TYPE;
import static cofh.lib.util.helpers.StringHelper.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ID_THERMAL)
public class TCoreClientEvents {

    private TCoreClientEvents() {

    }

    @SubscribeEvent
    public static void handleItemTooltipEvent(ItemTooltipEvent event) {

        List<ITextComponent> tooltip = event.getToolTip();
        if (tooltip.isEmpty()) {
            return;
        }
        ItemStack stack = event.getItemStack();

        if (AugmentDataHelper.hasAugmentData(stack)) {
            CompoundNBT augmentData = AugmentDataHelper.getAugmentData(stack);
            if (augmentData == null || augmentData.isEmpty()) {
                return;
            }
            String type = augmentData.getString(TAG_TYPE);
            if (!type.isEmpty()) {
                IFormattableTextComponent typeText = getTextComponent("info.thermal.augment.type." + type).mergeStyle(TextFormatting.WHITE);

                //                if (isTypeExclusive(type)) {
                //                    typeText.mergeStyle(TextFormatting.UNDERLINE);
                //                }
                tooltip.add(getTextComponent("info.cofh.type")
                        .mergeStyle(TextFormatting.YELLOW)
                        .appendString(": ")
                        .append(typeText)
                );
            }
            for (String mod : augmentData.keySet()) {
                if (mod.equals(TAG_TYPE) || !canLocalize("info.thermal.augment.attr." + mod)) {
                    continue;
                }
                float value = augmentData.getFloat(mod);
                boolean bad = isAdditive(mod) && value < 0
                        || isAdditive(mod) && value > 0 && isInverse(mod)
                        || isMultiplicative(mod) && (isInverse(mod) ? value > 1.0 : value < 1.0);

                IFormattableTextComponent modText = new StringTextComponent("" +
                        (isAdditive(mod) && value > 0 ? "+" : "") +
                        (isInteger(mod) ? DF0.format(value) : isMultiplicative(mod) ? DF2.format(value) + "x" : DF0.format(value * 100) + "%"))
                        .mergeStyle(bad ? TextFormatting.RED : TextFormatting.GREEN);

                if (isMaximized(mod)) {
                    modText.mergeStyle(TextFormatting.UNDERLINE);
                }
                tooltip.add(getTextComponent("info.thermal.augment.attr." + mod)
                        .appendString(": ")
                        .mergeStyle(TextFormatting.GRAY)
                        .append(modText)
                );
            }
        }
    }

}
