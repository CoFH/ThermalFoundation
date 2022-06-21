package cofh.thermal.core.util.recipes.device;

import cofh.thermal.core.util.managers.device.RockGenManager;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class RockGenMappingSerializer implements RecipeSerializer<RockGenMapping> {

    @Override
    public RockGenMapping fromJson(ResourceLocation recipeId, JsonObject json) {

        int time = RockGenManager.instance().getDefaultEnergy();

        Block below = Blocks.AIR;
        Block adjacent = Blocks.AIR;
        ItemStack result = ItemStack.EMPTY;

        /* BELOW */
        if (json.has(BELOW)) {
            below = parseBlock(json.get(BELOW));
        } else if (json.has(BASE)) {
            below = parseBlock(json.get(BASE));
        }
        /* ADJACENT */
        if (json.has(ADJACENT)) {
            adjacent = parseBlock(json.get(ADJACENT));
        }
        /* RESULT */
        if (json.has(RESULT)) {
            result = parseItemStack(json.get(RESULT));
        } else if (json.has(ITEM)) {
            result = parseItemStack(json.get(ITEM));
        }
        /* TIME */
        if (json.has(TIME)) {
            time = json.get(TIME).getAsInt();
        } else if (json.has(TICKS)) {
            time = json.get(TICKS).getAsInt();
        }
        return new RockGenMapping(recipeId, time, below, adjacent, result);
    }

    @Nullable
    @Override
    public RockGenMapping fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        int time = buffer.readInt();
        Block trunk = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        Block leaves = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        ItemStack result = buffer.readItem();

        return new RockGenMapping(recipeId, time, trunk, leaves, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, RockGenMapping recipe) {

        buffer.writeInt(recipe.time);
        buffer.writeResourceLocation(getRegistryName(recipe.below));
        buffer.writeResourceLocation(getRegistryName(recipe.adjacent));
        buffer.writeItem(recipe.result);
    }

}
