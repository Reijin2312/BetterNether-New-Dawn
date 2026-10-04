package org.betterx.betternether.mixin.common;

import org.betterx.betternether.portals.BNPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.portal.PortalShape;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.WeakHashMap;

import org.spongepowered.asm.mixin.Mixin;
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

    @Invoker("<init>")
    public static PortalShape bn_makePortalShape(
            Direction.Axis axis,
            int portalBlockCount,
            Direction rightDir,
            BlockPos bottomLeft,
            int width,
            int height
    ) {
        throw new AssertionError();
    }

    @Inject(method = "findPortalShape", at = @At("HEAD"), cancellable = true)
    private static void bn_findPortalShape(
            LevelAccessor levelAccessor,
            BlockPos blockPos,
            Predicate<PortalShape> predicate,
            Direction.Axis axis,
            CallbackInfoReturnable<Optional<PortalShape>> cir
    ) {
        Optional<PortalShape> shape = bn_findCustomShape(levelAccessor, blockPos, predicate, axis);
        if (shape.isEmpty()) {
            Direction.Axis otherAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            shape = bn_findCustomShape(levelAccessor, blockPos, predicate, otherAxis);
        }
        if (shape.isPresent()) {
            cir.setReturnValue(shape);
        }
    }

    @Unique
    private static Optional<PortalShape> bn_findCustomShape(
            LevelAccessor levelAccessor,
            BlockPos blockPos,
            Predicate<PortalShape> predicate,
            Direction.Axis axis
    ) {
        BNPortalShape customShape = new BNPortalShape(levelAccessor, blockPos, axis);
        if (!customShape.isValid()) return Optional.empty();

        Direction rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        PortalShape portalShape = bn_makePortalShape(
                axis,
                customShape.getExistingPortalBlocks(),
                rightDir,
                customShape.getBottomLeft(),
                customShape.getBoundingWidth(),
                customShape.getBoundingHeight()
        );
        BN_CUSTOM_SHAPES.put(portalShape, customShape);
        if (predicate.test(portalShape)) return Optional.of(portalShape);

        BN_CUSTOM_SHAPES.remove(portalShape);
        return Optional.empty();
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
