package org.betterx.betternether.client;

import org.betterx.bclib.integration.modmenu.ModMenu;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNRenderLayer;
import org.betterx.betternether.config.screen.ConfigScreen;
import org.betterx.betternether.registry.EntityRenderRegistry;
import org.betterx.betternether.registry.NetherParticles;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.registries.BuiltInRegistries;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;

public class BetterNetherClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderLayers();
        EntityRenderRegistry.register();

        NetherParticles.register();
        ModMenu.addModMenuScreen(BetterNether.C.modId, ConfigScreen::new);
    }

    private void registerRenderLayers() {
        ChunkSectionLayer cutout = ChunkSectionLayer.CUTOUT;
        ChunkSectionLayer translucent = ChunkSectionLayer.TRANSLUCENT;
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof IRenderTypeable) {
                BNRenderLayer layer = ((IRenderTypeable) block).getRenderLayer();
                if (layer == BNRenderLayer.CUTOUT)
                    BlockRenderLayerMap.putBlock(block, cutout);
                else if (layer == BNRenderLayer.TRANSLUCENT)
                    BlockRenderLayerMap.putBlock(block, translucent);
            }
        });
    }
}
