package cofh.thermal.core.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ElementalProjectileModel<T extends Entity> extends SegmentedModel<T> {

    private final ModelRenderer renderer;

    public ElementalProjectileModel() {

        this.texWidth = 32;
        this.texHeight = 16;
        this.renderer = new ModelRenderer(this);
        this.renderer.texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        this.renderer.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public Iterable<ModelRenderer> parts() {

        return ImmutableList.of(this.renderer);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.renderer.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.renderer.xRot = headPitch * ((float) Math.PI / 180F);
    }

}
