package org.betterx.betternether.registry;

import org.betterx.betternether.BN;
import org.betterx.betternether.registry.features.placed.NetherOresPlaced;
import org.betterx.betternether.world.biomes.util.NetherBiomeBuilder;
import org.betterx.betternether.world.features.*;
import org.betterx.betternether.world.structures.city.CityStructure;
import org.betterx.wover.events.api.WorldLifecycle;
import org.betterx.wover.feature.api.FeatureManager;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NetherFeatures {
    public static Feature<NoneFeatureConfiguration> JELLYFISH_MUSHROOM;
    public static Feature<NoneFeatureConfiguration> OBSIDIAN_CRYSTAL;
    public static Feature<NoneFeatureConfiguration> WART_BUSH;
    public static RubeusTreeFeature RUBEUS_TREE;
    public static MushroomFirFeature MUSHROOM_FIR;
    public static BigBrownMushroomFeature BIG_BROWN_MUSHROOM;
    public static Feature<NoneFeatureConfiguration> RUBEUS_BUSH;
    public static Feature<NoneFeatureConfiguration> LUCIS;
    public static SoulLilyFeature SOUL_LILY;
    public static WartTreeFeature WART_TREE;
    public static WillowBushFeature WILLOW_BUSH;
    public static WillowTreeFeature WILLOW_TREE;
    public static OldWillowTree OLD_WILLOW_TREE;
    public static NetherSakuraFeature SAKURA_TREE;
    public static NetherSakuraBushFeature SAKURA_BUSH;
    public static AnchorTreeBranchFeature ANCHOR_TREE_BRANCH;
    public static AnchorTreeFeature ANCHOR_TREE;
    public static AnchorTreeRootFeature ANCHOR_TREE_ROOT;
    public static WartCapFeature WART_CAP;
    public static TwistedVinesFeature TWISTING_VINES;

    private static boolean registered;

    // Features that should be added to all Nether Biomes
    public static NetherBiomeBuilder addDefaultFeatures(NetherBiomeBuilder builder) {
        return builder;
    }

    // Features that should be added to all BN Biomes
    public static void addDefaultBNFeatures(NetherBiomeBuilder builder) {

    }

    public static NetherBiomeBuilder addDefaultOres(NetherBiomeBuilder builder) {
        return builder
                .feature(NetherOresPlaced.CINCINNASITE_ORE)
                .feature(NetherOresPlaced.NETHER_RUBY_ORE_RARE)
                .feature(NetherOresPlaced.NETHER_LAPIS_ORE)
                .feature(NetherOresPlaced.NETHER_REDSTONE_ORE);
    }


    public static void register() {
        if (registered) return;
        registered = true;

        JELLYFISH_MUSHROOM = FeatureManager.register(
                BN.id("jellyfish_mushroom"),
                new JellyfishMushroomFeature()
        );
        OBSIDIAN_CRYSTAL = FeatureManager.register(
                BN.id("obsidian_crystal"),
                new CrystalFeature()
        );
        WART_BUSH = FeatureManager.register(
                BN.id("wart_bush"),
                new WartBushFeature()
        );
        RUBEUS_TREE = FeatureManager.register(
                BN.id("rubeus_tree"),
                new RubeusTreeFeature()
        );
        MUSHROOM_FIR = FeatureManager.register(
                BN.id("mushroom_fir"),
                new MushroomFirFeature()
        );
        BIG_BROWN_MUSHROOM = FeatureManager.register(
                BN.id("big_brown_mushroom"),
                new BigBrownMushroomFeature()
        );
        RUBEUS_BUSH = FeatureManager.register(
                BN.id("rubeus_bush"),
                new RubeusBushFeature()
        );
        LUCIS = FeatureManager.register(
                BN.id("lucis"),
                new LucisFeature()
        );
        SOUL_LILY = FeatureManager.register(
                BN.id("soul_lily"),
                new SoulLilyFeature()
        );
        WART_TREE = FeatureManager.register(
                BN.id("wart_tree"),
                new WartTreeFeature()
        );
        WILLOW_BUSH = FeatureManager.register(
                BN.id("willow_bush"),
                new WillowBushFeature()
        );
        WILLOW_TREE = FeatureManager.register(
                BN.id("willow_tree"),
                new WillowTreeFeature()
        );
        OLD_WILLOW_TREE = FeatureManager.register(
                BN.id("old_willow_tree"),
                new OldWillowTree()
        );
        SAKURA_TREE = FeatureManager.register(
                BN.id("sakura_tree"),
                new NetherSakuraFeature()
        );
        SAKURA_BUSH = FeatureManager.register(
                BN.id("sakura_bush"),
                new NetherSakuraBushFeature()
        );
        ANCHOR_TREE_BRANCH = FeatureManager.register(
                BN.id("anchor_tree_branch"),
                new AnchorTreeBranchFeature()
        );
        ANCHOR_TREE = FeatureManager.register(
                BN.id("anchor_tree"),
                new AnchorTreeFeature()
        );
        ANCHOR_TREE_ROOT = FeatureManager.register(
                BN.id("anchor_tree_root"),
                new AnchorTreeRootFeature()
        );
        WART_CAP = FeatureManager.register(
                BN.id("wart_cap"),
                new WartCapFeature()
        );
        TWISTING_VINES = FeatureManager.register(
                BN.id("twisting_vines"),
                new TwistedVinesFeature()
        );

        WorldLifecycle.SERVER_LEVEL_READY.subscribe(NetherFeatures::onWorldLoad);
    }

    private static void onWorldLoad(
            ServerLevel level,
            ResourceKey<Level> levelResourceKey,
            LevelStem levelStem,
            long seed
    ) {
        if (levelResourceKey.equals(Level.NETHER)) {
            CavesFeature.onLoad(seed);
            PathsFeature.onLoad(seed);

            CityStructure.initGenerator();
        }
    }


}
