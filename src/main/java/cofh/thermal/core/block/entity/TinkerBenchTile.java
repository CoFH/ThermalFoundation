package cofh.thermal.core.block.entity;

import cofh.core.fluid.PotionFluid;
import cofh.core.util.filter.EmptyFilter;
import cofh.core.util.filter.IFilter;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.core.util.helpers.AugmentableHelper;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.energy.EnergyStorageCoFH;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.thermal.core.inventory.container.TinkerBenchContainer;
import cofh.thermal.lib.tileentity.ThermalTileAugmentable;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.api.StorageGroup.INPUT;
import static cofh.lib.api.StorageGroup.INTERNAL;
import static cofh.lib.util.Constants.*;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.config.ThermalCoreConfig.storageAugments;
import static cofh.thermal.core.init.TCoreTileEntities.TINKER_BENCH_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE;

public class TinkerBenchTile extends ThermalTileAugmentable implements ITickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_FLUID);

    public static final int BASE_CAPACITY = 500000;
    public static final int BASE_XFER = 1000;

    protected static final byte REPLENISH = 0;
    protected static final byte AUGMENT = 1;

    protected ItemStorageCoFH tinkerSlot = new ItemStorageCoFH(1, item -> AugmentableHelper.isAugmentableItem(item) || ThermalEnergyHelper.hasEnergyHandlerCap(item) || FluidHelper.hasFluidHandlerCap(item));
    protected ItemStorageCoFH chargeSlot = new ItemStorageCoFH(1, ThermalEnergyHelper::hasEnergyHandlerCap);
    protected ItemStorageCoFH tankSlot = new ItemStorageCoFH(1, (item) -> FluidHelper.hasFluidHandlerCap(item) || item.getItem() == Items.POTION);

    protected FluidStorageCoFH tank = new FluidStorageCoFH(TANK_MEDIUM);

    protected byte mode;
    protected boolean pause;

    // TODO: Cap caching? Premature optimization possibly; revisit.
    //    protected LazyOptional<?> tinkerEnergyCap = LazyOptional.empty();
    //    protected LazyOptional<?> tinkerFluidCap = LazyOptional.empty();
    //    protected LazyOptional<?> chargeEnergyCap = LazyOptional.empty();
    //    protected LazyOptional<?> tankFluidCap = LazyOptional.empty();

    public TinkerBenchTile(BlockPos pos, BlockState state) {

        super(TINKER_BENCH_TILE.get(), pos, state);

        energyStorage = new EnergyStorageCoFH(BASE_CAPACITY, BASE_XFER);

        inventory.addSlot(tinkerSlot, INTERNAL);
        inventory.addSlot(chargeSlot, INTERNAL);
        inventory.addSlot(tankSlot, INTERNAL);

        tankInv.addTank(tank, INPUT);

        addAugmentSlots(storageAugments);
        initHandlers();
    }

    @Override
    public void tickServer() {

        if (redstoneControl.getState()) {
            chargeEnergy();
            fillFluid();
        }
    }

    public void setPause(boolean pause) {

        this.pause = pause;
    }

    public boolean allowAugmentation() {

        return mode == AUGMENT;
    }

    public void toggleTinkerSlotMode() {

        ++mode;
        mode %= 2;
    }

    protected void chargeEnergy() {

        if (!chargeSlot.isEmpty()) {
            int maxTransfer = Math.min(energyStorage.getMaxReceive(), energyStorage.getSpace());
            chargeSlot.getItemStack().getCapability(ThermalEnergyHelper.getBaseEnergySystem(), null).ifPresent(c -> energyStorage.receiveEnergy(c.extractEnergy(maxTransfer, false), false));
        }
        if (!tinkerSlot.isEmpty() && mode == REPLENISH && !pause) {
            int maxTransfer = Math.min(energyStorage.getMaxExtract(), energyStorage.getEnergyStored());
            tinkerSlot.getItemStack().getCapability(ThermalEnergyHelper.getBaseEnergySystem(), null).ifPresent(c -> energyStorage.extractEnergy(c.receiveEnergy(maxTransfer, false), false));
        }
    }

    protected void fillFluid() {

        if (!tankSlot.isEmpty()) {
            ItemStack tankStack = tankSlot.getItemStack();
            if (tankStack.getItem() == Items.POTION) {
                FluidStack potion = PotionFluid.getPotionFluidFromItem(BOTTLE_VOLUME, tankStack);
                if (tank.fill(potion, SIMULATE) == BOTTLE_VOLUME) {
                    tank.fill(potion, EXECUTE);
                    tankSlot.setItemStack(new ItemStack(Items.GLASS_BOTTLE));
                }
            } else {
                tankStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).ifPresent(c -> {
                    int toFill = tank.fill(new FluidStack(c.getFluidInTank(0), BUCKET_VOLUME), SIMULATE);
                    if (toFill > 0) {
                        tank.fill(c.drain(toFill, EXECUTE), EXECUTE);
                        tankSlot.setItemStack(c.getContainer());
                    }
                });
            }
        }
        if (!tinkerSlot.isEmpty() && mode == REPLENISH && !pause) {
            tinkerSlot.getItemStack().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).ifPresent(c -> {
                tank.drain(c.fill(new FluidStack(tank.getFluidStack(), Math.min(tank.getAmount(), BUCKET_VOLUME)), EXECUTE), EXECUTE);
                tinkerSlot.setItemStack(c.getContainer());
            });
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new TinkerBenchContainer(i, level, worldPosition, inventory, player);
    }

    // region GUI
    @Override
    public boolean canOpenGui() {

        return numPlayersUsing <= 0;
    }
    // endregion

    // region NETWORK
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeByte(mode);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        mode = buffer.readByte();
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        mode = nbt.getByte(TAG_MODE);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putByte(TAG_MODE, mode);
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion

    // region IFilterableTile
    @Override
    public IFilter getFilter(int filterId) {

        return EmptyFilter.INSTANCE;
    }

    @Override
    public boolean openGui(ServerPlayer player, int guiId) {

        return false;
    }

    @Override
    public boolean openFilterGui(ServerPlayer player, int filterId) {

        return false;
    }
    // endregion
}
