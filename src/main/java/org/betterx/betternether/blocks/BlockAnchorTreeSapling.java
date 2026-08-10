package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourSapling;
import org.betterx.bclib.blocks.FeatureSaplingBlock;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;
import org.betterx.betternether.registry.NetherGameRules;
import org.betterx.betternether.registry.features.configured.NetherTrees;
import org.betterx.wover.state.api.WorldState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockAnchorTreeSapling extends FeatureSaplingBlock
        implements BonemealableBlock, SurvivesOnNetherrack, BehaviourSapling {
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private static final VoxelShape FLOOR_SHAPE = Block.box(4, 0, 4, 12, 14, 12);
    private static final VoxelShape CEILING_SHAPE = Block.box(4, 2, 4, 12, 16, 12);

    public BlockAnchorTreeSapling(BlockBehaviour.Properties ignored) {
        super((level, pos, state, rnd) -> NetherTrees.ANCHOR_TREE_BRANCH
                .placeInWorld(WorldState.registryAccess(), level, pos, rnd));
        registerDefaultState(getStateDefinition().any().setValue(HANGING, true).setValue(STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HANGING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        boolean canHang = isSurvivable(context.getLevel().getBlockState(pos.above()));
        boolean canStand = isSurvivable(context.getLevel().getBlockState(pos.below()));
        boolean hanging = context.getClickedFace() == Direction.DOWN && canHang || !canStand && canHang;
        return defaultBlockState().setValue(HANGING, hanging);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos support = state.getValue(HANGING) ? pos.above() : pos.below();
        return isSurvivable(level.getBlockState(support));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? CEILING_SHAPE : FLOOR_SHAPE;
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(HANGING)) {
            super.advanceTree(level, pos, state, random);
            return;
        }
        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.cycle(STAGE), 4);
            return;
        }
        if (!largeAnchorTreesEnabled(level)) return;
        BlockPos origin = findSquare(level, pos);
        if (origin == null) return;
        BlockState[] old = new BlockState[4];
        int i = 0;
        for (int dx = 0; dx < 2; dx++) for (int dz = 0; dz < 2; dz++) {
            BlockPos at = origin.offset(dx, 0, dz);
            old[i++] = level.getBlockState(at);
            level.setBlock(at, Blocks.AIR.defaultBlockState(), 4);
        }
        if (NetherTrees.ANCHOR_TREE.placeInWorld(WorldState.registryAccess(), level, origin, random)) return;
        i = 0;
        for (int dx = 0; dx < 2; dx++) for (int dz = 0; dz < 2; dz++)
            level.setBlock(origin.offset(dx, 0, dz), old[i++], 4);
    }

    private BlockPos findSquare(ServerLevel level, BlockPos pos) {
        for (int ox = -1; ox <= 0; ox++) for (int oz = -1; oz <= 0; oz++) {
            BlockPos origin = pos.offset(ox, 0, oz);
            boolean matches = true;
            for (int dx = 0; dx < 2 && matches; dx++) for (int dz = 0; dz < 2; dz++) {
                BlockState candidate = level.getBlockState(origin.offset(dx, 0, dz));
                if (!candidate.is(this) || candidate.getValue(HANGING)) { matches = false; break; }
            }
            if (matches) return origin;
        }
        return null;
    }

    private boolean largeAnchorTreesEnabled(ServerLevel level) {
        return level.getGameRules().getBoolean(NetherGameRules.GROW_LARGE_ANCHOR_TREES);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return isSurvivable(state);
    }
}
