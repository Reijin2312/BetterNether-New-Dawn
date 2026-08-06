package org.betterx.betternether.world.biomes.providers;


import org.betterx.wover.math.api.MathHelper;
import org.betterx.wover.surface.api.conditions.SurfaceRulesContext;
import org.betterx.wover.surface.api.noise.NumericProvider;
import net.minecraft.util.RandomSource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class NetherGrasslandsNumericProvider implements NumericProvider {
    private static final int SEED = 0x6E677261;
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
        final RandomSource random = RandomSource.create(MathHelper.getSeed(SEED, ctx.getBlockX(), ctx.getBlockY(), ctx.getBlockZ()));
        if (depth <= 1) return random.nextInt(3);
        if (depth <= random.nextInt(3) + 1) return 0;
        return 2;
    }

    @Override
    public MapCodec<? extends NumericProvider> pcodec() {
        return CODEC;
    }

}
