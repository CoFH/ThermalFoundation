package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.core.util.ProxyUtils;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.item.IMultiModeItem;
import cofh.lib.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.constants.NBTTags.TAG_PRIMED;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class DetonatorItem extends ItemCoFH implements IMultiModeItem {

    protected static final Map<Block, ITNTFactory<? extends TNTEntity>> TNT_MAP = new IdentityHashMap<>();
    protected static final int MAX_PRIMED = 16;

    public static void registerTNT(Block block, ITNTFactory<? extends TNTEntity> factory) {

        if (block == null) {
            return;
        }
        TNT_MAP.put(block, factory);
    }

    public DetonatorItem(Properties builder) {

        super(builder);
        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("primed"), (stack, world, living) -> (getMode(stack) == 0 && getPrimedCount(stack) > 0 ? 1.0F : 0.0F));
        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("armed"), (stack, world, living) -> (getMode(stack) == 1 && getPrimedCount(stack) > 0 ? 1.0F : 0.0F));
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.detonator.use." + getMode(stack)).withStyle(TextFormatting.GRAY));
        tooltip.add(getTextComponent("info.thermal.detonator.primed").withStyle(TextFormatting.GRAY).append(getTextComponent(" " + getPrimedCount(stack) + "/" + MAX_PRIMED).withStyle(getPrimedCount(stack) <= 0 ? TextFormatting.RED : getMode(stack) == 0 ? TextFormatting.YELLOW : TextFormatting.GREEN)));
        tooltip.add(getTextComponent("info.thermal.detonator.use.sneak").withStyle(TextFormatting.DARK_GRAY));

        tooltip.add(getTextComponent("info.thermal.detonator.mode." + getMode(stack)).withStyle(TextFormatting.ITALIC));
        if (getPrimedCount(stack) > 0) {
            addIncrementModeChangeTooltip(stack, worldIn, tooltip, flagIn);
        }
    }

    protected boolean useDelegate(ItemStack stack, ItemUseContext context) {

        if (getMode(stack) == 0) {
            return primeTNT(stack, context.getLevel(), context.getClickedPos(), context.getPlayer());
        }
        return detonateTNT(stack, context.getLevel(), context.getPlayer());
    }

    protected boolean primeTNT(ItemStack stack, World world, BlockPos pos, PlayerEntity player) {

        if (player == null || world.isEmptyBlock(pos)) {
            return false;
        }
        if (TNT_MAP.containsKey(world.getBlockState(pos).getBlock())) {
            ListNBT primedTNT = stack.getOrCreateTag().getList(TAG_PRIMED, TAG_COMPOUND);
            CompoundNBT tntPos = NBTUtil.writeBlockPos(pos);
            if (primedTNT.size() >= MAX_PRIMED || primedTNT.contains(tntPos)) {
                return false;
            }
            primedTNT.add(tntPos);
            stack.getOrCreateTag().put(TAG_PRIMED, primedTNT);
            return true;
        }
        return false;
    }

    protected boolean detonateTNT(ItemStack stack, World world, PlayerEntity player) {

        if (player == null) {
            return false;
        }
        if (Utils.isServerWorld(world)) {
            ListNBT primedTNT = stack.getOrCreateTag().getList(TAG_PRIMED, TAG_COMPOUND);
            for (int i = 0; i < primedTNT.size(); ++i) {
                BlockPos tntPos = NBTUtil.readBlockPos(primedTNT.getCompound(i));
                attemptDetonate(world, tntPos, player, 0);
            }
            stack.removeTagKey(TAG_PRIMED);
        }
        setMode(stack, 0);
        return true;
    }

    protected void attemptDetonate(World world, BlockPos pos, PlayerEntity player, int fuse) {

        if (!world.isLoaded(pos)) {
            return;
        }
        Block block = world.getBlockState(pos).getBlock();
        if (TNT_MAP.containsKey(block)) {
            world.removeBlock(pos, false);
            TNTEntity tnt = TNT_MAP.get(block).createTNT(world, pos.getX(), pos.getY(), pos.getZ(), player);
            tnt.setFuse(fuse);
            world.addFreshEntity(tnt);
            world.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    protected int getPrimedCount(ItemStack stack) {

        if (!stack.hasTag()) {
            return 0;
        }
        ListNBT primedTNT = stack.getTag().getList(TAG_PRIMED, TAG_COMPOUND);
        return primedTNT.size();
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
            stack.removeTagKey(TAG_PRIMED);
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
            return ActionResult.success(stack);
        }
        if (getMode(stack) == 1 && detonateTNT(stack, world, player)) {
            player.swing(hand);
            player.playSound(SoundEvents.LEVER_CLICK, 0.4F, 1.0F);
            return ActionResult.success(stack);
        }
        return ActionResult.pass(stack);
    }

    // region IMultiModeItem
    @Override
    public int getNumModes(ItemStack stack) {

        return getPrimedCount(stack) > 0 ? 2 : 1;
    }

    @Override
    public void onModeChange(PlayerEntity player, ItemStack stack) {

        player.level.playSound(null, player.blockPosition(), SoundEvents.LEVER_CLICK, SoundCategory.PLAYERS, 0.4F, 1.0F - 0.3F * getMode(stack));
        ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.thermal.detonator.mode." + getMode(stack)));
    }
    // endregion

    // region FACTORY
    public interface ITNTFactory<T extends TNTEntity> {

        T createTNT(World world, double x, double y, double z, LivingEntity igniter);

    }
    // endregion
}
