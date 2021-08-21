package cofh.thermal.core.tileentity.storage;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.StorageGroup;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.thermal.core.inventory.container.storage.ItemCellContainer;
import cofh.thermal.lib.tileentity.CellTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.*;
import static cofh.thermal.core.init.TCoreReferences.ITEM_CELL_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.ITEM_STORAGE_VALIDATOR;
import static cofh.thermal.lib.common.ThermalConfig.storageAugments;

public class ItemCellTile extends CellTileBase implements ITickableTileEntity {

    public static final int BASE_CAPACITY = 10000;

    protected ItemStorageCoFH itemStorage = new ItemStorageCoFH(BASE_CAPACITY, item -> filter.valid(item));

    public ItemCellTile() {

        super(ITEM_CELL_TILE);

        amountInput = 8;
        amountOutput = 8;

        inventory.addSlot(itemStorage, StorageGroup.ACCESSIBLE);

        addAugmentSlots(storageAugments);
        initHandlers();
    }

    //    @Override
    //    public void neighborChanged(Block blockIn, BlockPos fromPos) {
    //
    //        super.neighborChanged(blockIn, fromPos);
    //
    //        // TODO: Handle caching of neighbor caps.
    //    }

    @Override
    public void tick() {

        if (redstoneControl.getState()) {
            transferOut();
            transferIn();
        }
        if (Utils.timeCheck(world)) {
            updateTrackers(true);
        }
    }

    protected void transferIn() {

        if (!transferControl.getTransferIn()) {
            return;
        }
        if (amountInput <= 0 || itemStorage.isFull()) {
            return;
        }
        for (int i = inputTracker; i < 6 && itemStorage.getSpace() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isInput()) {
                attemptTransferIn(Direction.byIndex(i));
            }
        }
        for (int i = 0; i < inputTracker && itemStorage.getSpace() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isInput()) {
                attemptTransferIn(Direction.byIndex(i));
            }
        }
        ++inputTracker;
        inputTracker %= 6;
    }

    protected void transferOut() {

        if (!transferControl.getTransferOut()) {
            return;
        }
        if (amountOutput <= 0 || itemStorage.isEmpty()) {
            return;
        }
        for (int i = outputTracker; i < 6 && itemStorage.getCount() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.byIndex(i));
            }
        }
        for (int i = 0; i < outputTracker && itemStorage.getCount() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.byIndex(i));
            }
        }
        ++outputTracker;
        outputTracker %= 6;
    }

    protected void attemptTransferIn(Direction side) {

        InventoryHelper.extractFromAdjacent(this, itemStorage, Math.min(amountInput, itemStorage.getSpace()), side);
    }

    protected void attemptTransferOut(Direction side) {

        InventoryHelper.insertIntoAdjacent(this, itemStorage, amountOutput, side);
    }

    @Override
    protected boolean keepItems() {

        return true;
    }

    @Override
    public int getMaxInput() {

        return 64;
    }

    @Override
    public int getMaxOutput() {

        return 64;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new ItemCellContainer(i, world, pos, inventory, player);
    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return new ModelDataMap.Builder()
                .withInitial(SIDES, reconfigControl().getRawSideConfig())
                .withInitial(FACING, reconfigControl.getFacing())
                .withInitial(LEVEL, levelTracker)
                .build();
    }

    @Override
    protected void updateTrackers(boolean send) {

        int curScale = itemStorage.getStored() > 0 ? 1 + (int) (itemStorage.getRatio() * 14) : 0;
        if (curScale != compareTracker) {
            compareTracker = curScale;
            if (send) {
                markDirty();
            }
        }
        if (itemStorage.isCreative()) {
            curScale = 9;
        } else {
            curScale = itemStorage.getStored() > 0 ? 1 + Math.min((int) (itemStorage.getRatio() * 8), 7) : 0;
        }
        if (levelTracker != curScale) {
            levelTracker = curScale;
            if (send) {
                TileStatePacket.sendToClient(this);
            }
        }
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && ITEM_STORAGE_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion

    // region CAPABILITIES
    @Override
    protected void updateHandlers() {

        // Optimization to prevent callback logic as contents may change rapidly.
        LazyOptional<?> prevCap = itemCap;
        itemCap = LazyOptional.of(() -> itemStorage);
        prevCap.invalidate();
    }

    @Override
    protected <T> LazyOptional<T> getItemHandlerCapability(@Nullable Direction side) {

        if (side == null || reconfigControl.getSideConfig(side.ordinal()) != SideConfig.SIDE_NONE) {
            return super.getItemHandlerCapability(side);
        }
        return LazyOptional.empty();
    }
    // endregion
}