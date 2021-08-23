package cofh.thermal.lib.tileentity;

import cofh.core.tileentity.TileCoFH;
import cofh.core.util.control.*;
import cofh.lib.util.helpers.MathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.ModelDataManager;

import java.util.Map;

import static cofh.lib.util.constants.Constants.FACING_HORIZONTAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.BlockHelper.*;

public abstract class CellTileBase extends ThermalTileAugmentable implements IReconfigurableTile, ITransferControllableTile {

    protected int compareTracker;
    protected int levelTracker;

    protected int inputTracker;
    protected int outputTracker;

    public int amountInput;
    public int amountOutput;

    protected int prevLight;

    protected ReconfigControlModule reconfigControl = new ReconfigControlModuleLimited(this);
    protected TransferControlModule transferControl = new TransferControlModule(this);

    public CellTileBase(TileEntityType<?> tileEntityTypeIn) {

        super(tileEntityTypeIn);
    }

    @Override
    public TileCoFH worldContext(BlockState state, IBlockReader world) {

        reconfigControl.setFacing(state.get(FACING_HORIZONTAL));
        updateHandlers();

        return this;
    }

    @Override
    public int getComparatorInputOverride() {

        return compareTracker;
    }

    @Override
    public void updateContainingBlockInfo() {

        super.updateContainingBlockInfo();
        updateSideCache();
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        CompoundNBT nbt = stack.getOrCreateChildTag(TAG_BLOCK_ENTITY);

        nbt.putInt(TAG_AMOUNT_IN, amountInput);
        nbt.putInt(TAG_AMOUNT_OUT, amountOutput);

        return super.createItemStackTag(stack);
    }

    public abstract int getMaxInput();

    public abstract int getMaxOutput();

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

    protected abstract void updateTrackers(boolean send);

    public int getLevelTracker() {

        return levelTracker;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

        super.onDataPacket(net, pkt);

        world.getChunkProvider().getLightManager().checkBlock(pos);
        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONFIG
    @Override
    public PacketBuffer getConfigPacket(PacketBuffer buffer) {

        super.getConfigPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

        return buffer;
    }

    @Override
    public void handleConfigPacket(PacketBuffer buffer) {

        super.handleConfigPacket(buffer);

        amountInput = MathHelper.clamp(buffer.readInt(), 0, getMaxInput());
        amountOutput = MathHelper.clamp(buffer.readInt(), 0, getMaxOutput());
    }

    // CONTROL
    @Override
    public PacketBuffer getControlPacket(PacketBuffer buffer) {

        super.getControlPacket(buffer);

        reconfigControl.writeToBuffer(buffer);
        transferControl.writeToBuffer(buffer);

        buffer.writeInt(compareTracker);
        buffer.writeInt(levelTracker);

        return buffer;
    }

    @Override
    public void handleControlPacket(PacketBuffer buffer) {

        super.handleControlPacket(buffer);

        reconfigControl.readFromBuffer(buffer);
        transferControl.readFromBuffer(buffer);

        compareTracker = buffer.readInt();
        levelTracker = buffer.readInt();

        ModelDataManager.requestModelDataRefresh(this);
    }

    // GUI
    @Override
    public PacketBuffer getGuiPacket(PacketBuffer buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

        return buffer;
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        amountInput = buffer.readInt();
        amountOutput = buffer.readInt();
    }

    // STATE
    @Override
    public PacketBuffer getStatePacket(PacketBuffer buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(compareTracker);
        buffer.writeInt(levelTracker);
        buffer.writeInt(prevLight);

        return buffer;
    }

    @Override
    public void handleStatePacket(PacketBuffer buffer) {

        super.handleStatePacket(buffer);

        compareTracker = buffer.readInt();
        levelTracker = buffer.readInt();
        prevLight = buffer.readInt();

        if (prevLight != getLightValue()) {
            world.getChunkProvider().getLightManager().checkBlock(pos);
        }
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

        amountInput = nbt.getInt(TAG_AMOUNT_IN);
        amountOutput = nbt.getInt(TAG_AMOUNT_OUT);

        updateTrackers(false);
        updateHandlers();
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putByte(TAG_FACING, (byte) reconfigControl.getFacing().getIndex());
        reconfigControl.write(nbt);
        transferControl.write(nbt);

        nbt.putInt(TAG_AMOUNT_IN, amountInput);
        nbt.putInt(TAG_AMOUNT_OUT, amountOutput);

        return nbt;
    }
    // endregion

    // region AUGMENTS
    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);

        amountInput = MathHelper.clamp(amountInput, 0, getMaxInput());
        amountOutput = MathHelper.clamp(amountOutput, 0, getMaxOutput());
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

    // region ITileCallback
    @Override
    public void onControlUpdate() {

        updateTrackers(false);
        super.onControlUpdate();
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