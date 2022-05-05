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
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;

public class BlizzModel<T extends LivingEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation BLIZZ_LAYER = new ModelLayerLocation(new ResourceLocation("thermal:blizz"), "main");
    private static final int CUBES = 4;

    private final ModelPart root;
    private final ModelPart[] topCubes;
    private final ModelPart[] botCubes;
    private final ModelPart head;

    public BlizzModel(ModelPart root) {

        this.root = root;
        this.head = root.getChild("head");
        this.topCubes = new ModelPart[CUBES];
        this.botCubes = new ModelPart[CUBES];

        Arrays.setAll(this.topCubes, (num) -> root.getChild("cube_top_" + num));
        Arrays.setAll(this.botCubes, (num) -> root.getChild("cube_bot_" + num));
    }

    public static LayerDefinition createMesh() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.5F, -4.0F, -4.0F, 9.0F, 8.0F, 8.0F)
                        .texOffs(0, 16).addBox(-4.5F, -4.0F, -6.0F, 9.0F, 4.0F, 2.0F),
                PartPose.ZERO);

        CubeListBuilder topCube = CubeListBuilder.create()
                .texOffs(34, 8)
                .addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F);
        CubeListBuilder botcube = CubeListBuilder.create()
                .texOffs(34, 2)
                .addBox(-2.0F, 17.0F, -2.0F, 3.0F, 3.0F, 3.0F);

        for (int i = 0; i < CUBES; ++i) {
            partdefinition.addOrReplaceChild("cube_top_" + i, topCube, PartPose.ZERO);
            partdefinition.addOrReplaceChild("cube_bot_" + i, botcube, PartPose.ZERO);
        }
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public ModelPart root() {

        return this.root;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float x = MathHelper.bevel(ageInTicks * 0.05F);
        float z = MathHelper.bevel(ageInTicks * 0.05F + 1.0F);
        for (int i = 0; i < CUBES; ++i) {
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

}
