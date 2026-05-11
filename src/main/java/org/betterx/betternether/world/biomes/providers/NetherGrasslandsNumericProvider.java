package org.betterx.betternether.world.biomes.providers;


import org.betterx.wover.surface.api.conditions.SurfaceRulesContext;
import org.betterx.wover.surface.api.noise.NumericProvider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class NetherGrasslandsNumericProvider implements NumericProvider {
    public static final NetherGrasslandsNumericProvider DEFAULT = new NetherGrasslandsNumericProvider();
    public static final MapCodec<NetherGrasslandsNumericProvider> CODEC = Codec.BYTE
            .fieldOf("nether_grasslands")
            .xmap(
                    (obj) -> DEFAULT,
                    obj -> (byte) 0
            );

    @Override
    public int getNumber(SurfaceRulesContext ctx) {
        final int depth = ctx.getStoneDepthAbove();
        if (depth <= 1) return randomInt(ctx, 0, 3);
        if (depth <= randomInt(ctx, 1, 3) + 1) return 0;
        return 2;
    }

    private static int randomInt(SurfaceRulesContext ctx, int salt, int bound) {
        int hash = ctx.getBlockX();
        hash = 31 * hash + ctx.getBlockY();
        hash = 31 * hash + ctx.getBlockZ();
        hash = 31 * hash + salt;
        hash ^= hash >>> 16;
        hash *= 0x7feb352d;
        hash ^= hash >>> 15;
        hash *= 0x846ca68b;
        hash ^= hash >>> 16;
        return Math.floorMod(hash, bound);
    }

    @Override
    public MapCodec<? extends NumericProvider> pcodec() {
        return CODEC;
    }

}
