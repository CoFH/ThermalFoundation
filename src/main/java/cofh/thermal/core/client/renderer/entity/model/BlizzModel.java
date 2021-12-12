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

@OnlyIn (Dist.CLIENT)
public class BlizzModel<T extends Entity> extends SegmentedModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer[] cubes;
    private final ImmutableList<ModelRenderer> partsList;

    public BlizzModel() {

        texWidth = 64;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.5F, -4.0F, -4.0F, 9.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(34, 10).addBox(-4.5F, -4.0F, -6.0F, 9.0F, 4.0F, 2.0F, 0.0F, false);
        this.cubes = new ModelRenderer[12];

        for (int i = 0; i < 4; ++i) {
            this.cubes[i] = new ModelRenderer(this, 16 * (i % 4), 24);
            this.cubes[i].addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        }
        for (int i = 4; i < 8; ++i) {
            this.cubes[i] = new ModelRenderer(this, 16 * (i % 4), 16);
            this.cubes[i].addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F);
        }
        for (int i = 8; i < this.cubes.length; ++i) {
            this.cubes[i] = new ModelRenderer(this, 16 * (i % 4), 24);
            this.cubes[i].addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        }
        Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.add(this.head);
        builder.addAll(Arrays.asList(this.cubes));
        this.partsList = builder.build();
    }

    public Iterable<ModelRenderer> parts() {

        return this.partsList;
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float f = ageInTicks * (float) Math.PI * -0.1F;
        for (int i = 0; i < 4; ++i) {
            this.cubes[i].y = -2.0F + MathHelper.cos(((float) (i * 2) + ageInTicks) * 0.25F);
            this.cubes[i].x = MathHelper.cos(f) * 13.0F;
            this.cubes[i].z = MathHelper.sin(f) * 13.0F;
            ++f;
        }

        f = ((float) Math.PI / 4F) + ageInTicks * (float) Math.PI * 0.03F;
        for (int j = 4; j < 8; ++j) {
            this.cubes[j].y = 5.0F + MathHelper.cos(((float) (j * 2) + ageInTicks) * 0.25F);
            this.cubes[j].x = MathHelper.cos(f) * 9.0F;
            this.cubes[j].z = MathHelper.sin(f) * 9.0F;
            ++f;
        }

        f = 0.47123894F + ageInTicks * (float) Math.PI * -0.05F;
        for (int k = 8; k < 12; ++k) {
            this.cubes[k].y = 12.0F + MathHelper.cos(((float) k * 1.5F + ageInTicks) * 0.5F);
            this.cubes[k].x = MathHelper.cos(f) * 5.0F;
            this.cubes[k].z = MathHelper.sin(f) * 5.0F;
            ++f;
        }
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

}