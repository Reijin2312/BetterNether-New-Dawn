package org.betterx.betternether.items.materials;

import org.betterx.wover.tag.api.TagManager;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public enum BNToolMaterial {
    CINCINNASITE(ToolMaterial.IRON.incorrectBlocksForDrops(), 2, 512, 6.2F, 2.5F, 16, "ingots/cincinnasite"),
    CINCINNASITE_DIAMOND(ToolMaterial.DIAMOND.incorrectBlocksForDrops(), 3, 2061, 8.2F, 3.7F, 14, "gems/diamond"),
    NETHER_RUBY(ToolMaterial.DIAMOND.incorrectBlocksForDrops(), 3, 2561, 7.1F, 3.1F, 18, "gems/nether_ruby"),
    FLAMING_RUBY(ToolMaterial.NETHERITE.incorrectBlocksForDrops(), 4, 2861, 10.4F, 6.0F, 32, "ingots/netherite");

    private final ToolMaterial toolMaterial;
    private final int level;
    public final TagKey<Block> incorrectBlocksForDrops;

    BNToolMaterial(
            TagKey<Block> incorrectBlocksForDrops,
            int level,
            int uses,
            float speed,
            float damage,
            int enchantibility,
            String repairTag
    ) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.level = level;
        this.toolMaterial = new ToolMaterial(
                incorrectBlocksForDrops,
                uses,
                speed,
                damage,
                enchantibility,
                TagManager.ITEMS.makeCommonTag(repairTag)
        );
    }

    public int getLevel() {
        return level;
    }

    public ToolMaterial toolMaterial() {
        return toolMaterial;
    }
}
