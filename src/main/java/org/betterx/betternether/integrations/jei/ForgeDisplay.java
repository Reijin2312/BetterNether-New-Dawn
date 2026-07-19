package org.betterx.betternether.integrations.jei;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
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
        return recipe.value().input();
    }

    public float getExperience() {
        return recipe.value().experience();
    }

    public int getCookingTime() {
        return recipe.value().cookingTime();
    }

    public ItemStack getResultItem(HolderLookup.Provider provider) {
        ItemStack sampleInput = getInput().items()
                                          .findFirst()
                                          .map(item -> new ItemStack(item.value()))
                                          .orElse(ItemStack.EMPTY);
        if (sampleInput.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return recipe.value().assemble(new SingleRecipeInput(sampleInput));
    }

    public Identifier getId() {
        return recipe.id().identifier();
    }
}
