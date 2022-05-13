package cofh.thermal.lib.compat.crt.base;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class CRTCatalyst {

    private final ResourceLocation name;
    private final Ingredient ingredient;
    private final float primaryMod;
    private final float secondaryMod;
    private final float energyMod;
    private final float minChance;
    private final float useChance;

    // Mainly used for Recipe Replacement.
    public CRTCatalyst(ResourceLocation name, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        this.name = name;
        this.ingredient = ingredient;
        this.primaryMod = primaryMod;
        this.secondaryMod = secondaryMod;
        this.energyMod = energyMod;
        this.minChance = minChance;
        this.useChance = useChance;
    }

    public CRTCatalyst(ResourceLocation name, IIngredientWithAmount ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        this(name, CRTHelper.mapIIngredientWithAmount(ingredient), primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    public <T extends ThermalCatalyst> T catalyst(ICatalystBuilder<T> builder) {

        return builder.apply(name, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    public interface ICatalystBuilder<T extends ThermalCatalyst> {

        T apply(ResourceLocation recipeId, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance);

    }

}
