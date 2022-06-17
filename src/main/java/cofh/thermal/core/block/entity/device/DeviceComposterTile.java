package cofh.thermal.core.block.entity.device;

import cofh.lib.block.entity.ICoFHTickableTile;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceComposterContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static cofh.lib.util.StorageGroup.*;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.block.device.TileBlockComposter.LEVEL;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_COMPOSTER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES;

public class DeviceComposterTile extends DeviceTileBase implements ICoFHTickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FILTER);

    protected static int timeConstant = 120;
    protected static boolean particles = true;

    protected static final Supplier<ItemStack> COMPOST = () -> new ItemStack(ITEMS.get("compost"), 0);
    protected ItemStorageCoFH outputSlot = new ItemStorageCoFH(e -> false).setEmptyItem(COMPOST);

    protected boolean hasInputsCache;
    protected float compostLevel;
    protected static final float COMPOST_LEVEL_MAX = 8.0F;

    protected int processMax = timeConstant;
    protected int process = processMax;

    public static void setTimeConstant(int configConstant) {

        timeConstant = configConstant;
    }

    public static void setParticles(boolean configConstant) {

        particles = configConstant;
    }

    public DeviceComposterTile(BlockPos pos, BlockState state) {

        super(DEVICE_COMPOSTER_TILE, pos, state);

        inventory.addSlots(INPUT, 9, item -> filter.valid(item) && COMPOSTABLES.containsKey(item.getItem()));
        inventory.addSlot(outputSlot, OUTPUT);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
        initHandlers();
    }

    @Override
    public void tickServer() {

        updateActiveState();

        if (Utils.timeCheck()) {
            updateTrackers();
        }
        if (!isActive) {
            return;
        }
        --process;
        if (process > 0) {
            return;
        }
        processMax = timeConstant / (int) baseMod;
        process = processMax;
        if (!outputSlot.isFull()) {
            for (ItemStorageCoFH slot : inventory.getInputSlots()) {
                if (COMPOSTABLES.containsKey(slot.getItemStack().getItem())) {
                    compostLevel += COMPOSTABLES.getFloat(slot.getItemStack().getItem());
                    slot.consume(1);
                }
            }
            hasInputsCache = hasInputs();
        }
        while (compostLevel >= COMPOST_LEVEL_MAX && !outputSlot.isFull()) {
            compostLevel -= COMPOST_LEVEL_MAX;
            outputSlot.modify(1);
            if (particles) {
                ((ServerLevel) level).sendParticles(ParticleTypes.COMPOSTER, worldPosition.getX() + 0.5, worldPosition.getY() + 1.5, worldPosition.getZ() + 0.5, 4, 0.1D, 0.0D, 0.1D, 0.02D);
            }
        }
    }

    @Override
    protected boolean isValid() {

        return hasInputsCache;
    }

    protected void updateTrackers() {

        int scaledOutput = 8 * outputSlot.getCount() / outputSlot.getCapacity();
        if (level != null && scaledOutput != this.getBlockState().getValue(LEVEL)) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(LEVEL, scaledOutput));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceComposterContainer(i, level, worldPosition, inventory, player);
    }

    // region GUI
    @Override
    public int getScaledProgress(int scale) {

        if (!isActive || processMax <= 0 || outputSlot.isFull() || !hasInputsCache) {
            return 0;
        }
        return scale * (processMax - process) / processMax;
    }

    protected boolean hasInputs() {

        for (ItemStorageCoFH slot : inventory.getInputSlots()) {
            if (!slot.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    // endregion

    // region NETWORK
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(process);
        buffer.writeInt(processMax);
        buffer.writeBoolean(hasInputsCache);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        process = buffer.readInt();
        processMax = buffer.readInt();
        hasInputsCache = buffer.readBoolean();
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        process = nbt.getInt(TAG_PROCESS);
        processMax = nbt.getInt(TAG_PROCESS_MAX);

        hasInputsCache = hasInputs();
        compostLevel = nbt.getFloat("Compost");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_PROCESS, process);
        nbt.putInt(TAG_PROCESS_MAX, processMax);

        nbt.putFloat("Compost", compostLevel);
    }
    // endregion

    // region ITileCallback
    @Override
    public void onInventoryChanged(int slot) {

        if (level != null && Utils.isServerWorld(level) && slot <= 8) {
            hasInputsCache = hasInputs();
            if (!hasInputsCache) {
                processMax = timeConstant / (int) baseMod;
                process = processMax;
            }
        }
        super.onInventoryChanged(slot);
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion

    // region CAPABILITIES
    @Override
    protected void updateHandlers() {

        LazyOptional<?> prevItemCap = itemCap;
        IItemHandler invHandler = inventory.getHandler(INPUT_OUTPUT);
        itemCap = inventory.hasAccessibleSlots() ? LazyOptional.of(() -> invHandler) : LazyOptional.empty();
        prevItemCap.invalidate();
    }

    @Override
    protected <T> LazyOptional<T> getItemHandlerCapability(@javax.annotation.Nullable Direction side) {

        if (!itemCap.isPresent() && inventory.hasAccessibleSlots()) {
            IItemHandler handler = inventory.getHandler(INPUT_OUTPUT);
            itemCap = LazyOptional.of(() -> handler);
        }
        return itemCap.cast();
    }
    // endregion
}
