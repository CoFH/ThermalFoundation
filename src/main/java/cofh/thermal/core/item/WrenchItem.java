package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.block.IDismantleable;
import cofh.lib.block.IWrenchable;
import cofh.lib.item.IMultiModeItem;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.BlockHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.lib.util.references.CoreReferences.WRENCHED;

public class WrenchItem extends ItemCoFH implements IMultiModeItem {

    private final Multimap<Attribute, AttributeModifier> toolAttributes;

    public WrenchItem(Properties builder) {

        super(builder);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> multimap = ImmutableMultimap.builder();
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 0.0D, AttributeModifier.Operation.ADDITION));

        this.toolAttributes = multimap.build();
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.wrench.mode." + getMode(stack)).withStyle(ChatFormatting.ITALIC));
        addModeChangeTooltip(this, stack, worldIn, tooltip, flagIn);

        super.tooltipDelegate(stack, worldIn, tooltip, flagIn);
    }

    protected boolean useDelegate(ItemStack stack, UseOnContext context) {

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (player == null || world.isEmptyBlock(pos)) {
            return false;
        }
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (player.isSecondaryUseActive() && block instanceof IDismantleable && ((IDismantleable) block).canDismantle(world, pos, state, player)) {
            if (Utils.isServerWorld(world)) {
                BlockHitResult target = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
                ((IDismantleable) block).dismantleBlock(world, pos, state, target, player, false);
            }
            player.swing(context.getHand());
            return true;
        } else if (!player.isSecondaryUseActive()) {
            if (block instanceof IWrenchable && ((IWrenchable) block).canWrench(world, pos, state, player)) {
                BlockHitResult target = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
                ((IWrenchable) block).wrenchBlock(world, pos, state, target, player);
                return true;
            }
            return BlockHelper.attemptRotateBlock(state, world, pos);
        }
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        target.addEffect(new MobEffectInstance(WRENCHED, 60, 0, false, false));
        stack.hurtAndBreak(1, attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {

        return slot == EquipmentSlot.MAINHAND ? this.toolAttributes : ImmutableMultimap.of();
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {

        return new ItemStack(this);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {

        return true;
    }

    // region IMultiModeItem
    @Override
    public void onModeChange(Player player, ItemStack stack) {

        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 0.4F, (isActive(stack) ? 0.7F : 0.5F) + 0.1F * getMode(stack));
        ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.thermal.wrench.mode." + getMode(stack)));
    }
    // endregion
}
