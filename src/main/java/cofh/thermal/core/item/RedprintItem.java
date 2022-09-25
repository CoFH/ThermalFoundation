package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.core.util.ProxyUtils;
import cofh.lib.api.IConveyableData;
import cofh.lib.api.control.ISecurable;
import cofh.lib.api.item.IPlacementItem;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.helpers.StringHelper.canLocalize;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static net.minecraft.ChatFormatting.DARK_GRAY;
import static net.minecraft.ChatFormatting.GRAY;

public class RedprintItem extends ItemCoFH implements IPlacementItem {

    public RedprintItem(Properties builder) {

        super(builder);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("has_data"), ((stack, world, entity, seed) -> stack.hasTag() ? 1F : 0F));
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        CompoundTag conveyableData = stack.getTag();

        if (conveyableData == null) {
            tooltip.add(getTextComponent("info.thermal.redprint.use").withStyle(GRAY));
        } else {
            tooltip.add(getTextComponent("info.thermal.redprint.use.contents").withStyle(GRAY));
            tooltip.add(getTextComponent("info.thermal.redprint.use.sneak").withStyle(DARK_GRAY));

            tooltip.add(getTextComponent("info.thermal.redprint.contents"));
            for (String type : conveyableData.getAllKeys()) {
                if (!canLocalize("info.thermal.redprint.data." + type)) {
                    tooltip.add(getTextComponent("info.thermal.redprint.unknown")
                            .withStyle(DARK_GRAY));
                }
                tooltip.add(new TextComponent(" - ")
                        .append(getTextComponent("info.thermal.redprint.data." + type)
                                .withStyle(GRAY))
                );
            }
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return stack.hasTag() ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    protected boolean useDelegate(ItemStack stack, UseOnContext context) {

        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        if (player.isSecondaryUseActive() && context.getHand() == InteractionHand.MAIN_HAND) {
            if (stack.getTag() != null) {
                player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.3F);
                stack.setTag(null);
            }
            return true;
        }
        BlockPos pos = context.getClickedPos();
        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof ISecurable && !((ISecurable) tile).canAccess(player)) {
            return false;
        }
        if (tile instanceof IConveyableData conveyableTile) {
            if (stack.getTag() == null && context.getHand() == InteractionHand.MAIN_HAND) {
                conveyableTile.writeConveyableData(player, stack.getOrCreateTag());
                tile.setChanged();
                if (stack.getTag().isEmpty()) {
                    stack.setTag(null);
                    return false;
                } else {
                    player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.7F);
                }
            } else if (stack.hasTag()) {
                conveyableTile.readConveyableData(player, stack.getTag());
                player.level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 0.5F, 0.8F);
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), stack) && useDelegate(stack, context) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (player.isSecondaryUseActive()) {
            if (stack.getTag() != null) {
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
            }
            stack.setTag(null);
        }
        player.swing(hand);
        return InteractionResultHolder.success(stack);
    }

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, UseOnContext context) {

        return useDelegate(stack, context);
    }
    // endregion
}
