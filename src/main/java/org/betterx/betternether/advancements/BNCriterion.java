package org.betterx.betternether.advancements;

import org.betterx.betternether.BetterNether;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class BNCriterion {
    public interface TriggerWithID<T extends CriterionTriggerInstance> extends CriterionTrigger<T> {
        Identifier getId();
    }

    public static final Identifier BREW_BLUE_ID = BetterNether.C.id("brew_blue");
    public static final Identifier USED_FORGE_ID = BetterNether.C.id("used_forge");

    public static PlayerTrigger BREW_BLUE;
    public static PlayerTrigger USED_FORGE;
    public static ConvertByLightningTrigger CONVERT_BY_LIGHTNING;

    public static Criterion<PlayerTrigger.TriggerInstance> BREW_BLUE_CRITERION;
    public static Criterion<PlayerTrigger.TriggerInstance> USED_FORGE_ANY_CRITERION;
    private static boolean initialized = false;

    public static void onRegister(net.neoforged.neoforge.registries.RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.TRIGGER_TYPE)) return;
        event.register(Registries.TRIGGER_TYPE, BNCriterion::register);
    }

    public static void register(
            net.neoforged.neoforge.registries.RegisterEvent.RegisterHelper<CriterionTrigger<?>> helper
    ) {
        if (initialized) return;
        initialized = true;
        BREW_BLUE = new PlayerTrigger();
        helper.register(BREW_BLUE_ID, BREW_BLUE);
        USED_FORGE = new PlayerTrigger();
        helper.register(USED_FORGE_ID, USED_FORGE);
        CONVERT_BY_LIGHTNING = new ConvertByLightningTrigger();
        helper.register(CONVERT_BY_LIGHTNING.getId(), CONVERT_BY_LIGHTNING);

        BREW_BLUE_CRITERION = BNCriterion.BREW_BLUE.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        USED_FORGE_ANY_CRITERION = BNCriterion.USED_FORGE.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
    }
}
