package cofh.thermal.lib.tileentity;

import cofh.core.util.helpers.AugmentDataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.util.helpers.AugmentableHelper.getAttributeModWithDefault;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_BASE_MOD;
import static cofh.thermal.lib.common.ThermalAugmentRules.DEVICE_NO_FLUID_VALIDATOR;
import static cofh.thermal.lib.common.ThermalAugmentRules.DEVICE_VALIDATOR;

public abstract class DeviceTileBase extends ThermalTileAugmentable {

    public DeviceTileBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
    }

    protected void updateValidity() {

    }

    protected void updateActiveState() {

        boolean curActive = isActive;
        isActive = redstoneControl.getState() && isValid();
        updateActiveState(curActive);
    }

    protected boolean isValid() {

        return true;
    }

    @Override
    public void neighborChanged(Block blockIn, BlockPos fromPos) {

        super.neighborChanged(blockIn, fromPos);
        updateValidity();
        updateActiveState();
    }

    @Override
    public void onPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        super.onPlacedBy(worldIn, pos, state, placer, stack);
        updateValidity();
        updateActiveState();
    }

    // region AUGMENTS
    protected float baseMod = 1.0F;

    @Override
    protected Predicate<ItemStack> augValidator() {

        BiPredicate<ItemStack, List<ItemStack>> validator = tankInv.hasTanks() ? DEVICE_VALIDATOR : DEVICE_NO_FLUID_VALIDATOR;
        return item -> AugmentDataHelper.hasAugmentData(item) && validator.test(item, getAugmentsAsList());
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);

        baseMod = getAttributeModWithDefault(augmentNBT, TAG_AUGMENT_BASE_MOD, 1.0F);
    }
    // endregion

    // region ITileCallback
    @Override
    public void onControlUpdate() {

        updateActiveState();
        super.onControlUpdate();
    }
    // endregion
}
