package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseSimpleVineBlock;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootTable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockGoldenVine extends BaseSimpleVineBlock implements BlockLootProvider {
    public BlockGoldenVine() {
        super(
                BehaviourBuilders
                        .createStaticVine(MapColor.COLOR_YELLOW)
                        .lightLevel((bs) -> 15)
                        .instabreak(),
                29,
                0
        );
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return provider.dropWithSilkTouchOrShears(this);
    }
}
