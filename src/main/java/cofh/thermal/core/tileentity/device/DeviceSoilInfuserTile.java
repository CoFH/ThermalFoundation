package cofh.thermal.core.tileentity.device;

import cofh.core.util.helpers.EnergyHelper;
import cofh.lib.block.impl.SoilBlock;
import cofh.lib.energy.EnergyStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.TimeTracker;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.init.TCoreReferences;
import cofh.thermal.core.inventory.container.device.DeviceSoilInfuserContainer;
import cofh.thermal.lib.tileentity.ThermalTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.util.StorageGroup.INTERNAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeModWithDefault;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DeviceSoilInfuserTile extends ThermalTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_AREA_EFFECT);

    protected static final int BASE_PROCESS_MAX = 4000;

    protected ItemStorageCoFH chargeSlot = new ItemStorageCoFH(1, EnergyHelper::hasEnergyHandlerCap);

    protected static final int RADIUS = 2;
    protected int radius = RADIUS;

    protected int process;
    protected int processMax = BASE_PROCESS_MAX * radius * radius;
    protected int processTick = getBaseProcessTick() / 4 * radius;

    public DeviceSoilInfuserTile() {

        super(TCoreReferences.DEVICE_SOIL_INFUSER_TILE);
        timeTracker = new TimeTracker();
        energyStorage = new EnergyStorageCoFH(getBaseEnergyStorage(), getBaseEnergyXfer());

        inventory.addSlot(chargeSlot, INTERNAL);

        addAugmentSlots(deviceAugments);
        initHandlers();
    }

    @Override
    public void tick() {

        boolean curActive = isActive;
        if (isActive) {
            if (energyStorage.getEnergyStored() >= processTick) {
                process += processTick;
                energyStorage.modify(-processTick);
                if (process >= processMax) {
                    process -= processMax;
                    BlockPos.getAllInBox(pos.add(-radius, -1, -radius), pos.add(radius, 1, radius))
                            .forEach(this::chargeSoil);
                }
            } else {
                isActive = false;
            }
        } else if (redstoneControl.getState() && energyStorage.getEnergyStored() >= processTick) {
            isActive = true;
        }
        updateActiveState(curActive);
        chargeEnergy();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceSoilInfuserContainer(i, world, pos, inventory, player);
    }

    // region GUI
    @Override
    public int getCurSpeed() {

        return isActive ? processTick : 0;
    }

    @Override
    public int getScaledProgress(int scale) {

        if (!isActive || processMax <= 0 || process <= 0) {
            return 0;
        }
        return scale * Math.min(process, processMax) / processMax;
    }
    // endregion

    // region NETWORK
    @Override
    public PacketBuffer getGuiPacket(PacketBuffer buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(process);
        buffer.writeInt(processMax);
        buffer.writeInt(processTick);

        return buffer;
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        process = buffer.readInt();
        processMax = buffer.readInt();
        processTick = buffer.readInt();
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        process = nbt.getInt(TAG_PROCESS);
        processMax = nbt.getInt(TAG_PROCESS_MAX);
        processTick = nbt.getInt(TAG_PROCESS_TICK);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putInt(TAG_PROCESS, process);
        nbt.putInt(TAG_PROCESS_MAX, processMax);
        nbt.putInt(TAG_PROCESS_TICK, processTick);

        return nbt;
    }
    // endregion

    // region HELPERS
    public int getRadius() {

        return radius;
    }

    protected void chargeSoil(BlockPos blockPos) {

        BlockState state = world.getBlockState(blockPos);
        if (state.getBlock() instanceof SoilBlock) {
            SoilBlock.charge(state, world, blockPos);
        }
    }

    protected void chargeEnergy() {

        if (!chargeSlot.isEmpty()) {
            chargeSlot.getItemStack()
                    .getCapability(CapabilityEnergy.ENERGY, null)
                    .ifPresent(c -> energyStorage.receiveEnergy(c.extractEnergy(Math.min(energyStorage.getMaxReceive(), energyStorage.getSpace()), false), false));
        }
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        radius = RADIUS;
    }

    @Override
    protected void setAttributesFromAugment(CompoundNBT augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);
        float baseMod = getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_BASE_MOD, 1.0F);

        processMax = BASE_PROCESS_MAX * (1 + radius);
        processTick = Math.round(getBaseProcessTick() * baseMod);
    }
    // endregion
}
