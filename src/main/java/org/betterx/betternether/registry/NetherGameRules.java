package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;

import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;

public class NetherGameRules {
    /**
     * Whether gloomwisps shed experience when something pushes through them.
     * <p>
     * Filed under DROPS beside {@code block_drops} and {@code entity_drops}, which is where a server
     * owner turning off incidental sources of experience would look for it. Defaults to on: the drip
     * is the point of the plant, and a rule that ships disabled is one nobody discovers.
     */
    public static final GameRule<Boolean> GLOOMWISP_DROPS_EXPERIENCE = GameRuleBuilder
            .forBoolean(true)
            .category(GameRuleCategory.DROPS)
            .buildAndRegister(BetterNether.C.id("gloomwisp_drops_experience"));

    public static void ensureStaticallyLoaded() {
    }
}
