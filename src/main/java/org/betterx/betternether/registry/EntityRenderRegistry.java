package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.model.*;
import org.betterx.betternether.entity.render.*;

import net.minecraft.client.model.geom.ModelLayerLocation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class EntityRenderRegistry {
    private static final String DEFAULT_LAYER = "main";
    public static final ModelLayerLocation FIREFLY_MODEL = registerMain("firefly");
    public static final ModelLayerLocation NAGA_MODEL = registerMain("naga");
    public static final ModelLayerLocation JUNGLE_SKELETON_MODEL = registerMain("jungle_skeleton");
    public static final ModelLayerLocation FLYING_PIG_MODEL = registerMain("flying_pig");
    public static final ModelLayerLocation HYDROGEN_JELLYFISH_MODEL = registerMain("hydrogen_jelly");
    public static final ModelLayerLocation SKULL_MODEL = registerMain("skull");


    public static ModelLayerLocation registerMain(String id) {
        //System.out.println("Register Entity: " + id);
        return new ModelLayerLocation(BetterNether.C.mk(id), DEFAULT_LAYER);
        //return EntityModelLayersMixin.callRegisterMain(key);
    }

    public static void register() {
        EntityRendererRegistry.register(NetherEntities.FIREFLY.type(), RenderFirefly::new);
        EntityRendererRegistry.register(NetherEntities.HYDROGEN_JELLYFISH.type(), RenderHydrogenJellyfish::new);
        EntityRendererRegistry.register(NetherEntities.NAGA.type(), RenderNaga::new);
        EntityRendererRegistry.register(NetherEntities.NAGA_PROJECTILE, RenderNagaProjectile::new);
        EntityRendererRegistry.register(NetherEntities.FLYING_PIG.type(), RenderFlyingPig::new);
        EntityRendererRegistry.register(NetherEntities.JUNGLE_SKELETON.type(), RenderJungleSkeleton::new);
        EntityRendererRegistry.register(NetherEntities.SKULL.type(), RenderSkull::new);


        EntityModelLayerRegistry.registerModelLayer(FIREFLY_MODEL, ModelEntityFirefly::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(NAGA_MODEL, ModelNaga::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(JUNGLE_SKELETON_MODEL, ModelJungleSkeleton::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(FLYING_PIG_MODEL, ModelEntityFlyingPig::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(
                HYDROGEN_JELLYFISH_MODEL,
                ModelEntityHydrogenJellyfish::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(SKULL_MODEL, ModelSkull::getTexturedModelData);
    }

}
