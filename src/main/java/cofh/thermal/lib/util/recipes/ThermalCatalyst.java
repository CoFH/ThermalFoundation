package cofh.thermal.lib.util.recipes;

import cofh.lib.util.recipes.SerializableRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/**
 * This class really just serves as a way to ride on Mojang's automated recipe syncing and datapack functionality.
 * Nothing in Thermal actually uses any of this for logic whatsoever. It's part of a shim layer, nothing more.
 */
public abstract class ThermalCatalyst extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected float primaryMod = 1.0F;
    protected float secondaryMod = 1.0F;
    protected float energyMod = 1.0F;
    protected float minChance = 0.0F;
    protected float useChance = 1.0F;

    protected ThermalCatalyst(ResourceLocation recipeId, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        super(recipeId);

        this.ingredient = ingredient;

        this.primaryMod = primaryMod;
        this.secondaryMod = secondaryMod;
        this.energyMod = energyMod;
        this.minChance = minChance;
        this.useChance = useChance;
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public float getPrimaryMod() {

        return primaryMod;
    }

    public float getSecondaryMod() {

        return secondaryMod;
    }

    public float getEnergyMod() {

        return energyMod;
    }

    public float getMinChance() {

        return minChance;
    }

    public float getUseChance() {

        return useChance;
    }
    // endregion
}
