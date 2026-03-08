package org.betterx.betternether.loot;

import org.betterx.bclib.interfaces.LootPoolAccessor;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherTemplates;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class BNLoot {
    public static final ResourceKey<LootTable> CITY_LOOT = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/city"));
    public static final ResourceKey<LootTable> CITY_LOOT_COMMON = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/city_common"));
    public static final ResourceKey<LootTable> CITY_LOOT_SURPRISE = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/city_surprise"));
    public static final ResourceKey<LootTable> LIBRARY_LOOT = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/library"));
    public static final ResourceKey<LootTable> WITHER_TOWER_LOOT = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/wither_tower"));
    public static final ResourceKey<LootTable> WITHER_TOWER_BONUS_LOOT = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/wither_tower_bonus"));
    public static final ResourceKey<LootTable> GHAST_HIVE = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("chests/ghast_hive"));

    public static final ResourceKey<LootTable> FIREFLY = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("entities/firefly"));
    public static final ResourceKey<LootTable> FLYING_PIG = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("entities/flying_pig"));
    public static final ResourceKey<LootTable> JUNGLE_SKELETON = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("entities/jungle_skeleton"));
    public static final ResourceKey<LootTable> NAGA = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("entities/naga"));
    public static final ResourceKey<LootTable> SKULL = ResourceKey.create(Registries.LOOT_TABLE, BetterNether.C.id("entities/skull"));

    public static void register() {
        NeoForge.EVENT_BUS.addListener(BNLoot::onLootTableLoad);
    }

    private static void onLootTableLoad(LootTableLoadEvent event) {
        final Identifier id = event.getName();
        final LootTable table = event.getTable();

        if (BuiltInLootTables.RUINED_PORTAL.equals(id) || BuiltInLootTables.NETHER_BRIDGE.equals(id)) {
            table.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 4))
                                  .add(LootItem.lootTableItem(NetherBlocks.BLUE_OBSIDIAN.asItem())
                                               .setWeight(1)
                                               .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                       1.0F,
                                                       2.0F
                                               ))))
                                  .add(EmptyLootItem.emptyItem()
                                                    .setWeight(9)).build());
            table.addPool(LootPool.lootPool()
                                  .setRolls(ConstantValue.exactly(1.0f))
                                  .add(EmptyLootItem.emptyItem().setWeight(9))
                                  .add(LootItem.lootTableItem(NetherTemplates.NETHER_BOWL_SMITHING_TEMPLATE)
                                               .setWeight(1))
                                  .build());
        } else if (BuiltInLootTables.BASTION_BRIDGE.equals(id)
                || BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(id)
                || BuiltInLootTables.BASTION_TREASURE.equals(id)) {
            table.addPool(LootPool.lootPool()
                                  .setRolls(UniformGenerator.between(1, 2))
                                  .add(LootItem.lootTableItem(NetherBlocks.BLUE_CRYING_OBSIDIAN.asItem())
                                               .setWeight(5)
                                               .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                       3.0F,
                                                       8.0F
                                               ))))
                                  .add(LootItem
                                          .lootTableItem(NetherBlocks.BLUE_WEEPING_OBSIDIAN.asItem())
                                          .setWeight(1)
                                          .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                  1.0F,
                                                  4.0F
                                          ))))
                                  .add(LootItem.lootTableItem(NetherBlocks.WEEPING_OBSIDIAN.asItem())
                                               .setWeight(1)
                                               .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                       1.0F,
                                                       4.0F
                                               ))))
                                  .add(EmptyLootItem.emptyItem()
                                                    .setWeight(50))
                                  .build());
            table.addPool(LootPool.lootPool()
                                  .setRolls(ConstantValue.exactly(1.0f))
                                  .add(EmptyLootItem.emptyItem().setWeight(99))
                                  .add(LootItem.lootTableItem(NetherTemplates.NETHER_BOWL_SMITHING_TEMPLATE)
                                               .setWeight(1))
                                  .build());
        } else if (BuiltInLootTables.BASTION_OTHER.equals(id)) {
            table.addPool(LootPool.lootPool()
                                  .setRolls(UniformGenerator.between(1, 2))
                                  .add(LootItem.lootTableItem(NetherBlocks.BLUE_OBSIDIAN.asItem())
                                               .setWeight(10)
                                               .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                       4.0F,
                                                       6.0F
                                               ))))
                                  .add(LootItem.lootTableItem(NetherBlocks.BLUE_CRYING_OBSIDIAN.asItem())
                                               .setWeight(5)
                                               .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                       1.0F,
                                                       5.0F
                                               ))))
                                  .add(LootItem
                                          .lootTableItem(NetherBlocks.BLUE_WEEPING_OBSIDIAN.asItem())
                                          .setWeight(1)
                                          .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                  1.0F,
                                                  2.0F
                                          ))))
                                  .add(LootItem.lootTableItem(NetherBlocks.WEEPING_OBSIDIAN.asItem())
                                               .setWeight(1)
                                               .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                                       1.0F,
                                                       2.0F
                                               ))))
                                  .add(EmptyLootItem.emptyItem()
                                                    .setWeight(50))
                                  .build());
            table.addPool(LootPool.lootPool()
                                  .setRolls(ConstantValue.exactly(1.0f))
                                  .add(EmptyLootItem.emptyItem().setWeight(9))
                                  .add(LootItem.lootTableItem(NetherTemplates.NETHER_BOWL_SMITHING_TEMPLATE)
                                               .setWeight(1))
                                  .build());
        } else if (BuiltInLootTables.PIGLIN_BARTERING.equals(id)) {
            ArrayList<LootPoolEntryContainer> list = Lists.newArrayList();
            list.add(LootItem
                    .lootTableItem(NetherBlocks.BLUE_OBSIDIAN.asItem())
                    .setWeight(40).build());
            list.add(LootItem
                    .lootTableItem(NetherBlocks.BLUE_CRYING_OBSIDIAN.asItem())
                    .setWeight(40)
                    .apply(SetItemCountFunction.setCount(
                            UniformGenerator.between(
                                    1.0F,
                                    3.0F
                            ))).build());
            list.add(LootItem
                    .lootTableItem(NetherBlocks.BLUE_WEEPING_OBSIDIAN.asItem())
                    .setWeight(20)
                    .apply(SetItemCountFunction.setCount(
                            UniformGenerator.between(
                                    1.0F,
                                    2.0F
                            ))).build());
            list.add(LootItem
                    .lootTableItem(NetherBlocks.WEEPING_OBSIDIAN.asItem())
                    .setWeight(20)
                    .apply(SetItemCountFunction.setCount(
                            UniformGenerator.between(
                                    1.0F,
                                    2.0F
                            ))).build());

            if (!addToPool(table, 0, list)) {
                BetterNether.C.log.error("Unable to build bartering table");
            }
        }
    }

    private static boolean addToPool(
            LootTable table,
            int index,
            ArrayList<LootPoolEntryContainer> newEntries
    ) {
        try {
            for (Field f : table.getClass().getDeclaredFields()) {
                if (List.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    List<?> list = (List<?>) f.get(table);
                    if (list != null && !list.isEmpty() && list.get(0) instanceof LootPool) {
                        @SuppressWarnings("unchecked")
                        List<LootPool> pools = (List<LootPool>) list;
                        if (pools.size() > index) {
                            LootPool pool = pools.get(index);
                            LootPoolAccessor acc = (LootPoolAccessor) pool;
                            pools.set(index, acc.bcl_mergeEntries(newEntries));
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            BetterNether.C.log.error("Unable to build bartering table", e);
        }
        return false;
    }
}
