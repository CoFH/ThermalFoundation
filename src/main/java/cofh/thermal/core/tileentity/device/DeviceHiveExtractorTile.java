package cofh.thermal.core.tileentity.device;

import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.device.DeviceHiveExtractorContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import static cofh.lib.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.references.CoreReferences.FLUID_HONEY;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_HIVE_EXTRACTOR_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DeviceHiveExtractorTile extends DeviceTileBase {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID);

    private static final int COMB_AMOUNT = 2;
    private static final int HONEY_AMOUNT = 250;

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
            extractProducts(pos.up());
        }
    }

    @Override
    protected boolean isValid() {

        return world != null && world.getBlockState(pos.up()).hasProperty(BeehiveBlock.HONEY_LEVEL);
    }

    protected void extractProducts(BlockPos above) {

        if (world == null) {
            return;
        }
        BlockState hive = world.getBlockState(above);
        if (hive.hasProperty(BeehiveBlock.HONEY_LEVEL) && BeehiveTileEntity.getHoneyLevel(hive) >= 5) {
            world.setBlockState(above, hive.with(BeehiveBlock.HONEY_LEVEL, 0), 3);
            if (outputSlot.isEmpty()) {
                outputSlot.setItemStack(cloneStack(Items.HONEYCOMB, COMB_AMOUNT));
            } else {
                outputSlot.modify(Math.min(COMB_AMOUNT, outputSlot.getSpace()));
            }
            if (outputTank.isEmpty()) {
                outputTank.setFluidStack(new FluidStack(FLUID_HONEY, HONEY_AMOUNT));
            } else {
                outputTank.modify(Math.min(HONEY_AMOUNT, outputTank.getSpace()));
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceHiveExtractorContainer(i, world, pos, inventory, player);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
