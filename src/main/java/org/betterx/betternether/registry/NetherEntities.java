package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.spawning.SpawnRuleBuilder;
import org.betterx.bclib.entity.BCLEntityWrapper;
import org.betterx.bclib.furniture.entity.EntityChair;
import org.betterx.bclib.interfaces.SpawnRule;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.*;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.biomes.util.NetherBiomeBuilder;
import org.betterx.ui.ColorUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = BetterNether.MOD_ID)
public class NetherEntities {
    public enum KnownSpawnTypes {
        GHAST(50, 4, 4, EntityTypes.GHAST),
        ZOMBIFIED_PIGLIN(100, 4, 4, EntityTypes.ZOMBIFIED_PIGLIN),
        MAGMA_CUBE(2, 4, 4, EntityTypes.MAGMA_CUBE),
        SKULL(2, 2, 4, NetherEntities.SKULL),
        ENDERMAN(1, 4, 4, EntityTypes.ENDERMAN),
        PIGLIN(15, 4, 4, EntityTypes.PIGLIN),
        STRIDER(60, 1, 2, EntityTypes.STRIDER),
        HOGLIN(9, 1, 2, EntityTypes.HOGLIN),
        FIREFLY(5, 1, 3, NetherEntities.FIREFLY),
        HYDROGEN_JELLYFISH(5, 2, 6, NetherEntities.HYDROGEN_JELLYFISH),
        NAGA(8, 3, 5, NetherEntities.NAGA),
        FLYING_PIG(20, 2, 4, NetherEntities.FLYING_PIG),
        JUNGLE_SKELETON(40, 2, 4, NetherEntities.JUNGLE_SKELETON),
        PIGLIN_BRUTE(0, 1, 1, EntityTypes.PIGLIN_BRUTE);

        public final int weight;
        public final int minGroupSize;
        public final int maxGroupSize;
        public EntityType type;
        public BCLEntityWrapper wrapper;

        public boolean isVanilla() {
            return wrapper == null;
        }

        public void addSpawn(NetherBiomeBuilder builder, NetherBiomeConfig data) {
            final int weight = data.spawnWeight(this);
            if (weight <= 0 || maxGroupSize <= 0) return;
            if (wrapper == null) {
                builder.spawn(this.type, weight, minGroupSize, maxGroupSize);
            } else {
                if (this.wrapper.canSpawn())
                    builder.spawn(this.wrapper.type(), weight, minGroupSize, maxGroupSize);
            }
        }

        KnownSpawnTypes(int w, int min, int max, EntityType type) {
            weight = w;
            minGroupSize = min;
            maxGroupSize = max;
            this.type = type;
            this.wrapper = null;
        }

        <M extends Mob> KnownSpawnTypes(int w, int min, int max, BCLEntityWrapper type) {
            weight = w;
            minGroupSize = min;
            maxGroupSize = max;
            this.type = type == null ? null : type.type();
            this.wrapper = type;
        }

        void setWrapper(BCLEntityWrapper wrapper) {
            this.wrapper = wrapper;
            if (wrapper != null) {
                this.type = wrapper.type();
            }
        }
    }

    public static final Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> ATTR_BUILDERS = Maps.newHashMap();
    private static final List<BCLEntityWrapper<?>> NETHER_ENTITIES = Lists.newArrayList();


    public static EntityType<EntityNagaProjectile> NAGA_PROJECTILE;
    public static EntityType<EntityChair> LEGACY_CHAIR;

    public static BCLEntityWrapper<EntityFirefly> FIREFLY;

    public static BCLEntityWrapper<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH;

    public static BCLEntityWrapper<EntityNaga> NAGA;

    public static BCLEntityWrapper<EntityFlyingPig> FLYING_PIG;

    public static BCLEntityWrapper<EntityJungleSkeleton> JUNGLE_SKELETON;

    public static BCLEntityWrapper<EntitySkull> SKULL;


    private static <T extends Mob> BCLEntityWrapper<T> register(
            String name,
            MobCategory group,
            float width,
            float height,
            EntityFactory<T> entity,
            Builder attributes,
            boolean fixedSize,
            int eggColor,
            int dotsColor
    ) {
        Identifier id = BetterNether.C.id(name);
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        EntityType<T> type = EntityType.Builder.of(entity, group)
                                               .sized(width, height)
                                               .fireImmune() //Nether Entities are by default immune to fire
                                               .build(key);
        ATTR_BUILDERS.put(type, attributes);
        NetherItems.makeEgg("spawn_egg_" + name, type, eggColor, dotsColor);


        return new BCLEntityWrapper<>(type, true);
    }

    private static boolean testSpawnAboveLava(LevelAccessor world, BlockPos pos, boolean allow) {
        int h = org.betterx.bclib.util.BlocksHelper.downRay(world, pos, MAX_FLOAT_HEIGHT + 2);
        if (h > MAX_FLOAT_HEIGHT) return false;

        for (int i = 1; i <= h + 1; i++)
            if (org.betterx.bclib.util.BlocksHelper.isLava(world.getBlockState(pos.below(i))))
                return allow;

        return !allow;
    }

    public static final int MAX_FLOAT_HEIGHT = 7;
    public static final SpawnRule RULE_FLOAT_NOT_ABOVE_LAVA = (type, world, spawnReason, pos, random) -> testSpawnAboveLava(
            world,
            pos,
            false
    );
    public static final SpawnRule RULE_FLOAT_ABOVE_LAVA = (type, world, spawnReason, pos, random) -> testSpawnAboveLava(
            world,
            pos,
            true
    );


    public static void register() {
        setupSpawnRules();
    }

    public static void registerEntity(String name, EntityType<? extends LivingEntity> entity) {
        registerEntity(name, entity, Mob.createMobAttributes());
    }

    public static void registerEntity(
            String name,
            EntityType<? extends LivingEntity> entity,
            AttributeSupplier.Builder builder
    ) {
        ATTR_BUILDERS.put(entity, builder);
    }

    public static boolean isNetherEntity(Entity entity) {
        return NETHER_ENTITIES.contains(entity.getType());
    }

    @SubscribeEvent
    public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        ATTR_BUILDERS.forEach((type, builder) -> event.put(type, builder.build()));
    }

    private static void setupSpawnRules() {
        if (FIREFLY == null || HYDROGEN_JELLYFISH == null || NAGA == null || FLYING_PIG == null || JUNGLE_SKELETON == null || SKULL == null) {
            return;
        }

        SpawnRuleBuilder
                .start(FIREFLY)
                .belowMaxHeight()
                .customRule(RULE_FLOAT_NOT_ABOVE_LAVA)
                .maxNearby(32, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder
                .start(HYDROGEN_JELLYFISH)
                .belowMaxHeight()
                .maxNearby(24, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING);

        SpawnRuleBuilder
                .start(NAGA)
                .hostile(8)
                .maxNearby(32, 64)
                .buildOnGround(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder
                .start(FLYING_PIG)
                .belowMaxHeight()
                .customRule(RULE_FLOAT_NOT_ABOVE_LAVA)
                .maxNearby(16, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING);

        SpawnRuleBuilder
                .start(JUNGLE_SKELETON)
                .notPeaceful()
                .maxNearby(16, 64)
                .buildOnGround(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder
                .start(SKULL)
                .belowMaxHeight()
                .vanillaHostile()
                .maxNearby(16, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING);
    }

    public static void onRegister(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ENTITY_TYPE)) return;

        event.register(Registries.ENTITY_TYPE, helper -> {
            // Legacy compatibility: old worlds may still contain this entity id.
            LEGACY_CHAIR = EntityType.Builder
                    .of(EntityChair::new, MobCategory.MISC)
                    .sized(0.5F, 0.8F)
                    .fireImmune()
                    .noSummon()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, BetterNether.C.mk("chair")));
            helper.register(BetterNether.C.mk("chair"), LEGACY_CHAIR);

            NAGA_PROJECTILE = EntityType.Builder
                    .of(EntityNagaProjectile::new, MobCategory.MISC)
                    .sized(1F, 1F)
                    .noSummon()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, BetterNether.C.mk("naga_projectile")));
            helper.register(BetterNether.C.mk("naga_projectile"), NAGA_PROJECTILE);
            ATTR_BUILDERS.put(NAGA_PROJECTILE, Mob.createMobAttributes());

            FIREFLY = register(
                    "firefly",
                    MobCategory.AMBIENT,
                    0.5f,
                    0.5f,
                    EntityFirefly::new,
                    EntityFirefly.createMobAttributes(),
                    true,
                    ColorUtil.color(255, 223, 168),
                    ColorUtil.color(233, 182, 95)
            );
            helper.register(BetterNether.C.mk("firefly"), FIREFLY.type());

            HYDROGEN_JELLYFISH = register(
                    "hydrogen_jellyfish",
                    MobCategory.AMBIENT,
                    2.0f,
                    5.0f,
                    EntityHydrogenJellyfish::new,
                    EntityHydrogenJellyfish.createMobAttributes(),
                    false,
                    ColorUtil.color(253, 164, 24),
                    ColorUtil.color(88, 21, 4)
            );
            helper.register(BetterNether.C.mk("hydrogen_jellyfish"), HYDROGEN_JELLYFISH.type());

            NAGA = register(
                    "naga",
                    MobCategory.MONSTER,
                    0.625f,
                    2.75f,
                    EntityNaga::new,
                    EntityNaga.createMobAttributes(),
                    true,
                    ColorUtil.color(12, 12, 12),
                    ColorUtil.color(210, 90, 26)
            );
            helper.register(BetterNether.C.mk("naga"), NAGA.type());

            FLYING_PIG = register(
                    "flying_pig",
                    MobCategory.AMBIENT,
                    1.0f,
                    1.25f,
                    EntityFlyingPig::new,
                    EntityFlyingPig.createMobAttributes(),
                    true,
                    ColorUtil.color(241, 140, 93),
                    ColorUtil.color(176, 58, 47)
            );
            helper.register(BetterNether.C.mk("flying_pig"), FLYING_PIG.type());

            JUNGLE_SKELETON = register(
                    "jungle_skeleton",
                    MobCategory.MONSTER,
                    0.6F,
                    1.99F,
                    EntityJungleSkeleton::new,
                    EntityJungleSkeleton.createMonsterAttributes(),
                    true,
                    ColorUtil.color(134, 162, 149),
                    ColorUtil.color(6, 111, 79)
            );
            helper.register(BetterNether.C.mk("jungle_skeleton"), JUNGLE_SKELETON.type());

            SKULL = register(
                    "skull",
                    MobCategory.MONSTER,
                    0.625F,
                    0.625F,
                    EntitySkull::new,
                    EntitySkull.createMobAttributes(),
                    true,
                    ColorUtil.color(24, 19, 19),
                    ColorUtil.color(255, 28, 18)
            );
            helper.register(BetterNether.C.mk("skull"), SKULL.type());

            // Update spawn type references now that wrappers exist
            KnownSpawnTypes.SKULL.setWrapper(SKULL);
            KnownSpawnTypes.FIREFLY.setWrapper(FIREFLY);
            KnownSpawnTypes.HYDROGEN_JELLYFISH.setWrapper(HYDROGEN_JELLYFISH);
            KnownSpawnTypes.NAGA.setWrapper(NAGA);
            KnownSpawnTypes.FLYING_PIG.setWrapper(FLYING_PIG);
            KnownSpawnTypes.JUNGLE_SKELETON.setWrapper(JUNGLE_SKELETON);

            setupSpawnRules();
        });
    }
}
