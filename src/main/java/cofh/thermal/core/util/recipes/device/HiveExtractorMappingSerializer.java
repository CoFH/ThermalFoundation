package cofh.thermal.core.util.recipes.device;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class HiveExtractorMappingSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<HiveExtractorMapping> {

    @Override
    public HiveExtractorMapping fromJson(ResourceLocation recipeId, JsonObject json) {

        Block hive = Blocks.AIR;
        ItemStack item = ItemStack.EMPTY;
        FluidStack fluid = FluidStack.EMPTY;

        if (json.has(HIVE)) {
            hive = parseBlock(json.get(HIVE));
        }
        if (json.has(ITEM)) {
            item = parseItemStack(json.get(ITEM));
        }
        if (json.has(FLUID)) {
            fluid = parseFluidStack(json.get(FLUID));
        }
        return new HiveExtractorMapping(recipeId, hive, item, fluid);
    }

    @Nullable
    @Override
    public HiveExtractorMapping fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

        Block hive = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        ItemStack item = buffer.readItem();
        FluidStack fluid = buffer.readFluidStack();

        return new HiveExtractorMapping(recipeId, hive, item, fluid);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, HiveExtractorMapping recipe) {

        buffer.writeResourceLocation(recipe.hive.getRegistryName());
        buffer.writeItem(recipe.item);
        buffer.writeFluidStack(recipe.fluid);
    }

}
