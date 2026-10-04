package org.betterx.betternether.world.features;

import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public abstract class ContextFeature implements Feature {
    @Override
    public final boolean place(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos origin) {
        return place(
                level,
                origin,
                random,
                generator.getGenDepth(),
                NetherThreadDataStorage.generatorForThread().context
        );
    }

    protected abstract boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    );
}
