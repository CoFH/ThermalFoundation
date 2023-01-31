package cofh.thermal.core.item;

import cofh.core.item.IMultiModeItem;
import cofh.core.util.ProxyUtils;
import cofh.core.util.filter.EmptyFilter;
import cofh.core.util.filter.FilterRegistry;
import cofh.core.util.filter.IFilter;
import cofh.core.util.filter.IFilterableItem;
import cofh.core.util.helpers.ChatHelper;
import cofh.core.util.helpers.FilterHelper;
import cofh.core.util.helpers.InventoryHelper;
import cofh.lib.api.item.IColorableItem;
import cofh.lib.api.item.ISecurableItem;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import cofh.thermal.lib.item.InventoryContainerItemAugmentable;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static cofh.core.util.helpers.AugmentableHelper.setAttributeFromAugmentString;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static net.minecraft.nbt.Tag.TAG_COMPOUND;

public class SatchelItem extends InventoryContainerItemAugmentable implements IColorableItem, DyeableLeatherItem, IFilterableItem, IMultiModeItem, ISecurableItem, MenuProvider {

    protected static final Set<Item> BANNED_ITEMS = new ObjectOpenHashSet<>();

    public static void setBannedItems(Collection<String> itemLocs) {

        synchronized (BANNED_ITEMS) {
            BANNED_ITEMS.clear();

            for (String loc : itemLocs) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(loc));
                if (item != null) {
                    BANNED_ITEMS.add(item);
                }
            }
        }
    }

    protected static final WeakHashMap<ItemStack, IFilter> FILTERS = new WeakHashMap<>(MAP_CAPACITY);

    public SatchelItem(Properties builder, int slots) {

        super(builder, slots);

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("color"), (stack, world, entity, seed) -> (hasCustomColor(stack) ? 1F : 0));
        ProxyUtils.registerColorable(this);

        numSlots = () -> ThermalCoreConfig.storageAugments;
        augValidator = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FILTER);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.satchel.use").withStyle(ChatFormatting.GRAY));
        if (FilterHelper.hasFilter(stack)) {
            tooltip.add(getTextComponent("info.thermal.satchel.use.sneak").withStyle(ChatFormatting.DARK_GRAY));
        }
        tooltip.add(getTextComponent("info.thermal.satchel.mode." + getMode(stack)).withStyle(ChatFormatting.ITALIC));
        addModeChangeTooltip(this, stack, worldIn, tooltip, flagIn);

        super.tooltipDelegate(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        return useDelegate(stack, playerIn, handIn) ? InteractionResultHolder.success(stack) : InteractionResultHolder.pass(stack);
    }

    // region HELPERS
    public static boolean onItemPickup(EntityItemPickupEvent event, ItemStack container) {

        SatchelItem satchelItem = (SatchelItem) container.getItem();
        if (satchelItem.getMode(container) <= 0 || !satchelItem.canPlayerAccess(container, event.getPlayer())) {
            return false;
        }
        ItemEntity eventItem = event.getItem();
        int count = eventItem.getItem().getCount();

        if (satchelItem.getFilter(container).valid(eventItem.getItem())) {
            Player player = event.getPlayer();
            dropExtraItems(container, player);

            SimpleItemInv containerInv = satchelItem.getContainerInventory(container);
            eventItem.setItem(InventoryHelper.insertStackIntoInventory(containerInv, eventItem.getItem(), false));
            if (eventItem.getItem().getCount() != count) {
                container.setPopTime(5);
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((MathHelper.RANDOM.nextFloat() - MathHelper.RANDOM.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                containerInv.write(satchelItem.getOrCreateInvTag(container));
                satchelItem.onContainerInventoryChanged(container);
            }
        }
        return eventItem.getItem().getCount() != count;
    }

    public static void dropExtraItems(ItemStack container, Player player) {

        if (container.getItem() instanceof SatchelItem satchel) {
            CompoundTag nbt = satchel.getOrCreateInvTag(container);
            int numSlots = satchel.getContainerSlots(container);

            ListTag list = nbt.getList(TAG_ITEM_INV, TAG_COMPOUND);
            for (int i = list.size(); i > 0; --i) {
                CompoundTag slotTag = list.getCompound(i);
                int slot = slotTag.getByte(TAG_SLOT);
                if (slot >= numSlots) {
                    Utils.dropItemStackIntoWorldWithRandomness(ItemStorageCoFH.loadItemStack(slotTag), player.getLevel(), player.position());
                } else {
                    return; // This optimization breaks out of the loop early, since slots are always tagged in ascending order.
                }
            }
        }
    }

    protected boolean useDelegate(ItemStack stack, Player player, InteractionHand hand) {

        if (Utils.isFakePlayer(player) || hand == InteractionHand.OFF_HAND) {
            return false;
        }
        if (player instanceof ServerPlayer) {
            if (!canPlayerAccess(stack, player)) {
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.cofh.secure_warning", SecurityHelper.getOwnerName(stack)));
                return false;
            } else if (SecurityHelper.attemptClaimItem(stack, player)) {
                ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.cofh.secure_item"));
                return false;
            }
            dropExtraItems(stack, player);
            if (player.isSecondaryUseActive()) {
                if (FilterHelper.hasFilter(stack) && getFilter(stack) instanceof MenuProvider filter) {
                    FilterHelper.openHeldScreen((ServerPlayer) player, filter);
                    return true;
                }
                return false;
            }
            NetworkHooks.openGui((ServerPlayer) player, this);
        }
        return true;
    }

    @Override
    protected SimpleItemInv readInventoryFromNBT(ItemStack container) {

        CompoundTag containerTag = getOrCreateInvTag(container);
        int numSlots = getContainerSlots(container);
        ArrayList<ItemStorageCoFH> invSlots = new ArrayList<>(numSlots);
        for (int i = 0; i < numSlots; ++i) {
            invSlots.add(new ItemStorageCoFH());
        }
        SimpleItemInv inventory = new SimpleItemInv(invSlots) {

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

                if (slot < 0 || slot >= getSlots()) {
                    return false;
                }
                return !BANNED_ITEMS.contains(stack.getItem());
            }
        };
        inventory.read(containerTag);
        return inventory;
    }

    @Override
    protected void setAttributesFromAugment(ItemStack container, CompoundTag augmentData) {

        CompoundTag subTag = container.getTagElement(TAG_PROPERTIES);
        if (subTag == null) {
            return;
        }
        setAttributeFromAugmentString(subTag, augmentData, TAG_FILTER_TYPE);

        super.setAttributesFromAugment(container, augmentData);
    }
    // endregion

    // region MenuProvider
    @Override
    public Component getDisplayName() {

        return new TranslatableComponent("item.thermal.satchel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new SatchelContainer(i, inventory, player);
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
    public void onModeChange(Player player, ItemStack stack) {

        player.level.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.4F, 0.8F + 0.4F * getMode(stack));
        ChatHelper.sendIndexedChatMessageToPlayer(player, new TranslatableComponent("info.thermal.satchel.mode." + getMode(stack)));
    }
    // endregion
}
