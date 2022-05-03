package cofh.thermal.core.tileentity;

import cofh.lib.block.entity.ICoFHTickableTile;
import cofh.lib.energy.EnergyStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.util.StorageGroup.ACCESSIBLE;
import static cofh.lib.util.StorageGroup.INTERNAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreReferences.CHARGE_BENCH_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.storageAugments;

public class ChargeBenchTile extends ThermalTileAugmentable implements ICoFHTickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_FILTER);

    public static final int BASE_CAPACITY = 500000;
    public static final int BASE_XFER = 4000;

    protected ItemStorageCoFH[] benchSlots = new ItemStorageCoFH[9];
    protected ItemStorageCoFH chargeSlot = new ItemStorageCoFH(1, ThermalEnergyHelper::hasEnergyHandlerCap);

    public ChargeBenchTile(BlockPos pos, BlockState state) {

        super(CHARGE_BENCH_TILE, pos, state);

        energyStorage = new EnergyStorageCoFH(BASE_CAPACITY, BASE_XFER);

        for (int i = 0; i < benchSlots.length; ++i) {
            benchSlots[i] = new ItemStorageCoFH(1, (item -> filter.valid(item) && ThermalEnergyHelper.hasEnergyHandlerCap(item)));
            inventory.addSlot(benchSlots[i], ACCESSIBLE);
        }
        inventory.addSlot(chargeSlot, INTERNAL);

        addAugmentSlots(storageAugments);
        initHandlers();
    }

    @Override
    public void tickServer() {

        boolean curActive = isActive;
        isActive = false;
        if (redstoneControl().getState()) {
            chargeItems();
        }
        updateActiveState(curActive);
        chargeEnergy();
    }

    protected void chargeEnergy() {

        if (!chargeSlot.isEmpty()) {
            int maxTransfer = Math.min(energyStorage.getMaxReceive(), energyStorage.getSpace());
            chargeSlot.getItemStack().getCapability(ThermalEnergyHelper.getBaseEnergySystem(), null).ifPresent(c -> energyStorage.receiveEnergy(c.extractEnergy(maxTransfer, false), false));
        }
    }

    protected void chargeItems() {

        for (ItemStorageCoFH benchSlot : benchSlots) {
            if (!benchSlot.isEmpty()) {
                if (!energyStorage.isEmpty()) {
                    int maxTransfer = Math.min(energyStorage.getMaxExtract(), energyStorage.getEnergyStored());
                    benchSlot.getItemStack().getCapability(ThermalEnergyHelper.getBaseEnergySystem(), null).ifPresent(c -> energyStorage.extractEnergy(c.receiveEnergy(maxTransfer, false), false));
                }
                isActive = true;
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new ChargeBenchContainer(i, level, worldPosition, inventory, player);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
