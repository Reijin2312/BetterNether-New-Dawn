package org.betterx.betternether.entity.render;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public final class RenderPhaseAccessor {
    private RenderPhaseAccessor() {
    }

    public static RenderType getFirefly(Identifier texture) {
        // Eyes shader is fully emissive and overblows firefly layers in 1.21.11.
        return RenderTypes.entityTranslucent(texture);
    }
}
