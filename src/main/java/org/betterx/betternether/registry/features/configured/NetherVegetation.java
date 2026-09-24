package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.bonemeal.BonemealAPI;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.JellyfishMushroomFeature;
import org.betterx.betternether.world.features.LucisFeature;
import org.betterx.betternether.world.features.NetherSakuraBushFeature;
import org.betterx.betternether.world.features.RubeusBushFeature;
import org.betterx.betternether.world.features.WartBushFeature;
import org.betterx.betternether.world.features.WillowBushFeature;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.events.api.WorldLifecycle;
import org.betterx.wover.feature.api.configured.FeatureKey;
import org.betterx.wover.feature.api.configured.FeatureContentManager;
import org.betterx.wover.feature.api.configured.configurators.*;
import org.betterx.wover.state.api.WorldState;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.stream.StreamSupport;

public class NetherVegetation {
    private static final ModCore C = BetterNether.C;

    // Ensure features are registered before any configured feature keys capture them
    static {
        NetherFeatures.register();
    }

    public static final FeatureKey<WeightedBlockPatch> BONEMEAL_NETHERRACK_MOSS =
            FeatureContentManager.bonemeal(C.id("bonemeal_netherrack_moss"));
    public static final FeatureKey<NetherForrestVegetation> BONEMEAL_NETHER_MYCELIUM =
            FeatureContentManager.bonemealNetherForrest(C.id("bonemeal_nether_mycelium"));
    public static final FeatureKey<NetherForrestVegetation> BONEMEAL_JUNGLE_GRASS =
            FeatureContentManager.bonemealNetherForrest(C.id("bonemeal_jungle_grass"));
    public static final FeatureKey<NetherForrestVegetation> BONEMEAL_MUSHROOM_GRASS =
            FeatureContentManager.bonemealNetherForrest(C.id("bonemeal_mushroom_grass"));
    public static final FeatureKey<NetherForrestVegetation> BONEMEAL_SEPIA_MUSHROOM_GRASS =
            FeatureContentManager.bonemealNetherForrest(C.id("bonemeal_sepia_mushroom_grass"));
    public static final FeatureKey<NetherForrestVegetation> BONEMEAL_SWAMPLAND_GRASS =
            FeatureContentManager.bonemealNetherForrest(C.id("bonemeal_swampland_grass"));
    public static final FeatureKey<WeightedBlockPatch> BONEMEAL_CEILING_MUSHROOMS =
            FeatureContentManager.bonemeal(C.id("bonemeal_ceiling_mushrooms"));
    public static final FeatureKey<AsMultiPlaceRandomSelect> VEGETATION_MUSHROOM_FORREST =
            FeatureContentManager.multiPlaceRandomFeature(C.id("vegetation_mushroom_forrest"));
    public static final FeatureKey<WithFeature<JellyfishMushroomFeature>> JELLYFISH_MUSHROOM =
            FeatureContentManager.withFeature(C.id("jellyfish_mushroom"), NetherFeatures.JELLYFISH_MUSHROOM);
    public static final FeatureKey<RandomPatch> PATCH_JELLYFISH_MUSHROOM =
            FeatureContentManager.randomPatch(C.id("patch_jellyfish_mushroom"));
    public static final FeatureKey<ForSimpleBlock> PATCH_BLACK_BUSH =
            FeatureContentManager.simple(C.id("patch_back_bush"));
    public static final FeatureKey<WithFeature<LucisFeature>> WALL_LUCIS =
            FeatureContentManager.withFeature(C.id("patch_lucis"), NetherFeatures.LUCIS);
    public static final FeatureKey<WeightedBlockPatch> BONEMEAL_SOUL_SOIL =
            FeatureContentManager.bonemeal(C.id("bonemeal_soul_soil"));
    /**
     * Gloomwood ground cover. A random block patch rather than
     * {@code netherForrestVegetation()}: that wraps vanilla's nether-forest-vegetation feature, which
     * refuses to place unless the block below is {@code #minecraft:nylium}, and the gloomwood floor is
     * sculk. Same reason {@code VEGETATION_MAGMA_LAND} below is built this way.
     */
    public static final FeatureKey<WeightedBlock> VEGETATION_GLOOMWOOD =
            FeatureContentManager.randomBlock(C.id("vegetation_gloomwood"));
    public static final FeatureKey<WeightedBlock> VEGETATION_MAGMA_LAND =
            FeatureContentManager.randomBlock(C.id("vegetation_magma_land"));
    public static final FeatureKey<WeightedBlock> VEGETATION_GRASSLANDS =
            FeatureContentManager.randomBlock(C.id("vegetation_nether_grasslands"));
    public static final FeatureKey<WeightedBlock> VEGETATION_GRAVEL_DESERT =
            FeatureContentManager.randomBlock(C.id("vegetation_nether_gravel_desert"));
    public static final FeatureKey<WeightedBlock> VEGETATION_JUNGLE =
            FeatureContentManager.randomBlock(C.id("vegetation_jungle"));
    public static final FeatureKey<AsMultiPlaceRandomSelect> VEGETATION_POOR_GRASSLANDS =
            FeatureContentManager.multiPlaceRandomFeature(C.id("vegetation_nether_poor_grasslands"));
    public static final FeatureKey<WeightedBlock> VEGETATION_SOUL_PLAIN =
            FeatureContentManager.randomBlock(C.id("vegetation_soul_plain"));
    public static final FeatureKey<WeightedBlock> VEGETATION_WART_FOREST =
            FeatureContentManager.randomBlock(C.id("vegetation_wart_forest"));
    public static final FeatureKey<WeightedBlock> VEGETATION_WART_FOREST_EDGE =
            FeatureContentManager.randomBlock(C.id("vegetation_wart_forest_edge"));
    public static final FeatureKey<WeightedBlock> VEGETATION_SWAMPLAND =
            FeatureContentManager.randomBlock(C.id("vegetation_nether_swampland"));
    public static final FeatureKey<WeightedBlock> VEGETATION_OLD_SWAMPLAND =
            FeatureContentManager.randomBlock(C.id("vegetation_old_swampland"));
    public static final FeatureKey<WeightedBlock> VEGETATION_OLD_WARPED_WOODS =
            FeatureContentManager.randomBlock(C.id("vegetation_old_warped_woods"));
    public static final FeatureKey<AsBlockColumn> NETHER_CACTUS =
            FeatureContentManager.blockColumn(C.id("patch_nether_cactus"));
    public static final FeatureKey<FacingBlock> WALL_MUSHROOM_RED_WITH_MOSS =
            FeatureContentManager.facingBlock(C.id("patch_wall_mushroom_red_with_moss"));
    public static final FeatureKey<FacingBlock> WALL_MUSHROOMS_WITH_MOSS =
            FeatureContentManager.facingBlock(C.id("patch_wall_mushrooms_with_moss"));
    public static final FeatureKey<FacingBlock> WALL_MUSHROOMS =
            FeatureContentManager.facingBlock(C.id("patch_wall_mushrooms"));
    public static final FeatureKey<FacingBlock> WALL_JUNGLE =
            FeatureContentManager.facingBlock(C.id("patch_wall_jungle"));
    public static final FeatureKey<FacingBlock> WALL_UPSIDE_DOWN =
            FeatureContentManager.facingBlock(C.id("patch_upside_down"));
    /**
     * A lone wisp head, for the shortest of the four heights.
     * <p>
     * Its own column because the block-column feature draws each layer independently: asking one column
     * for 0-2 stalk segments and 0-1 bright ones would produce a dark segment directly under the head a
     * third of the time, which is the join the two stalk textures exist to avoid.
     */
    public static final FeatureKey<AsBlockColumn> GLOOMWISP_VINE_HEAD =
            FeatureContentManager.blockColumn(C.id("patch_gloomwisp_vine_head"));
    public static final FeatureKey<AsBlockColumn> GLOOMWISP_VINE =
            FeatureContentManager.blockColumn(C.id("patch_gloomwisp_vine"));
    public static final FeatureKey<AsBlockColumn> NETHER_REED =
            FeatureContentManager.blockColumn(C.id("patch_nether_reed"));
    public static final FeatureKey<WithFeature<WartBushFeature>> WART_BUSH =
            FeatureContentManager.withFeature(C.id("patch_wart_bush"), NetherFeatures.WART_BUSH);
    public static final FeatureKey<WithFeature<WillowBushFeature>> WILLOW_BUSH =
            FeatureContentManager.withFeature(C.id("patch_willow_bush"), NetherFeatures.WILLOW_BUSH);
    public static final FeatureKey<WithFeature<RubeusBushFeature>> RUBEUS_BUSH =
            FeatureContentManager.withFeature(C.id("patch_rubeus_bush"), NetherFeatures.RUBEUS_BUSH);
    public static final FeatureKey<WithFeature<NetherSakuraBushFeature>> SAKURA_BUSH =
            FeatureContentManager.withFeature(C.id("patch_sakura_bush"), NetherFeatures.SAKURA_BUSH);
    public static final FeatureKey<WeightedBlock> SCULK_VEGETATION =
            FeatureContentManager.randomBlock(C.id("sculk_vegetation"));
    public static final FeatureKey<ForSimpleBlock> HOOK_MUSHROOM =
            FeatureContentManager.simple(C.id("patch_hook_mushroom"));
    public static final FeatureKey<ForSimpleBlock> MOSS_COVER =
            FeatureContentManager.simple(C.id("patch_moss_cover"));

    private static final TagKey<Block> TERRAIN_TAG = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("wover", "surfaces/nether/terrain")
    );
    private static final TagKey<Block> NETHERRACK_TAG = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath("wover", "surfaces/nether/netherrack")
    );
    private static boolean bonemealSetupDone = false;
    private static boolean terrainTagLogged = false;

    /**
     * Defers bonemeal hookup until the world lifecycle is ready to avoid touching registries during freeze.
     */
    public static void registerLifecycleHook() {
        WorldLifecycle.SERVER_LEVEL_READY.subscribe((level, key, stem, seed) -> {
            setupBonemealFeatures();
            logSurfaceTags(level);
        });
    }

    public static void setupBonemealFeatures() {
        if (bonemealSetupDone) return;
        bonemealSetupDone = true;
        NetherBlocks.NETHERRACK_MOSS.setVegetationFeature(() -> BONEMEAL_NETHERRACK_MOSS.getHolder(WorldState.registryAccess()));
        NetherBlocks.NETHER_MYCELIUM.setVegetationFeature(() -> BONEMEAL_NETHER_MYCELIUM.getHolder(WorldState.registryAccess()));
        NetherBlocks.JUNGLE_GRASS.setVegetationFeature(() -> BONEMEAL_JUNGLE_GRASS.getHolder(WorldState.registryAccess()));
        NetherBlocks.MUSHROOM_GRASS.setVegetationFeature(() -> BONEMEAL_MUSHROOM_GRASS.getHolder(WorldState.registryAccess()));
        NetherBlocks.SEPIA_MUSHROOM_GRASS.setVegetationFeature(() -> BONEMEAL_SEPIA_MUSHROOM_GRASS.getHolder(WorldState.registryAccess()));
        NetherBlocks.SWAMPLAND_GRASS.setVegetationFeature(() -> BONEMEAL_SWAMPLAND_GRASS.getHolder(WorldState.registryAccess()));
        NetherBlocks.CEILING_MUSHROOMS.setVegetationFeature(() -> BONEMEAL_CEILING_MUSHROOMS.getHolder(WorldState.registryAccess()));

        BonemealAPI.INSTANCE.addSpreadableFeatures(Blocks.SOUL_SOIL, () -> BONEMEAL_SOUL_SOIL.getHolder(WorldState.registryAccess()));
        BonemealAPI.INSTANCE.addSpreadableFeatures(Blocks.SOUL_SAND, () -> BONEMEAL_SOUL_SOIL.getHolder(WorldState.registryAccess()));
    }

    private static void logSurfaceTags(ServerLevel level) {
        if (terrainTagLogged) {
            return;
        }
        terrainTagLogged = true;
        Registry<Block> registry = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        logSurfaceTag(registry, TERRAIN_TAG, "wover:surfaces/nether/terrain");
        logSurfaceTag(registry, NETHERRACK_TAG, "wover:surfaces/nether/netherrack");
        logSurfaceTag(registry, CommonBlockTags.NETHER_STONES, "wover:surfaces/nether/stones");
        logSurfaceTag(registry, BlockTags.NYLIUM, "minecraft:nylium");
    }

    private static void logSurfaceTag(Registry<Block> registry, TagKey<Block> tagKey, String label) {
        var entries = StreamSupport.stream(registry.getTagOrEmpty(tagKey).spliterator(), false).toList();
        if (entries.isEmpty()) {
            BetterNether.C.LOG.warn("Missing block tag {} for vegetation placement checks.", label);
            return;
        }
        boolean hasJungleGrass = entries.stream().anyMatch(holder -> holder.value() == NetherBlocks.JUNGLE_GRASS);
        BetterNether.C.LOG.info(
                "Block tag {} size={}, has betternether:jungle_grass={}",
                label,
                entries.size(),
                hasJungleGrass
        );
    }
}
