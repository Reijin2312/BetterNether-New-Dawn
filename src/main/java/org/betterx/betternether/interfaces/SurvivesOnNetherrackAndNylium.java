package org.betterx.betternether.interfaces;

import org.betterx.bclib.interfaces.SurvivesOnTags;
import org.betterx.betternether.registry.NetherTags;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface SurvivesOnNetherrackAndNylium extends SurvivesOnTags {
    List<TagKey<Block>> TAGS = List.of(CommonBlockTags.NETHERRACK, NetherTags.NETHER_NYLIUM
    );

    @Override
    default List<TagKey<Block>> getSurvivableTags() {
        return TAGS;
    }
}
