package org.betterx.betternether.blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.betterx.bclib.behaviours.interfaces.BehaviourStone;
import org.betterx.wover.block.api.BlockTagProvider;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.util.RandomSource;


public class BlockSoulSandstone extends BlockBase implements BlockTagProvider, BehaviourStone {
    public static final BooleanProperty UP = BooleanProperty.create("up");

    public BlockSoulSandstone() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(UP);
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            LevelReader world,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction facing,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        return state.setValue(UP, world.getBlockState(pos.above()).getBlock() != this);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                   .setValue(UP, ctx.getLevel().getBlockState(ctx.getClickedPos().above()).getBlock() != this);
    }

    @Override
    public void registerBlockTags(Identifier location, TagBootstrapContext<Block> context) {
        context.add(this, CommonBlockTags.NETHER_TERRAIN);
    }
}
