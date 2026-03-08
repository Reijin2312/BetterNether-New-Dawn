package org.betterx.betternether.mixin.common;

import org.betterx.wover.tag.api.predefined.CommonBlockTags;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.block.state.BlockBehaviour;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PortalShape.class)
public class PortalShapeMixin {
    @Shadow
    @Final
    @Mutable
    private static BlockBehaviour.StatePredicate FRAME;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void bn_extendPortalFramePredicate(CallbackInfo ci) {
        BlockBehaviour.StatePredicate original = FRAME;
        FRAME = (state, level, pos) ->
                original.test(state, level, pos) || state.is(CommonBlockTags.NETHER_PORTAL_FRAME);
    }
}
