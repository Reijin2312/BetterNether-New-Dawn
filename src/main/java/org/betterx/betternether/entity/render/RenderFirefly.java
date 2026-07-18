package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityFirefly;
import org.betterx.betternether.entity.model.ModelEntityFirefly;
import org.betterx.betternether.registry.EntityRenderRegistry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import com.mojang.math.Axis;

import org.joml.Matrix3f;
import org.joml.Quaternionf;

public class RenderFirefly extends MobRenderer<EntityFirefly, RenderFirefly.FireflyRenderState, ModelEntityFirefly> {
    private static final int FULL_BRIGHT = 15728880;
    private static final Identifier TEXTURE = BetterNether.C.mk("textures/entity/firefly.png");

    public RenderFirefly(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelEntityFirefly(ctx.bakeLayer(EntityRenderRegistry.FIREFLY_MODEL)), 0);
        this.addLayer(new FireflyGlowLayer(this));
    }

    @Override
    public Identifier getTextureLocation(FireflyRenderState state) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(EntityFirefly entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public FireflyRenderState createRenderState() {
        return new FireflyRenderState();
    }

    @Override
    public void extractRenderState(EntityFirefly entity, FireflyRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.color = entity.getColor();
    }

    @Override
    public void submit(
            FireflyRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState cameraRenderState
    ) {
        state.cameraOrientation.set(cameraRenderState.orientation);
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
    }

    private static class FireflyGlowLayer extends RenderLayer<FireflyRenderState, ModelEntityFirefly> {

        public FireflyGlowLayer(RenderLayerParent<FireflyRenderState, ModelEntityFirefly> parent) {
            super(parent);
        }

        @Override
        public void submit(
                PoseStack poseStack,
                SubmitNodeCollector submitNodeCollector,
                int light,
                FireflyRenderState state,
                float yRot,
                float xRot
        ) {
            ModelEntityFirefly model = this.getParentModel();
            model.syncTransform();
            int glowPlaneColor = withAlpha(state.color, 0x90);
            int glowShellColor = withAlpha(state.color, 0x44);

            // Keep the legacy firefly look from older versions: a view-aligned glow plane + glow shell.
            RenderType layer = RenderPhaseAccessor.getFirefly(TEXTURE);
            addViewAlignedGlow(poseStack, submitNodeCollector, layer, glowPlaneColor, state.cameraOrientation);
            submitNodeCollector.order(1)
                .submitModelPart(
                        model.getGlowPart(),
                        poseStack,
                        layer,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        null,
                        glowShellColor,
                        null
                );
            submitNodeCollector.order(1)
                .submitModelPart(
                        model.getGlowPart(),
                        poseStack,
                        layer,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        null,
                        glowShellColor,
                        null
                );
        }

        private static int withAlpha(int color, int alpha) {
            return (color & 0x00FFFFFF) | ((alpha & 0xFF) << 24);
        }

        private static void addViewAlignedGlow(
                PoseStack poseStack,
                SubmitNodeCollector submitNodeCollector,
                RenderType layer,
                int color,
                Quaternionf cameraOrientation
        ) {
            poseStack.pushPose();
            poseStack.translate(0.0D, 1.25D, 0.0D);
            // Cancel entity/model rotation first, then apply camera-facing billboard rotation.
            Matrix3f inverseCurrentRotation = new Matrix3f(poseStack.last().normal());
            inverseCurrentRotation.transpose();
            poseStack.mulPose(inverseCurrentRotation.getNormalizedRotation(new Quaternionf()));
            poseStack.mulPose(cameraOrientation);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            submitNodeCollector.submitCustomGeometry(poseStack, layer, (pose, consumer) -> {
                addVertex(consumer, pose, -1.0F, -1.0F, 0.0F, 0.5F, color);
                addVertex(consumer, pose, 1.0F, -1.0F, 1.0F, 0.5F, color);
                addVertex(consumer, pose, 1.0F, 1.0F, 1.0F, 1.0F, color);
                addVertex(consumer, pose, -1.0F, 1.0F, 0.0F, 1.0F, color);
            });

            poseStack.popPose();
        }

        private static void addVertex(
                VertexConsumer vertexConsumer,
                PoseStack.Pose pose,
                float posX,
                float posY,
                float u,
                float v,
                int color
        ) {
            vertexConsumer.addVertex(pose, posX, posY, 0.0F)
                          .setColor(color)
                          .setUv(u, v)
                          .setOverlay(OverlayTexture.NO_OVERLAY)
                          .setLight(FULL_BRIGHT)
                          .setNormal(pose, 0.0F, 1.0F, 0.0F);
        }
    }

    public static class FireflyRenderState extends LivingEntityRenderState {
        public int color = -1;
        public final Quaternionf cameraOrientation = new Quaternionf();
    }
}
