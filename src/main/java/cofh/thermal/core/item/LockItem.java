package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.item.IPlacementItem;
import cofh.lib.util.Utils;
import cofh.lib.util.control.ISecurable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class LockItem extends ItemCoFH implements IPlacementItem {

    public LockItem(Properties builder) {

        super(builder);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

    }

    protected boolean useDelegate(ItemStack stack, UseOnContext context) {

        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        BlockPos pos = context.getClickedPos();
        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof ISecurable securable) {
            if (securable.setOwner(player.getGameProfile())) {
                securable.setAccess(ISecurable.AccessMode.PUBLIC);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                player.level.playSound(null, player.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 0.5F, 0.8F);
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.cofh.secure_block"));
            }
            return true;
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

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, UseOnContext context) {

        return useDelegate(stack, context);
    }
    // endregion

}
