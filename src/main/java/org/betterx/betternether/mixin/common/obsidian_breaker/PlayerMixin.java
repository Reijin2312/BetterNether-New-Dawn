package org.betterx.betternether.mixin.common.obsidian_breaker;

import org.betterx.betternether.enchantments.ObsidianBreaker;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Player.class, remap = false)
public class PlayerMixin {
    @WrapOperation(method = "getDigSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"))
    float wover_test_getDestroySpeed(Inventory instance, BlockState blockState, Operation<Float> original) {
        final LivingEntity entity = (LivingEntity) (Object) this;
        return ObsidianBreaker.modifyObsidianBreakerSpeed(blockState, original.call(instance, blockState), entity);
    }

}
