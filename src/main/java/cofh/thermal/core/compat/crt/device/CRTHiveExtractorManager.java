package cofh.thermal.core.compat.crt.device;

import cofh.thermal.core.init.TCoreRecipeTypes;
import cofh.thermal.core.util.recipes.device.HiveExtractorMapping;
import cofh.thermal.core.util.recipes.device.RockGenMapping;
import cofh.thermal.lib.compat.crt.base.CRTHelper;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipe;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("mods.thermal.HiveExtractor")
@IRecipeHandler.For(HiveExtractorMapping.class)
public class CRTHiveExtractorManager implements IRecipeManager, IRecipeHandler<HiveExtractorMapping> {

    @ZenCodeType.Method
    public void addMapping(String name, Block hive, IItemStack item, IFluidStack fluid) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);

        if (!hive.getStateDefinition().getProperties().contains(BeehiveBlock.HONEY_LEVEL)) {
            throw new IllegalArgumentException(String.format("Cannot add Hive Extractor Mapping as provided provided block: '%s' does not have a 'honey_level' Block Property!", ExpandBlock.getCommandString(hive)));
        }

        HiveExtractorMapping mapping = new HiveExtractorMapping(resourceLocation, hive, item.getInternal(), fluid.getInternal());
        CraftTweakerAPI.apply(new ActionAddRecipe(this, mapping));
    }

    @Override
    public IRecipeType<HiveExtractorMapping> getRecipeType() {

        return TCoreRecipeTypes.MAPPING_HIVE_EXTRACTOR;
    }

    @Override
    public void removeRecipe(IIngredient output) {
        removeMapping(output);
    }

    @ZenCodeType.Method
    public void removeMapping(IIngredient output) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof HiveExtractorMapping) {
                return output.matches(new MCItemStackMutable(((HiveExtractorMapping) recipe).getItem()));
            }
            return false;
        }));
    }

    @ZenCodeType.Method
    public void removeMapping(Block hive) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof HiveExtractorMapping) {
                return hive == ((HiveExtractorMapping) recipe).getHive();
            }
            return false;
        }));
    }

    @ZenCodeType.Method
    public void removeMapping(CTFluidIngredient fluid) {
        CraftTweakerAPI.apply(new ActionRemoveRecipe(this, recipe -> {
            if (recipe instanceof HiveExtractorMapping) {
                return CRTHelper.mapFluidIngredient(fluid).test(((HiveExtractorMapping) recipe).getFluid());
            }
            return false;
        }));
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, HiveExtractorMapping recipe) {
        return String.format("<recipetype:%s>.addMapping(\"%s\", %s, %s, %s);", recipe.getType(), recipe.getId(),  ExpandBlock.getCommandString(recipe.getHive()), new MCItemStackMutable(recipe.getItem()).getCommandString(), new MCFluidStackMutable(recipe.getFluid()).getCommandString());
    }

    @Override
    public Optional<Function<ResourceLocation, HiveExtractorMapping>> replaceIngredients(IRecipeManager manager, HiveExtractorMapping recipe, List<IReplacementRule> rules) {
        // CRT doesn't support replacing blocks right now.
        return Optional.empty();
    }

}
