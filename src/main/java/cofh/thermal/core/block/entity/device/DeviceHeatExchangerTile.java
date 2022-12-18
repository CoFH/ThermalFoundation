package cofh.thermal.core.block.entity.device;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.IAreaEffectTile;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.thermal.lib.tileentity.DeviceTileBase;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreTileEntities.DEVICE_HEAT_EXCHANGER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DeviceHeatExchangerTile extends DeviceTileBase implements ITickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_AREA_EFFECT);

    public DeviceHeatExchangerTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(DEVICE_HEAT_EXCHANGER_TILE.get(), pos, state);
    }

    @Override
    protected void updateActiveState(boolean prevActive) {

        super.updateActiveState(prevActive);
    }

    @Override
    public void tickServer() {

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        // return new DeviceHeatExchangerContainer(i, level, worldPosition, inventory, player);
        return null;
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}
