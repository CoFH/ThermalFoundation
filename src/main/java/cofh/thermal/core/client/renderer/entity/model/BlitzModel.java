/*
package cofh.thermal.core.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

@OnlyIn (Dist.CLIENT)
public class BlitzModel<T extends LivingEntity> extends SegmentedModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer[] body;
    private final ModelRenderer cyclone;
    private final ImmutableList<ModelRenderer> partsList;

    public BlitzModel() {

        texWidth = 128;
        texHeight = 64;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 39).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);

        this.body = new ModelRenderer[3];

        body[0] = new ModelRenderer(this);
        body[0].setPos(0.0F, 0.0F, 0.0F);
        body[0].texOffs(0, 0).addBox(-8.0F, 2.0F, -8.0F, 16.0F, 6.0F, 16.0F, 0.0F, false);

        body[1] = new ModelRenderer(this);
        body[1].setPos(0.0F, 0.0F, 0.0F);
        body[1].texOffs(0, 23).addBox(-5.0F, 9.0F, -5.0F, 10.0F, 6.0F, 10.0F, 0.0F, false);

        body[2] = new ModelRenderer(this);
        body[2].setPos(0.0F, 0.0F, 0.0F);
        body[2].texOffs(40, 27).addBox(-3.0F, 16.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        cyclone = new ModelRenderer(this);
        cyclone.setPos(0.0F, 0.0F, 0.0F);
        cyclone.texOffs(15, 39).addBox(-12.5F, 23.0F, -12.5F, 25.0F, 0.0F, 25.0F, 0.0F, false);

        Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.add(this.head);
        builder.addAll(Arrays.asList(this.body));
        builder.add(this.cyclone);
        this.partsList = builder.build();
    }

    public Iterable<ModelRenderer> parts() {

        return this.partsList;
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float partialTicks = ageInTicks - entityIn.tickCount;
        for (int i = 0; i < this.body.length; ++i) {
            this.body[i].yRot = (ageInTicks * (i + 1) * 10 - MathHelper.lerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot)) * (float) Math.PI / 180.0F;
        }
        cyclone.yRot = (ageInTicks * 20 - MathHelper.lerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot)) * (float) Math.PI / 180.0F;

        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

}
*/
