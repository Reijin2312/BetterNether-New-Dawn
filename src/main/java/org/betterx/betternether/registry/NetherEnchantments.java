package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;
import org.betterx.wover.enchantment.api.EnchantmentKey;
import org.betterx.wover.enchantment.api.EnchantmentManager;

import org.jetbrains.annotations.ApiStatus;

public class NetherEnchantments {
    public static EnchantmentKey OBSIDIAN_BREAKER = EnchantmentManager.createKey(BetterNether.C.id("obsidian_breaker"));
    ;
    public static EnchantmentKey RUBY_FIRE = EnchantmentManager.createKey(BetterNether.C.id("ruby_fire"));

    @ApiStatus.Internal
    public static void ensureStaticallyLoaded() {
        //NO-OP
    }
}
