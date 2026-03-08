package org.betterx.betternether.blocks;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.wover.loot.api.BlockLootProvider;
import org.betterx.wover.loot.api.LootLookupProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEyeball extends BlockEyeBase implements BlockLootProvider {
    public BlockEyeball() {
        super(Materials.NETHER_PLANT
                .mapColor(MapColor.COLOR_BROWN)
                .sound(SoundType.SLIME_BLOCK)
                .strength(0.5F, 0.5F)
                .randomTicks()
        );
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble() * 0.3;
            double z = pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(64) == 0) {
            int y = BlocksHelper.downRay(world, pos, 64) + 1;
            BlockPos down = pos.below(y);
            BlockState cauldron = world.getBlockState(down);
            if (cauldron.getBlock() == Blocks.CAULDRON) {
                int level = cauldron.getValue(LayeredCauldronBlock.LEVEL);
                if (level < 3) {
                    world.setBlockAndUpdate(down, cauldron.setValue(LayeredCauldronBlock.LEVEL, level + 1));
                }
            }
        }
    }

    @Override
    public @Nullable LootTable.Builder registerBlockLoot(
            @NotNull Identifier location,
            @NotNull LootLookupProvider provider,
            @NotNull ResourceKey<LootTable> tableKey
    ) {
        return LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(net.minecraft.world.item.Items.SLIME_BALL)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))
                                                       .when(ExplosionCondition.survivesExplosion())))
                        .withPool(LootPool.lootPool()
                                          .setRolls(ConstantValue.exactly(1.0F))
                                          .add(LootItem.lootTableItem(NetherBlocks.EYE_SEED)
                                                       .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                                       .when(ExplosionCondition.survivesExplosion())));
    }
}
