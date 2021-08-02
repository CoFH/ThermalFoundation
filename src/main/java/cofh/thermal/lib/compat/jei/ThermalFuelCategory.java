package cofh.thermal.lib.compat.jei;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.lib.util.recipes.ThermalFuel;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public abstract class ThermalFuelCategory<T extends ThermalFuel> implements IRecipeCategory<T> {

    protected final int DURATION_X = 70;
    protected final int DURATION_Y = 24;

    protected final int ENERGY_X = 106;
    protected final int ENERGY_Y = 10;

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    protected IDrawableStatic energyBackground;
    protected IDrawableStatic durationBackground;

    protected IDrawableAnimated energy;
    protected IDrawableAnimated duration;

    public ThermalFuelCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this(guiHelper, icon, uid, true);
    }

    public ThermalFuelCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid, boolean drawEnergy) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);

        if (drawEnergy) {
            energyBackground = Drawables.getDrawables(guiHelper).getEnergyEmpty();
            energy = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getEnergyFill(), 400, IDrawableAnimated.StartDirection.BOTTOM, false);
        }
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

        if (energyBackground != null) {
            energyBackground.draw(matrixStack, ENERGY_X, ENERGY_Y);
        }
        if (energy != null) {
            energy.draw(matrixStack, ENERGY_X, ENERGY_Y);
        }

        if (durationBackground != null) {
            durationBackground.draw(matrixStack, DURATION_X, DURATION_Y);
        }
        if (duration != null) {
            duration.draw(matrixStack, DURATION_X, DURATION_Y);
        }
    }

    @Override
    public List<ITextComponent> getTooltipStrings(T recipe, double mouseX, double mouseY) {

        List<ITextComponent> tooltip = new ArrayList<>();

        if (energy != null && mouseX > ENERGY_X && mouseX < ENERGY_X + energy.getWidth() - 1 && mouseY > ENERGY_Y && mouseY < ENERGY_Y + energy.getHeight() - 1) {
            tooltip.add(getTextComponent("info.cofh.energy").appendString(": " + StringHelper.format(recipe.getEnergy()) + " RF"));
        }
        return tooltip;
    }
    // endregion
}
