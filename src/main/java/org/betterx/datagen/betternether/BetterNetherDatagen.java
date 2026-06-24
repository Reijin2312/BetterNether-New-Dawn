package org.betterx.datagen.betternether;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.NetherItems;
import org.betterx.datagen.betternether.advancements.NetherAdvancementDataProvider;
import org.betterx.datagen.betternether.enchantments.NetherEnchantmentProvider;
import org.betterx.datagen.betternether.enchantments.NetherEnchantmentTagProvider;
import org.betterx.datagen.betternether.entity.NetherEntityTypeTagProvider;
import org.betterx.datagen.betternether.presets.FlatLevelPresetsDataProvider;
import org.betterx.datagen.betternether.recipes.*;
import org.betterx.datagen.betternether.worldgen.NetherBiomeModificationProvider;
import org.betterx.datagen.betternether.worldgen.NetherBiomesProvider;
import org.betterx.datagen.betternether.worldgen.StructureDataProvider;
import org.betterx.datagen.betternether.worldgen.features.*;
import org.betterx.datagen.bclib.worldgen.BCLAutoBlockTagProvider;
import org.betterx.datagen.bclib.worldgen.BCLAutoItemTagProvider;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.PackBuilder;
import org.betterx.wover.datagen.api.WoverDataGenEntryPoint;
import org.betterx.wover.entrypoint.LibWoverTag;
import org.betterx.wover.tag.datagen.BiomeTagProvider;
import org.betterx.wover.tag.datagen.BlockTagProvider;
import org.betterx.wover.tag.datagen.ItemTagProvider;

import net.minecraft.core.RegistrySetBuilder;

public class BetterNetherDatagen extends WoverDataGenEntryPoint {
    @Override
    protected void onInitializeProviders(PackBuilder globalPack) {
        NetherItems.register();
        NetherBlocks.register();
        NetherFeatures.register();

        globalPack.addMultiProvider(NetherBiomesProvider::new);
        globalPack.addMultiProvider(ObjectFeatureDataProvider::new);
        globalPack.addMultiProvider(OreFeatureDataProvider::new);
        globalPack.addMultiProvider(TerrainFeatureDataProvider::new);
        globalPack.addMultiProvider(PlacedTreeFeatureDataProvider::new);
        globalPack.addMultiProvider(VegetationFeatureDataProvider::new);
        globalPack.addMultiProvider(VineFeatureDataProvider::new);
        globalPack.addMultiProvider(StructureDataProvider::new);
        globalPack.addRegistryProvider(NetherBiomeModificationProvider::new);
        globalPack.addRegistryProvider(FlatLevelPresetsDataProvider::new);
        globalPack.addProvider(NetherChestLootTableProvider::new);
        globalPack.addProvider(NetherEntityLootTableProvider::new);
        globalPack.addProvider(NetherEnchantmentProvider::new);
        globalPack.addProvider(modCore -> new BlockTagProvider(LibWoverTag.C));
        globalPack.addProvider(modCore -> new ItemTagProvider(LibWoverTag.C));
        globalPack.addProvider(modCore -> new BiomeTagProvider(LibWoverTag.C));
        globalPack.addProvider(BCLAutoBlockTagProvider::new);
        globalPack.addProvider(BCLAutoItemTagProvider::new);
        globalPack.addProvider(NetherBlockTagDataProvider::new);
        globalPack.addProvider(NetherItemTagDataProvider::new);
        globalPack.addProvider(NetherEnchantmentTagProvider::new);
        globalPack.addProvider(NetherEntityTypeTagProvider::new);
        globalPack.addProvider(NetherModelProvider::new);
        globalPack.addProvider(NetherBlockRecipesProvider::new);
        globalPack.addProvider(NetherItemRecipeProvider::new);
        globalPack.addProvider(NetherCraftingRecipes::new);
        globalPack.addProvider(NetherBlockLootTableProvider::new);
        globalPack.addProvider(modCore -> (output, registries) ->
                new NetherAdvancementDataProvider(output, registries));

        //Add providers for the vanilla hammers extension
        addDatapack(BetterNether.VANILLA_HAMMERS_PACK)
                .addProvider(VanillaHammersRecipes::new);

        //Add providers for the vanilla excavators extension
        addDatapack(BetterNether.VANILLA_EXCAVATORS_PACK)
                .addProvider(VanillaExcavatorsRecipes::new);
    }


    @Override
    protected void onBuildRegistry(RegistrySetBuilder registryBuilder) {
        super.onBuildRegistry(registryBuilder);
    }

    @Override
    protected ModCore modCore() {
        return BetterNether.C;
    }
}
