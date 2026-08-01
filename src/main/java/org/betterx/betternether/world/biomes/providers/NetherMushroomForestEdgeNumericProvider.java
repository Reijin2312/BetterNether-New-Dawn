package org.betterx.betternether.world.biomes.providers;


import org.betterx.wover.math.api.MathHelper;
import org.betterx.wover.surface.api.conditions.SurfaceRulesContext;
import org.betterx.wover.surface.api.noise.NumericProvider;
import net.minecraft.util.RandomSource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class NetherMushroomForestEdgeNumericProvider implements NumericProvider {
    private static final int SEED = 0x6D736872;
    public static final NetherMushroomForestEdgeNumericProvider DEFAULT = new NetherMushroomForestEdgeNumericProvider();
    public static final MapCodec<NetherMushroomForestEdgeNumericProvider> CODEC = Codec.BYTE.fieldOf(
            "nether_mushroom_forrest_edge").xmap((obj) -> DEFAULT, obj -> (byte) 0);

    @Override
    public int getNumber(SurfaceRulesContext ctx) {
        final RandomSource random = RandomSource.create(MathHelper.getSeed(SEED, ctx.getBlockX(), ctx.getBlockY(), ctx.getBlockZ()));
        return random.nextInt(4) > 0 ? 0 : (random.nextBoolean() ? 1 : 2);
    }

    @Override
    public MapCodec<? extends NumericProvider> pcodec() {
        return CODEC;
    }
}
