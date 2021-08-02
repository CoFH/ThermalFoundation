package cofh.thermal.core.compat.jei.device;

import cofh.core.util.helpers.RenderHelper;
import cofh.thermal.core.client.gui.device.DeviceTreeExtractorScreen;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.lib.compat.jei.Drawables;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cofh.lib.util.constants.Constants.TANK_MEDIUM;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.*;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_TREE_EXTRACTOR_BLOCK;

public class TreeExtractorCategory implements IRecipeCategory<TreeExtractorMapping> {

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    protected IDrawableStatic tankBackground;
    protected IDrawableStatic tankOverlay;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableAnimated progressFluid;

    public TreeExtractorCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);

        background = guiHelper.drawableBuilder(DeviceTreeExtractorScreen.TEXTURE, 86, 11, 80, 62)
                .addPadding(0, 0, 16, 68)
                .build();
        name = getTextComponent(DEVICE_TREE_EXTRACTOR_BLOCK.getTranslationKey());

        tankBackground = Drawables.getDrawables(guiHelper).getTank(Drawables.TANK_MEDIUM);
        tankOverlay = Drawables.getDrawables(guiHelper).getTankOverlay(Drawables.TANK_MEDIUM);
        progressFluidBackground = Drawables.getDrawables(guiHelper).getProgressFill(Drawables.PROGRESS_DROP);
        progressFluid = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_DROP), 100, IDrawableAnimated.StartDirection.LEFT, true);
    }

    // region IRecipeCategory
    @Override
    public ResourceLocation getUid() {

        return uid;
    }

    @Override
    public Class<? extends TreeExtractorMapping> getRecipeClass() {

        return TreeExtractorMapping.class;
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
    public void setIngredients(TreeExtractorMapping recipe, IIngredients ingredients) {

        ArrayList<ItemStack> inputItems = new ArrayList<>();
        inputItems.add(new ItemStack(recipe.getTrunk()));
        inputItems.add(new ItemStack(recipe.getLeaves()));

        ingredients.setInputs(VanillaTypes.ITEM, inputItems);
        ingredients.setOutputs(VanillaTypes.FLUID, Collections.singletonList(recipe.getFluid()));
    }

    @Override
    public void setRecipe(IRecipeLayout layout, TreeExtractorMapping recipe, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<FluidStack>> outputs = ingredients.getOutputs(VanillaTypes.FLUID);

        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = layout.getFluidStacks();

        guiFluidStacks.init(0, false, 116, 11, 16, 40, tankSize(TANK_MEDIUM), false, tankOverlay(tankOverlay));

        guiItemStacks.init(0, true, 34, 40);
        guiItemStacks.init(1, true, 34, 22);
        guiItemStacks.init(2, true, 16, 13);
        guiItemStacks.init(3, true, 34, 4);
        guiItemStacks.init(4, true, 52, 13);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, inputs.get(0));
        guiItemStacks.set(2, inputs.get(1));
        guiItemStacks.set(3, inputs.get(1));
        guiItemStacks.set(4, inputs.get(1));

        guiFluidStacks.set(0, outputs.get(0));

        addDefaultFluidTooltipCallback(guiFluidStacks);
    }

    @Override
    public void draw(TreeExtractorMapping recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

        tankBackground.draw(matrixStack, 115, 10);

        RenderHelper.drawFluid(matrixStack, 78, 23, recipe.getFluid(), 24, 16);
        progressFluidBackground.draw(matrixStack, 78, 23);
        progressFluid.draw(matrixStack, 78, 23);
    }

}
