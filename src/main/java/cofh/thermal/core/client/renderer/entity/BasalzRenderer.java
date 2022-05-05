package cofh.thermal.core.client.renderer.entity;

import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.client.renderer.entity.model.BasalzModel;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.monster.Basalz;
import cofh.thermal.core.entity.projectile.BasalzProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn (Dist.CLIENT)
public class BasalzRenderer extends MobRenderer<Basalz, BasalzModel<Basalz>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/basalz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/basalz_angry.png");

    protected ElementalProjectileModel<BasalzProjectile> projectileModel;

    public BasalzRenderer(EntityRendererProvider.Context ctx) {

        super(ctx, new BasalzModel<>(ctx.getModelSet().bakeLayer(BasalzModel.BASALZ_LAYER)), 0.5F);
        this.projectileModel = new ElementalProjectileModel<>(ctx.getModelSet().bakeLayer(ElementalProjectileModel.PROJECTILE_LAYER));
    }

    @Override
    public void render(Basalz entity, float entityYaw, float partialTicks, PoseStack poseStackIn, MultiBufferSource bufferIn, int packedLightIn) {

        if (entity.isAlive()) {
            float scale = 1.0F - MathHelper.clamp((entity.angerTime + partialTicks) / Basalz.DEPLOY_TIME, 0.0F, 1.0F);
            scale = 1.0F - scale * scale * scale;
            if (!entity.isAngry()) {
                scale = 1.0F - scale;
            }
            if (scale > 0.0F) {
                poseStackIn.pushPose();
                float time = entity.tickCount + partialTicks;
                poseStackIn.translate(0, entity.getBbHeight() * (0.35F + 0.35F * scale), 0);
                poseStackIn.scale(scale, scale, scale);
                int orbit = entity.getOrbit();
                float inv = 1.0F / orbit;
                Quaternion rot = Vector3f.YP.rotationDegrees(360.0F * inv);
                poseStackIn.mulPose(Vector3f.YP.rotationDegrees(time * Math.max(36 * inv, 12)));
                for (int i = 0; i < orbit; ++i) {
                    poseStackIn.pushPose();
                    float t = time + i;
                    poseStackIn.translate(3, 0.5F * MathHelper.sin(time * 0.15708F - i * inv * 6.2832F), 0);
                    poseStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.sin(t * 0.1F) * 180.0F));
                    poseStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.cos(t * 0.1F) * 180.0F));
                    float invScale = 0.5F / scale;
                    poseStackIn.scale(invScale, invScale, invScale);
                    VertexConsumer builder = bufferIn.getBuffer(projectileModel.renderType(BasalzProjectileRenderer.TEXTURE));
                    this.projectileModel.renderToBuffer(poseStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.8F);
                    poseStackIn.popPose();
                    poseStackIn.mulPose(rot);
                }
                poseStackIn.popPose();
            }
        }
        super.render(entity, entityYaw, partialTicks, poseStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected int getBlockLightLevel(Basalz entityIn, BlockPos pos) {

        return entityIn.isAngry() ? 12 : super.getBlockLightLevel(entityIn, pos);
    }

    @Override
    public ResourceLocation getTextureLocation(Basalz entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}
