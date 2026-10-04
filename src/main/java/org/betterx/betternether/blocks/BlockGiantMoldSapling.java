package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourSapling;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.interfaces.SurvivesOnNetherMycelium;
import org.betterx.betternether.registry.features.configured.NetherTrees;
import org.betterx.wover.state.api.WorldState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.ScheduledTickAccess;

public class BlockGiantMoldSapling extends BlockBaseNotFull implements BonemealableBlock, SurvivesOnNetherMycelium, BehaviourSapling {
    private static final VoxelShape SHAPE = box(4, 0, 4, 12, 14, 12);

    public BlockGiantMoldSapling() {
        super(Materials.NETHER_SAPLING
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
        );
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSurviveOnTop(level, pos);
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            LevelReader world,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction facing,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        if (!canSurvive(state, world, pos))
            return Blocks.AIR.defaultBlockState();
        else
            return state;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return BlocksHelper.isFertile(world.getBlockState(pos.below()))
                ? (random.nextInt(8) == 0)
                : (random.nextInt(16) == 0);
    }

    private void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        NetherTrees.GIANT_MOLD.placeInWorld(WorldState.registryAccess(), world, pos, random);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        grow(world, random, pos, state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        if (BlocksHelper.isFertile(world.getBlockState(pos.below())) ? random.nextInt(8) == 0 : random.nextInt(16) == 0)
            grow(world, random, pos, state);
    }
}
