package org.betterx.betternether.blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.betterx.bclib.behaviours.interfaces.BehaviourMetal;
import org.betterx.betternether.registry.NetherBlocks;


public class BlockCincinnasiteLantern extends BlockBase implements BehaviourMetal {
    public BlockCincinnasiteLantern() {
        super(BlockBehaviour.Properties.ofFullCopy(NetherBlocks.CINCINNASITE_BLOCK).lightLevel(state -> 15));
    }
}
