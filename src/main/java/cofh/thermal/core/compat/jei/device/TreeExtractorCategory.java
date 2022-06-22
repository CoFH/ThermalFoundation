package cofh.thermal.core.compat.jei.device;

import cofh.core.util.helpers.RenderHelper;
import cofh.thermal.core.client.gui.device.DeviceTreeExtractorScreen;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.lib.compat.jei.Drawables;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static cofh.lib.util.Constants.TANK_MEDIUM;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.defaultFluidTooltip;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.tankSize;
import static cofh.thermal.lib.common.ThermalIDs.ID_DEVICE_TREE_EXTRACTOR;

public class TreeExtractorCategory implements IRecipeCategory<TreeExtractorMapping> {

    protected final RecipeType<TreeExtractorMapping> type;
    protected IDrawable background;
    protected IDrawable icon;
    protected Component name;

    protected IDrawableStatic tankBackground;
    protected IDrawableStatic tankOverlay;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableAnimated progressFluid;

    public TreeExtractorCategory(IGuiHelper guiHelper, ItemStack icon, RecipeType<TreeExtractorMapping> type) {

        this.type = type;
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, icon);

        background = guiHelper.drawableBuilder(DeviceTreeExtractorScreen.TEXTURE, 86, 11, 80, 62)
                .addPadding(0, 0, 16, 68)
                .build();
        name = getTextComponent(BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR).getDescriptionId());

        tankBackground = Drawables.getDrawables(guiHelper).getTank(Drawables.TANK_MEDIUM);
        tankOverlay = Drawables.getDrawables(guiHelper).getTankOverlay(Drawables.TANK_MEDIUM);
        progressFluidBackground = Drawables.getDrawables(guiHelper).getProgressFill(Drawables.PROGRESS_DROP);
        progressFluid = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_DROP), 100, IDrawableAnimated.StartDirection.LEFT, true);
    }

    // region IRecipeCategory
    @Override
    public RecipeType<TreeExtractorMapping> getRecipeType() {

        return type;
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
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluid()))
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