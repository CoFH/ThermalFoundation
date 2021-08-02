package cofh.thermal.core.compat.jei.device;

import cofh.core.util.helpers.RenderHelper;
import cofh.thermal.core.client.gui.device.DeviceRockGenScreen;
import cofh.thermal.core.util.recipes.device.RockGenMapping;
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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_ROCK_GEN_BLOCK;
import static cofh.thermal.lib.compat.jei.Drawables.SLOT;

public class RockGenCategory implements IRecipeCategory<RockGenMapping> {

    protected static final FluidStack LAVA_FLUID = new FluidStack(Fluids.LAVA, BUCKET_VOLUME);

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    protected IDrawableStatic slot;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableAnimated progressFluid;

    public RockGenCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);

        background = guiHelper.drawableBuilder(DeviceRockGenScreen.TEXTURE, 26, 11, 140, 62)
                .addPadding(0, 0, 16, 8)
                .build();
        name = getTextComponent(DEVICE_ROCK_GEN_BLOCK.getTranslationKey());

        slot = Drawables.getDrawables(guiHelper).getSlot(SLOT);
        progressFluidBackground = Drawables.getDrawables(guiHelper).getProgressFill(Drawables.PROGRESS_ARROW_FLUID);
        progressFluid = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_ARROW_FLUID), 40, IDrawableAnimated.StartDirection.LEFT, true);
    }

    // region IRecipeCategory
    @Override
    public ResourceLocation getUid() {

        return uid;
    }

    @Override
    public Class<? extends RockGenMapping> getRecipeClass() {

        return RockGenMapping.class;
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
    public void setIngredients(RockGenMapping recipe, IIngredients ingredients) {

        ArrayList<FluidStack> inputFluids = new ArrayList<>();
        ArrayList<ItemStack> inputItems = new ArrayList<>();
        inputFluids.add(LAVA_FLUID);

        Block adjacent = recipe.getAdjacent();
        Block below = recipe.getBelow();

        if (adjacent instanceof FlowingFluidBlock) {
            Fluid fluid = ((FlowingFluidBlock) adjacent).getFluid();
            inputFluids.add(new FluidStack(fluid, BUCKET_VOLUME));
        } else if (adjacent != Blocks.AIR) {
            inputItems.add(new ItemStack(adjacent));
        }
        if (below instanceof FlowingFluidBlock) {
            Fluid fluid = ((FlowingFluidBlock) below).getFluid();
            inputFluids.add(new FluidStack(fluid, BUCKET_VOLUME));
        } else if (below != Blocks.AIR) {
            inputItems.add(new ItemStack(below));
        }
        ingredients.setInputs(VanillaTypes.FLUID, inputFluids);
        ingredients.setInputs(VanillaTypes.ITEM, inputItems);
        ingredients.setOutputs(VanillaTypes.ITEM, Collections.singletonList(recipe.getResult()));
    }

    @Override
    public void setRecipe(IRecipeLayout layout, RockGenMapping recipe, IIngredients ingredients) {

        int itemCount = 1;
        int fluidCount = 1;

        List<List<FluidStack>> inputFluids = ingredients.getInputs(VanillaTypes.FLUID);
        List<List<ItemStack>> inputItems = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = layout.getFluidStacks();

        Block adjacent = recipe.getAdjacent();
        Block below = recipe.getBelow();

        guiItemStacks.init(0, false, 114, 23);
        guiFluidStacks.init(0, true, 23, 13, 16, 16, BUCKET_VOLUME, false, null);

        guiItemStacks.set(0, outputs.get(0));
        guiFluidStacks.set(0, inputFluids.get(0));

        if (adjacent instanceof FlowingFluidBlock) {
            guiFluidStacks.init(fluidCount, true, 45, 13, 16, 16, BUCKET_VOLUME, false, null);
            guiFluidStacks.set(fluidCount, inputFluids.get(fluidCount));
            ++fluidCount;
        } else if (adjacent != Blocks.AIR) {
            guiItemStacks.init(itemCount, true, 44, 12);
            guiItemStacks.set(itemCount, inputItems.get(itemCount - 1));
            ++itemCount;
        }
        if (below instanceof FlowingFluidBlock) {
            guiFluidStacks.init(fluidCount, true, 33, 33, 16, 16, BUCKET_VOLUME, false, null);
            guiFluidStacks.set(fluidCount, inputFluids.get(fluidCount));
        } else if (below != Blocks.AIR) {
            guiItemStacks.init(itemCount, true, 33, 33);
            guiItemStacks.set(itemCount, inputItems.get(itemCount - 1));
        }
    }

    @Override
    public void draw(RockGenMapping recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

        if (recipe.getBelow() != Blocks.AIR) {
            slot.draw(matrixStack, 33, 33);
        }
        RenderHelper.drawFluid(matrixStack, 74, 23, LAVA_FLUID, 24, 16);
        progressFluidBackground.draw(matrixStack, 74, 23);
        progressFluid.draw(matrixStack, 74, 23);
    }

}
