package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.HiveExtractorMapping;
import cofh.thermal.core.util.recipes.device.RockGenMapping;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipe;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.RockGen")
public class CRTRockGenManager implements IRecipeManager {

    @ZenCodeType.Method
    public void addMapping(String name, IItemStack result, Block adjacent, Block below, int time) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        RockGenMapping mapping = new RockGenMapping(resourceLocation, time, below, adjacent, result.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public IRecipeType<RockGenMapping> getRecipeType() {

        return TCoreRecipeTypes.MAPPING_ROCK_GEN;
    }

    @Override
    public void removeRecipe(IIngredient output) {
        removeMapping(output);
    }

    @ZenCodeType.Method
    public void removeMapping(IIngredient output) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof RockGenMapping) {
                return output.matches(new MCItemStackMutable(((RockGenMapping) recipe).getResult()));
            }
            return false;
        }));
    }



}
