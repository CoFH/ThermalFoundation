package cofh.thermal.core.client.renderer.entity.model;

import cofh.lib.util.helpers.MathHelper;
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

import java.util.Arrays;

public class BasalzModel<T extends LivingEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation BASALZ_LAYER = new ModelLayerLocation(new ResourceLocation("thermal:basalz"), "main");
    private static final int PILLARS = 4;

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart core;
    private final ModelPart[] pillars;

    public BasalzModel(ModelPart root) {

        this.root = root;
        this.head = root.getChild("head");
        this.core = root.getChild("core");
        this.pillars = new ModelPart[4];

        Arrays.setAll(this.pillars, (num) -> root.getChild("pillar_" + num));
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F),
                PartPose.ZERO);

        partdefinition.addOrReplaceChild("core",
                CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-4.0F, 10.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        for (int i = 0; i < PILLARS; ++i) {
            int odd = i & 1;
            partdefinition.addOrReplaceChild("pillar_" + i,
                    CubeListBuilder.create()
                            .texOffs(odd * 20, 38).addBox(((i + 1 & 2) >> 1) * 17 - 11, 6.0F + i, odd * 17 - 11, 5.0F, 13.0F, 5.0F, (i & 2) > 0),
                    PartPose.ZERO);
        }
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {

        return this.root;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        core.y = MathHelper.sin(ageInTicks * 0.1F);

        float partialTicks = ageInTicks - entityIn.tickCount;
        for (ModelPart pillar : pillars) {
            pillar.yRot = (ageInTicks * 6 - Mth.lerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot)) * (float) Math.PI / 180.0F;
        }
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

}
