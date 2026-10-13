package org.betterx.betternether.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;

public class BlockRubeusLeaves extends TintedParticleLeavesBlock {
    public BlockRubeusLeaves(Block sapling) {
        super(0.01F, org.betterx.bclib.behaviours.BehaviourBuilders.createLeaves(
                net.minecraft.world.level.material.MapColor.COLOR_LIGHT_BLUE,
                false
        ).noOcclusion());
    }
}
