package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourSapling;
import org.betterx.bclib.blocks.FeatureSaplingBlock;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherGameRules;
import org.betterx.betternether.interfaces.SurvivesOnNetherGround;
import org.betterx.betternether.registry.features.configured.NetherTrees;
import org.betterx.wover.state.api.WorldState;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;


public class BlockWillowSapling extends FeatureSaplingBlock implements BonemealableBlock, SurvivesOnNetherGround, BehaviourSapling {
    public BlockWillowSapling() {
        super((level, pos, state, rnd) -> NetherTrees.WILLOW_TREE
                .placeInWorld(WorldState.registryAccess(), level, pos, rnd)
        );
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(STAGE) != 0 && largeWillowsEnabled(level)) {
            final BlockPos origin = findSquare(level, pos);
            if (origin != null) {
                final BlockState[] old = new BlockState[4];
                int i = 0;
                for (int dx = 0; dx < 2; dx++) for (int dz = 0; dz < 2; dz++) {
                    BlockPos at = origin.offset(dx, 0, dz);
                    old[i++] = level.getBlockState(at);
                    level.setBlock(at, Blocks.AIR.defaultBlockState(), 4);
                }
                if (NetherTrees.OLD_WILLOW_TREE.placeInWorld(WorldState.registryAccess(), level, origin, random)) return;
                i = 0;
                for (int dx = 0; dx < 2; dx++) for (int dz = 0; dz < 2; dz++)
                    level.setBlock(origin.offset(dx, 0, dz), old[i++], 4);
            }
        }
        super.advanceTree(level, pos, state, random);
    }

    private BlockPos findSquare(ServerLevel level, BlockPos pos) {
        for (int ox = -1; ox <= 0; ox++) for (int oz = -1; oz <= 0; oz++) {
            BlockPos origin = pos.offset(ox, 0, oz);
            boolean matches = true;
            for (int dx = 0; dx < 2 && matches; dx++) for (int dz = 0; dz < 2; dz++)
                if (!level.getBlockState(origin.offset(dx, 0, dz)).is(this)) { matches = false; break; }
            if (matches) return origin;
        }
        return null;
    }

    private boolean largeWillowsEnabled(ServerLevel level) {
        return level.getGameRules().getBoolean(NetherGameRules.GROW_LARGE_WILLOWS);
    }

    @Override
    public boolean isBonemealSuccess(
            Level world,
            @NotNull RandomSource random,
            BlockPos pos,
            @NotNull BlockState state
    ) {
        return (BlocksHelper.isFertile(world.getBlockState(pos.below()))
                ? (random.nextInt(8) == 0)
                : (random.nextInt(16) == 0));
    }

    @Override
    protected boolean mayPlaceOn(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos
    ) {
        return isSurvivable(blockState);
    }
}
