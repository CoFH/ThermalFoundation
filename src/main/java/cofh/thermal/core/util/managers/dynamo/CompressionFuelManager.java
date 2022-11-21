package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleFluidFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import static cofh.thermal.core.init.TCoreRecipeTypes.COMPRESSION_FUEL;

public class CompressionFuelManager extends SingleFluidFuelManager {

    private static final CompressionFuelManager INSTANCE = new CompressionFuelManager();
    protected static final int DEFAULT_ENERGY = 100000;

    public static CompressionFuelManager instance() {

        return INSTANCE;
    }

    private CompressionFuelManager() {

        super(DEFAULT_ENERGY);
    }

    public int getEnergy(FluidStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.byType(COMPRESSION_FUEL.get());
        for (var entry : recipes.entrySet()) {
            addFuel(entry.getValue());
        }
    }
    // endregion
}
