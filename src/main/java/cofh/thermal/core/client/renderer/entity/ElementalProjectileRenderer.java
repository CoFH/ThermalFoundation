package cofh.thermal.core.client.renderer.entity;

import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.projectile.ElementalProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

public abstract class ElementalProjectileRenderer<T extends ElementalProjectile> extends EntityRenderer<T> {

    protected final EntityModel<T> model;

    protected ElementalProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
        this.model = new ElementalProjectileModel<>(ctx.getModelSet().bakeLayer(ElementalProjectileModel.PROJECTILE_LAYER));
    }

    protected RenderType getRenderType(T entityIn) {

        return this.model.renderType(getTextureLocation(entityIn));
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack poseStackIn, MultiBufferSource bufferIn, int packedLightIn) {

        poseStackIn.pushPose();
        float f = Mth.rotlerp(entityIn.yRotO, entityIn.yRot, partialTicks);
        float f1 = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.xRot);
        float f2 = (float) entityIn.tickCount + partialTicks;
        poseStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.sin(f2 * 0.1F) * 180.0F));
        poseStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.cos(f2 * 0.1F) * 180.0F));
        poseStackIn.scale(0.5F, 0.5F, 0.5F);
        this.model.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(getRenderType(entityIn));
        this.model.renderToBuffer(poseStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStackIn.popPose();

        super.render(entityIn, entityYaw, partialTicks, poseStackIn, bufferIn, packedLightIn);
    }

}
