package org.betterx.betternether.client;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.config.screen.ConfigScreen;
import org.betterx.betternether.registry.NetherParticles;

import org.betterx.bclib.client.render.BoatModelManager;
import org.betterx.bclib.items.boat.BoatTypeOverride;

import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.object.boat.RaftModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = BetterNether.MOD_ID, value = Dist.CLIENT)
public final class BetterNetherClient {
    private BetterNetherClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(BetterNetherClient::registerRenderLayers);

        IConfigScreenFactory factory = (modContainer, parent) -> new ConfigScreen(parent);
        ModList.get()
               .getModContainerById(BetterNether.MOD_ID)
               .ifPresent(container -> container.registerExtensionPoint(IConfigScreenFactory.class, factory));
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        NetherParticles.registerProviders(event);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition boatModel = BoatModel.createBoatModel();
        LayerDefinition chestBoatModel = BoatModel.createChestBoatModel();
        LayerDefinition raftModel = RaftModel.createRaftModel();
        LayerDefinition chestRaftModel = RaftModel.createChestRaftModel();

        BoatTypeOverride.values().forEach(type -> {
            event.registerLayerDefinition(BoatModelManager.boatLayer(type), () -> type.isRaft ? raftModel : boatModel);
            event.registerLayerDefinition(
                    BoatModelManager.chestBoatLayer(type),
                    () -> type.isRaft ? chestRaftModel : chestBoatModel
            );
        });
    }

    private static void registerRenderLayers() {
        // Block render layers are now resolved by the 26.x render pipeline.
    }
}
