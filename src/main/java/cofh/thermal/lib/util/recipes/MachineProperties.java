package cofh.thermal.lib.util.recipes;

import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.lib.util.recipes.internal.IRecipeCatalyst;
import net.minecraft.nbt.CompoundNBT;

import static cofh.lib.util.constants.Constants.AUG_SCALE_MAX;
import static cofh.lib.util.constants.Constants.AUG_SCALE_MIN;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeModWithDefault;

public class MachineProperties implements IRecipeCatalyst {

    protected float primaryMod = 1.0F;
    protected float secondaryMod = 1.0F;
    protected float energyMod = 1.0F;
    protected float xpMod = 1.0F;
    protected float catalystMod = 1.0F;
    protected float minOutputChance = 0.0F;

    public void resetAttributes() {

        primaryMod = 1.0F;
        secondaryMod = 1.0F;
        energyMod = 1.0F;
        xpMod = 1.0F;
        catalystMod = 1.0F;
        minOutputChance = 0.0F;
    }

    public void setAttributesFromAugment(CompoundNBT augmentData) {

        primaryMod += getAttributeMod(augmentData, TAG_AUGMENT_MACHINE_PRIMARY);
        secondaryMod += getAttributeMod(augmentData, TAG_AUGMENT_MACHINE_SECONDARY);
        energyMod *= getAttributeModWithDefault(augmentData, TAG_AUGMENT_MACHINE_ENERGY, 1.0F);
        xpMod *= getAttributeModWithDefault(augmentData, TAG_AUGMENT_MACHINE_XP, 1.0F);
        catalystMod *= getAttributeModWithDefault(augmentData, TAG_AUGMENT_MACHINE_CATALYST, 1.0F);

        minOutputChance = Math.max(getAttributeMod(augmentData, TAG_AUGMENT_MACHINE_MIN_OUTPUT), minOutputChance);
    }

    public void finalizeAttributes() {

        float scaleMin = AUG_SCALE_MIN;
        float scaleMax = AUG_SCALE_MAX;

        primaryMod = MathHelper.clamp(primaryMod, scaleMin, scaleMax);
        secondaryMod = MathHelper.clamp(secondaryMod, scaleMin, scaleMax);
        energyMod = MathHelper.clamp(energyMod, scaleMin, scaleMax);
        xpMod = MathHelper.clamp(xpMod, scaleMin, scaleMax);
        catalystMod = MathHelper.clamp(catalystMod, scaleMin, scaleMax);
    }

    // region IRecipeCatalyst
    @Override
    public final float getPrimaryMod() {

        return primaryMod;
    }

    @Override
    public final float getSecondaryMod() {

        return secondaryMod;
    }

    @Override
    public final float getEnergyMod() {

        return energyMod;
    }

    @Override
    public final float getXpMod() {

        return xpMod;
    }

    @Override
    public final float getMinOutputChance() {

        return minOutputChance;
    }

    @Override
    public final float getUseChance() {

        return catalystMod;
    }
    // endregion
}
