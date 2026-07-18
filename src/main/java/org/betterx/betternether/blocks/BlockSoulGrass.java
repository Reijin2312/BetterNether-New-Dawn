package org.betterx.betternether.blocks;

import org.betterx.betternether.client.block.BNModels;
import org.betterx.wover.block.api.model.BlockModelProvider;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSoulGrass extends BaseBlockNetherGrass.OnEverything implements BlockModelProvider {
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextInt(4) == 0) {
            world.addParticle(
                    ParticleTypes.PORTAL,
                    pos.getX() + random.nextDouble(),
                    pos.getY() + random.nextDouble() * 2,
                    pos.getZ() + random.nextDouble(),
                    random.nextDouble() * 0.05,
                    -1,
                    random.nextDouble() * 0.05
            );
        }
    }

    @Override
    public void provideBlockModels(Object modelGenerator) {
        WoverBlockModelGenerators generators = (WoverBlockModelGenerators) modelGenerator;
        BNModels.provideGrassBlockModels(generators, this, "soul_grass", 2);
    }
}
