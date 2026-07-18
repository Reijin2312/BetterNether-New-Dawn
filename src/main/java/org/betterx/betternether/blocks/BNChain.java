package org.betterx.betternether.blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.betterx.bclib.api.v3.datagen.DropSelfLootProvider;
import org.betterx.bclib.behaviours.interfaces.BehaviourMetal;
import org.betterx.betternether.client.IRenderTypeable;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;


public class BNChain extends ChainBlock implements IRenderTypeable, BehaviourMetal, DropSelfLootProvider<BNChain> {
    public BNChain() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN));
    }

    @Override
    public BNRenderLayer getRenderLayer() {
        return BNRenderLayer.CUTOUT;
    }
}
