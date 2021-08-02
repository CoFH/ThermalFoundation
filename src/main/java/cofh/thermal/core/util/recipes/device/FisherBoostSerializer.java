package cofh.thermal.core.util.recipes.device;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootTables;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class FisherBoostSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FisherBoost> {

    @Override
    public FisherBoost read(ResourceLocation recipeId, JsonObject json) {

        Ingredient ingredient;
        ResourceLocation lootTable = LootTables.GAMEPLAY_FISHING_FISH;
        float outputMod = 1.0F;
        float useChance = 1.0F;

        /* INPUT */
        ingredient = parseIngredient(json.get(INGREDIENT));

        if (json.has(LOOT_TABLE)) {
            String lootTableString = json.get(LOOT_TABLE).getAsString();
            lootTable = ResourceLocation.tryCreate(lootTableString);
        }
        if (json.has(OUTPUT)) {
            outputMod = json.get(OUTPUT).getAsFloat();
        } else if (json.has(OUTPUT_MOD)) {
            outputMod = json.get(OUTPUT_MOD).getAsFloat();
        }
        if (json.has(USE_CHANCE)) {
            useChance = json.get(USE_CHANCE).getAsFloat();
        }
        return new FisherBoost(recipeId, ingredient, lootTable, outputMod, useChance);
    }

    @Nullable
    @Override
    public FisherBoost read(ResourceLocation recipeId, PacketBuffer buffer) {

        Ingredient ingredient = Ingredient.read(buffer);

        ResourceLocation lootTable = buffer.readResourceLocation();
        float outputMod = buffer.readFloat();
        float useChance = buffer.readFloat();

        return new FisherBoost(recipeId, ingredient, lootTable, outputMod, useChance);
    }

    @Override
    public void write(PacketBuffer buffer, FisherBoost recipe) {

        recipe.ingredient.write(buffer);

        buffer.writeResourceLocation(recipe.lootTable);
        buffer.writeFloat(recipe.outputMod);
        buffer.writeFloat(recipe.useChance);
    }

}
