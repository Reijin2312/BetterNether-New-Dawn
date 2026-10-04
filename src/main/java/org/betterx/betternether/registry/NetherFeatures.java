package org.betterx.betternether.registry;

import com.mojang.serialization.MapCodec;
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
import org.betterx.betternether.world.features.configs.GloomwoodTreeConfiguration;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;
import org.betterx.betternether.world.structures.city.CityStructure;
import org.betterx.wover.events.api.WorldLifecycle;
import org.betterx.wover.feature.api.FeatureManager;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.feature.Feature;

public class NetherFeatures {
    public static final JellyfishMushroomFeature JELLYFISH_MUSHROOM = register(
            BN.id("jellyfish_mushroom"), JellyfishMushroomFeature.CODEC, new JellyfishMushroomFeature());
    public static final CrystalFeature OBSIDIAN_CRYSTAL = register(
            BN.id("obsidian_crystal"), CrystalFeature.CODEC, new CrystalFeature());
    public static final WartBushFeature WART_BUSH = register(
            BN.id("wart_bush"), WartBushFeature.CODEC, new WartBushFeature());
    public static final RubeusTreeFeature RUBEUS_TREE = register(
            BN.id("rubeus_tree"), RubeusTreeFeature.CODEC,
            new RubeusTreeFeature(NaturalTreeConfiguration.natural()));
    public static final GloomwoodTreeFeature GLOOMWOOD_TREE = register(
            BN.id("gloomwood_tree"), GloomwoodTreeFeature.CODEC,
            new GloomwoodTreeFeature(GloomwoodTreeConfiguration.natural()));
    public static final MushroomFirFeature MUSHROOM_FIR = register(
            BN.id("mushroom_fir"), MushroomFirFeature.CODEC, new MushroomFirFeature());
    public static final BigBrownMushroomFeature BIG_BROWN_MUSHROOM = register(
            BN.id("big_brown_mushroom"), BigBrownMushroomFeature.CODEC, new BigBrownMushroomFeature());
    public static final RubeusBushFeature RUBEUS_BUSH = register(
            BN.id("rubeus_bush"), RubeusBushFeature.CODEC, new RubeusBushFeature());
    public static final LucisFeature LUCIS = register(BN.id("lucis"), LucisFeature.CODEC, new LucisFeature());
    public static final SoulLilyFeature SOUL_LILY = register(
            BN.id("soul_lily"), SoulLilyFeature.CODEC, new SoulLilyFeature());
    public static final WartTreeFeature WART_TREE = register(
            BN.id("wart_tree"), WartTreeFeature.CODEC,
            new WartTreeFeature(NaturalTreeConfiguration.natural()));
    public static final WillowBushFeature WILLOW_BUSH = register(
            BN.id("willow_bush"), WillowBushFeature.CODEC, new WillowBushFeature());
    public static final WillowTreeFeature WILLOW_TREE = register(
            BN.id("willow_tree"), WillowTreeFeature.CODEC, new WillowTreeFeature());
    public static final OldWillowTree OLD_WILLOW_TREE = register(
            BN.id("old_willow_tree"), OldWillowTree.CODEC,
            new OldWillowTree(NaturalTreeConfiguration.naturalLarge()));
    public static final NetherSakuraFeature SAKURA_TREE = register(
            BN.id("sakura_tree"), NetherSakuraFeature.CODEC, new NetherSakuraFeature());
    public static final NetherSakuraBushFeature SAKURA_BUSH = register(
            BN.id("sakura_bush"), NetherSakuraBushFeature.CODEC, new NetherSakuraBushFeature());
    public static final AnchorTreeBranchFeature ANCHOR_TREE_BRANCH = register(
            BN.id("anchor_tree_branch"), AnchorTreeBranchFeature.CODEC, new AnchorTreeBranchFeature());
    public static final AnchorTreeFeature ANCHOR_TREE = register(
            BN.id("anchor_tree"), AnchorTreeFeature.CODEC, new AnchorTreeFeature());
    public static final AnchorTreeRootFeature ANCHOR_TREE_ROOT = register(
            BN.id("anchor_tree_root"), AnchorTreeRootFeature.CODEC, new AnchorTreeRootFeature());
    public static final WartCapFeature WART_CAP = register(
            BN.id("wart_cap"), WartCapFeature.CODEC, new WartCapFeature());
    public static final TwistedVinesFeature TWISTING_VINES = register(
            BN.id("twisting_vines"), TwistedVinesFeature.CODEC, new TwistedVinesFeature());

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

    public static void register() {
        if (registered) return;
        registered = true;

        WorldLifecycle.SERVER_LEVEL_READY.subscribe(NetherFeatures::onWorldLoad);
    }

    private static <F extends Feature> F register(Identifier id, MapCodec<F> codec, F feature) {
        FeatureManager.register(id, codec);
        return feature;
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
