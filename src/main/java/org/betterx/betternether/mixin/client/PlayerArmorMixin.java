package org.betterx.betternether.mixin.client;

import org.betterx.betternether.config.Configs;

import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AvatarRenderer.class, remap = false)
public abstract class PlayerArmorMixin extends LivingEntityRenderer<Avatar, AvatarRenderState, PlayerModel> {
    protected PlayerArmorMixin(EntityRendererProvider.Context context, PlayerModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void bcl_onInit(EntityRendererProvider.Context context, boolean slim, CallbackInfo info) {
        // EMF/ETF keep render-state associated with the armor layer created by
        // AvatarRenderer. Replacing that instance corrupts per-slot models/UVs.
        if (!Configs.CLIENT.thinArmor.get()
                || ModList.get().isLoaded("entity_model_features")
                || ModList.get().isLoaded("entity_texture_features")) {
            return;
        }

        for (int i = 0; i < this.layers.size(); i++) {
            RenderLayer<AvatarRenderState, PlayerModel> feature = this.layers.get(i);
            if (feature instanceof HumanoidArmorLayer<?, ?, ?>) {
                this.layers.remove(i);
                break;
            }
        }

        this.layers.add(
                0,
                new HumanoidArmorLayer<>(
                        this,
                        ArmorModelSet.bake(
                                slim ? ModelLayers.PLAYER_SLIM_ARMOR : ModelLayers.PLAYER_ARMOR,
                                context.getModelSet(),
                                part -> new PlayerModel(part, slim)
                        ),
                        context.getEquipmentRenderer()
                )
        );
    }
}
