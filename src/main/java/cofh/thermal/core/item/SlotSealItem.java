package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import net.minecraft.item.ItemStack;

public class SlotSealItem extends ItemCoFH {

    public SlotSealItem(Properties builder) {

        super(builder);
    }

    @Override
    public int hashCode() {

        return 0;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {

        return new ItemStack(this);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {

        return true;
    }

}
