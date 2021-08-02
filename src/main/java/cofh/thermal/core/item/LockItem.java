package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.item.IPlacementItem;
import cofh.lib.util.Utils;
import cofh.lib.util.control.ISecurable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LockItem extends ItemCoFH implements IPlacementItem {

    public LockItem(Properties builder) {

        super(builder);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

    }

    protected boolean useDelegate(ItemStack stack, ItemUseContext context) {

        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        BlockPos pos = context.getPos();
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof ISecurable) {
            ISecurable securable = (ISecurable) tile;
            if (securable.setOwner(player.getGameProfile())) {
                securable.setAccess(ISecurable.AccessMode.PUBLIC);
                if (!player.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
                player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 0.5F, 0.8F);
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_block"));
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

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, ItemUseContext context) {

        return useDelegate(stack, context);
    }
    // endregion

}
