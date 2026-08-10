package org.betterx.betternether.mixin.common;

import org.betterx.betternether.registry.BrewingRegistry;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public class BrewingRecipeRegistryMixin {
    @Inject(method = "isIngredient", at = @At("HEAD"), cancellable = true)
    private void bn_isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (BrewingRegistry.isValidIngridient(stack)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "hasMix", at = @At("HEAD"), cancellable = true)
    private void bn_hasMix(ItemStack bottle, ItemStack reagent, CallbackInfoReturnable<Boolean> info) {
        if (BrewingRegistry.getResult(reagent, bottle) != null) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "mix", at = @At("HEAD"), cancellable = true)
    private void bn_mix(ItemStack reagent, ItemStack bottle, CallbackInfoReturnable<ItemStack> info) {
        final ItemStack result = BrewingRegistry.getResult(reagent, bottle);
        if (result != null) {
            info.setReturnValue(result.copy());
        }
    }
}
