package cofh.thermal.core.client.renderer.entity.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;

public class BlitzModel<T extends LivingEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation BLITZ_LAYER = new ModelLayerLocation(new ResourceLocation("thermal:blitz"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart[] body;
    private final ModelPart cyclone;

    public BlitzModel(ModelPart root) {

        this.root = root;
        this.head = root.getChild("head");
        this.body = new ModelPart[3];
        this.cyclone = root.getChild("cyclone");

        Arrays.setAll(this.body, (num) -> root.getChild("body_" + num));
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 39).addBox(-6.0F, -7.0F, -4.0F, 12.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("body_0",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8.0F, 2.0F, -8.0F, 16.0F, 6.0F, 16.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("body_1",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-5.0F, 9.0F, -5.0F, 10.0F, 6.0F, 10.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("body_2",
                CubeListBuilder.create()
                        .texOffs(40, 27).addBox(-3.0F, 16.0F, -3.0F, 6.0F, 6.0F, 6.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("cyclone",
                CubeListBuilder.create()
                        .texOffs(15, 39).addBox(-12.5F, 23.0F, -12.5F, 25.0F, 0.0F, 25.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public ModelPart root() {

        return this.root;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float partialTicks = ageInTicks - entityIn.tickCount;
        for (int i = 0; i < this.body.length; ++i) {
            this.body[i].yRot = (ageInTicks * (i + 1) * 10 - Mth.lerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot)) * (float) Math.PI / 180.0F;
        }
        cyclone.yRot = (ageInTicks * 20 - Mth.lerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot)) * (float) Math.PI / 180.0F;

        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

}
