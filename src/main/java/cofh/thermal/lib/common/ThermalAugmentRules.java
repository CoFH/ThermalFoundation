package cofh.thermal.lib.common;

import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.ItemHelper;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import static cofh.lib.util.constants.NBTTags.*;

public class ThermalAugmentRules {

    private ThermalAugmentRules() {

    }

    // region PROPERTIES
    private static final Set<String> ATTR_ADD = new ObjectOpenHashSet<>();
    private static final Set<String> ATTR_MAX = new ObjectOpenHashSet<>();
    private static final Set<String> ATTR_MULT = new ObjectOpenHashSet<>();
    private static final Set<String> ATTR_INV = new ObjectOpenHashSet<>();
    private static final Set<String> ATTR_INT = new ObjectOpenHashSet<>();

    private static final Set<Item> ITEM_UNIQUE = new ObjectOpenHashSet<>();

    private static final Set<String> TYPE_EXC = new ObjectOpenHashSet<>();

    // Sets storing if an attribute is multiplicative or additive, and also if the calculation is maximized, or higher values are bad.
    static {
        // Additive
        ATTR_ADD.addAll(Arrays.asList(
                TAG_AUGMENT_DEPTH,
                TAG_AUGMENT_RADIUS,
                TAG_AUGMENT_REACH,

                TAG_AUGMENT_DYNAMO_POWER,

                TAG_AUGMENT_MACHINE_POWER,
                TAG_AUGMENT_MACHINE_PRIMARY,
                TAG_AUGMENT_MACHINE_SECONDARY,
                TAG_AUGMENT_MACHINE_SPEED,

                TAG_AUGMENT_POTION_AMPLIFIER,
                TAG_AUGMENT_POTION_DURATION
        ));
        // Multiplicative
        ATTR_MULT.addAll(Arrays.asList(
                TAG_AUGMENT_BASE_MOD,

                TAG_AUGMENT_RF_STORAGE,
                TAG_AUGMENT_RF_XFER,

                TAG_AUGMENT_FLUID_STORAGE,

                TAG_AUGMENT_DYNAMO_ENERGY,

                TAG_AUGMENT_MACHINE_CATALYST,
                TAG_AUGMENT_MACHINE_ENERGY,
                TAG_AUGMENT_MACHINE_XP
        ));
        // Maximized (Not exclusive with other sets)
        ATTR_MAX.addAll(Arrays.asList(
                TAG_AUGMENT_BASE_MOD,

                TAG_AUGMENT_RF_STORAGE,
                TAG_AUGMENT_RF_XFER,

                TAG_AUGMENT_FLUID_STORAGE,

                TAG_AUGMENT_MACHINE_MIN_OUTPUT
        ));
        // Inverse - HIGHER = WORSE (Not exclusive with other sets)
        ATTR_INV.addAll(Arrays.asList(
                TAG_AUGMENT_MACHINE_CATALYST,
                TAG_AUGMENT_MACHINE_ENERGY
        ));
        // Integer - Mod is NOT a % (Not exclusive with other sets)
        ATTR_INT.addAll(Arrays.asList(
                TAG_AUGMENT_DEPTH,
                TAG_AUGMENT_RADIUS,
                TAG_AUGMENT_REACH,

                TAG_AUGMENT_POTION_AMPLIFIER
        ));
        // Type Exclusive
        TYPE_EXC.addAll(Arrays.asList(
                TAG_AUGMENT_TYPE_FILTER,
                TAG_AUGMENT_TYPE_FLUID,
                TAG_AUGMENT_TYPE_RF,
                TAG_AUGMENT_TYPE_UPGRADE
        ));
    }

    public static boolean isTypeExclusive(String type) {

        return TYPE_EXC.contains(type);
    }

    public static boolean isUnique(ItemStack augment) {

        return ITEM_UNIQUE.contains(augment.getItem());
    }

    public static boolean isAdditive(String mod) {

        return ATTR_ADD.contains(mod);
    }

    public static boolean isMultiplicative(String mod) {

        return ATTR_MULT.contains(mod);
    }

    public static boolean isMaximized(String mod) {

        return ATTR_MAX.contains(mod);
    }

    public static boolean isInverse(String mod) {

        return ATTR_INV.contains(mod);
    }

    public static boolean isInteger(String mod) {

        return ATTR_INT.contains(mod);
    }
    // endregion

    public static void flagUniqueAugment(Item augment) {

        ITEM_UNIQUE.add(augment);
    }

    public static BiPredicate<ItemStack, List<ItemStack>> createAllowValidator(String... allowAugTypes) {

        return createAllowValidator(new ObjectOpenHashSet<>(allowAugTypes));
    }

    public static BiPredicate<ItemStack, List<ItemStack>> createAllowValidator(final Set<String> allowAugTypes) {

        return (newAugment, augments) -> {

            String newType = AugmentDataHelper.getAugmentType(newAugment);
            if (!newType.isEmpty() && !allowAugTypes.contains(newType)) {
                return false;
            }
            //            if (isTypeExclusive(newType)) {
            //                for (ItemStack augment : augments) {
            //                    if (newType.equals(AugmentDataHelper.getAugmentType(augment))) {
            //                        return false;
            //                    }
            //                }
            //            }
            if (isUnique(newAugment)) {
                for (ItemStack augment : augments) {
                    if (ItemHelper.itemsEqual(newAugment, augment)) {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    public static BiPredicate<ItemStack, List<ItemStack>> createDenyValidator(String... denyAugTypes) {

        return createDenyValidator(new ObjectOpenHashSet<>(denyAugTypes));
    }

    public static BiPredicate<ItemStack, List<ItemStack>> createDenyValidator(final Set<String> denyAugTypes) {

        return (newAugment, augments) -> {

            String newType = AugmentDataHelper.getAugmentType(newAugment);
            if (!newType.isEmpty() && denyAugTypes.contains(newType)) {
                return false;
            }
            //            if (isTypeExclusive(newType)) {
            //                for (ItemStack augment : augments) {
            //                    if (newType.equals(AugmentDataHelper.getAugmentType(augment))) {
            //                        return false;
            //                    }
            //                }
            //            }
            if (isUnique(newAugment)) {
                for (ItemStack augment : augments) {
                    if (ItemHelper.itemsEqual(newAugment, augment)) {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    // region VALIDATORS
    public static final BiPredicate<ItemStack, List<ItemStack>> DEVICE_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_DYNAMO);
    public static final BiPredicate<ItemStack, List<ItemStack>> DEVICE_NO_FLUID_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_DYNAMO, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> DYNAMO_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> DYNAMO_NO_FLUID_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> ENERGY_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_DYNAMO, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> FLUID_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_DYNAMO, TAG_AUGMENT_TYPE_RF, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> MACHINE_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_DYNAMO, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> MACHINE_NO_FLUID_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_DYNAMO, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_POTION);
    public static final BiPredicate<ItemStack, List<ItemStack>> STORAGE_VALIDATOR = createDenyValidator(TAG_AUGMENT_TYPE_MACHINE, TAG_AUGMENT_TYPE_DYNAMO, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_POTION);
    // endregion
}
