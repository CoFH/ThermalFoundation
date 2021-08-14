package cofh.thermal.core.event;

import cofh.core.inventory.container.HeldInventoryContainer;
import cofh.lib.util.filter.IFilterOptions;
import cofh.thermal.core.item.DivingArmorItem;
import cofh.thermal.core.item.SatchelItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.lib.util.Utils.getMaxEquippedEnchantmentLevel;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.references.EnsorcReferences.AIR_AFFINITY;

@Mod.EventBusSubscriber(modid = ID_THERMAL)
public class TCoreCommonEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void handleBreakSpeedEvent(PlayerEvent.BreakSpeed event) {

        if (event.isCanceled()) {
            return;
        }
        PlayerEntity player = event.getPlayer();
        if (player.areEyesInFluid(FluidTags.WATER)) {
            boolean diveChest = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() instanceof DivingArmorItem;
            if (!EnchantmentHelper.hasAquaAffinity(player) && diveChest) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
            boolean diveLegs = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.isOnGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, AIR_AFFINITY) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        } else if (player.isInWater()) {
            boolean diveLegs = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.isOnGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, AIR_AFFINITY) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        }
    }

    //    @SubscribeEvent(priority = EventPriority.LOW)
    //    public static void handleItemFishedEvent(ItemFishedEvent event) {
    //
    //        if (event.isCanceled()) {
    //            return;
    //        }
    //        FishingBobberEntity hook = event.getHookEntity();
    //        Entity angler = hook.func_234616_v_();
    //        if (!(angler instanceof PlayerEntity)) {
    //            return;
    //        }
    //        PlayerEntity player = (PlayerEntity) angler;
    //    }

    @SubscribeEvent
    public static void handleEntityItemPickup(final EntityItemPickupEvent event) {

        if (event.isCanceled()) {
            return;
        }
        PlayerEntity player = event.getPlayer();
        if (player.openContainer instanceof HeldInventoryContainer || player.openContainer instanceof IFilterOptions) {
            return;
        }
        PlayerInventory inventory = player.inventory;
        boolean cancel = false;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.getItem() instanceof SatchelItem) {
                cancel |= SatchelItem.onItemPickup(event, stack);
            }
        }
        event.setCanceled(cancel);
    }

}
