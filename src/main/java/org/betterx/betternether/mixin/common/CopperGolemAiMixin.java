package org.betterx.betternether.mixin.common;

import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.world.entity.animal.golem.CopperGolemAi;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

/** Lets copper golems recognize modded wooden chests as item-delivery targets. */
@Mixin(CopperGolemAi.class)
public class CopperGolemAiMixin {
    @ModifyArg(
            method = "initIdleActivity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/behavior/TransportItemsBetweenContainers;<init>(FLjava/util/function/Predicate;Ljava/util/function/Predicate;IILjava/util/Map;Ljava/util/function/Consumer;Ljava/util/function/Predicate;)V"
            ),
            index = 2
    )
    private static Predicate<BlockState> bn_acceptWoodenChests(Predicate<BlockState> destination) {
        return destination.or(state -> state.is(CommonBlockTags.WOODEN_CHEST));
    }
}
