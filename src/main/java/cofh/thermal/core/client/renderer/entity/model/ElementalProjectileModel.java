package cofh.thermal.core.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ElementalProjectileModel<T extends Entity> extends SegmentedModel<T> {

    private final ModelRenderer renderer;

    public ElementalProjectileModel() {

        this.textureWidth = 32;
        this.textureHeight = 16;
        this.renderer = new ModelRenderer(this);
        this.renderer.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        this.renderer.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {

        return ImmutableList.of(this.renderer);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.renderer.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.renderer.rotateAngleX = headPitch * ((float) Math.PI / 180F);
    }

}
