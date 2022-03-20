package cofh.thermal.lib.tileentity;

import cofh.core.util.control.IReconfigurableTile;
import cofh.core.util.control.ITransferControllableTile;
import cofh.core.util.control.ReconfigControlModule;
import cofh.core.util.control.TransferControlModule;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.core.client.renderer.model.ModelUtils.SIDES;
import static cofh.lib.util.StorageGroup.*;
import static cofh.lib.util.constants.Constants.DIRECTIONS;
import static cofh.lib.util.constants.Constants.FACING_HORIZONTAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.helpers.BlockHelper.*;

public abstract class ReconfigurableTile4Way extends ThermalTileAugmentable implements IReconfigurableTile, ITransferControllableTile, IThermalInventory {

    protected int inputTracker;
    protected int outputTracker;

    protected ReconfigControlModule reconfigControl = new ReconfigControlModule(this);
    protected TransferControlModule transferControl = new TransferControlModule(this);

    public ReconfigurableTile4Way(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
        reconfigControl.setEnabled(() -> reconfigControlFeature);
        transferControl.setEnabled(() -> reconfigControlFeature);

        reconfigControl.setFacing(state.getValue(FACING_HORIZONTAL));
        updateHandlers();
    }

    @Override
    public void setBlockState(BlockState state) {

        super.setBlockState(state);
        updateSideCache();
    }

    // TODO: Does this need to exist?
    @Override
    public void setRemoved() {

        super.setRemoved();

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

    @Override
    public List<? extends ItemStorageCoFH> inputSlots() {

        return inventory.getInputSlots();
    }

    @Override
    public List<? extends FluidStorageCoFH> inputTanks() {

        return tankInv.getInputTanks();
    }

    protected List<? extends ItemStorageCoFH> outputSlots() {

        return inventory.getOutputSlots();
    }

    protected List<? extends FluidStorageCoFH> outputTanks() {

        return tankInv.getOutputTanks();
    }

    // region HELPERS
    protected void updateSideCache() {

        Direction prevFacing = getFacing();
        Direction curFacing = getBlockState().getValue(FACING_HORIZONTAL);

        if (prevFacing != curFacing) {
            reconfigControl.setFacing(curFacing);

            int iPrev = prevFacing.get3DDataValue();
            int iFace = curFacing.get3DDataValue();
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONTROL
    @Override
    public FriendlyByteBuf getControlPacket(FriendlyByteBuf buffer) {

        super.getControlPacket(buffer);

        reconfigControl.writeToBuffer(buffer);
        transferControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        reconfigControl.readFromBuffer(buffer);
        transferControl.readFromBuffer(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // STATE
    @Override
    public void handleStatePacket(FriendlyByteBuf buffer) {

        super.handleStatePacket(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        reconfigControl.setFacing(Direction.from3DDataValue(nbt.getByte(TAG_FACING)));
        reconfigControl.read(nbt);
        transferControl.read(nbt);

        inputTracker = nbt.getInt(TAG_TRACK_IN);
        outputTracker = nbt.getInt(TAG_TRACK_OUT);

        updateHandlers();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putByte(TAG_FACING, (byte) reconfigControl.getFacing().get3DDataValue());
        reconfigControl.write(nbt);
        transferControl.write(nbt);

        nbt.putInt(TAG_TRACK_IN, inputTracker);
        nbt.putInt(TAG_TRACK_OUT, outputTracker);
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
    protected void setAttributesFromAugment(CompoundTag augmentData) {

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
    protected LazyOptional<?> ioItemCap = LazyOptional.empty();

    protected LazyOptional<?> inputFluidCap = LazyOptional.empty();
    protected LazyOptional<?> outputFluidCap = LazyOptional.empty();
    protected LazyOptional<?> ioFluidCap = LazyOptional.empty();

    protected void updateHandlers() {

        super.updateHandlers();

        // ITEMS
        LazyOptional<?> prevItemInputCap = inputItemCap;
        LazyOptional<?> prevItemOutputCap = outputItemCap;
        LazyOptional<?> prevItemIOCap = ioItemCap;

        IItemHandler inputInvHandler = inventory.getHandler(INPUT);
        IItemHandler outputInvHandler = inventory.getHandler(OUTPUT);
        IItemHandler ioInvHandler = inventory.getHandler(INPUT_OUTPUT);

        inputItemCap = inventory.hasInputSlots() ? LazyOptional.of(() -> inputInvHandler) : LazyOptional.empty();
        outputItemCap = inventory.hasOutputSlots() ? LazyOptional.of(() -> outputInvHandler) : LazyOptional.empty();
        ioItemCap = inventory.hasAccessibleSlots() ? LazyOptional.of(() -> ioInvHandler) : LazyOptional.empty();

        prevItemInputCap.invalidate();
        prevItemOutputCap.invalidate();
        prevItemIOCap.invalidate();

        // FLUID
        LazyOptional<?> prevFluidInputCap = inputFluidCap;
        LazyOptional<?> prevFluidOutputCap = outputFluidCap;
        LazyOptional<?> prevFluidIOCap = ioFluidCap;

        IFluidHandler inputFluidHandler = tankInv.getHandler(INPUT);
        IFluidHandler outputFluidHandler = tankInv.getHandler(OUTPUT);
        IFluidHandler ioFluidHandler = tankInv.getHandler(INPUT_OUTPUT);

        inputFluidCap = tankInv.hasInputTanks() ? LazyOptional.of(() -> inputFluidHandler) : LazyOptional.empty();
        outputFluidCap = tankInv.hasOutputTanks() ? LazyOptional.of(() -> outputFluidHandler) : LazyOptional.empty();
        ioFluidCap = tankInv.hasAccessibleTanks() ? LazyOptional.of(() -> ioFluidHandler) : LazyOptional.empty();

        prevFluidInputCap.invalidate();
        prevFluidOutputCap.invalidate();
        prevFluidIOCap.invalidate();
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
            case SIDE_BOTH:
                return ioItemCap.cast();
            case SIDE_ACCESSIBLE:
                if (!reconfigControl.isReconfigurable()) {
                    return ioItemCap.cast();
                }
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
            case SIDE_BOTH:
                return ioFluidCap.cast();
            case SIDE_ACCESSIBLE:
                if (!reconfigControl.isReconfigurable()) {
                    return ioFluidCap.cast();
                }
            default:
                return super.getFluidHandlerCapability(side);
        }
    }
    // endregion

    // region IConveyableData
    @Override
    public void readConveyableData(Player player, CompoundTag tag) {

        reconfigControl.readSettings(tag);
        transferControl.readSettings(tag);

        super.readConveyableData(player, tag);
    }

    @Override
    public void writeConveyableData(Player player, CompoundTag tag) {

        reconfigControl.writeSettings(tag);
        transferControl.writeSettings(tag);

        super.writeConveyableData(player, tag);
    }
    // endregion
}
