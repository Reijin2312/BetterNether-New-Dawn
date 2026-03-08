package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntitySkull;
import org.betterx.betternether.entity.model.ModelSkull;
import org.betterx.betternether.registry.EntityRenderRegistry;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class RenderSkull extends MobRenderer<EntitySkull, ModelSkull.SkullRenderState, ModelSkull> {
    private static final Identifier TEXTURE = BetterNether.C.mk(
            "textures/entity/skull.png"
    );

    public RenderSkull(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelSkull(ctx.bakeLayer(EntityRenderRegistry.SKULL_MODEL)), 0.7F);
        this.addLayer(new GlowFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(ModelSkull.SkullRenderState state) {
        return TEXTURE;
    }

    static class GlowFeatureRenderer extends EyesLayer<ModelSkull.SkullRenderState, ModelSkull> {
        private static final RenderType SKIN = RenderTypes.entityTranslucent(BetterNether.C.mk(
                "textures/entity/skull_glow.png"
        ));

        public GlowFeatureRenderer(RenderLayerParent<ModelSkull.SkullRenderState, ModelSkull> featureRendererContext) {
            super(featureRendererContext);
        }

        public RenderType renderType() {
            return SKIN;
        }
    }

    @Override
    protected int getBlockLightLevel(EntitySkull entity, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(entity, pos), 7, 15);
    }

    @Override
    public ModelSkull.SkullRenderState createRenderState() {
        return new ModelSkull.SkullRenderState();
    }

    @Override
    public void extractRenderState(EntitySkull entity, ModelSkull.SkullRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.swimAmount = entity.getSwimAmount(partialTick);
        state.rollTooBig = entity.getFallFlyingTicks() > 4;
    }
}
