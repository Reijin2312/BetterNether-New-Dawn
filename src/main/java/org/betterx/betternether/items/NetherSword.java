package org.betterx.betternether.items;

import org.betterx.bclib.items.tool.BaseSwordItem;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.wover.common.item.api.ItemWithCustomStack;
import org.betterx.wover.enchantment.api.EnchantmentUtils;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class NetherSword extends BaseSwordItem implements ItemWithCustomStack {
    private final ToolMaterial toolMaterial;

    public NetherSword(ToolMaterial material, Item.Properties settings) {
        super(material, settings);
        this.toolMaterial = material;
    }

    @Override
    public void setupItemStack(ItemStack stack, HolderLookup.Provider provider) {
        if (toolMaterial == BNToolMaterial.FLAMING_RUBY.toolMaterial()) {
            EnchantmentUtils.enchantInWorld(stack, NetherEnchantments.RUBY_FIRE.key(), 1, provider);
        }
    }
}
