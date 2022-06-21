package cofh.thermal.core.block.entity.device;

import cofh.core.content.block.impl.SoilBlock;
import cofh.core.content.energy.EnergyStorageCoFH;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.IAreaEffectTile;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.content.inventory.ItemStorageCoFH;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.init.TCoreReferences;
import cofh.thermal.core.inventory.container.device.DeviceSoilInfuserContainer;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.core.util.helpers.AugmentableHelper.getAttributeModWithDefault;
import static cofh.lib.api.StorageGroup.INTERNAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DeviceSoilInfuserTile extends ThermalTileAugmentable implements ITickableTile.IServerTickable, IAreaEffectTile {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_AREA_EFFECT);

    protected static final int BASE_PROCESS_MAX = 4000;

    protected ItemStorageCoFH chargeSlot = new ItemStorageCoFH(1, ThermalEnergyHelper::hasEnergyHandlerCap);

    protected static final int RADIUS = 2;
    protected int radius = RADIUS;
    protected AABB area;

    protected int process;
    protected int processMax = BASE_PROCESS_MAX * radius * radius;
    protected int processTick = getBaseProcessTick() / 4 * radius;

    public DeviceSoilInfuserTile(BlockPos pos, BlockState state) {

        super(TCoreReferences.DEVICE_SOIL_INFUSER_TILE, pos, state);
        energyStorage = new EnergyStorageCoFH(getBaseEnergyStorage(), getBaseEnergyXfer());

        inventory.addSlot(chargeSlot, INTERNAL);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
        initHandlers();
    }

    @Override
    public void tickServer() {

        boolean curActive = isActive;
        if (isActive) {
            if (energyStorage.getEnergyStored() >= processTick) {
                process += processTick;
                energyStorage.modify(-processTick);
                if (process >= processMax) {
                    process -= processMax;
                    BlockPos.betweenClosedStream(worldPosition.offset(-radius, -1, -radius), worldPosition.offset(radius, 1, radius))
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
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceSoilInfuserContainer(i, level, worldPosition, inventory, player);
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
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(process);
        buffer.writeInt(processMax);
        buffer.writeInt(processTick);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        process = buffer.readInt();
        processMax = buffer.readInt();
        processTick = buffer.readInt();
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        process = nbt.getInt(TAG_PROCESS);
        processMax = nbt.getInt(TAG_PROCESS_MAX);
        processTick = nbt.getInt(TAG_PROCESS_TICK);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_PROCESS, process);
        nbt.putInt(TAG_PROCESS_MAX, processMax);
        nbt.putInt(TAG_PROCESS_TICK, processTick);
    }
    // endregion

    // region HELPERS
    public int getRadius() {

        return radius;
    }

    protected void chargeSoil(BlockPos blockPos) {

        BlockState state = level.getBlockState(blockPos);
        if (state.getBlock() instanceof SoilBlock) {
            SoilBlock.charge(state, level, blockPos);
        }
    }

    protected void chargeEnergy() {

        if (!chargeSlot.isEmpty()) {
            chargeSlot.getItemStack()
                    .getCapability(ThermalEnergyHelper.getBaseEnergySystem(), null)
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
    protected void setAttributesFromAugment(CompoundTag augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);
        float baseMod = getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_BASE_MOD, 1.0F);

        processMax = BASE_PROCESS_MAX * (1 + radius);
        processTick = Math.round(getBaseProcessTick() * baseMod);
        area = null;
    }
    // endregion

    // region IAreaEffectTile
    @Override
    public AABB getArea() {

        if (area == null) {
            area = new AABB(worldPosition.offset(-radius, -1, -radius), worldPosition.offset(1 + radius, 1, 1 + radius));
        }
        return area;
    }

    @Override
    public int getColor() {

        return isActive ? 0x78E86F : 0x555555;
    }
    // endregion
}
