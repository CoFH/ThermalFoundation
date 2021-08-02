package cofh.thermal.core.util.recipes.device;

import cofh.thermal.core.util.managers.device.PotionDiffuserManager;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class PotionDiffuserBoostSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PotionDiffuserBoost> {

    @Override
    public PotionDiffuserBoost read(ResourceLocation recipeId, JsonObject json) {

        Ingredient ingredient;
        int amplifier = 0;
        float durationMod = 0.0F;
        int cycles = PotionDiffuserManager.instance().getDefaultEnergy();

        /* INPUT */
        ingredient = parseIngredient(json.get(INGREDIENT));

        if (json.has(AMPLIFIER)) {
            amplifier = json.get(AMPLIFIER).getAsInt();
        }
        if (json.has(DURATION_MOD)) {
            durationMod = json.get(DURATION_MOD).getAsFloat();
        }
        if (json.has(CYCLES)) {
            cycles = json.get(CYCLES).getAsInt();
        }
        return new PotionDiffuserBoost(recipeId, ingredient, amplifier, durationMod, cycles);
    }

    @Nullable
    @Override
    public PotionDiffuserBoost read(ResourceLocation recipeId, PacketBuffer buffer) {

        Ingredient ingredient = Ingredient.read(buffer);

        int amplifier = buffer.readInt();
        float durationMod = buffer.readFloat();
        int cycles = buffer.readInt();

        return new PotionDiffuserBoost(recipeId, ingredient, amplifier, durationMod, cycles);
    }

    @Override
    public void write(PacketBuffer buffer, PotionDiffuserBoost recipe) {

        recipe.ingredient.write(buffer);

        buffer.writeInt(recipe.amplifier);
        buffer.writeFloat(recipe.durationMod);
        buffer.writeInt(recipe.cycles);
    }

}
