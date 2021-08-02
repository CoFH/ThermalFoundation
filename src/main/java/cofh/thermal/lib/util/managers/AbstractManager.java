package cofh.thermal.lib.util.managers;

import cofh.lib.util.ComparableItemStack;
import cofh.lib.util.ComparableItemStackNBT;
import net.minecraft.item.ItemStack;

public abstract class AbstractManager implements IManager {

    protected int defaultEnergy;
    protected float defaultScale = 1.0F;

    protected AbstractManager() {

    }

    protected AbstractManager(int defaultEnergy) {

        this.defaultEnergy = defaultEnergy;
    }

    protected AbstractManager setDefaultEnergy(int defaultEnergy) {

        if (defaultEnergy > 0) {
            this.defaultEnergy = defaultEnergy;
        }
        return this;
    }

    protected AbstractManager setDefaultScale(int defaultScale) {

        if (defaultScale > 0) {
            this.defaultScale = defaultScale;
        }
        return this;
    }

    public static ComparableItemStack convert(ItemStack stack) {

        return new ComparableItemStack(stack);
    }

    public static ComparableItemStack convertNBT(ItemStack stack) {

        return new ComparableItemStackNBT(stack);
    }

    public int getDefaultEnergy() {

        return defaultEnergy;
    }

    public float getDefaultScale() {

        return defaultScale;
    }

}
