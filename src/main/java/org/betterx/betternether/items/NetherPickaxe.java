package org.betterx.betternether.items;

import org.betterx.bclib.items.tool.BasePickaxeItem;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.betternether.registry.NetherTags;
import org.betterx.wover.common.item.api.ItemWithCustomStack;
import org.betterx.wover.enchantment.api.EnchantmentUtils;
import org.betterx.wover.item.api.ItemTagProvider;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantments;

public class NetherPickaxe extends BasePickaxeItem implements ItemWithCustomStack, ItemTagProvider {
    private final ToolMaterial toolMaterial;

    public NetherPickaxe(ToolMaterial material, Item.Properties settings) {
        super(material, settings);
        this.toolMaterial = material;
    }

    @Override
    public void setupItemStack(ItemStack stack, HolderLookup.Provider provider) {
        provider.lookup(Registries.ENCHANTMENT).ifPresent(lookup -> {
            int obsidianLevel = 0;
            if (toolMaterial == BNToolMaterial.CINCINNASITE_DIAMOND.toolMaterial()) obsidianLevel = 2;
            else if (toolMaterial == BNToolMaterial.NETHER_RUBY.toolMaterial()) {
                obsidianLevel = 1;
            } else if (toolMaterial == BNToolMaterial.FLAMING_RUBY.toolMaterial()) {
                obsidianLevel = 3;
                EnchantmentUtils.enchantInWorld(stack, NetherEnchantments.RUBY_FIRE.key(), 1, lookup);
                EnchantmentUtils.enchantInWorld(stack, Enchantments.MENDING, 1, lookup);
            }

            if (obsidianLevel > 0) {
                EnchantmentUtils.enchantInWorld(stack, NetherEnchantments.OBSIDIAN_BREAKER.key(), obsidianLevel, lookup);
            }
        });

    }

    @Override
    public void registerItemTags(Identifier location, ItemTagBootstrapContext context) {
        context.add(NetherTags.NETHER_PICKAXES, this);
    }
}
