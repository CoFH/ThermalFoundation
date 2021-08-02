package cofh.thermal.core.util.recipes.device;

import cofh.thermal.core.util.managers.device.RockGenManager;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class RockGenMappingSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RockGenMapping> {

    @Override
    public RockGenMapping read(ResourceLocation recipeId, JsonObject json) {

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
    public RockGenMapping read(ResourceLocation recipeId, PacketBuffer buffer) {

        int time = buffer.readInt();
        Block trunk = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        Block leaves = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        ItemStack result = buffer.readItemStack();

        return new RockGenMapping(recipeId, time, trunk, leaves, result);
    }

    @Override
    public void write(PacketBuffer buffer, RockGenMapping recipe) {

        buffer.writeInt(recipe.time);
        buffer.writeResourceLocation(recipe.below.getRegistryName());
        buffer.writeResourceLocation(recipe.adjacent.getRegistryName());
        buffer.writeItemStack(recipe.result);
    }

}
