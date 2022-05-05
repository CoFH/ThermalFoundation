//package cofh.thermal.core.block.entity.storage;
//
//import cofh.core.network.packet.client.TileRenderPacket;
//import cofh.core.network.packet.client.TileStatePacket;
//import cofh.lib.inventory.ItemHandlerRestrictionWrapper;
//import cofh.lib.inventory.ItemStorageCoFH;
//import cofh.lib.util.StorageGroup;
//import cofh.lib.util.Utils;
//import cofh.lib.util.helpers.AugmentDataHelper;
//import cofh.lib.util.helpers.InventoryHelper;
//import cofh.thermal.core.inventory.container.storage.ItemCellContainer;
//import cofh.thermal.lib.tileentity.CellTileBase;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.tileentity.ITickableTileEntity;
//import net.minecraft.util.Direction;
//import net.minecraftforge.client.model.data.IModelData;
//import net.minecraftforge.client.model.data.ModelDataMap;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.IItemHandler;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.function.Predicate;
//
//import static cofh.core.client.renderer.model.ModelUtils.*;
//import static cofh.lib.util.helpers.ItemHelper.cloneStack;
//import static cofh.lib.util.helpers.ItemHelper.itemsEqualWithTags;
//import static cofh.thermal.core.init.TCoreReferences.ITEM_CELL_TILE;
//import static cofh.thermal.lib.common.ThermalAugmentRules.ITEM_STORAGE_VALIDATOR;
//import static cofh.thermal.lib.common.ThermalConfig.storageAugments;
//
//public class ItemCellTile extends CellTileBase implements ITickableTileEntity {
//
//    public static final int BASE_CAPACITY = 10000;
//
//    protected ItemStorageCoFH itemStorage = new ItemStorageCoFH(BASE_CAPACITY, item -> filter.valid(item));
//
//    protected ItemStack renderItem = ItemStack.EMPTY;
//
//    public ItemCellTile() {
//
//        super(ITEM_CELL_TILE);
//
//        amountInput = 8;
//        amountOutput = 8;
//
//        inventory.addSlot(itemStorage, StorageGroup.ACCESSIBLE);
//
//        addAugmentSlots(storageAugments);
//        initHandlers();
//    }
//
//    //    @Override
//    //    public void neighborChanged(Block blockIn, BlockPos fromPos) {
//    //
//    //        super.neighborChanged(blockIn, fromPos);
//    //
//    //        // TODO: Handle caching of neighbor caps.
//    //    }
//
//    @Override
//    public void tick() {
//
//        if (redstoneControl.getState()) {
//            transferOut();
//            transferIn();
//        }
//        if (Utils.timeCheck(level)) {
//            updateTrackers(true);
//            if (!itemsEqualWithTags(renderItem, itemStorage.getItemStack())) {
//                renderItem = cloneStack(itemStorage.getItemStack(), 1);
//                TileRenderPacket.sendToClient(this);
//            }
//        }
//    }
//
//    protected void transferIn() {
//
//        if (!transferControl.getTransferIn()) {
//            return;
//        }
//        if (amountInput <= 0 || itemStorage.isFull()) {
//            return;
//        }
//        for (int i = inputTracker; i < 6 && itemStorage.getSpace() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isInput()) {
//                attemptTransferIn(Direction.from3DDataValue(i));
//            }
//        }
//        for (int i = 0; i < inputTracker && itemStorage.getSpace() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isInput()) {
//                attemptTransferIn(Direction.from3DDataValue(i));
//            }
//        }
//        ++inputTracker;
//        inputTracker %= 6;
//    }
//
//    protected void transferOut() {
//
//        if (!transferControl.getTransferOut()) {
//            return;
//        }
//        if (amountOutput <= 0 || itemStorage.isEmpty()) {
//            return;
//        }
//        for (int i = outputTracker; i < 6 && itemStorage.getCount() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isOutput()) {
//                attemptTransferOut(Direction.from3DDataValue(i));
//            }
//        }
//        for (int i = 0; i < outputTracker && itemStorage.getCount() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isOutput()) {
//                attemptTransferOut(Direction.from3DDataValue(i));
//            }
//        }
//        ++outputTracker;
//        outputTracker %= 6;
//    }
//
//    protected void attemptTransferIn(Direction side) {
//
//        InventoryHelper.extractFromAdjacent(this, itemStorage, Math.min(amountInput, itemStorage.getSpace()), side);
//    }
//
//    protected void attemptTransferOut(Direction side) {
//
//        InventoryHelper.insertIntoAdjacent(this, itemStorage, amountOutput, side);
//    }
//
//    //    @Override
//    //    public boolean onActivatedDelegate(Level level, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
//    //
//    //        ItemStack heldItem = player.getHeldItem(hand);
//    //        ItemStack ret = inventory.insertItem(0, heldItem, false);
//    //
//    //        boolean inserted = false;
//    //        long time = player.getPersistentData().getLong("thermal:item_cell_click");
//    //        long currentTime = world.getGameTime();
//    //        player.getPersistentData().putLong("thermal:item_cell_click", currentTime);
//    //
//    //        if (!player.abilities.isCreativeMode) {
//    //            if (ret != heldItem) {
//    //                player.inventory.setInventorySlotContents(player.inventory.currentItem, ret);
//    //                inserted = true;
//    //            }
//    //            if (currentTime - time < 15 && player.getHeldItem(hand).isEmpty() && !inventory.get(0).isEmpty()) {
//    //                inserted |= insertAllItemsFromPlayer(player);
//    //            }
//    //        }
//    //        if (inserted) {
//    //            world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.1F, 0.7F);
//    //        }
//    //        return inserted;
//    //    }
//    //
//    //    protected boolean insertAllItemsFromPlayer(PlayerEntity player) {
//    //
//    //        boolean inserted = false;
//    //        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
//    //            if (inventory.insertItem(0, player.inventory.getStackInSlot(i), true) != player.inventory.getStackInSlot(i)) {
//    //                player.inventory.setInventorySlotContents(i, inventory.insertItem(0, player.inventory.getStackInSlot(i), false));
//    //                inserted = true;
//    //            }
//    //        }
//    //        return inserted;
//    //    }
//
//    @Override
//    protected boolean keepItems() {
//
//        return true;
//    }
//
//    public ItemStack getRenderItem() {
//
//        return renderItem;
//    }
//
//    @Override
//    public int getMaxInput() {
//
//        return 64;
//    }
//
//    @Override
//    public int getMaxOutput() {
//
//        return 64;
//    }
//
//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
//
//        return new ItemCellContainer(i, level, worldPosition, inventory, player);
//    }
//
//    @Nonnull
//    @Override
//    public IModelData getModelData() {
//
//        return new ModelDataMap.Builder()
//                .withInitial(SIDES, reconfigControl().getRawSideConfig())
//                .withInitial(FACING, reconfigControl.getFacing())
//                .withInitial(LEVEL, levelTracker)
//                .build();
//    }
//
//    @Override
//    protected void updateTrackers(boolean send) {
//
//        int curScale = itemStorage.getStored() > 0 ? 1 + (int) (itemStorage.getRatio() * 14) : 0;
//        if (curScale != compareTracker) {
//            compareTracker = curScale;
//            if (send) {
//                setChanged();
//            }
//        }
//        if (itemStorage.isCreative()) {
//            curScale = 9;
//        } else {
//            curScale = itemStorage.getStored() > 0 ? 1 + Math.min((int) (itemStorage.getRatio() * 8), 7) : 0;
//        }
//        if (levelTracker != curScale) {
//            levelTracker = curScale;
//            if (send) {
//                TileStatePacket.sendToClient(this);
//            }
//        }
//    }
//
//    // region NETWORK
//    @Override
//    public PacketBuffer getGuiPacket(PacketBuffer buffer) {
//
//        super.getGuiPacket(buffer);
//
//        buffer.writeItem(renderItem);
//        buffer.writeInt(itemStorage.getCount());
//
//        return buffer;
//    }
//
//    @Override
//    public void handleGuiPacket(PacketBuffer buffer) {
//
//        super.handleGuiPacket(buffer);
//
//        ItemStack stack = buffer.readItem();
//        stack.setCount(buffer.readInt());
//
//        itemStorage.setItemStack(stack);
//    }
//
//    @Override
//    public PacketBuffer getRenderPacket(PacketBuffer buffer) {
//
//        super.getRenderPacket(buffer);
//
//        buffer.writeItem(renderItem);
//
//        return buffer;
//    }
//
//    @Override
//    public void handleRenderPacket(PacketBuffer buffer) {
//
//        super.handleRenderPacket(buffer);
//
//        renderItem = buffer.readItem();
//    }
//    // endregion
//
//    // region NBT
//    @Override
//    public void load(BlockState state, CompoundTag nbt) {
//
//        super.load(state, nbt);
//
//        renderItem = inventory.get(0).getStack();
//    }
//    // endregion
//
//    // region AUGMENTS
//    @Override
//    protected Predicate<ItemStack> augValidator() {
//
//        return item -> AugmentDataHelper.hasAugmentData(item) && ITEM_STORAGE_VALIDATOR.test(item, getAugmentsAsList());
//    }
//    // endregion
//
//    // region CAPABILITIES
//    protected LazyOptional<?> inputItemCap = LazyOptional.empty();
//    protected LazyOptional<?> outputItemCap = LazyOptional.empty();
//
//    @Override
//    protected void updateHandlers() {
//
//        // Optimization to prevent callback logic as contents may change rapidly.
//        LazyOptional<?> prevItemCap = itemCap;
//        LazyOptional<?> prevItemInputCap = inputItemCap;
//        LazyOptional<?> prevItemOutputCap = outputItemCap;
//
//        IItemHandler inputHandler = new ItemHandlerRestrictionWrapper(itemStorage, true, false);
//        IItemHandler outputHandler = new ItemHandlerRestrictionWrapper(itemStorage, false, true);
//
//        itemCap = LazyOptional.of(() -> itemStorage);
//        inputItemCap = LazyOptional.of(() -> inputHandler);
//        outputItemCap = LazyOptional.of(() -> outputHandler);
//
//        prevItemCap.invalidate();
//        prevItemInputCap.invalidate();
//        prevItemOutputCap.invalidate();
//    }
//
//    @Override
//    protected <T> LazyOptional<T> getItemHandlerCapability(@Nullable Direction side) {
//
//        if (side == null) {
//            return super.getItemHandlerCapability(side);
//        }
//        switch (reconfigControl.getSideConfig(side)) {
//            case SIDE_NONE:
//                return LazyOptional.empty();
//            case SIDE_INPUT:
//                return inputItemCap.cast();
//            case SIDE_OUTPUT:
//                return outputItemCap.cast();
//            default:
//                return super.getItemHandlerCapability(side);
//        }
//    }
//    // endregion
//}
