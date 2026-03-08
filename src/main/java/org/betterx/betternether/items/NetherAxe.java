package org.betterx.betternether.items;

import org.betterx.bclib.items.tool.BaseAxeItem;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.wover.common.item.api.ItemWithCustomStack;
import org.betterx.wover.enchantment.api.EnchantmentUtils;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.state.BlockState;

public class NetherAxe extends BaseAxeItem implements ItemWithCustomStack {
    private final ToolMaterial toolMaterial;

    public NetherAxe(ToolMaterial material, Item.Properties settings) {
        super(material, settings);
        this.toolMaterial = material;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state);
    }


    @Override
    public void setupItemStack(ItemStack stack, HolderLookup.Provider provider) {
        if (toolMaterial == BNToolMaterial.FLAMING_RUBY.toolMaterial()) {
            EnchantmentUtils.enchantInWorld(stack, NetherEnchantments.RUBY_FIRE.key(), 1, provider);
        }
    }
}
