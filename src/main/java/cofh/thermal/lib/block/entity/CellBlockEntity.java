package cofh.thermal.lib.block.entity;

import cofh.core.util.control.*;
import cofh.lib.util.helpers.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;

import java.util.Map;

import static cofh.lib.util.constants.BlockStatePropertiesCoFH.FACING_HORIZONTAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.BlockHelper.*;

public abstract class CellBlockEntity extends AugmentableBlockEntity implements IReconfigurableTile, ITransferControllableTile {

    protected int compareTracker;
    protected int levelTracker;

    protected int inputTracker;
    protected int outputTracker;

    public int amountInput;
    public int amountOutput;

    protected int prevLight;

    protected ReconfigControlModule reconfigControl = new ReconfigControlModuleLimited(this);
    protected TransferControlModule transferControl = new TransferControlModule(this);

    public CellBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);

        reconfigControl.setFacing(state.getValue(FACING_HORIZONTAL));
    }

    @Override
    public int getComparatorInputOverride() {

        return compareTracker;
    }

    @Override
    public void setLevel(Level level) {

        super.setLevel(level);
        updateHandlers();
    }

    @Override
    public void setBlockState(BlockState state) {

        super.setBlockState(state);
        updateSideCache();
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        CompoundTag nbt = stack.getOrCreateTagElement(TAG_BLOCK_ENTITY);

        nbt.putInt(TAG_AMOUNT_IN, amountInput);
        nbt.putInt(TAG_AMOUNT_OUT, amountOutput);

        return super.createItemStackTag(stack);
    }

    public abstract int getMaxInput();

    public abstract int getMaxOutput();

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

    protected abstract void updateTrackers(boolean send);

    public int getLevelTracker() {

        return levelTracker;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        super.onDataPacket(net, pkt);

        level.getChunkSource().getLightEngine().checkBlock(worldPosition);
        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONFIG
    @Override
    public FriendlyByteBuf getConfigPacket(FriendlyByteBuf buffer) {

        super.getConfigPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

        return buffer;
    }

    @Override
    public void handleConfigPacket(FriendlyByteBuf buffer) {

        super.handleConfigPacket(buffer);

        amountInput = MathHelper.clamp(buffer.readInt(), 0, getMaxInput());
        amountOutput = MathHelper.clamp(buffer.readInt(), 0, getMaxOutput());
    }

    // CONTROL
    @Override
    public FriendlyByteBuf getControlPacket(FriendlyByteBuf buffer) {

        super.getControlPacket(buffer);

        reconfigControl.writeToBuffer(buffer);
        transferControl.writeToBuffer(buffer);

        buffer.writeInt(compareTracker);
        buffer.writeInt(levelTracker);

        return buffer;
    }

    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        reconfigControl.readFromBuffer(buffer);
        transferControl.readFromBuffer(buffer);

        compareTracker = buffer.readInt();
        levelTracker = buffer.readInt();

        ModelDataManager.requestModelDataRefresh(this);
    }

    // GUI
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        amountInput = buffer.readInt();
        amountOutput = buffer.readInt();
    }

    // STATE
    @Override
    public FriendlyByteBuf getStatePacket(FriendlyByteBuf buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(compareTracker);
        buffer.writeInt(levelTracker);
        buffer.writeInt(prevLight);

        return buffer;
    }

    @Override
    public void handleStatePacket(FriendlyByteBuf buffer) {

        super.handleStatePacket(buffer);

        compareTracker = buffer.readInt();
        levelTracker = buffer.readInt();
        prevLight = buffer.readInt();

        if (prevLight != getLightValue()) {
            level.getChunkSource().getLightEngine().checkBlock(worldPosition);
        }
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

        amountInput = nbt.getInt(TAG_AMOUNT_IN);
        amountOutput = nbt.getInt(TAG_AMOUNT_OUT);

        updateTrackers(false);
        updateHandlers();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putByte(TAG_FACING, (byte) reconfigControl.getFacing().get3DDataValue());
        reconfigControl.write(nbt);
        transferControl.write(nbt);

        nbt.putInt(TAG_AMOUNT_IN, amountInput);
        nbt.putInt(TAG_AMOUNT_OUT, amountOutput);
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
