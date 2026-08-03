package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

import net.neoforged.neoforge.registries.RegisterEvent;

public class SoundsRegistry {
    public static Holder<SoundEvent> AMBIENT_MUSHROOM_FOREST;
    public static Holder<SoundEvent> AMBIENT_GRAVEL_DESERT;
    public static Holder<SoundEvent> AMBIENT_NETHER_JUNGLE;
    public static Holder<SoundEvent> AMBIENT_SWAMPLAND;

    public static Holder<SoundEvent> MUSIC_GLOOMWOOD;
    public static Holder<SoundEvent> MUSIC_DISC_GLOOM_WOODS;
    public static Holder<SoundEvent> MUSIC_DISC_GLOOM_WISPS;
    public static Holder<SoundEvent> MUSIC_DISC_GLOOMSCULK;
    public static Holder<SoundEvent> BLOCK_GLOOMWISP_CHIME;
    public static Holder<SoundEvent> BLOCK_GLOOMWISP_BOON;

    public static Holder<SoundEvent> MOB_FIREFLY_FLY;
    public static Holder<SoundEvent> MOB_JELLYFISH;
    public static Holder<SoundEvent> MOB_NAGA_IDLE;
    public static Holder<SoundEvent> MOB_NAGA_ATTACK;
    public static Holder<SoundEvent> MOB_SKULL_FLIGHT;

    private static final ResourceKey<SoundEvent> KEY_AMBIENT_MUSHROOM_FOREST = key("betternether.ambient.mushroom_forest");
    private static final ResourceKey<SoundEvent> KEY_AMBIENT_GRAVEL_DESERT = key("betternether.ambient.gravel_desert");
    private static final ResourceKey<SoundEvent> KEY_AMBIENT_NETHER_JUNGLE = key("betternether.ambient.nether_jungle");
    private static final ResourceKey<SoundEvent> KEY_AMBIENT_SWAMPLAND = key("betternether.ambient.swampland");
    private static final ResourceKey<SoundEvent> KEY_MUSIC_GLOOMWOOD = key("betternether.music.gloomwood");
    private static final ResourceKey<SoundEvent> KEY_MUSIC_DISC_GLOOM_WOODS = key("betternether.music_disc.gloom_woods");
    private static final ResourceKey<SoundEvent> KEY_MUSIC_DISC_GLOOM_WISPS = key("betternether.music_disc.gloom_wisps");
    private static final ResourceKey<SoundEvent> KEY_MUSIC_DISC_GLOOMSCULK = key("betternether.music_disc.gloomsculk");
    private static final ResourceKey<SoundEvent> KEY_BLOCK_GLOOMWISP_CHIME = key("betternether.block.gloomwisp_chime");
    private static final ResourceKey<SoundEvent> KEY_BLOCK_GLOOMWISP_BOON = key("betternether.block.gloomwisp_boon");

    private static final ResourceKey<SoundEvent> KEY_MOB_FIREFLY_FLY = key("betternether.mob.firefly.fly");
    private static final ResourceKey<SoundEvent> KEY_MOB_JELLYFISH = key("betternether.mob.jellyfish");
    private static final ResourceKey<SoundEvent> KEY_MOB_NAGA_IDLE = key("betternether.mob.naga_idle");
    private static final ResourceKey<SoundEvent> KEY_MOB_NAGA_ATTACK = key("betternether.mob.naga_attack");
    private static final ResourceKey<SoundEvent> KEY_MOB_SKULL_FLIGHT = key("betternether.mob.skull_flight");

    private static ResourceKey<SoundEvent> key(String id) {
        return ResourceKey.create(Registries.SOUND_EVENT, BetterNether.C.mk(id));
    }

    public static void register(RegisterEvent event) {
        event.register(Registries.SOUND_EVENT, helper -> {
            register(helper, KEY_AMBIENT_MUSHROOM_FOREST);
            register(helper, KEY_AMBIENT_GRAVEL_DESERT);
            register(helper, KEY_AMBIENT_NETHER_JUNGLE);
            register(helper, KEY_AMBIENT_SWAMPLAND);
            register(helper, KEY_MUSIC_GLOOMWOOD);
            register(helper, KEY_MUSIC_DISC_GLOOM_WOODS);
            register(helper, KEY_MUSIC_DISC_GLOOM_WISPS);
            register(helper, KEY_MUSIC_DISC_GLOOMSCULK);
            register(helper, KEY_BLOCK_GLOOMWISP_CHIME);
            register(helper, KEY_BLOCK_GLOOMWISP_BOON);

            register(helper, KEY_MOB_FIREFLY_FLY);
            register(helper, KEY_MOB_JELLYFISH);
            register(helper, KEY_MOB_NAGA_IDLE);
            register(helper, KEY_MOB_NAGA_ATTACK);
            register(helper, KEY_MOB_SKULL_FLIGHT);
        });
    }

    private static void register(RegisterEvent.RegisterHelper<SoundEvent> helper, ResourceKey<SoundEvent> key) {
        helper.register(key.location(), SoundEvent.createVariableRangeEvent(key.location()));
        assignHolder(key, BuiltInRegistries.SOUND_EVENT.getHolder(key).orElseThrow());
    }

    private static void assignHolder(ResourceKey<SoundEvent> key, Holder<SoundEvent> holder) {
        if (key == KEY_AMBIENT_MUSHROOM_FOREST) {
            AMBIENT_MUSHROOM_FOREST = holder;
        } else if (key == KEY_AMBIENT_GRAVEL_DESERT) {
            AMBIENT_GRAVEL_DESERT = holder;
        } else if (key == KEY_AMBIENT_NETHER_JUNGLE) {
            AMBIENT_NETHER_JUNGLE = holder;
        } else if (key == KEY_AMBIENT_SWAMPLAND) {
            AMBIENT_SWAMPLAND = holder;
        } else if (key == KEY_MUSIC_GLOOMWOOD) {
            MUSIC_GLOOMWOOD = holder;
        } else if (key == KEY_MUSIC_DISC_GLOOM_WOODS) {
            MUSIC_DISC_GLOOM_WOODS = holder;
        } else if (key == KEY_MUSIC_DISC_GLOOM_WISPS) {
            MUSIC_DISC_GLOOM_WISPS = holder;
        } else if (key == KEY_MUSIC_DISC_GLOOMSCULK) {
            MUSIC_DISC_GLOOMSCULK = holder;
        } else if (key == KEY_BLOCK_GLOOMWISP_CHIME) {
            BLOCK_GLOOMWISP_CHIME = holder;
        } else if (key == KEY_BLOCK_GLOOMWISP_BOON) {
            BLOCK_GLOOMWISP_BOON = holder;
        } else if (key == KEY_MOB_FIREFLY_FLY) {
            MOB_FIREFLY_FLY = holder;
        } else if (key == KEY_MOB_JELLYFISH) {
            MOB_JELLYFISH = holder;
        } else if (key == KEY_MOB_NAGA_IDLE) {
            MOB_NAGA_IDLE = holder;
        } else if (key == KEY_MOB_NAGA_ATTACK) {
            MOB_NAGA_ATTACK = holder;
        } else if (key == KEY_MOB_SKULL_FLIGHT) {
            MOB_SKULL_FLIGHT = holder;
        }
    }

    public static void ensureStaticallyLoaded() {
        // no-op; registration is performed via RegisterEvent
    }
}
