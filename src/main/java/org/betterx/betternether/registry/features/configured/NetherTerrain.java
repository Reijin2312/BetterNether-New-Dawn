package org.betterx.betternether.registry.features.configured;

import org.betterx.betternether.BetterNether;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.feature.api.configured.FeatureKey;
import org.betterx.wover.feature.api.configured.FeatureContentManager;
import org.betterx.wover.feature.api.configured.configurators.ForSimpleBlock;
import org.betterx.wover.feature.api.configured.configurators.WithFeature;

import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import org.betterx.betternether.registry.NetherBlocks;
public class NetherTerrain {
    private static final ModCore C = BetterNether.C;

    public static final FeatureKey<ForSimpleBlock> LAVA_PITS = FeatureContentManager.simple(C.id("lava_pit"));
    public static final FeatureKey<WithFeature<MultifaceGrowthFeature>> SCULK_VEIN = FeatureContentManager.withFeature(
            C.id("sculk_vein"),
            new MultifaceGrowthFeature(
                    Blocks.SCULK_VEIN,
                    10,
                    true,
                    true,
                    true,
                    0.75F,
                    HolderSet.direct(
                            Block::builtInRegistryHolder,
                            NetherBlocks.BLEACHED_GLOOMSCULK,
                            NetherBlocks.GLOOMSCULK_GEODE,
                            NetherBlocks.MOLTEN_GLOOMSCULK,
                            NetherBlocks.VEINED_GLOOMSCULK
                    )
            )
    );
}
