package org.betterx.betternether.items;

import org.betterx.bclib.items.BaseArmorItem;
import org.betterx.betternether.items.materials.BNArmorTiers;
import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.wover.common.item.api.ItemWithCustomStack;
import org.betterx.wover.enchantment.api.EnchantmentUtils;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import org.jetbrains.annotations.NotNull;

public class NetherArmor extends BaseArmorItem implements ItemWithCustomStack {
    private final ArmorMaterial armorMaterial;

    public NetherArmor(ArmorMaterial material, ArmorType type, Item.Properties settings) {
        super(material, type, settings);
        this.armorMaterial = material;
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    @Override
    public void setupItemStack(ItemStack stack, HolderLookup.Provider provider) {
        if (BNArmorTiers.FLAMING_RUBY.is(armorMaterial)) {
            EnchantmentUtils.enchantInWorld(stack, NetherEnchantments.RUBY_FIRE.key(), 1, provider);
        }
    }
}
