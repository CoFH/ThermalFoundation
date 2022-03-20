/*
package cofh.thermal.core.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

@OnlyIn (Dist.CLIENT)
public class BasalzModel<T extends LivingEntity> extends SegmentedModel<T> {

    private final ModelRenderer head;
    private final ModelRenderer core;
    private final ModelRenderer[] pillars;
    private final ImmutableList<ModelRenderer> partsList;

    public BasalzModel() {

        texWidth = 64;
        texHeight = 64;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, 0.0F, false);

        pillars = new ModelRenderer[4];
        for (int i = 0; i < pillars.length; ++i) {
            pillars[i] = new ModelRenderer(this);
            pillars[i].setPos(0.0F, 0.0F, 0.0F);
            int odd = i & 1;
            pillars[i].texOffs(odd * 20, 38).addBox(((i + 1 & 2) >> 1) * 17 - 11, 6.0F + i, odd * 17 - 11, 5.0F, 13.0F, 5.0F, 0.0F, (i & 2) > 0);
        }

        core = new ModelRenderer(this);
        core.setPos(0.0F, 0.0F, 0.0F);
        core.texOffs(0, 22).addBox(-4.0F, 10.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        Builder<ModelRenderer> builder = ImmutableList.builder();
        builder.add(head);
        builder.addAll(Arrays.asList(pillars));
        builder.add(core);
        this.partsList = builder.build();
    }

    public Iterable<ModelRenderer> parts() {

        return this.partsList;
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        core.y = MathHelper.sin(ageInTicks * 0.1F);

        float partialTicks = ageInTicks - entityIn.tickCount;
        for (ModelRenderer pillar : pillars) {
            pillar.yRot = (ageInTicks * 6 - MathHelper.lerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot)) * (float) Math.PI / 180.0F;
        }

        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

}
*/
