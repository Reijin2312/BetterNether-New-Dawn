package org.betterx.betternether.integrations.jei;

import org.betterx.betternether.blockentities.BlockEntityForge;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class JEIForgeCategory implements IRecipeCategory<ForgeDisplay> {
    private static final int WIDTH = 82;
    private static final int HEIGHT = 54;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated flame;
    private final Component title;

    public JEIForgeCategory(IGuiHelper guiHelper) {
        Identifier vanillaFurnace = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/container/furnace.png");
        this.background = guiHelper.createDrawable(vanillaFurnace, 55, 16, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                new ItemStack(NetherBlocks.CINCINNASITE_FORGE)
        );
        this.title = Component.translatable("betternether.jei.forge.category");
        this.flame = guiHelper.drawableBuilder(vanillaFurnace, 176, 0, 14, 14)
                              .buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
        this.arrow = guiHelper.drawableBuilder(vanillaFurnace, 176, 14, 24, 17)
                              .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public @NotNull IRecipeType<ForgeDisplay> getRecipeType() {
        return JEIPlugin.FORGE_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return title;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ForgeDisplay display, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).add(display.getInput());
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            ItemStack result = display.getResultItem(minecraft.level.registryAccess());
            if (!result.isEmpty()) {
                builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 19).add(result);
            }
        }
    }

    @Override
    public void draw(
            ForgeDisplay display,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphicsExtractor guiGraphics,
            double mouseX,
            double mouseY
    ) {
        background.draw(guiGraphics, 0, 0);
        flame.draw(guiGraphics, 1, 20);
        arrow.draw(guiGraphics, 24, 18);

        float experience = display.getExperience();
        if (experience > 0) {
            Component experienceString = Component.translatable("betternether.jei.cooking.experience", experience);
            int xPos = 82 - Minecraft.getInstance().font.width(experienceString);
            guiGraphics.text(Minecraft.getInstance().font, experienceString, xPos, 0, 0xFF808080, false);
        }

        float cookTimeSeconds = (display.getCookingTime() / (float) BlockEntityForge.SPEEDUP) / 20.0F;
        String timeText = String.format(Locale.ROOT, "%.1fs", cookTimeSeconds);
        int timeXPos = 70 - (Minecraft.getInstance().font.width(timeText) / 2);
        guiGraphics.text(Minecraft.getInstance().font, timeText, timeXPos, 46, 0xFF404040, false);
    }
}
