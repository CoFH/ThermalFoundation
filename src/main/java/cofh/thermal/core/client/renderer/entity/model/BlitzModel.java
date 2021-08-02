package cofh.thermal.core.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public class BlitzModel<T extends Entity> extends SegmentedModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer[] motes;
    private final ImmutableList<ModelRenderer> partsList;

    public BlitzModel() {

        textureWidth = 64;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-6.0F, -4.0F, -4.0F, 12.0F, 8.0F, 8.0F, 0.0F, false);
        this.motes = new ModelRenderer[8];

        for (int i = 0; i < this.motes.length; ++i) {
            this.motes[i] = new ModelRenderer(this, 16 * (i % 4), 16);
            this.motes[i].addBox(0.0F, 0.0F, 0.0F, 4.0F, 5.0F, 4.0F);
        }
        Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.add(this.head);
        builder.addAll(Arrays.asList(this.motes));
        this.partsList = builder.build();
    }

    public Iterable<ModelRenderer> getParts() {

        return this.partsList;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        //        float f = ageInTicks * (float) Math.PI * -0.1F;
        //        for (int i = 0; i < 4; ++i) {
        //            this.motes[i].rotationPointY = -2.0F + MathHelper.cos(((float) (i * 2) + ageInTicks) * 0.25F);
        //            this.motes[i].rotationPointX = MathHelper.cos(f) * 13.0F;
        //            this.motes[i].rotationPointZ = MathHelper.sin(f) * 13.0F;
        //            ++f;
        //        }

        float f = ((float) Math.PI / 4F) + ageInTicks * (float) Math.PI * 0.03F;
        for (int j = 0; j < 4; ++j) {
            this.motes[j].rotationPointY = 4.0F + MathHelper.cos(((float) (j * 3) + ageInTicks) * 0.25F);
            this.motes[j].rotationPointX = MathHelper.cos(f) * 9.0F;
            this.motes[j].rotationPointZ = MathHelper.sin(f) * 9.0F;
            ++f;
        }

        f = 0.47123894F + ageInTicks * (float) Math.PI * -0.05F;
        for (int k = 4; k < 8; ++k) {
            this.motes[k].rotationPointY = 12.0F + MathHelper.cos(((float) k * 1.5F + ageInTicks) * 0.5F);
            this.motes[k].rotationPointX = MathHelper.cos(f) * 5.0F;
            this.motes[k].rotationPointZ = MathHelper.sin(f) * 5.0F;
            ++f;
        }
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
    }

}