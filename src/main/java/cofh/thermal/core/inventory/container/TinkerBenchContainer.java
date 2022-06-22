package cofh.thermal.core.inventory.container;

import cofh.core.content.inventory.container.TileContainer;
import cofh.core.network.packet.server.ContainerPacket;
import cofh.core.util.helpers.AugmentableHelper;
import cofh.lib.content.inventory.container.slot.SlotCoFH;
import cofh.lib.content.inventory.wrapper.InvWrapperCoFH;
import cofh.lib.content.inventory.wrapper.InvWrapperItem;
import cofh.thermal.core.block.entity.TinkerBenchTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.Constants.MAX_AUGMENTS;
import static cofh.thermal.core.init.TCoreContainers.TINKER_BENCH_CONTAINER;

public class TinkerBenchContainer extends TileContainer {

    public final TinkerBenchTile tile;

    protected SlotCoFH tinkerSlot;
    protected List<SlotCoFH> tinkerAugmentSlots = new ArrayList<>(MAX_AUGMENTS);
    protected InvWrapperItem itemInventory = new InvWrapperItem(this, MAX_AUGMENTS) {

        @Override
        public boolean canPlaceItem(int index, ItemStack stack) {

            return tile.allowAugmentation() && tinkerSlot.hasItem() && index < AugmentableHelper.getAugmentSlots(tinkerSlot.getItem()) && AugmentableHelper.validAugment(tinkerSlot.getItem(), stack, itemInventory.getStacks());
        }
    };

    public TinkerBenchContainer(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {

        super(TINKER_BENCH_CONTAINER.get(), windowId, world, pos, inventory, player);
        this.tile = (TinkerBenchTile) world.getBlockEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        allowSwap = false;

        tinkerSlot = new SlotCoFH(tileInv, 0, 44, 26) {

            @Override
            public void onTake(Player thePlayer, ItemStack stack) {

                writeAugmentsToItem(stack);
                itemInventory.clearContent();
                // TODO: Revisit sound.
                //                if (AugmentableHelper.isAugmentableItem(stack)) {
                //                    ProxyUtils.playSimpleSound(SOUND_TINKER, 0.2F, 1.0F);
                //                }
                super.onTake(thePlayer, stack);
            }

            @Override
            public void set(ItemStack stack) {

                if (syncing) {
                    super.set(stack);
                    return;
                }
                ItemStack curStack = tinkerSlot.getItem();
                if (!curStack.isEmpty() && !curStack.equals(stack)) {
                    writeAugmentsToItem(curStack);
                }
                super.set(stack);
                if (!stack.isEmpty()) {
                    readAugmentsFromItem(stack);
                }
            }
        };
        addSlot(tinkerSlot);

        addSlot(new SlotCoFH(tileInv, 1, 8, 53));
        addSlot(new SlotCoFH(tileInv, 2, 152, 53));

        bindAugmentSlots(tileInv, 3, this.tile.augSize());
        bindTinkerSlots(itemInventory, 0, MAX_AUGMENTS);
        bindPlayerInventory(inventory);

        readAugmentsFromItem(tinkerSlot.getItem());
    }

    private void readAugmentsFromItem(ItemStack stack) {

        if (!stack.isEmpty()) {
            itemInventory.setInvContainer(stack, AugmentableHelper.getAugments(stack), AugmentableHelper.getAugmentSlots(stack));
        }
    }

    private void writeAugmentsToItem(ItemStack stack) {

        if (!stack.isEmpty()) {
            tile.setPause(true);
            AugmentableHelper.setAugments(stack, itemInventory.getStacks());
            tile.setPause(false);
        }
    }

    private void bindTinkerSlots(Container inventory, int startIndex, int numSlots) {

        for (int i = 0; i < numSlots; ++i) {
            SlotCoFH slot = new SlotCoFH(inventory, i + startIndex, 0, 0, 1) {

                @Override
                public boolean mayPickup(Player player) {

                    return tile.allowAugmentation();
                }
            };
            tinkerAugmentSlots.add(slot);
            addSlot(slot);
        }
        ((ArrayList<SlotCoFH>) tinkerAugmentSlots).trimToSize();
    }

    public void onModeChange() {

        ContainerPacket.sendToServer(this);
    }

    public boolean isTinkerSlot(Slot slot) {

        return tinkerSlot.equals(slot);
    }

    public int getNumTinkerAugmentSlots() {

        return AugmentableHelper.getAugmentSlots(tinkerSlot.getItem());
    }

    public List<SlotCoFH> getTinkerAugmentSlots() {

        return tinkerAugmentSlots;
    }

    @Override
    public void removed(Player playerIn) {

        writeAugmentsToItem(tinkerSlot.getItem());
        super.removed(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        if (index == tinkerSlot.getSlotIndex()) {
            writeAugmentsToItem(tinkerSlot.getItem());
        }
        return super.quickMoveStack(player, index);
    }

    // region NETWORK
    @Override
    public void handleContainerPacket(FriendlyByteBuf buffer) {

        writeAugmentsToItem(tinkerSlot.getItem());
        tile.toggleTinkerSlotMode();
    }
    // endregion

}
