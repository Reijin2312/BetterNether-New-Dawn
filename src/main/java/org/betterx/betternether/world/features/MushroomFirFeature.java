package org.betterx.betternether.world.features;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockMushroomFir;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;
import org.betterx.wover.feature.api.features.GrowableFeature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.EnumMap;
import java.util.Map;

public class MushroomFirFeature extends ContextFeature<NoneFeatureConfiguration> implements GrowableFeature<NoneFeatureConfiguration> {

    private static final Map<BlockMushroomFir.MushroomFirShape, BlockState> STATE_CACHE = new EnumMap<>(BlockMushroomFir.MushroomFirShape.class);
    private static BlockState state(BlockMushroomFir.MushroomFirShape shape) {
        return STATE_CACHE.computeIfAbsent(shape, s -> NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                                    .defaultBlockState()
                                                                                    .setValue(BlockMushroomFir.SHAPE, s));
    }

    private static BlockState blueMycelium() {
        return NetherBlocks.NETHER_MYCELIUM.defaultBlockState().setValue(org.betterx.betternether.blocks.BlockNetherMycelium.IS_BLUE, true);
    }

    public MushroomFirFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    protected boolean place(
            ServerLevelAccessor level,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration config,
            int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        if (!level.getBlockState(pos.below()).is(org.betterx.wover.tag.api.predefined.CommonBlockTags.MYCELIUM))
            return false;

        final float scale_factor = MAX_HEIGHT / 128.0f;

        if (level.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM) {
            return grow(level, pos, random, scale_factor, context);
        }
        return false;
    }

    protected boolean grow(
            ServerLevelAccessor level,
            BlockPos pos,
            RandomSource random,
            float scale_factor,
            StructureGeneratorThreadContext context
    ) {
        final int RANDOM_BOUND = (int) (5 * scale_factor);
        int h = 3 + random.nextInt(RANDOM_BOUND);
        for (int y = 1; y < h; y++)
            if (!level.isEmptyBlock(pos.above(y))) {
                h = y;
                break;
            }
        if (h < 3)
            return false;

        BlocksHelper.setWithUpdate(level, pos, state(BlockMushroomFir.MushroomFirShape.BOTTOM));
        int h2 = (h + 1) >> 1;
        h += pos.getY();
        h2 += pos.getY();
        context.POS.set(pos);
        for (int y = pos.getY() + 1; y < h2; y++) {
            context.POS.setY(y);
            BlocksHelper.setWithUpdate(level, context.POS, state(BlockMushroomFir.MushroomFirShape.MIDDLE));
        }
        for (int y = h2; y < h; y++) {
            context.POS.setY(y);
            BlocksHelper.setWithUpdate(level, context.POS, state(BlockMushroomFir.MushroomFirShape.TOP));
        }
        int h3 = (h2 + h) >> 1;
        for (int y = h2 - 1; y < h3; y++) {
            context.POS.setY(y);
            BlockPos branch;
            if (random.nextBoolean()) {
                branch = context.POS.north();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_BIG_S));
            }
            if (random.nextBoolean()) {
                branch = context.POS.south();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_BIG_N));
            }
            if (random.nextBoolean()) {
                branch = context.POS.east();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_BIG_W));
            }
            if (random.nextBoolean()) {
                branch = context.POS.west();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_BIG_E));
            }
        }
        for (int y = h3; y < h; y++) {
            context.POS.setY(y);
            BlockPos branch;
            if (random.nextBoolean()) {
                branch = context.POS.north();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_SMALL_S));
            }
            if (random.nextBoolean()) {
                branch = context.POS.south();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_SMALL_N));
            }
            if (random.nextBoolean()) {
                branch = context.POS.east();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_SMALL_W));
            }
            if (random.nextBoolean()) {
                branch = context.POS.west();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, state(BlockMushroomFir.MushroomFirShape.SIDE_SMALL_E));

            }
        }
        context.POS.setY(h);
        if (level.isEmptyBlock(context.POS))
            BlocksHelper.setWithUpdate(level, context.POS, state(BlockMushroomFir.MushroomFirShape.END));

        BlocksHelper.cover(
                level,
                pos.below(),
                NetherBlocks.NETHER_MYCELIUM,
                blueMycelium(),
                5,
                random
        );
        return true;
    }

    @Override
    public boolean grow(
            ServerLevelAccessor level,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration configuration
    ) {
        return grow(level, pos, random, 1, NetherThreadDataStorage.generatorForThread().context);
    }
}
