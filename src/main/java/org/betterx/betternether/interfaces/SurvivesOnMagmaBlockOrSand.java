package org.betterx.betternether.interfaces;

import org.betterx.bclib.interfaces.SurvivesOnBlocks;
import org.betterx.bclib.interfaces.SurvivesOnTags;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public interface SurvivesOnMagmaBlockOrSand extends SurvivesOnBlocks, SurvivesOnTags {
    List<Block> GROUND = List.of(Blocks.MAGMA_BLOCK, Blocks.RED_SAND, Blocks.SAND);

    @Override
    default List<Block> getSurvivableBlocks() {
        return GROUND;
    }

    @Override
    default List<net.minecraft.tags.TagKey<Block>> getSurvivableTags() {
        return List.of(CommonBlockTags.SCULK_LIKE);
    }

    @Override
    default boolean isSurvivable(BlockState state) {
        return SurvivesOnBlocks.super.isSurvivable(state) || SurvivesOnTags.super.isSurvivable(state);
    }

    @Override
    default String getSurvivableBlocksString() {
        return SurvivesOnBlocks.super.getSurvivableBlocksString()
                + ", "
                + SurvivesOnTags.super.getSurvivableBlocksString();
    }
}
