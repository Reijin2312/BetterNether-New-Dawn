package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourPlant;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.materials.Materials;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.EnumMap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;

public class BlockPlantWall extends BlockBaseNotFull implements BehaviourPlant, BonemealableBlock {
    private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, box(2, 2, 10, 14, 14, 16),
            Direction.SOUTH, box(2, 2, 0, 14, 14, 6),
            Direction.WEST, box(10, 2, 2, 16, 14, 14),
            Direction.EAST, box(0, 2, 2, 6, 14, 14)
    ));
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public BlockPlantWall(MapColor color) {
        this(color, SoundType.GRASS);
    }

    public BlockPlantWall(MapColor color, SoundType sound) {
        super(Materials.makeNetherGrass(color).sound(sound).offsetType(OffsetType.NONE));
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.addFreshEntity(new ItemEntity(
                level,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                new ItemStack(this)
        ));
    }

    public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos targetPos = pos.relative(direction.getOpposite());
        BlockState targetState = level.getBlockState(targetPos);
        return targetState.isFaceSturdy(level, targetPos, direction) || targetState.is(BlockTags.LEAVES);
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction facing,
            BlockState neighborState,
            LevelAccessor world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (canSurvive(state, world, pos))
            return state;
        else
            return Blocks.AIR.defaultBlockState();
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return BlocksHelper.rotateHorizontal(state, rotation, FACING);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        LevelReader worldView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction[] directions = ctx.getNearestLookingDirections();
        for (int i = 0; i < directions.length; ++i) {
            Direction direction = directions[i];
            if (direction.getAxis().isHorizontal()) {
                Direction direction2 = direction.getOpposite();
                blockState = blockState.setValue(FACING, direction2);
                if (blockState.canSurvive(worldView, blockPos)) {
                    return blockState;
                }
            }
        }
        return null;
    }
}
