package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourMetal;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;


public class BlockCincinnasiteFrame extends BlockBaseNotFull implements BehaviourMetal {
    public BlockCincinnasiteFrame() {
        super(Properties.ofFullCopy(NetherBlocks.CINCINNASITE_BLOCK).noOcclusion());
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    public boolean skipRendering(BlockState state, BlockState neighbor, Direction facing) {
        return neighbor.getBlock() == this || super.skipRendering(state, neighbor, facing);
    }
}
