package cofh.thermal.core.block.entity.device;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.IAreaEffectTile;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceMagnetBlockerContainer;
import cofh.thermal.lib.block.entity.DeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DeviceMagnetBlockerTile extends DeviceBlockEntity implements ITickableTile.IServerTickable, IAreaEffectTile {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER);
    public static final Set<ItemEntity> AFFECTED_ENTITIES = Collections.newSetFromMap(new WeakHashMap<>());

    protected static final int TIME_CONSTANT = 1;

    protected static final Predicate<ItemEntity> VALID_ITEM_ENTITY = item -> {
        if (!item.isAlive() || item.hasPickUpDelay()) {
            return false;
        }
        CompoundTag data = item.getPersistentData();
        return !data.getBoolean(TAG_CONVEYOR_COMPAT) || data.getBoolean(TAG_DEMAGNETIZE_COMPAT);
    };

    protected static final int RADIUS = 4;
    public int radius = RADIUS;
    protected AABB area;

    protected int process = 1;

    public DeviceMagnetBlockerTile(BlockPos pos, BlockState state) {

        super(null /*DEVICE_MAGNET_BLOCKER_TILE.get()*/, pos, state);

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
    public void tickServer() {

        updateActiveState();

        if (!isActive) {
            return;
        }
        --process;
        if (process > 0) {
            return;
        }
        process = getTimeConstant();
        demagnetizeItems(getArea());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceMagnetBlockerContainer(i, level, worldPosition, inventory, player);
    }

    // region HELPERS
    public int getRadius() {

        return radius;
    }

    public int getTimeConstant() {

        return TIME_CONSTANT;
    }

    protected void demagnetizeItems(AABB area) {

        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area, VALID_ITEM_ENTITY);

        Predicate<ItemStack> filterRules = filter.getItemRules();
        for (ItemEntity item : items) {
            if (AFFECTED_ENTITIES.contains(item)) {
                continue;
            }
            ItemStack entityStack = item.getItem();
            if (!filterRules.test(entityStack)) {
                continue;
            }
            AFFECTED_ENTITIES.add(item);
            item.getPersistentData().putBoolean(TAG_CONVEYOR_COMPAT, true);
        }
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        radius = RADIUS;
    }

    @Override
    protected void setAttributesFromAugment(CompoundTag augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);
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

        return isActive ? 0xA81C62 : 0x555555;
    }
    // endregion
}
