package cofh.thermal.core.block.entity.device;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.core.util.helpers.InventoryHelper;
import cofh.lib.api.block.entity.IAreaEffectTile;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.content.xp.XpStorage;
import cofh.thermal.core.client.ThermalTextures;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceCollectorContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.UNDERLAY;
import static cofh.core.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.api.StorageGroup.ACCESSIBLE;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreTileEntities.DEVICE_COLLECTOR_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DeviceCollectorTile extends DeviceTileBase implements ITickableTile.IServerTickable, IAreaEffectTile {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER);

    protected static final int TIME_CONSTANT = 20;

    protected static final IModelData MODEL_DATA = new ModelDataMap.Builder()
            .withInitial(UNDERLAY, ThermalTextures.DEVICE_COLLECTOR_UNDERLAY_LOC)
            .build();

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

    public DeviceCollectorTile(BlockPos pos, BlockState state) {

        super(DEVICE_COLLECTOR_TILE.get(), pos, state);

        inventory.addSlots(ACCESSIBLE, 15, item -> filter.valid(item));

        xpStorage = new XpStorage(getBaseXpStorage());

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
        collectItemsAndXp();
    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return MODEL_DATA;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceCollectorContainer(i, level, worldPosition, inventory, player);
    }

    // region HELPERS
    public int getRadius() {

        return radius;
    }

    public int getTimeConstant() {

        return TIME_CONSTANT;
    }

    protected void collectItemsAndXp() {

        collectItems(getArea());

        if (xpStorageFeature) {
            collectXpOrbs(getArea());
        }
    }

    protected void collectItems(AABB area) {

        IItemHandler handler = inventory.getHandler(ACCESSIBLE);
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area, VALID_ITEM_ENTITY);

        Predicate<ItemStack> filterRules = filter.getItemRules();
        for (ItemEntity item : items) {
            ItemStack entityStack = item.getItem();
            if (!filterRules.test(entityStack)) {
                continue;
            }
            entityStack = InventoryHelper.insertStackIntoInventory(handler, entityStack, false);
            if (entityStack.isEmpty()) {
                item.discard();
            } else {
                item.setItem(entityStack);
            }
        }
    }

    protected void collectXpOrbs(AABB area) {

        List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, area, EntitySelector.ENTITY_STILL_ALIVE);

        for (ExperienceOrb orb : orbs) {
            orb.value -= xpStorage.receiveXp(orb.getValue(), false);
            if (orb.value <= 0) {
                orb.discard();
            }
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
