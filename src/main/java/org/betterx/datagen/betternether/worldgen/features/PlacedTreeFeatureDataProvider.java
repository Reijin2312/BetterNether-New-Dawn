package org.betterx.datagen.betternether.worldgen.features;

import org.betterx.betternether.BN;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.configured.NetherTrees;
import org.betterx.betternether.registry.features.placed.NetherTreesPlaced;
import org.betterx.betternether.world.features.configs.GloomwoodTreeConfiguration;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;
import org.betterx.wover.block.api.predicate.BlockPredicates;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.multi.WoverFeatureProvider;
import org.betterx.wover.feature.api.features.config.PillarFeatureConfig;

import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class PlacedTreeFeatureDataProvider extends WoverFeatureProvider {
    private static final float GROVE_SCALE_XZ = 2.0F;
    private static final float GROVE_SCALE_Y = 1.0F;
    private static final double GROVE_CORE = 0.25;
    private static final double GROVE_EDGE = 0.05;
    private static final float BLEACHED_GLOOMWOOD_CHANCE = 0.33F;

    public PlacedTreeFeatureDataProvider(ModCore modCore) {
        super(modCore, modCore.id("trees"));
    }

    @Override
    protected void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
        NetherTrees.RUBEUS_TREE
                .bootstrap(ctx)
                .configuration(NaturalTreeConfiguration.natural())
                .register();

        NetherTrees.GLOOMWOOD_TREE
                .bootstrap(ctx)
                .configuration(GloomwoodTreeConfiguration.natural())
                .register();

        NetherTrees.GLOOMWOOD_TREE_SOLITARY
                .bootstrap(ctx)
                .configuration(GloomwoodTreeConfiguration.bleachedSometimes(BLEACHED_GLOOMWOOD_CHANCE))
                .register();

        NetherTrees.MUSHROOM_FIR.bootstrap(ctx).register();

        NetherTrees.STALAGNATE
                .bootstrap(ctx)
                .transformer(PillarFeatureConfig.KnownTransformers.TRIPLE_SHAPE_FILL)
                .direction(Direction.UP)
                .blockState(NetherBlocks.MAT_STALAGNATE.getTrunk())
                .minHeight(3)
                .maxHeight(64)
                .register();

        NetherTrees.STALAGNATE_DOWN
                .bootstrap(ctx)
                .transformer(PillarFeatureConfig.KnownTransformers.TRIPLE_SHAPE_FILL)
                .direction(Direction.DOWN)
                .blockState(NetherBlocks.MAT_STALAGNATE.getTrunk())
                .minHeight(3)
                .maxHeight(64)
                .register();

        NetherTrees.GIANT_MOLD
                .bootstrap(ctx)
                .direction(Direction.UP)
                .addTripleShape(NetherBlocks.GIANT_MOLD.defaultBlockState(), ClampedNormalInt.of(5, 1.3f, 3, 8))
                .register();

        NetherTrees.PATCH_BIG_RED_MUSHROOM
                .bootstrap(ctx)
                .direction(Direction.UP)
                .prioritizeTip()
                .addTripleShape(NetherBlocks.RED_LARGE_MUSHROOM.defaultBlockState(), ClampedNormalInt.of(6, 2.1f, 3, 9))
                .inlinePlace()
                .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
                .inRandomPatch()
                .tries(30)
                .spreadXZ(6)
                .register();

        NetherTrees.PATCH_BIG_BROWN_MUSHROOM
                .bootstrap(ctx)
                .inlinePlace()
                .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
                .inRandomPatch()
                .likeDefaultNetherVegetation()
                .tries(30)
                .spreadXZ(7)
                .register();

        NetherTrees.WART_TREE
                .bootstrap(ctx)
                .configuration(NaturalTreeConfiguration.natural())
                .register();

        NetherTrees.WILLOW_TREE.bootstrap(ctx).register();
        NetherTrees.ANCHOR_TREE_BRANCH.bootstrap(ctx).register();
        NetherTrees.ANCHOR_TREE.bootstrap(ctx).register();
        NetherTrees.ANCHOR_TREE_ROOT.bootstrap(ctx).register();
        NetherTrees.SAKURA_TREE.bootstrap(ctx).register();
    }

    @Override
    protected void bootstrapPlaced(BootstrapContext<PlacedFeature> ctx) {
        NetherTreesPlaced.CRIMSON_GLOWING_TREE
                .inlineConfiguration(ctx)
                .templates()
                .add(BN.id("trees/crimson_glow_tree_01"), 0, 1.0f)
                .add(BN.id("trees/crimson_glow_tree_02"), 0, 1.0f)
                .add(BN.id("trees/crimson_glow_tree_03"), 0, 1.0f)
                .add(BN.id("trees/crimson_glow_tree_04"), 0, 1.0f)
                .inlinePlace()
                .vanillaNetherGround(12)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.CRIMSON_PINE
                .inlineConfiguration(ctx)
                .templates()
                .add(BN.id("trees/crimson_pine_01"), -1, 1.0f)
                .add(BN.id("trees/crimson_pine_02"), -1, 1.0f)
                .add(BN.id("trees/crimson_pine_03"), -1, 1.0f)
                .add(BN.id("trees/crimson_pine_04"), 0, 1.0f)
                .add(BN.id("trees/crimson_pine_05"), 0, 1.0f)
                .inlinePlace()
                .vanillaNetherGround(12)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.RUBEUS_TREE
                .place(ctx)
                .vanillaNetherGround(12)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.MUSHROOM_FIR
                .place(ctx)
                .vanillaNetherGround(14)
                .isEmptyAndOnNetherGround()
                .register();

        // Gloomwood grows on the sculk floor rather than on nether ground, so the usual
        // isEmptyAndOnNetherGround() filter would reject every position in its own biome.
        NetherTreesPlaced.GLOOMWOOD_TREE
                .place(ctx)
                .vanillaNetherGround(8)
                .noiseAbove(GROVE_CORE, GROVE_SCALE_XZ, GROVE_SCALE_Y)
                .isEmptyAndOn(BlockPredicate.matchesTag(CommonBlockTags.SCULK_LIKE))
                .register();

        NetherTreesPlaced.GLOOMWOOD_TREE_EDGE
                .place(ctx)
                .vanillaNetherGround(3)
                .noiseIn(GROVE_EDGE, GROVE_CORE, GROVE_SCALE_XZ, GROVE_SCALE_Y)
                .isEmptyAndOn(BlockPredicate.matchesTag(CommonBlockTags.SCULK_LIKE))
                .register();

        NetherTreesPlaced.GLOOMWOOD_TREE_SOLITARY
                .place(ctx)
                .vanillaNetherGround(1)
                .noiseBelow(GROVE_EDGE, GROVE_SCALE_XZ, GROVE_SCALE_Y)
                .onceEvery(3)
                .isEmptyAndOn(BlockPredicate.matchesTag(CommonBlockTags.SCULK_LIKE))
                .register();

        NetherTreesPlaced.STALAGNATE
                .place(ctx, NetherTrees.STALAGNATE)
                .isEmptyAndOnNetherGround()
                .inRandomPatch()
                .tries(30)
                .spreadXZ(6)
                .inlinePlace()
                .vanillaNetherGround(6)
                .onceEvery(7)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.GIANT_MOLD
                .place(ctx, NetherTrees.GIANT_MOLD)
                .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
                .inRandomPatch()
                .tries(30)
                .spreadXZ(6)
                .inlinePlace()
                .vanillaNetherGround(6)
                .onceEvery(5)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.BIG_RED_MUSHROOM
                .place(ctx)
                .vanillaNetherGround(6)
                .onceEvery(2)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.BIG_BROWN_MUSHROOM
                .place(ctx)
                .vanillaNetherGround(6)
                .onceEvery(2)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.OLD_RED_MUSHROOM
                .inlineConfiguration(ctx)
                .templates()
                .add(BN.id("trees/red_mushroom_01"), -1, 1.0f)
                .add(BN.id("trees/red_mushroom_02"), -0, 1.0f)
                .add(BN.id("trees/red_mushroom_03"), -0, 1.0f)
                .add(BN.id("trees/red_mushroom_04"), -3, 1.0f)
                .add(BN.id("trees/red_mushroom_05"), -3, 1.0f)
                .add(BN.id("trees/red_mushroom_06"), -1, 1.0f)
                .add(BN.id("trees/red_mushroom_07"), -4, 1.0f)
                .inlinePlace()
                .vanillaNetherGround(5)
                .onceEvery(3)
                .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
                .register();

        NetherTreesPlaced.OLD_BROWN_MUSHROOM
                .inlineConfiguration(ctx)
                .templates()
                .add(BN.id("trees/brown_mushroom_02"), -3, 1.0f)
                .add(BN.id("trees/brown_mushroom_03"), -2, 1.0f)
                .add(BN.id("trees/brown_mushroom_01"), -2, 1.0f)
                .add(BN.id("trees/brown_mushroom_04"), -1, 1.0f)
                .inlinePlace()
                .vanillaNetherGround(4)
                .onceEvery(3)
                .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
                .register();

        NetherTreesPlaced.SOUL_LILY
                .inlineConfiguration(ctx)
                .withFeature(NetherFeatures.SOUL_LILY)
                .inlinePlace()
                .isEmptyAndOnNetherGround()
                .inRandomPatch()
                .spreadXZ(3)
                .spreadY(3)
                .inlinePlace()
                .vanillaNetherGround(6)
                .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
                .register();

        NetherTreesPlaced.WART_TREE
                .place(ctx)
                .vanillaNetherGround(14)
                .onceEvery(3)
                .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
                .register();

        NetherTreesPlaced.WILLOW_TREE
                .place(ctx)
                .vanillaNetherGround(14)
                .onceEvery(3)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.OLD_WILLOW_TREE
                .place(ctx)
                .vanillaNetherGround(14)
                .onceEvery(16)
                .isEmptyAndOnNetherGround()
                .register();

        NetherTreesPlaced.SAKURA_TREE
                .place(ctx)
                .betterNetherCeiling(5)
                .onceEvery(6)
                .isEmptyAndUnderNetherGround()
                .register();

        NetherTreesPlaced.ANCHOR_TREE
                .place(ctx)
                .betterNetherCeiling(5)
                .isEmptyAndUnderNetherGround()
                .onceEvery(15)
                .register();

        NetherTreesPlaced.ANCHOR_TREE_SPARSE
                .place(ctx)
                .onlyInBiome()
                .count(1)
                .randomHeight4FromFloorCeil()
                .findSolidCeil(5)
                .isEmptyAndUnderNetherGround()
                .onceEvery(7)
                .register();

        NetherTreesPlaced.ANCHOR_TREE_BRANCH
                .place(ctx)
                .betterNetherCeiling(3)
                .isEmptyAndUnderNetherGround()
                .onceEvery(11)
                .register();

        NetherTreesPlaced.ANCHOR_TREE_ROOT
                .place(ctx)
                .betterNetherCeiling(2)
                .onceEvery(5)
                .isEmptyAndUnderNetherGround()
                .register();

        NetherTreesPlaced.BIG_WARPED_TREE
                .inlineConfiguration(ctx)
                .templates()
                .add(BN.id("trees/warped_tree_01"), -1, 1.0f)
                .add(BN.id("trees/warped_tree_02"), -1, 1.0f)
                .add(BN.id("trees/warped_tree_03"), -1, 1.0f)
                .add(BN.id("trees/warped_tree_04"), -1, 1.0f)
                .add(BN.id("trees/warped_tree_05"), -3, 1.0f)
                .inlinePlace()
                .vanillaNetherGround(6)
                .onceEvery(2)
                .isEmptyAndOnNetherGround()
                .register();

    }
}
