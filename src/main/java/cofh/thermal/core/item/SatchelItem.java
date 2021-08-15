package cofh.thermal.core.item;

import cofh.core.item.InventoryContainerItemAugmentable;
import cofh.core.util.ProxyUtils;
import cofh.core.util.filter.EmptyFilter;
import cofh.core.util.filter.FilterRegistry;
import cofh.core.util.helpers.ChatHelper;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.item.IColorableItem;
import cofh.lib.item.IMultiModeItem;
import cofh.lib.util.Utils;
import cofh.lib.util.filter.IFilter;
import cofh.lib.util.filter.IFilterableItem;
import cofh.lib.util.helpers.FilterHelper;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import cofh.thermal.lib.common.ThermalConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.WeakHashMap;

import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.setAttributeFromAugmentString;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class SatchelItem extends InventoryContainerItemAugmentable implements IColorableItem, IFilterableItem, IMultiModeItem, INamedContainerProvider {

    protected static final WeakHashMap<ItemStack, IFilter> FILTERS = new WeakHashMap<>(MAP_CAPACITY);

    public SatchelItem(Properties builder, int slots) {

        super(builder, slots);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("color"), (stack, world, entity) -> (getColor(stack, 1) != 0xFFFFFF ? 0.33F : 0) + (getColor(stack, 2) != 0xFFFFFF ? 0.66F : 0));
        ProxyUtils.registerColorable(this);

        numSlots = () -> ThermalConfig.storageAugments;
        augValidator = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FILTER);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.satchel.use").mergeStyle(TextFormatting.GRAY));
        if (FilterHelper.hasFilter(stack)) {
            tooltip.add(getTextComponent("info.thermal.satchel.use.sneak").mergeStyle(TextFormatting.DARK_GRAY));
        }
        tooltip.add(getTextComponent("info.thermal.satchel.mode." + getMode(stack)).mergeStyle(TextFormatting.ITALIC));
        addIncrementModeChangeTooltip(stack, worldIn, tooltip, flagIn);

        super.tooltipDelegate(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        return useDelegate(stack, playerIn, handIn) ? ActionResult.resultSuccess(stack) : ActionResult.resultPass(stack);
    }

    //    @Override
    //    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
    //
    //        return useDelegate(stack, context.getPlayer(), context.getHand()) ? ActionResultType.SUCCESS : ActionResultType.PASS;
    //    }

    // region HELPERS
    protected boolean useDelegate(ItemStack stack, PlayerEntity player, Hand hand) {

        if (Utils.isFakePlayer(player)) {
            return false;
        }
        if (player instanceof ServerPlayerEntity) {
            if (!canPlayerAccess(stack, player)) {
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_warning", SecurityHelper.getOwnerName(stack)));
                return false;
            } else if (SecurityHelper.attemptClaimItem(stack, player)) {
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.cofh.secure_item"));
                return false;
            }
            if (player.isSecondaryUseActive()) {
                if (FilterHelper.hasFilter(stack)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, getFilter(stack));
                    return true;
                }
                return false;
            }
            NetworkHooks.openGui((ServerPlayerEntity) player, this);
        }
        return true;
    }

    public static boolean onItemPickup(EntityItemPickupEvent event, ItemStack container) {

        SatchelItem satchelItem = (SatchelItem) container.getItem();
        if (satchelItem.getMode(container) <= 0 || !satchelItem.canPlayerAccess(container, event.getPlayer())) {
            return false;
        }
        ItemEntity eventItem = event.getItem();
        int count = eventItem.getItem().getCount();

        if (satchelItem.getFilter(container).valid(eventItem.getItem())) {
            SimpleItemInv containerInv = satchelItem.getContainerInventory(container);
            eventItem.setItem(InventoryHelper.insertStackIntoInventory(containerInv, eventItem.getItem(), false));

            if (eventItem.getItem().getCount() != count) {
                container.setAnimationsToGo(5);
                PlayerEntity player = event.getPlayer();
                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((MathHelper.RANDOM.nextFloat() - MathHelper.RANDOM.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                containerInv.write(satchelItem.getOrCreateInvTag(container));
                satchelItem.onContainerInventoryChanged(container);
            }
        }
        return eventItem.getItem().getCount() != count;
    }

    @Override
    protected void setAttributesFromAugment(ItemStack container, CompoundNBT augmentData) {

        CompoundNBT subTag = container.getChildTag(TAG_PROPERTIES);
        if (subTag == null) {
            return;
        }
        setAttributeFromAugmentString(subTag, augmentData, TAG_FILTER_TYPE);

        super.setAttributesFromAugment(container, augmentData);
    }
    // endregion

    // region INamedContainerProvider
    @Override
    public ITextComponent getDisplayName() {

        return new TranslationTextComponent("item.thermal.satchel");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new SatchelContainer(i, inventory, player);
    }
    // endregion

    // region IColorableItem
    @Override
    public int getColor(ItemStack item, int colorIndex) {

        if (colorIndex > 0) {
            CompoundNBT colorTag = item.getChildTag(TAG_COLORS);
            if (colorTag != null) {
                return colorTag.getInt(TAG_INDEX + colorIndex);
            }
        }
        return 0xFFFFFF;
    }
    // endregion

    // region IFilterableItem
    @Override
    public IFilter getFilter(ItemStack stack) {

        String filterType = FilterHelper.getFilterType(stack);
        if (filterType.isEmpty()) {
            return EmptyFilter.INSTANCE;
        }
        IFilter ret = FILTERS.get(stack);
        if (ret != null) {
            return ret;
        }
        if (FILTERS.size() > MAP_CAPACITY) {
            FILTERS.clear();
        }
        FILTERS.put(stack, FilterRegistry.getHeldFilter(filterType, stack.getTag()));
        return FILTERS.get(stack);
    }

    @Override
    public void onFilterChanged(ItemStack stack) {

        FILTERS.remove(stack);
    }
    // endregion

    // region IMultiModeItem
    @Override
    public void onModeChange(PlayerEntity player, ItemStack stack) {

        player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.4F, 0.8F + 0.4F * getMode(stack));
        ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslationTextComponent("info.thermal.satchel.mode." + getMode(stack)));
    }
    // endregion
}
