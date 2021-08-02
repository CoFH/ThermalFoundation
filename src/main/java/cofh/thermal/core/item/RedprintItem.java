package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.core.util.ProxyUtils;
import cofh.lib.item.IPlacementItem;
import cofh.lib.util.IConveyableData;
import cofh.lib.util.Utils;
import cofh.lib.util.control.ISecurable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.helpers.StringHelper.canLocalize;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static net.minecraft.util.text.TextFormatting.DARK_GRAY;
import static net.minecraft.util.text.TextFormatting.GRAY;

public class RedprintItem extends ItemCoFH implements IPlacementItem {

    public RedprintItem(Properties builder) {

        super(builder);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("has_data"), ((stack, world, entity) -> stack.hasTag() ? 1F : 0F));
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        CompoundNBT conveyableData = stack.getTag();

        if (conveyableData == null) {
            tooltip.add(getTextComponent("info.thermal.redprint.use").mergeStyle(GRAY));
        } else {
            tooltip.add(getTextComponent("info.thermal.redprint.use.contents").mergeStyle(GRAY));
            tooltip.add(getTextComponent("info.thermal.redprint.use.sneak").mergeStyle(DARK_GRAY));

            tooltip.add(getTextComponent("info.thermal.redprint.contents"));
            for (String type : conveyableData.keySet()) {
                if (!canLocalize("info.thermal.redprint.data." + type)) {
                    tooltip.add(getTextComponent("info.thermal.redprint.unknown")
                            .mergeStyle(DARK_GRAY));
                }
                tooltip.add(new StringTextComponent(" - ")
                        .append(getTextComponent("info.thermal.redprint.data." + type)
                                .mergeStyle(GRAY))
                );
            }
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return stack.hasTag() ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    protected boolean useDelegate(ItemStack stack, ItemUseContext context) {

        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        if (player.isSecondaryUseActive() && context.getHand() == Hand.MAIN_HAND) {
            if (stack.getTag() != null) {
                player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.3F);
                stack.setTag(null);
            }
            return true;
        }
        BlockPos pos = context.getPos();
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof ISecurable && !((ISecurable) tile).canAccess(player)) {
            return false;
        }
        if (tile instanceof IConveyableData) {
            IConveyableData conveyableTile = (IConveyableData) tile;
            if (stack.getTag() == null && context.getHand() == Hand.MAIN_HAND) {
                conveyableTile.writeConveyableData(player, stack.getOrCreateTag());
                tile.markDirty();
                if (stack.getTag().isEmpty()) {
                    stack.setTag(null);
                    return false;
                } else {
                    player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.7F);
                }
            } else {
                conveyableTile.readConveyableData(player, stack.getTag());
                player.world.playSound(null, player.getPosition(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5F, 0.8F);
            }
            return true;
        }
        return false;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.FAIL;
        }
        return player.canPlayerEdit(context.getPos(), context.getFace(), context.getItem()) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.PASS;
        }
        return player.canPlayerEdit(context.getPos(), context.getFace(), stack) && useDelegate(stack, context) ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);
        if (player.isSecondaryUseActive()) {
            if (stack.getTag() != null) {
                player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
            }
            stack.setTag(null);
        }
        player.swingArm(hand);
        return ActionResult.resultSuccess(stack);
    }

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, ItemUseContext context) {

        return useDelegate(stack, context);
    }
    // endregion
}
