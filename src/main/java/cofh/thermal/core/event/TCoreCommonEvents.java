package cofh.thermal.core.event;

import cofh.core.compat.curios.CuriosProxy;
import cofh.lib.util.filter.IFilterOptions;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
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
        if (player.isEyeInFluid(FluidTags.WATER)) {
            boolean diveChest = player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof DivingArmorItem;
            if (!EnchantmentHelper.hasAquaAffinity(player) && diveChest) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
            boolean diveLegs = player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.isOnGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, AIR_AFFINITY) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        } else if (player.isInWater()) {
            boolean diveLegs = player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.isOnGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, AIR_AFFINITY) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        }
    }

    @SubscribeEvent
    public static void handleEntityItemPickup(final EntityItemPickupEvent event) {

        if (event.isCanceled()) {
            return;
        }
        PlayerEntity player = event.getPlayer();
        if (player.containerMenu instanceof SatchelContainer || player.containerMenu instanceof IFilterOptions) {
            return;
        }
        PlayerInventory inventory = player.inventory;
        final boolean[] cancel = {false};
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() instanceof SatchelItem) {
                cancel[0] |= SatchelItem.onItemPickup(event, stack);
            }
        }
        CuriosProxy.getAllWorn(player).ifPresent(c -> {
            for (int i = 0; i < c.getSlots(); ++i) {
                ItemStack stack = c.getStackInSlot(i);
                if (stack.getItem() instanceof SatchelItem) {
                    // TODO: Revisit if copy is *really* necessary - probably isn't.
                    ItemStack satchelCopy = stack.copy();
                    cancel[0] |= SatchelItem.onItemPickup(event, satchelCopy);
                    c.setStackInSlot(i, satchelCopy);
                }
            }
        });
        event.setCanceled(cancel[0]);
    }

}
