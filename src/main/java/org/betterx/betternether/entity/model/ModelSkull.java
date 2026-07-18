package org.betterx.betternether.entity.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ModelSkull extends EntityModel<ModelSkull.SkullRenderState> {
    private final ModelPart head;

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                               .texOffs(0, 0)
                               .addBox(-4, -4, -4, 8, 8, 8),
                PartPose.offset(0, 20, 0)
        );
        /*head = ModelPart new (this, 0, 0);
        head.setPivot(0, 20, 0);
        head.addCuboid(-4, -4, -4, 8, 8, 8);*/

        /* textureHeight = 16;
        textureWidth = 32; */
        return LayerDefinition.create(modelData, 32, 16);
    }

    public ModelSkull(ModelPart root) {
        super(root);
        this.head = root.getChild(PartNames.HEAD);
    }

    @Override
    public void setupAnim(SkullRenderState state) {
        super.setupAnim(state);
        this.head.yRot = state.yRot * 0.017453292F;
        if (state.rollTooBig) {
            this.head.xRot = -0.7853982F;
        } else if (state.swimAmount > 0.0F) {
            this.head.xRot = this.lerpAngle(this.head.xRot, state.xRot * 0.017453292F, state.swimAmount);
        } else {
            this.head.xRot = state.xRot * 0.017453292F;
        }
    }

    protected float lerpAngle(float from, float to, float position) {
        float angle = (to - from) % 6.2831855F;

        if (angle < -3.1415927F) {
            angle += 6.2831855F;
        }

        if (angle >= 3.1415927F) {
            angle -= 6.2831855F;
        }

        return from + position * angle;
    }

    public static class SkullRenderState extends LivingEntityRenderState {
        public float swimAmount;
        public boolean rollTooBig;
    }
}
