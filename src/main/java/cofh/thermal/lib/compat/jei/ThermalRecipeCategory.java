package cofh.thermal.lib.compat.jei;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public abstract class ThermalRecipeCategory<T extends ThermalRecipe> implements IRecipeCategory<T> {

    protected final int ENERGY_X = 2;
    protected final int ENERGY_Y = 10;

    protected final int EXP_X = 20;
    protected final int EXP_Y = 10;

    protected final RecipeType<T> type;
    protected IDrawable background;
    protected IDrawable icon;
    protected Component name;

    protected IDrawableStatic energyBackground;
    protected IDrawableStatic progressBackground;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableStatic speedBackground;

    protected IDrawableAnimated energy;
    protected IDrawableAnimated progress;
    protected IDrawableAnimated progressFluid;
    protected IDrawableAnimated speed;

    public ThermalRecipeCategory(IGuiHelper guiHelper, ItemStack icon, RecipeType<T> type) {

        this.type = type;
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, icon);

        energyBackground = Drawables.getDrawables(guiHelper).getEnergyEmpty();
        energy = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getEnergyFill(), 400, IDrawableAnimated.StartDirection.TOP, true);
    }

    // region IRecipeCategory
    @Override
    public Component getTitle() {

        return name;
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public IDrawable getIcon() {

        return icon;
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {

        if (recipe.getEnergy() > 0) {
            energyBackground.draw(matrixStack, ENERGY_X, ENERGY_Y);
            energy.draw(matrixStack, ENERGY_X, ENERGY_Y);
        }
    }

    @Override
    public List<Component> getTooltipStrings(T recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {

        List<Component> tooltip = new ArrayList<>();

        if (recipe.getEnergy() > 0 && mouseX > ENERGY_X && mouseX < ENERGY_X + energy.getWidth() - 1 && mouseY > ENERGY_Y && mouseY < ENERGY_Y + energy.getHeight() - 1) {
            tooltip.add(getTextComponent("info.cofh.energy").append(": " + StringHelper.format(recipe.getEnergy()) + " RF"));
        }
        //        if (recipe.getXp() > 0 && mouseX > EXP_X && mouseX < EXP_X + xp.getWidth() - 1 && mouseY > EXP_Y && mouseY < EXP_Y + xp.getHeight() - 1) {
        //            tooltip.add(getTextComponent("info.cofh.xp").appendString(": " + recipe.getXp() + " XP"));
        //        }
        return tooltip;
    }
    // endregion
}
