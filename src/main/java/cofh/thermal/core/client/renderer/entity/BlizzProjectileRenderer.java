package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.entity.projectile.BlizzProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class BlizzProjectileRenderer extends ElementalProjectileRenderer<BlizzProjectile> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/blizz_projectile.png");

    public BlizzProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(BlizzProjectile entity) {

        return TEXTURE;
    }

}
