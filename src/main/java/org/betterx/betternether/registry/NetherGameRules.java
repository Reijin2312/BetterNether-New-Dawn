package org.betterx.betternether.registry;


import net.minecraft.world.level.GameRules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;

public class NetherGameRules {
    /**
     * Whether gloomwisps shed experience when something pushes through them.
     * <p>
     * Filed under DROPS beside {@code block_drops} and {@code entity_drops}, which is where a server
     * owner turning off incidental sources of experience would look for it. Defaults to on: the drip
     * is the point of the plant, and a rule that ships disabled is one nobody discovers.
     */
    public static final GameRules.Key<GameRules.BooleanValue> GLOOMWISP_DROPS_EXPERIENCE =
            GameRuleRegistry.register(
                    "betternether:gloomwisp_drops_experience",
                    GameRules.Category.DROPS,
                    GameRuleFactory.createBooleanRule(true)
            );

    public static void ensureStaticallyLoaded() {
    }
}
