package org.betterx.betternether.mixin.common.obsidian_breaker;

import org.betterx.betternether.enchantments.ObsidianBreaker;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class, remap = false)
public class PlayerMixin {
    @Inject(method = "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F", at = @At("RETURN"), cancellable = true, require = 0)
    private void wover_test_getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        final LivingEntity entity = (LivingEntity) (Object) this;
        cir.setReturnValue(ObsidianBreaker.modifyObsidianBreakerSpeed(blockState, cir.getReturnValue(), entity));
    }

}
