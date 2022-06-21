package cofh.thermal.core.util.recipes.device;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class FisherBoostSerializer implements RecipeSerializer<FisherBoost> {

    @Override
    public FisherBoost fromJson(ResourceLocation recipeId, JsonObject json) {

        Ingredient ingredient;
        ResourceLocation lootTable = BuiltInLootTables.FISHING_FISH;
        float outputMod = 1.0F;
        float useChance = 1.0F;

        /* INPUT */
        ingredient = parseIngredient(json.get(INGREDIENT));

        if (json.has(LOOT_TABLE)) {
            String lootTableString = json.get(LOOT_TABLE).getAsString();
            lootTable = ResourceLocation.tryParse(lootTableString);
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
    public FisherBoost fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        Ingredient ingredient = Ingredient.fromNetwork(buffer);

        ResourceLocation lootTable = buffer.readResourceLocation();
        float outputMod = buffer.readFloat();
        float useChance = buffer.readFloat();

        return new FisherBoost(recipeId, ingredient, lootTable, outputMod, useChance);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, FisherBoost recipe) {

        recipe.ingredient.toNetwork(buffer);

        buffer.writeResourceLocation(recipe.lootTable);
        buffer.writeFloat(recipe.outputMod);
        buffer.writeFloat(recipe.useChance);
    }

}
