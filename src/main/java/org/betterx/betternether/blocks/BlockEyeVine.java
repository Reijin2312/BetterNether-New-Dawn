package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseVineBlock;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEyeVine extends BaseVineBlock implements BlockLootProvider {
    public BlockEyeVine() {
        super(
                BehaviourBuilders
                        .createStaticVine(MapColor.COLOR_RED),
                9,
                2
        );
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        if (direction == Direction.DOWN && !neighborState.is(this) && !(neighborState.getBlock() instanceof BlockEyeBase)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(NetherBlocks.EYE_SEED);
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull Identifier location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(net.minecraft.world.level.storage.loot.providers.number.ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(NetherBlocks.EYE_SEED)
                                                       .when(ExplosionCondition.survivesExplosion())));
    }
}
