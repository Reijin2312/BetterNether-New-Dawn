package org.betterx.betternether.registry;

import org.betterx.bclib.BCLib;
import org.betterx.bclib.items.BaseSpawnEggItem;
import org.betterx.bclib.items.DebugDataItem;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNBlockProperties.FoodShape;
import org.betterx.betternether.integrations.VanillaExcavatorsIntegration;
import org.betterx.betternether.integrations.VanillaHammersIntegration;
import org.betterx.betternether.items.ItemBlackApple;
import org.betterx.betternether.items.ItemBowlFood;
import org.betterx.betternether.items.complex.DiamondSet;
import org.betterx.betternether.items.complex.NetherSet;
import org.betterx.betternether.items.materials.BNArmorTiers;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.items.materials.BNToolTiers;
import org.betterx.betternether.loot.BNLoot;
import org.betterx.wover.item.api.ItemRegistry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class NetherItems {
    public static Item BLACK_APPLE;

    public static Item STALAGNATE_BOWL;
    public static Item STALAGNATE_BOWL_WART;
    public static Item STALAGNATE_BOWL_MUSHROOM;
    public static Item STALAGNATE_BOWL_APPLE;
    public static Item HOOK_MUSHROOM_COOKED;

    public static Item CINCINNASITE;
    public static Item CINCINNASITE_INGOT;
    public static Item NETHER_RUBY;

    public static NetherSet CINCINNASITE_SET;

    public static NetherSet NETHER_RUBY_SET;

    public static DiamondSet CINCINNASITE_DIAMOND_SET;

    public static NetherSet FLAMING_RUBY_SET;
    public static Item CINCINNASITE_HAMMER;
    public static Item CINCINNASITE_HAMMER_DIAMOND;
    public static Item NETHER_RUBY_HAMMER;

    public static Item CINCINNASITE_EXCAVATOR;
    public static Item CINCINNASITE_EXCAVATOR_DIAMOND;
    public static Item NETHER_RUBY_EXCAVATOR;

    public static Item GLOWSTONE_PILE;
    public static Item LAPIS_PILE;

    public static Item AGAVE_LEAF;
    public static Item AGAVE_MEDICINE;
    public static Item HERBAL_MEDICINE;

    private NetherItems() {
    }

    private static ItemRegistry ITEMS_REGISTRY;
    private static boolean itemsRegistered;

    @NotNull
    public static ItemRegistry getItemRegistry() {
        if (ITEMS_REGISTRY == null) {
            ITEMS_REGISTRY = ItemRegistry.forMod(BetterNether.C);
        }
        return ITEMS_REGISTRY;
    }

    public static Stream<Item> getModItems() {
        return getItemRegistry().allItems();
    }

    private static <T extends Item> T createItemWithId(String name, Supplier<T> factory) {
        return ItemRegistry.withConstructionId(BetterNether.C.mk(name), factory);
    }

    public static Item registerShears(String name, Supplier<? extends Item> factory) {
        Item item = createItemWithId(name, factory::get);
        if (item != Items.AIR) {
            return getItemRegistry().registerAsTool(name, item);
        }

        return item;
    }

    public static Item registerTool(String name, Supplier<? extends Item> factory, TagKey<Item>... tags) {
        Item item = createItemWithId(name, factory::get);
        if (item != Items.AIR) {
            getItemRegistry().registerAsTool(name, item, tags);
        }

        return item;
    }

    public static Item registerItem(String name, Supplier<? extends Item> factory, TagKey<Item>... tags) {
        Item item = createItemWithId(name, factory::get);
        if (item != Items.AIR) {
            getItemRegistry().register(name, item, tags);
        }
        return item;
    }

    public static Item registerFood(String name, int hunger, float saturationMultiplier) {
        return registerItem(
                name,
                () -> new Item(defaultSettings().food(new FoodProperties.Builder().nutrition(hunger)
                                                                              .saturationModifier(
                                                                                      saturationMultiplier)
                                                                              .build()))
        );
    }

    public static Item registerMedicine(String name, int ticks, int power, boolean bowl) {
        if (bowl) {
            return registerItem(name, () -> new Item(defaultSettings().stacksTo(16)
                                                                      .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0).build())) {
                @Override
                public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
                    if (stack.getCount() == 1) {
                        super.finishUsingItem(stack, world, user);
                        return new ItemStack(NetherItems.STALAGNATE_BOWL, stack.getCount());
                    } else {
                        if (user instanceof Player player) {
                            if (!player.isCreative())
                                player.addItem(new ItemStack(NetherItems.STALAGNATE_BOWL));
                        }
                    }
                    return super.finishUsingItem(stack, world, user);
                }
            });
        }
        return registerItem(
                name,
                () -> new Item(defaultSettings().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0).build()))
        );
    }

    public static Properties defaultSettings() {
        return new Item.Properties();
    }

    public static Properties createDefaultNetherArmorSettings(ArmorType type, int durability) {
        return NetherItems.defaultSettings().fireResistant().durability(type.getDurability(durability));
    }

    public static Item.Properties createDefaultNetherToolSettings(
            ToolMaterial material,
            float attackDamage,
            float attackSpeed
    ) {
        return NetherItems.defaultSettings().fireResistant();
    }

    public static Item.Properties createDefaultNetherSwordSettings(
            ToolMaterial material,
            float attackDamage,
            float attackSpeed
    ) {
        return NetherItems.defaultSettings().fireResistant();
    }

    public static Item makeEgg(String name, EntityType<? extends Mob> type, int background, int dots) {
        SpawnEggItem egg = createItemWithId(name, () -> new BaseSpawnEggItem(type, background, dots, defaultSettings()));
        return getItemRegistry().registerEgg(name, egg);
    }

    public static Item registerNetherItem(String name, Supplier<? extends Item> factory) {
        return registerItem(name, factory);
    }

    static {
        // Ensure registry holder is created before RegisterEvent dispatch
        getItemRegistry();
    }

    @NotNull
    private static CompoundTag buildCitySpawnerData() {
        CompoundTag entity = new CompoundTag();
        entity.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypes.WITHER_SKELETON).toString());

        CompoundTag equipment = new CompoundTag();
        equipment.putString("loot_table", BetterNether.C.id("equipment/city_wither_skeleton").toString());
        equipment.putFloat("slot_drop_chances", 0.0F);

        CompoundTag spawnData = new CompoundTag();
        spawnData.put("entity", entity);
        spawnData.put("equipment", equipment);

        CompoundTag root = new CompoundTag();
        root.putShort("SpawnRange", (short) 4);
        root.putShort("SpawnCount", (short) 8);
        root.putShort("MaxNearbyEntities", (short) 18);
        root.putShort("Delay", (short) 499);
        root.putShort("MinSpawnDelay", (short) 300);
        root.putShort("MaxSpawnDelay", (short) 1600);
        root.putShort("RequiredPlayerRange", (short) 20);
        root.put("SpawnData", spawnData);


        return root;
    }


    @ApiStatus.Internal
    public static void register() {
        getItemRegistry();
        NetherTemplates.ensureStaticallyLoaded();
        registerItems();
    }

    private static void registerItems() {
        if (itemsRegistered) return;
        itemsRegistered = true;

        BLACK_APPLE = registerItem("black_apple", ItemBlackApple::new);

        STALAGNATE_BOWL = registerItem("stalagnate_bowl", () -> new ItemBowlFood(null, FoodShape.NONE));
        STALAGNATE_BOWL_WART = registerItem(
                "stalagnate_bowl_wart",
                () -> new ItemBowlFood(
                        Foods.COOKED_CHICKEN,
                        FoodShape.WART
                )
        );
        STALAGNATE_BOWL_MUSHROOM = registerItem(
                "stalagnate_bowl_mushroom",
                () -> new ItemBowlFood(
                        Foods.MUSHROOM_STEW,
                        FoodShape.MUSHROOM
                )
        );
        STALAGNATE_BOWL_APPLE = registerItem(
                "stalagnate_bowl_apple",
                () -> new ItemBowlFood(Foods.APPLE, FoodShape.APPLE)
        );
        HOOK_MUSHROOM_COOKED = registerFood("hook_mushroom_cooked", 4, 0.4F);

        CINCINNASITE = registerItem("cincinnasite", () -> new Item(defaultSettings()));
        CINCINNASITE_INGOT = registerItem("cincinnasite_ingot", () -> new Item(defaultSettings()));
        NETHER_RUBY = registerItem("nether_ruby", () -> new Item(defaultSettings()));

        CINCINNASITE_SET = new NetherSet(
                "cincinnasite",
                BNToolTiers.CINCINNASITE,
                BNArmorTiers.CINCINNASITE,
                true
        );

        NETHER_RUBY_SET = new NetherSet(
                "nether_ruby",
                BNToolTiers.NETHER_RUBY,
                BNArmorTiers.NETHER_RUBY,
                false
        );

        CINCINNASITE_DIAMOND_SET = new DiamondSet(CINCINNASITE_SET);

        FLAMING_RUBY_SET = new NetherSet(
                "flaming_ruby",
                BNToolTiers.FLAMING_RUBY,
                BNArmorTiers.FLAMING_RUBY,
                false,
                NETHER_RUBY_SET
        );
        CINCINNASITE_HAMMER = registerItem(
                "cincinnasite_hammer",
                () -> VanillaHammersIntegration.makeHammer(
                        BNToolMaterial.CINCINNASITE.toolMaterial(),
                        4,
                        -2.0F
                )
        );
        CINCINNASITE_HAMMER_DIAMOND = registerItem(
                "cincinnasite_hammer_diamond",
                () -> VanillaHammersIntegration.makeHammer(
                        BNToolMaterial.CINCINNASITE_DIAMOND.toolMaterial(),
                        5,
                        -2.0F
                )
        );
        NETHER_RUBY_HAMMER = registerItem(
                "nether_ruby_hammer",
                () -> VanillaHammersIntegration.makeHammer(
                        BNToolMaterial.NETHER_RUBY.toolMaterial(),
                        5,
                        -2.0F
                )
        );

        CINCINNASITE_EXCAVATOR = registerItem(
                "cincinnasite_excavator",
                () -> VanillaExcavatorsIntegration.makeExcavator(
                        BNToolMaterial.CINCINNASITE.toolMaterial(),
                        4,
                        -1.6F
                )
        );
        CINCINNASITE_EXCAVATOR_DIAMOND = registerItem(
                "cincinnasite_excavator_diamond",
                () -> VanillaExcavatorsIntegration.makeExcavator(
                        BNToolMaterial.CINCINNASITE_DIAMOND.toolMaterial(),
                        5,
                        -2.0F
                )
        );
        NETHER_RUBY_EXCAVATOR = registerItem(
                "nether_ruby_excavator",
                () -> VanillaExcavatorsIntegration.makeExcavator(
                        BNToolMaterial.NETHER_RUBY.toolMaterial(),
                        5,
                        -2.0F
                )
        );

        GLOWSTONE_PILE = registerItem("glowstone_pile", () -> new Item(defaultSettings()));
        LAPIS_PILE = registerItem("lapis_pile", () -> new Item(defaultSettings()));

        AGAVE_LEAF = registerItem("agave_leaf", () -> new Item(defaultSettings()));
        AGAVE_MEDICINE = registerMedicine("agave_medicine", 40, 2, true);
        HERBAL_MEDICINE = registerMedicine("herbal_medicine", 10, 5, true);

        if (BCLib.isDevEnvironment()) {
            BetterNether.C.log.warn("Generating Debug Helpers");

            registerNetherItem(
                    "debug/city_loot",
                    () -> DebugDataItem.forLootTable(BNLoot.CITY_LOOT, Items.IRON_INGOT)
            );
            registerNetherItem(
                    "debug/city_loot_common",
                    () -> DebugDataItem.forLootTable(BNLoot.CITY_LOOT_COMMON, Items.GOLD_INGOT)
            );
            registerNetherItem(
                    "debug/city_loot_surprise",
                    () -> DebugDataItem.forLootTable(BNLoot.CITY_LOOT_SURPRISE, Items.DIAMOND)
            );
            registerNetherItem(
                    "debug/wither_tower_loot",
                    () -> DebugDataItem.forLootTable(BNLoot.WITHER_TOWER_LOOT, NetherItems.CINCINNASITE_INGOT)
            );
            registerNetherItem(
                    "debug/wither_tower_bonus_loot",
                    () -> DebugDataItem.forLootTable(BNLoot.WITHER_TOWER_BONUS_LOOT, NetherItems.NETHER_RUBY)
            );

            registerNetherItem(
                    "debug/city_spawner",
                    () -> DebugDataItem.forSpawner(NetherItems::buildCitySpawnerData, Items.SPECTRAL_ARROW)
            );
        }
    }
}
