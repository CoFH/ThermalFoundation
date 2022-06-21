package cofh.thermal.lib.tileentity;

import cofh.core.content.block.entity.TileCoFH;
import cofh.core.content.energy.EmptyEnergyStorage;
import cofh.core.content.energy.EnergyStorageCoFH;
import cofh.core.content.item.IAugmentableItem;
import cofh.core.content.xp.EmptyXpStorage;
import cofh.core.content.xp.XpStorage;
import cofh.core.network.packet.client.TileControlPacket;
import cofh.core.network.packet.client.TileRedstonePacket;
import cofh.core.network.packet.client.TileStatePacket;
import cofh.core.util.control.*;
import cofh.core.util.filter.EmptyFilter;
import cofh.core.util.filter.FilterRegistry;
import cofh.core.util.filter.IFilter;
import cofh.core.util.filter.IFilterableTile;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.core.util.helpers.FilterHelper;
import cofh.lib.content.fluid.FluidStorageCoFH;
import cofh.lib.content.fluid.ManagedTankInv;
import cofh.lib.content.fluid.SimpleTankInv;
import cofh.lib.content.inventory.ItemStorageCoFH;
import cofh.lib.content.inventory.ManagedItemInv;
import cofh.lib.content.inventory.SimpleItemInv;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.SoundHelper;
import cofh.thermal.core.config.ThermalClientConfig;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.init.TCoreSounds;
import cofh.thermal.lib.util.ThermalEnergyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cofh.core.init.CoreEnchantments.HOLDING;
import static cofh.core.util.helpers.AugmentableHelper.*;
import static cofh.core.util.helpers.GuiHelper.*;
import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.core.util.helpers.ItemHelper.consumeItem;
import static cofh.lib.api.StorageGroup.ACCESSIBLE;
import static cofh.lib.api.StorageGroup.INTERNAL;
import static cofh.lib.util.constants.BlockStatePropertiesCoFH.ACTIVE;
import static cofh.lib.util.constants.NBTTags.*;
import static net.minecraft.nbt.Tag.TAG_COMPOUND;

public abstract class ThermalTileAugmentable extends TileCoFH implements ISecurableTile, IRedstoneControllableTile, MenuProvider, IFilterableTile {

    protected static final int BASE_ENERGY = 50000;
    protected static final int BASE_PROCESS_TICK = 20;
    protected static final int BASE_XP_STORAGE = 2500;

    protected ManagedItemInv inventory = new ManagedItemInv(this, TAG_ITEM_INV);
    protected ManagedTankInv tankInv = new ManagedTankInv(this, TAG_TANK_INV);
    protected EnergyStorageCoFH energyStorage = EmptyEnergyStorage.INSTANCE;
    protected XpStorage xpStorage = EmptyXpStorage.INSTANCE;
    protected IFilter filter = EmptyFilter.INSTANCE;

    protected SecurityControlModule securityControl = new SecurityControlModule(this);
    protected RedstoneControlModule redstoneControl = new RedstoneControlModule(this);

    protected List<ItemStorageCoFH> augments = Collections.emptyList();
    protected ListTag enchantments = new ListTag();

    public boolean isActive;
    protected FluidStack renderFluid = FluidStack.EMPTY;

    public ThermalTileAugmentable(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
        redstoneControl.setEnabled(() -> redstoneControlFeature);
    }

    // region BASE PARAMETERS
    protected int getBaseEnergyStorage() {

        return BASE_ENERGY;
    }

    protected int getBaseEnergyXfer() {

        return getBaseProcessTick() * 10;
    }

    protected int getBaseProcessTick() {

        return BASE_PROCESS_TICK;
    }

    protected int getBaseXpStorage() {

        return BASE_XP_STORAGE;
    }
    // endregion

    // TODO: Does this need to exist?
    @Override
    public void setRemoved() {

        super.setRemoved();

        energyCap.invalidate();
        itemCap.invalidate();
        fluidCap.invalidate();
    }

    // region HELPERS
    @Override
    public int invSize() {

        return inventory.getSlots();
    }

    @Override
    public int augSize() {

        return augments.size();
    }

    public SimpleItemInv getItemInv() {

        return inventory;
    }

    public SimpleTankInv getTankInv() {

        return tankInv;
    }

    protected void initHandlers() {

        inventory.initHandlers();
        tankInv.initHandlers();
    }

    protected void updateActiveState(boolean prevActive) {

        if (prevActive != isActive) {
            if (getBlockState().hasProperty(ACTIVE)) {
                level.setBlockAndUpdate(worldPosition, getBlockState().setValue(ACTIVE, isActive));
            }
            TileStatePacket.sendToClient(this);
        }
    }

    protected boolean cacheRenderFluid() {

        return false;
    }

    @Override
    public void neighborChanged(Block blockIn, BlockPos fromPos) {

        if (level != null && redstoneControl.isControllable()) {
            redstoneControl.setPower(level.getBestNeighborSignal(worldPosition));
            TileRedstonePacket.sendToClient(this);
        }
    }

    @Override
    public void onPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        super.onPlacedBy(worldIn, pos, state, placer, stack);

        enchantments = stack.getEnchantmentTags();

        updateAugmentState();

        if (redstoneControl.isControllable()) {
            redstoneControl.setPower(worldIn.getBestNeighborSignal(worldPosition));
        }
        onControlUpdate();
    }

    @Override
    public void onReplaced(BlockState state, Level worldIn, BlockPos pos, BlockState newState) {

        if (!keepItems()) {
            for (int i = 0; i < invSize() - augSize(); ++i) {
                Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
            }
        }
        if (!ThermalCoreConfig.keepAugments) {
            for (int i = invSize() - augSize(); i < invSize(); ++i) {
                Utils.dropItemStackIntoWorldWithRandomness(inventory.getStackInSlot(i), worldIn, pos);
            }
        }
        if (xpStorage.getStored() > 0) {
            spawnXpOrbs(level, xpStorage.getStored(), Vec3.atBottomCenterOf(pos));
        }
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        CompoundTag nbt = stack.getOrCreateTagElement(TAG_BLOCK_ENTITY);
        if (keepEnergy()) {
            getEnergyStorage().writeWithParams(nbt);
        }
        if (keepItems()) {
            getItemInv().writeSlotsToNBT(nbt, 0, invSize() - augSize());
        }
        if (ThermalCoreConfig.keepAugments && augSize() > 0) {
            getItemInv().writeSlotsToNBTUnordered(nbt, TAG_AUGMENTS, invSize() - augSize());
            if (stack.getItem() instanceof IAugmentableItem) {
                List<ItemStack> items = getAugmentsAsList();
                ((IAugmentableItem) stack.getItem()).updateAugmentState(stack, items);
            }
        }
        if (keepFluids()) {
            getTankInv().write(nbt);
        }
        // TODO: Keep XP?

        if (ThermalCoreConfig.keepRSControl && redstoneControlFeature) {
            redstoneControl().writeSettings(nbt);
        }
        if (ThermalCoreConfig.keepSideConfig && this instanceof IReconfigurableTile) {
            ((IReconfigurableTile) this).reconfigControl().writeSettings(nbt);
        }
        if (ThermalCoreConfig.keepTransferControl && this instanceof ITransferControllableTile) {
            ((ITransferControllableTile) this).transferControl().writeSettings(nbt);
        }
        if (hasSecurity()) {
            securityControl().write(nbt);
        }
        if (!nbt.isEmpty()) {
            stack.addTagElement(TAG_BLOCK_ENTITY, nbt);
        }
        if (!enchantments.isEmpty()) {
            stack.getOrCreateTag().put(TAG_ENCHANTMENTS, enchantments);
        }
        return super.createItemStackTag(stack);
    }

    @Override
    public boolean onActivatedDelegate(Level world, BlockPos pos, BlockState state, Player player, InteractionHand hand, BlockHitResult result) {

        if (player.isSecondaryUseActive()) {
            return openFilterGui((ServerPlayer) player, 0);
        }
        ItemStack stack = player.getItemInHand(hand);
        if (augValidator().test(stack)) {
            if (attemptAugmentInstall(stack)) {
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, consumeItem(stack, 1));
                }
                player.level.playSound(null, player.blockPosition(), TCoreSounds.SOUND_TINKER, SoundSource.PLAYERS, 0.1F, (MathHelper.RANDOM.nextFloat() - MathHelper.RANDOM.nextFloat()) * 0.35F + 0.9F);
            } else {
                player.level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 0.1F, 0.25F);
            }
            return true;
        }
        return super.onActivatedDelegate(world, pos, state, player, hand, result);
    }

    public void markChunkUnsaved() {

        if (this.level != null) {
            if (this.level.hasChunkAt(this.worldPosition)) {
                this.level.getChunkAt(this.worldPosition).setUnsaved(true);
            }
        }
    }

    protected boolean keepEnergy() {

        return ThermalCoreConfig.keepEnergy;
    }

    protected boolean keepFluids() {

        return ThermalCoreConfig.keepFluids;
    }

    protected boolean keepItems() {

        return ThermalCoreConfig.keepItems;
    }
    // endregion

    // region GUI
    public ItemStorageCoFH getSlot(int slot) {

        return inventory.getSlot(slot);
    }

    public FluidStorageCoFH getTank(int tank) {

        return tankInv.getTank(tank);
    }

    public EnergyStorageCoFH getEnergyStorage() {

        return energyStorage;
    }

    public FluidStack getRenderFluid() {

        return renderFluid;
    }

    public int getScaledDuration() {

        return getScaledDuration(DURATION);
    }

    public int getScaledDuration(int scale) {

        return isActive ? scale : 0;
    }

    public int getScaledProgress() {

        return getScaledProgress(PROGRESS);
    }

    public int getScaledProgress(int scale) {

        return isActive ? scale : 0;
    }

    public int getScaledSpeed() {

        return getScaledSpeed(SPEED);
    }

    public int getScaledSpeed(int scale) {

        return isActive ? scale : 0;
    }

    public int getCurSpeed() {

        return -1;
    }

    public int getMaxSpeed() {

        return -1;
    }

    public double getEfficiency() {

        return -1.0D;
    }

    @Override
    public boolean clearEnergy(int coil) {

        return energyStorage.clear();
    }

    @Override
    public boolean clearSlot(int slot) {

        if (slot >= inventory.getSlots()) {
            return false;
        }
        if (inventory.getSlot(slot).clear()) {
            onInventoryChanged(slot);
            return true;
        }
        return false;
    }

    @Override
    public boolean clearTank(int tank) {

        if (tank >= tankInv.getTanks()) {
            return false;
        }
        if (tankInv.getTank(tank).clear()) {
            onTankChanged(tank);
            return true;
        }
        return false;
    }
    // endregion

    // region NETWORK

    // CONTROL
    @Override
    public FriendlyByteBuf getControlPacket(FriendlyByteBuf buffer) {

        super.getControlPacket(buffer);

        securityControl.writeToBuffer(buffer);
        redstoneControl.writeToBuffer(buffer);

        buffer.writeFluidStack(renderFluid);

        return buffer;
    }

    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        securityControl.readFromBuffer(buffer);
        redstoneControl.readFromBuffer(buffer);

        renderFluid = buffer.readFluidStack();
    }

    // GUI
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeBoolean(isActive);
        buffer.writeFluidStack(renderFluid);

        energyStorage.writeToBuffer(buffer);
        xpStorage.writeToBuffer(buffer);

        for (int i = 0; i < tankInv.getTanks(); ++i) {
            buffer.writeFluidStack(tankInv.get(i));
        }
        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        isActive = buffer.readBoolean();
        renderFluid = buffer.readFluidStack();

        energyStorage.readFromBuffer(buffer);
        xpStorage.readFromBuffer(buffer);

        for (int i = 0; i < tankInv.getTanks(); ++i) {
            tankInv.set(i, buffer.readFluidStack());
        }
    }

    // REDSTONE
    @Override
    public FriendlyByteBuf getRedstonePacket(FriendlyByteBuf buffer) {

        super.getRedstonePacket(buffer);

        buffer.writeInt(redstoneControl.getPower());

        return buffer;
    }

    @Override
    public void handleRedstonePacket(FriendlyByteBuf buffer) {

        super.handleRedstonePacket(buffer);

        redstoneControl.setPower(buffer.readInt());
    }

    // STATE
    @Override
    public FriendlyByteBuf getStatePacket(FriendlyByteBuf buffer) {

        super.getStatePacket(buffer);

        buffer.writeBoolean(isActive);
        buffer.writeFluidStack(renderFluid);

        return buffer;
    }

    @Override
    public void handleStatePacket(FriendlyByteBuf buffer) {

        super.handleStatePacket(buffer);

        boolean prevActive = isActive;

        isActive = buffer.readBoolean();
        renderFluid = buffer.readFluidStack();

        if (ThermalClientConfig.blockAmbientSounds && isActive && !prevActive) {
            SoundHelper.playSound(getSound());
        }
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        isActive = nbt.getBoolean(TAG_ACTIVE);

        enchantments = nbt.getList(TAG_ENCHANTMENTS, 10);

        inventory.read(nbt);

        if (nbt.contains(TAG_AUGMENTS)) {
            inventory.readSlotsUnordered(nbt.getList(TAG_AUGMENTS, TAG_COMPOUND), invSize() - augSize());
        }
        updateAugmentState();

        tankInv.read(nbt);
        energyStorage.read(nbt);
        xpStorage.read(nbt);
        filter.read(nbt);

        securityControl.read(nbt);
        redstoneControl.read(nbt);

        renderFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompound(TAG_RENDER_FLUID));
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putBoolean(TAG_ACTIVE, isActive);

        nbt.put(TAG_ENCHANTMENTS, enchantments);

        inventory.write(nbt);
        tankInv.write(nbt);
        getEnergyStorage().write(nbt);
        getXpStorage().write(nbt);
        filter.write(nbt);

        securityControl.write(nbt);
        redstoneControl.write(nbt);

        if (!renderFluid.isEmpty()) {
            nbt.put(TAG_RENDER_FLUID, renderFluid.writeToNBT(new CompoundTag()));
        }
    }
    // endregion

    // region AUGMENTS
    protected boolean redstoneControlFeature = ThermalCoreConfig.defaultRSControl;
    protected boolean xpStorageFeature = ThermalCoreConfig.defaultXPStorage;

    protected boolean creativeEnergy = false;
    protected boolean creativeTanks = false;
    // protected boolean creativeSlots = false;

    // This is CLEARED after augments are finalized.
    protected CompoundTag augmentNBT;

    protected final boolean attemptAugmentInstall(ItemStack stack) {

        for (ItemStorageCoFH augSlot : augments) {
            if (augSlot.isEmpty() && augSlot.isItemValid(stack)) {
                augSlot.setItemStack(cloneStack(stack, 1));
                updateAugmentState();
                return true;
            }
        }
        return false;
    }

    /**
     * This should be called AFTER all other slots have been added.
     * Augment slots are added to the INTERNAL inventory category.
     *
     * @param numAugments Number of augment slots to add.
     */
    protected final void addAugmentSlots(int numAugments) {

        augments = new ArrayList<>(numAugments);
        for (int i = 0; i < numAugments; ++i) {
            ItemStorageCoFH slot = new ItemStorageCoFH(1, augValidator());
            augments.add(slot);
            inventory.addSlot(slot, INTERNAL);
        }
        ((ArrayList<ItemStorageCoFH>) augments).trimToSize();
    }

    protected final void updateAugmentState() {

        resetAttributes();
        for (ItemStorageCoFH slot : augments) {
            ItemStack augment = slot.getItemStack();
            CompoundTag augmentData = AugmentDataHelper.getAugmentData(augment);
            if (augmentData == null) {
                continue;
            }
            setAttributesFromAugment(augmentData);
        }
        finalizeAttributes(EnchantmentHelper.deserializeEnchantments(enchantments));
        augmentNBT = null;
    }

    protected final List<ItemStack> getAugmentsAsList() {

        return augments.stream().map(ItemStorageCoFH::getItemStack).collect(Collectors.toList());
    }

    protected Predicate<ItemStack> augValidator() {

        return AugmentDataHelper::hasAugmentData;
    }

    protected void resetAttributes() {

        augmentNBT = new CompoundTag();

        redstoneControlFeature = defaultRedstoneControlState();
        xpStorageFeature = defaultXpStorageState();

        creativeEnergy = false;
        creativeTanks = false;
        // creativeSlots = false;
    }

    protected void setAttributesFromAugment(CompoundTag augmentData) {

        redstoneControlFeature |= getAttributeMod(augmentData, TAG_AUGMENT_FEATURE_RS_CONTROL) > 0;
        xpStorageFeature |= getAttributeMod(augmentData, TAG_AUGMENT_FEATURE_XP_STORAGE) > 0;

        setAttributeFromAugmentMax(augmentNBT, augmentData, TAG_AUGMENT_BASE_MOD);
        setAttributeFromAugmentMax(augmentNBT, augmentData, TAG_AUGMENT_RF_XFER);
        setAttributeFromAugmentMax(augmentNBT, augmentData, TAG_AUGMENT_RF_STORAGE);
        setAttributeFromAugmentMax(augmentNBT, augmentData, TAG_AUGMENT_FLUID_STORAGE);
        setAttributeFromAugmentMax(augmentNBT, augmentData, TAG_AUGMENT_ITEM_STORAGE);

        setAttributeFromAugmentString(augmentNBT, augmentData, TAG_FILTER_TYPE);

        creativeEnergy |= getAttributeMod(augmentData, TAG_AUGMENT_RF_CREATIVE) > 0;
        creativeTanks |= getAttributeMod(augmentData, TAG_AUGMENT_FLUID_CREATIVE) > 0;
        // creativeSlots |= getAttributeMod(augmentData, TAG_AUGMENT_ITEM_CREATIVE) > 0;
    }

    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        float holdingMod = getHoldingMod(enchantmentMap);
        float baseMod = getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_BASE_MOD, 1.0F);

        float energyStorageMod = holdingMod * baseMod * getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_RF_STORAGE, 1.0F);
        float fluidStorageMod = holdingMod * baseMod * getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_FLUID_STORAGE, 1.0F);
        float itemStorageMod = holdingMod * baseMod * getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_ITEM_STORAGE, 1.0F);
        float xpStorageMod = holdingMod * baseMod;

        float energyXferMod = baseMod * getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_RF_XFER, 1.0F);

        energyStorage.applyModifiers(energyStorageMod, energyXferMod).setCreative(() -> creativeEnergy);

        for (int i = 0; i < tankInv.getTanks(); ++i) {
            tankInv.getTank(i).applyModifiers(fluidStorageMod).setCreative(() -> creativeTanks);
        }
        //        for (int i = 0; i < inventory.getSlots(); ++i) {
        //            inventory.getSlot(i).applyModifiers(itemStorageMod).setCreative(() -> creativeSlots);
        //        }

        // TODO: XP Storage improvement
        int storedXp = xpStorage.getStored();
        xpStorage.applyModifiers(xpStorageMod * (xpStorageFeature ? 1 : 0));
        if (storedXp > 0 && xpStorage.getStored() < storedXp) {
            spawnXpOrbs(level, storedXp - xpStorage.getStored(), Vec3.atBottomCenterOf(worldPosition));
        }

        CompoundTag filterNBT = filter.write(new CompoundTag());
        filter = FilterRegistry.getTileFilter(getAttributeModString(augmentNBT, TAG_FILTER_TYPE), this, filterNBT);
    }

    protected boolean defaultReconfigState() {

        return ThermalCoreConfig.defaultReconfigSides;
    }

    protected boolean defaultRedstoneControlState() {

        return ThermalCoreConfig.defaultRSControl;
    }

    protected boolean defaultXpStorageState() {

        return ThermalCoreConfig.defaultXPStorage;
    }

    protected float getHoldingMod(Map<Enchantment, Integer> enchantmentMap) {

        int holding = enchantmentMap.getOrDefault(HOLDING.get(), 0);
        return 1 + holding / 2F;
    }
    // endregion

    // region MODULES
    @Override
    public SecurityControlModule securityControl() {

        return securityControl;
    }

    @Override
    public RedstoneControlModule redstoneControl() {

        return redstoneControl;
    }
    // endregion

    // region CAPABILITIES
    protected LazyOptional<?> energyCap = LazyOptional.empty();
    protected LazyOptional<?> itemCap = LazyOptional.empty();
    protected LazyOptional<?> fluidCap = LazyOptional.empty();

    protected void updateHandlers() {

        LazyOptional<?> prevEnergyCap = energyCap;
        energyCap = energyStorage.getCapacity() > 0 ? LazyOptional.of(() -> energyStorage) : LazyOptional.empty();
        prevEnergyCap.invalidate();

        LazyOptional<?> prevItemCap = itemCap;
        IItemHandler invHandler = inventory.getHandler(ACCESSIBLE);
        itemCap = inventory.hasAccessibleSlots() ? LazyOptional.of(() -> invHandler) : LazyOptional.empty();
        prevItemCap.invalidate();

        LazyOptional<?> prevFluidCap = fluidCap;
        IFluidHandler fluidHandler = tankInv.getHandler(ACCESSIBLE);
        fluidCap = tankInv.hasAccessibleTanks() ? LazyOptional.of(() -> fluidHandler) : LazyOptional.empty();
        prevFluidCap.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if (cap == ThermalEnergyHelper.getBaseEnergySystem() && energyStorage.getMaxEnergyStored() > 0) {
            return getEnergyCapability(side);
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory.hasAccessibleSlots()) {
            return getItemHandlerCapability(side);
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && tankInv.hasAccessibleTanks()) {
            return getFluidHandlerCapability(side);
        }
        return super.getCapability(cap, side);
    }

    protected <T> LazyOptional<T> getEnergyCapability(@Nullable Direction side) {

        if (!energyCap.isPresent() && energyStorage.getCapacity() > 0) {
            energyCap = LazyOptional.of(() -> energyStorage);
        }
        return energyCap.cast();
    }

    protected <T> LazyOptional<T> getItemHandlerCapability(@Nullable Direction side) {

        if (!itemCap.isPresent() && inventory.hasAccessibleSlots()) {
            IItemHandler handler = inventory.getHandler(ACCESSIBLE);
            itemCap = LazyOptional.of(() -> handler);
        }
        return itemCap.cast();
    }

    protected <T> LazyOptional<T> getFluidHandlerCapability(@Nullable Direction side) {

        if (!fluidCap.isPresent() && tankInv.hasAccessibleTanks()) {
            IFluidHandler handler = tankInv.getHandler(ACCESSIBLE);
            fluidCap = LazyOptional.of(() -> handler);
        }
        return fluidCap.cast();
    }
    // endregion

    // region IFilterableTile
    @Override
    public IFilter getFilter(int filterId) {

        return filter;
    }

    @Override
    public void onFilterChanged(int filterId) {

    }

    @Override
    public boolean openGui(ServerPlayer player, int guiId) {

        if (canOpenGui()) {
            NetworkHooks.openGui(player, this, worldPosition);
            return true;
        }
        return false;
    }

    @Override
    public boolean openFilterGui(ServerPlayer player, int filterId) {

        if (FilterHelper.hasFilter(this, 0)) {
            NetworkHooks.openGui(player, getFilter(filterId), worldPosition);
            return true;
        }
        return false;
    }
    // endregion

    // region MenuProvider
    @Override
    public Component getDisplayName() {

        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }
    // endregion

    // region ITileCallback
    @Override
    public void onInventoryChanged(int slot) {

        /* Implicit requirement here that augments always come LAST in slot order.
        This isn't a bad assumption/rule though, as it's a solid way to handle it.*/
        if (slot >= invSize() - augSize()) {
            updateAugmentState();
        }
        markChunkUnsaved();
    }

    //    @Override
    //    public void onTankChanged(int tank) {
    //
    //        markChunkUnsaved();
    //    }

    @Override
    public void onControlUpdate() {

        updateHandlers();
        callNeighborStateChange();
        TileControlPacket.sendToClient(this);
    }
    // endregion

    // region ITileXpHandler
    @Override
    public XpStorage getXpStorage() {

        return xpStorage;
    }
    // endregion

    // region IConveyableData
    @Override
    public void readConveyableData(Player player, CompoundTag tag) {

        redstoneControl.readSettings(tag);

        onControlUpdate();
    }

    @Override
    public void writeConveyableData(Player player, CompoundTag tag) {

        redstoneControl.writeSettings(tag);
    }
    // endregion
}
