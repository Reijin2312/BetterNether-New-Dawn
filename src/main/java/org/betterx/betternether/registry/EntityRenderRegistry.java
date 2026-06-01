package org.betterx.betternether.registry;

import org.betterx.bclib.furniture.renderer.RenderChair;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.model.*;
import org.betterx.betternether.entity.render.*;

import net.minecraft.client.model.geom.ModelLayerLocation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = BetterNether.MOD_ID, value = Dist.CLIENT)
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

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        if (NetherEntities.LEGACY_CHAIR != null) {
            event.registerEntityRenderer(NetherEntities.LEGACY_CHAIR, RenderChair::new);
        }
        event.registerEntityRenderer(NetherEntities.FIREFLY.type(), RenderFirefly::new);
        event.registerEntityRenderer(NetherEntities.HYDROGEN_JELLYFISH.type(), RenderHydrogenJellyfish::new);
        event.registerEntityRenderer(NetherEntities.NAGA.type(), RenderNaga::new);
        event.registerEntityRenderer(NetherEntities.NAGA_PROJECTILE, RenderNagaProjectile::new);
        event.registerEntityRenderer(NetherEntities.FLYING_PIG.type(), RenderFlyingPig::new);
        event.registerEntityRenderer(NetherEntities.JUNGLE_SKELETON.type(), RenderJungleSkeleton::new);
        event.registerEntityRenderer(NetherEntities.SKULL.type(), RenderSkull::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FIREFLY_MODEL, ModelEntityFirefly::getTexturedModelData);
        event.registerLayerDefinition(NAGA_MODEL, ModelNaga::getTexturedModelData);
        event.registerLayerDefinition(JUNGLE_SKELETON_MODEL, ModelJungleSkeleton::createBodyLayer);
        event.registerLayerDefinition(FLYING_PIG_MODEL, ModelEntityFlyingPig::getTexturedModelData);
        event.registerLayerDefinition(HYDROGEN_JELLYFISH_MODEL, ModelEntityHydrogenJellyfish::getTexturedModelData);
        event.registerLayerDefinition(SKULL_MODEL, ModelSkull::getTexturedModelData);
    }
}
