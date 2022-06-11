package cofh.thermal.core.compat.jei.device;

import cofh.core.util.helpers.RenderHelper;
import cofh.thermal.core.client.gui.device.DeviceTreeExtractorScreen;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.lib.compat.jei.Drawables;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static cofh.lib.util.constants.Constants.TANK_MEDIUM;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.defaultFluidTooltip;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.tankSize;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_TREE_EXTRACTOR_BLOCK;

public class TreeExtractorCategory implements IRecipeCategory<TreeExtractorMapping> {

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected Component name;

    protected IDrawableStatic tankBackground;
    protected IDrawableStatic tankOverlay;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableAnimated progressFluid;

    public TreeExtractorCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, icon);

        background = guiHelper.drawableBuilder(DeviceTreeExtractorScreen.TEXTURE, 86, 11, 80, 62)
                .addPadding(0, 0, 16, 68)
                .build();
        name = getTextComponent(DEVICE_TREE_EXTRACTOR_BLOCK.getDescriptionId());

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
    public void setRecipe(IRecipeLayoutBuilder builder, TreeExtractorMapping recipe, IFocusGroup focuses) {

        ItemStack trunk = new ItemStack(recipe.getTrunk());
        ItemStack leaves = new ItemStack(recipe.getLeaves());

        builder.addSlot(RecipeIngredientRole.INPUT, 35, 41).addItemStack(trunk);
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 23).addItemStack(trunk);
        builder.addSlot(RecipeIngredientRole.INPUT, 17, 14).addItemStack(leaves);
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 5).addItemStack(leaves);
        builder.addSlot(RecipeIngredientRole.INPUT, 53, 14).addItemStack(leaves);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 11)
                .addIngredients(VanillaTypes.FLUID, List.of(recipe.getFluid()))
                .setFluidRenderer(tankSize(TANK_MEDIUM), false, 16, 40)
                .setOverlay(tankOverlay, 0, 0)
                .addTooltipCallback(defaultFluidTooltip());
    }

    @Override
    public void draw(TreeExtractorMapping recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {

        tankBackground.draw(matrixStack, 115, 10);

        RenderHelper.drawFluid(matrixStack, 78, 23, recipe.getFluid(), 24, 16);
        progressFluidBackground.draw(matrixStack, 78, 23);
        progressFluid.draw(matrixStack, 78, 23);
    }

}