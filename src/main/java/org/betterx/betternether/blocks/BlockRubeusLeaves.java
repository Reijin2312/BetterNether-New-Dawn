package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BaseLeavesBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

/**
 * Rubeus leaves use vanilla leaf ticking. RubeusTreeFeature already writes their distance from logs,
 * so natural leaves decay while player-placed leaves remain persistent.
 */
public class BlockRubeusLeaves extends BaseLeavesBlock {
    public BlockRubeusLeaves(Block sapling) {
        super(sapling, MapColor.COLOR_LIGHT_BLUE);
    }
}
