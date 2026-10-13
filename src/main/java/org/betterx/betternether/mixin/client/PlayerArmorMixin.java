package org.betterx.betternether.mixin.client;

import org.betterx.betternether.config.Configs;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Selects vanilla's slim armor geometry without replacing its armor render layer. Keeping vanilla's
 * layer factory lets rendering mods such as 3D Skin Layers apply their normal compatibility hooks.
 */
@Mixin(AvatarRenderer.class)
public abstract class PlayerArmorMixin {
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ArmorModelSet;bake(Lnet/minecraft/client/renderer/entity/ArmorModelSet;Lnet/minecraft/client/model/geom/EntityModelSet;Ljava/util/function/Function;)Lnet/minecraft/client/renderer/entity/ArmorModelSet;"
            ),
            index = 0
    )
    private ArmorModelSet<ModelLayerLocation> bn_useThinArmorLayers(ArmorModelSet<ModelLayerLocation> locations) {
        return Configs.CLIENT.thinArmor.get() ? ModelLayers.PLAYER_SLIM_ARMOR : locations;
    }
}
