package cofh.thermal.core.block.entity.device;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceHiveExtractorContainer;
import cofh.thermal.core.util.managers.device.HiveExtractorManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.api.StorageGroup.OUTPUT;
import static cofh.lib.util.Constants.TANK_MEDIUM;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_FLUID;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_UPGRADE;
import static cofh.thermal.core.init.TCoreTileEntities.DEVICE_HIVE_EXTRACTOR_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceHiveExtractorTile extends DeviceTileBase {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID);

    protected ItemStorageCoFH outputSlot = new ItemStorageCoFH();
    protected FluidStorageCoFH outputTank = new FluidStorageCoFH(TANK_MEDIUM);

    public DeviceHiveExtractorTile(BlockPos pos, BlockState state) {

        super(DEVICE_HIVE_EXTRACTOR_TILE.get(), pos, state);

        inventory.addSlot(outputSlot, OUTPUT);

        tankInv.addTank(outputTank, OUTPUT);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
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
        if (hive.hasProperty(BeehiveBlock.HONEY_LEVEL) && BeehiveBlockEntity.getHoneyLevel(hive) >= 5) {
            ItemStack comb = HiveExtractorManager.instance().getItem(hive);
            FluidStack honey = HiveExtractorManager.instance().getFluid(hive);

            outputSlot.insertItem(0, comb, false);
            outputTank.fill(honey, EXECUTE);

            level.setBlock(above, hive.setValue(BeehiveBlock.HONEY_LEVEL, 0), 3);
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceHiveExtractorContainer(i, level, worldPosition, inventory, player);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
