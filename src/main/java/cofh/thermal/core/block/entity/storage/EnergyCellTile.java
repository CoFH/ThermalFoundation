package cofh.thermal.core.block.entity.storage;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.energy.EnergyHandlerRestrictionWrapper;
import cofh.lib.energy.EnergyStorageRestrictable;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.BlockHelper;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.lib.block.entity.CellBlockEntity;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.*;
import static cofh.thermal.core.init.TCoreTileEntities.ENERGY_CELL_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.ENERGY_STORAGE_VALIDATOR;

public class EnergyCellTile extends CellBlockEntity implements ITickableTile.IServerTickable {

    public static final int BASE_CAPACITY = 1000000;
    public static final int BASE_RECV = 1000;
    public static final int BASE_SEND = 1000;

    public EnergyCellTile(BlockPos pos, BlockState state) {

        super(ENERGY_CELL_TILE.get(), pos, state);

        energyStorage = new EnergyStorageRestrictable(BASE_CAPACITY, BASE_RECV, BASE_SEND).setTransferLimits(() -> amountInput, () -> amountOutput);

        amountInput = energyStorage.getMaxReceive();
        amountOutput = energyStorage.getMaxExtract();

        transferControl.initControl(false, true);

        addAugmentSlots(ThermalCoreConfig.storageAugments);
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
    public void tickServer() {

        if (redstoneControl.getState()) {
            transferOut();
            transferIn();
        }
        if (Utils.timeCheck()) {
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
                attemptTransferIn(Direction.from3DDataValue(i));
            }
        }
        for (int i = 0; i < inputTracker && energyStorage.getSpace() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isInput()) {
                attemptTransferIn(Direction.from3DDataValue(i));
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
                attemptTransferOut(Direction.from3DDataValue(i));
            }
        }
        for (int i = 0; i < outputTracker && energyStorage.getEnergyStored() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.from3DDataValue(i));
            }
        }
        ++outputTracker;
        outputTracker %= 6;
    }

    protected void attemptTransferIn(Direction side) {

        BlockEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
        if (adjTile != null) {
            Direction opposite = side.getOpposite();
            int maxTransfer = Math.min(amountInput, energyStorage.getSpace());
            adjTile.getCapability(ThermalEnergyHelper.getBaseEnergySystem(), opposite)
                    .ifPresent(e -> {
                        if (e.canExtract()) {
                            energyStorage.modify(e.extractEnergy(maxTransfer, false));
                        }
                    });
        }
    }

    protected void attemptTransferOut(Direction side) {

        BlockEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
        if (adjTile != null) {
            Direction opposite = side.getOpposite();
            int maxTransfer = Math.min(amountOutput, energyStorage.getEnergyStored());
            adjTile.getCapability(ThermalEnergyHelper.getBaseEnergySystem(), opposite)
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
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new EnergyCellContainer(i, level, worldPosition, inventory, player);
    }

    // TODO: Does this need to exist?
    @Override
    public void setRemoved() {

        super.setRemoved();

        inputEnergyCap.invalidate();
        outputEnergyCap.invalidate();
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
                setChanged();
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

        return item -> AugmentDataHelper.hasAugmentData(item) && ENERGY_STORAGE_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion

    // region CAPABILITIES
    protected LazyOptional<?> inputEnergyCap = LazyOptional.empty();
    protected LazyOptional<?> outputEnergyCap = LazyOptional.empty();

    @Override
    protected void updateHandlers() {

        // Optimization to prevent callback logic as contents may change rapidly.
        LazyOptional<?> prevEnergyCap = energyCap;
        LazyOptional<?> prevEnergyInputCap = inputEnergyCap;
        LazyOptional<?> prevEnergyOutputCap = outputEnergyCap;

        IEnergyStorage inputHandler = new EnergyHandlerRestrictionWrapper(energyStorage, true, false);
        IEnergyStorage outputHandler = new EnergyHandlerRestrictionWrapper(energyStorage, false, true);

        energyCap = LazyOptional.of(() -> energyStorage);
        inputEnergyCap = LazyOptional.of(() -> inputHandler);
        outputEnergyCap = LazyOptional.of(() -> outputHandler);

        prevEnergyCap.invalidate();
        prevEnergyInputCap.invalidate();
        prevEnergyOutputCap.invalidate();
    }

    @Override
    protected <T> LazyOptional<T> getEnergyCapability(@Nullable Direction side) {

        if (side == null) {
            return super.getEnergyCapability(side);
        }
        return switch (reconfigControl.getSideConfig(side)) {
            case SIDE_NONE -> LazyOptional.empty();
            case SIDE_INPUT -> inputEnergyCap.cast();
            case SIDE_OUTPUT -> outputEnergyCap.cast();
            default -> super.getEnergyCapability(side);
        };
    }
    // endregion
}
