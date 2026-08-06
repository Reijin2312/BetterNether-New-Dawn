package org.betterx.betternether.client;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNRenderLayer;
import org.betterx.betternether.config.screen.ConfigScreen;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherParticles;

import org.betterx.bclib.items.boat.BoatTypeOverride;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.registries.BuiltInRegistries;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = BetterNether.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
        LayerDefinition boatModel = BoatModel.createBodyModel();
        LayerDefinition chestBoatModel = ChestBoatModel.createBodyModel();
        LayerDefinition raftModel = RaftModel.createBodyModel();
        LayerDefinition chestRaftModel = ChestRaftModel.createBodyModel();

        BoatTypeOverride.values().forEach(type -> {
            if (type.boatModelName != null) {
                event.registerLayerDefinition(type.boatModelName, () -> type.isRaft ? raftModel : boatModel);
                event.registerLayerDefinition(
                        type.chestBoatModelName,
                        () -> type.isRaft ? chestRaftModel : chestBoatModel
                );
            }
        });
    }

    private static void registerRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof IRenderTypeable) {
                BNRenderLayer layer = ((IRenderTypeable) block).getRenderLayer();
                if (layer == BNRenderLayer.CUTOUT)
                    ItemBlockRenderTypes.setRenderLayer(block, cutout);
                else if (layer == BNRenderLayer.TRANSLUCENT)
                    ItemBlockRenderTypes.setRenderLayer(block, translucent);
            }
        });
        ItemBlockRenderTypes.setRenderLayer(NetherBlocks.MAT_GLOOMWOOD.getSapling(), cutout);
        ItemBlockRenderTypes.setRenderLayer(NetherBlocks.GLOOMSCULK_GEODE, translucent);
        ItemBlockRenderTypes.setRenderLayer(NetherBlocks.GLOOMSCULK_LAMP, translucent);
    }
}
