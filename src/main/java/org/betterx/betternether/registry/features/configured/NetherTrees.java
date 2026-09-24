package org.betterx.betternether.registry.features.configured;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.*;
import org.betterx.betternether.world.features.configs.GloomwoodTreeConfiguration;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.feature.api.configured.FeatureKey;
import org.betterx.wover.feature.api.configured.FeatureContentManager;
import org.betterx.wover.feature.api.configured.configurators.AsBlockColumn;
import org.betterx.wover.feature.api.configured.configurators.AsPillar;
import org.betterx.wover.feature.api.configured.configurators.WithFeature;

public class NetherTrees {
    private static final ModCore C = BetterNether.C;

    public static final FeatureKey<WithFeature<RubeusTreeFeature>> RUBEUS_TREE
            = FeatureContentManager.withFeature(C.id("tree_rubeus"), NetherFeatures.RUBEUS_TREE);

    public static final FeatureKey<WithFeature<GloomwoodTreeFeature>> GLOOMWOOD_TREE
            = FeatureContentManager.withFeature(C.id("tree_gloomwood"), NetherFeatures.GLOOMWOOD_TREE);

    public static final FeatureKey<WithFeature<GloomwoodTreeFeature>> GLOOMWOOD_TREE_SOLITARY
            = FeatureContentManager.withFeature(C.id("tree_gloomwood_solitary"), NetherFeatures.GLOOMWOOD_TREE);

    public static final FeatureKey<WithFeature<MushroomFirFeature>> MUSHROOM_FIR
            = FeatureContentManager.withFeature(C.id("tree_mushroom_fir"), NetherFeatures.MUSHROOM_FIR);

    public static final FeatureKey<AsPillar> STALAGNATE
            = FeatureContentManager.pillar(C.id("stalagnate"));

    public static final FeatureKey<AsPillar> STALAGNATE_DOWN
            = FeatureContentManager.pillar(C.id("stalagnate_down"));

    public static final FeatureKey<AsBlockColumn> GIANT_MOLD
            = FeatureContentManager.blockColumn(C.id("giant_mold"));

    public static final FeatureKey<AsBlockColumn> PATCH_BIG_RED_MUSHROOM
            = FeatureContentManager.blockColumn(C.id("patch_big_red_mushroom"));

    public static final FeatureKey<WithFeature<BigBrownMushroomFeature>> PATCH_BIG_BROWN_MUSHROOM
            = FeatureContentManager.withFeature(C.id("patch_big_brown_mushroom"), NetherFeatures.BIG_BROWN_MUSHROOM);

    public static final FeatureKey<WithFeature<WartTreeFeature>> WART_TREE
            = FeatureContentManager.withFeature(C.id("tree_wart"), NetherFeatures.WART_TREE);

    public static final FeatureKey<WithFeature<WillowTreeFeature>> WILLOW_TREE
            = FeatureContentManager.withFeature(C.id("tree_willow"), NetherFeatures.WILLOW_TREE);

    public static final FeatureKey<WithFeature<OldWillowTree>> OLD_WILLOW_TREE
            = FeatureContentManager.withFeature(C.id("tree_old_willow"), NetherFeatures.OLD_WILLOW_TREE);

    public static final FeatureKey<WithFeature<AnchorTreeBranchFeature>> ANCHOR_TREE_BRANCH
            = FeatureContentManager.withFeature(C.id("anchor_tree_branch"), NetherFeatures.ANCHOR_TREE_BRANCH);

    public static final FeatureKey<WithFeature<AnchorTreeFeature>> ANCHOR_TREE
            = FeatureContentManager.withFeature(C.id("anchor_tree"), NetherFeatures.ANCHOR_TREE);

    public static final FeatureKey<WithFeature<AnchorTreeRootFeature>> ANCHOR_TREE_ROOT
            = FeatureContentManager.withFeature(C.id("anchor_tree_root"), NetherFeatures.ANCHOR_TREE_ROOT);

    public static final FeatureKey<WithFeature<NetherSakuraFeature>> SAKURA_TREE
            = FeatureContentManager.withFeature(C.id("tree_sakura"), NetherFeatures.SAKURA_TREE);
}
