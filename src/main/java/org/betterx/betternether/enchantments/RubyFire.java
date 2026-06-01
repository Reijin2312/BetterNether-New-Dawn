package org.betterx.betternether.enchantments;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubyFire {
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private static final Map<Item, RecipeHolder<BlastingRecipe>> FIRE_CONVERSIONS = new HashMap<>();
    public static final ThreadLocal<List<ItemStack>> convertedDrops = ThreadLocal.withInitial(ArrayList::new);

    public static boolean getDrops(
            BlockState brokenBlock,
            ServerLevel level,
            BlockPos blockPos,
            Player player,
            ItemStack breakingItem
    ) {
        final var rubyFire = NetherEnchantments.RUBY_FIRE.getHolder(player.registryAccess());
        if (rubyFire == null) return false;
        final int fireLevel = EnchantmentHelper.getItemEnchantmentLevel(rubyFire, breakingItem);
        if (fireLevel > 0) {
            // Respect Silk Touch: if present, skip auto-smelting entirely
            var silk = player.registryAccess()
                             .lookupOrThrow(Registries.ENCHANTMENT)
                             .getOrThrow(Enchantments.SILK_TOUCH);
            if (EnchantmentHelper.getItemEnchantmentLevel(silk, breakingItem) > 0) {
                return false;
            }

            if (FIRE_CONVERSIONS.isEmpty()) buildConversionTable(level);

            boolean didConvert = false;
            int xpDrop = 0;
            try {
                final List<ItemStack> drops = Block.getDrops(brokenBlock, level, blockPos, null, player, breakingItem);
                convertedDrops.get().clear();

                for (ItemStack stack : drops) {
                    RecipeHolder<BlastingRecipe> resultHolder = FIRE_CONVERSIONS.get(stack.getItem());
                    BlastingRecipe result = resultHolder != null ? resultHolder.value() : null;
                    if (result != null) {
                        didConvert = true;
                        final ItemStack resultStack = result.assemble(new SingleRecipeInput(stack));
                        xpDrop += result.experience();
                        convertedDrops.get()
                                      .add(new ItemStack(
                                              resultStack.getItem(),
                                              resultStack.getCount() * stack.getCount()
                                      ));
                    } else {
                        convertedDrops.get().add(stack);
                    }
                }
            } catch (Exception e) {
                BetterNether.C.log.error("Unable to get Drops for " + breakingItem, e);
            }

            if (didConvert) {
                if (xpDrop > 0) {
                    popExperience(level, blockPos, xpDrop);
                }
                convertedDrops.get().forEach(itemStack -> Block.popResource(level, blockPos, itemStack));
                brokenBlock.spawnAfterBreak(level, blockPos, breakingItem, true);
                convertedDrops.get().clear();
                return true;
            }
            convertedDrops.get().clear();
        }

        return false;
    }

    private static void popExperience(ServerLevel level, BlockPos blockPos, int amount) {
        if (level.getGameRules().get(GameRules.BLOCK_DROPS)) {
            ExperienceOrb.award(level, Vec3.atCenterOf(blockPos), amount);
        }
    }

    private static void buildConversionTable(ServerLevel level) {
        final var recipes = level.getServer()
                                 .getRecipeManager()
                                 .getRecipes()
                                 .stream()
                                 .filter(recipeHolder -> recipeHolder.value() instanceof BlastingRecipe)
                                 .map(recipeHolder -> (RecipeHolder<BlastingRecipe>) recipeHolder)
                                 .toList();

        for (RecipeHolder<BlastingRecipe> r : recipes) {
            Ingredient ingredient = r.value().input();
            for (var itemHolder : ingredient.items().toList()) {
                Item item = itemHolder.value();
                if (item instanceof BlockItem blitem) {
                        if (blitem.getBlock().defaultBlockState().is(CommonBlockTags.IS_OBSIDIAN)) {
                            continue;
                        }
                }
                FIRE_CONVERSIONS.put(item, r);
            }
        }
    }
}
