package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.interfaces.tools.AddMineableAxe;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockGiantLucis extends HugeMushroomBlock implements AddMineableAxe, BlockLootProvider {
    public BlockGiantLucis() {
        super(BehaviourBuilders
                .createWalkablePlant(MapColor.COLOR_YELLOW)
                .requiresCorrectToolForDrops()
                .lightLevel((bs) -> 15)
                .sound(SoundType.WOOD)
                .strength(1F));
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        var silkTouch = provider.hasSilkTouch();
        var notSilk = silkTouch.invert();

        return LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(this).when(silkTouch)))
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .when(notSilk)
                                          .when(ExplosionCondition.survivesExplosion())
                                          .add(LootItem.lootTableItem(NetherBlocks.LUCIS_SPORE)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))))
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .when(notSilk)
                                          .when(ExplosionCondition.survivesExplosion())
                                          .add(LootItem.lootTableItem(NetherItems.GLOWSTONE_PILE)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))));
    }
}
