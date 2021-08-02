package cofh.thermal.core.tileentity.device;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.inventory.container.device.DevicePotionDiffuserContainer;
import cofh.thermal.core.util.managers.device.PotionDiffuserManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.constants.Constants.*;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_POTION_DIFFUSER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DevicePotionDiffuserTile extends DeviceTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER, TAG_AUGMENT_TYPE_POTION);

    protected static final int TIME_CONSTANT = 60;

    protected ItemStorageCoFH inputSlot = new ItemStorageCoFH(item -> filter.valid(item) && PotionDiffuserManager.instance().validBoost(item));
    protected FluidStorageCoFH inputTank = new FluidStorageCoFH(TANK_MEDIUM, fluid -> filter.valid(fluid) && FluidHelper.hasPotionTag(fluid));

    protected static final int FLUID_AMOUNT = 25;
    protected static final int RADIUS = 4;
    protected int radius = RADIUS;

    protected boolean cached;
    protected List<EffectInstance> effects = Collections.emptyList();
    protected boolean instant;

    protected int process = 1;

    protected int boostCycles;
    protected int boostMax = PotionDiffuserManager.instance().getDefaultEnergy();
    protected int boostAmplifier;
    protected float boostDuration;

    public DevicePotionDiffuserTile() {

        super(DEVICE_POTION_DIFFUSER_TILE);

        inventory.addSlot(inputSlot, INPUT);

        tankInv.addTank(inputTank, INPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();
    }

    @Override
    protected void updateActiveState(boolean prevActive) {

        if (!prevActive && isActive) {
            process = 1;
        }
        super.updateActiveState(prevActive);
    }

    @Override
    protected boolean isValid() {

        return inputTank.getAmount() > FLUID_AMOUNT;
    }

    @Override
    public void tick() {

        updateActiveState();

        if (!isActive) {
            return;
        }
        --process;
        if (process > 0) {
            return;
        }
        process = getTimeConstant();

        if (Utils.isClientWorld(world())) {
            diffuseClient();
            return;
        }
        Fluid curFluid = renderFluid.getFluid();

        cacheEffects();
        diffuse();

        if (curFluid != renderFluid.getFluid()) {
            TileStatePacket.sendToClient(this);
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
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DevicePotionDiffuserContainer(i, world, pos, inventory, player);
    }

    // region GUI
    @Override
    public int getScaledDuration(int scale) {

        return !isActive || boostCycles <= 0 || boostMax <= 0 ? 0 : scale * boostCycles / boostMax;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONTROL
    @Override
    public void handleControlPacket(PacketBuffer buffer) {

        super.handleControlPacket(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // GUI
    @Override
    public PacketBuffer getGuiPacket(PacketBuffer buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(boostCycles);
        buffer.writeInt(boostMax);
        buffer.writeInt(boostAmplifier);
        buffer.writeFloat(boostDuration);

        return buffer;
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        boostCycles = buffer.readInt();
        boostMax = buffer.readInt();
        boostAmplifier = buffer.readInt();
        boostDuration = buffer.readFloat();
    }

    // STATE
    @Override
    public PacketBuffer getStatePacket(PacketBuffer buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(process);
        buffer.writeBoolean(instant);

        return buffer;
    }

    @Override
    public void handleStatePacket(PacketBuffer buffer) {

        super.handleStatePacket(buffer);

        process = buffer.readInt();
        instant = buffer.readBoolean();

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        boostCycles = nbt.getInt(TAG_BOOST_CYCLES);
        boostMax = nbt.getInt(TAG_BOOST_MAX);
        boostAmplifier = nbt.getInt(TAG_BOOST_AMP);
        boostDuration = nbt.getFloat(TAG_BOOST_DUR);

        instant = nbt.getBoolean(TAG_INSTANT);
        process = nbt.getInt(TAG_PROCESS);

        cacheEffects();
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putInt(TAG_BOOST_CYCLES, boostCycles);
        nbt.putInt(TAG_BOOST_MAX, boostMax);
        nbt.putInt(TAG_BOOST_AMP, boostAmplifier);
        nbt.putFloat(TAG_BOOST_DUR, boostDuration);

        nbt.putBoolean(TAG_INSTANT, instant);
        nbt.putInt(TAG_PROCESS, process);

        return nbt;
    }
    // endregion

    // region HELPERS
    @Override
    public boolean hasClientUpdate() {

        return true;
    }

    public int getRadius() {

        return radius;
    }

    public int getTimeConstant() {

        return TIME_CONSTANT;
    }

    public boolean isInstant() {

        return instant;
    }

    protected void cacheEffects() {

        if (inputTank.isEmpty()) {
            if (cached) {
                effects.clear();
                instant = false;
                cached = false;
            }
        } else if (!cached) {
            effects = PotionUtils.getEffectsFromTag(inputTank.getFluidStack().getTag());
            for (EffectInstance effect : effects) {
                instant |= effect.getPotion().isInstant();
            }
            cached = true;
        }
    }

    protected void diffuse() {

        if (inputTank.getAmount() < FLUID_AMOUNT) {
            return;
        }
        if (effects.isEmpty()) {
            return;
        }
        AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -1, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> targets = world.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE);
        if (targets.isEmpty()) { // TODO: Proximity sensor aug?
            return;
        }
        if (boostCycles > 0) {
            --boostCycles;
        } else if (!inputSlot.isEmpty()) {
            boostCycles = PotionDiffuserManager.instance().getBoostCycles(inputSlot.getItemStack());
            boostMax = boostCycles;
            boostAmplifier = PotionDiffuserManager.instance().getBoostAmplifier(inputSlot.getItemStack());
            boostDuration = PotionDiffuserManager.instance().getBoostDurationMod(inputSlot.getItemStack());
            inputSlot.consume(1);
        } else {
            boostCycles = 0;
            boostAmplifier = 0;
            boostDuration = 0;
        }
        for (LivingEntity target : targets) {
            if (target.canBeHitWithPotion()) {
                for (EffectInstance effect : effects) {
                    if (effect.getPotion().isInstant()) {
                        effect.getPotion().affectEntity(null, null, target, getEffectAmplifier(effect), 0.5D);
                    } else {
                        EffectInstance potion = new EffectInstance(effect.getPotion(), getEffectDuration(effect), getEffectAmplifier(effect), effect.isAmbient(), effect.doesShowParticles());
                        target.addPotionEffect(potion);
                    }
                }
            }
        }
        inputTank.modify(-FLUID_AMOUNT);
        renderFluid = inputTank.getFluidStack();
    }

    protected void diffuseClient() {

        if (renderFluid.getAmount() < FLUID_AMOUNT) {
            return;
        }
        AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -1, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> targets = world.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE);
        if (targets.isEmpty()) {
            return;
        }
        ThermalCore.PROXY.spawnDiffuserParticles(this);
    }

    protected int getEffectAmplifier(EffectInstance effect) {

        return Math.min(MAX_POTION_AMPLIFIER, Math.round(effect.getAmplifier() + potionAmpMod + boostAmplifier));
    }

    protected int getEffectDuration(EffectInstance effect) {

        return Math.min(MAX_POTION_DURATION, Math.round(effect.getDuration() * (1 + potionDurMod + boostDuration))) / 4;
    }
    // endregion

    // region AUGMENTS
    protected float potionAmpMod = 0.0F;
    protected float potionDurMod = 0.0F;

    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        radius = RADIUS;

        potionAmpMod = 0.0F;
        potionDurMod = 0.0F;
    }

    @Override
    protected void setAttributesFromAugment(CompoundNBT augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);

        potionAmpMod += getAttributeMod(augmentData, TAG_AUGMENT_POTION_AMPLIFIER);
        potionDurMod += getAttributeMod(augmentData, TAG_AUGMENT_POTION_DURATION);
    }
    // endregion
}
