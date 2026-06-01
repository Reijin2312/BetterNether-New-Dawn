package org.betterx.betternether.registry;

import org.betterx.betternether.BN;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.ArrayList;
import java.util.List;

public class BrewingRegistry {
    private static final List<BrewingRecipe> RECIPES = new ArrayList<>();
    private static final Identifier BARREL_CACTUS_ID = BN.id("barrel_cactus");
    private static final Identifier HOOK_MUSHROOM_ID = BN.id("hook_mushroom");
    private static boolean initialized = false;

    /**
        * Регистрируем рецепты варки. Делается лениво, чтобы не трогать
        * регистры блоков/предметов после их заморозки.
        */
    public static void register() {
        // Recipes are initialized lazily because ItemStack components are not bound during mod construction on 26.x.
    }

    private static void ensureRecipes() {
        if (initialized) return;
        initialized = true;

        register(
                stackFromId(BARREL_CACTUS_ID),
                new ItemStack(Items.GLASS_BOTTLE),
                makePotion(Potions.WATER)
        );
        register(stackFromId(HOOK_MUSHROOM_ID), makePotion(Potions.AWKWARD), makePotion(Potions.HEALING));
    }

    private static ItemStack stackFromId(Identifier id) {
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static void register(ItemStack source, ItemStack bottle, ItemStack result) {
        if (source.isEmpty()) return;
        RECIPES.add(new BrewingRecipe(source, bottle, result));
    }

    private static ItemStack makePotion(Holder<Potion> potion) {
        return PotionContents.createItemStack(Items.POTION, potion);
    }

    public static class BrewingRecipe {
        private final ItemStack source;
        private final ItemStack bottle;
        private final ItemStack result;

        public BrewingRecipe(ItemStack source, ItemStack bottle, ItemStack result) {
            this.source = source;
            this.bottle = bottle;
            this.result = result;
        }

        public boolean isValid(ItemStack source, ItemStack bottle) {
            return ItemStack.isSameItem(this.source, source) && ItemStack.isSameItem(this.bottle, bottle);
        }

        public boolean isValid(ItemStack source) {
            return ItemStack.isSameItem(this.source, source);
        }

        public ItemStack getResult() {
            return result;
        }
    }

    public static ItemStack getResult(ItemStack source, ItemStack bottle) {
        ensureRecipes();
        for (BrewingRecipe recipe : RECIPES)
            if (recipe.isValid(source, bottle))
                return recipe.getResult();
        return null;
    }

    public static boolean isValidIngridient(ItemStack source) {
        ensureRecipes();
        for (BrewingRecipe recipe : RECIPES)
            if (recipe.isValid(source))
                return true;
        return false;
    }
}
