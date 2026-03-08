package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityNaga;
import org.betterx.betternether.entity.model.ModelNaga;
import org.betterx.betternether.registry.EntityRenderRegistry;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderNaga extends MobRenderer<EntityNaga, ModelNaga.NagaRenderState, ModelNaga> {
    private static final Identifier TEXTURE = BetterNether.C.mk(
            "textures/entity/naga.png"
    );

    public RenderNaga(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelNaga(ctx.bakeLayer(EntityRenderRegistry.NAGA_MODEL)), 0.7F);
    }

    @Override
    public Identifier getTextureLocation(ModelNaga.NagaRenderState state) {
        return TEXTURE;
    }

    @Override
    public ModelNaga.NagaRenderState createRenderState() {
        return new ModelNaga.NagaRenderState();
    }

    @Override
    public void extractRenderState(EntityNaga entity, ModelNaga.NagaRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.swimAmount = entity.getSwimAmount(partialTick);
        state.rollTooBig = entity.getFallFlyingTicks() > 4;
        state.visuallySwimming = entity.isVisuallySwimming();
        state.movingOnGround = entity.onGround()
                && (entity.getDeltaMovement().x != 0 || entity.getDeltaMovement().z != 0)
                && !entity.isPassenger();
    }
}
