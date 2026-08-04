package org.betterx.betternether.mixin.common;

import org.betterx.betternether.advancements.BNCriterion;
import org.betterx.betternether.blockentities.ChangebleCookTime;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FuelValues;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractFurnaceBlockEntity.class, remap = false)
public abstract class AbstractFurnaceBlockEntityMixin {
    @Unique
    private static final double BETTERNETHER$CRYSTAL_BURN_CREDIT_RANGE = 8.0;

    @Inject(method = "getTotalCookTime", at = @At("RETURN"), cancellable = true)
    private static void betternether$getTotalCookTime(
            ServerLevel level,
            AbstractFurnaceBlockEntity inventory,
            CallbackInfoReturnable<Integer> cir
    ) {
        if (inventory instanceof ChangebleCookTime cct) {
            int val = cir.getReturnValue();
            cir.setReturnValue(cct.changeCookTime(val));
        }
    }

    @Inject(method = "getBurnDuration", at = @At("RETURN"))
    private void betternether$creditCrystalBurn(
            FuelValues fuelValues,
            ItemStack itemStack,
            CallbackInfoReturnable<Integer> cir
    ) {
        if (cir.getReturnValueI() <= 0
                || !(itemStack.is(NetherBlocks.GLOOMSCULK_CRYSTAL.asItem())
                || itemStack.is(NetherBlocks.GLOOMSCULK_GEODE_CRYSTAL.asItem()))) {
            return;
        }

        BlockEntity self = (BlockEntity) (Object) this;
        Level level = self.getLevel();
        if (!(level instanceof ServerLevel)) return;

        BlockPos pos = self.getBlockPos();
        Player player = level.getNearestPlayer(
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                BETTERNETHER$CRYSTAL_BURN_CREDIT_RANGE,
                false
        );
        if (player instanceof ServerPlayer serverPlayer) {
            BNCriterion.BURNED_GLOOMSCULK_CRYSTAL.trigger(serverPlayer);
        }
    }
}
