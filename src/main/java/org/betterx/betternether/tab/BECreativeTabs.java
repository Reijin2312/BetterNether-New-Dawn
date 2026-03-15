package org.betterx.betternether.tab;

import org.betterx.bclib.behaviours.interfaces.BehaviourPlantLike;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.complex.WillowMaterial;
import org.betterx.betternether.blocks.complex.slots.NetherSlots;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;
import org.betterx.wover.complex.api.equipment.ToolSlot;
import org.betterx.wover.tabs.api.CreativeTabs;
import org.betterx.wover.tabs.api.interfaces.CreativeTabPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.RegisterEvent;

public class BECreativeTabs {
    private static boolean registered;

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) return;
        if (registered) return;
        registered = true;

        final ItemLike natureIcon = fallbackIcon(NetherItems.BLACK_APPLE, Items.NETHER_WART);
        final ItemLike blocksIcon = fallbackIcon(NetherBlocks.JUNGLE_GRASS, Blocks.NETHERRACK);
        final ItemLike itemsIcon = resolveItemsTabIcon();

        CreativeTabs
                .start(BetterNether.C)
                .createTab("nature")
                .setPredicate(item -> BehaviourPlantLike.TAB_PREDICATE.contains(item)
                        || item == NetherItems.AGAVE_LEAF
                        || item == NetherItems.BLACK_APPLE
                        || (NetherBlocks.MAGMA_FLOWER != null && item == NetherBlocks.MAGMA_FLOWER.asItem())
                        || (NetherBlocks.MAT_RUBEUS != null
                            && item == NetherBlocks.MAT_RUBEUS.getBlockItem(NetherSlots.CONE))
                        || (NetherBlocks.MAT_WILLOW != null
                            && item == NetherBlocks.MAT_WILLOW.getBlockItem(WillowMaterial.BLOCK_TORCH)))
                .setIcon(natureIcon)
                .buildAndAdd()
                .createBlockOnlyTab(blocksIcon)
                .buildAndAdd()
                .createItemOnlyTab(itemsIcon)
                .setPredicate(item -> CreativeTabPredicate.ITEMS.contains(item) && !isDebugItem(item))
                .buildAndAdd()
                .processRegistries()
                .registerAllTabs();
    }

    private static ItemLike resolveItemsTabIcon() {
        if (NetherItems.FLAMING_RUBY_SET != null) {
            final Item icon = NetherItems.FLAMING_RUBY_SET.get(ToolSlot.PICKAXE_SLOT);
            if (icon != null) return icon;
        }

        if (NetherItems.CINCINNASITE_INGOT != null) return NetherItems.CINCINNASITE_INGOT;
        return Items.GOLD_INGOT;
    }

    private static ItemLike fallbackIcon(ItemLike icon, ItemLike fallback) {
        return icon != null ? icon : fallback;
    }

    private static boolean isDebugItem(Item item) {
        var key = BuiltInRegistries.ITEM.getKey(item);
        return key != null
                && BetterNether.MOD_ID.equals(key.getNamespace())
                && key.getPath().startsWith("debug/");
    }
}
