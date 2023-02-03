package cofh.thermal.core.block.entity.device;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.IAreaEffectTile;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.thermal.core.client.ThermalTextures;
import cofh.thermal.lib.block.entity.DeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.UNDERLAY;
import static cofh.core.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreTileEntities.DEVICE_XP_CONDENSER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DeviceXpCondenserTile extends DeviceBlockEntity implements ITickableTile.IServerTickable, IAreaEffectTile {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_AREA_EFFECT);

    protected static final IModelData MODEL_DATA = new ModelDataMap.Builder()
            .withInitial(UNDERLAY, ThermalTextures.DEVICE_COLLECTOR_UNDERLAY_LOC)
            .build();

    protected static final int RADIUS = 4;
    public int radius = RADIUS;
    protected AABB area;

    public DeviceXpCondenserTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(DEVICE_XP_CONDENSER_TILE.get(), pos, state);
    }

    @Override
    protected void updateActiveState(boolean prevActive) {

        super.updateActiveState(prevActive);
    }

    @Override
    public void tickServer() {

    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return MODEL_DATA;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        // return new DeviceXpCondenserContainer(i, level, worldPosition, inventory, player);
        return null;
    }

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
