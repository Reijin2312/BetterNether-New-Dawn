package org.betterx.betternether.mixin.common;

import org.betterx.betternether.portals.BNPortalShape;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PortalShape.class, remap = false)
public class PortalShapeMixin {
    @Unique
    private static final Map<PortalShape, BNPortalShape> BN_CUSTOM_SHAPES = Collections.synchronizedMap(
            new WeakHashMap<>()
    );

    @Shadow
    @Final
    @Mutable
    private static BlockBehaviour.StatePredicate FRAME;

    @Invoker("<init>")
    public static PortalShape bn_makePortalShape(
            Direction.Axis axis,
            int numPortalBlocks,
            Direction rightDir,
            BlockPos bottomLeft,
            int width,
            int height
    ) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void bn_extendPortalFramePredicate(CallbackInfo ci) {
        BlockBehaviour.StatePredicate original = FRAME;
        FRAME = (state, level, pos) ->
                original.test(state, level, pos) || state.is(CommonBlockTags.NETHER_PORTAL_FRAME);
    }

    @Inject(method = "findAnyShape", at = @At("HEAD"), cancellable = true)
    private static void bn_findAnyShape(
            BlockGetter blockGetter,
            BlockPos blockPos,
            Direction.Axis axis,
            CallbackInfoReturnable<PortalShape> cir
    ) {
        if (!(blockGetter instanceof LevelAccessor levelAccessor)) {
            return;
        }

        BNPortalShape shape = new BNPortalShape(levelAccessor, blockPos, axis);
        if (!shape.isValid()) {
            return;
        }

        BlockPos bottomLeft = shape.getBottomLeft();
        int width = shape.getBoundingWidth();
        int height = shape.getBoundingHeight();
        if (bottomLeft == null || width <= 0 || height <= 0) {
            return;
        }

        Direction rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        PortalShape portalShape = bn_makePortalShape(
                axis,
                shape.getExistingPortalBlocks(),
                rightDir,
                bottomLeft,
                width,
                height
        );
        BN_CUSTOM_SHAPES.put(portalShape, shape);
        cir.setReturnValue(portalShape);
    }

    @Inject(method = "createPortalBlocks", at = @At("HEAD"), cancellable = true)
    private void bn_createPortalBlocks(LevelAccessor levelAccessor, CallbackInfo ci) {
        BNPortalShape shape = BN_CUSTOM_SHAPES.get((PortalShape) (Object) this);
        if (shape != null) {
            shape.createPortalBlocks();
            ci.cancel();
        }
    }

    @Inject(method = "isComplete", at = @At("HEAD"), cancellable = true)
    private void bn_isComplete(CallbackInfoReturnable<Boolean> cir) {
        BNPortalShape shape = BN_CUSTOM_SHAPES.get((PortalShape) (Object) this);
        if (shape != null) {
            cir.setReturnValue(shape.isComplete());
        }
    }

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void bn_isValid(CallbackInfoReturnable<Boolean> cir) {
        BNPortalShape shape = BN_CUSTOM_SHAPES.get((PortalShape) (Object) this);
        if (shape != null) {
            cir.setReturnValue(shape.isValid());
        }
    }
}
