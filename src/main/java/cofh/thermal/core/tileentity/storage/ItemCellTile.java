//package cofh.thermal.core.tileentity.storage;
//
//import cofh.core.network.packet.client.TileStatePacket;
//import cofh.lib.inventory.ItemStorageCoFH;
//import cofh.lib.util.StorageGroup;
//import cofh.lib.util.Utils;
//import cofh.lib.util.helpers.AugmentDataHelper;
//import cofh.lib.util.helpers.BlockHelper;
//import cofh.thermal.lib.tileentity.CellTileBase;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.ITickableTileEntity;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.Direction;
//import net.minecraftforge.client.model.data.IModelData;
//import net.minecraftforge.client.model.data.ModelDataMap;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.energy.CapabilityEnergy;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.function.Predicate;
//
//import static cofh.core.client.renderer.model.ModelUtils.*;
//import static cofh.thermal.core.init.TCoreReferences.ITEM_CELL_TILE;
//import static cofh.thermal.lib.common.ThermalConfig.storageAugments;
//
//public class ItemCellTile extends CellTileBase implements ITickableTileEntity {
//
//    public static final int BASE_CAPACITY = 10000;
//
//    protected ItemStorageCoFH itemStorage = new ItemStorageCoFH(BASE_CAPACITY, item -> filter.valid(item));
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
//        if (Utils.timeCheck(world)) {
//            updateTrackers(true);
//        }
//    }
//
//    protected void transferIn() {
//
//        if (!transferControl.getTransferIn()) {
//            return;
//        }
//        if (amountInput <= 0 || energyStorage.isFull()) {
//            return;
//        }
//        for (int i = inputTracker; i < 6 && energyStorage.getSpace() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isInput()) {
//                attemptExtractItems(Direction.byIndex(i));
//            }
//        }
//        for (int i = 0; i < inputTracker && energyStorage.getSpace() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isInput()) {
//                attemptExtractItems(Direction.byIndex(i));
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
//        if (amountOutput <= 0 || energyStorage.isEmpty()) {
//            return;
//        }
//        for (int i = outputTracker; i < 6 && energyStorage.getEnergyStored() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isOutput()) {
//                attemptTransferItems(Direction.byIndex(i));
//            }
//        }
//        for (int i = 0; i < outputTracker && energyStorage.getEnergyStored() > 0; ++i) {
//            if (reconfigControl.getSideConfig(i).isOutput()) {
//                attemptTransferItems(Direction.byIndex(i));
//            }
//        }
//        ++outputTracker;
//        outputTracker %= 6;
//    }
//
//    protected void attemptExtractItems(Direction side) {
//
//        TileEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
//        if (adjTile != null) {
//            Direction opposite = side.getOpposite();
//            int maxTransfer = Math.min(amountInput, energyStorage.getSpace());
//            adjTile.getCapability(CapabilityEnergy.ENERGY, opposite)
//                    .ifPresent(e -> energyStorage.modify(e.extractEnergy(maxTransfer, false)));
//        }
//    }
//
//    protected void attemptTransferItems(Direction side) {
//
//        TileEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
//        if (adjTile != null) {
//            Direction opposite = side.getOpposite();
//            int maxTransfer = Math.min(amountOutput, energyStorage.getEnergyStored());
//            adjTile.getCapability(CapabilityEnergy.ENERGY, opposite)
//                    .ifPresent(e -> energyStorage.modify(-e.receiveEnergy(maxTransfer, false)));
//        }
//    }
//
//    @Override
//    protected boolean keepItems() {
//
//        return true;
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
//    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {
//
//        return new ItemCellContainer(i, world, pos, inventory, player);
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
//        prevLight = getLightValue();
//
//        int curScale = itemStorage.getStored() > 0 ? 1 + (int) (itemStorage.getRatio() * 14) : 0;
//        if (curScale != compareTracker) {
//            compareTracker = curScale;
//            if (send) {
//                markDirty();
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
//    // region AUGMENTS
//    @Override
//    protected Predicate<ItemStack> augValidator() {
//
//        return item -> AugmentDataHelper.hasAugmentData(item) && ITEM_VALIDATOR.test(item, getAugmentsAsList());
//    }
//    // endregion
//
//    // region CAPABILITIES
//    @Override
//    protected void updateHandlers() {
//
//        LazyOptional<?> prevCap = itemCap;
//        itemCap = LazyOptional.of(() -> itemStorage);
//        prevCap.invalidate();
//    }
//
//    @Override
//    protected <T> LazyOptional<T> getItemHandlerCapability(@Nullable Direction side) {
//
//        if (side == null || reconfigControl.getSideConfig(side.ordinal()) != SideConfig.SIDE_NONE) {
//            return super.getItemHandlerCapability(side);
//        }
//        return LazyOptional.empty();
//    }
//    // endregion
//}