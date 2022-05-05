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
import net.minecraft.world.entity.Entity;

public class ElementalProjectileModel<T extends Entity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation PROJECTILE_LAYER = new ModelLayerLocation(new ResourceLocation("thermal:elemental_projectile"), "main");

    protected final ModelPart root;
    protected final ModelPart cube;

    public ElementalProjectileModel(ModelPart root) {

        this.root = root;
        this.cube = root.getChild("cube");
    }

    public static LayerDefinition createMesh() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("cube",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public ModelPart root() {

        return this.root;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.cube.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.cube.xRot = headPitch * ((float) Math.PI / 180F);
    }

}
