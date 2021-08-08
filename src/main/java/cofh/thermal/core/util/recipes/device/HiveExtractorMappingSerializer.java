package cofh.thermal.core.util.recipes.device;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class HiveExtractorMappingSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HiveExtractorMapping> {

    @Override
    public HiveExtractorMapping read(ResourceLocation recipeId, JsonObject json) {

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
    public HiveExtractorMapping read(ResourceLocation recipeId, PacketBuffer buffer) {

        Block hive = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        ItemStack item = buffer.readItemStack();
        FluidStack fluid = buffer.readFluidStack();

        return new HiveExtractorMapping(recipeId, hive, item, fluid);
    }

    @Override
    public void write(PacketBuffer buffer, HiveExtractorMapping recipe) {

        buffer.writeResourceLocation(recipe.hive.getRegistryName());
        buffer.writeItemStack(recipe.item);
        buffer.writeFluidStack(recipe.fluid);
    }

}
