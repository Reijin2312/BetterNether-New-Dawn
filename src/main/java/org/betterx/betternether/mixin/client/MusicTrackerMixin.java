package org.betterx.betternether.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;

@Mixin(MusicManager.class)
public abstract class MusicTrackerMixin {
    @Unique private static final float FADE_SPEED = 0.2f; // Units per second (0.2f -> Fade across 5 seconds)
    @Unique private static final float TICK_DELTA = 0.05f;
    @Unique private static final Method BN_UPDATE_SOURCE_VOLUME = bn_findUpdateSourceVolume();
    // Note: Assume game is at a constant 20 tps since MC doesn't have getTPS()
    // The use of currentTimeMillis() is ditched since it is overly complex for this system
    // The difference from this constant will only be noticeable if the game's TPS is extremely low
    // If the game is lagging to that extent, smooth music blending is the least of the player's worries

    @Unique private final MusicManager bn_thisObj = (MusicManager)(Object)this;
    @Unique private boolean bn_waitChange = false;
    @Unique private float bn_volume = 1.0f;

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private RandomSource random;
    @Shadow private SoundInstance currentMusic;
    @Shadow private int nextSongDelay;

    @Unique
    private boolean bn_isInNether() {
        return minecraft.player != null && minecraft.level != null
                && minecraft.level.dimension() == Level.NETHER;
    }

    @Unique
    private boolean bn_shouldChangeMusic(Music toMusic) {
        Identifier currentId = bn_getCurrentMusicIdSafe();
        Identifier targetId = bn_getTargetMusicIdSafe(toMusic);
        return currentId == null || targetId == null || !targetId.equals(currentId);
    }

    @Unique
    private static Method bn_findUpdateSourceVolume() {
        try {
            return SoundManager.class.getMethod("updateSourceVolume", SoundSource.class, float.class);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    @Unique
    private Identifier bn_getCurrentMusicIdSafe() {
        if (currentMusic == null) {
            return null;
        }
        try {
            return currentMusic.getIdentifier();
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    @Unique
    private Identifier bn_getTargetMusicIdSafe(Music toMusic) {
        try {
            return toMusic.sound().value().location();
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    /** Returns currentMusic.getVolume() or fallback if the sound is not yet initialized (avoids NPE). */
    @Unique
    private float bn_getVolumeSafe(float fallback) {
        if (currentMusic == null) return fallback;
        try {
            return currentMusic.getVolume();
        } catch (NullPointerException e) {
            return fallback;
        }
    }

    @Unique
    private void bn_updateSourceVolumeIfSupported(float fallback) {
        if (BN_UPDATE_SOURCE_VOLUME == null || currentMusic == null) {
            return;
        }
        try {
            BN_UPDATE_SOURCE_VOLUME.invoke(
                    minecraft.getSoundManager(),
                    currentMusic.getSource(),
                    bn_getVolumeSafe(fallback)
            );
        } catch (ReflectiveOperationException ignored) {
        }
    }

    @Inject(method = "startPlaying", at = @At("TAIL"))
    public void bn_startPlaying(Music music, CallbackInfo ci) {
        bn_volume = 0.0f; // Mostly to fix issues when the blending system becomes desynced due to other dims
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void bn_onTick(CallbackInfo info) {
        if (!bn_isInNether()) {
            bn_waitChange = false;
            bn_volume = 1.0f;
            return;
        }

        Music targetMusic = minecraft.getSituationalMusic();
        if (targetMusic == null || !targetMusic.replaceCurrentMusic()) {
            bn_waitChange = false;
            bn_volume = 1.0f;
            return; // If the target music cannot replace the current, let vanilla handle it
        }

        if (currentMusic != null && !minecraft.getSoundManager().isActive(currentMusic)) {
            currentMusic = null;
            nextSongDelay = Math.min(
                    nextSongDelay,
                    Mth.nextInt(random, targetMusic.minDelay(), targetMusic.maxDelay())
            );
        }
        nextSongDelay = Math.min(nextSongDelay, targetMusic.maxDelay());

        if (currentMusic == null) {
            if (nextSongDelay-- <= 0) {
                bn_waitChange = false;
                bn_thisObj.startPlaying(targetMusic);
                if (currentMusic instanceof AbstractSoundInstanceAccessor accessor) {
                    accessor.setVolume(0.0f);
                    bn_updateSourceVolumeIfSupported(0.0f);
                }
            }
            info.cancel();
            return;
        }

        boolean volumeChanged = false;
        if (bn_waitChange || bn_shouldChangeMusic(targetMusic)) {
            if (!bn_waitChange) {
                nextSongDelay = random.nextInt(0, Math.max(targetMusic.minDelay() / 2, 1));
                bn_waitChange = true;
            }
            if (bn_volume > 0.0f) {
                // Fade out current music
                volumeChanged = true;
                bn_volume -= FADE_SPEED * TICK_DELTA;
                if (bn_volume <= 0.0f) {
                    bn_volume = 0.0f;
                    minecraft.getSoundManager().stop(currentMusic);
                    currentMusic = null;
                }
            } else if (nextSongDelay > 0) {
                // In-between music delay
                nextSongDelay -= 1;
            } else {
                // Start new music
                bn_waitChange = false;
                bn_thisObj.startPlaying(targetMusic);
                if (currentMusic instanceof AbstractSoundInstanceAccessor accessor) {
                    accessor.setVolume(0.0f);
                    bn_updateSourceVolumeIfSupported(0.0f);
                }
            }
        } else if (bn_volume < 1.0f) {
            // Fade in new music
            volumeChanged = true;
            bn_volume += FADE_SPEED * TICK_DELTA;
        }

        if (volumeChanged) {
            bn_volume = Mth.clamp(bn_volume, 0.0f, 1.0f);
            if (currentMusic instanceof AbstractSoundInstanceAccessor accessor) {
                accessor.setVolume(bn_volume);
                bn_updateSourceVolumeIfSupported(bn_volume);
            }
        }

        info.cancel();
    }

    @Shadow
    public abstract void startPlaying(Music type);
}
