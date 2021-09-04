package cofh.thermal.lib.compat.crt.base;

import cofh.lib.fluid.FluidIngredient;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import net.minecraftforge.fluids.FluidStack;

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

}
