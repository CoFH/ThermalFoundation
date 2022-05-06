package cofh.thermal.core.block.entity.device;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.block.entity.IAreaEffectTile;
import cofh.lib.block.entity.ICoFHTickableTile;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DevicePotionDiffuserContainer;
import cofh.thermal.core.util.managers.device.PotionDiffuserManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.util.StorageGroup.ACCESSIBLE;
import static cofh.lib.util.constants.Constants.*;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_POTION_DIFFUSER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DevicePotionDiffuserTile extends DeviceTileBase implements ICoFHTickableTile, IAreaEffectTile {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER, TAG_AUGMENT_TYPE_POTION);

    protected static final int TIME_CONSTANT = 60;

    protected ItemStorageCoFH inputSlot = new ItemStorageCoFH(item -> filter.valid(item) && PotionDiffuserManager.instance().validBoost(item));
    protected FluidStorageCoFH inputTank = new FluidStorageCoFH(TANK_MEDIUM, fluid -> filter.valid(fluid) && FluidHelper.hasPotionTag(fluid));

    protected static final int FLUID_AMOUNT = 25;
    protected static final int RADIUS = 4;
    protected int radius = RADIUS;
    protected AABB area;

    protected boolean cached;
    protected List<MobEffectInstance> effects = Collections.emptyList();
    protected boolean instant;

    protected int process = 1;

    protected int boostCycles;
    protected int boostMax = PotionDiffuserManager.instance().getDefaultEnergy();
    protected int boostAmplifier;
    protected float boostDuration;

    public DevicePotionDiffuserTile(BlockPos pos, BlockState state) {

        super(DEVICE_POTION_DIFFUSER_TILE, pos, state);

        inventory.addSlot(inputSlot, ACCESSIBLE);

        tankInv.addTank(inputTank, ACCESSIBLE);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
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

        if (inputTank.getAmount() < FLUID_AMOUNT) {
            cached = false;
            return false;
        }
        return true;
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
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DevicePotionDiffuserContainer(i, level, worldPosition, inventory, player);
    }

    // region GUI
    @Override
    public int getScaledDuration(int scale) {

        return !isActive || boostCycles <= 0 || boostMax <= 0 ? 0 : scale * boostCycles / boostMax;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONTROL
    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // GUI
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(boostCycles);
        buffer.writeInt(boostMax);
        buffer.writeInt(boostAmplifier);
        buffer.writeFloat(boostDuration);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        boostCycles = buffer.readInt();
        boostMax = buffer.readInt();
        boostAmplifier = buffer.readInt();
        boostDuration = buffer.readFloat();
    }

    // STATE
    @Override
    public FriendlyByteBuf getStatePacket(FriendlyByteBuf buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(process);
        buffer.writeBoolean(instant);

        return buffer;
    }

    @Override
    public void handleStatePacket(FriendlyByteBuf buffer) {

        super.handleStatePacket(buffer);

        process = buffer.readInt();
        instant = buffer.readBoolean();

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        boostCycles = nbt.getInt(TAG_BOOST_CYCLES);
        boostMax = nbt.getInt(TAG_BOOST_MAX);
        boostAmplifier = nbt.getInt(TAG_BOOST_AMP);
        boostDuration = nbt.getFloat(TAG_BOOST_DUR);

        instant = nbt.getBoolean(TAG_INSTANT);
        process = nbt.getInt(TAG_PROCESS);

        cacheEffects();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_BOOST_CYCLES, boostCycles);
        nbt.putInt(TAG_BOOST_MAX, boostMax);
        nbt.putInt(TAG_BOOST_AMP, boostAmplifier);
        nbt.putFloat(TAG_BOOST_DUR, boostDuration);

        nbt.putBoolean(TAG_INSTANT, instant);
        nbt.putInt(TAG_PROCESS, process);
    }
    // endregion

    // region HELPERS
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
            effects = PotionUtils.getAllEffects(inputTank.getFluidStack().getTag());
            for (MobEffectInstance effect : effects) {
                instant |= effect.getEffect().isInstantenous();
            }
            cached = true;
        }
    }

    protected void diffuse() {

        if (inputTank.getAmount() < FLUID_AMOUNT) {
            return;
        }
        if (effects.isEmpty() || level == null) {
            return;
        }
        AABB area = getArea();
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        if (targets.isEmpty()) { // TODO: Proximity sensor aug?
            return;
        }
        if (boostCycles > 0) {
            --boostCycles;
        } else if (PotionDiffuserManager.instance().validBoost(inputSlot.getItemStack())) {
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
            if (target.isAffectedByPotions()) {
                for (MobEffectInstance effect : effects) {
                    if (effect.getEffect().isInstantenous()) {
                        effect.getEffect().applyInstantenousEffect(null, null, target, getEffectAmplifier(effect), 0.5D);
                    } else {
                        MobEffectInstance potion = new MobEffectInstance(effect.getEffect(), getEffectDuration(effect), getEffectAmplifier(effect), effect.isAmbient(), effect.isVisible());
                        target.addEffect(potion);
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
        AABB area = new AABB(worldPosition.offset(-radius, -1, -radius), worldPosition.offset(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE);
        if (targets.isEmpty()) {
            return;
        }
        ThermalCore.PROXY.spawnDiffuserParticles(this);
    }

    protected int getEffectAmplifier(MobEffectInstance effect) {

        return Math.min(MAX_POTION_AMPLIFIER, Math.round(effect.getAmplifier() + potionAmpMod + boostAmplifier));
    }

    protected int getEffectDuration(MobEffectInstance effect) {

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
    protected void setAttributesFromAugment(CompoundTag augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);

        potionAmpMod += getAttributeMod(augmentData, TAG_AUGMENT_POTION_AMPLIFIER);
        potionDurMod += getAttributeMod(augmentData, TAG_AUGMENT_POTION_DURATION);
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);

        area = null;
    }
    // endregion

    // region IAreaEffectTile
    @Override
    public AABB getArea() {

        if (area == null) {
            area = new AABB(worldPosition.offset(-radius, -1, -radius), worldPosition.offset(1 + radius, 1 + radius, 1 + radius));
        }
        return area;
    }

    @Override
    public int getColor() {

        return isActive ? renderFluid.isEmpty() ? 0xF800F8 : FluidHelper.color(renderFluid) : 0x555555;
    }
    // endregion
}
