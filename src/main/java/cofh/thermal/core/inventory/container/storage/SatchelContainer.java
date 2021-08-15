package cofh.thermal.core.inventory.container.storage;

import cofh.core.network.packet.server.SecurityPacket;
import cofh.lib.inventory.IInventoryContainerItem;
import cofh.lib.inventory.SimpleItemInv;
import cofh.lib.inventory.container.ContainerCoFH;
import cofh.lib.inventory.container.slot.SlotCoFH;
import cofh.lib.inventory.container.slot.SlotLocked;
import cofh.lib.inventory.wrapper.InvWrapperCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.control.ISecurable;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.SecurityHelper;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import static cofh.thermal.core.init.TCoreReferences.SATCHEL_CONTAINER;

public class SatchelContainer extends ContainerCoFH implements ISecurable {

    public static final int MAX_SLOTS = 54;
    public static final int MAX_ROWS = MAX_SLOTS / 9;

    protected final IInventoryContainerItem containerItem;
    protected SimpleItemInv itemInventory;
    protected InvWrapperCoFH invWrapper;
    protected ItemStack containerStack;

    protected int slots;
    protected int rows;

    public SatchelContainer(int windowId, PlayerInventory inventory, PlayerEntity player) {

        super(SATCHEL_CONTAINER, windowId, inventory, player);

        allowSwap = false;

        containerStack = player.getHeldItemMainhand();
        containerItem = (IInventoryContainerItem) containerStack.getItem();

        slots = MathHelper.clamp(containerItem.getContainerSlots(containerStack), 0, MAX_SLOTS);
        itemInventory = containerItem.getContainerInventory(containerStack);
        invWrapper = new InvWrapperCoFH(itemInventory, slots);

        rows = MathHelper.clamp(slots / 9, 0, MAX_ROWS);
        int extraSlots = slots % 9;

        int xOffset = 8;
        int yOffset = 44 - 9 * MathHelper.clamp(rows + (extraSlots > 0 ? 1 : 0), 0, 3);

        for (int i = 0; i < rows * 9; ++i) {
            addSlot(new SlotCoFH(invWrapper, i, xOffset + i % 9 * 18, yOffset + i / 9 * 18));
        }
        if (extraSlots > 0) {
            xOffset = 89 - 9 * extraSlots;
            for (int i = slots - extraSlots; i < slots; ++i) {
                addSlot(new SlotCoFH(invWrapper, i, xOffset + i % extraSlots * 18, yOffset + 18 * rows));
            }
        }
        bindPlayerInventory(inventory);
    }

    @Override
    protected void bindPlayerInventory(PlayerInventory inventory) {

        int xOffset = getPlayerInventoryHorizontalOffset();
        int yOffset = getPlayerInventoryVerticalOffset();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            if (i == inventory.currentItem) {
                addSlot(new SlotLocked(inventory, i, xOffset + i * 18, yOffset + 58));
            } else {
                addSlot(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
            }
        }
    }

    @Override
    protected int getPlayerInventoryVerticalOffset() {

        return 84 + getExtraRows() * 18;
    }

    public int getExtraRows() {

        return MathHelper.clamp((rows + (slots % 9 > 0 ? 1 : 0)) - 3, 0, (MAX_ROWS - 3));
    }

    public int getContainerInventorySize() {

        return slots;
    }

    @Override
    protected int getMergeableSlotCount() {

        return invWrapper.getSizeInventory();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {

        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {

        itemInventory.write(containerItem.getOrCreateInvTag(containerStack));
        containerItem.onContainerInventoryChanged(containerStack);
        super.onContainerClosed(playerIn);
    }

    // region ISecurable
    @Override
    public AccessMode getAccess() {

        return SecurityHelper.getAccess(containerStack);
    }

    @Override
    public GameProfile getOwner() {

        return SecurityHelper.getOwner(containerStack);
    }

    @Override
    public void setAccess(AccessMode access) {

        SecurityHelper.setAccess(containerStack, access);
        if (Utils.isClientWorld(player.world)) {
            SecurityPacket.sendToServer(access);
        }
    }

    @Override
    public boolean setOwner(GameProfile profile) {

        return false;
    }
    // endregion
}