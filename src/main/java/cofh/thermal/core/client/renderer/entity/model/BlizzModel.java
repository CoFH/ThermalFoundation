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

import static cofh.lib.util.helpers.MathHelper.bevel;

@OnlyIn (Dist.CLIENT)
public class BlizzModel<T extends LivingEntity> extends SegmentedModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer[] topCubes;
    private final ModelRenderer[] botCubes;
    private final ImmutableList<ModelRenderer> partsList;

    public BlizzModel() {

        texWidth = 64;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.5F, -4.0F, -4.0F, 9.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(0, 16).addBox(-4.5F, -4.0F, -6.0F, 9.0F, 4.0F, 2.0F, 0.0F, false);

        this.topCubes = new ModelRenderer[4];
        for (int i = 0; i < topCubes.length; ++i) {
            topCubes[i] = new ModelRenderer(this);
            topCubes[i].setPos(0.0F, 0.0F, 0.0F);
            topCubes[i].texOffs(34, 8).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, true);
        }

        this.botCubes = new ModelRenderer[topCubes.length];
        for (int i = 0; i < botCubes.length; ++i) {
            botCubes[i] = new ModelRenderer(this);
            botCubes[i].setPos(0.0F, 0.0F, 0.0F);
            botCubes[i].texOffs(34, 2).addBox(-2.0F, 17.0F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F, true);
        }

        Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.add(this.head);
        builder.addAll(Arrays.asList(this.topCubes));
        builder.addAll(Arrays.asList(this.botCubes));
        this.partsList = builder.build();
    }

    public Iterable<ModelRenderer> parts() {

        return this.partsList;
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {


        float x = bevel(ageInTicks * 0.05F);
        float z = bevel(ageInTicks * 0.05F + 1.0F);
        for (int i = 0; i < topCubes.length; ++i) {
            topCubes[i].x = x * -4.0F;
            topCubes[i].z = z * 4.0F;
            topCubes[i].y = MathHelper.sin(ageInTicks * 0.2F + i * 4);
            botCubes[i].x = x * 3.5F;
            botCubes[i].z = z * 3.5F;
            botCubes[i].y = MathHelper.sin(ageInTicks * 0.2F + i * 4 + 2);
            float temp = -x;
            x = z;
            z = temp;
        }

        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

<<<<<<< HEAD
}
=======
    public static float bevel(float f) {

        int floor = MathHelper.floor(f);
        if (f - floor < 0.66667F && (floor & 1) == 0) {
            return -MathHelper.cos((float) Math.PI * 1.5F * f);
        }
        return ((floor >> 1) & 1) == 0 ? 1 : -1;
    }

}
*/
>>>>>>> 3bc6106 (Initial 1.18.2 compile pass.)
