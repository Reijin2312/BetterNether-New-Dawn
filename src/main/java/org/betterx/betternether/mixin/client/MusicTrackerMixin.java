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
    @Unique private static final float FADE_SPEED = 0.2f;
    @Unique private static final float TICK_DELTA = 0.05f;
    @Unique private final MusicManager bn_thisObj = (MusicManager) (Object) this;
    @Unique private boolean bn_waitChange;
    @Unique private float bn_volume = 1.0f;

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private RandomSource random;
    @Shadow private SoundInstance currentMusic;
    @Shadow private int nextSongDelay;

    @Unique
    private boolean bn_isInNether() {
        return minecraft.player != null && minecraft.level != null && minecraft.level.dimension() == Level.NETHER;
    }

    @Unique
    private boolean bn_shouldChangeMusic(Music toMusic) {
        return currentMusic == null || !toMusic.getEvent().value().getLocation().equals(currentMusic.getLocation());
    }

    @Unique
    private float bn_getVolumeSafe(float fallback) {
        if (currentMusic == null) return fallback;
        try {
            return currentMusic.getVolume();
        } catch (NullPointerException ignored) {
            return fallback;
        }
    }

    @Inject(method = "startPlaying", at = @At("TAIL"))
    private void bn_startPlaying(Music music, CallbackInfo info) {
        bn_volume = 0.0f;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void bn_onTick(CallbackInfo info) {
        if (!bn_isInNether()) {
            bn_waitChange = false;
            bn_volume = 1.0f;
            return;
        }

        Music targetMusic = minecraft.getSituationalMusic();
        if (targetMusic == null || !targetMusic.replaceCurrentMusic()) {
            bn_waitChange = false;
            bn_volume = 1.0f;
            return;
        }

        if (currentMusic != null && !minecraft.getSoundManager().isActive(currentMusic)) {
            currentMusic = null;
            nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, targetMusic.getMinDelay(), targetMusic.getMaxDelay()));
        }
        nextSongDelay = Math.min(nextSongDelay, targetMusic.getMaxDelay());

        if (currentMusic == null) {
            if (nextSongDelay-- <= 0) {
                bn_waitChange = false;
                bn_thisObj.startPlaying(targetMusic);
                bn_setCurrentMusicVolume(0.0f);
            }
            info.cancel();
            return;
        }

        boolean volumeChanged = false;
        if (bn_waitChange || bn_shouldChangeMusic(targetMusic)) {
            if (!bn_waitChange) {
                nextSongDelay = random.nextInt(0, Math.max(targetMusic.getMinDelay() / 2, 1));
                bn_waitChange = true;
            }
            if (bn_volume > 0.0f) {
                volumeChanged = true;
                bn_volume -= FADE_SPEED * TICK_DELTA;
                if (bn_volume <= 0.0f) {
                    bn_volume = 0.0f;
                    minecraft.getSoundManager().stop(currentMusic);
                    currentMusic = null;
                }
            } else if (nextSongDelay > 0) {
                nextSongDelay--;
            } else {
                bn_waitChange = false;
                bn_thisObj.startPlaying(targetMusic);
                bn_setCurrentMusicVolume(0.0f);
            }
        } else if (bn_volume < 1.0f) {
            volumeChanged = true;
            bn_volume += FADE_SPEED * TICK_DELTA;
        }

        if (volumeChanged) {
            bn_volume = Mth.clamp(bn_volume, 0.0f, 1.0f);
            bn_setCurrentMusicVolume(bn_volume);
        }

        info.cancel();
    }

    @Unique
    private void bn_setCurrentMusicVolume(float volume) {
        if (currentMusic instanceof AbstractSoundInstanceAccessor accessor) {
            accessor.setVolume(volume);
            minecraft.getSoundManager().updateSourceVolume(currentMusic.getSource(), bn_getVolumeSafe(volume));
        }
    }
}
