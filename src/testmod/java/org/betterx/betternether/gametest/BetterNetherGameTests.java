package org.betterx.betternether.gametest;

import org.betterx.betternether.registry.NetherItems;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class BetterNetherGameTests implements ModInitializer {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    @Override
    public void onInitialize() {
        // The headless GameTest server enables every built-in pack, including optional compatibility
        // packs. Supply harmless stand-ins when their owning mods are absent so those packs can parse.
        registerOptionalItems("vanilla-hammers", List.of(
                "cincinnasite_hammer", "cincinnasite_hammer_diamond", "nether_ruby_hammer"
        ));
        registerOptionalItems("vanillaexcavators", List.of(
                "cincinnasite_excavator", "cincinnasite_excavator_diamond", "nether_ruby_excavator"
        ));
    }

    private static void registerOptionalItems(String modId, List<String> paths) {
        if (!FabricLoader.getInstance().isModLoaded(modId)) {
            paths.forEach(path -> NetherItems.registerItem(path, () -> new net.minecraft.world.item.Item(
                    NetherItems.defaultSettings()
            )));
        }
    }

    @GameTest
    public void correctedLootTablesProduceDrops(GameTestHelper helper) {
        List<String> vines = List.of("black_vine", "blooming_vine", "gloomsculk_vine");
        for (String id : List.of(
                "black_vine", "blooming_vine", "gloomsculk_vine", "bone_cincinnasite_door",
                "bone_reed_door", "gloomwood_transition_planks", "moss_cover",
                "soul_lily_sapling", "whispering_gourd_lantern"
        )) {
            Block block = block(id);
            helper.setBlock(POS.below(), Blocks.NETHERRACK);
            helper.setBlock(POS, block.defaultBlockState());
            List<ItemStack> drops = Block.getDrops(
                    block.defaultBlockState(), helper.getLevel(), helper.absolutePos(POS), null, null,
                    new ItemStack(vines.contains(id) ? Items.DIAMOND_HOE : Items.SHEARS)
            );
            helper.assertTrue(!drops.isEmpty(), id + " produced no drops through its live loot table");
        }
        helper.succeed();
    }

    @GameTest
    public void correctedVanillaAndModTagsAreLoaded(GameTestHelper helper) {
        assertTagged(helper, "brick_pot", "minecraft", "flower_pots");
        assertTagged(helper, "cincinnasite_pot", "minecraft", "flower_pots");
        assertTagged(helper, "bone_button", "minecraft", "stone_buttons");
        assertTagged(helper, "rubeus_bookshelf", "minecraft", "enchantment_power_provider");
        assertTagged(helper, "rubeus_sapling", "minecraft", "washed_away_by_fluids");
        helper.assertTrue(Blocks.CRIMSON_NYLIUM.defaultBlockState().is(tag("betternether", "nether_nylium")),
                "vanilla crimson nylium is absent from BetterNether's soil tag");
        helper.succeed();
    }

    @GameTest
    public void rubeusLeavesUseVanillaDecayState(GameTestHelper helper) {
        BlockState state = block("rubeus_leaves").defaultBlockState();
        helper.assertTrue(state.hasProperty(LeavesBlock.DISTANCE), "Rubeus leaves have no vanilla distance property");
        helper.assertTrue(state.hasProperty(LeavesBlock.PERSISTENT), "Rubeus leaves have no vanilla persistent property");
        helper.assertTrue(state.isRandomlyTicking(), "Rubeus leaves cannot run vanilla decay ticks");
        helper.succeed();
    }

    @GameTest
    public void supportSensitivePlantsRejectAir(GameTestHelper helper) {
        for (String id : List.of("red_mold", "potted_plant")) {
            Block block = block(id);
            helper.setBlock(POS.below(), Blocks.AIR);
            helper.setBlock(POS, block.defaultBlockState());
            helper.assertTrue(!block.defaultBlockState().canSurvive(helper.getLevel(), helper.absolutePos(POS)),
                    id + " survives with no supporting block");
        }
        Block cone = block("rubeus_cone");
        helper.setBlock(POS.above(), Blocks.AIR);
        helper.setBlock(POS, cone.defaultBlockState());
        helper.assertTrue(!cone.defaultBlockState().canSurvive(helper.getLevel(), helper.absolutePos(POS)),
                "rubeus_cone survives with no ceiling support");
        helper.succeed();
    }

    private static void assertTagged(GameTestHelper helper, String blockId, String namespace, String path) {
        helper.assertTrue(block(blockId).defaultBlockState().is(tag(namespace, path)),
                blockId + " is absent from #" + namespace + ":" + path);
    }

    private static TagKey<Block> tag(String namespace, String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(namespace, path));
    }

    private static Block block(String path) {
        Block block = BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath("betternether", path));
        if (block == Blocks.AIR) throw new IllegalStateException("Missing BetterNether block: " + path);
        return block;
    }
}
