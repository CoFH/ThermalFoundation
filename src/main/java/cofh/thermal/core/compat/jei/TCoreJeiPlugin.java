package cofh.thermal.core.compat.jei;

import cofh.core.util.helpers.FluidHelper;
import cofh.thermal.core.client.gui.device.DeviceRockGenScreen;
import cofh.thermal.core.client.gui.device.DeviceTreeExtractorScreen;
import cofh.thermal.core.compat.jei.device.RockGenCategory;
import cofh.thermal.core.compat.jei.device.TreeExtractorCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.init.TCoreRecipeTypes.*;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_ROCK_GEN_BLOCK;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_TREE_EXTRACTOR_BLOCK;
import static cofh.thermal.lib.common.ThermalConfig.jeiBucketTanks;

@JeiPlugin
public class TCoreJeiPlugin implements IModPlugin {

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        RecipeManager recipeManager = getRecipeManager();
        if (recipeManager == null) {
            // TODO: Log an error.
            return;
        }
        registration.addRecipes(recipeManager.getRecipes(MAPPING_TREE_EXTRACTOR).values(), ID_MAPPING_TREE_EXTRACTOR);
        registration.addRecipes(recipeManager.getRecipes(MAPPING_ROCK_GEN).values(), ID_MAPPING_ROCK_GEN);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(new TreeExtractorCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(DEVICE_TREE_EXTRACTOR_BLOCK), ID_MAPPING_TREE_EXTRACTOR));
        registration.addRecipeCategories(new RockGenCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(DEVICE_ROCK_GEN_BLOCK), ID_MAPPING_ROCK_GEN));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {

        int progressY = 34;
        int progressW = 24;
        int progressH = 16;

        registration.addRecipeClickArea(DeviceTreeExtractorScreen.class, 80, 35, 16, progressH, ID_MAPPING_TREE_EXTRACTOR);
        registration.addRecipeClickArea(DeviceRockGenScreen.class, 84, progressY, progressW, progressH, ID_MAPPING_ROCK_GEN);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

        registration.addRecipeCatalyst(new ItemStack(DEVICE_TREE_EXTRACTOR_BLOCK), ID_MAPPING_TREE_EXTRACTOR);
        registration.addRecipeCatalyst(new ItemStack(DEVICE_ROCK_GEN_BLOCK), ID_MAPPING_ROCK_GEN);
    }

    @Override
    public ResourceLocation getPluginUid() {

        return new ResourceLocation(ID_THERMAL, "core");
    }

    // region HELPERS
    private RecipeManager getRecipeManager() {

        RecipeManager recipeManager = null;
        ClientWorld world = Minecraft.getInstance().world;
        if (world != null) {
            recipeManager = world.getRecipeManager();
        }
        return recipeManager;
    }

    public static void addDefaultFluidTooltipCallback(IGuiFluidStackGroup group) {

        group.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (FluidHelper.hasPotionTag(ingredient)) {
                FluidHelper.addPotionTooltipStrings(ingredient, tooltip);
            }
        });
    }

    public static int tankSize(int size) {

        return jeiBucketTanks ? BUCKET_VOLUME : size;
    }

    public static IDrawable tankOverlay(IDrawable overlay) {

        return jeiBucketTanks ? null : overlay;
    }
    // endregion
}
