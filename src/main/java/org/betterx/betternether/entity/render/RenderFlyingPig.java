package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityFlyingPig;
import org.betterx.betternether.entity.model.ModelEntityFlyingPig;
import org.betterx.betternether.registry.EntityRenderRegistry;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class RenderFlyingPig extends MobRenderer<EntityFlyingPig, RenderFlyingPig.FlyingPigRenderState, ModelEntityFlyingPig> {
    private static final Identifier TEXTURE = BetterNether.C.mk(
            "textures/entity/flying_pig.png"
    );
    private static final Identifier TEXTURE_WARTED = BetterNether.C.mk(
            "textures/entity/flying_pig_warted.png"
    );

    public RenderFlyingPig(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelEntityFlyingPig(ctx.bakeLayer(EntityRenderRegistry.FLYING_PIG_MODEL)), 0.6F);
    }

    @Override
    public Identifier getTextureLocation(FlyingPigRenderState state) {
        return state.warted ? TEXTURE_WARTED : TEXTURE;
    }

    @Override
    public FlyingPigRenderState createRenderState() {
        return new FlyingPigRenderState();
    }

    @Override
    public void extractRenderState(EntityFlyingPig entity, FlyingPigRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.warted = entity.isWarted();
    }

    public static class FlyingPigRenderState extends LivingEntityRenderState {
        public boolean warted;
    }
}
