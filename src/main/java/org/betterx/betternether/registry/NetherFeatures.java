package org.betterx.betternether.registry;

import org.betterx.betternether.BN;
import org.betterx.betternether.registry.features.placed.NetherOresPlaced;
import org.betterx.betternether.world.biomes.util.NetherBiomeBuilder;
import org.betterx.betternether.world.features.AnchorTreeBranchFeature;
import org.betterx.betternether.world.features.AnchorTreeFeature;
import org.betterx.betternether.world.features.AnchorTreeRootFeature;
import org.betterx.betternether.world.features.BigBrownMushroomFeature;
import org.betterx.betternether.world.features.CavesFeature;
import org.betterx.betternether.world.features.CrystalFeature;
import org.betterx.betternether.world.features.GloomwoodTreeFeature;
import org.betterx.betternether.world.features.JellyfishMushroomFeature;
import org.betterx.betternether.world.features.LucisFeature;
import org.betterx.betternether.world.features.MushroomFirFeature;
import org.betterx.betternether.world.features.NetherSakuraBushFeature;
import org.betterx.betternether.world.features.NetherSakuraFeature;
import org.betterx.betternether.world.features.OldWillowTree;
import org.betterx.betternether.world.features.PathsFeature;
import org.betterx.betternether.world.features.RubeusBushFeature;
import org.betterx.betternether.world.features.RubeusTreeFeature;
import org.betterx.betternether.world.features.SoulLilyFeature;
import org.betterx.betternether.world.features.TwistedVinesFeature;
import org.betterx.betternether.world.features.WartBushFeature;
import org.betterx.betternether.world.features.WartCapFeature;
import org.betterx.betternether.world.features.WartTreeFeature;
import org.betterx.betternether.world.features.WillowBushFeature;
import org.betterx.betternether.world.features.WillowTreeFeature;
import org.betterx.betternether.world.structures.city.CityStructure;
import org.betterx.wover.events.api.WorldLifecycle;
import org.betterx.wover.feature.api.FeatureManager;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import net.neoforged.neoforge.registries.RegisterEvent;

public class NetherFeatures {
    // Будут заполнены во время RegisterEvent, до заморозки реестров
    public static Feature<NoneFeatureConfiguration> JELLYFISH_MUSHROOM;
    public static Feature<NoneFeatureConfiguration> OBSIDIAN_CRYSTAL;
    public static Feature<NoneFeatureConfiguration> WART_BUSH;
    public static RubeusTreeFeature RUBEUS_TREE;
    public static GloomwoodTreeFeature GLOOMWOOD_TREE;
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

    private static boolean registered = false;

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

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.FEATURE)) {
            return;
        }
        register();
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
        GLOOMWOOD_TREE = FeatureManager.register(BN.id("gloomwood_tree"), new GloomwoodTreeFeature());
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
