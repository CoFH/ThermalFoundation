package cofh.thermal.core.tileentity;

import cofh.core.util.helpers.EnergyHelper;
import cofh.lib.energy.EnergyStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.ChargeBenchContainer;
import cofh.thermal.lib.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;

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

public class ChargeBenchTile extends ThermalTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_FILTER);

    public static final int BASE_CAPACITY = 500000;
    public static final int BASE_XFER = 4000;

    protected ItemStorageCoFH[] benchSlots = new ItemStorageCoFH[9];
    protected ItemStorageCoFH chargeSlot = new ItemStorageCoFH(1, EnergyHelper::hasEnergyHandlerCap);

    public ChargeBenchTile() {

        super(CHARGE_BENCH_TILE);

        energyStorage = new EnergyStorageCoFH(BASE_CAPACITY, BASE_XFER);

        for (int i = 0; i < benchSlots.length; ++i) {
            benchSlots[i] = new ItemStorageCoFH(1, EnergyHelper::hasEnergyHandlerCap);
            inventory.addSlot(benchSlots[i], ACCESSIBLE);
        }
        inventory.addSlot(chargeSlot, INTERNAL);

        addAugmentSlots(storageAugments);
        initHandlers();
    }

    @Override
    public void tick() {

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
            chargeSlot.getItemStack().getCapability(EnergyHelper.getEnergySystem(), null).ifPresent(c -> energyStorage.receiveEnergy(c.extractEnergy(maxTransfer, false), false));
        }
    }

    protected void chargeItems() {

        for (ItemStorageCoFH benchSlot : benchSlots) {
            if (!benchSlot.isEmpty()) {
                if (!energyStorage.isEmpty()) {
                    int maxTransfer = Math.min(energyStorage.getMaxExtract(), energyStorage.getEnergyStored());
                    benchSlot.getItemStack().getCapability(EnergyHelper.getEnergySystem(), null).ifPresent(c -> energyStorage.extractEnergy(c.receiveEnergy(maxTransfer, false), false));
                }
                isActive = true;
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new ChargeBenchContainer(i, world, pos, inventory, player);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
