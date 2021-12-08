package cofh.thermal.core.tileentity.device;

import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.device.DeviceHiveExtractorContainer;
import cofh.thermal.core.util.managers.device.HiveExtractorManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.Constants.TANK_MEDIUM;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_FLUID;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_UPGRADE;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_HIVE_EXTRACTOR_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceHiveExtractorTile extends DeviceTileBase {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID);

    protected ItemStorageCoFH outputSlot = new ItemStorageCoFH();
    protected FluidStorageCoFH outputTank = new FluidStorageCoFH(TANK_MEDIUM);

    public DeviceHiveExtractorTile() {

        super(DEVICE_HIVE_EXTRACTOR_TILE);

        inventory.addSlot(outputSlot, OUTPUT);

        tankInv.addTank(outputTank, OUTPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();
    }

    @Override
    protected void updateActiveState() {

        super.updateActiveState();

        if (isActive) {
            extractProducts(worldPosition.above());
        }
    }

    @Override
    protected boolean isValid() {

        return level != null && level.getBlockState(worldPosition.above()).hasProperty(BeehiveBlock.HONEY_LEVEL);
    }

    protected void extractProducts(BlockPos above) {

        if (level == null) {
            return;
        }
        BlockState hive = level.getBlockState(above);
        if (hive.hasProperty(BeehiveBlock.HONEY_LEVEL) && BeehiveTileEntity.getHoneyLevel(hive) >= 5) {
            ItemStack comb = HiveExtractorManager.instance().getItem(hive);
            FluidStack honey = HiveExtractorManager.instance().getFluid(hive);

            outputSlot.insertItem(0, comb, false);
            outputTank.fill(honey, EXECUTE);

            level.setBlock(above, hive.setValue(BeehiveBlock.HONEY_LEVEL, 0), 3);
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceHiveExtractorContainer(i, level, worldPosition, inventory, player);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
