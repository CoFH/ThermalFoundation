package cofh.thermal.core.client.renderer.model;

import cofh.core.client.renderer.model.ModelUtils;
import cofh.lib.client.renderer.model.RetexturedBakedQuad;
import cofh.lib.energy.IEnergyContainerItem;
import cofh.lib.item.ICoFHItem;
import cofh.lib.util.ComparableItemStack;
import cofh.lib.util.helpers.MathHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static cofh.lib.item.ContainerType.ENERGY;
import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;
import static cofh.lib.util.constants.NBTTags.TAG_SIDES;
import static cofh.thermal.core.client.ThermalTextures.*;
import static cofh.thermal.lib.common.ThermalConfig.DEFAULT_CELL_SIDES_RAW;
import static net.minecraft.util.Direction.*;

public class EnergyCellBakedModel extends BakedModelWrapper<IBakedModel> implements IDynamicBakedModel {

    private static final Map<List<Integer>, BakedQuad> FACE_QUAD_CACHE = new Object2ObjectOpenHashMap<>();
    private static final Int2ObjectMap<BakedQuad[]> SIDE_QUAD_CACHE = new Int2ObjectOpenHashMap<>();

    private static final Int2ObjectMap<BakedQuad[]> ITEM_QUAD_CACHE = new Int2ObjectOpenHashMap<>();
    private static final Map<List<Integer>, IBakedModel> MODEL_CACHE = new Object2ObjectOpenHashMap<>();

    public static void clearCache() {

        FACE_QUAD_CACHE.clear();
        SIDE_QUAD_CACHE.clear();

        ITEM_QUAD_CACHE.clear();
        MODEL_CACHE.clear();
    }

    public EnergyCellBakedModel(IBakedModel originalModel) {

        super(originalModel);
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        LinkedList<BakedQuad> quads = new LinkedList<>(originalModel.getQuads(state, side, rand, extraData));
        if (side == null || quads.isEmpty()) {
            return quads;
        }
        BakedQuad baseQuad = quads.get(0);
        int sideIndex = side.getIndex();

        // FACE
        Direction face = extraData.getData(ModelUtils.FACING);
        if (side == face) {
            Integer level = extraData.getData(ModelUtils.LEVEL);
            if (level == null) {
                // This shouldn't happen, but playing it safe.
                return quads;
            }
            BakedQuad faceQuad = FACE_QUAD_CACHE.get(Arrays.asList(face.getIndex(), level));
            if (faceQuad == null) {
                faceQuad = new RetexturedBakedQuad(baseQuad, getLevelTexture(level));
                FACE_QUAD_CACHE.put(Arrays.asList(face.getIndex(), level), faceQuad);
            }
            quads.add(faceQuad);
        }

        // SIDES
        byte[] sideConfigRaw = extraData.getData(ModelUtils.SIDES);
        if (sideConfigRaw == null) {
            // This shouldn't happen, but playing it safe.
            return quads;
        }
        int configHash = Arrays.hashCode(sideConfigRaw);
        BakedQuad[] cachedSideQuads = SIDE_QUAD_CACHE.get(configHash);
        if (cachedSideQuads == null || cachedSideQuads.length < 6) {
            cachedSideQuads = new BakedQuad[6];
        }
        if (cachedSideQuads[sideIndex] == null) {
            cachedSideQuads[sideIndex] = new RetexturedBakedQuad(baseQuad, getConfigTexture(sideConfigRaw[sideIndex]));
            SIDE_QUAD_CACHE.put(configHash, cachedSideQuads);
        }
        quads.add(cachedSideQuads[sideIndex]);

        return quads;
    }

    @Override
    public ItemOverrideList getOverrides() {

        return overrideList;
    }

    private final ItemOverrideList overrideList = new ItemOverrideList() {

        @Nullable
        @Override
        public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {

            CompoundNBT tag = stack.getChildTag(TAG_BLOCK_ENTITY);
            byte[] sideConfigRaw = getSideConfigRaw(tag);
            int itemHash = new ComparableItemStack(stack).hashCode();
            int level = getLevel(stack);
            int configHash = Arrays.hashCode(sideConfigRaw);

            IBakedModel ret = MODEL_CACHE.get(Arrays.asList(itemHash, level, configHash));
            if (ret == null) {
                ModelUtils.WrappedBakedModelBuilder builder = new ModelUtils.WrappedBakedModelBuilder(model);

                // FACE
                builder.addFaceQuad(NORTH, new RetexturedBakedQuad(builder.getQuads(NORTH).get(0), getLevelTexture(level)));

                // SIDES
                BakedQuad[] cachedQuads = ITEM_QUAD_CACHE.get(configHash);
                if (cachedQuads == null || cachedQuads.length < 6) {
                    cachedQuads = new BakedQuad[6];

                    cachedQuads[0] = new RetexturedBakedQuad(builder.getQuads(DOWN).get(0), getConfigTexture(sideConfigRaw[0]));
                    cachedQuads[1] = new RetexturedBakedQuad(builder.getQuads(UP).get(0), getConfigTexture(sideConfigRaw[1]));
                    cachedQuads[2] = new RetexturedBakedQuad(builder.getQuads(NORTH).get(0), getConfigTexture(sideConfigRaw[2]));
                    cachedQuads[3] = new RetexturedBakedQuad(builder.getQuads(SOUTH).get(0), getConfigTexture(sideConfigRaw[3]));
                    cachedQuads[4] = new RetexturedBakedQuad(builder.getQuads(WEST).get(0), getConfigTexture(sideConfigRaw[4]));
                    cachedQuads[5] = new RetexturedBakedQuad(builder.getQuads(EAST).get(0), getConfigTexture(sideConfigRaw[5]));
                    ITEM_QUAD_CACHE.put(configHash, cachedQuads);
                }
                builder.addFaceQuad(DOWN, cachedQuads[0]);
                builder.addFaceQuad(UP, cachedQuads[1]);
                builder.addFaceQuad(NORTH, cachedQuads[2]);
                builder.addFaceQuad(SOUTH, cachedQuads[3]);
                builder.addFaceQuad(WEST, cachedQuads[4]);
                builder.addFaceQuad(EAST, cachedQuads[5]);

                ret = builder.build();
                MODEL_CACHE.put(Arrays.asList(itemHash, level, configHash), ret);
            }
            return ret;
        }
    };

    // region HELPERS
    private TextureAtlasSprite getConfigTexture(byte side) {

        switch (side) {
            case 1:
                return CELL_CONFIG_INPUT;
            case 2:
                return CELL_CONFIG_OUTPUT;
            case 3:
                return CELL_CONFIG_BOTH;
            default:
                return CELL_CONFIG_NONE;
        }
    }

    private TextureAtlasSprite getLevelTexture(int level) {

        // Creative returned as 9
        if (level > 8) {
            return ENERGY_CELL_LEVEL_8_C;
        }
        return ENERGY_CELL_LEVELS[MathHelper.clamp(level, 0, 8)];
    }

    private byte[] getSideConfigRaw(CompoundNBT tag) {

        if (tag == null) {
            return DEFAULT_CELL_SIDES_RAW;
        }
        byte[] ret = tag.getByteArray(TAG_SIDES);
        return ret.length == 0 ? DEFAULT_CELL_SIDES_RAW : ret;
    }

    private int getLevel(ItemStack stack) {

        Item item = stack.getItem();
        if (item instanceof ICoFHItem && ((ICoFHItem) item).isCreative(stack, ENERGY)) {
            return -1;
        }
        if (item instanceof IEnergyContainerItem && ((IEnergyContainerItem) item).getEnergyStored(stack) > 0) {
            return 1 + Math.min(((IEnergyContainerItem) item).getScaledEnergyStored(stack, 8), 7);
        }
        return 0;
    }
    // endregion
}
