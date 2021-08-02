package cofh.thermal.core.tileentity.storage;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.lib.energy.EnergyStorageAdjustable;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.BlockHelper;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.lib.tileentity.CellTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.*;
import static cofh.thermal.core.init.TCoreReferences.ENERGY_CELL_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.ENERGY_VALIDATOR;
import static cofh.thermal.lib.common.ThermalConfig.storageAugments;

public class EnergyCellTile extends CellTileBase implements ITickableTileEntity {

    public static final int BASE_CAPACITY = 1000000;
    public static final int BASE_RECV = 1000;
    public static final int BASE_SEND = 1000;

    public EnergyCellTile() {

        super(ENERGY_CELL_TILE);

        energyStorage = new EnergyStorageAdjustable(BASE_CAPACITY, BASE_RECV, BASE_SEND).setTransferLimits(() -> amountInput, () -> amountOutput);

        amountInput = energyStorage.getMaxReceive();
        amountOutput = energyStorage.getMaxExtract();

        transferControl.initControl(false, true);

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

    @Override
    public int getLightValue() {

        return Math.min(levelTracker, 8);
    }

    protected void transferIn() {

        if (!transferControl.getTransferIn()) {
            return;
        }
        if (amountInput <= 0 || energyStorage.isFull()) {
            return;
        }
        for (int i = inputTracker; i < 6 && energyStorage.getSpace() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isInput()) {
                attemptTransferIn(Direction.byIndex(i));
            }
        }
        for (int i = 0; i < inputTracker && energyStorage.getSpace() > 0; ++i) {
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
        if (amountOutput <= 0 || energyStorage.isEmpty()) {
            return;
        }
        for (int i = outputTracker; i < 6 && energyStorage.getEnergyStored() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.byIndex(i));
            }
        }
        for (int i = 0; i < outputTracker && energyStorage.getEnergyStored() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.byIndex(i));
            }
        }
        ++outputTracker;
        outputTracker %= 6;
    }

    protected void attemptTransferIn(Direction side) {

        TileEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
        if (adjTile != null) {
            Direction opposite = side.getOpposite();
            int maxTransfer = Math.min(amountInput, energyStorage.getSpace());
            adjTile.getCapability(CapabilityEnergy.ENERGY, opposite)
                    .ifPresent(e -> {
                        if (e.canExtract()) {
                            energyStorage.modify(e.extractEnergy(maxTransfer, false));
                        }
                    });
        }
    }

    protected void attemptTransferOut(Direction side) {

        TileEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
        if (adjTile != null) {
            Direction opposite = side.getOpposite();
            int maxTransfer = Math.min(amountOutput, energyStorage.getEnergyStored());
            adjTile.getCapability(CapabilityEnergy.ENERGY, opposite)
                    .ifPresent(e -> energyStorage.modify(-e.receiveEnergy(maxTransfer, false)));
        }
    }

    @Override
    protected boolean keepEnergy() {

        return true;
    }

    @Override
    public int getMaxInput() {

        return energyStorage.getMaxReceive();
    }

    @Override
    public int getMaxOutput() {

        return energyStorage.getMaxExtract();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new EnergyCellContainer(i, world, pos, inventory, player);
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

        prevLight = getLightValue();

        int curScale = energyStorage.getEnergyStored() > 0 ? 1 + (int) (energyStorage.getRatio() * 14) : 0;
        if (curScale != compareTracker) {
            compareTracker = curScale;
            if (send) {
                markDirty();
            }
        }
        if (energyStorage.isCreative()) {
            curScale = 9;
        } else {
            curScale = energyStorage.getEnergyStored() > 0 ? 1 + Math.min((int) (energyStorage.getRatio() * 8), 7) : 0;
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

        return item -> AugmentDataHelper.hasAugmentData(item) && ENERGY_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion

    // region CAPABILITIES
    @Override
    protected void updateHandlers() {

        LazyOptional<?> prevCap = energyCap;
        energyCap = LazyOptional.of(() -> energyStorage);
        prevCap.invalidate();
    }

    @Override
    protected <T> LazyOptional<T> getEnergyCapability(@Nullable Direction side) {

        if (side == null || reconfigControl.getSideConfig(side.ordinal()) != SideConfig.SIDE_NONE) {
            return super.getEnergyCapability(side);
        }
        return LazyOptional.empty();
    }
    // endregion
}