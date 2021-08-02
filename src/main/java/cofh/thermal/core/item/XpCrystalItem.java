package cofh.thermal.core.item;

import cofh.core.item.XpContainerItem;
import cofh.core.util.ProxyUtils;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.item.IMultiModeItem;
import cofh.lib.util.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.constants.NBTTags.TAG_XP_TIMER;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public class XpCrystalItem extends XpContainerItem implements IMultiModeItem {

    public XpCrystalItem(Properties builder, int xpCapacity) {

        super(builder, xpCapacity);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("stored"), (stack, world, living) -> ((float) getStoredXp(stack)) / getCapacityXP(stack));
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.xp_crystal.use").mergeStyle(TextFormatting.GRAY));
        tooltip.add(getTextComponent("info.thermal.xp_crystal.use.sneak").mergeStyle(TextFormatting.DARK_GRAY));

        tooltip.add(getTextComponent("info.thermal.xp_crystal.mode." + getMode(stack)).mergeStyle(TextFormatting.ITALIC));
        addIncrementModeChangeTooltip(stack, worldIn, tooltip, flagIn);

        super.tooltipDelegate(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (Utils.isClientWorld(worldIn) || Utils.isFakePlayer(entityIn) || getMode(stack) <= 0) {
            return;
        }
        entityIn.getPersistentData().putLong(TAG_XP_TIMER, entityIn.world.getGameTime());
    }

    // region IMultiModeItem
    @Override
    public void onModeChange(PlayerEntity player, ItemStack stack) {

        player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.4F, 0.6F + 0.2F * getMode(stack));
        ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.thermal.xp_crystal.mode." + getMode(stack)));
    }
    // endregion
}
