package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BlitzModel;
import cofh.thermal.core.entity.monster.Blitz;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BlitzRenderer extends MobRenderer<Blitz, BlitzModel<Blitz>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blitz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blitz_angry.png");

    public BlitzRenderer(EntityRendererProvider.Context ctx) {

        super(ctx, new BlitzModel<>(ctx.getModelSet().bakeLayer(BlitzModel.BLITZ_LAYER)), 0.5F);
    }

    @Override
    protected int getBlockLightLevel(Blitz entityIn, BlockPos pos) {

        return entityIn.isAngry() ? 12 : super.getBlockLightLevel(entityIn, pos);
    }

    @Override
    public ResourceLocation getTextureLocation(Blitz entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}
