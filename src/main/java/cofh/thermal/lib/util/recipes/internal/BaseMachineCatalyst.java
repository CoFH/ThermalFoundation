package cofh.thermal.lib.util.recipes.internal;

public class BaseMachineCatalyst implements IRecipeCatalyst {

    protected final float primaryMod;
    protected final float secondaryMod;
    protected final float energyMod;
    protected final float minChance;
    protected final float useChance;

    public BaseMachineCatalyst(float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        this.primaryMod = primaryMod;
        this.secondaryMod = secondaryMod;
        this.energyMod = energyMod;
        this.minChance = minChance;
        this.useChance = useChance;
    }

    public float getPrimaryMod() {

        return primaryMod;
    }

    public float getSecondaryMod() {

        return secondaryMod;
    }

    public float getEnergyMod() {

        return energyMod;
    }

    public float getMinOutputChance() {

        return minChance;
    }

    public float getUseChance() {

        return useChance;
    }

}
