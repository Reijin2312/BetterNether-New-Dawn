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

    public static final GameRules.Key<GameRules.BooleanValue> GENERATE_BLUE_RUINED_PORTALS = GameRuleRegistry.register(
            "betternether:generate_blue_ruined_portals", GameRules.Category.MISC,
            GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> GROW_LARGE_WILLOWS = GameRuleRegistry.register(
            "betternether:grow_large_willows", GameRules.Category.UPDATES,
            GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> GROW_LARGE_ANCHOR_TREES = GameRuleRegistry.register(
            "betternether:grow_large_anchor_trees", GameRules.Category.UPDATES,
            GameRuleFactory.createBooleanRule(false));

    public static void ensureStaticallyLoaded() {
    }
}
