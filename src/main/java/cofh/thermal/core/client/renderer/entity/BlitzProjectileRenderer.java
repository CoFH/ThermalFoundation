package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.projectile.BlitzProjectileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class BlitzProjectileRenderer extends EntityRenderer<BlitzProjectileEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blitz_projectile.png");
    private static final RenderType RENDER_TYPE = RenderType.getEntityTranslucent(TEXTURE);
    private final ElementalProjectileModel<BlitzProjectileEntity> model = new ElementalProjectileModel<>();

    public BlitzProjectileRenderer(EntityRendererManager manager) {

        super(manager);
    }

    @Override
    public void render(BlitzProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        matrixStackIn.push();
        float f = MathHelper.rotLerp(entityIn.prevRotationYaw, entityIn.rotationYaw, partialTicks);
        float f1 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);
        float f2 = (float) entityIn.ticksExisted + partialTicks;
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.sin(f2 * 0.1F) * 180.0F));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(MathHelper.cos(f2 * 0.1F) * 180.0F));
        matrixStackIn.scale(0.5F, 0.5F, 0.5F);
        this.model.setRotationAngles(entityIn, 0.0F, 0.0F, 0.0F, f, f1);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RENDER_TYPE);
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.8F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BlitzProjectileEntity entity) {

        return TEXTURE;
    }

}
