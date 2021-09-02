package cofh.thermal.lib.compat.crt.base;

import cofh.lib.fluid.FluidIngredient;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.stream.Stream;

public class CRTHelper {
    
    /**
     * Maps a {@link CTFluidIngredient} to a {@link FluidIngredient}.
     *
     * @param ingredient The ingredient to map.
     *
     * @return A {@link FluidIngredient} representation of the given {@link CTFluidIngredient}.
     */
    public static FluidIngredient mapFluidIngredient(CTFluidIngredient ingredient) {
        return ingredient.mapTo(FluidIngredient::of, FluidIngredient::of, compound -> compound.reduce((first, second) -> FluidIngredient.of(Stream.concat(Arrays.stream(first.getFluids()), Arrays.stream(second.getFluids())))).orElseThrow(() -> new RuntimeException("Error while mapping compound fluid ingredients!")));
    }
}
