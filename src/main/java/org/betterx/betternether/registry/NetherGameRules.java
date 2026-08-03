package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

import net.neoforged.neoforge.registries.RegisterEvent;

public class NetherGameRules {
    /**
     * Whether gloomwisps shed experience when something pushes through them.
     * <p>
     * Filed under DROPS beside {@code block_drops} and {@code entity_drops}, which is where a server
     * owner turning off incidental sources of experience would look for it. Defaults to on: the drip
     * is the point of the plant, and a rule that ships disabled is one nobody discovers.
     */
    public static final GameRule<Boolean> GLOOMWISP_DROPS_EXPERIENCE = new GameRule<>(
            GameRuleCategory.DROPS,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            value -> value ? 1 : 0,
            true,
            FeatureFlagSet.of()
    );

    public static void register(RegisterEvent event) {
        event.register(
                Registries.GAME_RULE,
                helper -> helper.register(
                        BetterNether.C.mk("gloomwisp_drops_experience"),
                        GLOOMWISP_DROPS_EXPERIENCE
                )
        );
    }

    public static void ensureStaticallyLoaded() {
        // Registration is performed by NeoForge's RegisterEvent before the registry is frozen.
    }
}
