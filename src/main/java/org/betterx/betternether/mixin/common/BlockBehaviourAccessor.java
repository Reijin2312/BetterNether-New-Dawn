package org.betterx.betternether.mixin.common;

import net.minecraft.world.level.block.state.BlockBehaviour;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = BlockBehaviour.class, remap = false)
public abstract class BlockBehaviourAccessor {
    /**
     * Accessor for the block behaviour properties.
     * We call the new virtual method `properties()` via reflection and fall back to the field name if mappings differ.
     */
    @Unique
    public BlockBehaviour.Properties bn_getProperties() {
        try {
            Method m = BlockBehaviour.class.getDeclaredMethod("properties");
            m.setAccessible(true);
            return (BlockBehaviour.Properties) m.invoke(this);
        } catch (Exception ignored) {
            // Fallback to the backing field in case the method name changes in the obfuscated environment
            try {
                Field f = BlockBehaviour.class.getDeclaredField("properties");
                f.setAccessible(true);
                return (BlockBehaviour.Properties) f.get(this);
            } catch (Exception e) {
                throw new RuntimeException("Failed to access BlockBehaviour properties", e);
            }
        }
    }
}
