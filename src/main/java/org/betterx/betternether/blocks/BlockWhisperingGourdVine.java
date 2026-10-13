package org.betterx.betternether.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseVineBlock;
import org.betterx.bclib.items.tool.BaseShearsItem;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.wover.block.api.BlockProperties;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockWhisperingGourdVine extends BaseVineBlock.Growing implements BlockLootProvider {
    public BlockWhisperingGourdVine() {
        super(
                BehaviourBuilders
                        .createStaticVine(MapColor.COLOR_RED)
                        .randomTicks(),
                6,
                1,
                16
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
        if (!canSurvive(state, level, pos)) return Blocks.AIR.defaultBlockState();
        if (level.getBlockState(pos.below()).getBlock() != this) return makeBottomState(state);
        if (state.getValue(SHAPE) == BlockProperties.TripleShape.BOTTOM) return makeTopState(state);
        return state;
    }

    @Override
    public InteractionResult useItemOn(
            ItemStack itemStack,
            BlockState state,
            Level world,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (BaseShearsItem.isShear(itemStack) && state.getValue(SHAPE) == BlockProperties.TripleShape.MIDDLE) {
            if (!world.isClientSide()) {
                BlocksHelper.setWithUpdate(world, pos, makeTopState(state));
                popResource(world, pos, new ItemStack(NetherBlocks.WHISPERING_GOURD, 1 + world.getRandom().nextInt(2)));
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                itemStack.hurtAndBreak(1, player, hand);
                world.gameEvent(player, GameEvent.SHEAR, pos);
            }
            world.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            return world.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        } else {
            return super.useItemOn(itemStack, state, world, pos, player, hand, hit);
        }
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull Identifier location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        var fruityState = MatchBlock.blockMatches(
                provider.getProvider().lookupOrThrow(Registries.BLOCK),
                this,
                net.minecraft.advancements.predicates.StatePropertiesPredicate.Builder.properties()
                        .hasProperty(SHAPE, BlockProperties.TripleShape.TOP))
                .invert();


        return LootTable.lootTable().withPool(LootPool
                .lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .add(LootItem.lootTableItem(NetherBlocks.WHISPERING_GOURD.asItem())
                             .when(fruityState.and(provider.shearsOrSilkTouchCondition()))
                             .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                             .otherwise(LootItem.lootTableItem(this.asItem())
                                                .when(ExplosionCondition.survivesExplosion())
                                                .when(BonusLevelTableCondition.bonusLevelFlatChance(provider.fortune(), LootLookupProvider.VANILLA_LEAVES_SAPLING_CHANCES))
                             )
                )
        );
    }
}
