package org.betterx.betternether.blocks.complex.slots;

import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.complexmaterials.set.wood.AbstractSaplingSlot;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/** Anchor sapling slot; its two orientations are supplied by the dedicated blockstate resource. */
public class AnchorTreeSaplingSlot extends AbstractSaplingSlot {
    private final Function<BlockBehaviour.Properties, Block> maker;

    private AnchorTreeSaplingSlot(Function<BlockBehaviour.Properties, Block> maker) {
        this.maker = maker;
    }

    public static AnchorTreeSaplingSlot create(Function<BlockBehaviour.Properties, Block> maker) {
        return new AnchorTreeSaplingSlot(maker);
    }

    @Override
    protected @NotNull Block createBlock(
            WoodenComplexMaterial parentMaterial,
            BlockBehaviour.Properties settings
    ) {
        return maker.apply(settings);
    }
}
