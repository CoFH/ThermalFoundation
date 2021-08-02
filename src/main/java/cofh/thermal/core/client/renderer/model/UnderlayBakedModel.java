package cofh.thermal.core.client.renderer.model;

import cofh.core.client.renderer.model.ModelUtils;
import cofh.core.client.renderer.model.ModelUtils.FluidCacheWrapper;
import cofh.core.util.helpers.RenderHelper;
import cofh.lib.client.renderer.model.RetexturedBakedQuad;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class UnderlayBakedModel extends BakedModelWrapper<IBakedModel> implements IDynamicBakedModel {

    private static final IdentityHashMap<FluidCacheWrapper, BakedQuad[]> FLUID_QUAD_CACHE = new IdentityHashMap<>();
    private static final IdentityHashMap<BlockState, BakedQuad[]> UNDERLAY_QUAD_CACHE = new IdentityHashMap<>();

    public static void clearCache() {

        FLUID_QUAD_CACHE.clear();
        UNDERLAY_QUAD_CACHE.clear();
    }

    public UnderlayBakedModel(IBakedModel originalModel) {

        super(originalModel);
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        return addUnderlayQuads(new LinkedList<>(originalModel.getQuads(state, side, rand, extraData)), state, side, rand, extraData);
    }

    // region HELPERS
    protected List<BakedQuad> addUnderlayQuads(LinkedList<BakedQuad> quads, @Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        if (side == null || quads.isEmpty()) {
            return quads;
        }
        BakedQuad baseQuad = quads.get(0);
        int sideIndex = side.getIndex();

        // FLUID
        if (extraData.hasProperty(ModelUtils.FLUID)) {
            FluidStack fluid = extraData.getData(ModelUtils.FLUID);
            if (fluid != null && !fluid.isEmpty()) {
                FluidCacheWrapper wrapper = new FluidCacheWrapper(state, fluid);
                BakedQuad[] cachedFluidQuads = FLUID_QUAD_CACHE.get(wrapper);
                if (cachedFluidQuads == null || cachedFluidQuads.length < 6) {
                    cachedFluidQuads = new BakedQuad[6];
                }
                if (cachedFluidQuads[sideIndex] == null) {
                    cachedFluidQuads[sideIndex] = new RetexturedBakedQuad(RenderHelper.mulColor(baseQuad, RenderHelper.getFluidColor(fluid)), RenderHelper.getFluidTexture(fluid));
                    FLUID_QUAD_CACHE.put(wrapper, cachedFluidQuads);
                }
                quads.offerFirst(cachedFluidQuads[sideIndex]);
            }
        } else if (extraData.hasProperty(ModelUtils.UNDERLAY)) {
            ResourceLocation loc = extraData.getData(ModelUtils.UNDERLAY);
            BakedQuad[] cachedUnderlayQuads = UNDERLAY_QUAD_CACHE.get(state);
            if (cachedUnderlayQuads == null || cachedUnderlayQuads.length < 6) {
                cachedUnderlayQuads = new BakedQuad[6];
            }
            if (cachedUnderlayQuads[sideIndex] == null) {
                cachedUnderlayQuads[sideIndex] = new RetexturedBakedQuad(baseQuad, RenderHelper.getTexture(loc));
                UNDERLAY_QUAD_CACHE.put(state, cachedUnderlayQuads);
            }
            quads.offerFirst(cachedUnderlayQuads[sideIndex]);
        }
        return quads;
    }
    // endregion
}
