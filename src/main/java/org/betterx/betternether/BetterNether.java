package org.betterx.betternether;

import org.betterx.bclib.BCLib;
import org.betterx.betternether.commands.CommandRegistry;
import org.betterx.betternether.config.Config;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.loot.BNLoot;
import org.betterx.betternether.registry.*;
import org.betterx.betternether.registry.features.configured.NetherVegetation;
import org.betterx.betternether.tab.BECreativeTabs;
import org.betterx.betternether.world.BNWorldGenerator;
import org.betterx.datagen.betternether.BetterNetherDatagen;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.state.api.WorldConfig;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(BetterNether.MOD_ID)
public class BetterNether {
    public static final String MOD_ID = "betternether";
    public static final ModCore C = ModCore.create(MOD_ID);
    public static final ModCore VANILLA_HAMMERS = ModCore.create("vanilla-hammers");
    public static final ModCore VANILLA_EXCAVATORS = ModCore.create("vanillaexcavators");

    public static final Identifier VANILLA_HAMMERS_PACK = C.addDatapack(VANILLA_HAMMERS);
    public static final Identifier VANILLA_EXCAVATORS_PACK = C.addDatapack(VANILLA_EXCAVATORS);

    private static boolean lavafallParticles = true;
    private static float fogStart = 0.05F;
    private static float fogEnd = 0.5F;


    private void onDatagen() {
    }

    public BetterNether(IEventBus modBus) {
        C.registerDatapackListener(modBus);
        // Ensure registries are initialized before RegisterEvent listeners run.
        NetherBlocks.getBlockRegistry();
        NetherItems.getItemRegistry();
        modBus.addListener(SoundsRegistry::register);
        modBus.addListener(NetherEnchantments::register);
        modBus.addListener(RegisterEvent.class, NetherEntities::onRegister);
        modBus.addListener(RegisterEvent.class, NetherParticles::onRegister);
        modBus.addListener(RegisterEvent.class, NetherPoiTypes::onRegister);
        modBus.addListener(RegisterEvent.class, NetherFeatures::onRegister);
        modBus.addListener(net.neoforged.neoforge.registries.RegisterEvent.class, BlockEntitiesRegistry::register);
        modBus.addListener(RegisterEvent.class, this::ensureBlocksLoaded);
        modBus.addListener(RegisterEvent.class, this::ensureItemsLoaded);
        modBus.addListener(RegisterEvent.class, org.betterx.betternether.advancements.BNCriterion::onRegister);
        modBus.addListener(RegisterEvent.class, BECreativeTabs::onRegister);
        org.betterx.wover.block.api.BlockRegistry.hook(modBus);
        org.betterx.wover.item.api.ItemRegistry.hook(modBus);
        if (ModCore.isDatagen()) {
            BetterNetherDatagen datagen = new BetterNetherDatagen();
            modBus.addListener(net.neoforged.neoforge.data.event.GatherDataEvent.Client.class, datagen::onGatherData);
            modBus.addListener(net.neoforged.neoforge.data.event.GatherDataEvent.Server.class, datagen::onGatherData);
        }
        initialize();
    }

    private void ensureBlocksLoaded(RegisterEvent event) {
        if (event.getRegistryKey().equals(net.minecraft.core.registries.Registries.BLOCK)) {
            try {
                Class.forName("org.betterx.betternether.registry.NetherBlocks");
            } catch (ClassNotFoundException ignored) {
            }
        }
    }

    private void ensureItemsLoaded(RegisterEvent event) {
        if (event.getRegistryKey().equals(net.minecraft.core.registries.Registries.ITEM)) {
            try {
                Class.forName("org.betterx.betternether.registry.NetherItems");
            } catch (ClassNotFoundException ignored) {
            }
        }
    }

    private void initialize() {
        C.log.info("=^..^=    BetterNether Neoforged for 1.21    =^..^=");
        //MigrationProfile.fixCustomFolder(new File("/Users/frank/Entwicklung/BetterNether/src/main/resources/data/betternether/structures/lava"));

        initOptions();
        SoundsRegistry.ensureStaticallyLoaded();
        NetherEnchantments.ensureStaticallyLoaded();
        BlockEntitiesRegistry.register();
        NetherEntities.register();
        BNWorldGenerator.onModInit();
        NetherStructures.register();
        NetherBiomes.register();
        BrewingRegistry.register();
        CommandRegistry.register();
        Config.save();

        NetherTags.register();
        BNLoot.register();
        NetherVegetation.registerLifecycleHook();

        Configs.saveConfigs();
        WorldConfig.registerMod(C);
        if (BCLib.isClient()) {
            Patcher.register();
        }

        if (BCLib.isDatagen()) {
            onDatagen();
        }
    }

    private void initOptions() {
        lavafallParticles = Configs.CLIENT.lavafallParticles.get();
        float density = Configs.CLIENT.fogDensity.get();
        changeFogDensity(density);
    }

    public static boolean hasLavafallParticles() {
        return lavafallParticles;
    }

    public static void changeFogDensity(float density) {
        fogStart = -0.45F * density + 0.5F;
        fogEnd = -0.5F * density + 1;
    }

    public static float getFogStart() {
        return fogStart;
    }

    public static float getFogEnd() {
        return fogEnd;
    }

}
