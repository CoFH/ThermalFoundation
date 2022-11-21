package cofh.thermal.lib.util.managers;

import cofh.lib.util.crafting.ComparableItemStack;
import cofh.lib.util.crafting.ComparableItemStackNBT;
import cofh.lib.util.helpers.MathHelper;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractManager implements IManager {

    private static final int MIN_POWER = 1;
    private static final int MAX_POWER = 200;

    protected int defaultEnergy;
    protected float defaultScale = 1.0F;

    protected int basePower = 20;

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

    public static ComparableItemStack makeComparable(ItemStack stack) {

        return new ComparableItemStack(stack);
    }

    public static ComparableItemStack makeNBTComparable(ItemStack stack) {

        return new ComparableItemStackNBT(stack);
    }

    public int getDefaultEnergy() {

        return defaultEnergy;
    }

    public float getDefaultScale() {

        return defaultScale;
    }

    public void setBasePower(int rate) {

        basePower = MathHelper.clamp(rate, getMinPower(), getMaxPower());
    }

    public int getBasePower() {

        return basePower;
    }

    public int getMinPower() {

        return MIN_POWER;
    }

    public int getMaxPower() {

        return MAX_POWER;
    }

}
