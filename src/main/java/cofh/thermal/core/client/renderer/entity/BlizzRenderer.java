/*
package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BlizzModel;
import cofh.thermal.core.client.renderer.entity.model.ElementalProjectileModel;
import cofh.thermal.core.entity.monster.BlizzEntity;
import cofh.thermal.core.entity.projectile.BlizzProjectileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn (Dist.CLIENT)
public class BlizzRenderer extends MobRenderer<BlizzEntity, BlizzModel<BlizzEntity>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blizz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL, "textures/entity/blizz_angry.png");
    //protected static final Vec3[] offsets = getGaussOffsets(20, 1.5F, 0.5F);
    //protected static final int ticksPerProj = 5;

    private final ElementalProjectileModel<BlizzProjectileEntity> projectileModel = new ElementalProjectileModel<>();

    public BlizzRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, new BlizzModel<>(), 0.5F);
    }

    @Override
    public void render(BlizzEntity entity, float entityYaw, float partialTicks, MatrixStack poseStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        //if (true || entity.isAngry()) {
        //    poseStackIn.pushPose();
        //    float time = entity.tickCount + partialTicks;
        //    int mod = offsets.length * ticksPerProj;
        //    //poseStackIn.translate(0, entity.getBbHeight() * 0.7, 0);
        //    for (Vec3 offset : offsets) {
        //        poseStackIn.pushPose();
        //        poseStackIn.translate(offset.x, offset.y - (time % mod) * 0.4F, offset.z);
        //        poseStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.sin(time * 0.1F) * 180.0F));
        //        poseStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.cos(time * 0.1F) * 180.0F));
        //        poseStackIn.scale(0.5F, 0.5F, 0.5F);
        //        IVertexBuilder builder = bufferIn.getBuffer(projectileModel.renderType(BlizzProjectileRenderer.TEXTURE));
        //        this.projectileModel.renderToBuffer(poseStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.8F);
        //        poseStackIn.popPose();
        //        time += ticksPerProj;
        //    }
        //    poseStackIn.popPose();
        //}
        super.render(entity, entityYaw, partialTicks, poseStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected int getBlockLightLevel(BlizzEntity entityIn, BlockPos partialTicks) {

        return entityIn.isAngry() ? 7 : super.getBlockLightLevel(entityIn, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(BlizzEntity entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

    public static Vec3[] getGaussOffsets(int num, float horzScale, float yScale) {

        Random rand = new Random(69420);
        Vec3[] offsets = new Vec3[num];
        for (int i = 0; i < num; ++i) {
            offsets[i] = (new Vec3(rand.nextGaussian() * horzScale, rand.nextFloat() * yScale, 0)).yRot((float) Math.PI * rand.nextFloat());
        }
        return offsets;
    }

}
*/
