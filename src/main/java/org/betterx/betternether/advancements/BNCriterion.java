package org.betterx.betternether.advancements;

import org.betterx.betternether.BetterNether;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class BNCriterion {
    public interface TriggerWithID<T extends CriterionTriggerInstance> extends CriterionTrigger<T> {
        ResourceLocation getId();
    }

    public static final ResourceLocation BREW_BLUE_ID = BetterNether.C.id("brew_blue");
    public static final ResourceLocation USED_FORGE_ID = BetterNether.C.id("used_forge");
    public static final ResourceLocation DISTURBED_WISP_ID = BetterNether.C.id("disturbed_wisp");
    public static final ResourceLocation BURNED_GLOOMSCULK_CRYSTAL_ID = BetterNether.C.id("burned_gloomsculk_crystal");
    public static final ResourceLocation WISP_SHED_EXPERIENCE_ID = BetterNether.C.id("wisp_shed_experience");

    public static PlayerTrigger BREW_BLUE;
    public static PlayerTrigger USED_FORGE;
    public static PlayerTrigger DISTURBED_WISP;
    public static PlayerTrigger BURNED_GLOOMSCULK_CRYSTAL;
    public static PlayerTrigger WISP_SHED_EXPERIENCE;
    public static ConvertByLightningTrigger CONVERT_BY_LIGHTNING;

    public static Criterion<PlayerTrigger.TriggerInstance> BREW_BLUE_CRITERION;
    public static Criterion<PlayerTrigger.TriggerInstance> USED_FORGE_ANY_CRITERION;
    public static Criterion<PlayerTrigger.TriggerInstance> DISTURBED_WISP_CRITERION;
    public static Criterion<PlayerTrigger.TriggerInstance> BURNED_GLOOMSCULK_CRYSTAL_CRITERION;
    public static Criterion<PlayerTrigger.TriggerInstance> WISP_SHED_EXPERIENCE_CRITERION;
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
        DISTURBED_WISP = new PlayerTrigger();
        helper.register(DISTURBED_WISP_ID, DISTURBED_WISP);
        BURNED_GLOOMSCULK_CRYSTAL = new PlayerTrigger();
        helper.register(BURNED_GLOOMSCULK_CRYSTAL_ID, BURNED_GLOOMSCULK_CRYSTAL);
        WISP_SHED_EXPERIENCE = new PlayerTrigger();
        helper.register(WISP_SHED_EXPERIENCE_ID, WISP_SHED_EXPERIENCE);
        CONVERT_BY_LIGHTNING = new ConvertByLightningTrigger();
        helper.register(CONVERT_BY_LIGHTNING.getId(), CONVERT_BY_LIGHTNING);

        BREW_BLUE_CRITERION = BNCriterion.BREW_BLUE.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        USED_FORGE_ANY_CRITERION = BNCriterion.USED_FORGE.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        DISTURBED_WISP_CRITERION = DISTURBED_WISP.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        BURNED_GLOOMSCULK_CRYSTAL_CRITERION = BURNED_GLOOMSCULK_CRYSTAL.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
        WISP_SHED_EXPERIENCE_CRITERION = WISP_SHED_EXPERIENCE.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
    }
}
