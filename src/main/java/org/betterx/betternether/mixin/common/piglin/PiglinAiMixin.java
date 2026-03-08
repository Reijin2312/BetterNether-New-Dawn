package org.betterx.betternether.mixin.common.piglin;

import org.betterx.betternether.config.Configs;
import org.betterx.betternether.items.NetherArmor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PiglinAi.class, remap = false)
public class PiglinAiMixin {
    @Inject(method = "isWearingSafeArmor", at = @At("RETURN"), cancellable = true)
    private static void bn_isWearingGold(
            LivingEntity entity,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (cir.getReturnValue() || !Configs.GAME_RULES.piglinIgnoreNetherArmor.get()) {
            return;
        }

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;
            ItemStack stack = entity.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof NetherArmor) {
                cir.setReturnValue(true);
                return;
            }
        }
    }
}
