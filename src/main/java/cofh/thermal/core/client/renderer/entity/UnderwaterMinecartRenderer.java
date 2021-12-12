package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.UnderwaterMinecartModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn (Dist.CLIENT)
public class UnderwaterMinecartRenderer<T extends AbstractMinecartEntity> extends EntityRenderer<T> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/underwater_minecart.png");
    protected final EntityModel<T> modelMinecart = new UnderwaterMinecartModel<>();

    public UnderwaterMinecartRenderer(EntityRendererManager renderManager) {

        super(renderManager);
        this.shadowRadius = 0.7F;
    }

    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.pushPose();
        long i = (long) entityIn.getId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f1 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f2 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        matrixStackIn.translate(f, f1, f2);
        double d0 = MathHelper.lerp(partialTicks, entityIn.xOld, entityIn.getX());
        double d1 = MathHelper.lerp(partialTicks, entityIn.yOld, entityIn.getY());
        double d2 = MathHelper.lerp(partialTicks, entityIn.zOld, entityIn.getZ());
        Vector3d vec3d = entityIn.getPos(d0, d1, d2);
        float f3 = MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot);
        if (vec3d != null) {
            Vector3d vec3d1 = entityIn.getPosOffs(d0, d1, d2, 0.3F);
            Vector3d vec3d2 = entityIn.getPosOffs(d0, d1, d2, -0.3F);
            if (vec3d1 == null) {
                vec3d1 = vec3d;
            }
            if (vec3d2 == null) {
                vec3d2 = vec3d;
            }
            matrixStackIn.translate(vec3d.x - d0, (vec3d1.y + vec3d2.y) / 2.0D - d1, vec3d.z - d2);
            Vector3d vec3d3 = vec3d2.add(-vec3d1.x, -vec3d1.y, -vec3d1.z);
            if (vec3d3.length() != 0.0D) {
                vec3d3 = vec3d3.normalize();
                entityYaw = (float) (Math.atan2(vec3d3.z, vec3d3.x) * 180.0D / Math.PI);
                f3 = (float) (Math.atan(vec3d3.y) * 73.0D);
            }
        }
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-f3));
        float f5 = (float) entityIn.getHurtTime() - partialTicks;
        float f6 = entityIn.getDamage() - partialTicks;
        if (f6 < 0.0F) {
            f6 = 0.0F;
        }
        if (f5 > 0.0F) {
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f5) * f5 * f6 / 10.0F * (float) entityIn.getHurtDir()));
        }
        int j = entityIn.getDisplayOffset();
        BlockState blockstate = entityIn.getDisplayBlockState();
        if (blockstate.getRenderShape() != BlockRenderType.INVISIBLE) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.75F, 0.75F, 0.75F);
            matrixStackIn.translate(-0.5D, (float) (j - 8) / 16.0F, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            this.renderBlockState(entityIn, partialTicks, blockstate, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.popPose();
        }
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.modelMinecart.setupAnim(entityIn, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.modelMinecart.renderType(this.getTextureLocation(entityIn)));
        this.modelMinecart.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {

        return TEXTURE;
    }

    protected void renderBlockState(T entityIn, float partialTicks, BlockState stateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(stateIn, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

}
