package org.betterx.betternether.integrations.jei;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class ForgeDisplay {
    public final RecipeHolder<SmeltingRecipe> recipe;

    public ForgeDisplay(RecipeHolder<SmeltingRecipe> recipe) {
        this.recipe = recipe;
    }

    public Ingredient getInput() {
        if (recipe.value().getIngredients().isEmpty()) {
            return Ingredient.EMPTY;
        }
        return recipe.value().getIngredients().get(0);
    }

    public float getExperience() {
        return recipe.value().getExperience();
    }

    public int getCookingTime() {
        return recipe.value().getCookingTime();
    }

    public ItemStack getResultItem(HolderLookup.Provider provider) {
        ItemStack[] items = getInput().getItems();
        ItemStack sampleInput = items.length > 0 ? items[0].copyWithCount(1) : ItemStack.EMPTY;
        if (sampleInput.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return recipe.value().assemble(new SingleRecipeInput(sampleInput), provider);
    }

    public ResourceLocation getId() {
        return recipe.id();
    }
}
