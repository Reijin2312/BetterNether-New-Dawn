package org.betterx.betternether.world.biomes;


import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.features.placed.NetherObjectsPlaced;
import org.betterx.betternether.registry.features.placed.NetherVegetationPlaced;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.biomes.providers.NetherGrasslandsNumericProvider;
import org.betterx.betternether.world.biomes.util.NetherBiomeBuilder;
import org.betterx.wover.biome.api.builder.BiomeSurfaceRuleBuilder;
import org.betterx.wover.surface.impl.BaseSurfaceRuleBuilder;
import org.betterx.wover.surface.impl.rules.SwitchRuleSource;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;

public class NetherGrasslands extends NetherBiomeConfig {
    static final net.minecraft.world.level.levelgen.material.rule.MaterialRule SOUL_SOIL = MaterialRules.state(Blocks.SOUL_SOIL.defaultBlockState());
    static final net.minecraft.world.level.levelgen.material.rule.MaterialRule SOUL_SAND = MaterialRules.state(Blocks.SOUL_SAND.defaultBlockState());

    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule BLUE = MaterialRules.state(Blocks.CONCRETE.blue().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule LIGHT_BLUE = MaterialRules.state(Blocks.CONCRETE.lightBlue().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule CYAN = MaterialRules.state(Blocks.CONCRETE.cyan().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule GREEN = MaterialRules.state(Blocks.CONCRETE.green().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule LIME_GREEN = MaterialRules.state(Blocks.CONCRETE.lime().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule YELLOW = MaterialRules.state(Blocks.CONCRETE.yellow().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule ORANGE = MaterialRules.state(Blocks.CONCRETE.orange().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule RED = MaterialRules.state(Blocks.CONCRETE.red().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule PINK = MaterialRules.state(Blocks.CONCRETE.pink().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule PURPLE = MaterialRules.state(Blocks.CONCRETE.purple().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule MAGENTA = MaterialRules.state(Blocks.CONCRETE.magenta().defaultBlockState());
    private static final net.minecraft.world.level.levelgen.material.rule.MaterialRule BLACK = MaterialRules.state(Blocks.CONCRETE.black().defaultBlockState());
    //List.of(BLUE, LIGHT_BLUE, CYAN, GREEN, LIME_GREEN, YELLOW, ORANGE, RED, PINK, MAGENTA, PURPLE, BLACK)


    @Override
    public void addCustomBuildData(NetherBiomeBuilder builder) {
        builder.fogColor(113, 73, 133)
               .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
               .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
               .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
               .music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
               .structure(BiomeTags.HAS_BASTION_REMNANT)
               .structure(BiomeTags.HAS_NETHER_FORTRESS)
               .feature(NetherVegetationPlaced.NETHER_REED)
               .feature(NetherVegetationPlaced.BLACK_BUSH_SPARSE)
               .feature(NetherVegetationPlaced.VEGETATION_GRASSLANDS)
               .feature(NetherObjectsPlaced.SMOKER)
               .feature(NetherObjectsPlaced.STALACTITE)
               .feature(NetherVegetationPlaced.WALL_MUSHROOMS_WITH_MOSS)
               .addNetherClimate(0.0f, 0.7f, 0.0f)
        ;
    }

    public static net.minecraft.world.level.levelgen.material.rule.MaterialRule mossRule() {
        return MaterialRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
    }

    @Override
    public void surface(BiomeSurfaceRuleBuilder<NetherBiomeBuilder> builder) {
        super.surface(builder);

        builder.rule(
                MaterialRules.sequence(
                        MaterialRules.ifTrue(
                                MaterialRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
                                new SwitchRuleSource(
                                        NetherGrasslandsNumericProvider.DEFAULT,
                                        List.of(SOUL_SOIL, SOUL_SAND, mossRule(), NETHERRACK)
                                )
                        ),
                        new SwitchRuleSource(
                                NetherGrasslandsNumericProvider.DEFAULT,
                                List.of(SOUL_SOIL, SOUL_SAND, NETHERRACK)
                        )
                ),
                BaseSurfaceRuleBuilder.FLOOR_PRIORITY
        );
    }

    @Override
    public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
        int res = super.spawnWeight(type);
        switch (type) {
            case FIREFLY -> res = type.weight * 3;
        }
        return res;
    }

}
