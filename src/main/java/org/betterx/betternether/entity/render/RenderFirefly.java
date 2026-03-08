package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityFirefly;
import org.betterx.betternether.entity.model.ModelEntityFirefly;
import org.betterx.betternether.registry.EntityRenderRegistry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class RenderFirefly extends MobRenderer<EntityFirefly, RenderFirefly.FireflyRenderState, ModelEntityFirefly> {
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

            RenderType layer = RenderPhaseAccessor.getFirefly(TEXTURE);
            // Render twice like 1.21.1 to keep the same strong glow intensity.
            submitNodeCollector.order(1)
                .submitModelPart(
                        model.getGlowPart(),
                        poseStack,
                        layer,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        null,
                        state.color,
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
                        state.color,
                        null
                );
        }
    }

    public static class FireflyRenderState extends LivingEntityRenderState {
        public int color = -1;
    }
}
