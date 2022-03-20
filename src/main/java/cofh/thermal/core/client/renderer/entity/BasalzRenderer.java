/*
package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BasalzModel;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.monster.BasalzEntity;
import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn (Dist.CLIENT)
public class BasalzRenderer extends MobRenderer<BasalzEntity, BasalzModel<BasalzEntity>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/basalz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/basalz_angry.png");

    private final ElementalProjectileModel<BasalzProjectileEntity> projectileModel = new ElementalProjectileModel<>();

    public BasalzRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, new BasalzModel<>(), 0.5F);
    }

    @Override
    public void render(BasalzEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        if (entity.isAlive()) {
            float scale = 1.0F - MathHelper.clamp((entity.angerTime + partialTicks) / BasalzEntity.DEPLOY_TIME, 0.0F, 1.0F);
            scale = 1.0F - scale * scale * scale;
            if (!entity.isAngry()) {
                scale = 1.0F - scale;
            }
            if (scale > 0.0F) {
                matrixStackIn.pushPose();
                float time = entity.tickCount + partialTicks;
                matrixStackIn.translate(0, entity.getBbHeight() * (0.35F + 0.35F * scale), 0);
                matrixStackIn.scale(scale, scale, scale);
                int orbit = entity.getOrbit();
                float inv = 1.0F / orbit;
                Quaternion rot = Vector3f.YP.rotationDegrees(360.0F * inv);
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(time * Math.max(36 * inv, 12)));
                for (int i = 0; i < orbit; ++i) {
                    matrixStackIn.pushPose();
                    float t = time + i;
                    matrixStackIn.translate(3, 0.5F * MathHelper.sin(time * 0.15708F - i * inv * 6.2832F), 0);
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.sin(t * 0.1F) * 180.0F));
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.cos(t * 0.1F) * 180.0F));
                    float invScale = 0.5F / scale;
                    matrixStackIn.scale(invScale, invScale, invScale);
                    IVertexBuilder builder = bufferIn.getBuffer(projectileModel.renderType(BasalzProjectileRenderer.TEXTURE));
                    this.projectileModel.renderToBuffer(matrixStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.8F);
                    matrixStackIn.popPose();

                    matrixStackIn.mulPose(rot);
                }
                matrixStackIn.popPose();
            }
        }
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected int getBlockLightLevel(BasalzEntity entityIn, BlockPos partialTicks) {

        return entityIn.isAngry() ? 12 : super.getBlockLightLevel(entityIn, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(BasalzEntity entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}
*/
