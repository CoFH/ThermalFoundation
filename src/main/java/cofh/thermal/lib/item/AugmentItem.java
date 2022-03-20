package cofh.thermal.lib.item;

import cofh.core.item.ItemCoFH;
import cofh.lib.item.IAugmentItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AugmentItem extends ItemCoFH implements IAugmentItem {

    private CompoundTag augmentData;

    public AugmentItem(Properties builder, CompoundTag augmentData) {

        super(builder);
        setAugmentData(augmentData);
    }

    public boolean setAugmentData(CompoundTag augmentData) {

        if (augmentData == null || augmentData.isEmpty()) {
            return false;
        }
        this.augmentData = augmentData;
        return true;
    }

    // region IAugmentItem
    @Nullable
    @Override
    public CompoundTag getAugmentData(ItemStack augment) {

        return augmentData;
    }
    // endregion
}
