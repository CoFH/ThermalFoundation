package cofh.thermal.core.client.renderer.entity;

import cofh.thermal.core.entity.projectile.BasalzProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class BasalzProjectileRenderer extends ElementalProjectileRenderer<BasalzProjectile> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ID_THERMAL + ":textures/entity/basalz_projectile.png");

    public BasalzProjectileRenderer(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(BasalzProjectile entity) {

        return TEXTURE;
    }

}
