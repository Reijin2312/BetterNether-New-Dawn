package org.betterx.betternether.integrations.jade;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockCommonPlant;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        BetterNether.C.log.info("Registering Jade Integration for BetterNether.");
        registration.registerBlockComponent(NetherPlantProvider.INSTANCE, BlockCommonPlant.class);
    }
}
