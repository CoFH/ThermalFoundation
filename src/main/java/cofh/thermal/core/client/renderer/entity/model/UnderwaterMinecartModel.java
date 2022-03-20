/*
package cofh.thermal.core.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class UnderwaterMinecartModel<T extends Entity> extends EntityModel<T> {

    private final ModelRenderer dome;
    private final ModelRenderer minecart;

    public UnderwaterMinecartModel() {

        texWidth = 128;
        texHeight = 128;

        dome = new ModelRenderer(this);
        dome.addBox(-8, -20, -10, 16, 15, 20);

        minecart = new ModelRenderer(this);
        minecart.texOffs(0, 42).addBox(-8, -5, -10, 16, 10, 20);
        minecart.texOffs(56, 56).addBox(-6, -5, -8, 12, 8, 16);

        dome.yRot = ((float) Math.PI / 2F);
        minecart.yRot = ((float) Math.PI / 2F);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        dome.render(matrixStack, buffer, packedLight, packedOverlay);
        minecart.render(matrixStack, buffer, packedLight, packedOverlay);
    }

}
*/
