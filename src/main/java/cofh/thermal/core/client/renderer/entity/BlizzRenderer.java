package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BlizzModel;
import cofh.thermal.core.entity.monster.Blizz;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class BlizzRenderer extends MobRenderer<Blizz, BlizzModel<Blizz>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blizz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blizz_angry.png");

    public BlizzRenderer(EntityRendererProvider.Context ctx) {

        super(ctx, new BlizzModel<>(ctx.getModelSet().bakeLayer(BlizzModel.BLIZZ_LAYER)), 0.5F);
    }

    @Override
    protected int getBlockLightLevel(Blizz entityIn, BlockPos pos) {

        return entityIn.isAngry() ? 7 : super.getBlockLightLevel(entityIn, pos);
    }

    @Override
    public ResourceLocation getTextureLocation(Blizz entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}
