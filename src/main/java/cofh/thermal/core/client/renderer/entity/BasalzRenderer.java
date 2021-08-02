package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.client.renderer.entity.model.BasalzModel;
import cofh.thermal.core.entity.monster.BasalzEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

@OnlyIn(Dist.CLIENT)
public class BasalzRenderer extends MobRenderer<BasalzEntity, BasalzModel<BasalzEntity>> {

    private static final ResourceLocation CALM_TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/basalz.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/basalz_angry.png");

    public BasalzRenderer(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, new BasalzModel<>(), 0.5F);
    }

    @Override
    protected int getBlockLight(BasalzEntity entityIn, BlockPos partialTicks) {

        return entityIn.isAngry() ? 12 : super.getBlockLight(entityIn, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(BasalzEntity entity) {

        return entity.isAngry() ? ANGRY_TEXTURE : CALM_TEXTURE;
    }

}