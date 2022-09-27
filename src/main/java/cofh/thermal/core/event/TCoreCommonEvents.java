package cofh.thermal.core.event;

import cofh.core.compat.curios.CuriosProxy;
import cofh.thermal.core.inventory.container.storage.SatchelContainer;
import cofh.thermal.core.item.DivingArmorItem;
import cofh.thermal.core.item.SatchelItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.core.util.references.EnsorcIDs.ID_AIR_AFFINITY;
import static cofh.lib.util.Utils.getEnchantment;
import static cofh.lib.util.Utils.getMaxEquippedEnchantmentLevel;
import static cofh.lib.util.constants.ModIds.ID_ENSORCELLATION;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

@Mod.EventBusSubscriber (modid = ID_THERMAL)
public class TCoreCommonEvents {

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public static void handleBreakSpeedEvent(PlayerEvent.BreakSpeed event) {

        if (event.isCanceled()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.isEyeInFluid(FluidTags.WATER)) {
            boolean diveChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivingArmorItem;
            if (!EnchantmentHelper.hasAquaAffinity(player) && diveChest) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
            boolean diveLegs = player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.isOnGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, getEnchantment(ID_ENSORCELLATION, ID_AIR_AFFINITY)) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        } else if (player.isInWater()) {
            boolean diveLegs = player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.isOnGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, getEnchantment(ID_ENSORCELLATION, ID_AIR_AFFINITY)) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        }
    }

    @SubscribeEvent
    public static void handleEntityItemPickup(final EntityItemPickupEvent event) {

        if (event.isCanceled()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.containerMenu instanceof SatchelContainer) {
            return;
        }
        Inventory inventory = player.getInventory();
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
