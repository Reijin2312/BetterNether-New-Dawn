package org.betterx.betternether.blocks.complex;

import org.betterx.betternether.blocks.BNGlass;
import org.betterx.betternether.blocks.BNPane;
import org.betterx.betternether.recipes.RecipesHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.wover.core.api.ModCore;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class ColoredGlassMaterial {
    public final Block white;
    public final Block orange;
    public final Block magenta;
    public final Block light_blue;
    public final Block yellow;
    public final Block lime;
    public final Block pink;
    public final Block gray;
    public final Block light_gray;
    public final Block cyan;
    public final Block purple;
    public final Block blue;
    public final Block brown;
    public final Block green;
    public final Block red;
    public final Block black;

    /**
     * Full Block Constructor
     *
     * @param name - base name of block (prefix) and it's group
     * @param base - block base for material properties and crafting
     */
    public <T extends Block> ColoredGlassMaterial(String name, Block base) {
        white = makeInstance(name, base, Items.DYE.white(), true, false, RecipeCategory.BUILDING_BLOCKS);
        orange = makeInstance(name, base, Items.DYE.orange(), true, false, RecipeCategory.BUILDING_BLOCKS);
        magenta = makeInstance(name, base, Items.DYE.magenta(), true, false, RecipeCategory.BUILDING_BLOCKS);
        light_blue = makeInstance(name, base, Items.DYE.lightBlue(), true, false, RecipeCategory.BUILDING_BLOCKS);
        yellow = makeInstance(name, base, Items.DYE.yellow(), true, false, RecipeCategory.BUILDING_BLOCKS);
        lime = makeInstance(name, base, Items.DYE.lime(), true, false, RecipeCategory.BUILDING_BLOCKS);
        pink = makeInstance(name, base, Items.DYE.pink(), true, false, RecipeCategory.BUILDING_BLOCKS);
        gray = makeInstance(name, base, Items.DYE.gray(), true, false, RecipeCategory.BUILDING_BLOCKS);
        light_gray = makeInstance(name, base, Items.DYE.lightGray(), true, false, RecipeCategory.BUILDING_BLOCKS);
        cyan = makeInstance(name, base, Items.DYE.cyan(), true, false, RecipeCategory.BUILDING_BLOCKS);
        purple = makeInstance(name, base, Items.DYE.purple(), true, false, RecipeCategory.BUILDING_BLOCKS);
        blue = makeInstance(name, base, Items.DYE.blue(), true, false, RecipeCategory.BUILDING_BLOCKS);
        brown = makeInstance(name, base, Items.DYE.brown(), true, false, RecipeCategory.BUILDING_BLOCKS);
        green = makeInstance(name, base, Items.DYE.green(), true, false, RecipeCategory.BUILDING_BLOCKS);
        red = makeInstance(name, base, Items.DYE.red(), true, false, RecipeCategory.BUILDING_BLOCKS);
        black = makeInstance(name, base, Items.DYE.black(), true, false, RecipeCategory.BUILDING_BLOCKS);
    }

    /**
     * Pane Block Constructor
     *
     * @param name           - base name of block (prefix) and it's group
     * @param base           - block base for material properties and crafting
     * @param paneDropItself - will pane drop itself on break or not (will require silk
     *                       touch)
     */
    public <T extends Block> ColoredGlassMaterial(String name, Block base, boolean paneDropItself) {
        white = makeInstance(name, base, Items.DYE.white(), false, paneDropItself, RecipeCategory.DECORATIONS);
        orange = makeInstance(name, base, Items.DYE.orange(), false, paneDropItself, RecipeCategory.DECORATIONS);
        magenta = makeInstance(name, base, Items.DYE.magenta(), false, paneDropItself, RecipeCategory.DECORATIONS);
        light_blue = makeInstance(name, base, Items.DYE.lightBlue(), false, paneDropItself, RecipeCategory.DECORATIONS);
        yellow = makeInstance(name, base, Items.DYE.yellow(), false, paneDropItself, RecipeCategory.DECORATIONS);
        lime = makeInstance(name, base, Items.DYE.lime(), false, paneDropItself, RecipeCategory.DECORATIONS);
        pink = makeInstance(name, base, Items.DYE.pink(), false, paneDropItself, RecipeCategory.DECORATIONS);
        gray = makeInstance(name, base, Items.DYE.gray(), false, paneDropItself, RecipeCategory.DECORATIONS);
        light_gray = makeInstance(name, base, Items.DYE.lightGray(), false, paneDropItself, RecipeCategory.DECORATIONS);
        cyan = makeInstance(name, base, Items.DYE.cyan(), false, paneDropItself, RecipeCategory.DECORATIONS);
        purple = makeInstance(name, base, Items.DYE.purple(), false, paneDropItself, RecipeCategory.DECORATIONS);
        blue = makeInstance(name, base, Items.DYE.blue(), false, paneDropItself, RecipeCategory.DECORATIONS);
        brown = makeInstance(name, base, Items.DYE.brown(), false, paneDropItself, RecipeCategory.DECORATIONS);
        green = makeInstance(name, base, Items.DYE.green(), false, paneDropItself, RecipeCategory.DECORATIONS);
        red = makeInstance(name, base, Items.DYE.red(), false, paneDropItself, RecipeCategory.DECORATIONS);
        black = makeInstance(name, base, Items.DYE.black(), false, paneDropItself, RecipeCategory.DECORATIONS);
    }

    private Block makeInstance(
            String group,
            Block base,
            Item dye,
            boolean isFullBlock,
            boolean paneDropItself,
            RecipeCategory category
    ) {
        String dyeName = BuiltInRegistries.ITEM.getKey(dye).getPath().replace("_dye", "");
        String name = group + "_" + dyeName;
        Block block = NetherBlocks.registerBlock(name, () -> isFullBlock ? new BNGlass(base) : BNPane.from(base, paneDropItself));
        if (ModCore.isDatagen())
            RecipesHelper.makeColoringRecipe(base, block, dye, group, category);

        return block;
    }
}
