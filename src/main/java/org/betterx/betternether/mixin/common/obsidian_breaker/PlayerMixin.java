package org.betterx.betternether.mixin.common.obsidian_breaker;

import org.betterx.betternether.enchantments.ObsidianBreaker;
import org.betterx.betternether.registry.NetherEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class, remap = false)
public class PlayerMixin {
    @WrapOperation(method = "createAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;createLivingAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"))
    private static AttributeSupplier.Builder wover_test_createAttributes(Operation<AttributeSupplier.Builder> original) {
        return original.call().add(NetherEnchantments.OBSIDIAN_BLOCK_BREAK_SPEED);
    }

    @Inject(method = "getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F", at = @At("RETURN"), cancellable = true, require = 0)
    private void wover_test_getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        final LivingEntity entity = (LivingEntity) (Object) this;
        cir.setReturnValue(ObsidianBreaker.modifyObsidianBreakerSpeed(blockState, cir.getReturnValue(), entity));
    }

}
