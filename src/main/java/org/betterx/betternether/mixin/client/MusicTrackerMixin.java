package org.betterx.betternether.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
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

@Mixin(MusicManager.class)
public abstract class MusicTrackerMixin {
    @Unique private static final float FADE_SPEED = 0.2f; // Units per second (0.2f -> Fade across 5 seconds)
    @Unique private static final float TICK_DELTA = 0.05f;
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
        return currentMusic == null || !toMusic.sound().value().location().equals(currentMusic.getIdentifier());
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
            }
        }

        info.cancel();
    }

    @Shadow
    public abstract void startPlaying(Music type);
}
