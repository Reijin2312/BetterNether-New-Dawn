package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BaseGlassBlock;
import org.betterx.betternether.BetterNether;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import org.betterx.wover.block.api.model.WoverBlockModelGeneratorsAccess;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;


import java.util.Optional;

public class BNGlass extends BaseGlassBlock {
    public BNGlass(Block block) {
        super(block, 0.3f);
    }

    @Override
    public void provideBlockModels(Object modelGenerator) {
        WoverBlockModelGenerators generators = (WoverBlockModelGenerators) modelGenerator;
        Identifier resource = TextureMapping.getBlockTexture(this).sprite();
        Identifier blockModel;
        if (!resource.getPath().equals("block/quartz_glass") && !resource
                .getPath()
                .equals("block/quartz_glass_framed")) {
            final var model = TexturedModel.CUBE.get(this);
            final var mapping = WoverBlockModelGenerators.textureMappingOf(
                    TextureSlot.ALL,
                    Identifier.fromNamespaceAndPath(
                            resource.getNamespace(),
                            resource
                                    .getPath()
                                    .replace("quartz_glass_", "quartz_stained_glass_")
                    )
            );

            final var loc = model.getTemplate()
                                 .create(this, mapping, generators.modelOutput());
            blockModel = loc;

            generators.acceptBlockState(
                    WoverBlockModelGeneratorsAccess.createSimpleBlock(
                            this,
                            loc
                    )
            );
        } else {
            generators.modelFor(TexturedModel.CUBE.get(this)).createFullBlock(this);
            blockModel = ModelLocationUtils.getModelLocation(this);
        }

        if (resource.getPath().equals("block/quartz_glass")) {
            final var mapping = WoverBlockModelGenerators.textureMappingOf(TextureSlot.ALL, BetterNether.C.mk("item/quartz_glass"));
            final var template = new ModelTemplate(Optional.of(ModelLocationUtils.getModelLocation(this)), Optional.empty(), TextureSlot.ALL);

            template.create(ModelLocationUtils.getModelLocation(this.asItem()), mapping, generators.modelOutput());
        } else {
            generators.delegateItemModel(this, blockModel);
        }
    }
}
