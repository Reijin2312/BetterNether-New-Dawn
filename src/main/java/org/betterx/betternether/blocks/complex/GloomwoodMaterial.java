package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.complexmaterials.entry.SlotMap;
import org.betterx.bclib.complexmaterials.set.wood.AbstractSaplingSlot;
import org.betterx.bclib.complexmaterials.set.wood.WoodSlots;
import org.betterx.betternether.blocks.BlockGloomwoodSapling;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class GloomwoodMaterial extends NetherWoodenMaterial<GloomwoodMaterial> {
    public GloomwoodMaterial() {
        super("gloomwood", MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_WHITE);
        setFurnitureCloth(NetherBlocks.NETHER_BRICK_TILE_LARGE);
    }

    @Override
    protected SlotMap<WoodenComplexMaterial> createMaterialSlots() {
        return super.createMaterialSlots().add(AbstractSaplingSlot.create((material, properties) ->
                new BlockGloomwoodSapling(properties
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.GRASS))
        ));
    }

    public Block getSapling() {
        return getBlock(WoodSlots.SAPLING);
    }
}
