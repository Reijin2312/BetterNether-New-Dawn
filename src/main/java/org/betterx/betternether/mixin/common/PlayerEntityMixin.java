package org.betterx.betternether.mixin.common;

import org.betterx.betternether.blocks.BlockStatueRespawner;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = ServerPlayer.class, priority = 200)
public abstract class PlayerEntityMixin {

    @Inject(method = "findRespawnAndUseSpawnBlock", at = @At(value = "HEAD"), cancellable = true, remap = false, require = 0)
    private static void bn_findRespawnAndUseSpawnBlock(
            ServerLevel world,
            ServerPlayer.RespawnConfig respawnConfig,
            boolean bl,
            CallbackInfoReturnable<Optional<ServerPlayer.RespawnPosAngle>> info
    ) {
        if (respawnConfig == null || respawnConfig.respawnData() == null) {
            return;
        }
        BlockPos pos = respawnConfig.respawnData().pos();
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof BlockStatueRespawner) {
            info.setReturnValue(bn_findRespawnPosition(world, pos, blockState));
            info.cancel();
        }
    }

    @Unique
    private static Optional<ServerPlayer.RespawnPosAngle> bn_findRespawnPosition(
            ServerLevel world,
            BlockPos pos,
            BlockState state
    ) {
        if (state.getValue(BlockStatueRespawner.TOP)) {
            pos = pos.below();
        }
        pos = pos.relative(state.getValue(BlockStatueRespawner.FACING));
        BlockState state2 = world.getBlockState(pos);
        if (!state2.blocksMotion() && state2.getCollisionShape(world, pos).isEmpty()) {
            Vec3 respawnPos = Vec3.atLowerCornerOf(pos).add(0.5, 0, 0.5);
            return Optional.of(ServerPlayer.RespawnPosAngle.of(respawnPos, pos, 0.0F));
        }
        return Optional.empty();
    }
}
