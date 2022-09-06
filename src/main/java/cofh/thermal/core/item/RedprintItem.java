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
                tooltip.add(new StringTextComponent(" - ")
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

    protected boolean useDelegate(ItemStack stack, ItemUseContext context) {

        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        if (player.isSecondaryUseActive() && context.getHand() == Hand.MAIN_HAND) {
            if (stack.getTag() != null) {
                player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.3F);
                stack.setTag(null);
            }
            return true;
        }
        BlockPos pos = context.getClickedPos();
        TileEntity tile = world.getBlockEntity(pos);

        if (tile instanceof ISecurable && !((ISecurable) tile).canAccess(player)) {
            return false;
        }
        if (tile instanceof IConveyableData) {
            IConveyableData conveyableTile = (IConveyableData) tile;
            if (stack.getTag() == null && context.getHand() == Hand.MAIN_HAND) {
                conveyableTile.writeConveyableData(player, stack.getOrCreateTag());
                tile.setChanged();
                if (stack.getTag().isEmpty()) {
                    stack.setTag(null);
                    return false;
                } else {
                    player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 0.7F);
                }
            } else if (stack.hasTag()) {
                conveyableTile.readConveyableData(player, stack.getTag());
                player.level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 0.5F, 0.8F);
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.FAIL;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.PASS;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), stack) && useDelegate(stack, context) ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (player.isSecondaryUseActive()) {
            if (stack.getTag() != null) {
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
            }
            stack.setTag(null);
        }
        player.swing(hand);
        return ActionResult.success(stack);
    }

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, ItemUseContext context) {

        return useDelegate(stack, context);
    }
    // endregion
}
