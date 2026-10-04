package org.betterx.betternether.world.features;

import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public abstract class NonOverlappingFeature<FC extends NaturalTreeConfiguration> extends ContextFeature {
    public final FC config;

    protected NonOverlappingFeature(FC config) {
        this.config = config;
    }

    protected abstract boolean isStructure(BlockState state);

    protected abstract boolean isGround(BlockState state);

    protected boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        if (isGround(world.getBlockState(pos.below())) && noObjNear(world, pos, this.config)) {
            return grow(world, pos, random, this.config, context);
        }
        return false;
    }

    protected abstract boolean grow(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            FC config,
            StructureGeneratorThreadContext context
    );


    private boolean noObjNear(LevelAccessor world, BlockPos pos, FC config) {
        final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();

        int x1 = pos.getX() - config.distance;
        int z1 = pos.getZ() - config.distance;
        int x2 = pos.getX() + config.distance;
        int z2 = pos.getZ() + config.distance;
        POS.setY(pos.getY());
        for (int x = x1; x <= x2; x++) {
            POS.setX(x);
            for (int z = z1; z <= z2; z++) {
                POS.setZ(z);
                if (isInside(x - pos.getX(), z - pos.getZ(), config) && isStructure(world.getBlockState(POS)))
                    return false;
            }
        }
        return true;
    }

    private boolean isInside(int x, int z, FC config) {
        return (Math.abs(x) + Math.abs(z)) <= config.manDist;
    }

}
