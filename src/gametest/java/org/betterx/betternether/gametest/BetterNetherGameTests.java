package org.betterx.betternether.gametest;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;


import java.util.List;
import java.util.function.Consumer;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

@EventBusSubscriber(modid = "betternether")
public final class BetterNetherGameTests {
    private static final String TEMPLATE = "altar_01";
    private static final BlockPos POS = new BlockPos(1, 2, 1);

    @SubscribeEvent
    public static void registerGameTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(
                id("upstream_regressions"), new TestEnvironmentDefinition.AllOf());
        register(event, environment, "corrected_loot_tables_produce_drops", BetterNetherGameTests::correctedLootTablesProduceDrops);
        register(event, environment, "corrected_vanilla_and_mod_tags_are_loaded", BetterNetherGameTests::correctedVanillaAndModTagsAreLoaded);
        register(event, environment, "rubeus_leaves_use_vanilla_decay_state", BetterNetherGameTests::rubeusLeavesUseVanillaDecayState);
        register(event, environment, "support_sensitive_plants_reject_air", BetterNetherGameTests::supportSensitivePlantsRejectAir);
    }

    private static void register(
            RegisterGameTestsEvent event,
            Holder<TestEnvironmentDefinition<?>> environment,
            String name,
            Consumer<GameTestHelper> test
    ) {
        TestData<Holder<TestEnvironmentDefinition<?>>> data = new TestData<>(environment, id(TEMPLATE), 200, 0, true);
        event.registerTest(id(name), new DirectTestInstance(data, test));
    }

    private static final class DirectTestInstance extends GameTestInstance {
        private final Consumer<GameTestHelper> test;

        private DirectTestInstance(TestData<Holder<TestEnvironmentDefinition<?>>> data, Consumer<GameTestHelper> test) {
            super(data);
            this.test = test;
        }

        @Override
        public void run(GameTestHelper helper) {
            test.accept(helper);
        }

        @Override
        public MapCodec<? extends GameTestInstance> codec() {
            return FunctionGameTestInstance.CODEC;
        }

        @Override
        protected MutableComponent typeDescription() {
            return Component.literal("betternether upstream regression");
        }
    }

    private BetterNetherGameTests() {
    }

    public static void correctedLootTablesProduceDrops(GameTestHelper helper) {
        List<String> vines = List.of("black_vine", "blooming_vine", "gloomsculk_vine");
        for (String id : List.of(
                "black_vine", "blooming_vine", "gloomsculk_vine", "bone_cincinnasite_door",
                "bone_reed_door", "gloomwood_transition_planks", "moss_cover",
                "soul_lily_sapling", "whispering_gourd_lantern"
        )) {
            Block block = block(id);
            helper.setBlock(POS, block.defaultBlockState());
            List<ItemStack> drops = Block.getDrops(
                    block.defaultBlockState(), helper.getLevel(), helper.absolutePos(POS), null, null,
                    new ItemStack(vines.contains(id) ? Items.DIAMOND_HOE : Items.SHEARS)
            );
            helper.assertTrue(!drops.isEmpty(), id + " produced no drops through its live loot table");
        }
        helper.succeed();
    }

    public static void correctedVanillaAndModTagsAreLoaded(GameTestHelper helper) {
        assertTagged(helper, "brick_pot", "minecraft", "flower_pots");
        assertTagged(helper, "cincinnasite_pot", "minecraft", "flower_pots");
        assertTagged(helper, "bone_button", "minecraft", "stone_buttons");
        assertTagged(helper, "rubeus_bookshelf", "minecraft", "enchantment_power_provider");
        assertTagged(helper, "rubeus_sapling", "minecraft", "washed_away_by_fluids");
        helper.assertTrue(Blocks.CRIMSON_NYLIUM.defaultBlockState().is(tag("betternether", "nether_nylium")),
                "vanilla crimson nylium is absent from BetterNether's soil tag");
        helper.succeed();
    }

    public static void rubeusLeavesUseVanillaDecayState(GameTestHelper helper) {
        BlockState state = block("rubeus_leaves").defaultBlockState();
        helper.assertTrue(state.hasProperty(LeavesBlock.DISTANCE), "Rubeus leaves have no vanilla distance property");
        helper.assertTrue(state.hasProperty(LeavesBlock.PERSISTENT), "Rubeus leaves have no vanilla persistent property");
        helper.assertTrue(state.isRandomlyTicking(), "Rubeus leaves cannot run vanilla decay ticks");
        helper.succeed();
    }

    public static void supportSensitivePlantsRejectAir(GameTestHelper helper) {
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

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath("betternether", path);
    }
}
