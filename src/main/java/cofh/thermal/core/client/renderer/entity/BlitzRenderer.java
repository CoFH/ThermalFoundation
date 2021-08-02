package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BlitzModel;
import cofh.thermal.core.entity.monster.BlitzEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn(Dist.CLIENT)
public class BlitzRenderer extends MobRenderer<BlitzEntity, BlitzModel<BlitzEntity>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blitz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blitz_angry.png");

    public BlitzRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, new BlitzModel<>(), 0.5F);
    }

    @Override
    protected int getBlockLight(BlitzEntity entityIn, BlockPos partialTicks) {

        return entityIn.isAngry() ? 12 : super.getBlockLight(entityIn, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(BlitzEntity entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}