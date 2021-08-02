package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BlizzModel;
import cofh.thermal.core.entity.monster.BlizzEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn(Dist.CLIENT)
public class BlizzRenderer extends MobRenderer<BlizzEntity, BlizzModel<BlizzEntity>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blizz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blizz_angry.png");

    public BlizzRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, new BlizzModel<>(), 0.5F);
    }

    @Override
    protected int getBlockLight(BlizzEntity entityIn, BlockPos partialTicks) {

        return entityIn.isAngry() ? 7 : super.getBlockLight(entityIn, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(BlizzEntity entity) {

        return entity.isAngry() ? CALM_TEXTURE : CALM_TEXTURE;
    }

}