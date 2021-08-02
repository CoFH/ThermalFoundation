package cofh.thermal.lib.compat.jei;

import cofh.thermal.lib.util.recipes.ThermalCatalyst;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

import static cofh.lib.util.helpers.StringHelper.DF0;
import static cofh.lib.util.helpers.StringHelper.localize;
import static java.util.Collections.singletonList;

public abstract class ThermalCatalystCategory<T extends ThermalCatalyst> implements IRecipeCategory<T> {

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    protected IDrawableStatic slot;

    public ThermalCatalystCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);

        background = guiHelper.drawableBuilder(Drawables.JEI_TEXTURE, 26, 11, 140, 62)
                .addPadding(0, 0, 16, 8)
                .build();
        slot = Drawables.getDrawables(guiHelper).getSlot(Drawables.SLOT);
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
    public void setIngredients(T recipe, IIngredients ingredients) {

        ingredients.setInputIngredients(singletonList(recipe.getIngredient()));
    }

    @Override
    public void setRecipe(IRecipeLayout layout, T recipe, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();

        guiItemStacks.init(0, true, 16, 22);
        guiItemStacks.set(0, inputs.get(0));
    }

    @Override
    public void draw(T recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

        slot.draw(matrixStack, 16, 22);

        Minecraft minecraft = Minecraft.getInstance();

        String primaryMod = localize("info.thermal.primary_mod") + ": " + recipe.getPrimaryMod() + "x";
        minecraft.fontRenderer.drawString(matrixStack, primaryMod, 44, 8, 0xFF606060);

        String secondaryMod = localize("info.thermal.secondary_mod") + ": " + recipe.getSecondaryMod() + "x";
        minecraft.fontRenderer.drawString(matrixStack, secondaryMod, 44, 20, 0xFF606060);

        String energyMod = localize("info.thermal.energy_mod") + ": " + recipe.getEnergyMod() + "x";
        minecraft.fontRenderer.drawString(matrixStack, energyMod, 44, 32, 0xFF606060);

        String useChance = localize("info.thermal.use_chance") + ": " + DF0.format(recipe.getUseChance() * 100) + "%";
        minecraft.fontRenderer.drawString(matrixStack, useChance, 44, 44, 0xFF606060);
    }
    // endregion
}