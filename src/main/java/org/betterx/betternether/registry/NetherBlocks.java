package org.betterx.betternether.registry;

import org.betterx.bclib.api.v3.tag.BCLBlockTags;
import org.betterx.bclib.blocks.*;
import org.betterx.bclib.complexmaterials.set.wood.BarStool;
import org.betterx.bclib.complexmaterials.set.wood.Chair;
import org.betterx.bclib.complexmaterials.set.wood.Taburet;
import org.betterx.bclib.complexmaterials.set.wood.WoodSlots;
import org.betterx.bclib.furniture.block.BaseBarStool;
import org.betterx.bclib.furniture.block.BaseChair;
import org.betterx.bclib.furniture.block.BaseTaburet;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.*;
import org.betterx.betternether.blocks.complex.*;
import org.betterx.betternether.blocks.complex.slots.VanillaNetherWood;
import org.betterx.betternether.blocks.complex.slots.VanillaWood;
import org.betterx.betternether.recipes.RecipesHelper;
import org.betterx.betternether.registry.features.configured.NetherVines;
import org.betterx.wover.block.api.BlockRegistry;
import org.betterx.wover.complex.api.equipment.ToolTiers;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.state.api.WorldState;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;
import org.betterx.wover.tag.api.predefined.CommonPoiTags;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class NetherBlocks {

        public static Block NETHER_BRICK_TILE_LARGE;

    // Reed //
        public static Block NETHER_REED_STEM;
        public static NetherReedMaterial MAT_REED;

    // Stalagnate //
        public static StalagnateMaterial MAT_STALAGNATE;

    // Willow //
        public static WillowMaterial MAT_WILLOW;
        public static Block WILLOW_LEAVES;

    // Wart //
        public static WartMaterial MAT_WART;
    // Cincinnasite //
        public static Block CINCINNASITE_ORE;
        public static Block CINCINNASITE_BLOCK;
        public static Block CINCINNASITE_FORGED;
        public static Block CINCINNASITE_PILLAR;
        public static Block CINCINNASITE_BRICKS;
        public static Block CINCINNASITE_BRICK_PLATE;
        public static Block CINCINNASITE_STAIRS;
        public static Block CINCINNASITE_SLAB;
        public static Block TABURET_CINCINNASITE;
        public static Block CHAIR_CINCINNASITE;
        public static Block BAR_STOOL_CINCINNASITE;
        public static Block CINCINNASITE_BUTTON;
        public static Block CINCINNASITE_PLATE;
        public static Block CINCINNASITE_LANTERN;
        public static Block CINCINNASITE_TILE_LARGE;
        public static Block CINCINNASITE_TILE_SMALL;
        public static Block CINCINNASITE_CARVED;
        public static Block CINCINNASITE_WALL;
        public static Block CINCINNASITE_BRICKS_PILLAR;
        public static Block CINCINNASITE_BARS;
        public static Block CINCINNASITE_PEDESTAL;
        public static Block CINCINNASITE_FRAME;
        public static Block CINCINNASITE_LANTERN_SMALL;
        public static Block CINCINNASITE_CHAIN;
    // Ruby //
        public static Block NETHER_RUBY_ORE;
        public static Block NETHER_RUBY_BLOCK;
        public static Block NETHER_RUBY_STAIRS;
        public static Block NETHER_RUBY_SLAB;
    // Vanilla Ores
        public static Block NETHER_LAPIS_ORE;
        public static Block NETHER_REDSTONE_ORE;
    // Bricks //
        public static Block NETHER_BRICK_TILE_SMALL;
        public static Block NETHER_BRICK_WALL;
        public static Block NETHER_BRICK_TILE_SLAB;
        public static Block NETHER_BRICK_TILE_STAIRS;
    // Bone //
        public static Block BONE_BLOCK;
        public static Block BONE_STAIRS;
        public static Block BONE_SLAB;
        public static Block BONE_BUTTON;
        public static Block BONE_PLATE;
        public static Block BONE_WALL;
        public static Block BONE_TILE;
        public static Block BONE_REED_DOOR;
        public static Block BONE_CINCINNASITE_DOOR;
    // Quartz Glass //
        public static Block QUARTZ_GLASS;
        public static Block QUARTZ_GLASS_FRAMED;
        public static ColoredGlassMaterial QUARTZ_GLASS_FRAMED_COLORED;
        public static Block QUARTZ_GLASS_PANE;
        public static ColoredGlassMaterial QUARTZ_GLASS_PANE_COLORED;
        public static Block QUARTZ_GLASS_FRAMED_PANE;
        public static ColoredGlassMaterial QUARTZ_GLASS_FRAMED_PANE_COLORED;
    // Quartz Glass Colored //
        public static ColoredGlassMaterial QUARTZ_GLASS_COLORED;
    // Obsidian //
        public static Block BLUE_WEEPING_OBSIDIAN;
        public static Block WEEPING_OBSIDIAN;
        public static Block BLUE_CRYING_OBSIDIAN;
        public static Block OBSIDIAN_BRICKS;
        public static Block OBSIDIAN_BRICKS_STAIRS;
        public static Block OBSIDIAN_BRICKS_SLAB;
        public static Block OBSIDIAN_TILE;
        public static Block OBSIDIAN_TILE_SMALL;
        public static Block OBSIDIAN_TILE_STAIRS;
        public static Block OBSIDIAN_TILE_SLAB;
        public static Block OBSIDIAN_ROD_TILES;
        public static Block OBSIDIAN_GLASS;
        public static Block OBSIDIAN_GLASS_PANE;
        public static Block BLUE_OBSIDIAN;
        public static Block BLUE_OBSIDIAN_BRICKS;
        public static Block BLUE_OBSIDIAN_BRICKS_STAIRS;
        public static Block BLUE_OBSIDIAN_BRICKS_SLAB;
        public static Block BLUE_OBSIDIAN_TILE;
        public static Block BLUE_OBSIDIAN_TILE_SMALL;
        public static Block BLUE_OBSIDIAN_TILE_STAIRS;
        public static Block BLUE_OBSIDIAN_TILE_SLAB;
        public static Block BLUE_OBSIDIAN_ROD_TILES;
        public static Block BLUE_OBSIDIAN_GLASS;
        public static Block BLUE_OBSIDIAN_GLASS_PANE;
    // Soul Sandstone //
        public static Block SOUL_SANDSTONE;
        public static Block SOUL_SANDSTONE_CUT;
        public static Block SOUL_SANDSTONE_CUT_STAIRS;
        public static Block SOUL_SANDSTONE_CUT_SLAB;
        public static Block SOUL_SANDSTONE_WALL;
        public static Block SOUL_SANDSTONE_SMOOTH;
        public static Block SOUL_SANDSTONE_CHISELED;
        public static Block SOUL_SANDSTONE_STAIRS;
        public static Block SOUL_SANDSTONE_SMOOTH_STAIRS;
        public static Block SOUL_SANDSTONE_SLAB;
        public static Block SOUL_SANDSTONE_SMOOTH_SLAB;
    // Basalt Bricks //
        public static Block BASALT_BRICKS;
        public static Block BASALT_BRICKS_STAIRS;
        public static Block BASALT_BRICKS_SLAB;
        public static Block BASALT_BRICKS_WALL;
        public static Block BASALT_SLAB;
        public static Block ORANGE_MUSHROOM;
        public static Block RED_MOLD;
        public static Block GRAY_MOLD;
        public static Block LUCIS_SPORE;
        public static Block GIANT_LUCIS;
        public static Block GIANT_MOLD_SAPLING;
        public static Block JELLYFISH_MUSHROOM_SAPLING;
        public static Block EYE_SEED;
    // Grass //
        public static Block NETHER_GRASS;
        public static Block SWAMP_GRASS;
        public static Block SOUL_GRASS;
        public static Block JUNGLE_PLANT;
        public static Block BONE_GRASS;
        public static Block SEPIA_BONE_GRASS;
    // Vines //
        public static Block BLACK_VINE;
        public static Block BLOOMING_VINE;
        public static Block GOLDEN_VINE;

        public static BlockLumabusVine LUMABUS_VINE;
        public static BlockLumabusVine GOLDEN_LUMABUS_VINE;

    // Small Plants
        public static Block SOUL_VEIN;
        public static Block BONE_MUSHROOM;
        public static Block BLACK_BUSH;
        public static Block INK_BUSH;
        public static Block INK_BUSH_SEED;
        public static Block SMOKER;
        public static Block EGG_PLANT;
        public static Block BLACK_APPLE;
        public static Block BLACK_APPLE_SEED;
        public static Block MAGMA_FLOWER;
        public static Block FEATHER_FERN;
        public static Block MOSS_COVER;
        public static Block NEON_EQUISETUM;
        public static Block HOOK_MUSHROOM;
        public static Block WHISPERING_GOURD_VINE;
        public static Block WHISPERING_GOURD;
        public static Block WHISPERING_GOURD_LANTERN;
    // Cactuses //
        public static Block AGAVE;
        public static Block BARREL_CACTUS;
        public static Block NETHER_CACTUS;
    // Wall plants
        public static Block WALL_MOSS;
        public static Block WALL_MUSHROOM_BROWN;
        public static Block WALL_MUSHROOM_RED;
        public static Block JUNGLE_MOSS;
    // Decorations //
        public static Block PIG_STATUE_RESPAWNER;
        public static Block CINCINNASITE_POT;
        public static Block BRICK_POT;
        public static Block GEYSER;
        public static Block NETHERRACK_STALACTITE;
        public static Block GLOWSTONE_STALACTITE;
        public static Block BLACKSTONE_STALACTITE;
        public static Block BASALT_STALACTITE;
        public static Block BONE_STALACTITE;
    // Fire Bowls
        public static Block CINCINNASITE_FIRE_BOWL;
        public static Block BRICKS_FIRE_BOWL;
        public static Block NETHERITE_FIRE_BOWL;
        public static Block CINCINNASITE_FIRE_BOWL_SOUL;
        public static Block BRICKS_FIRE_BOWL_SOUL;
        public static Block NETHERITE_FIRE_BOWL_SOUL;
    // Terrain //
        public static BlockTerrain NETHERRACK_MOSS;
        public static BlockNetherMycelium NETHER_MYCELIUM;
        public static BlockTerrain JUNGLE_GRASS;
        public static BlockTerrain MUSHROOM_GRASS;
        public static BlockTerrain SEPIA_MUSHROOM_GRASS;
        public static BlockTerrain SWAMPLAND_GRASS;
        public static Block FARMLAND;
        public static BlockTerrain CEILING_MUSHROOMS;
    // Roofs //
        public static Block ROOF_TILE_NETHER_BRICKS;
        public static Block ROOF_TILE_NETHER_BRICKS_STAIRS;
        public static Block ROOF_TILE_NETHER_BRICKS_SLAB;
        public static Block ROOF_TILE_CINCINNASITE;
        public static Block ROOF_TILE_CINCINNASITE_STAIRS;
        public static Block ROOF_TILE_CINCINNASITE_SLAB;
    // Craft Stations //
        public static Block BLACKSTONE_FURNACE;
        public static Block BASALT_FURNACE;
        public static Block NETHERRACK_FURNACE;
        public static Block CINCINNASITE_FORGE;
        public static Block NETHER_BREWING_STAND;
        public static Block CINCINNASITE_ANVIL;

        public static VanillaNetherWood WARPED_WOOD;

        public static VanillaNetherWood CRIMSON_WOOD;

        public static VanillaWood OAK_WOOD;
        public static VanillaWood SPRUCE_WOOD;
        public static VanillaWood BIRCH_WOOD;
        public static VanillaWood JUNGLE_WOOD;
        public static VanillaWood ACACIA_WOOD;
        public static VanillaWood DARK_OAK_WOOD;
        public static VanillaWood CHERRY_WOOD;
        public static VanillaWood BAMBOO_WOOD;
        public static VanillaWood MANGROVE_WOOD;
    // Storage
        public static Block CHEST_OF_DRAWERS;

    private static BlockRegistry BLOCKS_REGISTRY;
    private static boolean blocksRegistered;
    // Rubeus //
        public static RubeusMaterial MAT_RUBEUS;
        public static Block RUBEUS_LEAVES;
    // Mushroom Fir //
        public static MushroomFirMaterial MAT_MUSHROOM_FIR;
        public static Block TRIMMED_MUSHROOM_FIR_CHEST;
    // Mushroom //
        public static NetherMushroomMaterial MAT_NETHER_MUSHROOM;
    // Anchor Tree
        public static AnchorTreeMaterial MAT_ANCHOR_TREE;
        public static Block ANCHOR_TREE_LEAVES;
        public static Block ANCHOR_TREE_VINE;
    // Nether Sakura
        public static NetherSakuraMaterial MAT_NETHER_SAKURA;
        public static Block NETHER_SAKURA_LEAVES;
    // Soul lily //
        public static Block SOUL_LILY;
        public static Block SOUL_LILY_SAPLING;
    // Large & Small Mushrooms //
        public static Block RED_LARGE_MUSHROOM;
        public static Block BROWN_LARGE_MUSHROOM;
    // Lucis //
        public static Block LUCIS_MUSHROOM;
    // Giant Mold //
        public static Block GIANT_MOLD;
        public static Block JELLYFISH_MUSHROOM;
    // Eyes //
        public static Block EYEBALL;
        public static Block EYEBALL_SMALL;
        public static Block EYE_VINE;

        public static Block POTTED_PLANT;
        public static Block VEINED_SAND;

        public static Block NETHERRACK_SLAB;
        public static Block NETHERRACK_STAIR;
        public static Block NETHERRACK_WALLS;


    // DEFERED BLOCKS //
        public static Block LUMABUS_SEED;

        public static Block GOLDEN_LUMABUS_SEED;

    static {
        getBlockRegistry();
    }

    private NetherBlocks() {

    }

    @NotNull
    public static BlockRegistry getBlockRegistry() {
        if (BLOCKS_REGISTRY == null) {
            BLOCKS_REGISTRY = BlockRegistry.forMod(BetterNether.C);
            BLOCKS_REGISTRY.setInitializer(NetherBlocks::registerBlocks);
        }
        return BLOCKS_REGISTRY;
    }

    private static void registerBlocks() {
        if (blocksRegistered) return;
        blocksRegistered = true;


            NETHER_BRICK_TILE_LARGE = registerBlock("nether_brick_tile_large", new BNNetherBrick());


    // Reed //
            NETHER_REED_STEM = registerBlock("nether_reed_stem", new BlockNetherReed());

            MAT_REED = new NetherReedMaterial().init();


    // Stalagnate //
            MAT_STALAGNATE = new StalagnateMaterial().init();


    // Willow //
            MAT_WILLOW = new WillowMaterial().init();

            WILLOW_LEAVES = registerBlock(
            "willow_leaves",
            new BlockWillowLeaves(MAT_WILLOW.getSapling())
    );


    // Wart //
            MAT_WART = new WartMaterial(
            "wart",
            MapColor.COLOR_RED,
            MapColor.COLOR_RED
    ).init();

    // Cincinnasite //
            CINCINNASITE_ORE = registerBlock(
            "cincinnasite_ore",
            new BlockOre(
                    () -> NetherItems.CINCINNASITE,
                    1,
                    3,
                    0,
                    ToolTiers.IRON_TOOL.blockTag,
                    true
            )
    );

            CINCINNASITE_BLOCK = registerBlock("cincinnasite_block", new BlockCincinnasite());

            CINCINNASITE_FORGED = registerBlock("cincinnasite_forged", new BlockCincinnasite());

            CINCINNASITE_PILLAR = registerBlock("cincinnasite_pillar", new BlockCincinnasitPillar());

            CINCINNASITE_BRICKS = registerBlock("cincinnasite_bricks", new BlockCincinnasite());

            CINCINNASITE_BRICK_PLATE = registerBlock(
            "cincinnasite_brick_plate",
            new BlockCincinnasite()
    );

            CINCINNASITE_STAIRS = registerStairs("cincinnasite_stairs", CINCINNASITE_FORGED, false);

            CINCINNASITE_SLAB = registerSlab("cincinnasite_slab", CINCINNASITE_FORGED, false);

            TABURET_CINCINNASITE = registerTaburet("taburet_cincinnasite", CINCINNASITE_SLAB);

            CHAIR_CINCINNASITE = registerChair("chair_cincinnasite", CINCINNASITE_SLAB);

            BAR_STOOL_CINCINNASITE = registerBarStool("bar_stool_cincinnasite", CINCINNASITE_SLAB);

            CINCINNASITE_BUTTON = registerBlock(
            "cincinnasite_button",
            BaseButtonBlock.from(CINCINNASITE_FORGED, BlockSetType.GOLD)
    );

            CINCINNASITE_PLATE = registerPlate(
            "cincinnasite_plate",
            CINCINNASITE_FORGED,
            BlockSetType.GOLD
    );

            CINCINNASITE_LANTERN = registerBlock(
            "cincinnasite_lantern",
            new BlockCincinnasiteLantern()
    );

            CINCINNASITE_TILE_LARGE = registerBlock(
            "cincinnasite_tile_large",
            new BlockCincinnasite()
    );

            CINCINNASITE_TILE_SMALL = registerBlock(
            "cincinnasite_tile_small",
            new BlockCincinnasite()
    );

            CINCINNASITE_CARVED = registerBlock("cincinnasite_carved", new BlockCincinnasite());

            CINCINNASITE_WALL = registerWall("cincinnasite_wall", CINCINNASITE_FORGED);

            CINCINNASITE_BRICKS_PILLAR = registerBlock(
            "cincinnasite_bricks_pillar",
            new BNPillar.Metal(CINCINNASITE_FORGED)
    );

            CINCINNASITE_BARS = registerBlock(
            "cincinnasite_bars",
            new BNPane.Metal(CINCINNASITE_FORGED, true)
    );

            CINCINNASITE_PEDESTAL = registerBlock(
            "cincinnasite_pedestal",
            new BlockCincinnasitePedestal()
    );

            CINCINNASITE_FRAME = registerBlock("cincinnasite_frame", new BlockCincinnasiteFrame());

            CINCINNASITE_LANTERN_SMALL = registerBlock(
            "cincinnasite_lantern_small",
            new BlockSmallLantern.Metal()
    );

            CINCINNASITE_CHAIN = registerBlock("cincinnasite_chain", new BNChain());

    // Ruby //
            NETHER_RUBY_ORE = registerBlock(
            "nether_ruby_ore",
            new BlockOre(
                    () -> NetherItems.NETHER_RUBY,
                    1,
                    2,
                    5,
                    ToolTiers.DIAMOND_TOOL.blockTag,
                    true
            )
    );

            NETHER_RUBY_BLOCK = registerBlock("nether_ruby_block", new BlockNetherRuby());

            NETHER_RUBY_STAIRS = registerStairs("nether_ruby_stairs", NETHER_RUBY_BLOCK, true);

            NETHER_RUBY_SLAB = registerSlab("nether_ruby_slab", NETHER_RUBY_BLOCK, true);

    // Vanilla Ores
            NETHER_LAPIS_ORE = registerBlock(
            "nether_lapis_ore",
            new BlockOre(
                    () -> NetherItems.LAPIS_PILE,
                    3,
                    6,
                    3,
                    ToolTiers.IRON_TOOL.blockTag,
                    false
            )
    );

            NETHER_REDSTONE_ORE = registerBlock("nether_redstone_ore", new RedstoneOreBlock());

    // Bricks //
            NETHER_BRICK_TILE_SMALL = registerBlock("nether_brick_tile_small", new BNNetherBrick());

            NETHER_BRICK_WALL = registerWall("nether_brick_wall", NETHER_BRICK_TILE_LARGE);

            NETHER_BRICK_TILE_SLAB = registerSlab(
            "nether_brick_tile_slab",
            NETHER_BRICK_TILE_SMALL,
            false
    );

            NETHER_BRICK_TILE_STAIRS = registerStairs(
            "nether_brick_tile_stairs",
            NETHER_BRICK_TILE_SMALL,
            false
    );

    // Bone //
            BONE_BLOCK = registerBlock("bone_block", new BNBoneBlock());

            BONE_STAIRS = registerStairs("bone_stairs", BONE_BLOCK, false);

            BONE_SLAB = registerSlab("bone_slab", BONE_BLOCK, false);

            BONE_BUTTON = registerButton("bone_button", BONE_BLOCK, BlockSetType.CRIMSON);

            BONE_PLATE = registerPlate("bone_plate", BONE_BLOCK, BlockSetType.CRIMSON);

            BONE_WALL = registerWall("bone_wall", BONE_BLOCK);

            BONE_TILE = registerBlock("bone_tile", new BNBoneBlock());

            BONE_REED_DOOR = registerBlock(
            "bone_reed_door",
            new BNWoodlikeDoor(BONE_BLOCK, WoodType.CRIMSON)
    );

            BONE_CINCINNASITE_DOOR = registerBlock(
            "bone_cincinnasite_door",
            new BNWoodlikeDoor(BONE_BLOCK, WoodType.CRIMSON)
    );

    // Quartz Glass //
            QUARTZ_GLASS = registerBlock("quartz_glass", new BNGlass(Blocks.GLASS));

            QUARTZ_GLASS_FRAMED = registerBlock(
            "quartz_glass_framed",
            new BNGlass(CINCINNASITE_BLOCK)
    );

            QUARTZ_GLASS_FRAMED_COLORED = new ColoredGlassMaterial(
            "quartz_glass_framed",
            QUARTZ_GLASS_FRAMED
    );

            QUARTZ_GLASS_PANE = registerBlock(
            "quartz_glass_pane",
            new BNPane.Glass(QUARTZ_GLASS, true)
    );

            QUARTZ_GLASS_PANE_COLORED = new ColoredGlassMaterial(
            "quartz_glass_pane",
            QUARTZ_GLASS_PANE,
            false
    );

            QUARTZ_GLASS_FRAMED_PANE = registerBlock(
            "quartz_glass_framed_pane",
            new BNPane.Metal(CINCINNASITE_BLOCK, true)
    );

            QUARTZ_GLASS_FRAMED_PANE_COLORED = new ColoredGlassMaterial(
            "quartz_glass_framed_pane",
            QUARTZ_GLASS_FRAMED_PANE,
            true
    );

    // Quartz Glass Colored //
            QUARTZ_GLASS_COLORED = new ColoredGlassMaterial(
            "quartz_glass",
            QUARTZ_GLASS
    );

    // Obsidian //
            BLUE_WEEPING_OBSIDIAN = registerBlock(
            "blue_weeping_obsidian",
            new BlueWeepingObsidianBlock()
    );

            WEEPING_OBSIDIAN = registerBlock(
            "weeping_obsidian",
            new VanillaWeepingObsidianBlock()
    );

            BLUE_CRYING_OBSIDIAN = registerBlock(
            "blue_crying_obsidian",
            new BlueCryingObsidianBlock()
    );

            OBSIDIAN_BRICKS = registerBlock(
            "obsidian_bricks",
            new BNObsidian()
    );

            OBSIDIAN_BRICKS_STAIRS = registerStairs(
            "obsidian_bricks_stairs",
            OBSIDIAN_BRICKS,
            false
    );

            OBSIDIAN_BRICKS_SLAB = registerSlab(
            "obsidian_bricks_slab",
            OBSIDIAN_BRICKS,
            false
    );

            OBSIDIAN_TILE = registerBlock(
            "obsidian_tile",
            new BNObsidian()
    );

            OBSIDIAN_TILE_SMALL = registerBlock(
            "obsidian_tile_small",
            new BNObsidian()
    );

            OBSIDIAN_TILE_STAIRS = registerStairs(
            "obsidian_tile_stairs",
            OBSIDIAN_TILE_SMALL,
            false
    );

            OBSIDIAN_TILE_SLAB = registerSlab(
            "obsidian_tile_slab",
            OBSIDIAN_TILE_SMALL,
            false
    );

            OBSIDIAN_ROD_TILES = registerBlock(
            "obsidian_rod_tiles",
            new BNObsidian()
    );

            OBSIDIAN_GLASS = registerBlock(
            "obsidian_glass",
            new BlockObsidianGlass()
    );

            OBSIDIAN_GLASS_PANE = registerBlock(
            "obsidian_glass_pane",
            new BNPane.Glass(OBSIDIAN_GLASS, true)
    );

            BLUE_OBSIDIAN = registerBlock(
            "blue_obsidian",
            new BNObsidian(BLUE_CRYING_OBSIDIAN)
    );

            BLUE_OBSIDIAN_BRICKS = registerBlock(
            "blue_obsidian_bricks",
            new BNObsidian()
    );

            BLUE_OBSIDIAN_BRICKS_STAIRS = registerStairs(
            "blue_obsidian_bricks_stairs",
            BLUE_OBSIDIAN_BRICKS,
            false
    );

            BLUE_OBSIDIAN_BRICKS_SLAB = registerSlab(
            "blue_obsidian_bricks_slab",
            BLUE_OBSIDIAN_BRICKS,
            false
    );

            BLUE_OBSIDIAN_TILE = registerBlock(
            "blue_obsidian_tile",
            new BNObsidian()
    );

            BLUE_OBSIDIAN_TILE_SMALL = registerBlock(
            "blue_obsidian_tile_small",
            new BNObsidian()
    );

            BLUE_OBSIDIAN_TILE_STAIRS = registerStairs(
            "blue_obsidian_tile_stairs",
            BLUE_OBSIDIAN_TILE_SMALL,
            false
    );

            BLUE_OBSIDIAN_TILE_SLAB = registerSlab(
            "blue_obsidian_tile_slab",
            BLUE_OBSIDIAN_TILE_SMALL,
            false
    );

            BLUE_OBSIDIAN_ROD_TILES = registerBlock(
            "blue_obsidian_rod_tiles",
            new BNObsidian()
    );

            BLUE_OBSIDIAN_GLASS = registerBlock(
            "blue_obsidian_glass",
            new BlockObsidianGlass()
    );

            BLUE_OBSIDIAN_GLASS_PANE = registerBlock(
            "blue_obsidian_glass_pane",
            new BNPane.Glass(BLUE_OBSIDIAN_GLASS, true)
    );

    // Soul Sandstone //
            SOUL_SANDSTONE = registerMakeable2X2Soul(
            "soul_sandstone",
            new BlockSoulSandstone(),
            "soul_sandstone",
            RecipeCategory.BUILDING_BLOCKS,
            Blocks.SOUL_SAND
    );

            SOUL_SANDSTONE_CUT = registerMakeable2X2Soul(
            "soul_sandstone_cut",
            new BlockSoulSandstone(),
            "soul_sandstone",
            RecipeCategory.BUILDING_BLOCKS,
            SOUL_SANDSTONE
    );

            SOUL_SANDSTONE_CUT_STAIRS = registerStairs(
            "soul_sandstone_cut_stairs",
            SOUL_SANDSTONE_CUT,
            false,
            BlockTags.SOUL_SPEED_BLOCKS,
            BlockTags.SOUL_FIRE_BASE_BLOCKS
    );

            SOUL_SANDSTONE_CUT_SLAB = registerSlab(
            "soul_sandstone_cut_slab",
            SOUL_SANDSTONE_CUT,
            false
    );

            SOUL_SANDSTONE_WALL = registerWall("soul_sandstone_wall", SOUL_SANDSTONE_CUT);

            SOUL_SANDSTONE_SMOOTH = registerSoulBlock(
            "soul_sandstone_smooth",
            new BlockBase.Stone(Blocks.SANDSTONE)
    );

            SOUL_SANDSTONE_CHISELED = registerMakeable2X2Soul(
            "soul_sandstone_chiseled",
            new BlockBase.Stone(Blocks.SANDSTONE),
            "soul_sandstone",
            RecipeCategory.BUILDING_BLOCKS,
            SOUL_SANDSTONE_SMOOTH
    );

            SOUL_SANDSTONE_STAIRS = registerStairs(
            "soul_sandstone_stairs",
            SOUL_SANDSTONE,
            false,
            BlockTags.SOUL_SPEED_BLOCKS,
            BlockTags.SOUL_FIRE_BASE_BLOCKS
    );

            SOUL_SANDSTONE_SMOOTH_STAIRS = registerStairs(
            "soul_sandstone_smooth_stairs",
            SOUL_SANDSTONE_SMOOTH,
            false,
            BlockTags.SOUL_SPEED_BLOCKS,
            BlockTags.SOUL_FIRE_BASE_BLOCKS
    );

            SOUL_SANDSTONE_SLAB = registerSlab("soul_sandstone_slab", SOUL_SANDSTONE, false);

            SOUL_SANDSTONE_SMOOTH_SLAB = registerSlab(
            "soul_sandstone_smooth_slab",
            SOUL_SANDSTONE_SMOOTH,
            false
    );

    // Basalt Bricks //
            BASALT_BRICKS = registerMakeable2X2(
            "basalt_bricks",
            new BlockBase.Stone(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)),
            "basalt_bricks",
            RecipeCategory.BUILDING_BLOCKS,
            Blocks.POLISHED_BASALT
    );

            BASALT_BRICKS_STAIRS = registerStairs("basalt_bricks_stairs", BASALT_BRICKS, true);

            BASALT_BRICKS_SLAB = registerSlab("basalt_bricks_slab", BASALT_BRICKS, true);

            BASALT_BRICKS_WALL = registerWall("basalt_bricks_wall", BASALT_BRICKS);

            BASALT_SLAB = registerSlab(
            "basalt_slab",
            Blocks.BASALT,
            false
    );

            ORANGE_MUSHROOM = registerBlock("orange_mushroom", new BlockOrangeMushroom());

            RED_MOLD = registerBlock("red_mold", new BlockRedMold());

            GRAY_MOLD = registerBlock("gray_mold", new BlockGrayMold());

            LUCIS_SPORE = registerBlock("lucis_spore", new BlockLucisSpore());

            GIANT_LUCIS = registerBlock("giant_lucis", new BlockGiantLucis());

            GIANT_MOLD_SAPLING = registerBlock("giant_mold_sapling", new BlockGiantMoldSapling());

            JELLYFISH_MUSHROOM_SAPLING = registerBlock(
            "jellyfish_mushroom_sapling",
            new BlockJellyfishMushroomSapling()
    );

            EYE_SEED = registerBlock("eye_seed", new BlockEyeSeed());

    // Grass //
            NETHER_GRASS = registerBlock("nether_grass", new BlockNetherGrass.NetherGrass());

            SWAMP_GRASS = registerBlock("swamp_grass", new BlockNetherGrass.SwampGrass());

            SOUL_GRASS = registerBlock("soul_grass", new BlockSoulGrass());

            JUNGLE_PLANT = registerBlock("jungle_plant", new BlockNetherGrass.JunglePlant());

            BONE_GRASS = registerBlock("bone_grass", new BlockNetherGrass.BoneGrass());

            SEPIA_BONE_GRASS = registerBlock("sepia_bone_grass", new BlockNetherGrass.SepiaBoneGrass());

    // Vines //
            BLACK_VINE = registerBlock(
            "black_vine",
            new BlockBlackVine()

    );

            BLOOMING_VINE = registerBlock(
            "blooming_vine",
            new BlockBlackVine()
    );

            GOLDEN_VINE = registerBlock(
            "golden_vine",
            new BlockGoldenVine()
    );


            LUMABUS_VINE = registerBlockNI("lumabus_vine", new BlockLumabusVine(MapColor.COLOR_CYAN));

            GOLDEN_LUMABUS_VINE = registerBlockNI(
            "golden_lumabus_vine",
            new BlockLumabusVine(MapColor.COLOR_YELLOW)
    );


    // Small Plants
            SOUL_VEIN = registerBlock("soul_vein", new BlockSoulVein());

            BONE_MUSHROOM = registerBlock("bone_mushroom", new BlockBoneMushroom());

            BLACK_BUSH = registerBlock("black_bush", new BlockBlackBush());

            INK_BUSH = registerBlockNI("ink_bush", new BlockInkBush());

            INK_BUSH_SEED = registerBlock("ink_bush_seed", new BlockInkBushSeed());

            SMOKER = registerBlock("smoker", new BlockSmoker());

            EGG_PLANT = registerBlock("egg_plant", new BlockEggPlant());

            BLACK_APPLE = registerBlockNI("black_apple", new BlockBlackApple());

            BLACK_APPLE_SEED = registerBlock("black_apple_seed", new BlockBlackAppleSeed());

            MAGMA_FLOWER = registerBlock("magma_flower", new BlockMagmaFlower());

            FEATHER_FERN = registerBlock("feather_fern", new BlockFeatherFern());

            MOSS_COVER = registerBlock("moss_cover", new BlockMossCover());

            NEON_EQUISETUM = registerBlock("neon_equisetum", new BlockNeonEquisetum());

            HOOK_MUSHROOM = registerBlock("hook_mushroom", new BlockHookMushroom());

            WHISPERING_GOURD_VINE = registerBlock(
            "whispering_gourd_vine",
            new BlockWhisperingGourdVine()
    );

            WHISPERING_GOURD = registerBlock("whispering_gourd", new BlockWhisperingGourd());

            WHISPERING_GOURD_LANTERN = registerBlock(
            "whispering_gourd_lantern",
            new BlockWhisperingGourdLantern()
    );

    // Cactuses //
            AGAVE = registerBlock("agave", new BlockAgave());

            BARREL_CACTUS = registerBlock("barrel_cactus", new BlockBarrelCactus());

            NETHER_CACTUS = registerBlock("nether_cactus", new BlockNetherCactus());

    // Wall plants
            WALL_MOSS = registerBlock("wall_moss", new BlockPlantWall(MapColor.COLOR_RED));

            WALL_MUSHROOM_BROWN = registerBlock(
            "wall_mushroom_brown",
            new BlockPlantWall(MapColor.COLOR_BROWN)
    );

            WALL_MUSHROOM_RED = registerBlock(
            "wall_mushroom_red",
            new BlockPlantWall(MapColor.COLOR_RED)
    );

            JUNGLE_MOSS = registerBlock(
            "jungle_moss",
            new BlockPlantWall(MapColor.COLOR_LIGHT_GREEN)
    );

    // Decorations //
            PIG_STATUE_RESPAWNER = registerBlock("pig_statue_respawner", new BlockStatueRespawner());

            CINCINNASITE_POT = registerBlock(
            "cincinnasite_pot",
            new BlockBNPot.Metal(CINCINNASITE_BLOCK)
    );

            BRICK_POT = registerBlock("brick_pot", new BlockBNPot.Stone(Blocks.NETHER_BRICKS));

            GEYSER = registerBlock("geyser", new BlockGeyser());

            NETHERRACK_STALACTITE = registerStalactite("netherrack_stalactite", Blocks.NETHERRACK);

            GLOWSTONE_STALACTITE = registerStalactite("glowstone_stalactite", Blocks.GLOWSTONE);

            BLACKSTONE_STALACTITE = registerStalactite("blackstone_stalactite", Blocks.BLACKSTONE);

            BASALT_STALACTITE = registerStalactite("basalt_stalactite", Blocks.BASALT);

            BONE_STALACTITE = registerStalactite("bone_stalactite", BONE_BLOCK);

    // Fire Bowls
            CINCINNASITE_FIRE_BOWL = registerFireBowl(
            "cincinnasite_fire_bowl",
            CINCINNASITE_FORGED,
            Blocks.NETHERRACK,
            NetherItems.CINCINNASITE_INGOT
    );

            BRICKS_FIRE_BOWL = registerFireBowl(
            "bricks_fire_bowl",
            NETHER_BRICK_TILE_LARGE,
            Blocks.NETHERRACK,
            Items.NETHER_BRICK
    );

            NETHERITE_FIRE_BOWL = registerFireBowl(
            "netherite_fire_bowl",
            Blocks.NETHERITE_BLOCK,
            Blocks.NETHERRACK,
            Items.NETHERITE_INGOT
    );

            CINCINNASITE_FIRE_BOWL_SOUL = registerFireBowl(
            "cincinnasite_fire_bowl_soul",
            CINCINNASITE_FORGED,
            Blocks.SOUL_SAND,
            NetherItems.CINCINNASITE_INGOT
    );

            BRICKS_FIRE_BOWL_SOUL = registerFireBowl(
            "bricks_fire_bowl_soul",
            NETHER_BRICK_TILE_LARGE,
            Blocks.SOUL_SAND,
            Items.NETHER_BRICK
    );

            NETHERITE_FIRE_BOWL_SOUL = registerFireBowl(
            "netherite_fire_bowl_soul",
            Blocks.NETHERITE_BLOCK,
            Blocks.SOUL_SAND,
            Items.NETHERITE_INGOT
    );

    // Terrain //
            NETHERRACK_MOSS = registerBlock(
            "netherrack_moss",
            new BlockTerrain(),
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK
    );

            NETHER_MYCELIUM = registerBlock(
            "nether_mycelium",
            new BlockNetherMycelium(),
            BlockTags.NYLIUM,
            CommonBlockTags.MYCELIUM,
            CommonBlockTags.NETHER_MYCELIUM,
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK,
            org.betterx.wover.tag.api.predefined.CommonBlockTags.NETHER_MYCELIUM
    );

            JUNGLE_GRASS = registerBlock(
            "jungle_grass",
            new BlockTerrain(),
            BlockTags.NYLIUM,
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK
    );

            MUSHROOM_GRASS = registerBlock(
            "mushroom_grass",
            new BlockTerrain(),
            BlockTags.NYLIUM,
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK
    );

            SEPIA_MUSHROOM_GRASS = registerBlock(
            "sepia_mushroom_grass",
            new BlockTerrain(),
            BlockTags.NYLIUM,
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK
    );

            SWAMPLAND_GRASS = registerBlock(
            "swampland_grass",
            new BlockTerrain(),
            BlockTags.NYLIUM,
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK
    );

            FARMLAND = registerBlock(
            "farmland",
            new BlockFarmland()
    );

            CEILING_MUSHROOMS = registerBlock(
            "ceiling_mushrooms",
            new BlockTerrain(),
            BCLBlockTags.BONEMEAL_SOURCE_NETHERRACK
    );

    // Roofs //
            ROOF_TILE_NETHER_BRICKS = registerRoof("roof_tile_nether_bricks", Blocks.NETHER_BRICKS);

            ROOF_TILE_NETHER_BRICKS_STAIRS = registerStairs(
            "roof_tile_nether_bricks_stairs",
            ROOF_TILE_NETHER_BRICKS,
            false
    );

            ROOF_TILE_NETHER_BRICKS_SLAB = registerSlab(
            "roof_tile_nether_bricks_slab",
            ROOF_TILE_NETHER_BRICKS,
            false
    );

            ROOF_TILE_CINCINNASITE = registerRoof("roof_tile_cincinnasite", CINCINNASITE_FORGED);

            ROOF_TILE_CINCINNASITE_STAIRS = registerStairs(
            "roof_tile_cincinnasite_stairs",
            ROOF_TILE_CINCINNASITE,
            false
    );

            ROOF_TILE_CINCINNASITE_SLAB = registerSlab(
            "roof_tile_cincinnasite_slab",
            ROOF_TILE_CINCINNASITE,
            false
    );

    // Craft Stations //
            BLACKSTONE_FURNACE = registerFurnace("blackstone_furnace", Blocks.BLACKSTONE);

            BASALT_FURNACE = registerFurnace("basalt_furnace", Blocks.BASALT);

            NETHERRACK_FURNACE = registerFurnace("netherrack_furnace", Blocks.NETHERRACK);

            CINCINNASITE_FORGE = registerBlock("cincinnasite_forge", new BlockCincinnasiteForge());

            NETHER_BREWING_STAND = registerBlock("nether_brewing_stand", new BNBrewingStand(), CommonPoiTags.CLERIC_WORKSTATION);

            CINCINNASITE_ANVIL = registerBlock(
            "cincinnasite_anvil",
            new BlockCincinnasiteAnvil(),
            BlockTags.ANVIL
    );


            WARPED_WOOD = new VanillaNetherWood(
            "warped",
            Blocks.WARPED_PLANKS.defaultMapColor(),
            MapColor.WARPED_STEM
    ).setFurnitureCloth(Blocks.RED_WOOL).init();


            CRIMSON_WOOD = new VanillaNetherWood(
            "crimson",
            Blocks.CRIMSON_PLANKS.defaultMapColor(),
            MapColor.CRIMSON_STEM
    ).setFurnitureCloth(Blocks.RED_WOOL).init();


            OAK_WOOD = VanillaWood.create("oak", Blocks.RED_WOOL);

            SPRUCE_WOOD = VanillaWood.create("spruce", Blocks.RED_WOOL);

            BIRCH_WOOD = VanillaWood.create("birch", Blocks.RED_WOOL);

            JUNGLE_WOOD = VanillaWood.create("jungle", Blocks.RED_WOOL);

            ACACIA_WOOD = VanillaWood.create("acacia", Blocks.BLACK_WOOL);

            DARK_OAK_WOOD = VanillaWood.create("dark_oak", Blocks.RED_WOOL);

            CHERRY_WOOD = VanillaWood.create("cherry", Blocks.WHITE_WOOL);

            BAMBOO_WOOD = VanillaWood.create("bamboo", Blocks.BROWN_WOOL);

            MANGROVE_WOOD = VanillaWood.create("mangrove", Blocks.BLACK_WOOL);

    // Storage
            CHEST_OF_DRAWERS = registerBlock("chest_of_drawers", new BlockChestOfDrawers());

    // Rubeus //
            MAT_RUBEUS = new RubeusMaterial().init();

            RUBEUS_LEAVES = registerBlock(
            "rubeus_leaves",
            new BlockRubeusLeaves(MAT_RUBEUS.getSapling())
    );

    // Mushroom Fir //
            MAT_MUSHROOM_FIR = new MushroomFirMaterial().init();

            TRIMMED_MUSHROOM_FIR_CHEST = registerBlock(
            "mushroom_fir_trimmed_chest",
            new BaseChestBlock.Wood(MAT_MUSHROOM_FIR.getBlock(WoodSlots.PLANKS))
    );

    // Mushroom //
            MAT_NETHER_MUSHROOM = new NetherMushroomMaterial().init();

    // Anchor Tree
            MAT_ANCHOR_TREE = new AnchorTreeMaterial().init();

            ANCHOR_TREE_LEAVES = registerBlock(
            "anchor_tree_leaves",
            new BNLeaves(
                    MAT_ANCHOR_TREE.getSapling(),
                    MapColor.COLOR_GREEN
            )
    );

            ANCHOR_TREE_VINE = registerBlockNI(
            "anchor_tree_vine",
            new BlockAnchorTreeVine()
    );

    // Nether Sakura
            MAT_NETHER_SAKURA = new NetherSakuraMaterial().init();

            NETHER_SAKURA_LEAVES = registerBlock(
            "nether_sakura_leaves",
            new BlockNetherSakuraLeaves(MAT_NETHER_SAKURA.getSapling())
    );

    // Soul lily //
            SOUL_LILY = registerBlockNI("soul_lily", new BlockSoulLily());

            SOUL_LILY_SAPLING = registerBlock("soul_lily_sapling", new BlockSoulLilySapling());

    // Large & Small Mushrooms //
            RED_LARGE_MUSHROOM = registerBlockNI("red_large_mushroom", new BlockRedLargeMushroom());

            BROWN_LARGE_MUSHROOM = registerBlockNI(
            "brown_large_mushroom",
            new BlockBrownLargeMushroom()
    );

    // Lucis //
            LUCIS_MUSHROOM = registerBlockNI("lucis_mushroom", new BlockLucisMushroom());

    // Giant Mold //
            GIANT_MOLD = registerBlockNI("giant_mold", new BlockGiantMold());

            JELLYFISH_MUSHROOM = registerBlockNI("jellyfish_mushroom", new BlockJellyfishMushroom());

    // Eyes //
            EYEBALL = registerBlockNI("eyeball", new BlockEyeball());

            EYEBALL_SMALL = registerBlockNI("eyeball_small", new BlockEyeballSmall());

            EYE_VINE = registerBlockNI(
            "eye_vine",
            new BlockEyeVine()
    );


            POTTED_PLANT = registerBlockNI("potted_plant", new BlockPottedPlant());

            VEINED_SAND = registerBlockNI(
            "veined_sand",
            new BlockVeinedSand(),
            NetherTags.NETHER_SAND
    );


            NETHERRACK_SLAB = registerSlab("netherrack_slab", Blocks.NETHERRACK, true);

            NETHERRACK_STAIR = registerStairs("netherrack_stairs", Blocks.NETHERRACK, true);

            NETHERRACK_WALLS = registerWall("netherrack_wall", Blocks.NETHERRACK);



    // DEFERED BLOCKS //
            LUMABUS_SEED = registerBlock(
            "lumabus_seed",
            new BlockLumabusSeed(LUMABUS_VINE, () -> NetherVines.LUMABUS_VINE.getHolder(WorldState.registryAccess()))
    );


            GOLDEN_LUMABUS_SEED = registerBlock(
            "golden_lumabus_seed",
            new BlockLumabusSeed(GOLDEN_LUMABUS_VINE, () -> NetherVines.GOLDEN_LUMABUS_VINE.getHolder(WorldState.registryAccess()))
    );

    }

    public static Stream<Block> getModBlocks() {
        return getBlockRegistry().allBlocks();
    }

    public static Stream<BlockItem> getModBlockItems() {
        return getBlockRegistry().allBlockItems();
    }

    @SafeVarargs
    public static <T extends Block> T registerBlock(String name, T block, TagKey<Block>... tags) {
        registerBlockDirectly(name, block, tags);
        return block;
    }

    @SafeVarargs
    private static <B extends Block> B registerBlockNI(String name, B block, TagKey<Block>... tags) {
        return registerBlock(name, block, false, tags);
    }

    @SafeVarargs
    public static void registerBlockDirectly(String name, Block block, TagKey<Block>... tags) {
        registerBlock(name, block, true, tags);
    }

    @SafeVarargs
    private static <B extends Block> B registerBlock(String name, B block, boolean hasItem, TagKey<Block>... tags) {
        final BlockRegistry blockRegistry = getBlockRegistry();
        if (hasItem) {
            blockRegistry.register(name, block, tags);
        } else {
            blockRegistry.registerBlockOnly(name, block, tags);
        }

        return block;
    }

    private static void addFuel(Block source, Block result) {
        // NeoForge fuel handling happens elsewhere; keep no-op to avoid Fabric dependency
    }

    @SafeVarargs
    public static Block registerStairs(
            String name,
            Block source,
            boolean fireproof,
            TagKey<Block>... tags
    ) {
        Block stairs = BaseStairsBlock.from(source, fireproof);

        registerBlockDirectly(name, stairs, tags);
        if (stairs.defaultBlockState().ignitedByLava())
            addFuel(source, stairs);
        if (ModCore.isDatagen())
            RecipesHelper.makeStairsRecipe(source, stairs);

        return stairs;
    }

    @SafeVarargs
    public static Block registerSlab(String name, Block source, boolean fireproof, TagKey<Block>... tags) {
        Block slab = BaseSlabBlock.from(source, fireproof);

        registerBlockDirectly(name, slab, tags);
        if (slab.defaultBlockState().ignitedByLava())
            addFuel(source, slab);
        if (ModCore.isDatagen())
            RecipesHelper.makeSlabRecipe(source, slab);

        return slab;
    }

    private static Block registerRoof(String name, Block source) {
        Block roof = BlockBase.from(source);

        registerBlockDirectly(name, roof);
        addFuel(source, roof);
        if (ModCore.isDatagen())
            RecipesHelper.makeRoofRecipe(source, roof);

        return roof;
    }

    public static Block registerButton(String name, Block source, BlockSetType type) {
        Block button = BaseButtonBlock.from(source, type);

        registerBlockDirectly(name, button);
        addFuel(source, button);
        if (ModCore.isDatagen())
            RecipesHelper.makeButtonRecipe(source, button);

        return button;
    }

    public static Block registerPlate(String name, Block source, BlockSetType type) {
        Block plate = BasePressurePlateBlock.from(source, type);

        registerBlockDirectly(name, plate);
        addFuel(source, plate);
        if (ModCore.isDatagen())
            RecipesHelper.makePlateRecipe(source, plate);

        return plate;
    }


    public static Block registerSoulBlock(String name, Block block) {
        return registerBlock(
                name,
                block,
                BlockTags.SOUL_FIRE_BASE_BLOCKS,
                BlockTags.SOUL_SPEED_BLOCKS
        );
    }

    public static Block registerMakeable2X2Soul(
            String name,
            Block result,
            String group,
            RecipeCategory category,
            Block source
    ) {
        final Block block = registerMakeable2X2(name, result, group, category, source,
                BlockTags.SOUL_FIRE_BASE_BLOCKS, BlockTags.SOUL_SPEED_BLOCKS
        );
        ;
        return block;
    }

    public static Block registerMakeable2X2(
            String name,
            Block result,
            String group,
            RecipeCategory category,
            Block source,
            TagKey<Block>... tags
    ) {

        registerBlockDirectly(name, result, tags);
        if (ModCore.isDatagen())
            RecipesHelper.makeSimpleRecipe2(source, result, 4, group, category);

        return result;
    }

    public static Block registerWall(String name, Block source) {
        Block wall = BaseWallBlock.from(source);

        registerBlockDirectly(name, wall, BlockTags.WALLS);
        if (ModCore.isDatagen())
            RecipesHelper.makeWallRecipe(source, wall);

        return wall;
    }


    public static Block registerTaburet(String name, Block source) {
        Block block = BaseTaburet.from(source);

        registerBlockDirectly(name, block, BlockTags.MINEABLE_WITH_AXE);
        addFuel(source, block);
        RecipesHelper.addProvider(ctx -> Taburet.makeTaburetRecipe(
                ctx.context,
                BetterNether.C.mk(name),
                block,
                source
        ));


        return block;
    }

    public static Block registerChair(String name, Block source) {
        Block block = BaseChair.from(source, NETHER_BRICK_TILE_LARGE);

        registerBlockDirectly(name, block, BlockTags.MINEABLE_WITH_AXE);
        addFuel(source, block);
        RecipesHelper.addProvider(ctx -> Chair.makeChairRecipe(
                ctx.context,
                BetterNether.C.mk(name),
                block,
                source
        ));


        return block;
    }

    public static Block registerBarStool(String name, Block source) {
        Block block = BaseBarStool.from(source, NETHER_BRICK_TILE_LARGE);

        registerBlockDirectly(name, block, BlockTags.MINEABLE_WITH_PICKAXE);
        addFuel(source, block);
        RecipesHelper.addProvider(ctx -> BarStool.makeBarStoolRecipe(
                ctx.context,
                BetterNether.C.mk(name),
                block,
                source
        ));


        return block;
    }

    public static Block registerFurnace(String name, Block source) {
        Block block = new BlockNetherFurnace(source);

        registerBlockDirectly(name, block, CommonPoiTags.ARMORER_WORKSTATION);
        if (ModCore.isDatagen())
            RecipesHelper.makeRoundRecipe(source, block, "nether_furnace", RecipeCategory.DECORATIONS);


        return block;
    }

    private static Block registerStalactite(String name, Block source) {
        Block block = new BlockStalactite(source);

        registerBlockDirectly(name, block);
        if (ModCore.isDatagen())
            RecipesHelper.makeSimpleRecipe2(block, source, 1, "nether_stalactite", RecipeCategory.DECORATIONS);


        return block;
    }

    private static Block registerFireBowl(String name, Block source, Block inside, Item leg) {
        final boolean isNetherite = name.startsWith("netherite");
        Block block = isNetherite ? new BlockFireBowl.Metal(source) : BlockFireBowl.from(source);

        registerBlockDirectly(name, block);
        if (!isNetherite) {
            RecipesHelper.makeFireBowlRecipe(source, inside, leg, block);
        }


        return block;
    }

    @ApiStatus.Internal
    public static void register() {
        //NO-OP; registration happens through BlockRegistry initializer
        getBlockRegistry();
    }
}
