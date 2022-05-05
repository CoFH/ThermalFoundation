package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.entity.projectile.BlitzProjectile;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class BlitzProjectileRenderer extends ElementalProjectileRenderer<BlitzProjectile> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blitz_projectile.png");

    public BlitzProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    protected RenderType getRenderType(BlitzProjectile entityIn) {

        return RenderType.entityTranslucent(TEXTURE);
    }

    @Override
    public ResourceLocation getTextureLocation(BlitzProjectile entity) {

        return TEXTURE;
    }

}

