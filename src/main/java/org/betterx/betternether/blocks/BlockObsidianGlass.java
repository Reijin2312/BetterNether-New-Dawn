package org.betterx.betternether.blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.betterx.bclib.behaviours.interfaces.BehaviourGlass;
import org.betterx.bclib.behaviours.interfaces.BehaviourImmobile;
import org.betterx.bclib.behaviours.interfaces.BehaviourPortalFrame;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


public class BlockObsidianGlass extends BlockBaseNotFull implements BehaviourImmobile, BehaviourPortalFrame, BehaviourGlass {
    public BlockObsidianGlass() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
                                 .noOcclusion()
                                 .isSuffocating((arg1, arg2, arg3) -> {
                                     return false;
                                 })
                                 .isViewBlocking((arg1, arg2, arg3) -> {
                                     return false;
                                 }));
        this.setRenderLayer(BNRenderLayer.TRANSLUCENT);
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
