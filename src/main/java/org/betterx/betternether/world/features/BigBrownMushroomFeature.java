package org.betterx.betternether.world.features;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.EnumMap;
import java.util.Map;

public class BigBrownMushroomFeature extends ContextFeature<NoneFeatureConfiguration> {
    private static final Map<org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape, BlockState> STATE_CACHE = new EnumMap<>(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.class);

    private static BlockState state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape shape) {
        return STATE_CACHE.computeIfAbsent(
                shape,
                s -> org.betterx.betternether.registry.NetherBlocks.BROWN_LARGE_MUSHROOM
                        .defaultBlockState()
                        .setValue(org.betterx.betternether.blocks.BlockBrownLargeMushroom.SHAPE, s)
        );
    }

    public BigBrownMushroomFeature() {
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
        int size = org.betterx.bclib.util.BlocksHelper.blockCount(
                level,
                pos,
                Direction.UP,
                2 + random.nextInt(3),
                org.betterx.bclib.util.BlocksHelper::isFree
        );
        if (size < 3) return false;

        boolean hasAir = true;
        for (int x = -1; x < 2; x++)
            for (int z = -1; z < 2; z++)
                hasAir = hasAir && level.isEmptyBlock(pos.above(size).offset(x, 0, z));

        if (hasAir) {
            BlocksHelper.setWithoutUpdate(level, pos, state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.BOTTOM));
            for (int y = 1; y < size; y++)
                BlocksHelper.setWithoutUpdate(level, pos.above(y), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.MIDDLE));
            pos = pos.above(size);
            BlocksHelper.setWithUpdate(level, pos, state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.TOP));
            BlocksHelper.setWithoutUpdate(level, pos.north(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.SIDE_N));
            BlocksHelper.setWithoutUpdate(level, pos.south(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.SIDE_S));
            BlocksHelper.setWithoutUpdate(level, pos.east(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.SIDE_E));
            BlocksHelper.setWithoutUpdate(level, pos.west(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.SIDE_W));
            BlocksHelper.setWithoutUpdate(level, pos.north().east(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.CORNER_N));
            BlocksHelper.setWithoutUpdate(level, pos.north().west(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.CORNER_W));
            BlocksHelper.setWithoutUpdate(level, pos.south().east(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.CORNER_E));
            BlocksHelper.setWithoutUpdate(level, pos.south().west(), state(org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape.CORNER_S));

            return true;
        }
        return false;
    }

}
