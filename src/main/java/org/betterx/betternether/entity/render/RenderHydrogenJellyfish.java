package org.betterx.betternether.entity.render;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityHydrogenJellyfish;
import org.betterx.betternether.entity.model.ModelEntityHydrogenJellyfish;
import org.betterx.betternether.registry.EntityRenderRegistry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class RenderHydrogenJellyfish extends MobRenderer<EntityHydrogenJellyfish, LivingEntityRenderState, ModelEntityHydrogenJellyfish> {
    private static final Identifier TEXTURE =
            BetterNether.C.mk("textures/entity/jellyfish.png");

    public RenderHydrogenJellyfish(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelEntityHydrogenJellyfish(ctx.bakeLayer(EntityRenderRegistry.HYDROGEN_JELLYFISH_MODEL)), 1);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(EntityHydrogenJellyfish entity, BlockPos pos) {
        return 15;
    }

    @Override
    protected void scale(LivingEntityRenderState state, PoseStack matrixStack) {
        float scale = state.scale;
        matrixStack.scale(scale, scale, scale);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
