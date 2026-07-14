package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.interfaces.BehaviourPlant;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootTable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockHookMushroom extends BaseBlockMold implements SurvivesOnNetherrack, BehaviourPlant, BlockLootProvider {
    public BlockHookMushroom() {
        super(Materials.makeNetherGrass(MapColor.COLOR_PINK)
                       .lightLevel(s -> 13)
                       .sound(SoundType.CROP)
        );
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnBottom(world, pos);
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull ResourceLocation location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return provider.drop(this);
    }
}
