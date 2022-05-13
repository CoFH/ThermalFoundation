package cofh.thermal.lib.compat.crt;

import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helpers functions for recipe printing.
 */
public class RecipePrintingUtil {

    /**
     * Creates a CraftTweaker String representation of the given {@link Ingredient} List.
     *
     * @param ingredients The List of {@link Ingredient} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     * @return A String representing the given ingredients.
     */
    public static String stringifyIngredients(List<Ingredient> ingredients, String delimeter) {

        return ingredients.stream()
                .map(IIngredient::fromIngredient)
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }

    /**
     * Creates a CraftTweaker String representation of the given {@link ItemStack} List.
     *
     * @param ingredients The List of {@link ItemStack} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     * @return A String representing the given ingredients.
     */
    public static String stringifyStacks(List<ItemStack> ingredients, String delimeter) {

        return ingredients.stream()
                .map(MCItemStack::new)
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }

    /**
     * Creates a CraftTweaker String representation of the given {@link ItemStack} List.
     *
     * @param ingredients The List of {@link ItemStack} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     * @return A String representing the given ingredients.
     */
    public static String stringifyWeightedStacks(List<ItemStack> ingredients, List<Float> weights, String delimeter) {

        Iterator<Float> iterator = weights.iterator();
        return ingredients.stream()
                .filter(val -> iterator.hasNext())
                .map(itemStack -> new MCItemStack(itemStack).percent(iterator.next() * 100))
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }

    /**
     * Creates a CraftTweaker String representation of the given {@link ItemStack} List.
     *
     * @param ingredients The List of {@link ItemStack} to stringify.
     * @param delimeter   The string delimeter to use between the values. (` | ` for "OR'd" Ingredients, `, ` for an array).
     * @return A String representing the given ingredients.
     */
    public static String stringifyFluidStacks(List<FluidStack> ingredients, String delimeter) {

        return ingredients.stream()
                .map(MCFluidStackMutable::new)
                .map(CommandStringDisplayable::getCommandString)
                .collect(Collectors.joining(delimeter));
    }

}