package org.betterx.betternether.blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.level.block.Blocks;


public class BNNetherBrick extends BlockBase.Stone {
    public BNNetherBrick() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
    }
}
