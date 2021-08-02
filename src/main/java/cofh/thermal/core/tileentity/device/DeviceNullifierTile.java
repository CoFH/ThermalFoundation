package cofh.thermal.core.tileentity.device;

import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.fluid.NullFluidStorage;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.NullItemStorage;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.device.DeviceNullifierContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.StorageGroup.INTERNAL;
import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_FILTER;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_NULLIFIER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DeviceNullifierTile extends DeviceTileBase {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_FILTER);

    protected ItemStorageCoFH nullSlot = new NullItemStorage(item -> filter.valid(item)).setEnabled(() -> isActive);
    protected ItemStorageCoFH[] binSlots;
    // protected ItemStorageCoFH tankSlot = new ItemStorageCoFH(1, (item) -> FluidHelper.hasFluidHandlerCap(item) || item.getItem() == Items.POTION);

    protected FluidStorageCoFH nullTank = new NullFluidStorage(BUCKET_VOLUME * 64, fluid -> filter.valid(fluid)).setEnabled(() -> isActive);

    public DeviceNullifierTile() {

        super(DEVICE_NULLIFIER_TILE);

        inventory.addSlot(nullSlot, INPUT);

        binSlots = new ItemStorageCoFH[9];
        for (int i = 0; i < binSlots.length; ++i) {
            binSlots[i] = new ItemStorageCoFH(item -> filter.valid(item));
            inventory.addSlot(binSlots[i], INTERNAL);
        }
        tankInv.addTank(nullTank, INPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();
    }

    public boolean binHasItems() {

        for (ItemStorageCoFH slot : binSlots) {
            if (!slot.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void emptyBin() {

        for (ItemStorageCoFH slot : binSlots) {
            slot.clear();
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceNullifierContainer(i, world, pos, inventory, player);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
