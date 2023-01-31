package cofh.thermal.core.compat.jei.device;

import cofh.thermal.core.client.gui.device.DeviceComposterScreen;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.getCenteredOffset;
import static cofh.thermal.core.compat.jei.TCoreJeiPlugin.roundToPlaces;
import static cofh.thermal.lib.common.ThermalIDs.ID_DEVICE_COMPOSTER;
import static cofh.thermal.lib.compat.jei.Drawables.SLOT;
import static net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES;

public class ComposterCategory implements IRecipeCategory<ComposterCategory.ComposterMapping> {

    public record ComposterMapping(ItemStack input, float chance) {}

    public static final ResourceLocation ID_MAPPING_COMPOSTER = new ResourceLocation(ID_THERMAL, "composter");

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected Component name;

    protected IDrawableStatic slot;
    protected IDrawableStatic progressBackground;
    protected IDrawableAnimated progress;

    protected static ItemStack COMPOST;

    public static List<ComposterMapping> getMappings() {

        List<ComposterMapping> mappings = new ArrayList<>();
        for (var compostable : COMPOSTABLES.object2FloatEntrySet()) {
            mappings.add(new ComposterMapping(compostable.getKey().asItem().getDefaultInstance(), compostable.getFloatValue()));
        }
        return mappings;
    }

    public ComposterCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, icon);

        background = guiHelper.drawableBuilder(DeviceComposterScreen.TEXTURE, 90, 11, 76, 62)
                .addPadding(0, 0, 80, 8)
                .build();
        name = getTextComponent(BLOCKS.get(ID_DEVICE_COMPOSTER).getDescriptionId());

        slot = Drawables.getDrawables(guiHelper).getSlot(SLOT);
        progressBackground = Drawables.getDrawables(guiHelper).getProgressFill(Drawables.PROGRESS_ARROW);
        progress = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_ARROW), 120, IDrawableAnimated.StartDirection.LEFT, true);
    }

    // region IRecipeCategory
    @Override
    public ResourceLocation getUid() {

        return uid;
    }

    @Override
    public Class<? extends ComposterMapping> getRecipeClass() {

        return ComposterMapping.class;
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
    public void setRecipe(IRecipeLayoutBuilder builder, ComposterMapping recipe, IFocusGroup focuses) {

        if (COMPOST == null) {
            COMPOST = new ItemStack(ITEMS.get("compost"));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 35, 24)
                .addItemStack(recipe.input);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 115, 24)
                .addItemStack(COMPOST);
    }

    @Override
    public void draw(ComposterMapping recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {

        slot.draw(matrixStack, 34, 23);

        progressBackground.draw(matrixStack, 69, 24);
        progress.draw(matrixStack, 69, 24);

        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        String compostPercentage = roundToPlaces(100 * recipe.chance / 8.0D, 4) + "%";
        font.draw(matrixStack, compostPercentage, getCenteredOffset(font, compostPercentage, 80), 44, 0xFF808080);
    }

}

