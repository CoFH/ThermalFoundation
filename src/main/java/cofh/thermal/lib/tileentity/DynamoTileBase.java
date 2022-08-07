package cofh.thermal.lib.tileentity;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.energy.EnergyStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.util.helpers.AugmentableHelper.*;
import static cofh.lib.util.Constants.AUG_SCALE_MAX;
import static cofh.lib.util.Constants.AUG_SCALE_MIN;
import static cofh.lib.util.constants.BlockStatePropertiesCoFH.FACING_ALL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.DYNAMO_NO_FLUID_VALIDATOR;
import static cofh.thermal.lib.common.ThermalAugmentRules.DYNAMO_VALIDATOR;

public abstract class DynamoTileBase extends ThermalTileAugmentable implements ITickableTile.IServerTickable {

    protected Direction facing;

    protected int fuel;
    protected int fuelMax;
    protected int coolant;
    protected int coolantMax;

    protected int baseProcessTick = getBaseProcessTick();
    protected int processTick = baseProcessTick;
    protected int minProcessTick = processTick / 10;

    public DynamoTileBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
        energyStorage = new EnergyStorageCoFH(getBaseEnergyStorage(), 0, getBaseEnergyXfer()) {

            @Override
            public boolean canExtract() {

                return false;
            }
        };
        facing = state.getValue(FACING_ALL);
    }

    // region BASE PARAMETERS
    protected int getBaseEnergyStorage() {

        return getBaseProcessTick() * 100;
    }
    // endregion

    @Override
    public void setLevel(Level level) {

        super.setLevel(level);
        updateHandlers();
    }

    @Override
    public void setBlockState(BlockState state) {

        super.setBlockState(state);
        updateFacing();
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

        boolean curActive = isActive;
        if (isActive) {
            processTick();
            if (canProcessFinish()) {
                processFinish();
                if (!redstoneControl.getState() || !canProcessStart()) {
                    isActive = false;
                } else {
                    processStart();
                }
            }
        } else if (Utils.timeCheckQuarter()) {
            if (redstoneControl.getState() && canProcessStart()) {
                processStart();
                processTick();
                isActive = true;
            } else {
                energyStorage.modify(-minProcessTick);
            }
        }
        updateActiveState(curActive);
    }

    // region PROCESS
    protected abstract boolean canProcessStart();

    protected boolean canProcessFinish() {

        return fuel <= 0;
    }

    protected abstract void processStart();

    protected void processFinish() {

    }

    protected int processTick() {

        if (fuel <= 0) {
            return 0;
        }
        int energy = calcEnergy();
        energyStorage.modify(energy);
        fuel -= energy;
        transferRF();
        return energy;
    }

    protected int calcEnergy() {

        return Math.min(processTick, minProcessTick + (int) (processTick * (1.0D - energyStorage.getRatio())));
    }
    // endregion

    // region HELPERS
    protected void transferRF() {

        BlockEntity adjTile = BlockHelper.getAdjacentTileEntity(this, getFacing());
        if (adjTile != null) {
            Direction opposite = getFacing().getOpposite();
            int maxTransfer = Math.min(energyStorage.getMaxExtract(), energyStorage.getEnergyStored());
            adjTile.getCapability(ThermalEnergyHelper.getBaseEnergySystem(), opposite)
                    .ifPresent(e -> energyStorage.modify(-e.receiveEnergy(maxTransfer, false)));
        }
    }

    protected Direction getFacing() {

        if (facing == null) {
            updateFacing();
        }
        return facing;
    }

    protected void updateFacing() {

        facing = getBlockState().getValue(FACING_ALL);
        updateHandlers();
    }
    // endregion

    // region GUI
    @Override
    public int getCurSpeed() {

        return isActive ? calcEnergy() : 0;
    }

    @Override
    public int getMaxSpeed() {

        return baseProcessTick;
    }

    @Override
    public double getEfficiency() {

        if (getEnergyMod() <= 0) {
            return Double.MIN_VALUE;
        }
        return getEnergyMod();
    }

    @Override
    public int getScaledDuration(int scale) {

        if (fuelMax <= 0 || fuel <= 0) {
            return 0;
        }
        return scale * fuel / fuelMax;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        super.onDataPacket(net, pkt);

        this.level.getModelDataManager().requestRefresh(this);
    }

    // CONTROL
    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        this.level.getModelDataManager().requestRefresh(this);
    }

    // GUI
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(fuelMax);
        buffer.writeInt(fuel);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        fuelMax = buffer.readInt();
        fuel = buffer.readInt();
    }

    // STATE
    @Override
    public void handleStatePacket(FriendlyByteBuf buffer) {

        super.handleStatePacket(buffer);

        this.level.getModelDataManager().requestRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        fuelMax = nbt.getInt(TAG_FUEL_MAX);
        fuel = nbt.getInt(TAG_FUEL);
        coolantMax = nbt.getInt(TAG_COOLANT_MAX);
        coolant = nbt.getInt(TAG_COOLANT);
        processTick = nbt.getInt(TAG_PROCESS_TICK);

        updateHandlers();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_FUEL_MAX, fuelMax);
        nbt.putInt(TAG_FUEL, fuel);
        nbt.putInt(TAG_COOLANT_MAX, coolantMax);
        nbt.putInt(TAG_COOLANT, coolant);
        nbt.putInt(TAG_PROCESS_TICK, processTick);
    }
    // endregion

    // region AUGMENTS
    protected float energyMod = 1.0F;
    protected boolean throttleFeature = false;

    @Override
    protected Predicate<ItemStack> augValidator() {

        BiPredicate<ItemStack, List<ItemStack>> validator = tankInv.hasTanks() ? DYNAMO_VALIDATOR : DYNAMO_NO_FLUID_VALIDATOR;
        return item -> AugmentDataHelper.hasAugmentData(item) && validator.test(item, getAugmentsAsList());
    }

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        setAttribute(augmentNBT, TAG_AUGMENT_DYNAMO_POWER, 1.0F);

        energyMod = 1.0F;

        throttleFeature = false;
    }

    @Override
    protected void setAttributesFromAugment(CompoundTag augmentData) {

        super.setAttributesFromAugment(augmentData);

        setAttributeFromAugmentAdd(augmentNBT, augmentData, TAG_AUGMENT_DYNAMO_POWER);

        energyMod *= getAttributeModWithDefault(augmentData, TAG_AUGMENT_DYNAMO_ENERGY, 1.0F);

        throttleFeature |= getAttributeMod(augmentData, TAG_AUGMENT_DYNAMO_THROTTLE) > 0;
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        creativeEnergy = false;

        super.finalizeAttributes(enchantmentMap);
        float baseMod = getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_BASE_MOD, 1.0F);
        float processMod = getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_DYNAMO_POWER, 1.0F);
        float totalMod = baseMod * processMod;

        energyStorage.applyModifiers(totalMod, totalMod);

        baseProcessTick = Math.round(getBaseProcessTick() * totalMod);
        energyMod = MathHelper.clamp(energyMod, AUG_SCALE_MIN, AUG_SCALE_MAX);

        processTick = baseProcessTick;
        minProcessTick = throttleFeature ? 0 : baseProcessTick / 10;
    }

    protected final float getEnergyMod() {

        return energyMod;
    }
    // endregion

    // region CAPABILITIES
    @Override
    protected <T> LazyOptional<T> getEnergyCapability(@Nullable Direction side) {

        if (side == null || side.equals(getFacing())) {
            return super.getEnergyCapability(side);
        }
        return LazyOptional.empty();
    }

    @Override
    protected <T> LazyOptional<T> getItemHandlerCapability(@Nullable Direction side) {

        if (side != null && side.equals(getFacing())) {
            return LazyOptional.empty();
        }
        return super.getItemHandlerCapability(side);
    }

    @Override
    protected <T> LazyOptional<T> getFluidHandlerCapability(@Nullable Direction side) {

        if (side != null && side.equals(getFacing())) {
            return LazyOptional.empty();
        }
        return super.getFluidHandlerCapability(side);
    }
    // endregion
}
