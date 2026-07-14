package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseVineBlock;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return new ItemStack(NetherBlocks.EYE_SEED);
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(NetherBlocks.EYE_SEED)
                                                       .when(ExplosionCondition.survivesExplosion())));
    }
}
