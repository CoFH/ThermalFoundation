/*
package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.projectile.BlizzProjectileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class BlizzProjectileRenderer extends EntityRenderer<BlizzProjectileEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blizz_projectile.png");
    private final ElementalProjectileModel<BlizzProjectileEntity> model = new ElementalProjectileModel<>();

    public BlizzProjectileRenderer(EntityRendererManager manager) {

        super(manager);
    }

    @Override
    public void render(BlizzProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        matrixStackIn.pushPose();
        float f = MathHelper.rotlerp(entityIn.yRotO, entityIn.yRot, partialTicks);
        float f1 = MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot);
        float f2 = (float) entityIn.tickCount + partialTicks;
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.sin(f2 * 0.1F) * 180.0F));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.cos(f2 * 0.1F) * 180.0F));
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        this.model.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(BlizzProjectileEntity entity) {

        return TEXTURE;
    }

}
*/
