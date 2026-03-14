package org.betterx.betternether.integrations.jei;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.recipe.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final RecipeType<ForgeDisplay> FORGE_RECIPE_TYPE =
            RecipeType.create(BetterNether.MOD_ID, "forge", ForgeDisplay.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return BetterNether.C.mk("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new JEIForgeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;
        RecipeManager recipeManager = minecraft.level.getRecipeManager();

        List<ForgeDisplay> smeltingRecipes = collectRecipes(recipeManager, SmeltingRecipe.class)
                .stream()
                .map(ForgeDisplay::new)
                .toList();
        registration.addRecipes(FORGE_RECIPE_TYPE, smeltingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(NetherBlocks.CINCINNASITE_FORGE), FORGE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NetherBlocks.CINCINNASITE_FORGE), RecipeTypes.SMELTING);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Recipe<?>> List<RecipeHolder<T>> collectRecipes(
            RecipeManager recipeManager,
            Class<T> recipeClass
    ) {
        return recipeManager.getRecipes()
                            .stream()
                            .filter(recipeHolder -> recipeClass.isInstance(recipeHolder.value()))
                            .map(recipeHolder -> (RecipeHolder<T>) recipeHolder)
                            .toList();
    }
}
