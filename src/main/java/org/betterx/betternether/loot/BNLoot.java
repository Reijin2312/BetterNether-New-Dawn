package org.betterx.betternether.loot;

import org.betterx.betternether.BetterNether;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

/** Loot-table keys used by BetterNether structures and entities. */
public final class BNLoot {
    public static final ResourceKey<LootTable> CITY_LOOT = key("chests/city");
    public static final ResourceKey<LootTable> CITY_LOOT_COMMON = key("chests/city_common");
    public static final ResourceKey<LootTable> CITY_LOOT_SURPRISE = key("chests/city_surprise");
    public static final ResourceKey<LootTable> LIBRARY_LOOT = key("chests/library");
    public static final ResourceKey<LootTable> WITHER_TOWER_LOOT = key("chests/wither_tower");
    public static final ResourceKey<LootTable> WITHER_TOWER_BONUS_LOOT = key("chests/wither_tower_bonus");
    public static final ResourceKey<LootTable> GHAST_HIVE = key("chests/ghast_hive");

    public static final ResourceKey<LootTable> FIREFLY = key("entities/firefly");
    public static final ResourceKey<LootTable> FLYING_PIG = key("entities/flying_pig");
    public static final ResourceKey<LootTable> JUNGLE_SKELETON = key("entities/jungle_skeleton");
    public static final ResourceKey<LootTable> NAGA = key("entities/naga");
    public static final ResourceKey<LootTable> SKULL = key("entities/skull");

    private BNLoot() {
    }

    private static ResourceKey<LootTable> key(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id(path));
    }

    /** Loot additions are data-driven in 26.3. */
    public static void register() {
    }
}
