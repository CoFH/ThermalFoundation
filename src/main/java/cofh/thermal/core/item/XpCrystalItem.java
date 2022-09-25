package cofh.thermal.core.item;

import cofh.core.item.IMultiModeItem;
import cofh.core.item.XpContainerItem;
import cofh.core.util.ProxyUtils;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.constants.NBTTags.TAG_XP_TIMER;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public class XpCrystalItem extends XpContainerItem implements IMultiModeItem {

    public XpCrystalItem(Properties builder, int xpCapacity) {

        super(builder, xpCapacity);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("stored"), (stack, world, living, seed) -> ((float) getStoredXp(stack)) / getCapacityXP(stack));
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.xp_crystal.use").withStyle(ChatFormatting.GRAY));
        tooltip.add(getTextComponent("info.thermal.xp_crystal.use.sneak").withStyle(ChatFormatting.DARK_GRAY));

        tooltip.add(getTextComponent("info.thermal.xp_crystal.mode." + getMode(stack)).withStyle(ChatFormatting.ITALIC));
        addModeChangeTooltip(this, stack, worldIn, tooltip, flagIn);

        super.tooltipDelegate(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (Utils.isClientWorld(worldIn) || Utils.isFakePlayer(entityIn) || getMode(stack) <= 0) {
            return;
        }
        entityIn.getPersistentData().putLong(TAG_XP_TIMER, entityIn.level.getGameTime());
    }

    // region IMultiModeItem
    @Override
    public void onModeChange(Player player, ItemStack stack) {

        player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.4F, 0.6F + 0.2F * getMode(stack));
        ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.thermal.xp_crystal.mode." + getMode(stack)));
    }
    // endregion
}
