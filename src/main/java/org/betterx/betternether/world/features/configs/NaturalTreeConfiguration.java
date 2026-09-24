package org.betterx.betternether.world.features.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.betterx.betternether.world.features.NonOverlappingFeature;

import java.util.function.Function;

public class NaturalTreeConfiguration {
    private static final NaturalTreeConfiguration NATURAL = new NaturalTreeConfiguration(true, 7);
    private static final NaturalTreeConfiguration USER = new NaturalTreeConfiguration(false, 7);
    private static final NaturalTreeConfiguration NATURAL_LARGE = new NaturalTreeConfiguration(true, 13);
    private static final NaturalTreeConfiguration USER_LARGE = new NaturalTreeConfiguration(false, 13);
    public final boolean natural;
    public final int distance;
    public final int manDist;

    public static <T extends NonOverlappingFeature<?>> MapCodec<T> mapCodecFor(
            Function<NaturalTreeConfiguration, T> factory
    ) {
        return RecordCodecBuilder.mapCodec(instance -> instance
                .group(
                        Codec.BOOL.fieldOf("natural").orElse(true).forGetter(o -> o.config.natural),
                        Codec.INT.fieldOf("distance").orElse(7).forGetter(o -> o.config.distance)
                )
                .apply(instance, (natural, distance) -> factory.apply(new NaturalTreeConfiguration(natural, distance))));
    }

    public NaturalTreeConfiguration(boolean natural, int distance) {
        this.natural = natural;
        this.distance = distance;
        this.manDist = (int) Math.ceil(distance * 1.5);
    }

    public static NaturalTreeConfiguration natural() {
        return NATURAL;
    }

    public static NaturalTreeConfiguration userGrown() {
        return USER;
    }

    public static NaturalTreeConfiguration naturalLarge() {
        return NATURAL_LARGE;
    }

    public static NaturalTreeConfiguration userGrownLarge() {
        return USER_LARGE;
    }
}
