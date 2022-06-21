package cofh.thermal.lib.compat.crt.base;

import cofh.lib.content.fluid.FluidIngredient;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CRTHelper {

    /**
     * Maps a {@link CTFluidIngredient} to a {@link FluidIngredient}.
     *
     * @param ingredient The ingredient to map.
     * @return A {@link FluidIngredient} representation of the given {@link CTFluidIngredient}.
     */
    public static FluidIngredient mapFluidIngredient(CTFluidIngredient ingredient) {

        return ingredient.mapTo(FluidIngredient::of, FluidIngredient::of, compound -> compound.reduce((first, second) -> FluidIngredient.of(Stream.concat(Arrays.stream(first.getFluids()), Arrays.stream(second.getFluids())))).orElseThrow(() -> new RuntimeException("Error while mapping compound fluid ingredients!")));
    }

    /**
     * Creates a CraftTweaker String representation of the given {@link FluidIngredient} List.
     * <p>
     * This is mainly used for printing a recipe.
     *
     * @param ingredients The List of {@link FluidIngredient} to stringify.
     * @return A String representing the given ingredients.
     */
    public static String stringifyFluidIngredients(List<FluidIngredient> ingredients) {

        return ingredients.stream().flatMap(fluidIngredient -> Arrays.stream(fluidIngredient.getFluids())).map(MCFluidStackMutable::new).map(CommandStringDisplayable::getCommandString).collect(Collectors.joining(" | "));
    }

    /**
     * Maps a {@link IIngredientWithAmount} to a vanilla {@link Ingredient} with a count.
     *
     * @param ingredient The ingredient to map/
     * @return A {@link Ingredient} representation of the given {@link IIngredientWithAmount}
     */
    public static Ingredient mapIIngredientWithAmount(IIngredientWithAmount ingredient) {

        Ingredient vanillaIngredient = ingredient.getIngredient().asVanillaIngredient();
        // This will probably end badly for some ingredients that don't use the list of ItemStacks,
        // however, it is what Thermal itself uses, so it will be on par with JSON recipes.
        for (ItemStack stack : vanillaIngredient.getItems()) {
            stack.setCount(ingredient.getAmount());
        }
        return vanillaIngredient;
    }

}
