package cofh.thermal.lib.tileentity;

import cofh.core.tileentity.TileCoFH;
import cofh.core.util.control.IReconfigurableTile;
import cofh.core.util.control.ITransferControllableTile;
import cofh.core.util.control.ReconfigControlModule;
import cofh.core.util.control.TransferControlModule;
import cofh.core.util.helpers.EnergyHelper;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.core.client.renderer.model.ModelUtils.SIDES;
import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.Constants.DIRECTIONS;
import static cofh.lib.util.constants.Constants.FACING_HORIZONTAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.helpers.BlockHelper.*;

public abstract class ReconfigurableTile4Way extends ThermalTileBase implements IReconfigurableTile, ITransferControllableTile {

    protected ItemStorageCoFH chargeSlot = new ItemStorageCoFH(1, EnergyHelper::hasEnergyHandlerCap);

    protected int inputTracker;
    protected int outputTracker;

    protected ReconfigControlModule reconfigControl = new ReconfigControlModule(this);
    protected TransferControlModule transferControl = new TransferControlModule(this);

    public ReconfigurableTile4Way(TileEntityType<?> tileEntityTypeIn) {

        super(tileEntityTypeIn);
        reconfigControl.setEnabled(() -> reconfigControlFeature);
        transferControl.setEnabled(() -> reconfigControlFeature);
    }

    @Override
    public TileCoFH worldContext(BlockState state, IBlockReader world) {

        reconfigControl.setFacing(state.get(FACING_HORIZONTAL));
        updateHandlers();

        return this;
    }

    @Override
    public void updateContainingBlockInfo() {

        super.updateContainingBlockInfo();
        updateSideCache();
    }

    // TODO: Does this need to exist?
    @Override
    public void remove() {

        super.remove();

        inputItemCap.invalidate();
        outputItemCap.invalidate();

        inputFluidCap.invalidate();
        outputFluidCap.invalidate();
    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return new ModelDataMap.Builder()
                .withInitial(SIDES, reconfigControl().getRawSideConfig())
                .withInitial(FLUID, renderFluid)
                .build();
    }

    // region HELPERS
    protected void updateSideCache() {

        Direction prevFacing = getFacing();
        Direction curFacing = getBlockState().get(FACING_HORIZONTAL);

        if (prevFacing != curFacing) {
            reconfigControl.setFacing(curFacing);

            int iPrev = prevFacing.getIndex();
            int iFace = curFacing.getIndex();
            SideConfig[] sides = new SideConfig[6];

            if (iPrev == SIDE_RIGHT[iFace]) {
                for (int i = 0; i < 6; ++i) {
                    sides[i] = reconfigControl.getSideConfig()[ROTATE_CLOCK_Y[i]];
                }
            } else if (iPrev == SIDE_LEFT[iFace]) {
                for (int i = 0; i < 6; ++i) {
                    sides[i] = reconfigControl.getSideConfig()[ROTATE_COUNTER_Y[i]];
                }
            } else if (iPrev == SIDE_OPPOSITE[iFace]) {
                for (int i = 0; i < 6; ++i) {
                    sides[i] = reconfigControl.getSideConfig()[INVERT_AROUND_Y[i]];
                }
            }
            reconfigControl.setSideConfig(sides);
        }
        updateHandlers();
    }

    protected void chargeEnergy() {

        if (!chargeSlot.isEmpty()) {
            chargeSlot.getItemStack()
                    .getCapability(CapabilityEnergy.ENERGY, null)
                    .ifPresent(c -> energyStorage.receiveEnergy(c.extractEnergy(Math.min(energyStorage.getMaxReceive(), energyStorage.getSpace()), false), false));
        }
    }

    protected void transferInput() {

        if (!transferControl.getTransferIn()) {
            return;
        }
        int newTracker = inputTracker;
        boolean updateTracker = false;

        for (int i = inputTracker + 1; i <= inputTracker + 6; ++i) {
            Direction side = DIRECTIONS[i % 6];
            if (reconfigControl.getSideConfig(side).isInput()) {
                for (ItemStorageCoFH slot : inputSlots()) {
                    if (slot.getSpace() > 0) {
                        InventoryHelper.extractFromAdjacent(this, slot, Math.min(getInputItemAmount(), slot.getSpace()), side);
                    }
                }
                for (FluidStorageCoFH tank : inputTanks()) {
                    if (tank.getSpace() > 0) {
                        FluidHelper.extractFromAdjacent(this, tank, Math.min(getInputFluidAmount(), tank.getSpace()), side);
                    }
                }
                if (!updateTracker) {
                    newTracker = side.ordinal();
                    updateTracker = true;
                }
            }
        }
        inputTracker = newTracker;
    }

    protected void transferOutput() {

        if (!transferControl.getTransferOut()) {
            return;
        }
        int newTracker = outputTracker;
        boolean updateTracker = false;

        for (int i = outputTracker + 1; i <= outputTracker + 6; ++i) {
            Direction side = DIRECTIONS[i % 6];
            if (reconfigControl.getSideConfig(side).isOutput()) {
                for (ItemStorageCoFH slot : outputSlots()) {
                    InventoryHelper.insertIntoAdjacent(this, slot, getOutputItemAmount(), side);
                }
                for (FluidStorageCoFH tank : outputTanks()) {
                    FluidHelper.insertIntoAdjacent(this, tank, getOutputFluidAmount(), side);
                }
                if (!updateTracker) {
                    newTracker = side.ordinal();
                    updateTracker = true;
                }
            }
        }
        outputTracker = newTracker;
    }

    protected int getInputItemAmount() {

        return 64;
    }

    protected int getOutputItemAmount() {

        return 64;
    }

    protected int getInputFluidAmount() {

        return 1000;
    }

    protected int getOutputFluidAmount() {

        return 1000;
    }

    @Override
    public void neighborChanged(Block blockIn, BlockPos fromPos) {

        super.neighborChanged(blockIn, fromPos);
        // TODO: Handle caching of neighbor caps?
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONTROL
    @Override
    public PacketBuffer getControlPacket(PacketBuffer buffer) {

        super.getControlPacket(buffer);

        reconfigControl.writeToBuffer(buffer);
        transferControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public void handleControlPacket(PacketBuffer buffer) {

        super.handleControlPacket(buffer);

        reconfigControl.readFromBuffer(buffer);
        transferControl.readFromBuffer(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // STATE
    @Override
    public void handleStatePacket(PacketBuffer buffer) {

        super.handleStatePacket(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        reconfigControl.setFacing(Direction.byIndex(nbt.getByte(TAG_FACING)));
        reconfigControl.read(nbt);
        transferControl.read(nbt);

        inputTracker = nbt.getInt(TAG_TRACK_IN);
        outputTracker = nbt.getInt(TAG_TRACK_OUT);

        updateHandlers();
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putByte(TAG_FACING, (byte) reconfigControl.getFacing().getIndex());
        reconfigControl.write(nbt);
        transferControl.write(nbt);

        nbt.putInt(TAG_TRACK_IN, inputTracker);
        nbt.putInt(TAG_TRACK_OUT, outputTracker);

        return nbt;
    }
    // endregion

    // region AUGMENTS
    protected boolean reconfigControlFeature = defaultReconfigState();

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        reconfigControlFeature = defaultReconfigState();
    }

    @Override
    protected void setAttributesFromAugment(CompoundNBT augmentData) {

        super.setAttributesFromAugment(augmentData);

        reconfigControlFeature |= getAttributeMod(augmentData, TAG_AUGMENT_FEATURE_SIDE_CONFIG) > 0;
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);

        if (!reconfigControlFeature) {
            transferControl.disable();
            reconfigControl.disable();
        }
    }
    // endregion

    // region MODULES
    @Override
    public ReconfigControlModule reconfigControl() {

        return reconfigControl;
    }

    @Override
    public TransferControlModule transferControl() {

        return transferControl;
    }
    // endregion

    // region CAPABILITIES
    protected LazyOptional<?> inputItemCap = LazyOptional.empty();
    protected LazyOptional<?> outputItemCap = LazyOptional.empty();

    protected LazyOptional<?> inputFluidCap = LazyOptional.empty();
    protected LazyOptional<?> outputFluidCap = LazyOptional.empty();

    protected void updateHandlers() {

        super.updateHandlers();

        // ITEMS
        LazyOptional<?> prevItemInputCap = inputItemCap;
        LazyOptional<?> prevItemOutputCap = outputItemCap;

        IItemHandler inputInvHandler = inventory.getHandler(INPUT);
        IItemHandler outputInvHandler = inventory.getHandler(OUTPUT);

        inputItemCap = inventory.hasInputSlots() ? LazyOptional.of(() -> inputInvHandler) : LazyOptional.empty();
        outputItemCap = inventory.hasOutputSlots() ? LazyOptional.of(() -> outputInvHandler) : LazyOptional.empty();

        prevItemInputCap.invalidate();
        prevItemOutputCap.invalidate();

        // FLUID
        LazyOptional<?> prevFluidInputCap = inputFluidCap;
        LazyOptional<?> prevFluidOutputCap = outputFluidCap;

        IFluidHandler inputFluidHandler = tankInv.getHandler(INPUT);
        IFluidHandler outputFluidHandler = tankInv.getHandler(OUTPUT);

        inputFluidCap = tankInv.hasInputTanks() ? LazyOptional.of(() -> inputFluidHandler) : LazyOptional.empty();
        outputFluidCap = tankInv.hasOutputTanks() ? LazyOptional.of(() -> outputFluidHandler) : LazyOptional.empty();

        prevFluidInputCap.invalidate();
        prevFluidOutputCap.invalidate();
    }

    @Override
    protected <T> LazyOptional<T> getItemHandlerCapability(@Nullable Direction side) {

        if (side == null) {
            return super.getItemHandlerCapability(side);
        }
        switch (reconfigControl.getSideConfig(side)) {
            case SIDE_NONE:
                return LazyOptional.empty();
            case SIDE_INPUT:
                return inputItemCap.cast();
            case SIDE_OUTPUT:
                return outputItemCap.cast();
            default:
                return super.getItemHandlerCapability(side);
        }
    }

    @Override
    protected <T> LazyOptional<T> getFluidHandlerCapability(@Nullable Direction side) {

        if (side == null) {
            return super.getFluidHandlerCapability(side);
        }
        switch (reconfigControl.getSideConfig(side)) {
            case SIDE_NONE:
                return LazyOptional.empty();
            case SIDE_INPUT:
                return inputFluidCap.cast();
            case SIDE_OUTPUT:
                return outputFluidCap.cast();
            default:
                return super.getFluidHandlerCapability(side);
        }
    }
    // endregion

    // region IConveyableData
    @Override
    public void readConveyableData(PlayerEntity player, CompoundNBT tag) {

        reconfigControl.readSettings(tag);
        transferControl.readSettings(tag);

        super.readConveyableData(player, tag);
    }

    @Override
    public void writeConveyableData(PlayerEntity player, CompoundNBT tag) {

        reconfigControl.writeSettings(tag);
        transferControl.writeSettings(tag);

        super.writeConveyableData(player, tag);
    }
    // endregion
}
