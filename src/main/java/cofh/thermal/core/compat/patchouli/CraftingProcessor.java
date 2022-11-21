package cofh.thermal.core.compat.patchouli;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.apache.logging.log4j.LogManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class CraftingProcessor implements IComponentProcessor {

    private CraftingRecipe recipe;

    @Override
    public void setup(IVariableProvider variables) {

        if (!variables.has("recipe"))
            return;
        ResourceLocation recipeId = new ResourceLocation(variables.get("recipe").asString());
        Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(recipeId);
        if (recipe.isPresent() && recipe.get() instanceof CraftingRecipe) {
            this.recipe = (CraftingRecipe) recipe.get();
        } else {
            LogManager.getLogger().warn("Thermalpedia missing the crafting recipe: " + recipeId);
        }
    }

    @Override
    public IVariable process(String key) {

        if (recipe == null) {
            return null;
        }
        if (key.equals("out")) {
            return IVariable.from(recipe.getResultItem());
        } else if (key.startsWith("in")) {
            int index = Integer.parseInt(key.substring(key.length() - 1));
            if (recipe instanceof ShapedRecipe) {
                int width = ((ShapedRecipe) recipe).getRecipeWidth();
                if (width < 3) {
                    if (index % 3 >= width) {
                        return null;
                    }
                    index = index * width / 3 + index % 3;
                }
            }
            if (recipe.getIngredients().size() <= index) {
                return null;
            }
            return IVariable.wrapList(Arrays.stream(recipe.getIngredients().get(index).getItems()).map(IVariable::from).collect(Collectors.toList()));
        } else if (key.equals("title")) {
            return IVariable.from(recipe.getResultItem().getHoverName());
        } else if (key.equals("show")) {
            return IVariable.wrap(true);
        }
        return null;
    }

}
