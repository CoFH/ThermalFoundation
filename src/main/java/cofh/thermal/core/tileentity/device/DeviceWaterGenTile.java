package cofh.thermal.core.tileentity.device;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.client.audio.ConditionalSound;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.tileentity.ICoFHTickableTile;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.device.DeviceWaterGenContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.util.StorageGroup.INTERNAL;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.Constants.TANK_SMALL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_WATER_GEN_TILE;
import static cofh.thermal.core.init.TCoreSounds.SOUND_DEVICE_WATER_GEN;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceWaterGenTile extends DeviceTileBase implements ICoFHTickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_FILTER);

    protected static final int GENERATION_RATE = 250;
    protected static final Supplier<FluidStack> WATER = () -> new FluidStack(Fluids.WATER, 0);

    protected ItemStorageCoFH fillSlot = new ItemStorageCoFH(1, FluidHelper::hasFluidHandlerCap);
    protected FluidStorageCoFH tank = new FluidStorageCoFH(TANK_SMALL, e -> false).setEmptyFluid(WATER).setEnabled(() -> isActive);

    protected boolean cached;
    protected boolean valid;

    public DeviceWaterGenTile(BlockPos pos, BlockState state) {

        super(DEVICE_WATER_GEN_TILE, pos, state);

        inventory.addSlot(fillSlot, INTERNAL);

        tankInv.addTank(tank, OUTPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();

        renderFluid = new FluidStack(Fluids.WATER, BUCKET_VOLUME);
    }

    @Override
    protected void updateValidity() {

        if (level == null || !level.isAreaLoaded(worldPosition, 1)) {
            return;
        }
        int adjWaterSource = 0;
        valid = false;

        BlockPos[] cardinals = new BlockPos[] {
                worldPosition.north(),
                worldPosition.south(),
                worldPosition.west(),
                worldPosition.east(),
        };
        for (BlockPos adj : cardinals) {
            FluidState state = level.getFluidState(adj);
            if (state.getType().equals(Fluids.WATER)) {
                ++adjWaterSource;
            }
        }
        if (adjWaterSource > 1) {
            valid = true;
        } else {
            tank.clear();
        }
        cached = true;
    }

    @Override
    protected void updateActiveState() {

        if (!cached) {
            updateValidity();
        }
        super.updateActiveState();
    }

    @Override
    protected boolean isValid() {

        return valid;
    }

    protected void fillFluid() {

        if (!fillSlot.isEmpty()) {
            fillSlot.getItemStack()
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
                    .ifPresent(c -> {
                        tank.drain(c.fill(new FluidStack(tank.getFluidStack(), (int) (BUCKET_VOLUME * baseMod)), EXECUTE), EXECUTE);
                        fillSlot.setItemStack(c.getContainer());
                    });
        }
    }

    @Override
    public void tickServer() {

        updateActiveState();

        if (isActive) {
            tank.modify((int) (GENERATION_RATE * baseMod));
            fillFluid();
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return new ModelDataMap.Builder()
                .withInitial(FLUID, renderFluid)
                .build();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceWaterGenContainer(i, level, worldPosition, inventory, player);
    }

    @Override
    protected Object getSound() {

        return new ConditionalSound(SOUND_DEVICE_WATER_GEN, SoundSource.AMBIENT, this, () -> !remove && isActive);
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
