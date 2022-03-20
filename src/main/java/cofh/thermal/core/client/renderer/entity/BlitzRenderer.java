/*
package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BlitzModel;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.monster.BlitzEntity;
import cofh.thermal.core.entity.projectile.BlitzProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn (Dist.CLIENT)
public class BlitzRenderer extends MobRenderer<BlitzEntity, BlitzModel<BlitzEntity>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blitz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blitz_angry.png");

    private final ElementalProjectileModel<BlitzProjectileEntity> projectileModel = new ElementalProjectileModel<>();

    public BlitzRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, new BlitzModel<>(), 0.5F);
    }

    //@Override
    //public void render(BlitzEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
    //
    //    if (entity.isAngry() && entity.isAlive()) {
    //        Random random = new Random(69420);
    //        matrixStackIn.pushPose();
    //        matrixStackIn.translate(0, entity.getBbHeight() + 1.5F, 0);
    //        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(45.0F));
    //        float time = entity.tickCount + partialTicks;
    //        Quaternion rot90 = Vector3f.YP.rotationDegrees(90.0F);
    //        Quaternion rot = Vector3f.YP.rotationDegrees(6 * time);
    //        Quaternion yRot = Vector3f.YP.rotationDegrees(MathHelper.sin(time * 0.1F) * 180.0F);
    //        Quaternion xRot = Vector3f.XP.rotationDegrees(MathHelper.cos(time * 0.1F) * 180.0F);
    //        for (int i = 0; i < 4; ++i) {
    //            matrixStackIn.pushPose();
    //            matrixStackIn.translate(3, 0, 0);
    //            matrixStackIn.mulPose(rot);
    //
    //            matrixStackIn.pushPose();
    //            matrixStackIn.translate(4.0, 0, 0);
    //            RenderHelper.renderArcs(matrixStackIn, bufferIn.getBuffer(RENDER_TYPE), packedLightIn, new Vector3f(0, -5, 0),
    //                    2, 0.2F, RenderHelper.getSeedWithTime(random.nextInt(), time), -0.25F);
    //            matrixStackIn.mulPose(yRot);
    //            matrixStackIn.mulPose(xRot);
    //            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
    //            IVertexBuilder builder = bufferIn.getBuffer(projectileModel.renderType(BlitzProjectileRenderer.TEXTURE));
    //            this.projectileModel.renderToBuffer(matrixStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.8F);
    //            matrixStackIn.popPose();
    //
    //            matrixStackIn.popPose();
    //            matrixStackIn.mulPose(rot90);
    //        }
    //        matrixStackIn.popPose();
    //    }
    //    super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    //}

    @Override
    protected int getBlockLightLevel(BlitzEntity entityIn, BlockPos partialTicks) {

        return entityIn.isAngry() ? 12 : super.getBlockLightLevel(entityIn, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(BlitzEntity entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}
*/
