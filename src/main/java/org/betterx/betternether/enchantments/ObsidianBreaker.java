package org.betterx.betternether.enchantments;

import org.betterx.betternether.registry.NetherEnchantments;
import org.betterx.betternether.registry.NetherTags;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;

public class ObsidianBreaker {
    public static float modifyObsidianBreakerSpeed(BlockState blockState, float speed, LivingEntity entity) {
        if (blockState.is(NetherTags.OBSIDIAN_BREAKER_MINEABLE)) {
            speed *= getSpeedMultiplier(entity);
        }
        return speed;
    }

    private static float getSpeedMultiplier(LivingEntity entity) {
        Holder<Enchantment> enchantment = NetherEnchantments.OBSIDIAN_BREAKER.getHolder(entity.level().registryAccess());
        if (enchantment != null) {
            int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, entity.getMainHandItem());
            if (level > 0) {
                return 1.0F + bonusForLevel(level);
            }
        }

        return 1.0F;
    }

    private static float bonusForLevel(int level) {
        return switch (level) {
            case 1 -> 6.0F;
            case 2 -> 12.0F;
            case 3 -> 18.0F;
            default -> level * level + 9.0F;
        };
    }
}
