package cofh.thermal.lib.compat.crt.base;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import com.blamejared.crafttweaker.api.item.IIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CRTCatalyst {

    private final ResourceLocation name;
    private final Ingredient ingredient;
    private final float primaryMod;
    private final float secondaryMod;
    private final float energyMod;
    private final float minChance;
    private final float useChance;

    public CRTCatalyst(ResourceLocation name, IIngredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {

        this.name = name;
        this.ingredient = ingredient.asVanillaIngredient();
        this.primaryMod = primaryMod;
        this.secondaryMod = secondaryMod;
        this.energyMod = energyMod;
        this.minChance = minChance;
        this.useChance = useChance;
    }

    public <T extends ThermalCatalyst> T catalyst(ICatalystBuilder<T> builder) {

        return builder.apply(name, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    public interface ICatalystBuilder<T extends ThermalCatalyst> {

        T apply(ResourceLocation recipeId, Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance);

    }

}