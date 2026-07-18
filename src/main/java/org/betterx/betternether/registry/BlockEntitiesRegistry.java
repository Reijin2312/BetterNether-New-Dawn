package org.betterx.betternether.registry;

import org.betterx.bclib.blocks.BaseBarrelBlock;
import org.betterx.bclib.blocks.BaseChestBlock;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blockentities.BNBrewingStandBlockEntity;
import org.betterx.betternether.blockentities.BlockEntityChestOfDrawers;
import org.betterx.betternether.blockentities.BlockEntityForge;
import org.betterx.betternether.blockentities.BlockEntityFurnace;
import org.betterx.betternether.blocks.BlockNetherFurnace;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockEntitiesRegistry {
    public static final BlockEntityType<BlockEntityForge> CINCINNASITE_FORGE = new BlockEntityType<>(
            BlockEntityForge::new,
            Set.of(NetherBlocks.CINCINNASITE_FORGE)
    );
    public static final BlockEntityType<BlockEntityFurnace> NETHERRACK_FURNACE = new BlockEntityType<>(
            BlockEntityFurnace::new,
            Set.of(getFurnaces())
    );
    public static final BlockEntityType<BlockEntityChestOfDrawers> CHEST_OF_DRAWERS = new BlockEntityType<>(
            BlockEntityChestOfDrawers::new,
            Set.of(NetherBlocks.CHEST_OF_DRAWERS)
    );
    public static final BlockEntityType<BNBrewingStandBlockEntity> NETHER_BREWING_STAND = new BlockEntityType<>(
            BNBrewingStandBlockEntity::new,
            Set.of(NetherBlocks.NETHER_BREWING_STAND)
    );

    public static void register() {
        RegisterBlockEntity("forge", CINCINNASITE_FORGE);
        RegisterBlockEntity("furnace", NETHERRACK_FURNACE);
        RegisterBlockEntity("chest_of_drawers", CHEST_OF_DRAWERS);
        RegisterBlockEntity("nether_brewing_stand", NETHER_BREWING_STAND);
    }

    public static void RegisterBlockEntity(String name, BlockEntityType<? extends BlockEntity> type) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, BetterNether.C.mk(name), type);
    }

    private static Block[] getChests() {
        List<Block> result = new ArrayList<Block>();
        NetherBlocks.getModBlocks().forEach((block) -> {
            if (block instanceof BaseChestBlock)
                result.add(block);
        });
        return result.toArray(new Block[]{});
    }

    private static Block[] getBarrels() {
        List<Block> result = new ArrayList<Block>();
        NetherBlocks.getModBlocks().forEach((block) -> {
            if (block instanceof BaseBarrelBlock)
                result.add(block);
        });
        return result.toArray(new Block[]{});
    }

    private static Block[] getFurnaces() {
        List<Block> result = new ArrayList<Block>();
        NetherBlocks.getModBlocks().forEach((block) -> {
            if (block instanceof BlockNetherFurnace)
                result.add(block);
        });
        return result.toArray(new Block[]{});
    }
}
