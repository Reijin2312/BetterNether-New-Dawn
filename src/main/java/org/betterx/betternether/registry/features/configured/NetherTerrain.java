package org.betterx.betternether.registry.features.configured;

import org.betterx.betternether.BetterNether;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureKey;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureManager;
import org.betterx.wover.feature.api.configured.configurators.ForSimpleBlock;
import org.betterx.wover.feature.api.configured.configurators.WithConfiguration;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;

public class NetherTerrain {
    private static final ModCore C = BetterNether.C;

    public static final ConfiguredFeatureKey<ForSimpleBlock> LAVA_PITS = ConfiguredFeatureManager.simple(C.id("lava_pit"));
    public static final ConfiguredFeatureKey<WithConfiguration<Feature<MultifaceGrowthConfiguration>, MultifaceGrowthConfiguration>> SCULK_VEIN
            = ConfiguredFeatureManager.configuration(C.id("sculk_vein"), Feature.MULTIFACE_GROWTH);
}
