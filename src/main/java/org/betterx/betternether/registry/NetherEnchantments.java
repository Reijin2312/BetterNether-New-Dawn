package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;
import org.betterx.wover.enchantment.api.EnchantmentKey;
import org.betterx.wover.enchantment.api.EnchantmentManager;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import net.neoforged.neoforge.registries.RegisterEvent;

import org.jetbrains.annotations.ApiStatus;

public class NetherEnchantments {
    public static EnchantmentKey OBSIDIAN_BREAKER = EnchantmentManager.createKey(BetterNether.C.id("obsidian_breaker"));
    ;
    public static EnchantmentKey RUBY_FIRE = EnchantmentManager.createKey(BetterNether.C.id("ruby_fire"));

    public static final ResourceKey<Attribute> OBSIDIAN_BLOCK_BREAK_SPEED_KEY = ResourceKey.create(
            Registries.ATTRIBUTE,
            BetterNether.C.mk("player.obsidian_block_break_speed")
    );
    public static final Attribute OBSIDIAN_BLOCK_BREAK_SPEED_ATTRIBUTE = new RangedAttribute(
            "attribute.name.player.bn_obsidian_block_break_speed",
            1.0,
            1.0f,
            100.0f
    ).setSyncable(true);
    public static final Holder<Attribute> OBSIDIAN_BLOCK_BREAK_SPEED = createAttributeHolder(
            OBSIDIAN_BLOCK_BREAK_SPEED_ATTRIBUTE
    );

    private static Holder<Attribute> createAttributeHolder(Attribute attribute) {
        try {
            return net.minecraft.core.registries.BuiltInRegistries.ATTRIBUTE.createIntrusiveHolder(attribute);
        } catch (RuntimeException ex) {
            return Holder.direct(attribute);
        }
    }

    public static void register(RegisterEvent event) {
        event.register(Registries.ATTRIBUTE, helper -> {
            helper.register(
                    OBSIDIAN_BLOCK_BREAK_SPEED_KEY.identifier(),
                    OBSIDIAN_BLOCK_BREAK_SPEED_ATTRIBUTE
            );
        });
    }

    @ApiStatus.Internal
    public static void ensureStaticallyLoaded() {
        //NO-OP
    }
}
