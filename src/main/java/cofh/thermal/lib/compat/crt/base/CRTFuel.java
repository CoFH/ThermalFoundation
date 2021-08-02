package cofh.thermal.lib.compat.crt.base;

import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CRTFuel {

    private final ResourceLocation name;
    private final int energy;
    private List<Ingredient> item;
    private List<FluidStack> fluid;

    public CRTFuel(ResourceLocation name, int energy) {

        this.name = name;
        this.energy = energy;
    }

    public CRTFuel item(IIngredient item) {

        this.item = Collections.singletonList(item.asVanillaIngredient());
        return this;
    }

    public CRTFuel fluid(IFluidStack fluid) {

        this.fluid = Collections.singletonList(fluid.getInternal());
        return this;
    }

    public <T extends ThermalFuel> T fuel(IFuelBuilder<T> builder) {

        return builder.apply(name, energy, item, fluid);
    }

    public interface IFuelBuilder<T extends ThermalFuel> {

        T apply(ResourceLocation recipeId, int energy, @Nullable List<Ingredient> inputItems, @Nullable List<FluidStack> inputFluids);

    }

}
