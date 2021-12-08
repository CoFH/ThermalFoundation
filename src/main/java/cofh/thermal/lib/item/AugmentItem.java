package cofh.thermal.lib.item;

import cofh.core.item.ItemCoFH;
import cofh.lib.item.IAugmentItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;

public class AugmentItem extends ItemCoFH implements IAugmentItem {

    private CompoundNBT augmentData;

    public AugmentItem(Properties builder, CompoundNBT augmentData) {

        super(builder);
        setAugmentData(augmentData);
    }

    public boolean setAugmentData(CompoundNBT augmentData) {

        if (augmentData == null || augmentData.isEmpty()) {
            return false;
        }
        this.augmentData = augmentData;
        return true;
    }

    // region IAugmentItem
    @Nullable
    @Override
    public CompoundNBT getAugmentData(ItemStack augment) {

        return augmentData;
    }
    // endregion
}
