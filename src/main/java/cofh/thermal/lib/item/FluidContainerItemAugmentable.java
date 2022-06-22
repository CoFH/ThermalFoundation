package cofh.thermal.lib.item;

import cofh.core.content.item.FluidContainerItem;
import cofh.core.content.item.IAugmentableItem;
import cofh.core.util.helpers.AugmentDataHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

import static cofh.core.util.helpers.AugmentableHelper.getPropertyWithDefault;
import static cofh.core.util.helpers.AugmentableHelper.setAttributeFromAugmentMax;
import static cofh.lib.api.ContainerType.FLUID;
import static cofh.lib.util.Constants.MAX_POTION_AMPLIFIER;
import static cofh.lib.util.Constants.MAX_POTION_DURATION;
import static cofh.lib.util.constants.NBTTags.*;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class FluidContainerItemAugmentable extends FluidContainerItem implements IAugmentableItem {

    protected IntSupplier numSlots = () -> 0;
    protected BiPredicate<ItemStack, List<ItemStack>> augValidator = (e, f) -> true;

    public FluidContainerItemAugmentable(Properties builder, int fluidCapacity, Predicate<FluidStack> validator) {

        super(builder, fluidCapacity, validator);
    }

    public FluidContainerItemAugmentable(Properties builder, int fluidCapacity) {

        super(builder, fluidCapacity);
    }

    public FluidContainerItemAugmentable setNumSlots(IntSupplier numSlots) {

        this.numSlots = numSlots;
        return this;
    }

    public FluidContainerItemAugmentable setAugValidator(BiPredicate<ItemStack, List<ItemStack>> augValidator) {

        this.augValidator = augValidator;
        return this;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {

        return Math.round(super.getItemEnchantability(stack) * getBaseMod(stack));
    }

    protected float getBaseMod(ItemStack stack) {

        return getPropertyWithDefault(stack, TAG_AUGMENT_BASE_MOD, 1.0F);
    }

    protected void setAttributesFromAugment(ItemStack container, CompoundTag augmentData) {

        CompoundTag subTag = container.getTagElement(TAG_PROPERTIES);
        if (subTag == null) {
            return;
        }
        setAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_BASE_MOD);
        setAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_FLUID_STORAGE);
        setAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_FLUID_CREATIVE);
    }

    protected int getEffectAmplifier(MobEffectInstance effect, ItemStack stack) {

        return Math.min(MAX_POTION_AMPLIFIER, Math.round(effect.getAmplifier() + getPotionAmplifierMod(stack)));
    }

    protected int getEffectDuration(MobEffectInstance effect, ItemStack stack) {

        return Math.min(MAX_POTION_DURATION, Math.round(effect.getDuration() * getPotionDurationMod(stack)));
    }

    protected float getPotionAmplifierMod(ItemStack stack) {

        return getPropertyWithDefault(stack, TAG_AUGMENT_POTION_AMPLIFIER, 0.0F);
    }

    protected float getPotionDurationMod(ItemStack stack) {

        return 1.0F + getPropertyWithDefault(stack, TAG_AUGMENT_POTION_DURATION, 0.0F);
    }

    // region IFluidContainerItem
    @Override
    public int getCapacity(ItemStack container) {

        float base = getPropertyWithDefault(container, TAG_AUGMENT_BASE_MOD, 1.0F);
        float mod = getPropertyWithDefault(container, TAG_AUGMENT_FLUID_STORAGE, 1.0F);
        return getMaxStored(container, Math.round(fluidCapacity * mod * base));
    }
    // endregion

    // region IAugmentableItem
    @Override
    public int getAugmentSlots(ItemStack augmentable) {

        return numSlots.getAsInt();
    }

    @Override
    public boolean validAugment(ItemStack augmentable, ItemStack augment, List<ItemStack> augments) {

        return augValidator.test(augment, augments);
    }

    @Override
    public void updateAugmentState(ItemStack container, List<ItemStack> augments) {

        container.getOrCreateTag().put(TAG_PROPERTIES, new CompoundTag());
        for (ItemStack augment : augments) {
            CompoundTag augmentData = AugmentDataHelper.getAugmentData(augment);
            if (augmentData == null) {
                continue;
            }
            setAttributesFromAugment(container, augmentData);
        }
        FluidStack fluid = getFluid(container);
        if (isCreative(container, FLUID)) {
            if (!fluid.isEmpty()) {
                fill(container, new FluidStack(fluid, getSpace(container)), EXECUTE);
            }
        } else {
            int fluidExcess = getFluidAmount(container) - getCapacity(container);
            if (fluidExcess > 0) {
                drain(container, fluidExcess, EXECUTE);
            }
        }
    }
    // endregion
}
