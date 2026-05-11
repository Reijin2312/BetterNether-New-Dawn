package org.betterx.betternether.world.biomes.providers;


import org.betterx.wover.surface.api.conditions.SurfaceRulesContext;
import org.betterx.wover.surface.api.noise.NumericProvider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class NetherMushroomForestEdgeNumericProvider implements NumericProvider {
    public static final NetherMushroomForestEdgeNumericProvider DEFAULT = new NetherMushroomForestEdgeNumericProvider();
    public static final MapCodec<NetherMushroomForestEdgeNumericProvider> CODEC = Codec.BYTE.fieldOf(
            "nether_mushroom_forrest_edge").xmap((obj) -> DEFAULT, obj -> (byte) 0);

    @Override
    public int getNumber(SurfaceRulesContext ctx) {
        return randomInt(ctx, 0, 4) > 0 ? 0 : (randomInt(ctx, 1, 2) == 0 ? 1 : 2);
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
