package cofh.thermal.lib.compat.jei;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.constants.Constants.BASE_CHANCE;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public abstract class ThermalRecipeCategory<T extends ThermalRecipe> implements IRecipeCategory<T> {

    protected final int ENERGY_X = 2;
    protected final int ENERGY_Y = 10;

    protected final int EXP_X = 20;
    protected final int EXP_Y = 10;

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    protected IDrawableStatic energyBackground;
    protected IDrawableStatic progressBackground;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableStatic speedBackground;

    protected IDrawableAnimated energy;
    protected IDrawableAnimated progress;
    protected IDrawableAnimated progressFluid;
    protected IDrawableAnimated speed;

    public ThermalRecipeCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);

        energyBackground = Drawables.getDrawables(guiHelper).getEnergyEmpty();
        energy = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getEnergyFill(), 400, IDrawableAnimated.StartDirection.TOP, true);
    }

    protected void addDefaultItemTooltipCallback(IGuiItemStackGroup group, List<Float> chances, int indexOffset) {

        group.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (!chances.isEmpty() && slotIndex >= indexOffset && slotIndex < indexOffset + chances.size()) {
                float chance = Math.abs(chances.get(slotIndex - indexOffset));
                if (chance < BASE_CHANCE) {
                    tooltip.add(getTextComponent("info.cofh.chance").appendString(": " + (int) (100 * chance) + "%"));
                } else {
                    chance -= (int) chance;
                    if (chance > 0) {
                        tooltip.add(getTextComponent("info.cofh.chance_additional").appendString(": " + (int) (100 * chance) + "%"));
                    }
                }
            }
        });
    }

    protected void addCatalyzedItemTooltipCallback(IGuiItemStackGroup group, List<Float> chances, int indexOffset) {

        group.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (slotIndex == indexOffset - 1) {
                tooltip.add(getTextComponent("info.cofh.optional_catalyst"));
            } else if (!chances.isEmpty() && slotIndex >= indexOffset && slotIndex < indexOffset + chances.size()) {
                float chance = Math.abs(chances.get(slotIndex - indexOffset));
                if (chance < BASE_CHANCE) {
                    tooltip.add(getTextComponent("info.cofh.chance").appendString(": " + (int) (100 * chance) + "%"));
                } else {
                    chance -= (int) chance;
                    if (chance > 0) {
                        tooltip.add(getTextComponent("info.cofh.chance_additional").appendString(": " + (int) (100 * chance) + "%"));
                    }
                }
            }
        });
    }

    // region IRecipeCategory
    @Override
    public ResourceLocation getUid() {

        return uid;
    }

    @Override
    public String getTitle() {

        return name.getString();
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
    public void draw(T recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

        if (recipe.getEnergy() > 0) {
            energyBackground.draw(matrixStack, ENERGY_X, ENERGY_Y);
            energy.draw(matrixStack, ENERGY_X, ENERGY_Y);
        }
    }

    @Override
    public List<ITextComponent> getTooltipStrings(T recipe, double mouseX, double mouseY) {

        List<ITextComponent> tooltip = new ArrayList<>();

        if (recipe.getEnergy() > 0 && mouseX > ENERGY_X && mouseX < ENERGY_X + energy.getWidth() - 1 && mouseY > ENERGY_Y && mouseY < ENERGY_Y + energy.getHeight() - 1) {
            tooltip.add(getTextComponent("info.cofh.energy").appendString(": " + StringHelper.format(recipe.getEnergy()) + " RF"));
        }
        //        if (recipe.getXp() > 0 && mouseX > EXP_X && mouseX < EXP_X + xp.getWidth() - 1 && mouseY > EXP_Y && mouseY < EXP_Y + xp.getHeight() - 1) {
        //            tooltip.add(getTextComponent("info.cofh.xp").appendString(": " + recipe.getXp() + " XP"));
        //        }
        return tooltip;
    }
    // endregion
}