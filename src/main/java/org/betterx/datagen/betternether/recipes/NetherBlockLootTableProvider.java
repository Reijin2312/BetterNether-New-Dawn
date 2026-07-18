package org.betterx.datagen.betternether.recipes;

import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.WoverLootTableProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public class NetherBlockLootTableProvider extends WoverLootTableProvider {
    public NetherBlockLootTableProvider(ModCore modCore) {
        super(modCore, "BetterNether Block Loot", LootContextParamSets.BLOCK);
    }

    @Override
    protected void boostrap(
            @NotNull HolderLookup.Provider lookup,
            @NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer
    ) {
        // Use BlockRegistry.bootstrapBlockLoot() which automatically handles
        // all blocks implementing BlockLootProvider (including BaseOreBlock)
        NetherBlocks.getBlockRegistry().bootstrapBlockLoot(lookup, biConsumer);
    }
}
