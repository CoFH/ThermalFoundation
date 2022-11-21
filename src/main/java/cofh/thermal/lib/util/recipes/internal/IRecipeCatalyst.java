package cofh.thermal.lib.util.recipes.internal;

public interface IRecipeCatalyst {

    default float getPrimaryMod() {

        return 1.0F;
    }

    default float getSecondaryMod() {

        return 1.0F;
    }

    default float getEnergyMod() {

        return 1.0F;
    }

    default float getXpMod() {

        return 1.0F;
    }

    default float getMinOutputChance() {

        return 0.0F;
    }

    default float getUseChance() {

        return 1.0F;
    }

}
