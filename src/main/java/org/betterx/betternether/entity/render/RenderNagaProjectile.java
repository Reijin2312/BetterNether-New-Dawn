package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityNagaProjectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class RenderNagaProjectile extends EntityRenderer<EntityNagaProjectile, RenderNagaProjectile.NagaProjectileRenderState> {
    private static final Identifier TEXTURE = BetterNether.C.mk("textures/entity/naga_projectile.png");
    private static final RenderType LAYER = RenderTypes.entityCutout(TEXTURE);

    public RenderNagaProjectile(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected int getBlockLightLevel(EntityNagaProjectile entity, BlockPos pos) {
        return 15;
    }

    @Override
    public void submit(
            NagaProjectileRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState cameraRenderState
    ) {
        float start = state.frame * 0.25F;
        float end = start + 0.25F;

        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.mulPose(cameraRenderState.orientation);
        submitNodeCollector.submitCustomGeometry(poseStack, LAYER, (pose, consumer) -> {
            vertex(consumer, pose, state.lightCoords, 0.0F, 0, 0, end);
            vertex(consumer, pose, state.lightCoords, 1.0F, 0, 1, end);
            vertex(consumer, pose, state.lightCoords, 1.0F, 1, 1, start);
            vertex(consumer, pose, state.lightCoords, 0.0F, 1, 0, start);
        });
        poseStack.popPose();

        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    public NagaProjectileRenderState createRenderState() {
        return new NagaProjectileRenderState();
    }

    @Override
    public void extractRenderState(EntityNagaProjectile entity, NagaProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.frame = ((int) (state.ageInTicks / 3.0F)) & 3;
    }

    private static void vertex(
            VertexConsumer vertexConsumer,
            PoseStack.Pose pose,
            int light,
            float posX,
            int posY,
            float u,
            float v
    ) {
        vertexConsumer.addVertex(pose, posX - 0.5F, posY - 0.25F, 0.0F)
                      .setColor(-1)
                      .setUv(u, v)
                      .setOverlay(OverlayTexture.NO_OVERLAY)
                      .setLight(light)
                      .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    public static class NagaProjectileRenderState extends EntityRenderState {
        public int frame;
    }
}
